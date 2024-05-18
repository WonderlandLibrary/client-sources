/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.pathfinding.PathNavigateGround;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.village.Village;
/*    */ import net.minecraft.village.VillageDoorInfo;
/*    */ 
/*    */ public class EntityAIRestrictOpenDoor
/*    */   extends EntityAIBase {
/*    */   private EntityCreature entityObj;
/*    */   private VillageDoorInfo frontDoor;
/*    */   
/*    */   public EntityAIRestrictOpenDoor(EntityCreature creatureIn) {
/* 16 */     this.entityObj = creatureIn;
/*    */     
/* 18 */     if (!(creatureIn.getNavigator() instanceof PathNavigateGround))
/*    */     {
/* 20 */       throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 29 */     if (this.entityObj.worldObj.isDaytime())
/*    */     {
/* 31 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 35 */     BlockPos blockpos = new BlockPos((Entity)this.entityObj);
/* 36 */     Village village = this.entityObj.worldObj.getVillageCollection().getNearestVillage(blockpos, 16);
/*    */     
/* 38 */     if (village == null)
/*    */     {
/* 40 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 44 */     this.frontDoor = village.getNearestDoor(blockpos);
/* 45 */     return (this.frontDoor == null) ? false : ((this.frontDoor.getDistanceToInsideBlockSq(blockpos) < 2.25D));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 55 */     return this.entityObj.worldObj.isDaytime() ? false : ((!this.frontDoor.getIsDetachedFromVillageFlag() && this.frontDoor.func_179850_c(new BlockPos((Entity)this.entityObj))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 63 */     ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(false);
/* 64 */     ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 72 */     ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(true);
/* 73 */     ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(true);
/* 74 */     this.frontDoor = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 82 */     this.frontDoor.incrementDoorOpeningRestrictionCounter();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAIRestrictOpenDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */