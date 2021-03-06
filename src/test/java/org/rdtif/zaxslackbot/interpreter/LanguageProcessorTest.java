package org.rdtif.zaxslackbot.interpreter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

class LanguageProcessorTest {
    private static final String TEST_ACTION_RESPONSE = RandomStringUtils.randomAlphanumeric(12);
    private static final Map<LanguageAction, Action> actionMap = ImmutableMap.<LanguageAction, Action>builder()
            .put(LanguageAction.ListGames, new TestAction())
            .build();
    private final LanguageProcessor languageProcessor = new LanguageProcessor(actionMap);

    @Test
    void returnDefaultResponseWhenNoRegisteredPatterns() {
        assertThat(languageProcessor.responseTo(RandomStringUtils.randomAlphabetic(10)), equalTo(LanguagePattern.DEFAULT_MESSAGE));
    }

    @Test
    void returnDefaultResponseWhenInputIsUnknown() {
        languageProcessor.registerPattern(createLanguagePattern("/doesnt/g", "matter"));
        assertThat(languageProcessor.responseTo(RandomStringUtils.randomAlphabetic(10)), equalTo(LanguagePattern.DEFAULT_MESSAGE));
    }

    @Test
    void doNotRegisterNullPattern() {
        languageProcessor.registerPattern(null);
        assertThat(languageProcessor.responseTo(RandomStringUtils.randomAlphabetic(10)), equalTo(LanguagePattern.DEFAULT_MESSAGE));
    }

    @Test
    void doNotRegisterLanguagePatternWhenNoPatternPresent() {
        languageProcessor.registerPattern(new LanguagePattern());
        assertThat(languageProcessor.responseTo(RandomStringUtils.randomAlphabetic(10)), equalTo(LanguagePattern.DEFAULT_MESSAGE));
    }

    @Test
    void caseInsensitiveMatching() {
        String input = RandomStringUtils.randomAlphabetic(10);
        String response = RandomStringUtils.randomAlphabetic(8);
        languageProcessor.registerPattern(createLanguagePattern(input.toUpperCase(), response));

        assertThat(languageProcessor.responseTo(input.toLowerCase()), equalTo(response));
    }

    @Test
    void returnResponseWhenInputMatchesPattern() {
        String input = RandomStringUtils.randomAlphabetic(10);
        String response = RandomStringUtils.randomAlphabetic(8);
        languageProcessor.registerPattern(createLanguagePattern(input, response));

        assertThat(languageProcessor.responseTo(input), equalTo(response));
    }

    @Test
    void returnRandomResponseFromMatchingPattern() {
        String input = RandomStringUtils.randomAlphabetic(10);
        String response = RandomStringUtils.randomAlphabetic(8);
        String response2 = RandomStringUtils.randomAlphabetic(9);
        String response3 = RandomStringUtils.randomAlphabetic(7);
        languageProcessor.registerPattern(createLanguagePattern(input, response, response2, response3));

        boolean matchedResponse1 = false;
        boolean matchedResponse2 = false;
        boolean matchedResponse3 = false;

        for (int i = 0; i < 100;++i) {
            String result = languageProcessor.responseTo(input);

            if (!matchedResponse1 && Objects.equals(result, response)) {
                matchedResponse1 = true;
            } else if (!matchedResponse2 && Objects.equals(result, response2)) {
                matchedResponse2 = true;
            } else if (!matchedResponse3 && Objects.equals(result, response3)) {
                matchedResponse3 = true;
            }

            if (matchedResponse1 && matchedResponse2 && matchedResponse3) {
                break;
            }
        }

        assertThat(matchedResponse1 && matchedResponse2 && matchedResponse3, equalTo(true));
    }

    @Test
    void matchCorrectLanguagePattern() {
        String input = RandomStringUtils.randomAlphabetic(10);
        String response = RandomStringUtils.randomAlphabetic(8);
        languageProcessor.registerPattern(createLanguagePattern(RandomStringUtils.randomAlphanumeric(12), ""));
        languageProcessor.registerPattern(createLanguagePattern(input, response));

        assertThat(languageProcessor.responseTo(input), equalTo(response));
    }

    @Test
    void returnDefaultResponseIfPatternHasNoResponses() {
        String input = RandomStringUtils.randomAlphabetic(10);
        LanguagePattern languagePattern = new LanguagePattern();
        languagePattern.setPattern(input);
        languageProcessor.registerPattern(languagePattern);

        assertThat(languageProcessor.responseTo(input), equalTo(LanguagePattern.DEFAULT_MESSAGE));
    }

    @Test
    void useSpecifiedAction() {
        String input = RandomStringUtils.randomAlphabetic(10);
        LanguagePattern languagePattern = new LanguagePattern();
        languagePattern.setPattern(input);
        languagePattern.setAction(LanguageAction.ListGames);
        languageProcessor.registerPattern(languagePattern);

        assertThat(languageProcessor.responseTo(input), equalTo(TEST_ACTION_RESPONSE));
    }

    private LanguagePattern createLanguagePattern(String pattern, String... response) {
        LanguagePattern languagePattern = new LanguagePattern();
        languagePattern.setPattern(pattern);

        LanguageResponse languageResponse = new LanguageResponse();
        languageResponse.setName("default");
        languageResponse.setResponses(Arrays.asList(response));
        languagePattern.setResponses(Collections.singletonList(languageResponse));

        return languagePattern;
    }

    private static class TestAction implements Action {
        @Override
        public String execute(String input, LanguagePattern pattern) {
            return TEST_ACTION_RESPONSE;
        }
    }
}