package net.minecraft.src;

import org.lwjgl.opengl.*;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer
{
    private ModelChest theEnderChestModel;
    
    public TileEntityEnderChestRenderer() {
        this.theEnderChestModel = new ModelChest();
    }
    
    public void renderEnderChest(final TileEntityEnderChest par1TileEntityEnderChest, final double par2, final double par4, final double par6, final float par8) {
        int var9 = 0;
        if (par1TileEntityEnderChest.func_70309_m()) {
            var9 = par1TileEntityEnderChest.getBlockMetadata();
        }
        this.bindTextureByName("/item/enderchest.png");
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef((float)par2, (float)par4 + 1.0f, (float)par6 + 1.0f);
        GL11.glScalef(1.0f, -1.0f, -1.0f);
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        short var10 = 0;
        if (var9 == 2) {
            var10 = 180;
        }
        if (var9 == 3) {
            var10 = 0;
        }
        if (var9 == 4) {
            var10 = 90;
        }
        if (var9 == 5) {
            var10 = -90;
        }
        GL11.glRotatef(var10, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        float var11 = par1TileEntityEnderChest.prevLidAngle + (par1TileEntityEnderChest.lidAngle - par1TileEntityEnderChest.prevLidAngle) * par8;
        var11 = 1.0f - var11;
        var11 = 1.0f - var11 * var11 * var11;
        this.theEnderChestModel.chestLid.rotateAngleX = -(var11 * 3.1415927f / 2.0f);
        this.theEnderChestModel.renderAll();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity par1TileEntity, final double par2, final double par4, final double par6, final float par8) {
        this.renderEnderChest((TileEntityEnderChest)par1TileEntity, par2, par4, par6, par8);
    }
}
