package de.demokratie.polizeibot.utils;

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

    public static void warn(Member m, String reason, Member warner) {
        try {
            File d = new File("users/" + m.getId());
            File f = new File("users/" + m.getId() + "/warns.yml");
            if(!d.exists()) {
                d.mkdir();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
            int warns = c.getInt("warns");
            List<String> reasons = new ArrayList<>();
            reasons.add(reason);
            c.set("reasons", reasons);
            c.set(reason + ".warner", warner.getId());
            c.set(reason + ".date", System.currentTimeMillis());
            c.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void mute(GuildMessageReceivedEvent e, Member m, String reason) {
        try {
            e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0));
            File d = new File("users/" + m.getId());
            File f = new File("users/" + m.getId() + "/mutes.yml");
            if(!d.exists()) {
                d.mkdir();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

            c.set("muted", true);
            c.set("reason", reason);
            c.set("type", "GENERAL");
            c.set("date", System.currentTimeMillis());
            c.set("warner", e.getMember().getId());
            c.set("permanent", true);
            c.save(f);
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    public static void voiceMute(GuildMessageReceivedEvent e, Member m, String reason) {
        try {
            e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Voicemute", true).get(0));
            File d = new File("users/" + m.getId());
            File f = new File("users/" + m.getId() + "/mutes.yml");
            if(!d.exists()) {
                d.mkdir();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

            c.set("muted", true);
            c.set("reason", reason);
            c.set("type", "VOICE");
            c.set("date", System.currentTimeMillis());
            c.set("warner", e.getMember().getId());
            c.set("permanent", true);
            c.save(f);
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    public static void chatMute(GuildMessageReceivedEvent e, Member m, String reason) {
        try {
            e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Chatmute", true).get(0));
            File d = new File("users/" + m.getId());
            File f = new File("users/" + m.getId() + "/mutes.yml");
            if(!d.exists()) {
                d.mkdir();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

            c.set("muted", true);
            c.set("reason", reason);
            c.set("type", "CHAT");
            c.set("date", System.currentTimeMillis());
            c.set("warner", e.getMember().getId());
            c.set("permanent", true);
            c.save(f);
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    public static void tempMute(GuildMessageReceivedEvent e, Member m, String reason, int days) {
        try {
            e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0));
            File d = new File("users/" + m.getId());
            File f = new File("users/" + m.getId() + "/mutes.yml");
            if(!d.exists()) {
                d.mkdir();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

            c.set("muted", true);
            c.set("reason", reason);
            c.set("type", "GENERAL");
            c.set("date", System.currentTimeMillis());
            c.set("warner", e.getMember().getId());
            c.set("permanent", false);
            Date expireDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(expireDate);
            calendar.add(Calendar.DATE, days);
            expireDate = calendar.getTime();
            c.set("expireDate", expireDate.getTime());
            c.save(f);
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    public static void tempVoiceMute(GuildMessageReceivedEvent e, Member m, String reason, int days) {
        try {
            e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0));
            File d = new File("users/" + m.getId());
            File f = new File("users/" + m.getId() + "/mutes.yml");
            if(!d.exists()) {
                d.mkdir();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

            c.set("muted", true);
            c.set("reason", reason);
            c.set("type", "VOICE");
            c.set("date", System.currentTimeMillis());
            c.set("warner", e.getMember().getId());
            c.set("permanent", false);
            Date expireDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(expireDate);
            calendar.add(Calendar.DATE, days);
            expireDate = calendar.getTime();
            c.set("expireDate", expireDate.getTime());
            c.save(f);
        } catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    public static void tempChatMute(GuildMessageReceivedEvent e, Member m, String reason, int days) {
        try {
            e.getGuild().addRoleToMember(m, e.getGuild().getRolesByName("Mute", true).get(0));
            File d = new File("users/" + m.getId());
            File f = new File("users/" + m.getId() + "/mutes.yml");
            if(!d.exists()) {
                d.mkdir();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

            c.set("muted", true);
            c.set("reason", reason);
            c.set("type", "CHAT");
            c.set("date", System.currentTimeMillis());
            c.set("warner", e.getMember().getId());
            c.set("permanent", false);
            Date expireDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(expireDate);
            calendar.add(Calendar.DATE, days);
            expireDate = calendar.getTime();
            c.set("expireDate", expireDate.getTime());
            c.save(f);
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

}
