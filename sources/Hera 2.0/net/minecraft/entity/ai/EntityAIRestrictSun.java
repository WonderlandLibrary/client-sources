/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.pathfinding.PathNavigateGround;
/*    */ 
/*    */ public class EntityAIRestrictSun
/*    */   extends EntityAIBase
/*    */ {
/*    */   private EntityCreature theEntity;
/*    */   
/*    */   public EntityAIRestrictSun(EntityCreature p_i1652_1_) {
/* 12 */     this.theEntity = p_i1652_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 20 */     return this.theEntity.worldObj.isDaytime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 28 */     ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 36 */     ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAIRestrictSun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */