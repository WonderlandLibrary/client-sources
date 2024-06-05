package net.minecraft.item;

import net.minecraft.block.BlockLeaves;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemLeaves extends ItemBlock {
   private final BlockLeaves leaves;

   public ItemLeaves(BlockLeaves var1) {
      super(var1);
      this.leaves = var1;
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   public int getMetadata(int var1) {
      return var1 | 4;
   }

   public int getColorFromItemStack(ItemStack var1, int var2) {
      return this.leaves.getRenderColor(this.leaves.getStateFromMeta(var1.getMetadata()));
   }

   public String getUnlocalizedName(ItemStack var1) {
      return super.getUnlocalizedName() + "." + this.leaves.getWoodType(var1.getMetadata()).getUnlocalizedName();
   }
}
