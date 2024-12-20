package net.minecraft.src;

public class EntityEgg extends EntityThrowable
{
    public EntityEgg(final World par1World) {
        super(par1World);
    }
    
    public EntityEgg(final World par1World, final EntityLiving par2EntityLiving) {
        super(par1World, par2EntityLiving);
    }
    
    public EntityEgg(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (par1MovingObjectPosition.entityHit != null) {
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
        }
        if (!this.worldObj.isRemote && this.rand.nextInt(8) == 0) {
            byte var2 = 1;
            if (this.rand.nextInt(32) == 0) {
                var2 = 4;
            }
            for (int var3 = 0; var3 < var2; ++var3) {
                final EntityChicken var4 = new EntityChicken(this.worldObj);
                var4.setGrowingAge(-24000);
                var4.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                this.worldObj.spawnEntityInWorld(var4);
            }
        }
        for (int var5 = 0; var5 < 8; ++var5) {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
        }
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
}
