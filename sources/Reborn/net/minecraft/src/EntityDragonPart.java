package net.minecraft.src;

public class EntityDragonPart extends Entity
{
    public final IEntityMultiPart entityDragonObj;
    public final String name;
    
    public EntityDragonPart(final IEntityMultiPart par1, final String par2, final float par3, final float par4) {
        super(par1.func_82194_d());
        this.setSize(par3, par4);
        this.entityDragonObj = par1;
        this.name = par2;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        return !this.isEntityInvulnerable() && this.entityDragonObj.attackEntityFromPart(this, par1DamageSource, par2);
    }
    
    @Override
    public boolean isEntityEqual(final Entity par1Entity) {
        return this == par1Entity || this.entityDragonObj == par1Entity;
    }
}
