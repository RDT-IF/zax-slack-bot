package org.rdtif.zaxslackbot;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ZaxSlackBotConfigurationTest {
    @Test
    public void getApiTokenFromProperties() {
        String apiToken = RandomStringUtils.randomAlphabetic(13);
        Properties properties = new Properties();
        properties.setProperty("api-token", apiToken);

        ZaxSlackBotConfiguration configuration = new ZaxSlackBotConfiguration(properties);

        assertThat(configuration.getApiToken(), equalTo(apiToken));
    }

    @Test
    public void getGameDirectoryFromPropertiesTrailingSlashPresent() {
        String gameDirectory = RandomStringUtils.randomAlphabetic(13) + "/";
        Properties properties = new Properties();
        properties.setProperty("game-directory", gameDirectory);

        ZaxSlackBotConfiguration configuration = new ZaxSlackBotConfiguration(properties);

        assertThat(configuration.getGameDirectory(), equalTo(gameDirectory));
    }

    @Test
    public void getGameDirectoryFromPropertiesWhenTrailingSlashNotPresent() {
        String gameDirectory = RandomStringUtils.randomAlphabetic(13);
        Properties properties = new Properties();
        properties.setProperty("game-directory", gameDirectory);

        ZaxSlackBotConfiguration configuration = new ZaxSlackBotConfiguration(properties);

        assertThat(configuration.getGameDirectory(), equalTo(gameDirectory + "/"));
    }
}