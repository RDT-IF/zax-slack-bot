package org.rdtif.zaxslackbot;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPersona;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.rdtif.zaxslackbot.interpreter.LanguageProcessor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessagePostedListenerTest {
    private final SlackPersona persona = mock(SlackPersona.class);
    private final SlackUser sender = mock(SlackUser.class);
    private final SlackSession slackSession = mock(SlackSession.class);
    private final SlackMessagePosted messagePosted = mock(SlackMessagePosted.class);
    private final SlackChannel channel = mock(SlackChannel.class);

    private final LanguageProcessor languageProcessor = new EchoProcessor();
    private final MessagePostedListener messagePostedListener = new MessagePostedListener(languageProcessor);

    private String id;

    @Before
    public void setup() {
        id = RandomStringUtils.randomAlphabetic(5);
        when(persona.getId()).thenReturn(id);
        when(slackSession.sessionPersona()).thenReturn(persona);
        when(messagePosted.getChannel()).thenReturn(channel);
    }

    @Test
    public void doNotTalkToBots() {
        when(sender.isBot()).thenReturn(true);
        when(messagePosted.getSender()).thenReturn(sender);

        messagePostedListener.onEvent(messagePosted, slackSession);

        verify(slackSession, never()).sendMessage(any(SlackChannel.class), any(String.class));
    }

    @Test
    public void doNotSpeakUnlessSpokenTo() {
        String message = RandomStringUtils.randomAlphabetic(10);

        when(sender.isBot()).thenReturn(false);
        when(messagePosted.getSender()).thenReturn(sender);
        when(messagePosted.getMessageContent()).thenReturn(message);

        messagePostedListener.onEvent(messagePosted, slackSession);

        verify(slackSession, never()).sendMessage(any(SlackChannel.class), any(String.class));
    }

    @Test
    public void speakWhenSpokenTo() {
        String tag = "<@" + id + ">";
        String message = RandomStringUtils.randomAlphabetic(10);

        when(sender.isBot()).thenReturn(false);
        when(messagePosted.getSender()).thenReturn(sender);
        when(messagePosted.getMessageContent()).thenReturn(tag + " " + message);

        when(messagePosted.getChannel()).thenReturn(channel);

        messagePostedListener.onEvent(messagePosted, slackSession);

        verify(slackSession).sendMessage(eq(channel), eq(message));
    }

    @Test
    public void speakWhenSpokenToInDirectMessage() {
        String message = RandomStringUtils.randomAlphabetic(10);

        when(sender.isBot()).thenReturn(false);
        when(messagePosted.getSender()).thenReturn(sender);
        when(messagePosted.getMessageContent()).thenReturn(message);

        when(channel.isDirect()).thenReturn(true);
        when(messagePosted.getChannel()).thenReturn(channel);

        messagePostedListener.onEvent(messagePosted, slackSession);

        verify(slackSession).sendMessage(eq(channel), eq(message));
    }

    private static class EchoProcessor extends LanguageProcessor {
        EchoProcessor() {
            super(null);
        }

        @Override
        public String responseTo(String input, SlackSession session, SlackChannel channel) {
            return input;
        }
    }
}