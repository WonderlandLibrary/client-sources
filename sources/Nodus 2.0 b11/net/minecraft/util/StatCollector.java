/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ public class StatCollector
/*  4:   */ {
/*  5: 5 */   private static StringTranslate localizedName = ;
/*  6:11 */   private static StringTranslate fallbackTranslator = new StringTranslate();
/*  7:   */   private static final String __OBFID = "CL_00001211";
/*  8:   */   
/*  9:   */   public static String translateToLocal(String par0Str)
/* 10:   */   {
/* 11:19 */     return localizedName.translateKey(par0Str);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public static String translateToLocalFormatted(String par0Str, Object... par1ArrayOfObj)
/* 15:   */   {
/* 16:27 */     return localizedName.translateKeyFormat(par0Str, par1ArrayOfObj);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static String translateToFallback(String p_150826_0_)
/* 20:   */   {
/* 21:36 */     return fallbackTranslator.translateKey(p_150826_0_);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static boolean canTranslate(String par0Str)
/* 25:   */   {
/* 26:44 */     return localizedName.containsTranslateKey(par0Str);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static long getLastTranslationUpdateTimeInMilliseconds()
/* 30:   */   {
/* 31:52 */     return localizedName.getLastUpdateTimeInMilliseconds();
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.StatCollector
 * JD-Core Version:    0.7.0.1
 */