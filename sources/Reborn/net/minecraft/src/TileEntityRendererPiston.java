package net.minecraft.src;

import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class TileEntityRendererPiston extends TileEntitySpecialRenderer
{
    private RenderBlocks blockRenderer;
    
    public void renderPiston(final TileEntityPiston par1TileEntityPiston, final double par2, final double par4, final double par6, final float par8) {
        final Block var9 = Block.blocksList[par1TileEntityPiston.getStoredBlockID()];
        if (var9 != null && par1TileEntityPiston.getProgress(par8) < 1.0f) {
            final Tessellator var10 = Tessellator.instance;
            this.bindTextureByName("/terrain.png");
            RenderHelper.disableStandardItemLighting();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            if (Minecraft.isAmbientOcclusionEnabled()) {
                GL11.glShadeModel(7425);
            }
            else {
                GL11.glShadeModel(7424);
            }
            var10.startDrawingQuads();
            var10.setTranslation((float)par2 - par1TileEntityPiston.xCoord + par1TileEntityPiston.getOffsetX(par8), (float)par4 - par1TileEntityPiston.yCoord + par1TileEntityPiston.getOffsetY(par8), (float)par6 - par1TileEntityPiston.zCoord + par1TileEntityPiston.getOffsetZ(par8));
            var10.setColorOpaque(1, 1, 1);
            if (var9 == Block.pistonExtension && par1TileEntityPiston.getProgress(par8) < 0.5f) {
                this.blockRenderer.renderPistonExtensionAllFaces(var9, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord, false);
            }
            else if (par1TileEntityPiston.shouldRenderHead() && !par1TileEntityPiston.isExtending()) {
                Block.pistonExtension.setHeadTexture(((BlockPistonBase)var9).getPistonExtensionTexture());
                this.blockRenderer.renderPistonExtensionAllFaces(Block.pistonExtension, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord, par1TileEntityPiston.getProgress(par8) < 0.5f);
                Block.pistonExtension.clearHeadTexture();
                var10.setTranslation((float)par2 - par1TileEntityPiston.xCoord, (float)par4 - par1TileEntityPiston.yCoord, (float)par6 - par1TileEntityPiston.zCoord);
                this.blockRenderer.renderPistonBaseAllFaces(var9, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord);
            }
            else {
                this.blockRenderer.renderBlockAllFaces(var9, par1TileEntityPiston.xCoord, par1TileEntityPiston.yCoord, par1TileEntityPiston.zCoord);
            }
            var10.setTranslation(0.0, 0.0, 0.0);
            var10.draw();
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    @Override
    public void onWorldChange(final World par1World) {
        this.blockRenderer = new RenderBlocks(par1World);
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity par1TileEntity, final double par2, final double par4, final double par6, final float par8) {
        this.renderPiston((TileEntityPiston)par1TileEntity, par2, par4, par6, par8);
    }
}
