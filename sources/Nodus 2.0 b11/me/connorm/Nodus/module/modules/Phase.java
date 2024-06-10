/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.module.core.Category;
/*  5:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ 
/*  8:   */ public class Phase
/*  9:   */   extends NodusModule
/* 10:   */ {
/* 11:   */   public Phase()
/* 12:   */   {
/* 13:11 */     super("Phase", Category.WORLD);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void onEnable()
/* 17:   */   {
/* 18:16 */     if (Nodus.theNodus.getMinecraft().thePlayer != null) {
/* 19:18 */       Nodus.theNodus.getMinecraft().thePlayer.noClip = true;
/* 20:   */     }
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void onDisable()
/* 24:   */   {
/* 25:24 */     Nodus.theNodus.getMinecraft().thePlayer.noClip = false;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Phase
 * JD-Core Version:    0.7.0.1
 */