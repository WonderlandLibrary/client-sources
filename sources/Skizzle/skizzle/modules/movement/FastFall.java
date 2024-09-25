/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;

public class FastFall
extends Module {
    public NumberSetting dist = new NumberSetting(Qprot0.0("\u7c9b\u71ce\u47ce\ua7e3\u5e48\uf91d"), 4.0, 1.0, 20.0, 1.0);

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventMotion && !Client.ghostMode && Nigga.isPre()) {
            FastFall Nigga2;
            int Nigga3 = 0;
            int Nigga4 = 0;
            while ((double)Nigga4 < Nigga2.dist.getValue()) {
                if (Minecraft.theWorld.isAirBlock(new BlockPos(Nigga2.mc.thePlayer.posX, Nigga2.mc.thePlayer.posY, Nigga2.mc.thePlayer.posZ).offsetDown(Nigga4))) {
                    ++Nigga3;
                }
                ++Nigga4;
            }
            if ((double)Nigga3 == Nigga2.dist.getValue()) {
                Nigga2.mc.thePlayer.moveEntityWithHeading(Float.intBitsToFloat(2.11525875E9f ^ 0x7E14497F), Float.intBitsToFloat(2.12419533E9f ^ 0x7E9CA5D3));
            }
        }
    }

    public FastFall() {
        super(Qprot0.0("\u7c95\u71ca\u47d4\ua7f0\u5e66\uf908\u8c23\u10f2"), 0, Module.Category.MOVEMENT);
        FastFall Nigga;
        Nigga.addSettings(Nigga.dist);
    }
}

