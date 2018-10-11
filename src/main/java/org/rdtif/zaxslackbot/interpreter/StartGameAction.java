package org.rdtif.zaxslackbot.interpreter;

import com.google.inject.Inject;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.zaxsoft.zax.zmachine.ZCPU;
import org.rdtif.zaxslackbot.GameRepository;
import org.rdtif.zaxslackbot.userinterface.Extent;
import org.rdtif.zaxslackbot.userinterface.SlackTextScreen;
import org.rdtif.zaxslackbot.userinterface.SlackZUserInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartGameAction implements Action {
    private final GameRepository gameRepository;

    @Inject
    public StartGameAction(GameRepository repository) {
        gameRepository = repository;
    }

    @Override
    public String execute(String input, LanguagePattern pattern, SlackSession session, SlackChannel channel) {
        String gameName = extractGameName(input, pattern);

        if (gameRepository.fileNames().contains(gameName)) {
            ZCPU cpu = new ZCPU(new SlackZUserInterface(new SlackTextScreen(session, channel, new Extent(30, 80))));
            cpu.initialize("games/anchor.z8");
            cpu.run();
            return pattern.responseFor("start") + " " + gameName;
        }

        return pattern.responseFor("default");
    }

    private String extractGameName(String input, LanguagePattern pattern) {
        Pattern regex = Pattern.compile(pattern.getPattern());
        Matcher matcher = regex.matcher(input);

        if (matcher.find()) {
            return matcher.group(2);
        }

        return "";
    }

}
