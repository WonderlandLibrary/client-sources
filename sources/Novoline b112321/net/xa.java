package net;

import net.minecraft.entity.player.EntityPlayer$EnumChatVisibility;

public class xa {
   public static int a(EntityPlayer$EnumChatVisibility var0) {
      return var0.getChatVisibility();
   }

   public static EntityPlayer$EnumChatVisibility a(int var0) {
      return EntityPlayer$EnumChatVisibility.getEnumChatVisibility(var0);
   }

   public static String b(EntityPlayer$EnumChatVisibility var0) {
      return var0.getResourceKey();
   }
}
