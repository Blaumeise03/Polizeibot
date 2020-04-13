package de.demokratie.polizeibot.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Utils {

    public static File warnsFile = new File("warns.yml");
    public static File mutesFile = new File("mutes.yml");

    public static YamlConfiguration warns = YamlConfiguration.loadConfiguration(warnsFile);
    public static YamlConfiguration mutes = YamlConfiguration.loadConfiguration(mutesFile);

    public static void warn(Member m, String reason) {

    }

    public static void mute(GuildMessageReceivedEvent e, Member m, String reason) {

    }

    public static void tempmute(GuildMessageReceivedEvent e, Member m, String reason, int days) {

    }

}
