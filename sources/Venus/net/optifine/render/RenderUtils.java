/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import com.mojang.blaze3d.platform.GlStateManager;
import mpp.venusfr.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    private static boolean flushRenderBuffers = true;
    private static Minecraft mc = Minecraft.getInstance();

    public static boolean setFlushRenderBuffers(boolean bl) {
        boolean bl2 = flushRenderBuffers;
        flushRenderBuffers = bl;
        return bl2;
    }

    public static boolean isFlushRenderBuffers() {
        return flushRenderBuffers;
    }

    public static void flushRenderBuffers() {
        if (flushRenderBuffers) {
            RenderTypeBuffers renderTypeBuffers = mc.getRenderTypeBuffers();
            renderTypeBuffers.getBufferSource().flushRenderBuffers();
            renderTypeBuffers.getCrumblingBufferSource().flushRenderBuffers();
        }
    }

    public static void drawBlockBox(BlockPos blockPos, int n) {
        RenderUtils.drawBox(new AxisAlignedBB(blockPos).offset(-RenderUtils.mc.getRenderManager().info.getProjectedView().x, -RenderUtils.mc.getRenderManager().info.getProjectedView().y, -RenderUtils.mc.getRenderManager().info.getProjectedView().z), n);
    }

    public static void drawBox(AxisAlignedBB axisAlignedBB, int n) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        float[] fArray = ColorUtils.rgba(n);
        GlStateManager.color4f(fArray[0], fArray[1], fArray[2], fArray[3]);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        tessellator.draw();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        tessellator.draw();
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        tessellator.draw();
        GlStateManager.color4f(fArray[0], fArray[1], fArray[2], fArray[3]);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
}

