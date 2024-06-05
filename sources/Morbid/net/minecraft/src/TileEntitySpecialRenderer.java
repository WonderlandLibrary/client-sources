package net.minecraft.src;

import org.lwjgl.opengl.*;

public abstract class TileEntitySpecialRenderer
{
    protected TileEntityRenderer tileEntityRenderer;
    
    public abstract void renderTileEntityAt(final TileEntity p0, final double p1, final double p2, final double p3, final float p4);
    
    protected void bindTextureByName(final String par1Str) {
        final RenderEngine var2 = this.tileEntityRenderer.renderEngine;
        if (var2 != null) {
            var2.bindTexture(par1Str);
        }
    }
    
    protected void bindTextureByURL(final String par1Str, final String par2Str) {
        final RenderEngine var3 = this.tileEntityRenderer.renderEngine;
        if (var3 != null) {
            GL11.glBindTexture(3553, var3.getTextureForDownloadableImage(par1Str, par2Str));
        }
        var3.resetBoundTexture();
    }
    
    public void setTileEntityRenderer(final TileEntityRenderer par1TileEntityRenderer) {
        this.tileEntityRenderer = par1TileEntityRenderer;
    }
    
    public void onWorldChange(final World par1World) {
    }
    
    public FontRenderer getFontRenderer() {
        return this.tileEntityRenderer.getFontRenderer();
    }
}
