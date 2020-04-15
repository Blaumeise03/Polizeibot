package de.demokratie.polizeibot;

import de.demokratie.polizeibot.command.CommandHandler;
import de.demokratie.polizeibot.commands.MuteCommand;
import de.demokratie.polizeibot.commands.TempMuteCommand;
import de.demokratie.polizeibot.commands.WarnCommand;
import de.demokratie.polizeibot.listener.JoinListener;
import de.demokratie.polizeibot.objects.Mute;
import de.demokratie.polizeibot.utils.Utils;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.time.Instant;
import java.util.Date;

public class Bot {

    public static final String COMMAND_PREFIX = "!";

    private static Bot bot;

    public JDA jda;
    public CommandHandler commandHandler;

    public Bot() {

        bot = this;

        commandHandler = new CommandHandler();
        commandHandler.addCommand(new WarnCommand());
        commandHandler.addCommand(new TempMuteCommand());
        commandHandler.addCommand(new MuteCommand());

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken("Njk5MjQyNjk5MDk3ODk5MTE4.XpRitQ.rrEmpeJ7Xufe0bTt9jvwRLiqNEs")
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
