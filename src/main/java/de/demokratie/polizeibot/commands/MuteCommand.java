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
                "Syntax: '" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID> <Typ: general, voice, chat> <Grund>'";
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
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Bitte nutze folgende Syntax: `" + Bot.COMMAND_PREFIX + getInvoke() + "<@Nutzer | Nutzer-ID> <Typ: general, voice, chat> <Grund>`").build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
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

        String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
        Utils.mute(event, target, reason, args[1].toUpperCase());

    }
}
