package org.rdtif.zaxslackbot.interpreter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.rdtif.zaxslackbot.GameRepository;

public class ListGamesActionTest {
    private static final String GAMES_MESSAGE = RandomStringUtils.randomAlphabetic(13);
    private static final String DEFAULT_MESSAGE = RandomStringUtils.randomAlphabetic(13);
    private final GameRepository repository = mock(GameRepository.class);
    private final ListGamesAction action = new ListGamesAction(repository);

    @Test
    public void neverReturnNull() {
        when(repository.fileNames()).thenReturn(Collections.emptyList());
        String response = action.execute("", createPattern(), null, null);

        assertThat(response, notNullValue());
    }

    @Test
    public void returnFileListNone() {
        when(repository.fileNames()).thenReturn(Collections.emptyList());
        String response = action.execute("", createPattern(), null, null);

        assertThat(response, equalTo(DEFAULT_MESSAGE));
    }

    @Test
    public void returnFileListOne() {
        when(repository.fileNames()).thenReturn(Collections.singletonList("file1"));
        String response = action.execute("", createPattern(), null, null);

        assertThat(response, equalTo(GAMES_MESSAGE + "file1."));
    }

    @Test
    public void returnFileListTwo() {
        when(repository.fileNames()).thenReturn(Arrays.asList("file1", "file2"));
        String response = action.execute("", createPattern(), null, null);

        assertThat(response, equalTo(GAMES_MESSAGE + "file1 and file2."));
    }

    @Test
    public void returnFileListThreeOrMore() {
        when(repository.fileNames()).thenReturn(Arrays.asList("file1", "file2", "file3"));
        String response = action.execute("", createPattern(), null, null);

        assertThat(response, equalTo(GAMES_MESSAGE + "file1, file2, and file3."));
    }

    private LanguagePattern createPattern() {
        LanguagePattern pattern = new LanguagePattern();
        LanguageResponse response1 = new LanguageResponse();
        response1.setName("default");
        response1.setResponses(Collections.singletonList(DEFAULT_MESSAGE));
        LanguageResponse response2 = new LanguageResponse();
        response2.setName("games");
        response2.setResponses(Collections.singletonList(GAMES_MESSAGE));
        pattern.setResponses(Arrays.asList(response1, response2));
        return pattern;
    }
}
