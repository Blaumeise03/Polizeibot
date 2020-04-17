package de.demokratie.polizeibot.utils;

import de.demokratie.polizeibot.objects.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    private ConfigUtils() {
    }

    public static void serialize(@NotNull Object o, @NotNull String path) throws IOException {
        Yaml yaml = new Yaml();
        File file = new File(path);
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        yaml.dump(o, new FileWriter(file));
    }


    public static @Nullable Object deserialize(@NotNull String path) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        File file = new File(path);
        if(!file.exists()) return null;
        InputStream inputStream = new FileInputStream(file);
        return yaml.load(inputStream);
    }
}
