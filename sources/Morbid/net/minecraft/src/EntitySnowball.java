package net.minecraft.src;

public class EntitySnowball extends EntityThrowable
{
    public EntitySnowball(final World par1World) {
        super(par1World);
    }
    
    public EntitySnowball(final World par1World, final EntityLiving par2EntityLiving) {
        super(par1World, par2EntityLiving);
    }
    
    public EntitySnowball(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (par1MovingObjectPosition.entityHit != null) {
            byte var2 = 0;
            if (par1MovingObjectPosition.entityHit instanceof EntityBlaze) {
                var2 = 3;
            }
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), var2);
        }
        for (int var3 = 0; var3 < 8; ++var3) {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
        }
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
}
