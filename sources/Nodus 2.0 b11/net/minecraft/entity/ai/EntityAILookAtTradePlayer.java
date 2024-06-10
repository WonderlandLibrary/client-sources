/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.passive.EntityVillager;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ 
/*  6:   */ public class EntityAILookAtTradePlayer
/*  7:   */   extends EntityAIWatchClosest
/*  8:   */ {
/*  9:   */   private final EntityVillager theMerchant;
/* 10:   */   private static final String __OBFID = "CL_00001593";
/* 11:   */   
/* 12:   */   public EntityAILookAtTradePlayer(EntityVillager par1EntityVillager)
/* 13:   */   {
/* 14:13 */     super(par1EntityVillager, EntityPlayer.class, 8.0F);
/* 15:14 */     this.theMerchant = par1EntityVillager;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean shouldExecute()
/* 19:   */   {
/* 20:22 */     if (this.theMerchant.isTrading())
/* 21:   */     {
/* 22:24 */       this.closestEntity = this.theMerchant.getCustomer();
/* 23:25 */       return true;
/* 24:   */     }
/* 25:29 */     return false;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAILookAtTradePlayer
 * JD-Core Version:    0.7.0.1
 */