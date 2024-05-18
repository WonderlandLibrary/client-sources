package net.minecraft.src;

public class EntityWitherSkull extends EntityFireball
{
    public EntityWitherSkull(final World par1World) {
        super(par1World);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public EntityWitherSkull(final World par1World, final EntityLiving par2EntityLiving, final double par3, final double par5, final double par7) {
        super(par1World, par2EntityLiving, par3, par5, par7);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    protected float getMotionFactor() {
        return this.isInvulnerable() ? 0.73f : super.getMotionFactor();
    }
    
    public EntityWitherSkull(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    public boolean isBurning() {
        return false;
    }
    
    @Override
    public float func_82146_a(final Explosion par1Explosion, final World par2World, final int par3, final int par4, final int par5, final Block par6Block) {
        float var7 = super.func_82146_a(par1Explosion, par2World, par3, par4, par5, par6Block);
        if (this.isInvulnerable() && par6Block != Block.bedrock && par6Block != Block.endPortal && par6Block != Block.endPortalFrame) {
            var7 = Math.min(0.8f, var7);
        }
        return var7;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (!this.worldObj.isRemote) {
            if (par1MovingObjectPosition.entityHit != null) {
                if (this.shootingEntity != null) {
                    if (par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8) && !par1MovingObjectPosition.entityHit.isEntityAlive()) {
                        this.shootingEntity.heal(5);
                    }
                }
                else {
                    par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.magic, 5);
                }
                if (par1MovingObjectPosition.entityHit instanceof EntityLiving) {
                    byte var2 = 0;
                    if (this.worldObj.difficultySetting > 1) {
                        if (this.worldObj.difficultySetting == 2) {
                            var2 = 10;
                        }
                        else if (this.worldObj.difficultySetting == 3) {
                            var2 = 40;
                        }
                    }
                    if (var2 > 0) {
                        ((EntityLiving)par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * var2, 1));
                    }
                }
            }
            this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0f, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
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
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(10, (byte)0);
    }
    
    public boolean isInvulnerable() {
        return this.dataWatcher.getWatchableObjectByte(10) == 1;
    }
    
    public void setInvulnerable(final boolean par1) {
        this.dataWatcher.updateObject(10, (byte)(byte)(par1 ? 1 : 0));
    }
}
