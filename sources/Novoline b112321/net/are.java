package net;

import net.acE;
import net.md_5.bungee.api.ChatColor;

public class are {
   private static acE[] b;

   public static String a(char var0, String var1) {
      return ChatColor.translateAlternateColorCodes(var0, var1);
   }

   public static ChatColor a(char var0) {
      return ChatColor.getByChar(var0);
   }

   public static String a(String var0) {
      return ChatColor.stripColor(var0);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[1]);
      }

   }
}
