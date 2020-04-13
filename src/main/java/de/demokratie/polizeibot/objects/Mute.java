package de.demokratie.polizeibot.objects;

import net.dv8tion.jda.api.entities.Member;

import java.util.Date;

public class Mute {

    String reason;
    String type;
    Member m;
    Member warner;
    Date d;

    public Mute(String reason, Member m, Member warner, Date d) {
        this.reason = reason;
        this.m = m;
        this.warner = warner;
        this.d = d;
    }

    public Date getDate() {
        return d;
    }

    public Member getMember() {
        return m;
    }

    public Member getWarner() {
        return warner;
    }

    public String getReason() {
        return reason;
    }

    public void setDate(Date d) {
        this.d = d;
    }

    public void setMember(Member m) {
        this.m = m;
    }

    public void setWarner(Member warner) {
        this.warner = warner;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
