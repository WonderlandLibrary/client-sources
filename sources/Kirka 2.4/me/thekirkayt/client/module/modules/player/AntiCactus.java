/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.player;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.BoundingBoxEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@Module.Mod(displayName="AntiCactus")
public class AntiCactus
extends Module {
    @EventTarget
    private void onBoundingBox(BoundingBoxEvent event) {
        if (event.getBlock() instanceof BlockCactus) {
            event.setBoundingBox(new AxisAlignedBB(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ(), event.getBlockPos().getX() + 1, event.getBoundingBox().maxY, event.getBlockPos().getZ() + 1));
        }
    }
}

