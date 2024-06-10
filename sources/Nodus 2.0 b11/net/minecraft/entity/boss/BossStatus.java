/*  1:   */ package net.minecraft.entity.boss;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.IChatComponent;
/*  4:   */ 
/*  5:   */ public final class BossStatus
/*  6:   */ {
/*  7:   */   public static float healthScale;
/*  8:   */   public static int statusBarTime;
/*  9:   */   public static String bossName;
/* 10:   */   public static boolean hasColorModifier;
/* 11:   */   private static final String __OBFID = "CL_00000941";
/* 12:   */   
/* 13:   */   public static void setBossStatus(IBossDisplayData par0IBossDisplayData, boolean par1)
/* 14:   */   {
/* 15:13 */     healthScale = par0IBossDisplayData.getHealth() / par0IBossDisplayData.getMaxHealth();
/* 16:14 */     statusBarTime = 100;
/* 17:15 */     bossName = par0IBossDisplayData.func_145748_c_().getFormattedText();
/* 18:16 */     hasColorModifier = par1;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.boss.BossStatus
 * JD-Core Version:    0.7.0.1
 */