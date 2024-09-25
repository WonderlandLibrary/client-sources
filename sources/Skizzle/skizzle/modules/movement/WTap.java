/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package skizzle.modules.movement;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import skizzle.events.Event;
import skizzle.events.listeners.EventDamage;
import skizzle.modules.Module;
import skizzle.util.Timer;

public class WTap
extends Module {
    public boolean shouldUnPress = false;
    public Timer undelay;
    public boolean shouldPress = false;
    public Timer delay = new Timer();

    @Override
    public void onEvent(Event Nigga) {
        WTap Nigga2;
        if (Nigga instanceof EventDamage) {
            boolean Nigga3 = false;
            EventDamage Nigga4 = (EventDamage)Nigga;
            if (Nigga4.source.getDamageType().equals(Qprot0.0("\u737f\u71ce\u4802\ue268\ue714\uf6cb\u8c2c"))) {
                Nigga2.delay.lastMS = System.currentTimeMillis();
                Nigga2.undelay.lastMS = System.currentTimeMillis();
                if (!Keyboard.isKeyDown((int)Nigga2.mc.gameSettings.keyBindForward.getKeyCode())) {
                    Nigga2.shouldUnPress = true;
                }
                KeyBinding.setKeyBindState(Nigga2.mc.gameSettings.keyBindForward.getKeyCode(), false);
                Nigga2.shouldPress = true;
            }
        }
        if (Nigga2.shouldPress && Nigga2.delay.hasTimeElapsed((long)454226104 ^ 0x1B12F08AL, true)) {
            Nigga2.shouldPress = false;
            KeyBinding.setKeyBindState(Nigga2.mc.gameSettings.keyBindForward.getKeyCode(), true);
        }
        if (Nigga2.shouldUnPress && Nigga2.undelay.hasTimeElapsed((long)-1208408809 ^ 0xFFFFFFFFB7F92573L, true)) {
            KeyBinding.setKeyBindState(Nigga2.mc.gameSettings.keyBindForward.getKeyCode(), false);
            Nigga2.shouldUnPress = false;
        }
    }

    public WTap() {
        super(Qprot0.0("\u734f\u71ff\u480d\ua7f4"), 0, Module.Category.MOVEMENT);
        WTap Nigga;
        Nigga.undelay = new Timer();
    }

    public static {
        throw throwable;
    }
}

