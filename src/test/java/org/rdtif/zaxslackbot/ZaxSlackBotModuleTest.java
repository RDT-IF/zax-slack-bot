package org.rdtif.zaxslackbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class ZaxSlackBotModuleTest {
    private final Injector injector = Guice.createInjector(new ZaxSlackBotModule());

    @BeforeClass
    public static void beforeAll() {
        Zoey.createTestConfigurationFile("");
    }

    @AfterClass
    public static void afterAll() throws IOException {
        Files.delete(Paths.get("configuration.properties"));
    }

    @Test
    public void providesConfiguration() {
        ZaxSlackBotConfiguration configuration = injector.getInstance(ZaxSlackBotConfiguration.class);

        assertThat(configuration, notNullValue());
    }

    @Test
    public void providesSlackConnection() {
        SlackConnection connection = injector.getInstance(SlackConnection.class);

        assertThat(connection, notNullValue());
    }
}
