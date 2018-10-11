package org.rdtif.zaxslackbot.interpreter;

import com.zaxsoft.zax.zmachine.ZCPU;
import com.zaxsoft.zax.zmachine.ZUserInterface;

class ZCpuFactory {
    ZCPU create(ZUserInterface userInterface) {
        return new ZCPU(userInterface);
    }
}
