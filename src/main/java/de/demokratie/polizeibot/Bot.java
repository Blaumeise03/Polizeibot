package de.demokratie.polizeibot;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {

    public JDA jda;

    public Bot() {

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken("Njk5MjQyNjk5MDk3ODk5MTE4.XpRitQ.rrEmpeJ7Xufe0bTt9jvwRLiqNEs")
                    .setAutoReconnect(true)
                    .build().awaitReady();
        } catch (Exception ex) {
            System.err.println("Bot konnte sich nicht verbinden");
        }

    }

    public static void main(String[] args) {
        new Bot();
    }

}
