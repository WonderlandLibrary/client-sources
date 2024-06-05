package net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class ajs {
   public static MapData a(ItemMap var0, ItemStack var1, World var2) {
      return var0.getMapData(var1, var2);
   }

   public static Packet a(ItemMap var0, ItemStack var1, World var2, EntityPlayer var3) {
      return var0.createMapDataPacket(var1, var2, var3);
   }

   public static MapData a(int var0, World var1) {
      return ItemMap.loadMapData(var0, var1);
   }
}
