package de.demokratie.polizeibot.objects;

import net.dv8tion.jda.api.entities.Member;

import java.util.Date;
import java.util.List;

public class Information {

    private Member m;
    private boolean muted;

    private String muteReason;
    private String muteType;
    private boolean isPermanent;
    private Date expireDate;
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

    public String getMuteReason() {
        return muteReason;
    }

    public void setMuteReason(String muteReason) {
        this.muteReason = muteReason;
    }

    public String getMuteType() {
        return muteType;
    }

    public void setMuteType(String muteType) {
        this.muteType = muteType;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public List<Warn> getWarns() {
        return warns;
    }

    public void setWarns(List<Warn> warns) {
        this.warns = warns;
    }
}
