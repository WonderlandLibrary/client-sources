/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAIWatchClosest
/*    */   extends EntityAIBase
/*    */ {
/*    */   protected EntityLiving theWatcher;
/*    */   protected Entity closestEntity;
/*    */   protected float maxDistanceForPlayer;
/*    */   private int lookTime;
/*    */   private float chance;
/*    */   protected Class<? extends Entity> watchedClass;
/*    */   
/*    */   public EntityAIWatchClosest(EntityLiving entitylivingIn, Class<? extends Entity> watchTargetClass, float maxDistance) {
/* 22 */     this.theWatcher = entitylivingIn;
/* 23 */     this.watchedClass = watchTargetClass;
/* 24 */     this.maxDistanceForPlayer = maxDistance;
/* 25 */     this.chance = 0.02F;
/* 26 */     setMutexBits(2);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityAIWatchClosest(EntityLiving entitylivingIn, Class<? extends Entity> watchTargetClass, float maxDistance, float chanceIn) {
/* 31 */     this.theWatcher = entitylivingIn;
/* 32 */     this.watchedClass = watchTargetClass;
/* 33 */     this.maxDistanceForPlayer = maxDistance;
/* 34 */     this.chance = chanceIn;
/* 35 */     setMutexBits(2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 43 */     if (this.theWatcher.getRNG().nextFloat() >= this.chance)
/*    */     {
/* 45 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 49 */     if (this.theWatcher.getAttackTarget() != null)
/*    */     {
/* 51 */       this.closestEntity = (Entity)this.theWatcher.getAttackTarget();
/*    */     }
/*    */     
/* 54 */     if (this.watchedClass == EntityPlayer.class) {
/*    */       
/* 56 */       this.closestEntity = (Entity)this.theWatcher.worldObj.getClosestPlayerToEntity((Entity)this.theWatcher, this.maxDistanceForPlayer);
/*    */     }
/*    */     else {
/*    */       
/* 60 */       this.closestEntity = this.theWatcher.worldObj.findNearestEntityWithinAABB(this.watchedClass, this.theWatcher.getEntityBoundingBox().expand(this.maxDistanceForPlayer, 3.0D, this.maxDistanceForPlayer), (Entity)this.theWatcher);
/*    */     } 
/*    */     
/* 63 */     return (this.closestEntity != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 72 */     return !this.closestEntity.isEntityAlive() ? false : ((this.theWatcher.getDistanceSqToEntity(this.closestEntity) > (this.maxDistanceForPlayer * this.maxDistanceForPlayer)) ? false : ((this.lookTime > 0)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 80 */     this.lookTime = 40 + this.theWatcher.getRNG().nextInt(40);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 88 */     this.closestEntity = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 96 */     this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, this.theWatcher.getVerticalFaceSpeed());
/* 97 */     this.lookTime--;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAIWatchClosest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */