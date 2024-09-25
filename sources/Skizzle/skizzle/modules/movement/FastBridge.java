/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import skizzle.events.Event;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;
import skizzle.util.Timer;

public class FastBridge
extends Module {
    public Timer timer;
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u5c8c\u71c4\u67d1\ua7e1"), Qprot0.0("\u5c92\u71db\u67da\ua7eb\u7e70"), Qprot0.0("\u5c92\u71db\u67da\ua7eb\u7e70"), Qprot0.0("\u5c8a\u71ce\u67cc\ua7e6\u7e7f\ud915\u8c2b"));

    public FastBridge() {
        super(Qprot0.0("\u5c87\u71ca\u67c6\ua7f0\u7e54\ud909\u8c26\u30e8\u5705\ud539"), 0, Module.Category.MOVEMENT);
        FastBridge Nigga;
        Nigga.timer = new Timer();
        Nigga.addSettings(Nigga.mode);
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        FastBridge Nigga2;
        if (Nigga2.mc.thePlayer != null) {
            BlockPos Nigga3 = new BlockPos(Nigga2.mc.thePlayer.posX, Nigga2.mc.thePlayer.posY - 1.0, Nigga2.mc.thePlayer.posZ);
            if (Minecraft.theWorld.isAirBlock(Nigga3)) {
                if (Nigga2.mode.getMode().equals(Qprot0.0("\u5c92\u71db\u67da\ue262\ub8ef"))) {
                    Nigga2.mc.thePlayer.movementInput.sneak = true;
                }
                if (Nigga2.mode.getMode().equals(Qprot0.0("\u5c8a\u71ce\u67cc\ue26f\ub8e0\ud915\u8c2b"))) {
                    KeyBinding.setKeyBindState(Nigga2.mc.gameSettings.keyBindSneak.getKeyCode(), true);
                }
                Nigga2.timer.reset();
            } else if (Nigga2.timer.hasTimeElapsed(Nigga2.mode.getMode().equals(Qprot0.0("\u5c92\u71db\u67da\ue262\ub8ef")) ? 100 : 10, false)) {
                if (Nigga2.mode.getMode().equals(Qprot0.0("\u5c92\u71db\u67da\ue262\ub8ef"))) {
                    Nigga2.mc.thePlayer.movementInput.sneak = false;
                }
                if (Nigga2.mode.getMode().equals(Qprot0.0("\u5c8a\u71ce\u67cc\ue26f\ub8e0\ud915\u8c2b"))) {
                    KeyBinding.setKeyBindState(Nigga2.mc.gameSettings.keyBindSneak.getKeyCode(), false);
                }
            }
        }
    }
}

