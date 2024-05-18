package net.minecraft.src;

public abstract class EntityAITarget extends EntityAIBase
{
    protected EntityLiving taskOwner;
    protected float targetDistance;
    protected boolean shouldCheckSight;
    private boolean field_75303_a;
    private int field_75301_b;
    private int field_75302_c;
    private int field_75298_g;
    
    public EntityAITarget(final EntityLiving par1EntityLiving, final float par2, final boolean par3) {
        this(par1EntityLiving, par2, par3, false);
    }
    
    public EntityAITarget(final EntityLiving par1EntityLiving, final float par2, final boolean par3, final boolean par4) {
        this.field_75301_b = 0;
        this.field_75302_c = 0;
        this.field_75298_g = 0;
        this.taskOwner = par1EntityLiving;
        this.targetDistance = par2;
        this.shouldCheckSight = par3;
        this.field_75303_a = par4;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLiving var1 = this.taskOwner.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        if (!var1.isEntityAlive()) {
            return false;
        }
        if (this.taskOwner.getDistanceSqToEntity(var1) > this.targetDistance * this.targetDistance) {
            return false;
        }
        if (this.shouldCheckSight) {
            if (this.taskOwner.getEntitySenses().canSee(var1)) {
                this.field_75298_g = 0;
            }
            else if (++this.field_75298_g > 60) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void startExecuting() {
        this.field_75301_b = 0;
        this.field_75302_c = 0;
        this.field_75298_g = 0;
    }
    
    @Override
    public void resetTask() {
        this.taskOwner.setAttackTarget(null);
    }
    
    protected boolean isSuitableTarget(final EntityLiving par1EntityLiving, final boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this.taskOwner) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (!this.taskOwner.canAttackClass(par1EntityLiving.getClass())) {
            return false;
        }
        if (this.taskOwner instanceof EntityTameable && ((EntityTameable)this.taskOwner).isTamed()) {
            if (par1EntityLiving instanceof EntityTameable && ((EntityTameable)par1EntityLiving).isTamed()) {
                return false;
            }
            if (par1EntityLiving == ((EntityTameable)this.taskOwner).getOwner()) {
                return false;
            }
        }
        else if (par1EntityLiving instanceof EntityPlayer && !par2 && ((EntityPlayer)par1EntityLiving).capabilities.disableDamage) {
            return false;
        }
        if (!this.taskOwner.isWithinHomeDistance(MathHelper.floor_double(par1EntityLiving.posX), MathHelper.floor_double(par1EntityLiving.posY), MathHelper.floor_double(par1EntityLiving.posZ))) {
            return false;
        }
        if (this.shouldCheckSight && !this.taskOwner.getEntitySenses().canSee(par1EntityLiving)) {
            return false;
        }
        if (this.field_75303_a) {
            if (--this.field_75302_c <= 0) {
                this.field_75301_b = 0;
            }
            if (this.field_75301_b == 0) {
                this.field_75301_b = (this.func_75295_a(par1EntityLiving) ? 1 : 2);
            }
            if (this.field_75301_b == 2) {
                return false;
            }
        }
        return true;
    }
    
    private boolean func_75295_a(final EntityLiving par1EntityLiving) {
        this.field_75302_c = 10 + this.taskOwner.getRNG().nextInt(5);
        final PathEntity var2 = this.taskOwner.getNavigator().getPathToEntityLiving(par1EntityLiving);
        if (var2 == null) {
            return false;
        }
        final PathPoint var3 = var2.getFinalPathPoint();
        if (var3 == null) {
            return false;
        }
        final int var4 = var3.xCoord - MathHelper.floor_double(par1EntityLiving.posX);
        final int var5 = var3.zCoord - MathHelper.floor_double(par1EntityLiving.posZ);
        return var4 * var4 + var5 * var5 <= 2.25;
    }
}
