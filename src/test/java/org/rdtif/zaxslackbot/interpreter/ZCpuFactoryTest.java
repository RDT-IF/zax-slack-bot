package org.rdtif.zaxslackbot.interpreter;

import com.zaxsoft.zax.zmachine.ZCPU;
import com.zaxsoft.zax.zmachine.ZUserInterface;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ZCpuFactoryTest {
    @Rule public ExpectedException thrown = ExpectedException.none();
    private final ZUserInterface userInterface = mock(ZUserInterface.class);
    private final ZCpuFactory factory = new ZCpuFactory();

    @Test
    public void neverReturnNull() {
        ZCPU zcpu = factory.create(userInterface);

        assertThat(zcpu, notNullValue());
    }

    @Test
    public void createWithProvidedUserInterface() {
        ZCPU zcpu = factory.create(userInterface);
        doThrow(IllegalArgumentException.class).when(userInterface).fatal(anyString());
        thrown.expect(IllegalArgumentException.class);

        zcpu.initialize("");
    }
}