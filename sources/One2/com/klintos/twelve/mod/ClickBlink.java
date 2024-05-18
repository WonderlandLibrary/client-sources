// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import net.minecraft.block.Block;
import com.klintos.twelve.utils.RenderUtils;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockSign;
import com.klintos.twelve.mod.events.EventRender;
import com.klintos.twelve.utils.PlayerUtils;
import org.lwjgl.input.Mouse;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.mod.events.EventReach;
import net.minecraft.util.BlockPos;

public class ClickBlink extends Mod
{
    private BlockPos endPos;
    private boolean canTP;
    private int delay;
    
    public ClickBlink() {
        super("ClickBlink", 0, ModCategory.EXPLOITS);
    }
    
    @EventTarget
    public void onReach(final EventReach event) {
        event.setReach(42.0f);
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        if (this.canTP && Mouse.isButtonDown(1) && !ClickBlink.mc.thePlayer.isSneaking() && this.delay == 0 && ClickBlink.mc.inGameHasFocus) {
            this.endPos = ClickBlink.mc.objectMouseOver.func_178782_a();
            PlayerUtils.blinkToPos(PlayerUtils.getPos(), this.endPos, 0.0);
            ClickBlink.mc.thePlayer.setPosition(this.endPos.getX() + 0.5, this.endPos.getY() + 1.0, this.endPos.getZ() + 0.5);
            ClickBlink.mc.thePlayer.playSound("mob.endermen.portal", 100.0f, 1.0f);
            this.delay = 5;
        }
        if (this.delay > 0) {
            --this.delay;
        }
    }
    
    @EventTarget
    public void onRender(final EventRender event) {
        final int x = ClickBlink.mc.objectMouseOver.func_178782_a().getX();
        final int y = ClickBlink.mc.objectMouseOver.func_178782_a().getY();
        final int z = ClickBlink.mc.objectMouseOver.func_178782_a().getZ();
        final Block block1 = PlayerUtils.getBlock(new BlockPos(x, y, z));
        final Block block2 = PlayerUtils.getBlock(new BlockPos((double)x, y + 1.0, (double)z));
        final Block block3 = PlayerUtils.getBlock(new BlockPos((double)x, y + 2.0, (double)z));
        final boolean blockBelow = !(block1 instanceof BlockSign) && block1.getMaterial().isSolid();
        final boolean blockLevel = block2 instanceof BlockSign || !block2.getMaterial().isSolid();
        final boolean blockAbove = block3 instanceof BlockSign || !block3.getMaterial().isSolid();
        if (PlayerUtils.getBlock(ClickBlink.mc.objectMouseOver.func_178782_a()).getMaterial() != Material.air && blockBelow && blockLevel && blockAbove) {
            this.canTP = true;
            GL11.glColor4f(1.0f, 0.33f, 0.33f, 1.0f);
            RenderUtils.drawBoundingBox(new AxisAlignedBB(x - RenderManager.renderPosX, y + 1 - RenderManager.renderPosY, z - RenderManager.renderPosZ, x - RenderManager.renderPosX + 1.0, y + 1.1 - RenderManager.renderPosY, z - RenderManager.renderPosZ + 1.0));
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
            RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - RenderManager.renderPosX, y + 1 - RenderManager.renderPosY, z - RenderManager.renderPosZ, x - RenderManager.renderPosX + 1.0, y + 1.1 - RenderManager.renderPosY, z - RenderManager.renderPosZ + 1.0));
        }
        else {
            this.canTP = false;
        }
    }
}
