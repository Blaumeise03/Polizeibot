package de.demokratie.polizeibot.objects;

import net.dv8tion.jda.api.entities.Member;

import java.util.Date;

public class Mute {

    private String reason;
    private String type;
    private Member m;
    private Member muter;
    private Date d;
    private Date expireDate;
    private boolean permanent;



    public Mute(String reason, Member m, Member muter, Date d) {
        this.reason = reason;
        this.m = m;
        this.muter = muter;
        this.d = d;
    }

    public Mute() {
    }

    public Date getDate() {
        return d;
    }

    public Member getMember() {
        return m;
    }

    public Member getMuter() {
        return muter;
    }

    public String getReason() {
        return reason;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public boolean isPermanent() {
        return permanent;
    }
    public void setDate(Date d) {
        this.d = d;
    }

    public void setMember(Member m) {
        this.m = m;
    }

    public void setMuter(Member muter) {
        this.muter = muter;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
