package net;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class a8v {
   public static IInventory a(World var0, double var1, double var3, double var5) {
      return TileEntityHopper.getInventoryAtPosition(var0, var1, var3, var5);
   }

   public static ItemStack a(IInventory var0, ItemStack var1, EnumFacing var2) {
      return TileEntityHopper.putStackInInventoryAllSlots(var0, var1, var2);
   }

   public static void a(TileEntityHopper var0, String var1) {
      var0.setCustomName(var1);
   }

   public static boolean a(IHopper var0) {
      return TileEntityHopper.captureDroppedItems(var0);
   }

   public static boolean a(IInventory var0, EntityItem var1) {
      return TileEntityHopper.a(var0, var1);
   }
}
