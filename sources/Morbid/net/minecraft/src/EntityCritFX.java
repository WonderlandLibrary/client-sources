package net.minecraft.src;

public class EntityCritFX extends EntityFX
{
    float field_70561_a;
    
    public EntityCritFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        this(par1World, par2, par4, par6, par8, par10, par12, 1.0f);
    }
    
    public EntityCritFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12, final float par14) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        this.motionX += par8 * 0.4;
        this.motionY += par10 * 0.4;
        this.motionZ += par12 * 0.4;
        final float particleRed = (float)(Math.random() * 0.30000001192092896 + 0.6000000238418579);
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale *= 0.75f;
        this.particleScale *= par14;
        this.field_70561_a = this.particleScale;
        this.particleMaxAge = (int)(6.0 / (Math.random() * 0.8 + 0.6));
        this.particleMaxAge *= (int)par14;
        this.noClip = false;
        this.setParticleTextureIndex(65);
        this.onUpdate();
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        float var8 = (this.particleAge + par2) / this.particleMaxAge * 32.0f;
        if (var8 < 0.0f) {
            var8 = 0.0f;
        }
        if (var8 > 1.0f) {
            var8 = 1.0f;
        }
        this.particleScale = this.field_70561_a * var8;
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
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.particleGreen *= 0.96;
        this.particleBlue *= 0.9;
        this.motionX *= 0.699999988079071;
        this.motionY *= 0.699999988079071;
        this.motionZ *= 0.699999988079071;
        this.motionY -= 0.019999999552965164;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}
