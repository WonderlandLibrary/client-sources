package net.minecraft.src;

public class EntityPortalFX extends EntityFX
{
    private float portalParticleScale;
    private double portalPosX;
    private double portalPosY;
    private double portalPosZ;
    
    public EntityPortalFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.posX = par2;
        this.portalPosX = par2;
        this.posY = par4;
        this.portalPosY = par4;
        this.posZ = par6;
        this.portalPosZ = par6;
        final float var14 = this.rand.nextFloat() * 0.6f + 0.4f;
        final float n = this.rand.nextFloat() * 0.2f + 0.5f;
        this.particleScale = n;
        this.portalParticleScale = n;
        final float particleRed = 1.0f * var14;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGreen *= 0.3f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + 40;
        this.noClip = true;
        this.setParticleTextureIndex((int)(Math.random() * 8.0));
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        float var8 = (this.particleAge + par2) / this.particleMaxAge;
        var8 = 1.0f - var8;
        var8 *= var8;
        var8 = 1.0f - var8;
        this.particleScale = this.portalParticleScale * var8;
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
        final int var2 = super.getBrightnessForRender(par1);
        float var3 = this.particleAge / this.particleMaxAge;
        var3 *= var3;
        var3 *= var3;
        final int var4 = var2 & 0xFF;
        int var5 = var2 >> 16 & 0xFF;
        var5 += (int)(var3 * 15.0f * 16.0f);
        if (var5 > 240) {
            var5 = 240;
        }
        return var4 | var5 << 16;
    }
    
    @Override
    public float getBrightness(final float par1) {
        final float var2 = super.getBrightness(par1);
        float var3 = this.particleAge / this.particleMaxAge;
        var3 *= var3 * var3 * var3;
        return var2 * (1.0f - var3) + var3;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final float var2;
        float var1 = var2 = this.particleAge / this.particleMaxAge;
        var1 = -var1 + var1 * var1 * 2.0f;
        var1 = 1.0f - var1;
        this.posX = this.portalPosX + this.motionX * var1;
        this.posY = this.portalPosY + this.motionY * var1 + (1.0f - var2);
        this.posZ = this.portalPosZ + this.motionZ * var1;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }
}
