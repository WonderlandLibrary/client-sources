package net;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class aJr {
   private static int[] b;

   public static void c(TextComponent var0, Boolean var1) {
      var0.setItalic(var1);
   }

   public static void a(TextComponent var0, String var1) {
      var0.setText(var1);
   }

   public static void a(TextComponent var0, BaseComponent var1) {
      var0.addExtra(var1);
   }

   public static void e(TextComponent var0, Boolean var1) {
      var0.setBold(var1);
   }

   public static void a(TextComponent var0, Boolean var1) {
      var0.setUnderlined(var1);
   }

   public static void d(TextComponent var0, Boolean var1) {
      var0.setStrikethrough(var1);
   }

   public static void b(TextComponent var0, Boolean var1) {
      var0.setObfuscated(var1);
   }

   public static void a(TextComponent var0, ChatColor var1) {
      var0.setColor(var1);
   }

   public static String a(BaseComponent[] var0) {
      return TextComponent.toLegacyText(var0);
   }

   public static BaseComponent[] a(String var0) {
      return TextComponent.fromLegacyText(var0);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[3]);
      }

   }
}
