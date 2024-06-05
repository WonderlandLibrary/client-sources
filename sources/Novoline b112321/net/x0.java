package net;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;

public class x0 {
   public static Integer a(EnumConnectionState var0, EnumPacketDirection var1, Packet var2) {
      return var0.getPacketId(var1, var2);
   }

   public static EnumConnectionState a(int var0) {
      return EnumConnectionState.getById(var0);
   }

   public static int a(EnumConnectionState var0) {
      return var0.getId();
   }

   public static EnumConnectionState a(Packet var0) {
      return EnumConnectionState.getFromPacket(var0);
   }

   public static Packet a(EnumConnectionState var0, EnumPacketDirection var1, int var2) {
      return var0.getPacket(var1, var2);
   }
}
