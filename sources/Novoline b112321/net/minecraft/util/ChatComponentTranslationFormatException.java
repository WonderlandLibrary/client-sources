package net.minecraft.util;

import net.minecraft.util.ChatComponentTranslation;

public class ChatComponentTranslationFormatException extends IllegalArgumentException {
   public ChatComponentTranslationFormatException(ChatComponentTranslation var1, String var2) {
      super(String.format("Error parsing: %s: %s", new Object[]{var1, var2}));
   }

   public ChatComponentTranslationFormatException(ChatComponentTranslation var1, int var2) {
      super(String.format("Invalid index %d requested for %s", new Object[]{Integer.valueOf(var2), var1}));
   }

   public ChatComponentTranslationFormatException(ChatComponentTranslation var1, Throwable var2) {
      super(String.format("Error while parsing: %s", new Object[]{var1}), var2);
   }
}
