package org.rdtif.zaxslackbot;

import java.util.Properties;

public class ZaxSlackBotConfiguration {
    private final Properties properties;

    public ZaxSlackBotConfiguration(Properties properties) {
        this.properties = properties;
    }

    public String getApiToken() {
        return properties.getProperty("api-token");
    }

    public String getGameDirectory() {
        String gameDirectory = properties.getProperty("game-directory");

        return gameDirectory.endsWith("/") ? gameDirectory : gameDirectory + "/";

    }
}
