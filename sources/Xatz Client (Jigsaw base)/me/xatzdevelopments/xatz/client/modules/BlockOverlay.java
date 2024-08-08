package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import me.xatzdevelopments.xatz.utils.Player.PlayerUtils1;
import me.xatzdevelopments.xatz.utils.Render.RenderUtils2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;

public class BlockOverlay extends Module {

	public BlockOverlay() {
		super("BlockOverlay", Keyboard.KEY_NONE, Category.RENDER, "Puts an overlay over blocks");
	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {

		super.onEnable();
	}

	@Override
	public void onRender() {

        final Block block = PlayerUtils1.getBlock(BlockOverlay.mc.objectMouseOver.getBlockPos().getX(), BlockOverlay.mc.objectMouseOver.getBlockPos().getY(), BlockOverlay.mc.objectMouseOver.getBlockPos().getZ());
        final float damage = BlockOverlay.mc.playerController.curBlockDamageMP;
        if (block.getMaterial() != Material.air) {
            this.drawESP(BlockOverlay.mc.objectMouseOver.getBlockPos().getX(), BlockOverlay.mc.objectMouseOver.getBlockPos().getY(), BlockOverlay.mc.objectMouseOver.getBlockPos().getZ(), 0.0f + damage, 1.0f - damage, 0.0f, 0.15f, 0.0f, 0.0f, 0.0f, 1.0f);
        }
    }
    
    public void drawESP(double posX, double posY, double posZ, final float insideR, final float insideG, final float insideB, final float insideA, final float outsideR, final float outsideG, final float outsideB, final float outsideA) {
        final float damage = Minecraft.getMinecraft().playerController.curBlockDamageMP / 2.0f;
        posX -= RenderManager.renderPosX;
        posY -= RenderManager.renderPosY;
        posZ -= RenderManager.renderPosZ;
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        RenderUtils2.drawOutlinedBoundingBox(new AxisAlignedBB(posX + damage, posY + damage, posZ + damage, posX + 1.0 - damage, posY + 1.0 - damage, posZ + 1.0 - damage));
        GL11.glColor4f(insideR, insideG, insideB, insideA);
        RenderUtils2.drawBoundingBox(new AxisAlignedBB(posX + damage, posY + damage, posZ + damage, posX + 1.0 - damage, posY + 1.0 - damage, posZ + 1.0 - damage));
        GL11.glColor4f(outsideR, outsideG, outsideB, outsideA);
        RenderUtils2.drawOutlinedBoundingBox(new AxisAlignedBB(posX, posY, posZ, posX + 1.0, posY + 1.0, posZ + 1.0));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}