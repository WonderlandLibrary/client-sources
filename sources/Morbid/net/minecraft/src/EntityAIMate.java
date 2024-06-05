package net.minecraft.src;

import java.util.*;

public class EntityAIMate extends EntityAIBase
{
    private EntityAnimal theAnimal;
    World theWorld;
    private EntityAnimal targetMate;
    int spawnBabyDelay;
    float moveSpeed;
    
    public EntityAIMate(final EntityAnimal par1EntityAnimal, final float par2) {
        this.spawnBabyDelay = 0;
        this.theAnimal = par1EntityAnimal;
        this.theWorld = par1EntityAnimal.worldObj;
        this.moveSpeed = par2;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theAnimal.isInLove()) {
            return false;
        }
        this.targetMate = this.getNearbyMate();
        return this.targetMate != null;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
    }
    
    @Override
    public void resetTask() {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }
    
    @Override
    public void updateTask() {
        this.theAnimal.getLookHelper().setLookPositionWithEntity(this.targetMate, 10.0f, this.theAnimal.getVerticalFaceSpeed());
        this.theAnimal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
        ++this.spawnBabyDelay;
        if (this.spawnBabyDelay >= 60 && this.theAnimal.getDistanceSqToEntity(this.targetMate) < 9.0) {
            this.spawnBaby();
        }
    }
    
    private EntityAnimal getNearbyMate() {
        final float var1 = 8.0f;
        final List var2 = this.theWorld.getEntitiesWithinAABB(this.theAnimal.getClass(), this.theAnimal.boundingBox.expand(var1, var1, var1));
        double var3 = Double.MAX_VALUE;
        EntityAnimal var4 = null;
        for (final EntityAnimal var6 : var2) {
            if (this.theAnimal.canMateWith(var6) && this.theAnimal.getDistanceSqToEntity(var6) < var3) {
                var4 = var6;
                var3 = this.theAnimal.getDistanceSqToEntity(var6);
            }
        }
        return var4;
    }
    
    private void spawnBaby() {
        final EntityAgeable var1 = this.theAnimal.createChild(this.targetMate);
        if (var1 != null) {
            this.theAnimal.setGrowingAge(6000);
            this.targetMate.setGrowingAge(6000);
            this.theAnimal.resetInLove();
            this.targetMate.resetInLove();
            var1.setGrowingAge(-24000);
            var1.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0f, 0.0f);
            this.theWorld.spawnEntityInWorld(var1);
            final Random var2 = this.theAnimal.getRNG();
            for (int var3 = 0; var3 < 7; ++var3) {
                final double var4 = var2.nextGaussian() * 0.02;
                final double var5 = var2.nextGaussian() * 0.02;
                final double var6 = var2.nextGaussian() * 0.02;
                this.theWorld.spawnParticle("heart", this.theAnimal.posX + var2.nextFloat() * this.theAnimal.width * 2.0f - this.theAnimal.width, this.theAnimal.posY + 0.5 + var2.nextFloat() * this.theAnimal.height, this.theAnimal.posZ + var2.nextFloat() * this.theAnimal.width * 2.0f - this.theAnimal.width, var4, var5, var6);
            }
            this.theWorld.spawnEntityInWorld(new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, var2.nextInt(7) + 1));
        }
    }
}
