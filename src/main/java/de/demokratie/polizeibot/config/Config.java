package de.demokratie.polizeibot.config;

import de.demokratie.polizeibot.Bot;

import java.io.*;
import java.util.Properties;

public class Config {

    private File file;
    private Properties properties;

    public Config(String filename) throws IOException {

        file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
            PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            w.println("Token=token");
            w.close();
        }

        properties = new Properties();
        properties.load(new FileInputStream(file));

    }

    public void reload() {
        try {
            Bot.config = new Config(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        return properties.getProperty("Token");
    }

    public void setProperty(String path, String value) {
        properties.setProperty(path, value);
        try {
            properties.store(new FileWriter(file), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String path) {
        return properties.getProperty(path);
    }

}
