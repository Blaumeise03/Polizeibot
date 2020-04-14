package de.demokratie.polizeibot.commands;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.command.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class TempMuteCommand implements Command {
    @Override
    public String getHelp() {
        return "Mutet einen Nutzer temporär\n" +
                "Syntax: '" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID> <Type: general, voice, chat> <Zeit in Tagen> <Grund>'";
    }

    @Override
    public String getInvoke() {
        return "tempmute";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

    }
}
