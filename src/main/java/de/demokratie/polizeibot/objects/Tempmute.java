package de.demokratie.polizeibot.objects;

import net.dv8tion.jda.api.entities.Member;

import java.util.Date;

public class Tempmute {

    String reason;
    String type;
    Member m;
    Member warner;
    Date d;
    Date expireDate;

    public Tempmute(String reason, Member m, Member warner, Date d, Date expireDate) {
        this.reason = reason;
        this.m = m;
        this.warner = warner;
        this.d = d;
        this.expireDate = expireDate;
    }

    public Date getDate() {
        return d;
    }

    public String getType() {
        return type;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getExpireDate() {
        return expireDate;
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
