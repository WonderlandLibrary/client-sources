/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import me.connorm.lib.EventTarget;
/*  8:   */ import net.minecraft.client.Minecraft;
/*  9:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/* 10:   */ import net.minecraft.client.settings.GameSettings;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ 
/* 13:   */ public class Flight
/* 14:   */   extends NodusModule
/* 15:   */ {
/* 16:   */   public Flight()
/* 17:   */   {
/* 18:16 */     super("Flight", Category.PLAYER);
/* 19:   */   }
/* 20:   */   
/* 21:   */   @EventTarget
/* 22:   */   public void updatePlayer(EventPlayerUpdate event)
/* 23:   */   {
/* 24:22 */     EntityPlayer thePlayer = event.getPlayer();
/* 25:   */     
/* 26:24 */     thePlayer.landMovementFactor = 0.5F;
/* 27:25 */     thePlayer.jumpMovementFactor = 0.5F;
/* 28:   */     
/* 29:27 */     thePlayer.capabilities.isFlying = false;
/* 30:28 */     thePlayer.setSneaking(false);
/* 31:   */     
/* 32:30 */     Nodus.theNodus.getMinecraft().thePlayer.motionX = 0.0D;
/* 33:31 */     Nodus.theNodus.getMinecraft().thePlayer.motionY = 0.0D;
/* 34:32 */     Nodus.theNodus.getMinecraft().thePlayer.motionZ = 0.0D;
/* 35:34 */     if (Nodus.theNodus.getMinecraft().currentScreen == null)
/* 36:   */     {
/* 37:36 */       if (GameSettings.isKeyDown(Nodus.theNodus.getMinecraft().gameSettings.keyBindJump)) {
/* 38:38 */         Nodus.theNodus.getMinecraft().thePlayer.motionY = 1.0D;
/* 39:   */       }
/* 40:40 */       if (GameSettings.isKeyDown(Nodus.theNodus.getMinecraft().gameSettings.keyBindSneak)) {
/* 41:42 */         Nodus.theNodus.getMinecraft().thePlayer.motionY = -1.0D;
/* 42:   */       }
/* 43:   */     }
/* 44:46 */     Nodus.theNodus.getMinecraft().thePlayer.jumpMovementFactor *= 3.0F;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Flight
 * JD-Core Version:    0.7.0.1
 */