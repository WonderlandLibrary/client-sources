/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import net.minecraft.client.settings.KeyBinding;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;

public class AutoWalk
extends Module {
    public AutoWalk() {
        super(Qprot0.0("\u5eed\u71de\u65b4\ua7eb\u7054\udb77\u8c23\u3292"), 0, Module.Category.MOVEMENT);
        AutoWalk Nigga;
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        AutoWalk Nigga2;
        if (Nigga instanceof EventUpdate && !Client.ghostMode && !Nigga2.mc.thePlayer.isCollidedHorizontally) {
            KeyBinding.setKeyBindState(Nigga2.mc.gameSettings.keyBindForward.getKeyCode(), true);
        }
    }

    @Override
    public void onDisable() {
        AutoWalk Nigga;
        KeyBinding.setKeyBindState(Nigga.mc.gameSettings.keyBindForward.getKeyCode(), false);
    }
}

