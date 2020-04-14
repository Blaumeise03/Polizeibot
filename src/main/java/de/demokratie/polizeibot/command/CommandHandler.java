package de.demokratie.polizeibot.command;

import de.demokratie.polizeibot.Bot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Pattern;

public class CommandHandler extends ListenerAdapter {

    private HashMap<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<>();
    }

    public void handle(GuildMessageReceivedEvent event) {

        // Splitting the message by spaces and removing the command prefix
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Bot.COMMAND_PREFIX), "")
                .split(" ");

        // Getting the invoke
        String invoke = split[0].toLowerCase();

        // Executing command
        if (commands.containsKey(invoke)) {
            String[] args = Arrays.copyOfRange(split, 1, split.length);

            commands.get(invoke).execute(event, args);

        }

    }

    public void addCommand(Command command) {
        if (!commands.containsKey(command.getInvoke()))
            commands.put(command.getInvoke(), command);
    }

    public Command getCommand(String invoke) {
        if (commands.containsKey(invoke))
            return commands.get(invoke);
        return null;
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().startsWith(Bot.COMMAND_PREFIX) && !event.getAuthor().isBot() && !event.isWebhookMessage())
            return;

        if (!event.getAuthor().isBot() && !event.isWebhookMessage())
            handle(event);

    }

}
