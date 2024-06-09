package net.minecraft.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class Gui
{
    public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
    public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
    protected float zLevel;

    /**
     * Draw a 1 pixel wide horizontal line. Args: x1, x2, y, color
     */
    protected void drawHorizontalLine(int startX, int endX, int y, int color)
    {
        if (endX < startX)
        {
            int var5 = startX;
            startX = endX;
            endX = var5;
        }

        drawRect(startX, y, endX + 1, y + 1, color);
    }

    /**
     * Draw a 1 pixel wide vertical line. Args : x, y1, y2, color
     */
    protected void drawVerticalLine(int x, int startY, int endY, int color)
    {
        if (endY < startY)
        {
            int var5 = startY;
            startY = endY;
            endY = var5;
        }

        drawRect(x, startY + 1, x + 1, endY, color);
    }
    
    public static void disableGL2D()
    {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
    }
    
    
    

    public static void enableGL2D()
    {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
    }
    
    public static void drawFullCircle(double d, double e, double r, int c)
    {
      r *= 2.0D;
      d *= 2.0D;
      e *= 2.0D;
      float f = (c >> 24 & 0xFF) / 255.0F;
      float f2 = (c >> 16 & 0xFF) / 255.0F;
      float f3 = (c >> 8 & 0xFF) / 255.0F;
      float f4 = (c & 0xFF) / 255.0F;
      enableGL2D();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glColor4f(f2, f3, f4, f);
      GL11.glBegin(6);
      for (int i = 0; i <= 360; i++)
      {
        double x = Math.sin(i * 3.141592653589793D / 180.0D) * r;
        double y = Math.cos(i * 3.141592653589793D / 180.0D) * r;
        GL11.glVertex2d(d + x, e + y);
      }
      GL11.glEnd();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */
    public static void drawRect(double d, double e, double g, double h, int col1)
	   {
	     float f = (col1 >> 24 & 0xFF) / 255.0F;
	     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
	     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
	     float f3 = (col1 & 0xFF) / 255.0F;
	     
	     GL11.glEnable(3042);
	     GL11.glDisable(3553);
	     GL11.glBlendFunc(770, 771);
	     GL11.glEnable(2848);
	     
	     GL11.glPushMatrix();
	     GL11.glColor4f(f1, f2, f3, f);
	     GL11.glBegin(7);
	     GL11.glVertex2d(g, e);
	     GL11.glVertex2d(d, e);
	     GL11.glVertex2d(d, h);
	     GL11.glVertex2d(g, h);
	     GL11.glEnd();
	     GL11.glPopMatrix();
	     
	     GL11.glEnable(3553);
	     GL11.glDisable(3042);
	     GL11.glDisable(2848);
	   }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors (ARGB format). Args : x1, y1, x2, y2,
     * topColor, bottomColor
     */
    protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {
        float var7 = (float)(startColor >> 24 & 255) / 255.0F;
        float var8 = (float)(startColor >> 16 & 255) / 255.0F;
        float var9 = (float)(startColor >> 8 & 255) / 255.0F;
        float var10 = (float)(startColor & 255) / 255.0F;
        float var11 = (float)(endColor >> 24 & 255) / 255.0F;
        float var12 = (float)(endColor >> 16 & 255) / 255.0F;
        float var13 = (float)(endColor >> 8 & 255) / 255.0F;
        float var14 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator var15 = Tessellator.getInstance();
        WorldRenderer var16 = var15.getWorldRenderer();
        var16.startDrawingQuads();
        var16.setColorRGBA_F(var8, var9, var10, var7);
        var16.addVertex((double)right, (double)top, (double)this.zLevel);
        var16.addVertex((double)left, (double)top, (double)this.zLevel);
        var16.setColorRGBA_F(var12, var13, var14, var11);
        var16.addVertex((double)left, (double)bottom, (double)this.zLevel);
        var16.addVertex((double)right, (double)bottom, (double)this.zLevel);
        var15.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    

    /**
     * Renders the specified text to the screen, center-aligned. Args : renderer, string, x, y, color
     */
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
    }

    /**
     * Renders the specified text to the screen. Args : renderer, string, x, y, color
     */
    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, (float)x, (float)y, color);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV((double)(x + 0), (double)(y + height), (double)this.zLevel, (double)((float)(textureX + 0) * var7), (double)((float)(textureY + height) * var8));
        var10.addVertexWithUV((double)(x + width), (double)(y + height), (double)this.zLevel, (double)((float)(textureX + width) * var7), (double)((float)(textureY + height) * var8));
        var10.addVertexWithUV((double)(x + width), (double)(y + 0), (double)this.zLevel, (double)((float)(textureX + width) * var7), (double)((float)(textureY + 0) * var8));
        var10.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((float)(textureX + 0) * var7), (double)((float)(textureY + 0) * var8));
        var9.draw();
    }

    /**
     * Draws a textured rectangle using the texture currently bound to the TextureManager
     */
    public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV)
    {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV((double)(xCoord + 0.0F), (double)(yCoord + (float)maxV), (double)this.zLevel, (double)((float)(minU + 0) * var7), (double)((float)(minV + maxV) * var8));
        var10.addVertexWithUV((double)(xCoord + (float)maxU), (double)(yCoord + (float)maxV), (double)this.zLevel, (double)((float)(minU + maxU) * var7), (double)((float)(minV + maxV) * var8));
        var10.addVertexWithUV((double)(xCoord + (float)maxU), (double)(yCoord + 0.0F), (double)this.zLevel, (double)((float)(minU + maxU) * var7), (double)((float)(minV + 0) * var8));
        var10.addVertexWithUV((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), (double)this.zLevel, (double)((float)(minU + 0) * var7), (double)((float)(minV + 0) * var8));
        var9.draw();
    }

    /**
     * Draws a texture rectangle using the texture currently bound to the TextureManager
     */
    public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn)
    {
        Tessellator var6 = Tessellator.getInstance();
        WorldRenderer var7 = var6.getWorldRenderer();
        var7.startDrawingQuads();
        var7.addVertexWithUV((double)(xCoord + 0), (double)(yCoord + heightIn), (double)this.zLevel, (double)textureSprite.getMinU(), (double)textureSprite.getMaxV());
        var7.addVertexWithUV((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)this.zLevel, (double)textureSprite.getMaxU(), (double)textureSprite.getMaxV());
        var7.addVertexWithUV((double)(xCoord + widthIn), (double)(yCoord + 0), (double)this.zLevel, (double)textureSprite.getMaxU(), (double)textureSprite.getMinV());
        var7.addVertexWithUV((double)(xCoord + 0), (double)(yCoord + 0), (double)this.zLevel, (double)textureSprite.getMinU(), (double)textureSprite.getMinV());
        var6.draw();
    }

    /**
     * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
     */
    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight)
    {
        float var8 = 1.0F / textureWidth;
        float var9 = 1.0F / textureHeight;
        Tessellator var10 = Tessellator.getInstance();
        WorldRenderer var11 = var10.getWorldRenderer();
        var11.startDrawingQuads();
        var11.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * var8), (double)((v + (float)height) * var9));
        var11.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)width) * var8), (double)((v + (float)height) * var9));
        var11.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)width) * var8), (double)(v * var9));
        var11.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * var8), (double)(v * var9));
        var10.draw();
    }

    /**
     * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't used anywhere in vanilla code.
     */
    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight)
    {
        float var10 = 1.0F / tileWidth;
        float var11 = 1.0F / tileHeight;
        Tessellator var12 = Tessellator.getInstance();
        WorldRenderer var13 = var12.getWorldRenderer();
        var13.startDrawingQuads();
        var13.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * var10), (double)((v + (float)vHeight) * var11));
        var13.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)uWidth) * var10), (double)((v + (float)vHeight) * var11));
        var13.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)uWidth) * var10), (double)(v * var11));
        var13.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * var10), (double)(v * var11));
        var12.draw();
    }
}
