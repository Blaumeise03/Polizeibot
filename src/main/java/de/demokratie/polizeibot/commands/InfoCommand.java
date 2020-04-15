package de.demokratie.polizeibot.commands;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.command.Command;
import de.demokratie.polizeibot.embed.EmbedCreator;
import de.demokratie.polizeibot.objects.Information;
import de.demokratie.polizeibot.utils.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InfoCommand implements Command {

    @Override
    public String getHelp() {
        return "Zeigt Infos über einen User an\n" +
                "Syntax: '" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID> <Typ: general, voice, chat> <Zeit in Tagen> <Grund>'";
    }

    @Override
    public String getInvoke() {
        return "info";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        Member m = event.getMember();
        if(args.length == 1) {
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
            Information info = Utils.getInformation(target);
        }
        else {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Bitte nutze folgende Syntax: '" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID>'").build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
        }
    }
}
