/*    */ package net.minecraft.entity.passive;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class EntityWaterMob
/*    */   extends EntityLiving implements IAnimals {
/*    */   public EntityWaterMob(World worldIn) {
/* 12 */     super(worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBreatheUnderwater() {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getCanSpawnHere() {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isNotColliding() {
/* 33 */     return this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTalkInterval() {
/* 41 */     return 120;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canDespawn() {
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getExperiencePoints(EntityPlayer player) {
/* 57 */     return 1 + this.worldObj.rand.nextInt(3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityUpdate() {
/* 65 */     int i = getAir();
/* 66 */     super.onEntityUpdate();
/*    */     
/* 68 */     if (isEntityAlive() && !isInWater()) {
/*    */       
/* 70 */       i--;
/* 71 */       setAir(i);
/*    */       
/* 73 */       if (getAir() == -20)
/*    */       {
/* 75 */         setAir(0);
/* 76 */         attackEntityFrom(DamageSource.drown, 2.0F);
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 81 */       setAir(300);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPushedByWater() {
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\passive\EntityWaterMob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */