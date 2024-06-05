package net;

import java.util.List;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class ahR {
   private static String b;

   public static String b(IChatComponent var0) {
      return var0.getFormattedText();
   }

   public static String d(IChatComponent var0) {
      return var0.getUnformattedText();
   }

   public static ChatStyle c(IChatComponent var0) {
      return var0.getChatStyle();
   }

   public static IChatComponent a(IChatComponent var0, String var1) {
      return var0.appendText(var1);
   }

   public static IChatComponent a(IChatComponent var0, IChatComponent var1) {
      return var0.appendSibling(var1);
   }

   public static IChatComponent a(IChatComponent var0, ChatStyle var1) {
      return var0.setChatStyle(var1);
   }

   public static List e(IChatComponent var0) {
      return var0.getSiblings();
   }

   public static IChatComponent a(IChatComponent var0) {
      return var0.createCopy();
   }

   public static String f(IChatComponent var0) {
      return var0.getUnformattedTextForChat();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("rVZyB");
      }

   }
}
