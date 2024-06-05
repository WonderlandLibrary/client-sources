package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class ItemMapBase extends Item {
   public boolean isMap() {
      return true;
   }

   public Packet createMapDataPacket(ItemStack var1, World var2, EntityPlayer var3) {
      return null;
   }
}
