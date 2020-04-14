package de.demokratie.polizeibot.objects;

import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class Information {

    private Member m;
    private boolean muted;
    private Mute mute;
    private List<Warn> warns;

    public Information(Member m) {
        this.m = m;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public Member getMember() {
        return m;
    }

    public void setMember(Member m) {
        this.m = m;
    }

    public Mute getMute() {
        return mute;
    }

    public void setMute(Mute mute) {
        this.mute = mute;
    }

    public List<Warn> getWarns() {
        return warns;
    }

    public void setWarns(List<Warn> warns) {
        this.warns = warns;
    }
}
