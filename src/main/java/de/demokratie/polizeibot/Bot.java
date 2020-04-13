package de.demokratie.polizeibot;

import de.demokratie.polizeibot.command.CommandHandler;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {

    public static final String COMMAND_PREFIX = "!";

    private static Bot bot;

    public JDA jda;
    public CommandHandler commandHandler;

    public Bot() {

        bot = this;

        commandHandler = new CommandHandler();

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken("Njk5MjQyNjk5MDk3ODk5MTE4.XpRitQ.rrEmpeJ7Xufe0bTt9jvwRLiqNEs")
                    .setAutoReconnect(true)
                    .addEventListeners(commandHandler)
                    .build().awaitReady();
        } catch (Exception ex) {
            System.err.println("Bot konnte sich nicht verbinden");
        }

    }

    public static void main(String[] args) {
        new Bot();
    }

    public static Bot getBot() {
        return bot;
    }
}
