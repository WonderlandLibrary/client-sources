/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import me.connorm.lib.EventTarget;
/*  8:   */ import net.minecraft.client.Minecraft;
/*  9:   */ import net.minecraft.client.settings.GameSettings;
/* 10:   */ 
/* 11:   */ public class AutoMine
/* 12:   */   extends NodusModule
/* 13:   */ {
/* 14:   */   public AutoMine()
/* 15:   */   {
/* 16:14 */     super("AutoMine", Category.PLAYER);
/* 17:   */   }
/* 18:   */   
/* 19:   */   @EventTarget
/* 20:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 21:   */   {
/* 22:20 */     if (!isToggled()) {
/* 23:21 */       return;
/* 24:   */     }
/* 25:22 */     Nodus.theNodus.getMinecraft().gameSettings.keyBindAttack.pressed = true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void onDisable()
/* 29:   */   {
/* 30:27 */     Nodus.theNodus.getMinecraft().gameSettings.keyBindAttack.pressed = false;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.AutoMine
 * JD-Core Version:    0.7.0.1
 */