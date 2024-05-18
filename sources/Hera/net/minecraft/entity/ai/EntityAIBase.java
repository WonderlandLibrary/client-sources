/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EntityAIBase
/*    */ {
/*    */   private int mutexBits;
/*    */   
/*    */   public abstract boolean shouldExecute();
/*    */   
/*    */   public boolean continueExecuting() {
/* 21 */     return shouldExecute();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInterruptible() {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMutexBits(int mutexBitsIn) {
/* 60 */     this.mutexBits = mutexBitsIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMutexBits() {
/* 69 */     return this.mutexBits;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAIBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */