package de.demokratie.polizeibot.commands;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.command.Command;
import de.demokratie.polizeibot.command.CommandHandler;
import de.demokratie.polizeibot.embed.EmbedCreator;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class HelpCommand implements Command {

    private CommandHandler handler;

    public HelpCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public String getHelp() {
        return "Zeigt eine Liste mit allen Commands\n" +
                "Syntax: `" + Bot.COMMAND_PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "help";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        deleteLastMessage(event);

        Member member = event.getMember();

        if (!member.getRoles().contains(event.getGuild().getRolesByName("Polizei", true).get(0))) {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Du hast keine Berechtigung, um diesen Befehl auszuführen").build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
            return;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("tempmute")) {
            Command command = handler.getCommand("tempmute");
            EmbedCreator embedCreator = new EmbedCreator("Hilfe für den TempMute-Command", Color.CYAN);
            embedCreator.setDescription(command.getHelp());
            embedCreator.addField("Erweiterte Informationen", "Die Zeit als Zahl in Tagen kann auch als Datum der Form ```\n" +
                    "dd.MM.yyyy-HH:mm:ss oder\n" +
                    "dd.MM.yyyy``` angegeben werden.");
            embedCreator.addField("Legende", "dd = Tag (z.B. 01)\nMM = Monat (z.B. 12)\nyyyy = Jahr (z.B. 2004)\nHH = Stunde (z.B. 13)\nmm = Minute (z.B. 59)\nss = Sekunde (z.B. 25)");
            embedCreator.addField("Beispiel 1", "`" + Bot.COMMAND_PREFIX + command.getInvoke() + " @Dummy general 15 Test mute`");
            embedCreator.addField("Beispiel 2", "`" + Bot.COMMAND_PREFIX + command.getInvoke() + " @Dummy general 15.04.2020-13:30:35 Test mute`");

            event.getChannel().sendMessage(embedCreator.build()).queue();

            return;
        }

        EmbedCreator embedCreator = new EmbedCreator("Alle Bot-Befehle", Color.CYAN);

        for (Command command : handler.getCommands()) {

            embedCreator.addField("", "**__" + command.getInvoke() + ":__**\n" + command.getHelp());
        }

        embedCreator.setTimestamp(Instant.now());
        embedCreator.setFooter("Demokratieserver Polizeibot | Gemacht von @ce_phox#1259 und @Janis btw.#4242");

        event.getChannel().sendMessage(embedCreator.build()).queue();

    }
}
