package net.labymod.spm;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class DrawUtils
{
    public static void drawBackground(int left, int top, int right, int bottom)
    {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        int i = 0;
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos((double)left, (double)bottom, 0.0D).tex((double)((float)left / f), (double)((float)(bottom + i) / f)).color(32, 32, 32, 255).endVertex();
        worldrenderer.pos((double)right, (double)bottom, 0.0D).tex((double)((float)right / f), (double)((float)(bottom + i) / f)).color(32, 32, 32, 255).endVertex();
        worldrenderer.pos((double)right, (double)top, 0.0D).tex((double)((float)right / f), (double)((float)(top + i) / f)).color(32, 32, 32, 255).endVertex();
        worldrenderer.pos((double)left, (double)top, 0.0D).tex((double)((float)left / f), (double)((float)(top + i) / f)).color(32, 32, 32, 255).endVertex();
        tessellator.draw();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        int j = 4;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.enableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos((double)left, (double)(top + j), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos((double)right, (double)(top + j), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos((double)right, (double)top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos((double)left, (double)top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        tessellator.draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos((double)left, (double)bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos((double)right, (double)bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos((double)right, (double)(bottom - j), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos((double)left, (double)(bottom - j), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
    }

    public static void overlayBackground(int startX, int startY, int endX, int endY)
    {
        int i = 255;
        int j = 255;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos((double)startX, (double)endY, 0.0D).tex(0.0D, (double)((float)endY / 32.0F)).color(64, 64, 64, i).endVertex();
        worldrenderer.pos((double)(startX + endX), (double)endY, 0.0D).tex((double)((float)endX / 32.0F), (double)((float)endY / 32.0F)).color(64, 64, 64, i).endVertex();
        worldrenderer.pos((double)(startX + endX), (double)startY, 0.0D).tex((double)((float)endX / 32.0F), (double)((float)startY / 32.0F)).color(64, 64, 64, j).endVertex();
        worldrenderer.pos((double)startX, (double)startY, 0.0D).tex(0.0D, (double)((float)startY / 32.0F)).color(64, 64, 64, j).endVertex();
        tessellator.draw();
    }
}
