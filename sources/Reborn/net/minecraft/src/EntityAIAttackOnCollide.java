package net.minecraft.src;

public class EntityAIAttackOnCollide extends EntityAIBase
{
    World worldObj;
    EntityLiving attacker;
    EntityLiving entityTarget;
    int attackTick;
    float field_75440_e;
    boolean field_75437_f;
    PathEntity entityPathEntity;
    Class classTarget;
    private int field_75445_i;
    
    public EntityAIAttackOnCollide(final EntityLiving par1EntityLiving, final Class par2Class, final float par3, final boolean par4) {
        this(par1EntityLiving, par3, par4);
        this.classTarget = par2Class;
    }
    
    public EntityAIAttackOnCollide(final EntityLiving par1EntityLiving, final float par2, final boolean par3) {
        this.attackTick = 0;
        this.attacker = par1EntityLiving;
        this.worldObj = par1EntityLiving.worldObj;
        this.field_75440_e = par2;
        this.field_75437_f = par3;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLiving var1 = this.attacker.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        if (this.classTarget != null && !this.classTarget.isAssignableFrom(var1.getClass())) {
            return false;
        }
        this.entityTarget = var1;
        this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(this.entityTarget);
        return this.entityPathEntity != null;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLiving var1 = this.attacker.getAttackTarget();
        return var1 != null && this.entityTarget.isEntityAlive() && (this.field_75437_f ? this.attacker.isWithinHomeDistance(MathHelper.floor_double(this.entityTarget.posX), MathHelper.floor_double(this.entityTarget.posY), MathHelper.floor_double(this.entityTarget.posZ)) : (!this.attacker.getNavigator().noPath()));
    }
    
    @Override
    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.field_75440_e);
        this.field_75445_i = 0;
    }
    
    @Override
    public void resetTask() {
        this.entityTarget = null;
        this.attacker.getNavigator().clearPathEntity();
    }
    
    @Override
    public void updateTask() {
        this.attacker.getLookHelper().setLookPositionWithEntity(this.entityTarget, 30.0f, 30.0f);
        if ((this.field_75437_f || this.attacker.getEntitySenses().canSee(this.entityTarget)) && --this.field_75445_i <= 0) {
            this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
            this.attacker.getNavigator().tryMoveToEntityLiving(this.entityTarget, this.field_75440_e);
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
        final double var1 = this.attacker.width * 2.0f * this.attacker.width * 2.0f;
        if (this.attacker.getDistanceSq(this.entityTarget.posX, this.entityTarget.boundingBox.minY, this.entityTarget.posZ) <= var1 && this.attackTick <= 0) {
            this.attackTick = 20;
            if (this.attacker.getHeldItem() != null) {
                this.attacker.swingItem();
            }
            this.attacker.attackEntityAsMob(this.entityTarget);
        }
    }
}
