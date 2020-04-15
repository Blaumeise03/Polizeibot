package de.demokratie.polizeibot.commands;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.command.Command;
import de.demokratie.polizeibot.embed.EmbedCreator;
import de.demokratie.polizeibot.utils.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TempMuteCommand implements Command {
    @Override
    public String getHelp() {
        return "Mutet einen Nutzer temporär\n" +
                "Syntax: '" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID> <Typ: general, voice, chat> <Zeit in Tagen> <Grund>'";
    }

    @Override
    public String getInvoke() {
        return "tempmute";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        deleteLastMessage(event);

        Member member = event.getMember();

        if (!member.getRoles().contains(event.getGuild().getRolesByName("Polizei", true).get(0))) {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Du hast keine Berechtigung, um diesen Befehl auszuführen").build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
            return;
        }

        if (args.length < 4) {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Bitte nutze folgende Syntax: `" + Bot.COMMAND_PREFIX + getInvoke() + "<@Nutzer | Nutzer-ID> <Typ: general, voice, chat> <Zeit in Tagen> <Grund>`").build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
            return;
        }

        if(!args[1].equalsIgnoreCase("general") && !args[1].equalsIgnoreCase("voice") && !args[1].equalsIgnoreCase("voice")) {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Bitte nutze folgende Syntax: `" + Bot.COMMAND_PREFIX + getInvoke() + "<@Nutzer | Nutzer-ID> <Typ: general, voice, chat> <Zeit in Tagen> <Grund>`").build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
            return;
        }

        Member target = null;
        List<Member> mentioned = event.getMessage().getMentionedMembers();
        if (mentioned.isEmpty()) {
            try {
                target = event.getGuild().getMemberById(args[0]);
                if (target == null) {
                    event.getChannel().sendMessage(new EmbedCreator("Diese ID nicht vorhanden!", Color.RED).build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
                    return;
                }
            } catch (Exception ex) {
                event.getChannel().sendMessage(new EmbedCreator("Diese ID nicht vorhanden!", Color.RED).build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
                return;
            }
        } else
            target = mentioned.get(0);

        String reason = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
        int days = 0;
        try {
            days = Integer.parseInt(args[2]);
        } catch (Exception ex) {
            Date date = Utils.tempMute(event, target, reason, args[2], args[1].toUpperCase());
            if (date != null) {

                target.getUser().openPrivateChannel().complete().sendMessage(
                        new EmbedCreator(Color.RED)
                                .setDescription("Du wurdest temporär gemutet!")
                                .addField("", "**__Grund:__**\n" + reason)
                                .addField("", "**__Auslaufdatum__**\n" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date))
                                .build()
                ).queue();

                Utils.log(Color.RED, member, target, "hat gegen Regeln verstoßen, führt zu temporärem Mute", target.getAsMention() + " wurde bis " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date) + " temporär gemutet", "Regelverstoß", reason);

                return;
            }

            event.getChannel().sendMessage(new EmbedCreator(Color.RED)
                    .setDescription("Bitte gib das 3. Argument als Zahl in Tagen oder Datum mit folgender Syntax an:```\n" +
                            "dd.MM.yyyy-HH:mm:ss oder\n" +
                            "dd.MM.yyyy```").build()).queue();
            return;
        }

        Date date = Utils.tempMute(event, target, reason, days, args[1].toUpperCase());

        target.getUser().openPrivateChannel().complete().sendMessage(
                new EmbedCreator(Color.RED)
                        .setDescription("Du wurdest temporär gemutet!")
                        .addField("", "**__Grund:__**\n" + reason)
                        .addField("", "**__Auslaufdatum__**\n" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date))
                        .build()
        ).queue();

        Utils.log(Color.RED, member, target, "hat gegen Regeln verstoßen, führt zu temporärem Mute", target.getAsMention() + " wurde bis " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date) + " temporär gemutet\nTyp: " + args[1].toUpperCase(), "Regelverstoß", reason);

    }
}
