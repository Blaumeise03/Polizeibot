package de.demokratie.polizeibot;

import de.demokratie.polizeibot.command.CommandHandler;
import de.demokratie.polizeibot.commands.*;
import de.demokratie.polizeibot.config.Config;
import de.demokratie.polizeibot.listener.JoinListener;
import de.demokratie.polizeibot.objects.Mute;
import de.demokratie.polizeibot.utils.Utils;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class Bot {

    public static final String COMMAND_PREFIX = "!";

    private static Bot bot;

    public JDA jda;
    public CommandHandler commandHandler;

    public static Config config;

    public Bot() {

        bot = this;

        try {
            config = new Config("config.properties");
        } catch (IOException e) {
        }

        String token = config.getToken();

        while (token.equalsIgnoreCase("token")) {
            System.out.println("Waiting for entering the token in config.properties...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            token = config.getToken();
            config.reload();
        }

        commandHandler = new CommandHandler();
        commandHandler.addCommand(new WarnCommand());
        commandHandler.addCommand(new TempMuteCommand());
        commandHandler.addCommand(new MuteCommand());
        commandHandler.addCommand(new InfoCommand());
        commandHandler.addCommand(new UnmuteCommand());
        commandHandler.addCommand(new HelpCommand(commandHandler));

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .setAutoReconnect(true)
                    .addEventListeners(commandHandler)
                    .build().awaitReady();
        } catch (Exception ex) {
            System.err.println("Bot konnte sich nicht verbinden");
        }

        new Thread(() -> {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

            while (true) {

                for (Guild guild : jda.getGuilds()) {

                    for (Mute mute : Utils.getMutes()) {

                        if (mute.isPermanent())
                            continue;

                        Date expiration = mute.getExpireDate();
                        Date now = Date.from(Instant.now());

                        if (now.after(expiration)) {

                            guild.removeRoleFromMember(mute.getMember(), guild.getRolesByName("Mute", true).get(0)).queue();
                            guild.removeRoleFromMember(mute.getMember(), guild.getRolesByName("Voicemute", true).get(0)).queue();
                            guild.removeRoleFromMember(mute.getMember(), guild.getRolesByName("Chatmute", true).get(0)).queue();
                            Utils.unmute(mute.getMember());
                        }
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }).start();
        jda.addEventListener(new JoinListener());
    }

    public static void main(String[] args) {
        new Bot();
    }

    public static Bot getBot() {
        return bot;
    }
}
