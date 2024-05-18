package net.minecraft.src;

public class EntityAILeapAtTarget extends EntityAIBase
{
    EntityLiving leaper;
    EntityLiving leapTarget;
    float leapMotionY;
    
    public EntityAILeapAtTarget(final EntityLiving par1EntityLiving, final float par2) {
        this.leaper = par1EntityLiving;
        this.leapMotionY = par2;
        this.setMutexBits(5);
    }
    
    @Override
    public boolean shouldExecute() {
        this.leapTarget = this.leaper.getAttackTarget();
        if (this.leapTarget == null) {
            return false;
        }
        final double var1 = this.leaper.getDistanceSqToEntity(this.leapTarget);
        return var1 >= 4.0 && var1 <= 16.0 && this.leaper.onGround && this.leaper.getRNG().nextInt(5) == 0;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.leaper.onGround;
    }
    
    @Override
    public void startExecuting() {
        final double var1 = this.leapTarget.posX - this.leaper.posX;
        final double var2 = this.leapTarget.posZ - this.leaper.posZ;
        final float var3 = MathHelper.sqrt_double(var1 * var1 + var2 * var2);
        final EntityLiving leaper = this.leaper;
        leaper.motionX += var1 / var3 * 0.5 * 0.800000011920929 + this.leaper.motionX * 0.20000000298023224;
        final EntityLiving leaper2 = this.leaper;
        leaper2.motionZ += var2 / var3 * 0.5 * 0.800000011920929 + this.leaper.motionZ * 0.20000000298023224;
        this.leaper.motionY = this.leapMotionY;
    }
}
