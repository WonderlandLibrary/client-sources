package net.minecraft.src;

public abstract class EntityWaterMob extends EntityCreature implements IAnimals
{
    public EntityWaterMob(final World par1World) {
        super(par1World);
    }
    
    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.checkNoEntityCollision(this.boundingBox);
    }
    
    @Override
    public int getTalkInterval() {
        return 120;
    }
    
    @Override
    protected boolean canDespawn() {
        return true;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer par1EntityPlayer) {
        return 1 + this.worldObj.rand.nextInt(3);
    }
    
    @Override
    public void onEntityUpdate() {
        int var1 = this.getAir();
        super.onEntityUpdate();
        if (this.isEntityAlive() && !this.isInsideOfMaterial(Material.water)) {
            --var1;
            this.setAir(var1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.drown, 2);
            }
        }
        else {
            this.setAir(300);
        }
    }
}
