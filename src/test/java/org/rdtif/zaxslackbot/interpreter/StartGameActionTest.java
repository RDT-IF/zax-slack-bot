package org.rdtif.zaxslackbot.interpreter;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.rdtif.zaxslackbot.GameFileRepository;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StartGameActionTest {
    private static final String DEFAULT_MESSAGE = RandomStringUtils.randomAlphabetic(13);
    private static final String START_MESSAGE = RandomStringUtils.randomAlphabetic(12);

    private final GameFileRepository repository = mock(GameFileRepository.class);
    private final StartGameAction startGameAction = new StartGameAction(repository);
    private final LanguagePattern languagePattern = createPattern();

    @Test
    void returnDefaultMessageIfGameDoesNotExist() {
        String badGameName = RandomStringUtils.randomAlphabetic(12);
        String message = startGameAction.execute("play " + badGameName, languagePattern);
        assertThat(message, equalTo(DEFAULT_MESSAGE));
    }

    @Test
    void returnStartMessageWithGameName() {
        String gameName = RandomStringUtils.randomAlphabetic(12);
        String input = "play " + gameName;

        when(repository.fileNames()).thenReturn(Collections.singletonList(gameName));
        String message = startGameAction.execute(input, languagePattern);

        assertThat(message, equalTo(START_MESSAGE + " " + gameName));
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