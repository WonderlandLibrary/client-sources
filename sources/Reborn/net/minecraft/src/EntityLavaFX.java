package net.minecraft.src;

public class EntityLavaFX extends EntityFX
{
    private float lavaParticleScale;
    
    public EntityLavaFX(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.motionX *= 0.800000011920929;
        this.motionY *= 0.800000011920929;
        this.motionZ *= 0.800000011920929;
        this.motionY = this.rand.nextFloat() * 0.4f + 0.05f;
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale *= this.rand.nextFloat() * 2.0f + 0.2f;
        this.lavaParticleScale = this.particleScale;
        this.particleMaxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.noClip = false;
        this.setParticleTextureIndex(49);
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
        final short var4 = 240;
        final int var5 = var3 >> 16 & 0xFF;
        return var4 | var5 << 16;
    }
    
    @Override
    public float getBrightness(final float par1) {
        return 1.0f;
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        final float var8 = (this.particleAge + par2) / this.particleMaxAge;
        this.particleScale = this.lavaParticleScale * (1.0f - var8 * var8);
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        final float var1 = this.particleAge / this.particleMaxAge;
        if (this.rand.nextFloat() > var1) {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
        }
        this.motionY -= 0.03;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9990000128746033;
        this.motionY *= 0.9990000128746033;
        this.motionZ *= 0.9990000128746033;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}
