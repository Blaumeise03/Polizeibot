package de.demokratie.polizeibot.command;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Command {

    String getHelp();

    String getInvoke();

    void execute(GuildMessageReceivedEvent event, String[] args);

}
