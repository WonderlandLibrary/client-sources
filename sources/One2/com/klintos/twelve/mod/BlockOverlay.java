// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.klintos.twelve.utils.RenderUtils;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.Minecraft;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import com.klintos.twelve.utils.PlayerUtils;
import com.klintos.twelve.mod.events.EventRender;

public class BlockOverlay extends Mod
{
    public BlockOverlay() {
        super("BlockOverlay", 0, ModCategory.RENDER);
    }
    
    @EventTarget
    public void onRender(final EventRender event) {
        final Block block = PlayerUtils.getBlock(BlockOverlay.mc.objectMouseOver.func_178782_a().getX(), BlockOverlay.mc.objectMouseOver.func_178782_a().getY(), BlockOverlay.mc.objectMouseOver.func_178782_a().getZ());
        final float damage = BlockOverlay.mc.playerController.curBlockDamageMP;
        if (block.getMaterial() != Material.air) {
            this.drawESP(BlockOverlay.mc.objectMouseOver.func_178782_a().getX(), BlockOverlay.mc.objectMouseOver.func_178782_a().getY(), BlockOverlay.mc.objectMouseOver.func_178782_a().getZ(), 0.0f + damage, 1.0f - damage, 0.0f, 0.15f, 0.0f, 0.0f, 0.0f, 1.0f);
        }
    }
    
    public void drawESP(double posX, double posY, double posZ, final float insideR, final float insideG, final float insideB, final float insideA, final float outsideR, final float outsideG, final float outsideB, final float outsideA) {
        final float damage = Minecraft.getMinecraft().playerController.curBlockDamageMP / 2.0f;
        posX -= RenderManager.renderPosX;
        posY -= RenderManager.renderPosY;
        posZ -= RenderManager.renderPosZ;
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(posX + damage, posY + damage, posZ + damage, posX + 1.0 - damage, posY + 1.0 - damage, posZ + 1.0 - damage));
        GL11.glColor4f(insideR, insideG, insideB, insideA);
        RenderUtils.drawBoundingBox(new AxisAlignedBB(posX + damage, posY + damage, posZ + damage, posX + 1.0 - damage, posY + 1.0 - damage, posZ + 1.0 - damage));
        GL11.glColor4f(outsideR, outsideG, outsideB, outsideA);
        RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(posX, posY, posZ, posX + 1.0, posY + 1.0, posZ + 1.0));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
