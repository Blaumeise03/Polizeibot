package de.demokratie.polizeibot.utils;

public enum MuteType {
    GENERAL(new String[]{
            "general",
            "g"
    }), CHAT(new String[]{
            "chat",
            "c"
    }), VOICE(new String[]{
            "voice",
            "v"
    });

    String[] names;

    MuteType(String[] names) {
        this.names = names;
    }

    public static MuteType getType(String s) {
        s = s.toLowerCase().trim();
        for(MuteType type : MuteType.values()) {
            for(String s1 : type.names) {
                if(s1.equalsIgnoreCase(s)) return type;
            }
        }
        return null;
    }

    public String getName() {
        if(names.length < 1) return this.name().toLowerCase();
        return names[0];
    }
}
