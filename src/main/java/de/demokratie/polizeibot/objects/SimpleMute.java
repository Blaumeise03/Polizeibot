package de.demokratie.polizeibot.objects;

public class SimpleMute {
    private boolean muted;

    private String reason;
    private String type;
    private String member;
    private String muter;
    private long date;
    private long expireDate;
    private boolean permanent;

    public boolean getMuted() {
        return muted;
    }

    public String getReason() {
        return reason;
    }

    public String getType() {
        return type;
    }

    public String getMember() {
        return member;
    }

    public String getMuter() {
        return muter;
    }

    public long getDate() {
        return date;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public boolean getPermanent() {
        return permanent;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public void setMuter(String muter) {
        this.muter = muter;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }
}
