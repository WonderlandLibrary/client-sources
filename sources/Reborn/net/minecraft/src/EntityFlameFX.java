package net.minecraft.src;

public class EntityFlameFX extends EntityFX
{
    private float flameScale;
    
    public EntityFlameFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.motionX = this.motionX * 0.009999999776482582 + par8;
        this.motionY = this.motionY * 0.009999999776482582 + par10;
        this.motionZ = this.motionZ * 0.009999999776482582 + par12;
        double var10000 = par2 + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        var10000 = par4 + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        var10000 = par6 + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        this.flameScale = this.particleScale;
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
        this.noClip = true;
        this.setParticleTextureIndex(48);
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        final float var8 = (this.particleAge + par2) / this.particleMaxAge;
        this.particleScale = this.flameScale * (1.0f - var8 * var8 * 0.5f);
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
        float var2 = (this.particleAge + par1) / this.particleMaxAge;
        if (var2 < 0.0f) {
            var2 = 0.0f;
        }
        if (var2 > 1.0f) {
            var2 = 1.0f;
        }
        final int var3 = super.getBrightnessForRender(par1);
        int var4 = var3 & 0xFF;
        final int var5 = var3 >> 16 & 0xFF;
        var4 += (int)(var2 * 15.0f * 16.0f);
        if (var4 > 240) {
            var4 = 240;
        }
        return var4 | var5 << 16;
    }
    
    @Override
    public float getBrightness(final float par1) {
        float var2 = (this.particleAge + par1) / this.particleMaxAge;
        if (var2 < 0.0f) {
            var2 = 0.0f;
        }
        if (var2 > 1.0f) {
            var2 = 1.0f;
        }
        final float var3 = super.getBrightness(par1);
        return var3 * var2 + (1.0f - var2);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}
