/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import winter.Client;
import winter.event.EventListener;
import winter.event.events.TickEvent;
import winter.module.Module;

public class NoVoid
extends Module {
    public NoVoid() {
        super("AntiVoid", Module.Category.Other, -4422865);
    }

    @EventListener
    public void onTick(TickEvent event) {
        boolean hasGround = false;
        int i2 = 1;
        while ((double)i2 < this.mc.thePlayer.posY) {
            BlockPos pos = new BlockPos(this.mc.thePlayer.posX, (double)i2, this.mc.thePlayer.posZ);
            if (!(Client.getBlock(pos) instanceof BlockAir)) {
                hasGround = true;
            }
            ++i2;
        }
        if (!hasGround && this.mc.thePlayer.posY <= 0.0) {
            this.mc.thePlayer.motionY = 0.1;
        }
    }
}

