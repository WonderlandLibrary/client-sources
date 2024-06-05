package net.minecraft.util;

public class ChatComponentTranslationFormatException extends IllegalArgumentException {
   private static final String __OBFID = "CL_00001271";

   public ChatComponentTranslationFormatException(ChatComponentTranslation var1, String var2) {
      super(String.format("Error parsing: %s: %s", var1, var2));
   }

   public ChatComponentTranslationFormatException(ChatComponentTranslation var1, int var2) {
      super(String.format("Invalid index %d requested for %s", var2, var1));
   }

   public ChatComponentTranslationFormatException(ChatComponentTranslation var1, Throwable var2) {
      super(String.format("Error while parsing: %s", var1), var2);
   }
}
