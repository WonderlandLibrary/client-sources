// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.misc;

import net.minecraft.block.BlockCactus;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.BoundingBoxEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.util.AxisAlignedBB;

@Mod(displayName = "Anti Cactus")
public class AntiCactus extends Module
{
	public AntiCactus(){
		this.setProperties("AntiCactus", "AntiCactus", Module.Category.Misc, 0, "", true);
	}
	
    @EventTarget
    private void onBoundingBox(final BoundingBoxEvent event) {
        if (event.getBlock() instanceof BlockCactus) {
            event.setBoundingBox(new AxisAlignedBB(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ(), event.getBlockPos().getX() + 1, event.getBoundingBox().maxY, event.getBlockPos().getZ() + 1));
        }
    }
}
