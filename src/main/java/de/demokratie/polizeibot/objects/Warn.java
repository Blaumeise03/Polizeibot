package de.demokratie.polizeibot.objects;

import net.dv8tion.jda.api.entities.Member;

import java.util.Date;

public class Warn {

    private String reason;
    private Member warner;
    private Date d;

    public Warn(String reason, Member warner, Date d) {
        this.reason = reason;
        this.warner = warner;
        this.d = d;
    }

    public Warn() {
    }

    public Date getDate() {
        return d;
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

    public void setWarner(Member warner) {
        this.warner = warner;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
