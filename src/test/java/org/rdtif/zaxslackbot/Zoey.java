package org.rdtif.zaxslackbot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Zoey {
    private static final String GAME_DIRECTORY_PROPERTY_NAME = "game-directory=";
    private static final Path CONFIGURATION_PATH = Paths.get("configuration.properties");

    public static Path createTestConfigurationFile(String gameDirectory) {
        try {
            Path path = Files.createFile(CONFIGURATION_PATH);
            Files.write(path, (GAME_DIRECTORY_PROPERTY_NAME + gameDirectory + "\n").getBytes());
            return path;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
