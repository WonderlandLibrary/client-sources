package net;

import java.util.List;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class ym {
   public static ChatStyle a(ChatComponentText var0) {
      return var0.getChatStyle();
   }

   public static String b(ChatComponentText var0) {
      return var0.getChatComponentText_TextValue();
   }

   public static List c(ChatComponentText var0) {
      return var0.getSiblings();
   }

   public static IChatComponent a(ChatComponentText var0, IChatComponent var1) {
      return var0.appendSibling(var1);
   }

   public static IChatComponent a(ChatComponentText var0, ChatStyle var1) {
      return var0.setChatStyle(var1);
   }

   public static IChatComponent a(ChatComponentText var0, String var1) {
      return var0.appendText(var1);
   }
}
