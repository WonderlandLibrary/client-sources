package net.minecraft.src;

public abstract class EntityGolem extends EntityCreature implements IAnimals
{
    public EntityGolem(final World par1World) {
        super(par1World);
    }
    
    @Override
    protected void fall(final float par1) {
    }
    
    @Override
    protected String getLivingSound() {
        return "none";
    }
    
    @Override
    protected String getHurtSound() {
        return "none";
    }
    
    @Override
    protected String getDeathSound() {
        return "none";
    }
    
    @Override
    public int getTalkInterval() {
        return 120;
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
}
