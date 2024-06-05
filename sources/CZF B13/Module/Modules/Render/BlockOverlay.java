package gq.vapu.czfclient.Module.Modules.Render;


import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender2D;
import gq.vapu.czfclient.API.Events.Render.EventRender3D;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public class BlockOverlay extends Module {
    public Numbers<Double> r;
    public Numbers<Double> g;
    public Numbers<Double> b;

    public Option<Boolean> togg;

    public BlockOverlay() {
        super("BlockOverlay", new String[]{"BlockOverlay"}, ModuleType.Render);
        this.r = new Numbers<Double>("Red", "Red", 0.0, 0.0, 255.0, 1.0);
        this.g = new Numbers<Double>("Green", "Green", 128.0, 0.0, 255.0, 1.0);
        this.b = new Numbers<Double>("Blue", "Blue", 255.0, 0.0, 255.0, 1.0);

        this.togg = new Option<Boolean>("RenderString", "RenderString", true);

        this.addValues(this.r, this.g, this.b, this.togg);
    }

    public static int reAlpha(final int color, final float alpha) {
        final Color c = new Color(color);
        final float r = 0.003921569f * c.getRed();
        final float g = 0.003921569f * c.getGreen();
        final float b = 0.003921569f * c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }

    public int getRed() {
        return this.r.getValue().intValue();
    }

    public int getGreen() {
        return this.g.getValue().intValue();
    }

    public int getBlue() {
        return this.b.getValue().intValue();
    }

    public boolean getRender() {
        return this.togg.getValue();
    }

    @EventHandler
    public void onRender(final EventRender2D event) {
        if (BlockOverlay.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final FontRenderer fr = BlockOverlay.mc.fontRendererObj;
            final BlockPos pos = BlockOverlay.mc.objectMouseOver.getBlockPos();
            final Minecraft mc = BlockOverlay.mc;
            final Block block = mc.theWorld.getBlockState(pos).getBlock();
            final int id = Block.getIdFromBlock(block);
            final String s = String.valueOf(block.getLocalizedName()) + " ID:" + id;
            final String s2 = block.getLocalizedName();
            final String s3 = " ID:" + id;
            if (BlockOverlay.mc.objectMouseOver != null && this.getRender()) {
                final ScaledResolution res = new ScaledResolution(BlockOverlay.mc);
                final int x = res.getScaledWidth() / 2 + 6;
                final int y = res.getScaledHeight() / 2 - 1;
                BlockOverlay.mc.fontRendererObj.drawStringWithShadow(s2, x + 4.0f, y - 2.65f, new Color(this.getRed() / 255.0f, this.getGreen() / 255.0f, this.getBlue() / 255.0f).getRGB());
            }
        }
    }

    @EventHandler
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
            GL11.glColor4f(this.getRed() / 255.0f, this.getGreen() / 255.0f, this.getBlue() / 255.0f, 0.15f);
            final double minX = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinX();
            final double minY = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinY();
            final double minZ = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0 : block.getBlockBoundsMinZ();
            RenderUtil.drawBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            GL11.glColor4f(this.getRed() / 255.0f, this.getGreen() / 255.0f, this.getBlue() / 255.0f, 1.0f);
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
