package net;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.storage.MapData$MapInfo;

public class a_z {
   public static Packet a(MapData$MapInfo var0, ItemStack var1) {
      return var0.getPacket(var1);
   }

   public static void a(MapData$MapInfo var0, int var1, int var2) {
      var0.update(var1, var2);
   }
}
