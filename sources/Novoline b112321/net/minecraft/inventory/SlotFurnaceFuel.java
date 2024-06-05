package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFurnaceFuel extends Slot {
   public SlotFurnaceFuel(IInventory var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4);
   }

   public boolean isItemValid(ItemStack var1) {
      return TileEntityFurnace.isItemFuel(var1) || isBucket(var1);
   }

   public int getItemStackLimit(ItemStack var1) {
      return isBucket(var1)?1:super.getItemStackLimit(var1);
   }

   public static boolean isBucket(ItemStack var0) {
      return var0.getItem() != null && var0.getItem() == Items.bucket;
   }
}
