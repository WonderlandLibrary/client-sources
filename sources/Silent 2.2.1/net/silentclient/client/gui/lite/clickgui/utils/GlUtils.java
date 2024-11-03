package net.silentclient.client.gui.lite.clickgui.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GlUtils {
	public static void scissor(float x, float y, float width, float height) {
        int scaleFactor = getScaleFactor();
        GL11.glScissor((int)(x * scaleFactor), (int)(Minecraft.getMinecraft().displayHeight - (y + height) * scaleFactor), (int)(((x + width) - x) * scaleFactor), (int)(((y + height) - y) * scaleFactor));
    }

    public static int getScaleFactor() {
        int scaleFactor = 1;
        boolean isUnicode = Minecraft.getMinecraft().isUnicode();
        int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
        if (guiScale == 0) {
            guiScale = 1000;
        }

        while (scaleFactor < guiScale && Minecraft.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Minecraft.getMinecraft().displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        if (isUnicode && scaleFactor % 2 != 0 && scaleFactor != 1) {
            --scaleFactor;
        }
        return scaleFactor;
    }

    public static void startScale(float x, float y, float width, float height, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((x + (x + width)) / 2, (y + (y + height)) / 2, 0);
        GlStateManager.scale(scale, scale, 1);
        GlStateManager.translate(-(x + (x + width)) / 2, -(y + (y + height)) / 2, 0);
    }
    
    public static void startScale(float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, 1);
        GlStateManager.translate(-x, -y, 0);
    }

    public static void stopScale() {
        GlStateManager.popMatrix();
    }

    public static void fixEnchantment() {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

	public static void bindTexture(int texture) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		
	}
	
	public static void startTranslate(float x, float y) {
    	GlStateManager.pushMatrix();
    	GlStateManager.translate(x, y, 0);
    }
    
    public static void stopTranslate() {
    	GlStateManager.popMatrix();
    }

}
