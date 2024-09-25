/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;
import skizzle.util.MoveUtil;

public class LongJump
extends Module {
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u7bb3\u71c4\u4116\ua7e1"), Qprot0.0("\u7bac\u71ce\u4116\ua7e1\u55be\ufe2f\u8c36"), Qprot0.0("\u7bac\u71ce\u4116\ua7e1\u55be\ufe2f\u8c36"));
    public int redesky = 0;

    @Override
    public void onEnable() {
        Nigga.redesky = 0;
    }

    @Override
    public void onEvent(Event Nigga) {
        LongJump Nigga2;
        if (Nigga instanceof EventMotion && Nigga2.mode.getMode().equals(Qprot0.0("\u7bac\u71ce\u4116\ue268\u9e37\ufe2f\u8c36"))) {
            if (Nigga2.mc.thePlayer.onGround) {
                Nigga2.mc.thePlayer.jump();
            }
            if (MoveUtil.isMoving()) {
                Nigga2.mc.timer.timerSpeed = Float.intBitsToFloat(1.09247629E9f ^ 0x7EBBBB21);
                Nigga2.mc.thePlayer.speedInAir = Float.intBitsToFloat(1.12254669E9f ^ 0x7F09F39B);
                Nigga2.mc.thePlayer.motionY += 0.0;
                ++Nigga2.redesky;
                if (Nigga2.redesky > 40) {
                    Nigga2.toggle();
                }
            } else {
                Nigga2.mc.timer.timerSpeed = Float.intBitsToFloat(1.12031027E9f ^ 0x7D4693CF);
                Nigga2.toggle();
            }
        }
    }

    public static {
        throw throwable;
    }

    public LongJump() {
        super(Qprot0.0("\u7bb2\u71c4\u411c\ua7e3\u5587\ufe31\u8c22\u163b"), 0, Module.Category.MOVEMENT);
        LongJump Nigga;
    }

    @Override
    public void onDisable() {
        Nigga.mc.thePlayer.speedInAir = Float.intBitsToFloat(1.11870182E9f ^ 0x7E0DDE2F);
        Nigga.mc.timer.timerSpeed = Float.intBitsToFloat(1.13077952E9f ^ 0x7CE653BF);
    }
}

