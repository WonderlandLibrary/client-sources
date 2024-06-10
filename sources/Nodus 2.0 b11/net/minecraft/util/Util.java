/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import java.util.UUID;
/*  4:   */ import java.util.regex.Matcher;
/*  5:   */ import java.util.regex.Pattern;
/*  6:   */ 
/*  7:   */ public class Util
/*  8:   */ {
/*  9:12 */   private static final Pattern uuidPattern = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
/* 10:   */   private static final String __OBFID = "CL_00001633";
/* 11:   */   
/* 12:   */   public static EnumOS getOSType()
/* 13:   */   {
/* 14:17 */     String var0 = System.getProperty("os.name").toLowerCase();
/* 15:18 */     return var0.contains("unix") ? EnumOS.LINUX : var0.contains("linux") ? EnumOS.LINUX : var0.contains("sunos") ? EnumOS.SOLARIS : var0.contains("solaris") ? EnumOS.SOLARIS : var0.contains("mac") ? EnumOS.MACOS : var0.contains("win") ? EnumOS.WINDOWS : EnumOS.UNKNOWN;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static boolean isUUIDString(String p_147172_0_)
/* 19:   */   {
/* 20:26 */     return uuidPattern.matcher(p_147172_0_).matches();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static UUID tryGetUUIDFromString(String p_147173_0_)
/* 24:   */   {
/* 25:34 */     if (p_147173_0_ == null) {
/* 26:36 */       return null;
/* 27:   */     }
/* 28:38 */     if (isUUIDString(p_147173_0_)) {
/* 29:40 */       return UUID.fromString(p_147173_0_);
/* 30:   */     }
/* 31:44 */     if (p_147173_0_.length() == 32)
/* 32:   */     {
/* 33:46 */       String var1 = p_147173_0_.substring(0, 8) + "-" + p_147173_0_.substring(8, 12) + "-" + p_147173_0_.substring(12, 16) + "-" + p_147173_0_.substring(16, 20) + "-" + p_147173_0_.substring(20, 32);
/* 34:48 */       if (isUUIDString(var1)) {
/* 35:50 */         return UUID.fromString(var1);
/* 36:   */       }
/* 37:   */     }
/* 38:54 */     return null;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static enum EnumOS
/* 42:   */   {
/* 43:60 */     LINUX("LINUX", 0),  SOLARIS("SOLARIS", 1),  WINDOWS("WINDOWS", 2),  MACOS("MACOS", 3),  UNKNOWN("UNKNOWN", 4);
/* 44:   */     
/* 45:66 */     private static final EnumOS[] $VALUES = { LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN };
/* 46:   */     private static final String __OBFID = "CL_00001660";
/* 47:   */     
/* 48:   */     private EnumOS(String par1Str, int par2) {}
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.Util
 * JD-Core Version:    0.7.0.1
 */