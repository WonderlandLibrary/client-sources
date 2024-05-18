/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class EntityAITradePlayer
/*    */   extends EntityAIBase
/*    */ {
/*    */   private EntityVillager villager;
/*    */   
/*    */   public EntityAITradePlayer(EntityVillager villagerIn) {
/* 13 */     this.villager = villagerIn;
/* 14 */     setMutexBits(5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 22 */     if (!this.villager.isEntityAlive())
/*    */     {
/* 24 */       return false;
/*    */     }
/* 26 */     if (this.villager.isInWater())
/*    */     {
/* 28 */       return false;
/*    */     }
/* 30 */     if (!this.villager.onGround)
/*    */     {
/* 32 */       return false;
/*    */     }
/* 34 */     if (this.villager.velocityChanged)
/*    */     {
/* 36 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 40 */     EntityPlayer entityplayer = this.villager.getCustomer();
/* 41 */     return (entityplayer == null) ? false : ((this.villager.getDistanceSqToEntity((Entity)entityplayer) > 16.0D) ? false : (entityplayer.openContainer instanceof net.minecraft.inventory.Container));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 50 */     this.villager.getNavigator().clearPathEntity();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 58 */     this.villager.setCustomer(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAITradePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */