package org.rdtif.zaxslackbot.interpreter;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;

public interface Action {
    String execute(String input, LanguagePattern pattern, SlackSession session, SlackChannel channel);
}
