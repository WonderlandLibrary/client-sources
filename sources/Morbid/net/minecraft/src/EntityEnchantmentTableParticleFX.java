package net.minecraft.src;

public class EntityEnchantmentTableParticleFX extends EntityFX
{
    private float field_70565_a;
    private double field_70568_aq;
    private double field_70567_ar;
    private double field_70566_as;
    
    public EntityEnchantmentTableParticleFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.posX = par2;
        this.field_70568_aq = par2;
        this.posY = par4;
        this.field_70567_ar = par4;
        this.posZ = par6;
        this.field_70566_as = par6;
        final float var14 = this.rand.nextFloat() * 0.6f + 0.4f;
        final float n = this.rand.nextFloat() * 0.5f + 0.2f;
        this.particleScale = n;
        this.field_70565_a = n;
        final float particleRed = 1.0f * var14;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGreen *= 0.9f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + 30;
        this.noClip = true;
        this.setParticleTextureIndex((int)(Math.random() * 26.0 + 1.0 + 224.0));
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
        var3 *= var3;
        var3 *= var3;
        return var2 * (1.0f - var3) + var3;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float var1 = this.particleAge / this.particleMaxAge;
        var1 = 1.0f - var1;
        float var2 = 1.0f - var1;
        var2 *= var2;
        var2 *= var2;
        this.posX = this.field_70568_aq + this.motionX * var1;
        this.posY = this.field_70567_ar + this.motionY * var1 - var2 * 1.2f;
        this.posZ = this.field_70566_as + this.motionZ * var1;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }
}
