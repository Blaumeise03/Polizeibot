package de.demokratie.polizeibot.commands;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.command.Command;
import de.demokratie.polizeibot.embed.EmbedCreator;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WarnCommand implements Command {

    @Override
    public String getHelp() {
        return "Verwarnt einen Nutzer\n" +
                "Syntax: `" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID> <Grund>`";
    }

    @Override
    public String getInvoke() {
        return "warn";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        deleteLastMessage(event);

        Member member = event.getMember();

        if (args.length < 2) {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Du hast keine Berechtigung, um diesen Befehl auszuführen").build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
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

        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        target.getUser().openPrivateChannel().complete().sendMessage("Verwarnung: " + reason).queue();

    }
}