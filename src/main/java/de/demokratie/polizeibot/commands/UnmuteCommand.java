package de.demokratie.polizeibot.commands;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.command.Command;
import de.demokratie.polizeibot.embed.EmbedCreator;
import de.demokratie.polizeibot.utils.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UnmuteCommand implements Command {
    @Override
    public String getHelp() {
        return "Entmutet einen Nutzer\n" +
                "Syntax: '" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID>'";
    }

    @Override
    public String getInvoke() {
        return "unmute";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        if(args.length != 1) {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Bitte nutze folgende Syntax: `" + Bot.COMMAND_PREFIX + getInvoke() + "<@Nutzer | Nutzer-ID>`").build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
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
        event.getGuild().removeRoleFromMember(target, event.getGuild().getRolesByName("Mute", true).get(0)).queue();
        event.getGuild().removeRoleFromMember(target, event.getGuild().getRolesByName("Voicemute", true).get(0)).queue();
        event.getGuild().removeRoleFromMember(target, event.getGuild().getRolesByName("Chatmute", true).get(0)).queue();

        Utils.unmute(target);

        target.getUser().openPrivateChannel().complete().sendMessage(new EmbedCreator(Color.GREEN).setDescription("Du wurdest entmutet").build()).queue();
        Utils.log(Color.GREEN, event.getMember(), target, "wurde entmutet", target.getAsMention() + " wurde entmutet", "Unmute");

    }
}
