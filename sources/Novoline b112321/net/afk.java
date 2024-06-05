package net;

import net.minecraft.util.ChatComponentScore;

public class afk {
   public static void a(ChatComponentScore var0, String var1) {
      var0.setValue(var1);
   }

   public static String b(ChatComponentScore var0) {
      return var0.getName();
   }

   public static String a(ChatComponentScore var0) {
      return var0.getObjective();
   }

   public static String c(ChatComponentScore var0) {
      return var0.getUnformattedTextForChat();
   }
}
