/*    */ package net.minecraft.entity.monster;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.passive.IAnimals;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class EntityGolem
/*    */   extends EntityCreature
/*    */   implements IAnimals {
/*    */   public EntityGolem(World worldIn) {
/* 11 */     super(worldIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void fall(float distance, float damageMultiplier) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLivingSound() {
/* 23 */     return "none";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getHurtSound() {
/* 31 */     return "none";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getDeathSound() {
/* 39 */     return "none";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTalkInterval() {
/* 47 */     return 120;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canDespawn() {
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\monster\EntityGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */