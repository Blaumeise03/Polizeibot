package de.demokratie.polizeibot.utils;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.date.DateUtil;
import de.demokratie.polizeibot.embed.EmbedCreator;
import de.demokratie.polizeibot.objects.Information;
import de.demokratie.polizeibot.objects.Mute;
import de.demokratie.polizeibot.objects.Warn;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

    public static int warn(Member m, String reason, Member warner) {
        try {
            File d = new File("users/" + m.getId() + "/");
            File f = new File("users/" + m.getId() + "/warns.yml");
            if (!d.exists()) {
                d.mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }

            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
            int warns = c.getInt("warns");
            c.set("warns", ++warns);

            List<String> reasons = c.getStringList("reasons");
            reasons.add(reason);
            c.set("reasons", reasons);
            c.set(reason.replace(".", "-") + ".warner", warner.getId());
            c.set(reason.replace(".", "-") + ".date", System.currentTimeMillis());
            c.save(f);

            return warns;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void mute(GuildMessageReceivedEvent e, Member m, String reason, String TYPE) {
        try {
            switch(TYPE) {
                case "GENERAL":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0)).queue();
                    break;
                case "VOICE":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Voicemute", true).get(0)).queue();
                    break;
                case "CHAT":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Chatmute", true).get(0)).queue();
                    break;
            }
            File d = new File("users/" + m.getId() + "/");
            File f = new File("users/" + m.getId() + "/mutes.yml");
            if(!d.exists()) {
                d.mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

            c.set("muted", true);
            c.set("reason", reason);
            c.set("type", TYPE);
            c.set("date", System.currentTimeMillis());
            c.set("muter", e.getMember().getId());
            c.set("permanent", true);
            c.save(f);
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    public static Date tempMute(GuildMessageReceivedEvent e, Member m, String reason, int days, String TYPE) {
        try {
            switch (TYPE) {
                case "GENERAL":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0)).queue();
                    break;
                case "VOICE":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Voicemute", true).get(0)).queue();
                    break;
                case "CHAT":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Chatmute", true).get(0)).queue();
                    break;
            }
            File d = new File("users/" + m.getId() + "/");
            File f = new File("users/" + m.getId() + "/mutes.yml");
            if(!d.exists()) {
                d.mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

            c.set("muted", true);
            c.set("reason", reason);
            c.set("type", TYPE);
            c.set("date", System.currentTimeMillis());
            c.set("muter", e.getMember().getId());
            c.set("permanent", false);
            Date expireDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(expireDate);
            calendar.add(Calendar.DATE, days);
            expireDate = calendar.getTime();
            c.set("expireDate", expireDate.getTime());
            c.save(f);
            File file = new File("tempmutes.yml");
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            return expireDate;
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public static Date tempMute(GuildMessageReceivedEvent e, Member m, String reason, String until, String TYPE) {
        try {
            switch (TYPE) {
                case "GENERAL":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0)).queue();
                    break;
                case "VOICE":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Voicemute", true).get(0)).queue();
                    break;
                case "CHAT":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Chatmute", true).get(0)).queue();
                    break;
            }
            File d = new File("users/" + m.getId() + "/");
            File f = new File("users/" + m.getId() + "/mutes.yml");
            if (!d.exists()) {
                d.mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

            c.set("muted", true);
            c.set("reason", reason);
            c.set("type", TYPE);
            c.set("date", System.currentTimeMillis());
            c.set("muter", e.getMember().getId());
            c.set("permanent", false);

            Date expireDate = null;
            try {
                expireDate = DateUtil.parse(until);
            } catch (ParseException ex) {
                e.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Bitte nutze ein Datum mit folgender Syntax: ```\n" +
                        "dd.MM.yyyy-HH:mm:ss oder\n" +
                        "dd.MM.yyyy```").build()).queue();
                expireDate = new Date();
                return null;
            }

            c.set("expireDate", expireDate.getTime());
            c.save(f);
            File file = new File("tempmutes.yml");
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            return expireDate;
        } catch (IOException exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public static List<Warn> getWarns(Member m) {
        List<Warn> warns = new ArrayList<>();
        File f = new File("users/" + m.getId() + "/warns.yml");
        if (f.exists()) {
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
            List<String> reasons = c.getStringList("reasons");

            Guild guild = m.getGuild();

            reasons.stream().forEach((reason) -> {
                Warn warn = new Warn();
                long millis = c.getLong(reason + ".date");
                warn.setDate(new Date(millis));
                warn.setMember(m);
                Member warner = guild.getMemberById(c.getString(reason + ".warner"));
                warn.setWarner(warner);
                warn.setReason(reason);
                warns.add(warn);
            });
        }
        else {
            return warns;
        }
        return warns;
    }

    public static boolean isMuted(Member m) {
        boolean muted = false;
        File f = new File("users/" + m.getId() + "/mutes.yml");
        if(f.exists()) {
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
            muted = c.getBoolean("muted");
        }
        return muted;
    }

    public static List<Mute> getMutes() {
        List<Mute> list = new ArrayList<>();
        Bot.getBot().jda.getGuilds().stream().forEach((guild -> {
            guild.getMembers().stream().forEach(m -> {
                File f = new File("users/" + m.getId() + "/mutes.yml");
                if(f.exists()) {
                    YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
                    if(c.getBoolean("muted")) {
                        Mute mute = new Mute();
                        long time = c.getLong("expireDate");
                        mute.setDate(new Date(time));
                        mute.setMember(guild.getMemberById(m.getId()));
                        mute.setMuter(guild.getMemberById(c.getString("muter")));
                        mute.setReason(c.getString("reason"));
                        mute.setPermanent(c.getBoolean("permanent"));
                        mute.setType(c.getString("type"));
                        if (!mute.isPermanent()) {
                            Date expireDate = new Date(c.getLong("expireDate"));
                            mute.setExpireDate(expireDate);
                        }
                        list.add(mute);
                    }
                }
            });
        }));
        return list;
    }

    public static Information getInformation(Member m) {
        Information info = new Information(m);
        info.setWarns(getWarns(m));
        info.setMuted(isMuted(m));
        if(info.isMuted()) {

            File f = new File("users/" + m.getId() + "/mutes.yml");

            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

            Mute mute = new Mute();
            mute.setPermanent(c.getBoolean("permanent"));

            if (!mute.isPermanent()) {
                Date d = new Date(c.getLong("expireDate"));
                mute.setExpireDate(d);
            }

            mute.setReason(c.getString("reason"));
            mute.setType(c.getString(("type")));
            mute.setMuted(info.isMuted());
            mute.setDate(new Date(c.getLong("date")));
            /*List<Member> muter = new ArrayList<>();

            Bot.getBot().jda.getGuilds().stream().forEach((guild -> {
                guild.getMembers().stream().forEach((member -> {
                    if(member.getId() == c.getString("muter")) {
                        muter.clear();
                        muter.add(member);
                    }
                }));
            }));

            mute.setMuter(muter.get(0));*/
            mute.setMuter(m.getGuild().getMemberById(c.getString("muter")));
            info.setMute(mute);
        }
        return info;
    }

    public static void unmute(Member m) {
        File f = new File("users/" + m.getId() + "/mutes.yml");
        YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
        c.set("muted", false);
        c.set("reason", null);
        c.set("expireDate", null);
        try {
            c.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(Color color, Member member, Member target, String what_he_done, String long_descr, String offense, String reason) {
        Guild guild = member.getGuild();

        EmbedCreator embed = new EmbedCreator(color);
        embed.setMessageContent(target.getAsMention() + " " + what_he_done);
        embed.setTitle(offense);
        embed.setDescription(long_descr);
        embed.addField("Begündung", reason);
        embed.addField("Von", member.getAsMention());
        embed.addField("", "\n" + member.getAsMention() + ", wenn möglich, bitte Beweis in Form von Bild/Ton senden.");

        guild.getTextChannelsByName("logbuch", true).get(0).sendMessage(embed.build()).queue();

    }

    public static void log(Color color, Member member, Member target, String what_he_done, String long_descr, String title) {
        Guild guild = member.getGuild();

        EmbedCreator embed = new EmbedCreator(color);
        embed.setMessageContent(target.getAsMention() + " " + what_he_done);
        embed.setTitle(title);
        embed.setDescription(long_descr);
        embed.addField("Von", member.getAsMention());

        guild.getTextChannelsByName("logbuch", true).get(0).sendMessage(embed.build()).queue();

    }

}
