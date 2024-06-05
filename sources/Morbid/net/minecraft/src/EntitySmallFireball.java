package net.minecraft.src;

public class EntitySmallFireball extends EntityFireball
{
    public EntitySmallFireball(final World par1World) {
        super(par1World);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public EntitySmallFireball(final World par1World, final EntityLiving par2EntityLiving, final double par3, final double par5, final double par7) {
        super(par1World, par2EntityLiving, par3, par5, par7);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public EntitySmallFireball(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (par1MovingObjectPosition.entityHit != null) {
                if (!par1MovingObjectPosition.entityHit.isImmuneToFire() && par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 5)) {
                    par1MovingObjectPosition.entityHit.setFire(5);
                }
            }
            else {
                int var2 = par1MovingObjectPosition.blockX;
                int var3 = par1MovingObjectPosition.blockY;
                int var4 = par1MovingObjectPosition.blockZ;
                switch (par1MovingObjectPosition.sideHit) {
                    case 0: {
                        --var3;
                        break;
                    }
                    case 1: {
                        ++var3;
                        break;
                    }
                    case 2: {
                        --var4;
                        break;
                    }
                    case 3: {
                        ++var4;
                        break;
                    }
                    case 4: {
                        --var2;
                        break;
                    }
                    case 5: {
                        ++var2;
                        break;
                    }
                }
                if (this.worldObj.isAirBlock(var2, var3, var4)) {
                    this.worldObj.setBlock(var2, var3, var4, Block.fire.blockID);
                }
            }
            this.setDead();
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        return false;
    }
}
