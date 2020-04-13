package de.demokratie.polizeibot.utils;

import de.demokratie.polizeibot.objects.Warn;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void mute(GuildMessageReceivedEvent e, Member m, String reason) {
    }

    public static void tempmute(GuildMessageReceivedEvent e, Member m, String reason, int days) {
    }

    public static List<Warn> getWarns(Member m, GuildMessageReceivedEvent e) {
        List<Warn> warns = new ArrayList<>();
        File f = new File("users/" + m.getId() + "/warns.yml");
        if(f.exists()) {
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
            List<String> reasons = c.getStringList("reasons");
            reasons.stream().forEach((reason) -> {
                Warn warn = new Warn();
                warn.setDate(new Date());
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
