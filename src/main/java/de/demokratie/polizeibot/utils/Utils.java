package de.demokratie.polizeibot.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Utils {

    public static void warn(Member m, String reason) {
        try {
            File users = new File("users/");
            if (!users.exists())
                users.mkdirs();
            File f = new File("users/" + m.getId() + ".yml");
            if (!f.exists()) {
                f.createNewFile();
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
            if (c.contains(m.getId())) {
                int warns = c.getInt(m.getId() + ".warns");
                c.set(m.getId() + ".warns", warns + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void mute(GuildMessageReceivedEvent e, Member m, String reason) {
    }

    public static void tempmute(GuildMessageReceivedEvent e, Member m, String reason, int days) {
    }

}
