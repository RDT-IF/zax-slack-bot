package org.rdtif.zaxslackbot.interpreter;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.rdtif.zaxslackbot.GameRepository;

import java.util.ArrayList;
import java.util.List;

class ListGamesAction implements Action {
    private final GameRepository repository;

    ListGamesAction(GameRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(String input, LanguagePattern pattern, SlackSession session, SlackChannel channel) {
        List<String> names = new ArrayList<>(repository.fileNames());
        if (names.size() == 0) {
            return pattern.responseFor("default");
        } else {
            return pattern.responseFor("games") + formatList(names) + ".";
        }
    }

    private String formatList(List<String> names) {
        if (names.size() == 1) {
            return names.get(0);
        } else if (names.size() == 2) {
            return names.get(0) + " and " + names.get(1);
        } else {
            String last = "and " + names.remove(names.size() - 1);
            names.add(last);
            return String.join(", ", names);
        }
    }
}
