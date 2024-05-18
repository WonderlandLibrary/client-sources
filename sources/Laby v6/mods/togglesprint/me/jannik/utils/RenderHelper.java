package mods.togglesprint.me.jannik.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderHelper {
	
	public static ScaledResolution getScaledRes() {
		ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft());
		return scaledRes;
	}
	
	public static int getScaledHeight() {
		return getScaledRes().getScaledHeight();
	}
	
	public static int getScaledWidth() {
		return getScaledRes().getScaledWidth();
	}
	
	public static int toRGB(int r, int g, int b, int a) {
        return (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | (b & 255) << 0;
    }
	
	public static void drawRect(float left, float top, float right, float bottom, int color) {
		if (left < right) {
        	float i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
        	float j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (color >> 24 & 255) / 255.0F;
        float f = (color >> 16 & 255) / 255.0F;
        float f1 = (color >> 8 & 255) / 255.0F;
        float f2 = (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
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
