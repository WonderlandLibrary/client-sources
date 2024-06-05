package net.minecraft.src;

public class EntityDiggingFX extends EntityFX
{
    private Block blockInstance;
    
    public EntityDiggingFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12, final Block par14Block, final int par15, final int par16, final RenderEngine par17RenderEngine) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.blockInstance = par14Block;
        this.setParticleIcon(par17RenderEngine, par14Block.getIcon(0, par16));
        this.particleGravity = par14Block.blockParticleGravity;
        final float particleRed = 0.6f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale /= 2.0f;
    }
    
    public EntityDiggingFX func_70596_a(final int par1, final int par2, final int par3) {
        if (this.blockInstance == Block.grass) {
            return this;
        }
        final int var4 = this.blockInstance.colorMultiplier(this.worldObj, par1, par2, par3);
        this.particleRed *= (var4 >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (var4 >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (var4 & 0xFF) / 255.0f;
        return this;
    }
    
    public EntityDiggingFX applyRenderColor(final int par1) {
        if (this.blockInstance == Block.grass) {
            return this;
        }
        final int var2 = this.blockInstance.getRenderColor(par1);
        this.particleRed *= (var2 >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (var2 >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (var2 & 0xFF) / 255.0f;
        return this;
    }
    
    @Override
    public int getFXLayer() {
        return 1;
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
        final float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - EntityDiggingFX.interpPosX);
        final float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - EntityDiggingFX.interpPosY);
        final float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - EntityDiggingFX.interpPosZ);
        final float var16 = 1.0f;
        par1Tessellator.setColorOpaque_F(var16 * this.particleRed, var16 * this.particleGreen, var16 * this.particleBlue);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var8, var11);
        par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var8, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var9, var10);
        par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var9, var11);
    }
}
