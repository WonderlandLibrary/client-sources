/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import me.connorm.lib.EventTarget;
/*  8:   */ import net.minecraft.client.Minecraft;
/*  9:   */ 
/* 10:   */ public class Brightness
/* 11:   */   extends NodusModule
/* 12:   */ {
/* 13:   */   public Brightness()
/* 14:   */   {
/* 15:13 */     super("Brightness", Category.DISPLAY);
/* 16:   */   }
/* 17:   */   
/* 18:   */   @EventTarget
/* 19:   */   public void updateGamma(EventPlayerUpdate theEvent)
/* 20:   */   {
/* 21:19 */     Nodus.theNodus.getMinecraft().gameSettings.gammaSetting = 10.0F;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void onDisable()
/* 25:   */   {
/* 26:24 */     Nodus.theNodus.getMinecraft().gameSettings.gammaSetting = 1.0F;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Brightness
 * JD-Core Version:    0.7.0.1
 */