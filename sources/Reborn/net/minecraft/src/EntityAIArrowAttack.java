package net.minecraft.src;

public class EntityAIArrowAttack extends EntityAIBase
{
    private final EntityLiving entityHost;
    private final IRangedAttackMob rangedAttackEntityHost;
    private EntityLiving attackTarget;
    private int rangedAttackTime;
    private float entityMoveSpeed;
    private int field_75318_f;
    private int field_96561_g;
    private int maxRangedAttackTime;
    private float field_96562_i;
    private float field_82642_h;
    
    public EntityAIArrowAttack(final IRangedAttackMob par1IRangedAttackMob, final float par2, final int par3, final float par4) {
        this(par1IRangedAttackMob, par2, par3, par3, par4);
    }
    
    public EntityAIArrowAttack(final IRangedAttackMob par1IRangedAttackMob, final float par2, final int par3, final int par4, final float par5) {
        this.rangedAttackTime = -1;
        this.field_75318_f = 0;
        if (!(par1IRangedAttackMob instanceof EntityLiving)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        this.rangedAttackEntityHost = par1IRangedAttackMob;
        this.entityHost = (EntityLiving)par1IRangedAttackMob;
        this.entityMoveSpeed = par2;
        this.field_96561_g = par3;
        this.maxRangedAttackTime = par4;
        this.field_96562_i = par5;
        this.field_82642_h = par5 * par5;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLiving var1 = this.entityHost.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        this.attackTarget = var1;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }
    
    @Override
    public void resetTask() {
        this.attackTarget = null;
        this.field_75318_f = 0;
        this.rangedAttackTime = -1;
    }
    
    @Override
    public void updateTask() {
        final double var1 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.boundingBox.minY, this.attackTarget.posZ);
        final boolean var2 = this.entityHost.getEntitySenses().canSee(this.attackTarget);
        if (var2) {
            ++this.field_75318_f;
        }
        else {
            this.field_75318_f = 0;
        }
        if (var1 <= this.field_82642_h && this.field_75318_f >= 20) {
            this.entityHost.getNavigator().clearPathEntity();
        }
        else {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }
        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0f, 30.0f);
        final int rangedAttackTime = this.rangedAttackTime - 1;
        this.rangedAttackTime = rangedAttackTime;
        if (rangedAttackTime == 0) {
            if (var1 > this.field_82642_h || !var2) {
                return;
            }
            float var4;
            final float var3 = var4 = MathHelper.sqrt_double(var1) / this.field_96562_i;
            if (var3 < 0.1f) {
                var4 = 0.1f;
            }
            if (var4 > 1.0f) {
                var4 = 1.0f;
            }
            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, var4);
            this.rangedAttackTime = MathHelper.floor_float(var3 * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
        }
        else if (this.rangedAttackTime < 0) {
            final float var3 = MathHelper.sqrt_double(var1) / this.field_96562_i;
            this.rangedAttackTime = MathHelper.floor_float(var3 * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
        }
    }
}
