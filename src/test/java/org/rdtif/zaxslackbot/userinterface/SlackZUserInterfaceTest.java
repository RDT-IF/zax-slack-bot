package org.rdtif.zaxslackbot.userinterface;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SlackZUserInterfaceTest {
    private final SlackTextScreen textScreen = mock(SlackTextScreen.class);
    private final SlackZUserInterface userInterface = new SlackZUserInterface(textScreen);

    @Test
    public void initializeCallsTextScreenInitialize() {
        userInterface.initialize(0);

        verify(textScreen).initialize();
    }
}