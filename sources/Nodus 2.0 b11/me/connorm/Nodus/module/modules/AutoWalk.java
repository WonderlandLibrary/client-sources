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
/* 11:   */ public class AutoWalk
/* 12:   */   extends NodusModule
/* 13:   */ {
/* 14:   */   public AutoWalk()
/* 15:   */   {
/* 16:14 */     super("AutoWalk", Category.PLAYER);
/* 17:   */   }
/* 18:   */   
/* 19:   */   @EventTarget
/* 20:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 21:   */   {
/* 22:20 */     Nodus.theNodus.getMinecraft().gameSettings.keyBindForward.pressed = true;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void onDisable()
/* 26:   */   {
/* 27:25 */     Nodus.theNodus.getMinecraft().gameSettings.keyBindForward.pressed = false;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.AutoWalk
 * JD-Core Version:    0.7.0.1
 */