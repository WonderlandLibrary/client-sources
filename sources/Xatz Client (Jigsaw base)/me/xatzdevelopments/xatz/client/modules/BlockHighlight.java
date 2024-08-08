package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.xatzdevelopments.xatz.client.NahrFont.FontType;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.tools.BlockHelper;
import me.xatzdevelopments.xatz.client.tools.RenderHelper2;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import java.util.Objects;


public class BlockHighlight extends Module {

    BlockPos pos;
    Block block;

	
	public BlockHighlight() {
		super("BlockHighlight", Keyboard.KEY_NONE, Category.RENDER, "Highlights blocks..");
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
	public void onUpdate() {

		super.onUpdate();
	}

	@Override
	public void onRender() {
        if (!Objects.isNull(mc.objectMouseOver.getBlockPos())) {
            pos = mc.objectMouseOver.getBlockPos();
            if (pos != null) {
               block = BlockHelper.getBlockAtPos(pos);
               GL11.glDisable(2896);
               GL11.glDisable(3553);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               GL11.glDisable(2929);
               GL11.glEnable(2848);
               GL11.glDepthMask(false);
               GL11.glLineWidth(1.25F);
               double x = (double)pos.getX() - RenderManager.renderPosX;
               double y = (double)pos.getY() - RenderManager.renderPosY;
               double z = (double)pos.getZ() - RenderManager.renderPosZ;
               new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
               AxisAlignedBB mask;
               if (block instanceof BlockSlab) {
                  mask = new AxisAlignedBB(x, y, z, x + 1.0D - (double)(1.0F * mc.playerController.curBlockDamageMP), y + 0.5D - 0.5D * (double)mc.playerController.curBlockDamageMP, z + 1.0D - (double)(1.0F * mc.playerController.curBlockDamageMP));
               } else {
                  mask = new AxisAlignedBB(x, y, z, x + 1.0D - (double)(1.0F * mc.playerController.curBlockDamageMP), y + 1.0D - (double)(1.0F * mc.playerController.curBlockDamageMP), z + 1.0D - (double)(1.0F * mc.playerController.curBlockDamageMP));
               }

               if (!(block instanceof BlockAir)) {
                  GL11.glColor4f(0.25F, 1.22F, 0.56F, 0.25F);
                  RenderHelper2.drawFilledBox(mask);
                  GL11.glColor4f(0.25F, 1.22F, 0.56F, 0.85F);
                  RenderHelper2.drawOutlinedBoundingBox(mask);
               }

               GL11.glDepthMask(true);
               GL11.glDisable(2848);
               GL11.glEnable(2929);
               GL11.glDisable(3042);
               GL11.glEnable(2896);
               GL11.glEnable(3553);
            }
         }
       if (!Objects.isNull(mc.objectMouseOver.getBlockPos())) {
         pos = mc.objectMouseOver.getBlockPos();
         if (pos != null) {
            block = BlockHelper.getBlockAtPos(pos);
            ScaledResolution scaledRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            if (!(block instanceof BlockAir)) {
               RenderHelper2.drawBorderedRect((float)(scaledRes.getScaledWidth() / 2 + 9), (float)(scaledRes.getScaledHeight() / 2 - 4), (float)(scaledRes.getScaledWidth() / 2 + 11) + RenderHelper2.getNahrFont().getStringWidth(block.getLocalizedName()), (float)(scaledRes.getScaledHeight() / 2 + 7), 1.0F, 1610612736, Integer.MIN_VALUE);
               RenderHelper2.getNahrFont().drawString(block.getLocalizedName(), (float)(scaledRes.getScaledWidth() / 2 + 10), (float)(scaledRes.getScaledHeight() / 2 - 7), FontType.OUTLINE_THIN, -1, -16777216);
            }
         }
      }
	}
}
