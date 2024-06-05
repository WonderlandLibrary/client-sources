package net.minecraft.src;

public class EntityAITargetNonTamed extends EntityAINearestAttackableTarget
{
    private EntityTameable theTameable;
    
    public EntityAITargetNonTamed(final EntityTameable par1EntityTameable, final Class par2Class, final float par3, final int par4, final boolean par5) {
        super(par1EntityTameable, par2Class, par3, par4, par5);
        this.theTameable = par1EntityTameable;
    }
    
    @Override
    public boolean shouldExecute() {
        return !this.theTameable.isTamed() && super.shouldExecute();
    }
}
