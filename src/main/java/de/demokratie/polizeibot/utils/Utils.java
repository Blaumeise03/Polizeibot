package de.demokratie.polizeibot.utils;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.date.DateUtil;
import de.demokratie.polizeibot.embed.EmbedCreator;
import de.demokratie.polizeibot.objects.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

    /**
     * @param m the {@link Member} who gets warned
     * @param reason the reason
     * @param warner the {@link Member} who executed the warn
     * @return always -1
     */
    public static int warn(Member m, String reason, Member warner) {
        SimpleWarnList simpleWarnList = new SimpleWarnList();
        List<SimpleWarn> simpleWarns = new ArrayList<>();
        simpleWarnList.setWarns(simpleWarns);
        SimpleWarn warn = new SimpleWarn();
        warn.setWarner(warner.getId());
        warn.setReason(reason);
        warn.setDate(System.currentTimeMillis());
        simpleWarns.add(warn);
        List<Warn> warns = getWarns(m);
        for(Warn w : warns) {
            SimpleWarn sw = new SimpleWarn();
            sw.setDate(w.getDate().getTime());
            sw.setReason(w.getReason());
            sw.setWarner(w.getWarner().getId());
            simpleWarns.add(sw);
        }
        try {
            ConfigUtils.serialize(simpleWarnList, "users/" + m.getId() + "/warns.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void mute(GuildMessageReceivedEvent e, Member m, String reason, MuteType type) {
        try {
            switch (type) {
                case GENERAL:
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0)).queue();
                    break;
                case VOICE:
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Voicemute", true).get(0)).queue();
                    break;
                case CHAT:
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Chatmute", true).get(0)).queue();
                    break;
            }

            SimpleMute mute = new SimpleMute();
            mute.setMuted(true);
            mute.setReason(reason);
            mute.setType(type.getName());
            mute.setDate(System.currentTimeMillis());
            mute.setMuter(e.getMember().getId());
            mute.setPermanent(true);
            ConfigUtils.serialize(mute, "users/" + m.getId() + "/mutes.yml");
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public static Date tempMute(GuildMessageReceivedEvent e, Member m, String reason, int days, MuteType type) {
        try {
            switch (type) {
                case GENERAL:
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0)).queue();
                    break;
                case VOICE:
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Voicemute", true).get(0)).queue();
                    break;
                case CHAT:
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Chatmute", true).get(0)).queue();
                    break;
            }
            SimpleMute mute = new SimpleMute();
            mute.setMuted(true);
            mute.setReason(reason);
            mute.setType(type.getName());
            mute.setDate(System.currentTimeMillis());
            mute.setMuter(e.getMember().getId());
            mute.setPermanent(false);
            Date expireDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(expireDate);
            calendar.add(Calendar.DATE, days);
            expireDate = calendar.getTime();
            mute.setExpireDate(expireDate.getTime());
            ConfigUtils.serialize(mute, "users/" + m.getId() + "/mutes.yml");
            return expireDate;
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public static Date tempMute(GuildMessageReceivedEvent e, Member m, String reason, String until, MuteType type) {
        try {
            switch (type) {
                case GENERAL:
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0)).queue();
                    break;
                case VOICE:
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Voicemute", true).get(0)).queue();
                    break;
                case CHAT:
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
            SimpleMute mute = new SimpleMute();
            mute.setMuted(true);
            mute.setReason(reason);
            mute.setType(type.getName());
            mute.setDate(System.currentTimeMillis());
            mute.setMuter(e.getMember().getId());
            mute.setPermanent(false);
            Date expireDate = null;
            try {
                expireDate = DateUtil.parse(until);
            } catch (ParseException ex) {
                e.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Bitte nutze ein Datum mit folgender Syntax: ```\n" +
                        "dd.MM.yyyy-HH:mm:ss oder\n" +
                        "dd.MM.yyyy```").build()).queue();
                return null;
            }
            mute.setExpireDate(expireDate.getTime());
            ConfigUtils.serialize(mute, "users/" + m.getId() + "/mutes.yml");
            return expireDate;
        } catch (IOException exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public static List<Warn> getWarns(Member m) {
        List<Warn> warns = new ArrayList<>();
        /*
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
        }*/
        File f = new File("users/" + m.getId() + "/warns.yml");
        if (f.exists()) {
            try {
                SimpleWarnList list = (SimpleWarnList) ConfigUtils.deserialize("users/" + m.getId() + "/warns.yml");
                Guild guild = m.getGuild();
                if (list != null)
                    for (SimpleWarn sWarn : list.getWarns()) {
                        Warn warn = new Warn();
                        long millis = sWarn.getDate();
                        warn.setDate(new Date(millis));;
                        Member warner = guild.getMemberById(sWarn.getWarner());
                        warn.setWarner(warner);
                        warn.setReason(sWarn.getReason());
                        warns.add(warn);
                    }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return warns;
    }

    public static boolean isMuted(Member m) {
        File f = new File("users/" + m.getId() + "/mutes.yml");
        if (f.exists()) {
            try {
                SimpleMute sMute = (SimpleMute) ConfigUtils.deserialize("users/" + m.getId() + "/mutes.yml");
                if (sMute != null && sMute.getMuted()) return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static List<Mute> getMutes() {
        List<Mute> list = new ArrayList<>();
        Bot.getBot().jda.getGuilds().forEach((guild -> {
            guild.getMembers().forEach(m -> {
                File f = new File("users/" + m.getId() + "/mutes.yml");
                if (f.exists()) {
                    /*
                    YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
                    if (c.getBoolean("muted")) {
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

                    }*/
                    try {
                        SimpleMute sMute = (SimpleMute) ConfigUtils.deserialize("users/" + m.getId() + "/mutes.yml");
                        if(sMute != null && sMute.getMuted()) {
                            Mute mute = new Mute();
                            mute.setDate(new Date(sMute.getDate()));
                            mute.setMember(guild.getMemberById(m.getId()));
                            mute.setMuter(guild.getMemberById(sMute.getMuter()));
                            mute.setReason(sMute.getReason());
                            mute.setPermanent(sMute.getPermanent());
                            mute.setType(MuteType.getType(sMute.getType()));
                            if (!mute.isPermanent()) {
                                Date expireDate = new Date(sMute.getExpireDate());
                                mute.setExpireDate(expireDate);
                            }
                            list.add(mute);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
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
        if (info.isMuted()) {
            try {
                SimpleMute simpleMute = (SimpleMute) ConfigUtils.deserialize("users/" + m.getId() + "/mutes.yml");
                Mute mute = new Mute();
                assert simpleMute != null;
                mute.setPermanent(simpleMute.getPermanent());
                if (!mute.isPermanent()) {
                    Date d = new Date();
                    mute.setExpireDate(d);
                }
                mute.setReason(simpleMute.getReason());
                mute.setType(MuteType.getType(simpleMute.getType()));
                mute.setMuted(info.isMuted());
                mute.setDate(new Date(simpleMute.getDate()));
                mute.setMuter(m.getGuild().getMemberById(simpleMute.getMuter()));
                info.setMute(mute);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return info;
    }

    public static void unmute(Member m) {
        File f = new File("users/" + m.getId() + "/mutes.yml");
        SimpleMute mute = new SimpleMute();
        mute.setMuted(false);
        mute.setReason(null);
        mute.setExpireDate(-1);
        try {
            ConfigUtils.serialize(mute, "users/" + m.getId() + "/mutes.yml");
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
