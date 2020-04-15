package de.demokratie.polizeibot.commands;

import de.demokratie.polizeibot.Bot;
import de.demokratie.polizeibot.command.Command;
import de.demokratie.polizeibot.embed.EmbedCreator;
import de.demokratie.polizeibot.objects.Information;
import de.demokratie.polizeibot.objects.Mute;
import de.demokratie.polizeibot.objects.Warn;
import de.demokratie.polizeibot.utils.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InfoCommand implements Command {

    @Override
    public String getHelp() {
        return "Zeigt Infos über einen User an\n" +
                "Syntax: `" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID> `";
    }

    @Override
    public String getInvoke() {
        return "info";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        deleteLastMessage(event);

        Member m = event.getMember();

        if (!m.getRoles().contains(event.getGuild().getRolesByName("Polizei", true).get(0))) {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Du hast keine Berechtigung, um diesen Befehl auszuführen").build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
            return;
        }

        if (args.length == 1) {
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
            OffsetDateTime joined = event.getMember().getTimeJoined();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

            String dateString = joined.getDayOfMonth() + "." + joined.getMonthValue() + "." + joined.getYear() + " " + joined.getHour() + ":" + joined.getMinute() + ":" + joined.getSecond();
            String infoString = "Gejoint: " + dateString + "\n\n";
            if (info.isMuted()) {

                Mute mute = info.getMute();

                infoString = infoString + "Gemutet: Ja\n" +
                        "Typ: " + mute.getType() + "\n";
                if (mute.isPermanent()) {

                    infoString = infoString + "Ablaufdatum: Keins (Permanent gemutet)\n";

                } else {

                    Date expire = mute.getExpireDate();
                    String expireString = dateFormat.format(expire);
                    infoString = infoString + "Ablaufdatum: " + expireString + "\n";

                }

                Date muteDate = mute.getDate();
                String muteString = dateFormat.format(muteDate);
                Member muter = mute.getMuter();
                infoString = infoString + "Gemutet am " + muteString + "\n" +
                        "Von " + muter.getAsMention() + "\n";
                infoString += "Begründung: " + mute.getReason() + "\n\n";
            }
            else {
                infoString = infoString + "Gemutet: Nein\n\n";
            }
            if(info.getWarns().size() != 0) {

                List<String> warnString = new ArrayList<>();
                warnString.add("Warns: " + info.getWarns().size() + "\n");

                info.getWarns().stream().forEach((warn -> {
                    String s = warnString.get(0);
                    Warn lastWarn = info.getWarns().get(info.getWarns().size() - 1);
                    if (!lastWarn.equals(warn)) {
                        String warnDate = dateFormat.format(warn.getDate());
                        warnString.clear();
                        s = s + "\"" + warn.getReason() + "\":\n" +
                                "-> Verwarnt am " + warnDate + "\n" +
                                "-> Von " + warn.getWarner().getAsMention() + "\n\n";
                        warnString.add(s);
                    } else {
                        String warnDate = dateFormat.format(warn.getDate());
                        warnString.clear();
                        s = s + "\"" + warn.getReason() + "\":\n" +
                                "-> Verwarnt am " + warnDate + "\n" +
                                "-> Von " + warn.getWarner().getAsMention();
                        warnString.add(s);
                    }
                }));
                infoString = infoString + warnString.get(0);
            }
            Message msg = null;

            if(target.getNickname() != null) {
                msg = new EmbedCreator(Color.YELLOW).setTitle("Informationen über " + target.getNickname()).setDescription(infoString).build();
            }
            else {
                msg = new EmbedCreator(Color.YELLOW).setTitle("Informationen über " + target.getEffectiveName()).setDescription(infoString).build();
            }
            event.getChannel().sendMessage(msg).queue();
        }
        else {
            event.getChannel().sendMessage(new EmbedCreator(Color.RED).setDescription("Bitte nutze folgende Syntax: '" + Bot.COMMAND_PREFIX + getInvoke() + " <@Nutzer | Nutzer-ID>'").build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
        }
    }
}
