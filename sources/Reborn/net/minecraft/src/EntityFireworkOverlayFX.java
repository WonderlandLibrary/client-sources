package net.minecraft.src;

public class EntityFireworkOverlayFX extends EntityFX
{
    protected EntityFireworkOverlayFX(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
        this.particleMaxAge = 4;
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        final float var8 = 0.25f;
        final float var9 = var8 + 0.25f;
        final float var10 = 0.125f;
        final float var11 = var10 + 0.25f;
        final float var12 = 7.1f * MathHelper.sin((this.particleAge + par2 - 1.0f) * 0.25f * 3.1415927f);
        this.particleAlpha = 0.6f - (this.particleAge + par2 - 1.0f) * 0.25f * 0.5f;
        final float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - EntityFireworkOverlayFX.interpPosX);
        final float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - EntityFireworkOverlayFX.interpPosY);
        final float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - EntityFireworkOverlayFX.interpPosZ);
        par1Tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var9, var11);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var9, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var8, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var8, var11);
    }
}
