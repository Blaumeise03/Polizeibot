package de.demokratie.polizeibot.commands;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.command.Command;
import de.demokratie.polizeibot.embed.EmbedCreator;
import de.demokratie.polizeibot.utils.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MuteCommand implements Command {
    @Override
    public String getHelp() {
        return "Mutet einen Nutzer permanent\n" +
                "Syntax: `" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID> <Typ: general, voice, chat> <Grund>`";
    }

    @Override
    public String getInvoke() {
        return "mute";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        deleteLastMessage(event);

        Member member = event.getMember();

        if (!member.getRoles().contains(event.getGuild().getRolesByName("Polizei", true).get(0))) {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Du hast keine Berechtigung, um diesen Befehl auszuführen").build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
            return;
        }

        if (args.length < 3) {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Bitte nutze folgende Syntax: `" + Bot.COMMAND_PREFIX + getInvoke() + "<@Nutzer | Nutzer-ID> <Typ: general, voice, chat> <Grund>`").build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
            return;
        }
        if(!args[1].equalsIgnoreCase("general") && !args[1].equalsIgnoreCase("voice") && !args[1].equalsIgnoreCase("chat")) {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Bitte nutze folgende Syntax: `" + Bot.COMMAND_PREFIX + getInvoke() + "<@Nutzer | Nutzer-ID> <Typ: general, voice, chat> <Grund>`").build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
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
        } else {
            target = mentioned.get(0);
        }
        String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
        Utils.mute(event, target, reason, args[1].toUpperCase());

        target.getUser().openPrivateChannel().complete().sendMessage(
                new EmbedCreator(Color.RED)
                        .setDescription("Du wurdest permanent gemutet!")
                        .addField("", "**__Grund:__**\n" + reason)
                        .build()
        ).queue();
        Utils.log(Color.RED, member, target, "hat gegen Regeln verstoßen, führt zu permanentem Mute", target.getAsMention() + " wurde permanent gemutet\nTyp: " + args[1].toUpperCase(), "Regelverstoß", reason);

    }
}
