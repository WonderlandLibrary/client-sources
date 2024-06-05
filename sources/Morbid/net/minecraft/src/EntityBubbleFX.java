package net.minecraft.src;

public class EntityBubbleFX extends EntityFX
{
    public EntityBubbleFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.setParticleTextureIndex(32);
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.2f;
        this.motionX = par8 * 0.20000000298023224 + (float)(Math.random() * 2.0 - 1.0) * 0.02f;
        this.motionY = par10 * 0.20000000298023224 + (float)(Math.random() * 2.0 - 1.0) * 0.02f;
        this.motionZ = par12 * 0.20000000298023224 + (float)(Math.random() * 2.0 - 1.0) * 0.02f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY += 0.002;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.8500000238418579;
        this.motionY *= 0.8500000238418579;
        this.motionZ *= 0.8500000238418579;
        if (this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) != Material.water) {
            this.setDead();
        }
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }
}
