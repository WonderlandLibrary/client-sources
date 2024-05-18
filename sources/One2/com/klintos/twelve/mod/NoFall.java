// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import com.klintos.twelve.utils.PlayerUtils;
import net.minecraft.util.MathHelper;
import com.klintos.twelve.mod.events.EventPostUpdate;
import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.mod.events.EventPreUpdate;

public class NoFall extends Mod
{
    public NoFall() {
        super("NoFall", 49, ModCategory.PLAYER);
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        NoFall.mc.thePlayer.onGround = false;
    }
    
    @EventTarget
    public void onPostUpdate(final EventPostUpdate event) {
        NoFall.mc.thePlayer.onGround = this.isOnGround();
    }
    
    public boolean isOnGround() {
        final int y = (int)NoFall.mc.thePlayer.getBoundingBox().offset(0.0, -0.1, 0.0).minY;
        for (int x = MathHelper.floor_double(NoFall.mc.thePlayer.getBoundingBox().minX); x < MathHelper.floor_double(NoFall.mc.thePlayer.getBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(NoFall.mc.thePlayer.getBoundingBox().minZ); z < MathHelper.floor_double(NoFall.mc.thePlayer.getBoundingBox().maxZ) + 1; ++z) {
                final Block block = PlayerUtils.getBlock(x, y, z);
                if (block != null && !(block instanceof BlockAir)) {
                    return true;
                }
            }
        }
        return false;
    }
}
