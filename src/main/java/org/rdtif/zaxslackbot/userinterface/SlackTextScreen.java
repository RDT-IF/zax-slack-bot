package org.rdtif.zaxslackbot.userinterface;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;

public class SlackTextScreen extends TextScreen {
    private final SlackSession slackSession;
    private final SlackChannel slackChannel;
    private String slackMessageTimeStamp;

    public SlackTextScreen(SlackSession slackSession, SlackChannel slackChannel, Extent size) {
        super(size);
        this.slackSession = slackSession;
        this.slackChannel = slackChannel;
    }

    public void initialize() {
        slackMessageTimeStamp = slackSession.sendMessage(slackChannel, "Display loading...").getReply().getTimestamp();
    }

    public void update() {
        String replaceActualBackTicks = "`" + (char) 11 + "`" + (char) 11 + "`";
        String message = "```\n" + getJoinedText().replaceAll("```", replaceActualBackTicks) + "\n```";
        slackMessageTimeStamp = slackSession.updateMessage(slackMessageTimeStamp, slackChannel, message).getReply().getTimestamp();
    }
}
