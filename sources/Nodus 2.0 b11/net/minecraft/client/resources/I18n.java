/*  1:   */ package net.minecraft.client.resources;
/*  2:   */ 
/*  3:   */ public class I18n
/*  4:   */ {
/*  5:   */   private static Locale i18nLocale;
/*  6:   */   private static final String __OBFID = "CL_00001094";
/*  7:   */   
/*  8:   */   static void setLocale(Locale par0Locale)
/*  9:   */   {
/* 10:10 */     i18nLocale = par0Locale;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static String format(String par0Str, Object... par1ArrayOfObj)
/* 14:   */   {
/* 15:18 */     return i18nLocale.formatMessage(par0Str, par1ArrayOfObj);
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.I18n
 * JD-Core Version:    0.7.0.1
 */