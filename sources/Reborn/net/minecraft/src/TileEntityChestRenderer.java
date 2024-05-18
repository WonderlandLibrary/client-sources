package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer
{
    private ModelChest chestModel;
    private ModelChest largeChestModel;
    private boolean isChristmas;
    
    public TileEntityChestRenderer() {
        this.chestModel = new ModelChest();
        this.largeChestModel = new ModelLargeChest();
        final Calendar var1 = Calendar.getInstance();
        if (var1.get(2) + 1 == 12 && var1.get(5) >= 24 && var1.get(5) <= 26) {
            this.isChristmas = true;
        }
    }
    
    public void renderTileEntityChestAt(final TileEntityChest par1TileEntityChest, final double par2, final double par4, final double par6, final float par8) {
        int var9;
        if (!par1TileEntityChest.func_70309_m()) {
            var9 = 0;
        }
        else {
            final Block var10 = par1TileEntityChest.getBlockType();
            var9 = par1TileEntityChest.getBlockMetadata();
            if (var10 instanceof BlockChest && var9 == 0) {
                ((BlockChest)var10).unifyAdjacentChests(par1TileEntityChest.getWorldObj(), par1TileEntityChest.xCoord, par1TileEntityChest.yCoord, par1TileEntityChest.zCoord);
                var9 = par1TileEntityChest.getBlockMetadata();
            }
            par1TileEntityChest.checkForAdjacentChests();
        }
        if (par1TileEntityChest.adjacentChestZNeg == null && par1TileEntityChest.adjacentChestXNeg == null) {
            ModelChest var11;
            if (par1TileEntityChest.adjacentChestXPos == null && par1TileEntityChest.adjacentChestZPosition == null) {
                var11 = this.chestModel;
                if (par1TileEntityChest.func_98041_l() == 1) {
                    this.bindTextureByName("/item/chests/trap_small.png");
                }
                else if (this.isChristmas) {
                    this.bindTextureByName("/item/xmaschest.png");
                }
                else {
                    this.bindTextureByName("/item/chest.png");
                }
            }
            else {
                var11 = this.largeChestModel;
                if (par1TileEntityChest.func_98041_l() == 1) {
                    this.bindTextureByName("/item/chests/trap_large.png");
                }
                else if (this.isChristmas) {
                    this.bindTextureByName("/item/largexmaschest.png");
                }
                else {
                    this.bindTextureByName("/item/largechest.png");
                }
            }
            GL11.glPushMatrix();
            GL11.glEnable(32826);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glTranslatef((float)par2, (float)par4 + 1.0f, (float)par6 + 1.0f);
            GL11.glScalef(1.0f, -1.0f, -1.0f);
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
            short var12 = 0;
            if (var9 == 2) {
                var12 = 180;
            }
            if (var9 == 3) {
                var12 = 0;
            }
            if (var9 == 4) {
                var12 = 90;
            }
            if (var9 == 5) {
                var12 = -90;
            }
            if (var9 == 2 && par1TileEntityChest.adjacentChestXPos != null) {
                GL11.glTranslatef(1.0f, 0.0f, 0.0f);
            }
            if (var9 == 5 && par1TileEntityChest.adjacentChestZPosition != null) {
                GL11.glTranslatef(0.0f, 0.0f, -1.0f);
            }
            GL11.glRotatef(var12, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            float var13 = par1TileEntityChest.prevLidAngle + (par1TileEntityChest.lidAngle - par1TileEntityChest.prevLidAngle) * par8;
            if (par1TileEntityChest.adjacentChestZNeg != null) {
                final float var14 = par1TileEntityChest.adjacentChestZNeg.prevLidAngle + (par1TileEntityChest.adjacentChestZNeg.lidAngle - par1TileEntityChest.adjacentChestZNeg.prevLidAngle) * par8;
                if (var14 > var13) {
                    var13 = var14;
                }
            }
            if (par1TileEntityChest.adjacentChestXNeg != null) {
                final float var14 = par1TileEntityChest.adjacentChestXNeg.prevLidAngle + (par1TileEntityChest.adjacentChestXNeg.lidAngle - par1TileEntityChest.adjacentChestXNeg.prevLidAngle) * par8;
                if (var14 > var13) {
                    var13 = var14;
                }
            }
            var13 = 1.0f - var13;
            var13 = 1.0f - var13 * var13 * var13;
            var11.chestLid.rotateAngleX = -(var13 * 3.1415927f / 2.0f);
            var11.renderAll();
            GL11.glDisable(32826);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity par1TileEntity, final double par2, final double par4, final double par6, final float par8) {
        this.renderTileEntityChestAt((TileEntityChest)par1TileEntity, par2, par4, par6, par8);
    }
}
