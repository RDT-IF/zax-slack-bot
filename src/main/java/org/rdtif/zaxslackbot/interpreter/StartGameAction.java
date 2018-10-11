package org.rdtif.zaxslackbot.interpreter;

import com.google.inject.Inject;
import com.zaxsoft.zax.zmachine.ZCPU;
import org.rdtif.zaxslackbot.GameRepository;
import org.rdtif.zaxslackbot.userinterface.SlackZUserInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartGameAction implements Action {
    private final GameRepository gameRepository;
    private final ZCpuFactory zCpuFactory;
    private final String gameDirectory;

    @Inject
    public StartGameAction(GameRepository repository, ZCpuFactory zCpuFactory, String gameDirectory) {
        this.gameRepository = repository;
        this.zCpuFactory = zCpuFactory;
        this.gameDirectory = gameDirectory;
    }

    @Override
    public String execute(String input, LanguagePattern pattern) {
        String gameName = extractGameName(input, pattern);

        if (gameRepository.fileNames().contains(gameName)) {
            ZCPU cpu = zCpuFactory.create(new SlackZUserInterface());
            cpu.initialize(gameDirectory + gameName);
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

    String getGameDirectory() {
        return gameDirectory;
    }
}
