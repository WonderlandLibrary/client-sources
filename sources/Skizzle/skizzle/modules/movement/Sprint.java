/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;

public class Sprint
extends Module {
    public Sprint() {
        super(Qprot0.0("\u55ef\u71de\u6eb6\ua7eb\u472e\ud064\u8c3d\u3992\u570c\uec43"), 0, Module.Category.MOVEMENT);
        Sprint Nigga;
    }

    @Override
    public void onEvent(Event Nigga) {
        Sprint Nigga2;
        if (!(!(Nigga instanceof EventUpdate) || !Nigga.isPre() || !(Nigga2.mc.thePlayer.moveForward > Float.intBitsToFloat(2.13072154E9f ^ 0x7F003AE8)) || Nigga2.mc.thePlayer.isCollidedHorizontally || (Nigga2.mc.thePlayer.isSneaking() || ModuleManager.fastSneak.isEnabled()) && !ModuleManager.fastSneak.isEnabled() || Nigga2.mc.thePlayer.isUsingItem() || ModuleManager.killaura.isEnabled() && ModuleManager.killaura.targeting != null && ModuleManager.killaura.rotations.getMode().equals(Qprot0.0("\u55ed\u71c7\u6ea3\ue27e\u8187\ud07d\u8c2c")))) {
            Nigga2.mc.thePlayer.setSprinting(true);
        }
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEnable() {
        Sprint Nigga;
        Nigga.mc.thePlayer.setSprinting(true);
    }

    @Override
    public void onDisable() {
        Sprint Nigga;
        Nigga.mc.thePlayer.setSprinting(Nigga.mc.gameSettings.keyBindSprint.getIsKeyPressed());
    }
}

