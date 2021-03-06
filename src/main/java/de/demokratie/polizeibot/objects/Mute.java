package de.demokratie.polizeibot.objects;

import de.demokratie.polizeibot.utils.MuteType;
import net.dv8tion.jda.api.entities.Member;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.rmi.UnexpectedException;
import java.util.Date;
import java.util.Map;

public class Mute {

    private boolean muted;

    private String reason;
    private MuteType type;
    private Member m;
    private Member muter;
    private Date d;
    private Date expireDate;
    private boolean permanent;

    public MuteType getType() {
        return type;
    }

    public void setType(MuteType type) {
        this.type = type;
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
    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
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
