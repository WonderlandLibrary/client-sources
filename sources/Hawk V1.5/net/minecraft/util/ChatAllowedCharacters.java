package net.minecraft.util;

public class ChatAllowedCharacters {
   public static final char[] allowedCharactersArray = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'};
   private static final String __OBFID = "CL_00001606";

   public static String filterAllowedCharacters(String var0) {
      StringBuilder var1 = new StringBuilder();
      char[] var2 = var0.toCharArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var2[var4];
         if (isAllowedCharacter(var5)) {
            var1.append(var5);
         }
      }

      return String.valueOf(var1);
   }

   public static boolean isAllowedCharacter(char var0) {
      return var0 != 167 && var0 >= ' ' && var0 != 127;
   }
}
