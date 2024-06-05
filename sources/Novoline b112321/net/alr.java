package net;

import cc.novoline.utils.messages.TextMessage;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class alr {
   public static String a(TextMessage var0) {
      return var0.getFormattedText();
   }

   public static TextMessage a(TextMessage var0, String var1, EnumChatFormatting var2) {
      return var0.append(var1, var2);
   }

   public static TextMessage a(TextMessage var0, TextMessage var1) {
      return var0.prefix(var1);
   }

   public static TextMessage a(TextMessage var0, String var1) {
      return var0.append(var1);
   }

   public static IChatComponent a(TextMessage var0, EnumChatFormatting var1) {
      return var0.setColor(var1);
   }

   public static TextMessage a(TextMessage var0, IChatComponent var1) {
      return var0.append(var1);
   }

   public static TextMessage a(String var0) {
      return TextMessage.of(var0);
   }

   public static TextMessage a(String var0, EnumChatFormatting var1) {
      return TextMessage.of(var0, var1);
   }
}
