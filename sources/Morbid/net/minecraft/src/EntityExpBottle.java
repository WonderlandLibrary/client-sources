package net.minecraft.src;

public class EntityExpBottle extends EntityThrowable
{
    public EntityExpBottle(final World par1World) {
        super(par1World);
    }
    
    public EntityExpBottle(final World par1World, final EntityLiving par2EntityLiving) {
        super(par1World, par2EntityLiving);
    }
    
    public EntityExpBottle(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
    }
    
    @Override
    protected float getGravityVelocity() {
        return 0.07f;
    }
    
    @Override
    protected float func_70182_d() {
        return 0.7f;
    }
    
    @Override
    protected float func_70183_g() {
        return -20.0f;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (!this.worldObj.isRemote) {
            this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
            int var2 = 3 + this.worldObj.rand.nextInt(5) + this.worldObj.rand.nextInt(5);
            while (var2 > 0) {
                final int var3 = EntityXPOrb.getXPSplit(var2);
                var2 -= var3;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var3));
            }
            this.setDead();
        }
    }
}
