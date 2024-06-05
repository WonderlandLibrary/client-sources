package net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapData$MapInfo;

public class bg9 {
   public static void a(MapData var0, double var1, double var3, int var5) {
      var0.calculateMapCenter(var1, var3, var5);
   }

   public static void a(MapData var0) {
      var0.markDirty();
   }

   public static MapData$MapInfo a(MapData var0, EntityPlayer var1) {
      return var0.getMapInfo(var1);
   }

   public static void a(MapData var0, int var1, int var2) {
      var0.updateMapData(var1, var2);
   }

   public static void a(MapData var0, EntityPlayer var1, ItemStack var2) {
      var0.updateVisiblePlayers(var1, var2);
   }

   public static Packet a(MapData var0, ItemStack var1, World var2, EntityPlayer var3) {
      return var0.getMapPacket(var1, var2, var3);
   }
}
