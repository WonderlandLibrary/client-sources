package net.minecraft.src;

public class EntityDropParticleFX extends EntityFX
{
    private Material materialType;
    private int bobTimer;
    
    public EntityDropParticleFX(final World par1World, final double par2, final double par4, final double par6, final Material par8Material) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        if (par8Material == Material.water) {
            this.particleRed = 0.0f;
            this.particleGreen = 0.0f;
            this.particleBlue = 1.0f;
        }
        else {
            this.particleRed = 1.0f;
            this.particleGreen = 0.0f;
            this.particleBlue = 0.0f;
        }
        this.setParticleTextureIndex(113);
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.materialType = par8Material;
        this.bobTimer = 40;
        this.particleMaxAge = (int)(64.0 / (Math.random() * 0.8 + 0.2));
        final double motionX2 = 0.0;
        this.motionZ = motionX2;
        this.motionY = motionX2;
        this.motionX = motionX2;
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
        return (this.materialType == Material.water) ? super.getBrightnessForRender(par1) : 257;
    }
    
    @Override
    public float getBrightness(final float par1) {
        return (this.materialType == Material.water) ? super.getBrightness(par1) : 1.0f;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.materialType == Material.water) {
            this.particleRed = 0.2f;
            this.particleGreen = 0.3f;
            this.particleBlue = 1.0f;
        }
        else {
            this.particleRed = 1.0f;
            this.particleGreen = 16.0f / (40 - this.bobTimer + 16);
            this.particleBlue = 4.0f / (40 - this.bobTimer + 8);
        }
        this.motionY -= this.particleGravity;
        if (this.bobTimer-- > 0) {
            this.motionX *= 0.02;
            this.motionY *= 0.02;
            this.motionZ *= 0.02;
            this.setParticleTextureIndex(113);
        }
        else {
            this.setParticleTextureIndex(112);
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
        if (this.onGround) {
            if (this.materialType == Material.water) {
                this.setDead();
                this.worldObj.spawnParticle("splash", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            }
            else {
                this.setParticleTextureIndex(114);
            }
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        final Material var1 = this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
        if (var1.isLiquid() || var1.isSolid()) {
            final double var2 = MathHelper.floor_double(this.posY) + 1 - BlockFluid.getFluidHeightPercent(this.worldObj.getBlockMetadata(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
            if (this.posY < var2) {
                this.setDead();
            }
        }
    }
}
