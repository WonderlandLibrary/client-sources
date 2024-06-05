package net.minecraft.src;

public class EntityBreakingFX extends EntityFX
{
    public EntityBreakingFX(final World par1World, final double par2, final double par4, final double par6, final Item par8Item, final RenderEngine par9RenderEngine) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.setParticleIcon(par9RenderEngine, par8Item.getIconFromDamage(0));
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGravity = Block.blockSnow.blockParticleGravity;
        this.particleScale /= 2.0f;
    }
    
    public EntityBreakingFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12, final Item par14Item, final RenderEngine par15RenderEngine) {
        this(par1World, par2, par4, par6, par14Item, par15RenderEngine);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        this.motionX += par8;
        this.motionY += par10;
        this.motionZ += par12;
    }
    
    @Override
    public int getFXLayer() {
        return 2;
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        float var8 = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0f) / 16.0f;
        float var9 = var8 + 0.015609375f;
        float var10 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0f) / 16.0f;
        float var11 = var10 + 0.015609375f;
        final float var12 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            var8 = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0f * 16.0f);
            var9 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0f) / 4.0f * 16.0f);
            var10 = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0f * 16.0f);
            var11 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0f) / 4.0f * 16.0f);
        }
        final float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - EntityBreakingFX.interpPosX);
        final float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - EntityBreakingFX.interpPosY);
        final float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - EntityBreakingFX.interpPosZ);
        final float var16 = 1.0f;
        par1Tessellator.setColorOpaque_F(var16 * this.particleRed, var16 * this.particleGreen, var16 * this.particleBlue);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var8, var11);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var8, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var9, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var9, var11);
    }
}
