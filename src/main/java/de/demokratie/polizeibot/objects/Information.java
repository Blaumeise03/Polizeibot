package de.demokratie.polizeibot.objects;

import net.dv8tion.jda.api.entities.Member;

import java.util.Date;
import java.util.List;

public class Information {

    Member m;
    boolean muted;



    String muteReason;
    String muteType;
    boolean isPermanent;
    Date expireDate;
    List<Warn> warns;

    public Information(Member m) {
        this.m = m;
    }
    public void setMember(Member m) {
        this.m = m;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void setMuteReason(String muteReason) {
        this.muteReason = muteReason;
    }

    public void setMuteType(String muteType) {
        this.muteType = muteType;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setWarns(List<Warn> warns) {
        this.warns = warns;
    }

    public boolean isMuted() {
        return muted;
    }

    public Member getMember() {
        return m;
    }

    public String getMuteReason() {
        return muteReason;
    }

    public String getMuteType() {
        return muteType;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public List<Warn> getWarns() {
        return warns;
    }
}
