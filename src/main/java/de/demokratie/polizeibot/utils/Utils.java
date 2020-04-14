package de.demokratie.polizeibot.utils;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.objects.Mute;
import de.demokratie.polizeibot.objects.Warn;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
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
            c.set(reason + ".warner", warner.getId());
            c.set(reason + ".date", System.currentTimeMillis());
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
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Voice", true).get(0)).queue();
                    break;
                case "CHAT":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Chatmute", true).get(0)).queue();
                    break;
            }
            e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0));
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

    public static void tempMute(GuildMessageReceivedEvent e, Member m, String reason, int days, String TYPE) {
        try {
            switch(TYPE) {
                case "GENERAL":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0)).queue();
                    break;
                case "VOICE":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Voice", true).get(0)).queue();
                    break;
                case "CHAT":
                    e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Chatmute", true).get(0)).queue();
                    break;
            }
            e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0));
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
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    public static List<Warn> getWarns(Member m, GuildMessageReceivedEvent e) {
        List<Warn> warns = new ArrayList<>();
        File f = new File("users/" + m.getId() + "/warns.yml");
        if(f.exists()) {
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
            List<String> reasons = c.getStringList("reasons");
            reasons.stream().forEach((reason) -> {
                Warn warn = new Warn();
                long millis = c.getLong(reason + ".date");
                warn.setDate(new Date(millis));
                warn.setMember(m);
                Member warner = e.getGuild().getMemberById(reason + ".warner");
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
                        list.add(mute);
                    }
                }
            });
        }));
        return list;
    }

}
