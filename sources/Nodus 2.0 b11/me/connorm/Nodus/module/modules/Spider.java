/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  4:   */ import me.connorm.Nodus.module.core.Category;
/*  5:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  6:   */ import me.connorm.lib.EventTarget;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ 
/*  9:   */ public class Spider
/* 10:   */   extends NodusModule
/* 11:   */ {
/* 12:   */   public Spider()
/* 13:   */   {
/* 14:14 */     super("Spider", Category.PLAYER);
/* 15:   */   }
/* 16:   */   
/* 17:   */   @EventTarget
/* 18:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 19:   */   {
/* 20:20 */     EntityPlayer thePlayer = theEvent.getPlayer();
/* 21:21 */     if (thePlayer.isCollidedHorizontally)
/* 22:   */     {
/* 23:23 */       thePlayer.motionY = 0.2D;
/* 24:   */       
/* 25:25 */       float climbCount = 0.15F;
/* 26:27 */       if (thePlayer.motionX < -climbCount) {
/* 27:29 */         thePlayer.motionX = (-climbCount);
/* 28:   */       }
/* 29:32 */       if (thePlayer.motionX > climbCount) {
/* 30:34 */         thePlayer.motionX = climbCount;
/* 31:   */       }
/* 32:37 */       if (thePlayer.motionZ < -climbCount) {
/* 33:39 */         thePlayer.motionZ = (-climbCount);
/* 34:   */       }
/* 35:42 */       if (thePlayer.motionZ > climbCount) {
/* 36:44 */         thePlayer.motionZ = climbCount;
/* 37:   */       }
/* 38:47 */       thePlayer.fallDistance = 0.0F;
/* 39:49 */       if (thePlayer.motionY < -0.15D) {
/* 40:51 */         thePlayer.motionY = -0.15D;
/* 41:   */       }
/* 42:   */     }
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Spider
 * JD-Core Version:    0.7.0.1
 */