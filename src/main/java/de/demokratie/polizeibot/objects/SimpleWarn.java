package de.demokratie.polizeibot.objects;

public class SimpleWarn {
    private String reason;
    private String warner;
    private long date;

    public SimpleWarn() {
    }

    public String getReason() {
        return reason;
    }

    public String getWarner() {
        return warner;
    }

    public long getDate() {
        return date;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setWarner(String warner) {
        this.warner = warner;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
