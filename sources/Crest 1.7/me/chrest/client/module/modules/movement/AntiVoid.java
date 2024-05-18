// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement;

import net.minecraft.block.Block;
import me.chrest.event.EventTarget;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import me.chrest.event.events.TickEvent;
import net.minecraft.client.Minecraft;
import me.chrest.client.module.Module;

@Mod
public class AntiVoid extends Module
{
    protected Minecraft mc;
    
    public AntiVoid() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @EventTarget
    public void onTick(final TickEvent event) {
        boolean hasGround = false;
        for (int i = 1; i < this.mc.thePlayer.posY; ++i) {
            final BlockPos pos = new BlockPos(this.mc.thePlayer.posX, i, this.mc.thePlayer.posZ);
            if (!(getBlock(pos) instanceof BlockAir)) {
                hasGround = true;
            }
        }
        if (!hasGround && this.mc.thePlayer.posY <= 0.0) {
            this.mc.thePlayer.motionY = 0.1;
        }
    }
    
    public static Block getBlock(final BlockPos block) {
        return Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock();
    }
}
