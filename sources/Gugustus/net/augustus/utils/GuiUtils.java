package net.augustus.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiUtils {

    public static void drawRect1(double x, double y, double width, double height, int color) {
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glColor4f(f1, f2, f3, f);
        Gui.drawRect((int)x, (int)y, (int)(x + width), (int)(y + height), color);
    }

    public static boolean isHovering(int mouseX, int mouseY, int posX, int posY, int width, int height) {
        return (mouseX >= posX && mouseX < posX + width && mouseY >= posY && mouseY < posY + height);
    }

    public static void glResets() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
    }

    public static void drawStraightLine(int startPos, int endPos, int yPos, int thickness, int color) {
        Gui.drawRect(startPos, yPos + thickness, endPos, yPos, color);
    }

    public static void glStartFontRenderer() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
    }

    public static void drawImage(ResourceLocation resourceLocation, float x, float y, float width, float height, int rgba) {
        float a = (rgba >> 24 & 0xFF) / 255.0F;
        float r = (rgba >> 16 & 0xFF) / 255.0F;
        float g = (rgba >> 8 & 0xFF) / 255.0F;
        float b = (rgba & 0xFF) / 255.0F;
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GlStateManager.color(r, g, b, a);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        Gui.drawScaledCustomSizeModalRect((int)x, (int)y, 0.0F, 0.0F, (int)width, (int)height, (int)width, (int)height, width, height);
        GL11.glPopMatrix();
    }


    public static void drawRect(double left, double top, double right, double bottom, float opacity) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(0.1F, 0.1F, 0.1F, opacity);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
