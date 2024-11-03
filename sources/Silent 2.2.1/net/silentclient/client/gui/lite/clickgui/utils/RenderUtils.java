package net.silentclient.client.gui.lite.clickgui.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.silentclient.client.utils.ColorUtils;

public class RenderUtils {
	private static float zLevelFloat;
	 public static void drawRect(float x, float y, float width, float height, int color) {
	        float f = (color >> 24 & 0xFF) / 255.0F;
	        float f1 = (color >> 16 & 0xFF) / 255.0F;
	        float f2 =  (color >> 8 & 0xFF) / 255.0F;
	        float f3 = (color & 0xFF) / 255.0F;

	        GL11.glEnable(3042);
	        GL11.glDisable(3553);
	        GL11.glBlendFunc(770, 771);
	        GL11.glEnable(2848);

	        GL11.glPushMatrix();
	        GL11.glColor4f(f1, f2, f3, f);
	        GL11.glBegin(7);
	        GL11.glVertex2d(x + width, y);
	        GL11.glVertex2d(x, y);
	        GL11.glVertex2d(x, y + height);
	        GL11.glVertex2d(x + width, y + height);
	        GL11.glEnd();
	        GL11.glPopMatrix();

	        GL11.glEnable(3553);
	        GL11.glDisable(3042);
	        GL11.glDisable(2848);
	        GlStateManager.enableTexture2D();
	        GlStateManager.disableBlend();
	        GL11.glColor4f(1, 1, 1, 1);
	    }
	 
	 public static void renderMarker(float x, float y, int color) {
	        Tessellator tessellator = Tessellator.getInstance();
	        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

	        GlStateManager.enableBlend();
	        GlStateManager.disableTexture2D();
	        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	        ColorUtils.setColor(color);
	        worldrenderer.begin(6, DefaultVertexFormats.POSITION);
	        worldrenderer.pos((double) x, (double) (y + 4), 0.0D).endVertex();
	        worldrenderer.pos((double) (x + 4), (double) y, 0.0D).endVertex();
	        worldrenderer.pos((double) (x - 4), (double) y, 0.0D).endVertex();
	        tessellator.draw();
	        GlStateManager.enableTexture2D();
	        GlStateManager.disableBlend();
	    }
	 
	 public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height){
	        float f = 0.00390625F;
	        float f1 = 0.00390625F;
	        Tessellator tessellator = Tessellator.getInstance();
	        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
	        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
	        worldrenderer.pos((double)(x + 0), (double)(y + height), (double)RenderUtils.zLevelFloat).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();
	        worldrenderer.pos((double)(x + width), (double)(y + height), (double)RenderUtils.zLevelFloat).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
	        worldrenderer.pos((double)(x + width), (double)(y + 0), (double)RenderUtils.zLevelFloat).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
	        worldrenderer.pos((double)(x + 0), (double)(y + 0), (double)RenderUtils.zLevelFloat).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
	        tessellator.draw();
	    }
}
