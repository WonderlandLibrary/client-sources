/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import java.util.regex.Matcher;
/*  4:   */ import java.util.regex.Pattern;
/*  5:   */ 
/*  6:   */ public class StringUtils
/*  7:   */ {
/*  8: 7 */   private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
/*  9:   */   private static final String __OBFID = "CL_00001501";
/* 10:   */   
/* 11:   */   public static String ticksToElapsedTime(int par0)
/* 12:   */   {
/* 13:15 */     int var1 = par0 / 20;
/* 14:16 */     int var2 = var1 / 60;
/* 15:17 */     var1 %= 60;
/* 16:18 */     return var2 + ":" + var1;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static String stripControlCodes(String par0Str)
/* 20:   */   {
/* 21:23 */     return patternControlCode.matcher(par0Str).replaceAll("");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static boolean isNullOrEmpty(String p_151246_0_)
/* 25:   */   {
/* 26:31 */     return (p_151246_0_ == null) || ("".equals(p_151246_0_));
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.StringUtils
 * JD-Core Version:    0.7.0.1
 */