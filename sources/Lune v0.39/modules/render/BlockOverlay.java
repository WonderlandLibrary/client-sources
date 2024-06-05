package me.superskidder.lune.modules.render;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventRender2D;
import me.superskidder.lune.events.EventRender3D;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.render.Colors;
import me.superskidder.lune.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.*;

import java.awt.Color;

import org.lwjgl.opengl.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class BlockOverlay extends Mod {

    public BlockOverlay() {
        super("BlockOverlay", ModCategory.Render,"High light your selecting block");
    }

    @EventTarget
    public void onRender(final EventRender2D event) {
        if (BlockOverlay.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final FontRenderer fr = BlockOverlay.mc.fontRendererObj;
            final BlockPos pos = BlockOverlay.mc.objectMouseOver.getBlockPos();
            final Minecraft mc = BlockOverlay.mc;
            final Block block = mc.theWorld.getBlockState(pos).getBlock();
            final int id = Block.getIdFromBlock(block);
            final String s = String.valueOf(String.valueOf(block.getLocalizedName())) + " ID:" + id;
            final String s2 = block.getLocalizedName();
            final String s3 = " ID:" + id;
            if (BlockOverlay.mc.objectMouseOver != null) {
                final ScaledResolution res = new ScaledResolution(BlockOverlay.mc);
                final int x = res.getScaledWidth() / 2 + 6;
                final int y = res.getScaledHeight() / 2 - 1;
                BlockOverlay.mc.fontRendererObj.drawStringWithShadow(s2, x + 4.0f, y - 2.65f, Colors.GREY.c);
            }
        }
    }

    public static int reAlpha(final int color, final float alpha) {
        final Color c = new Color(color);
        final float r = 0.003921569f * c.getRed();
        final float g = 0.003921569f * c.getGreen();
        final float b = 0.003921569f * c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }

    @EventTarget
    public void onRender3D(final EventRender3D event) {
        if (BlockOverlay.mc.objectMouseOver != null && BlockOverlay.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos pos = BlockOverlay.mc.objectMouseOver.getBlockPos();
            final Minecraft mc = BlockOverlay.mc;
            final Block block = mc.theWorld.getBlockState(pos).getBlock();
            final String s = block.getLocalizedName();
            BlockOverlay.mc.getRenderManager();
            final double n = pos.getX();
            BlockOverlay.mc.getRenderManager();
            final double x = n - RenderManager.renderPosX;
            BlockOverlay.mc.getRenderManager();
            final double n2 = pos.getY();
            BlockOverlay.mc.getRenderManager();
            final double y = n2 - RenderManager.renderPosY;
            BlockOverlay.mc.getRenderManager();
            final double n3 = pos.getZ();
            BlockOverlay.mc.getRenderManager();
            final double z = n3 - RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glColor4f(1, 1, 1, 0.15f);
            final double minX = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinX();
            final double minY = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinY();
            final double minZ = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinZ();
            RenderUtil.drawBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            GL11.glColor4f(1, 1, 1, 1.0f);
            GL11.glLineWidth(0.5f);
            RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
