package org.rdtif.zaxslackbot.interpreter;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class LanguageProcessor {
    private final Map<LanguageAction, Action> actionMap;
    private List<LanguagePattern> languagePatterns = new ArrayList<>();

    @Inject
    public LanguageProcessor(Map<LanguageAction, Action> actionMap) {
        this.actionMap = actionMap;
    }

    public String responseTo(String input, SlackSession session, SlackChannel channel) {
        for (LanguagePattern pattern : languagePatterns) {
            if (input.toLowerCase().matches(pattern.getPattern().toLowerCase())) {
                Action action = actionMap.get(pattern.getAction());
                if (action == null) {
                    return pattern.responseForFirst();
                } else {
                    return action.execute(input, pattern, session, channel);
                }
            }
        }

        return LanguagePattern.DEFAULT_MESSAGE;
    }

    void registerPattern(LanguagePattern languagePattern) {
        if (languagePattern == null || languagePattern.getPattern() == null) {
            return;
        }

        languagePatterns.add(languagePattern);
    }
}
