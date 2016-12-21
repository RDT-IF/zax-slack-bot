package org.rdtif.zaxslackbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

class LanguageProcessor {
    private final Map<LanguageAction, Action> actionMap;
    private List<LanguagePattern> languagePatterns = new ArrayList<>();

    @Inject
    LanguageProcessor(Map<LanguageAction, Action> actionMap) {
        this.actionMap = actionMap;
    }

    String responseTo(String input) {
        for (LanguagePattern pattern : languagePatterns) {
            if (input.toLowerCase().matches(pattern.getPattern().toLowerCase())) {
                Action action = actionMap.get(pattern.getAction());
                if (action == null) {
                    return pattern.responseForFirst();
                } else {
                    return action.execute(pattern);
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
