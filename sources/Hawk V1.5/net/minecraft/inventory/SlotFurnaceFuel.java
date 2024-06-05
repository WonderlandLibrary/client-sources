package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFurnaceFuel extends Slot {
   private static final String __OBFID = "CL_00002184";

   public boolean isItemValid(ItemStack var1) {
      return TileEntityFurnace.isItemFuel(var1) || func_178173_c_(var1);
   }

   public static boolean func_178173_c_(ItemStack var0) {
      return var0 != null && var0.getItem() != null && var0.getItem() == Items.bucket;
   }

   public int func_178170_b(ItemStack var1) {
      return func_178173_c_(var1) ? 1 : super.func_178170_b(var1);
   }

   public SlotFurnaceFuel(IInventory var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4);
   }
}
