package de.demokratie.polizeibot.commands;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.command.Command;
import de.demokratie.polizeibot.embed.EmbedCreator;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class WarnCommand implements Command {

    @Override
    public String getHelp() {
        return "Verwarnt einen Nutzer\n" +
                "Syntax: `" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID> <Grund>`";
    }

    @Override
    public String getInvoke() {
        return "warn";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        deleteLastMessage(event);

        Member member = event.getMember();

        if (args.length < 2) {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Du hast keine Berechtigung, um diesen Befehl auszuführen").build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
            return;
        }

    }
}
