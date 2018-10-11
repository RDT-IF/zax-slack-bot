package org.rdtif.zaxslackbot.interpreter;

import com.zaxsoft.zax.zmachine.ZCPU;
import com.zaxsoft.zax.zmachine.ZUserInterface;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.rdtif.zaxslackbot.GameRepository;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StartGameActionTest {
    private static final String DEFAULT_MESSAGE = RandomStringUtils.randomAlphabetic(13);
    private static final String START_MESSAGE = RandomStringUtils.randomAlphabetic(12);

    private final GameRepository repository = mock(GameRepository.class);
    private final ZCpuFactory zCpuFactory = mock(ZCpuFactory.class);
    private final ZCPU zcpu = mock(ZCPU.class);
    private final StartGameAction startGameAction = new StartGameAction(repository, zCpuFactory, "");
    private final LanguagePattern languagePattern = createPattern();

    @Before
    public void beforeEach() {
        when(zCpuFactory.create(any(ZUserInterface.class))).thenReturn(zcpu);
    }

    @Test
    public void returnDefaultMessageIfGameDoesNotExist() {
        String badGameName = RandomStringUtils.randomAlphabetic(12);
        String message = startGameAction.execute("play " + badGameName, languagePattern);
        assertThat(message, equalTo(DEFAULT_MESSAGE));
    }

    @Test
    public void returnStartMessageWithGameName() {
        String gameName = RandomStringUtils.randomAlphabetic(12);
        String input = "play " + gameName;

        when(repository.fileNames()).thenReturn(Collections.singletonList(gameName));
        String message = startGameAction.execute(input, languagePattern);

        assertThat(message, equalTo(START_MESSAGE + " " + gameName));
    }

    @Test
    public void useFactoryToCreateZCPU() {
        String gameName = RandomStringUtils.randomAlphabetic(12);
        String input = "play " + gameName;

        when(repository.fileNames()).thenReturn(Collections.singletonList(gameName));
        startGameAction.execute(input, languagePattern);

        verify(zCpuFactory).create(any(ZUserInterface.class));
    }

    @Test
    public void initializeZCPU() {
        String gameName = RandomStringUtils.randomAlphabetic(12);
        String input = "play " + gameName;

        when(repository.fileNames()).thenReturn(Collections.singletonList(gameName));
        startGameAction.execute(input, languagePattern);

        verify(zcpu).initialize(anyString());
    }

    @Test
    public void initializeZCPUWithGamePath() {
        String gameDirectory = RandomStringUtils.randomAlphabetic(14);
        StartGameAction startGameAction = new StartGameAction(repository, zCpuFactory, gameDirectory);
        String gameName = RandomStringUtils.randomAlphabetic(12);
        String input = "play " + gameName;

        when(repository.fileNames()).thenReturn(Collections.singletonList(gameName));

        startGameAction.execute(input, languagePattern);

        verify(zcpu).initialize(gameDirectory + gameName);
    }

    @Test
    public void runZCPU() {
        String gameName = RandomStringUtils.randomAlphabetic(12);
        String input = "play " + gameName;

        when(repository.fileNames()).thenReturn(Collections.singletonList(gameName));

        startGameAction.execute(input, languagePattern);

        verify(zcpu).run();
    }

    private LanguagePattern createPattern() {
        LanguagePattern pattern = new LanguagePattern();
        pattern.setPattern("(play|start|open|run) (.*)");
        LanguageResponse response1 = new LanguageResponse();
        response1.setName("default");
        response1.setResponses(Collections.singletonList(DEFAULT_MESSAGE));
        LanguageResponse response2 = new LanguageResponse();
        response2.setName("start");
        response2.setResponses(Collections.singletonList(START_MESSAGE));
        pattern.setResponses(Arrays.asList(response1, response2));
        return pattern;
    }
}