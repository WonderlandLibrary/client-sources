package me.gishreload.yukon.gui;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
 
public class WatermarkRenderer
{
    public static ResourceLocation watermark = null;
    public static SmoothColorProvider colorProvider = new SmoothColorProvider(1);
   
    public WatermarkRenderer() {
        super();
    }
   
    public static void render(final ScaledResolution resolution) {
            GL11.glPushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final int r = WatermarkRenderer.colorProvider.getR();
            final int g = WatermarkRenderer.colorProvider.getG();
            final int b = WatermarkRenderer.colorProvider.getB();
            final int a = 255;
            final int width = Minecraft.fontRendererObj.getStringWidth("Edictum") * 2;
            final Tessellator tessellator = Tessellator.getInstance();
            final VertexBuffer worldrenderer = tessellator.getBuffer();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos(0.0, 16.0, 0.0).tex(0.0, 1.0).color(r, g, b, a).endVertex();
            worldrenderer.pos(width, 16.0, 0.0).tex(1.0, 1.0).color(r, g, b, a).endVertex();
            worldrenderer.pos(width, 0.0, 0.0).tex(1.0, 0.0).color(r, g, b, a).endVertex();
            worldrenderer.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(r, g, b, a).endVertex();
            tessellator.draw();
            GL11.glPopMatrix();
    }
}
