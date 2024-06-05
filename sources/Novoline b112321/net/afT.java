package net;

import net.minecraft.entity.item.EntityMinecart$EnumMinecartType;

public class afT {
   public static EntityMinecart$EnumMinecartType a(int var0) {
      return EntityMinecart$EnumMinecartType.byNetworkID(var0);
   }

   public static String b(EntityMinecart$EnumMinecartType var0) {
      return var0.getName();
   }

   public static int a(EntityMinecart$EnumMinecartType var0) {
      return var0.getNetworkID();
   }
}
