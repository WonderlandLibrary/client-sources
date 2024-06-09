package us.loki.legit.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

public class DrawUtils extends Gui{

	public void drawTexturedModalRectUV(double x, double y, double textureX, double textureY, double width, double height)
    {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.getInstance();
		WorldRenderer var10 = var9.getWorldRenderer();
		var10.startDrawingQuads();
		var10.addVertexWithUV((double) (x + 0), (double) (y + height), (double) zLevel,
				(double) ((float) (textureX + 0) * var7), (double) ((float) (textureY + height) * var8));
		var10.addVertexWithUV((double) (x + width), (double) (y + height), (double) zLevel,
				(double) ((float) (textureX + width) * var7), (double) ((float) (textureY + height) * var8));
		var10.addVertexWithUV((double) (x + width), (double) (y + 0), (double) zLevel,
				(double) ((float) (textureX + width) * var7), (double) ((float) (textureY + 0) * var8));
		var10.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) zLevel,
				(double) ((float) (textureX + 0) * var7), (double) ((float) (textureY + 0) * var8));
		var9.draw();
    }

	public void drawTexturedModalRect(double x, double y, double textureX, double textureY, double width, double height)
    {
        this.drawTexturedModalRect((int)x, (int)y, (int)textureX, (int)textureY, (int)width, (int)height);
    }
	public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel)
    {
    		float var7 = 0.00390625F;
    		float var8 = 0.00390625F;
    		Tessellator var9 = Tessellator.getInstance();
    		WorldRenderer var10 = var9.getWorldRenderer();
    		var10.startDrawingQuads();
    		var10.addVertexWithUV((double) (x + 0), (double) (y + height), (double) zLevel,
    				(double) ((float) (textureX + 0) * var7), (double) ((float) (textureY + height) * var8));
    		var10.addVertexWithUV((double) (x + width), (double) (y + height), (double) zLevel,
    				(double) ((float) (textureX + width) * var7), (double) ((float) (textureY + height) * var8));
    		var10.addVertexWithUV((double) (x + width), (double) (y + 0), (double) zLevel,
    				(double) ((float) (textureX + width) * var7), (double) ((float) (textureY + 0) * var8));
    		var10.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) zLevel,
    				(double) ((float) (textureX + 0) * var7), (double) ((float) (textureY + 0) * var8));
    		var9.draw();
    	}
	public void drawTexturedModalRectFixed(double x, double y, double imageWidth, double imageHeight, double maxWidth, double maxHeight)
    {
        GL11.glPushMatrix();
        double d0 = maxWidth / imageWidth;
        double d1 = maxHeight / imageHeight;
        GL11.glScaled(d0, d1, 0.0D);
        this.drawTexturedModalRect(x / d0, y / d1, x / d0 + imageWidth, y / d1 + imageHeight, d1, d1);
        GL11.glPopMatrix();
    }

    public void drawTexturedModalRectFixed(double x, double y, double texturePosX, double texturePosY, double imageWidth, double imageHeight, double maxWidth, double maxHeight)
    {
        GL11.glPushMatrix();
        double d0 = maxWidth / imageWidth;
        double d1 = maxHeight / imageHeight;
        GL11.glScaled(d0, d1, 0.0D);
        this.drawTexturedModalRectUV(x / d0, y / d1, texturePosX, texturePosY, x / d0 + imageWidth - x / d0, y / d1 + imageHeight - y / d1);
        GL11.glPopMatrix();
    }
    public static void rect(float x1, float y1, float x2, float y2, int fill) {
		GlStateManager.color(0, 0, 0);
		GL11.glColor4f(0, 0, 0, 0);
		
		float var5;

		if (x1 < x2) {
			var5 = x1;
			x1 = x2;
			x2 = var5;
		}
		if (y1 < y2) {
			var5 = y1;
			y1 = y2;
			y2 = var5;
		}

        float var11 = (float)(fill >> 24 & 255) / 255.0F;
        float var6 = (float)(fill >> 16 & 255) / 255.0F;
        float var7 = (float)(fill >> 8 & 255) / 255.0F;
        float var8 = (float)(fill & 255) / 255.0F;
        
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);
        
        var10.startDrawingQuads();
        var10.addVertex(x1, y2, 0.0D);
        var10.addVertex(x2, y2, 0.0D);
        var10.addVertex(x2, y1, 0.0D);
        var10.addVertex(x1, y1, 0.0D);
        var9.draw();
        
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
	}
	
	public static void rectBorder(float x1, float y1, float x2, float y2, int outline) {
		rect(x1, y2-1, x2, y2, outline);
		rect(x1, y1, x2, y1+1, outline);
		rect(x1, y1, x1+1, y2, outline);
		rect(x2-1, y1, x2, y2, outline);
	}

}
