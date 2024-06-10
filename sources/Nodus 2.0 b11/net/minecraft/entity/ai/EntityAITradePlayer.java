/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.passive.EntityVillager;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.inventory.Container;
/*  6:   */ import net.minecraft.pathfinding.PathNavigate;
/*  7:   */ 
/*  8:   */ public class EntityAITradePlayer
/*  9:   */   extends EntityAIBase
/* 10:   */ {
/* 11:   */   private EntityVillager villager;
/* 12:   */   private static final String __OBFID = "CL_00001617";
/* 13:   */   
/* 14:   */   public EntityAITradePlayer(EntityVillager par1EntityVillager)
/* 15:   */   {
/* 16:14 */     this.villager = par1EntityVillager;
/* 17:15 */     setMutexBits(5);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean shouldExecute()
/* 21:   */   {
/* 22:23 */     if (!this.villager.isEntityAlive()) {
/* 23:25 */       return false;
/* 24:   */     }
/* 25:27 */     if (this.villager.isInWater()) {
/* 26:29 */       return false;
/* 27:   */     }
/* 28:31 */     if (!this.villager.onGround) {
/* 29:33 */       return false;
/* 30:   */     }
/* 31:35 */     if (this.villager.velocityChanged) {
/* 32:37 */       return false;
/* 33:   */     }
/* 34:41 */     EntityPlayer var1 = this.villager.getCustomer();
/* 35:42 */     return this.villager.getDistanceSqToEntity(var1) > 16.0D ? false : var1 == null ? false : var1.openContainer instanceof Container;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void startExecuting()
/* 39:   */   {
/* 40:51 */     this.villager.getNavigator().clearPathEntity();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void resetTask()
/* 44:   */   {
/* 45:59 */     this.villager.setCustomer(null);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAITradePlayer
 * JD-Core Version:    0.7.0.1
 */