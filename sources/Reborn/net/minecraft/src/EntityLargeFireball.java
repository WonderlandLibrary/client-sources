package net.minecraft.src;

public class EntityLargeFireball extends EntityFireball
{
    public int field_92057_e;
    
    public EntityLargeFireball(final World par1World) {
        super(par1World);
        this.field_92057_e = 1;
    }
    
    public EntityLargeFireball(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.field_92057_e = 1;
    }
    
    public EntityLargeFireball(final World par1World, final EntityLiving par2EntityLiving, final double par3, final double par5, final double par7) {
        super(par1World, par2EntityLiving, par3, par5, par7);
        this.field_92057_e = 1;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (par1MovingObjectPosition.entityHit != null) {
                par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6);
            }
            this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, this.field_92057_e, true, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            this.setDead();
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("ExplosionPower", this.field_92057_e);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.hasKey("ExplosionPower")) {
            this.field_92057_e = par1NBTTagCompound.getInteger("ExplosionPower");
        }
    }
}
