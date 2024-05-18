/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityCreeper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAICreeperSwell
/*    */   extends EntityAIBase
/*    */ {
/*    */   EntityCreeper swellingCreeper;
/*    */   EntityLivingBase creeperAttackTarget;
/*    */   
/*    */   public EntityAICreeperSwell(EntityCreeper entitycreeperIn) {
/* 18 */     this.swellingCreeper = entitycreeperIn;
/* 19 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 27 */     EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
/* 28 */     return !(this.swellingCreeper.getCreeperState() <= 0 && (entitylivingbase == null || this.swellingCreeper.getDistanceSqToEntity((Entity)entitylivingbase) >= 9.0D));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 36 */     this.swellingCreeper.getNavigator().clearPathEntity();
/* 37 */     this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 45 */     this.creeperAttackTarget = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 53 */     if (this.creeperAttackTarget == null) {
/*    */       
/* 55 */       this.swellingCreeper.setCreeperState(-1);
/*    */     }
/* 57 */     else if (this.swellingCreeper.getDistanceSqToEntity((Entity)this.creeperAttackTarget) > 49.0D) {
/*    */       
/* 59 */       this.swellingCreeper.setCreeperState(-1);
/*    */     }
/* 61 */     else if (!this.swellingCreeper.getEntitySenses().canSee((Entity)this.creeperAttackTarget)) {
/*    */       
/* 63 */       this.swellingCreeper.setCreeperState(-1);
/*    */     }
/*    */     else {
/*    */       
/* 67 */       this.swellingCreeper.setCreeperState(1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAICreeperSwell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */