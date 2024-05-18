package net.minecraft.src;

import org.lwjgl.opengl.*;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer
{
    private ModelSign modelSign;
    
    public TileEntitySignRenderer() {
        this.modelSign = new ModelSign();
    }
    
    public void renderTileEntitySignAt(final TileEntitySign par1TileEntitySign, final double par2, final double par4, final double par6, final float par8) {
        final Block var9 = par1TileEntitySign.getBlockType();
        GL11.glPushMatrix();
        final float var10 = 0.6666667f;
        if (var9 == Block.signPost) {
            GL11.glTranslatef((float)par2 + 0.5f, (float)par4 + 0.75f * var10, (float)par6 + 0.5f);
            final float var11 = par1TileEntitySign.getBlockMetadata() * 360 / 16.0f;
            GL11.glRotatef(-var11, 0.0f, 1.0f, 0.0f);
            this.modelSign.signStick.showModel = true;
        }
        else {
            final int var12 = par1TileEntitySign.getBlockMetadata();
            float var13 = 0.0f;
            if (var12 == 2) {
                var13 = 180.0f;
            }
            if (var12 == 4) {
                var13 = 90.0f;
            }
            if (var12 == 5) {
                var13 = -90.0f;
            }
            GL11.glTranslatef((float)par2 + 0.5f, (float)par4 + 0.75f * var10, (float)par6 + 0.5f);
            GL11.glRotatef(-var13, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.3125f, -0.4375f);
            this.modelSign.signStick.showModel = false;
        }
        this.bindTextureByName("/item/sign.png");
        GL11.glPushMatrix();
        GL11.glScalef(var10, -var10, -var10);
        this.modelSign.renderSign();
        GL11.glPopMatrix();
        final FontRenderer var14 = this.getFontRenderer();
        float var13 = 0.016666668f * var10;
        GL11.glTranslatef(0.0f, 0.5f * var10, 0.07f * var10);
        GL11.glScalef(var13, -var13, var13);
        GL11.glNormal3f(0.0f, 0.0f, -1.0f * var13);
        GL11.glDepthMask(false);
        final byte var15 = 0;
        for (int var16 = 0; var16 < par1TileEntitySign.signText.length; ++var16) {
            String var17 = par1TileEntitySign.signText[var16];
            if (var16 == par1TileEntitySign.lineBeingEdited) {
                var17 = "> " + var17 + " <";
                var14.drawString(var17, -var14.getStringWidth(var17) / 2, var16 * 10 - par1TileEntitySign.signText.length * 5, var15);
            }
            else {
                var14.drawString(var17, -var14.getStringWidth(var17) / 2, var16 * 10 - par1TileEntitySign.signText.length * 5, var15);
            }
        }
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity par1TileEntity, final double par2, final double par4, final double par6, final float par8) {
        this.renderTileEntitySignAt((TileEntitySign)par1TileEntity, par2, par4, par6, par8);
    }
}
