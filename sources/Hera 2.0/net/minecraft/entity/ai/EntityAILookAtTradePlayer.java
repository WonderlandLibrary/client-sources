/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class EntityAILookAtTradePlayer extends EntityAIWatchClosest {
/*    */   private final EntityVillager theMerchant;
/*    */   
/*    */   public EntityAILookAtTradePlayer(EntityVillager theMerchantIn) {
/* 12 */     super((EntityLiving)theMerchantIn, (Class)EntityPlayer.class, 8.0F);
/* 13 */     this.theMerchant = theMerchantIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 21 */     if (this.theMerchant.isTrading()) {
/*    */       
/* 23 */       this.closestEntity = (Entity)this.theMerchant.getCustomer();
/* 24 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 28 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAILookAtTradePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */