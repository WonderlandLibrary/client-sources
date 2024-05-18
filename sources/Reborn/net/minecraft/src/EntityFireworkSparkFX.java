package net.minecraft.src;

public class EntityFireworkSparkFX extends EntityFX
{
    private int field_92049_a;
    private boolean field_92054_ax;
    private boolean field_92048_ay;
    private final EffectRenderer field_92047_az;
    private float field_92050_aA;
    private float field_92051_aB;
    private float field_92052_aC;
    private boolean field_92053_aD;
    
    public EntityFireworkSparkFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12, final EffectRenderer par14EffectRenderer) {
        super(par1World, par2, par4, par6);
        this.field_92049_a = 160;
        this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.field_92047_az = par14EffectRenderer;
        this.particleScale *= 0.75f;
        this.particleMaxAge = 48 + this.rand.nextInt(12);
        this.noClip = false;
    }
    
    public void func_92045_e(final boolean par1) {
        this.field_92054_ax = par1;
    }
    
    public void func_92043_f(final boolean par1) {
        this.field_92048_ay = par1;
    }
    
    public void func_92044_a(final int par1) {
        final float var2 = ((par1 & 0xFF0000) >> 16) / 255.0f;
        final float var3 = ((par1 & 0xFF00) >> 8) / 255.0f;
        final float var4 = ((par1 & 0xFF) >> 0) / 255.0f;
        final float var5 = 1.0f;
        this.setRBGColorF(var2 * var5, var3 * var5, var4 * var5);
    }
    
    public void func_92046_g(final int par1) {
        this.field_92050_aA = ((par1 & 0xFF0000) >> 16) / 255.0f;
        this.field_92051_aB = ((par1 & 0xFF00) >> 8) / 255.0f;
        this.field_92052_aC = ((par1 & 0xFF) >> 0) / 255.0f;
        this.field_92053_aD = true;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    @Override
    public boolean canBePushed() {
        return false;
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        if (!this.field_92048_ay || this.particleAge < this.particleMaxAge / 3 || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0) {
            super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
        }
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        if (this.particleAge > this.particleMaxAge / 2) {
            this.setAlphaF(1.0f - (this.particleAge - this.particleMaxAge / 2) / this.particleMaxAge);
            if (this.field_92053_aD) {
                this.particleRed += (this.field_92050_aA - this.particleRed) * 0.2f;
                this.particleGreen += (this.field_92051_aB - this.particleGreen) * 0.2f;
                this.particleBlue += (this.field_92052_aC - this.particleBlue) * 0.2f;
            }
        }
        this.setParticleTextureIndex(this.field_92049_a + (7 - this.particleAge * 8 / this.particleMaxAge));
        this.motionY -= 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9100000262260437;
        this.motionY *= 0.9100000262260437;
        this.motionZ *= 0.9100000262260437;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        if (this.field_92054_ax && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0) {
            final EntityFireworkSparkFX var1 = new EntityFireworkSparkFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, this.field_92047_az);
            var1.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
            var1.particleAge = var1.particleMaxAge / 2;
            if (this.field_92053_aD) {
                var1.field_92053_aD = true;
                var1.field_92050_aA = this.field_92050_aA;
                var1.field_92051_aB = this.field_92051_aB;
                var1.field_92052_aC = this.field_92052_aC;
            }
            var1.field_92048_ay = this.field_92048_ay;
            this.field_92047_az.addEffect(var1);
        }
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
        return 15728880;
    }
    
    @Override
    public float getBrightness(final float par1) {
        return 1.0f;
    }
}
