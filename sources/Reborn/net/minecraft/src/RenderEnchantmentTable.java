package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderEnchantmentTable extends TileEntitySpecialRenderer
{
    private ModelBook enchantmentBook;
    
    public RenderEnchantmentTable() {
        this.enchantmentBook = new ModelBook();
    }
    
    public void renderTileEntityEnchantmentTableAt(final TileEntityEnchantmentTable par1TileEntityEnchantmentTable, final double par2, final double par4, final double par6, final float par8) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2 + 0.5f, (float)par4 + 0.75f, (float)par6 + 0.5f);
        final float var9 = par1TileEntityEnchantmentTable.tickCount + par8;
        GL11.glTranslatef(0.0f, 0.1f + MathHelper.sin(var9 * 0.1f) * 0.01f, 0.0f);
        float var10;
        for (var10 = par1TileEntityEnchantmentTable.bookRotation2 - par1TileEntityEnchantmentTable.bookRotationPrev; var10 >= 3.1415927f; var10 -= 6.2831855f) {}
        while (var10 < -3.1415927f) {
            var10 += 6.2831855f;
        }
        final float var11 = par1TileEntityEnchantmentTable.bookRotationPrev + var10 * par8;
        GL11.glRotatef(-var11 * 180.0f / 3.1415927f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(80.0f, 0.0f, 0.0f, 1.0f);
        this.bindTextureByName("/item/book.png");
        float var12 = par1TileEntityEnchantmentTable.pageFlipPrev + (par1TileEntityEnchantmentTable.pageFlip - par1TileEntityEnchantmentTable.pageFlipPrev) * par8 + 0.25f;
        float var13 = par1TileEntityEnchantmentTable.pageFlipPrev + (par1TileEntityEnchantmentTable.pageFlip - par1TileEntityEnchantmentTable.pageFlipPrev) * par8 + 0.75f;
        var12 = (var12 - MathHelper.truncateDoubleToInt(var12)) * 1.6f - 0.3f;
        var13 = (var13 - MathHelper.truncateDoubleToInt(var13)) * 1.6f - 0.3f;
        if (var12 < 0.0f) {
            var12 = 0.0f;
        }
        if (var13 < 0.0f) {
            var13 = 0.0f;
        }
        if (var12 > 1.0f) {
            var12 = 1.0f;
        }
        if (var13 > 1.0f) {
            var13 = 1.0f;
        }
        final float var14 = par1TileEntityEnchantmentTable.bookSpreadPrev + (par1TileEntityEnchantmentTable.bookSpread - par1TileEntityEnchantmentTable.bookSpreadPrev) * par8;
        GL11.glEnable(2884);
        this.enchantmentBook.render(null, var9, var12, var13, var14, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity par1TileEntity, final double par2, final double par4, final double par6, final float par8) {
        this.renderTileEntityEnchantmentTableAt((TileEntityEnchantmentTable)par1TileEntity, par2, par4, par6, par8);
    }
}
