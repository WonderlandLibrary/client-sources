/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.module.core.Category;
/*  5:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ 
/*  8:   */ public class Timer
/*  9:   */   extends NodusModule
/* 10:   */ {
/* 11:   */   public Timer()
/* 12:   */   {
/* 13:11 */     super("Timer", Category.PLAYER);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void onEnable()
/* 17:   */   {
/* 18:16 */     Nodus.theNodus.getMinecraft().timer.timerSpeed = 2.0F;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void onDisable()
/* 22:   */   {
/* 23:21 */     Nodus.theNodus.getMinecraft().timer.timerSpeed = 1.0F;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Timer
 * JD-Core Version:    0.7.0.1
 */