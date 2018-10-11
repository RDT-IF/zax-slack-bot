package org.rdtif.zaxslackbot;

import javax.inject.Inject;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.rdtif.zaxslackbot.interpreter.LanguageProcessor;

class MessagePostedListener implements SlackMessagePostedListener {
    private final LanguageProcessor languageProcessor;

    @Inject
    MessagePostedListener(LanguageProcessor languageProcessor) {
        this.languageProcessor = languageProcessor;
    }

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        if (!event.getSender().isBot()) {
            String messageContent = event.getMessageContent();
            String tag = "<@" + session.sessionPersona().getId() + ">";
            if (event.getChannel().isDirect() || messageContent.contains(tag)) {
                String message = languageProcessor.responseTo(messageContent.replaceAll(tag, "").trim(), session, event.getChannel());
                session.sendMessage(event.getChannel(), message);
            }
        }
    }
}
