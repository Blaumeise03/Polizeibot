package de.demokratie.polizeibot.command;

import de.demokratie.polizeibot.utils.MessageUtils;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Command {

    String getHelp();

    String getInvoke();

    void execute(GuildMessageReceivedEvent event, String[] args);

    default void deleteLastMessage(GuildMessageReceivedEvent event) {
        MessageUtils.deleteLastMessage(event.getChannel());
    }

}
