/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ public class ChatAllowedCharacters
/*  4:   */ {
/*  5: 8 */   public static final char[] allowedCharacters = { '/', '\n', '\r', '\t', '\000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
/*  6:   */   private static final String __OBFID = "CL_00001606";
/*  7:   */   
/*  8:   */   public static boolean isAllowedCharacter(char par0)
/*  9:   */   {
/* 10:13 */     return (par0 != '§') && (par0 >= ' ') && (par0 != '');
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static String filerAllowedCharacters(String par0Str)
/* 14:   */   {
/* 15:21 */     StringBuilder var1 = new StringBuilder();
/* 16:22 */     char[] var2 = par0Str.toCharArray();
/* 17:23 */     int var3 = var2.length;
/* 18:25 */     for (int var4 = 0; var4 < var3; var4++)
/* 19:   */     {
/* 20:27 */       char var5 = var2[var4];
/* 21:29 */       if (isAllowedCharacter(var5)) {
/* 22:31 */         var1.append(var5);
/* 23:   */       }
/* 24:   */     }
/* 25:35 */     return var1.toString();
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.ChatAllowedCharacters
 * JD-Core Version:    0.7.0.1
 */