/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.render;

import skizzle.events.Event;
import skizzle.modules.Module;
import skizzle.util.Timer;

public class Child
extends Module {
    public float subtract = Float.intBitsToFloat(2.13439219E9f ^ 0x7F383DBF);
    public Timer timer = new Timer();

    @Override
    public void onEvent(Event Nigga) {
        Child Nigga2;
        float Nigga3 = Nigga2.timer.getDelay();
        if (Nigga2.timer.hasTimeElapsed((long)1843195557 ^ 0x6DDCEEA4L, true)) {
            for (int Nigga4 = 0; Nigga4 < 5; ++Nigga4) {
                if (!((double)Nigga2.subtract < 0.0)) continue;
                Nigga2.subtract = Nigga2.subtract + Float.intBitsToFloat(1.19766541E9f ^ 0x7DE1FED7) + Nigga3 / Float.intBitsToFloat(9.6404576E8f ^ 0x7F6A6BA5);
            }
        }
    }

    @Override
    public void onEnable() {
        Child Nigga;
        Nigga.subtract = Float.intBitsToFloat(2.12776525E9f ^ 0x7ED31EC3);
        Nigga.timer.reset();
    }

    public static {
        throw throwable;
    }

    @Override
    public void onDisable() {
        Nigga.subtract = Float.intBitsToFloat(2.13772122E9f ^ 0x7F6B09A3);
    }

    public Child() {
        super(Qprot0.0("\u7d6c\u71c3\u462a\ua7e8\u5c98"), 0, Module.Category.RENDER);
        Child Nigga;
    }
}

