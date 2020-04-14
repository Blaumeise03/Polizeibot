package de.demokratie.polizeibot.listener;

import de.demokratie.polizeibot.objects.Information;
import de.demokratie.polizeibot.utils.Utils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class JoinListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        Member m = event.getMember();
        Guild guild = event.getGuild();
        Information info = Utils.getInformation(m);
        if(info.isMuted()) {
            switch(info.getMute().getType()) {
                case "GENERAL":
                    guild.addRoleToMember(m, guild.getRolesByName("Mute", true).get(0)).queue();
                    break;
                case "VOICE":
                    guild.addRoleToMember(m, guild.getRolesByName("Voicemute", true).get(0)).queue();
                    break;
                case "CHAT":
                    guild.addRoleToMember(m, guild.getRolesByName("Chatmute", true).get(0)).queue();
                    break;
            }
        }
    }
}
