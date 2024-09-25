/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.world;

import net.minecraft.client.Minecraft;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.settings.NumberSetting;

public class Timer
extends Module {
    public Minecraft mc = Minecraft.getMinecraft();
    public NumberSetting speed = new NumberSetting(Qprot0.0("\u81ed\u71db\ubad7\ua7e1\uab69"), 2.0, 0.0, 10.0, 0.0);

    public Timer() {
        super(Qprot0.0("\u81ea\u71c2\ubadf\ua7e1\uab7f"), 0, Module.Category.WORLD);
        Timer Nigga;
        Nigga.addSettings(Nigga.speed);
    }

    @Override
    public void onDisable() {
        Nigga.mc.timer.timerSpeed = Float.intBitsToFloat(1.08289779E9f ^ 0x7F0BB578);
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventUpdate) {
            Timer Nigga2;
            Nigga2.mc.timer.timerSpeed = (float)Nigga2.speed.getValue() * Float.intBitsToFloat(1.07332198E9f ^ 0x7ED997FD);
            if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u81f0\u71c4\ubac0\ue260\uf5e5\u0468"))) {
                Nigga2.displayName = String.valueOf(Nigga2.name) + Qprot0.0("\u819e\u710c\uba85") + Nigga2.speed.getValue();
            } else if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u81f0\u71c4\ubac6\ue265\uf5ed\u046a\u8c28"))) {
                Nigga2.displayName = Nigga2.name;
            } else if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u81ea\u71c4\ubadd\ue22d\uf5c9\u0471\u8c2c\uede3"))) {
                Nigga2.displayName = String.valueOf(Nigga2.name) + Qprot0.0("\u819e\u710c\uba85\ue25e\uf5be") + Nigga2.speed.getValue();
            }
        }
    }

    public static {
        throw throwable;
    }
}

