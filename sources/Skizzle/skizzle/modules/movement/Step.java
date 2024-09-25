/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;

public class Step
extends Module {
    public NumberSetting height = new NumberSetting(Qprot0.0("\u2bb0\u71ce\u10e5\ua7e3\u05a7\uae36"), 1.0, 1.0, 100.0, 1.0);

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventUpdate) {
            Step Nigga2;
            Nigga2.displayName = String.valueOf(Nigga2.name) + Qprot0.0("\u2bd8\u710c\u10bb") + (float)Nigga2.height.getValue();
            if (Nigga.isPre() && (double)Nigga2.mc.thePlayer.stepHeight != Nigga2.height.getValue()) {
                Nigga2.mc.thePlayer.stepHeight = (float)Nigga2.height.getValue();
            }
        }
    }

    @Override
    public void onEnable() {
        Step Nigga;
        float Nigga2;
        Nigga.mc.thePlayer.stepHeight = Nigga2 = (float)(Nigga.height.getValue() + 0.0);
    }

    public Step() {
        super(Qprot0.0("\u2bab\u71df\u10e9\ua7f4"), 0, Module.Category.MOVEMENT);
        Step Nigga;
        Nigga.addSettings(Nigga.height);
    }

    public static {
        throw throwable;
    }

    @Override
    public void onDisable() {
        Nigga.mc.thePlayer.stepHeight = Float.intBitsToFloat(1.07684595E9f ^ 0x7F2F5D46);
    }
}

