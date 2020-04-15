package de.demokratie.polizeibot.commands;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.command.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class UnmuteCommand implements Command {
    @Override
    public String getHelp() {
        return "Entmutet einen Nutzer\n" +
                "Syntax: '" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID>'";
    }

    @Override
    public String getInvoke() {
        return null;
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

    }
}
