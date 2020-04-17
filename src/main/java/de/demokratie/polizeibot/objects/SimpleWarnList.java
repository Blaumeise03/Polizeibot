package de.demokratie.polizeibot.objects;

import java.util.List;

public class SimpleWarnList {
    private List<SimpleWarn> warns;

    public SimpleWarnList() {

    }

    public List<SimpleWarn> getWarns() {
        return warns;
    }

    public void setWarns(List<SimpleWarn> warns) {
        this.warns = warns;
    }
}
