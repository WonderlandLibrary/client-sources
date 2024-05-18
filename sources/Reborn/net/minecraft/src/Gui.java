package net.minecraft.src;

import org.lwjgl.opengl.*;

public class Gui
{
    protected float zLevel;
    
    public Gui() {
        this.zLevel = 0.0f;
    }
    
    protected void drawHorizontalLine(int par1, int par2, final int par3, final int par4) {
        if (par2 < par1) {
            final int var5 = par1;
            par1 = par2;
            par2 = var5;
        }
        drawRect(par1, par3, par2 + 1, par3 + 1, par4);
    }
    
    protected void drawVerticalLine(final int par1, int par2, int par3, final int par4) {
        if (par3 < par2) {
            final int var5 = par2;
            par2 = par3;
            par3 = var5;
        }
        drawRect(par1, par2 + 1, par1 + 1, par3, par4);
    }
    
    public static void drawRect(int par0, int par1, int par2, int par3, final int par4) {
        if (par0 < par2) {
            final int var5 = par0;
            par0 = par2;
            par2 = var5;
        }
        if (par1 < par3) {
            final int var5 = par1;
            par1 = par3;
            par3 = var5;
        }
        final float var6 = (par4 >> 24 & 0xFF) / 255.0f;
        final float var7 = (par4 >> 16 & 0xFF) / 255.0f;
        final float var8 = (par4 >> 8 & 0xFF) / 255.0f;
        final float var9 = (par4 & 0xFF) / 255.0f;
        final Tessellator var10 = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(var7, var8, var9, var6);
        var10.startDrawingQuads();
        var10.addVertex(par0, par3, 0.0);
        var10.addVertex(par2, par3, 0.0);
        var10.addVertex(par2, par1, 0.0);
        var10.addVertex(par0, par1, 0.0);
        var10.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    protected void drawGradientRect(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final float var7 = (par5 >> 24 & 0xFF) / 255.0f;
        final float var8 = (par5 >> 16 & 0xFF) / 255.0f;
        final float var9 = (par5 >> 8 & 0xFF) / 255.0f;
        final float var10 = (par5 & 0xFF) / 255.0f;
        final float var11 = (par6 >> 24 & 0xFF) / 255.0f;
        final float var12 = (par6 >> 16 & 0xFF) / 255.0f;
        final float var13 = (par6 >> 8 & 0xFF) / 255.0f;
        final float var14 = (par6 & 0xFF) / 255.0f;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        final Tessellator var15 = Tessellator.instance;
        var15.startDrawingQuads();
        var15.setColorRGBA_F(var8, var9, var10, var7);
        var15.addVertex(par3, par2, this.zLevel);
        var15.addVertex(par1, par2, this.zLevel);
        var15.setColorRGBA_F(var12, var13, var14, var11);
        var15.addVertex(par1, par4, this.zLevel);
        var15.addVertex(par3, par4, this.zLevel);
        var15.draw();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }
    
    public void drawCenteredString(final FontRenderer par1FontRenderer, final String par2Str, final int par3, final int par4, final int par5) {
        par1FontRenderer.drawStringWithShadow(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
    }
    
    public void drawString(final FontRenderer par1FontRenderer, final String par2Str, final int par3, final int par4, final int par5) {
        par1FontRenderer.drawStringWithShadow(par2Str, par3, par4, par5);
    }
    
    public void drawTexturedModalRect(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(par1 + 0, par2 + par6, this.zLevel, (par3 + 0) * var7, (par4 + par6) * var8);
        var9.addVertexWithUV(par1 + par5, par2 + par6, this.zLevel, (par3 + par5) * var7, (par4 + par6) * var8);
        var9.addVertexWithUV(par1 + par5, par2 + 0, this.zLevel, (par3 + par5) * var7, (par4 + 0) * var8);
        var9.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, (par3 + 0) * var7, (par4 + 0) * var8);
        var9.draw();
    }
    
    public void drawTexturedModelRectFromIcon(final int par1, final int par2, final Icon par3Icon, final int par4, final int par5) {
        final Tessellator var6 = Tessellator.instance;
        var6.startDrawingQuads();
        var6.addVertexWithUV(par1 + 0, par2 + par5, this.zLevel, par3Icon.getMinU(), par3Icon.getMaxV());
        var6.addVertexWithUV(par1 + par4, par2 + par5, this.zLevel, par3Icon.getMaxU(), par3Icon.getMaxV());
        var6.addVertexWithUV(par1 + par4, par2 + 0, this.zLevel, par3Icon.getMaxU(), par3Icon.getMinV());
        var6.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, par3Icon.getMinU(), par3Icon.getMinV());
        var6.draw();
    }
}
