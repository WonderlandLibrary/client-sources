/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.settings.BooleanSetting;

public class SafeWalk
extends Module {
    public boolean overBlockFly = false;
    public BooleanSetting voidSetting = new BooleanSetting(Qprot0.0("\u1d84\u71c4\u26cf\ua7e0"), true);

    public boolean shouldStop() {
        SafeWalk Nigga;
        if (Nigga.isEnabled() && Nigga.mc.thePlayer != null) {
            return Nigga.mc.thePlayer.onGround;
        }
        return false;
    }

    public SafeWalk() {
        super(Qprot0.0("\u1d81\u71ca\u26c0\ua7e1\u3f76\u9809\u8c23\u71f4"), 0, Module.Category.MOVEMENT);
        SafeWalk Nigga;
        Nigga.settings.add(Nigga.voidSetting);
    }

    public static {
        throw throwable;
    }

    public boolean inVoid() {
        SafeWalk Nigga;
        boolean Nigga2 = false;
        int Nigga3 = 0;
        while ((double)Nigga3 < Nigga.mc.thePlayer.posY) {
            BlockPos Nigga4 = new BlockPos(Nigga.mc.thePlayer.posX, (double)Nigga3 - 0.0, Nigga.mc.thePlayer.posZ);
            if (!Minecraft.theWorld.isAirBlock(Nigga4)) {
                Nigga2 = true;
            }
            ++Nigga3;
        }
        if (ModuleManager.fly.isEnabled()) {
            if (!Nigga2) {
                Nigga.overBlockFly = false;
            } else if (Nigga.overBlockFly) {
                Nigga.overBlockFly = true;
            }
        } else {
            Nigga.overBlockFly = true;
        }
        return Nigga2;
    }
}

