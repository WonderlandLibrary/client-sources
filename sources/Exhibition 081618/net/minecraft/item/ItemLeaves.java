package net.minecraft.item;

import net.minecraft.block.BlockLeaves;

public class ItemLeaves extends ItemBlock {
   private final BlockLeaves field_150940_b;

   public ItemLeaves(BlockLeaves p_i45344_1_) {
      super(p_i45344_1_);
      this.field_150940_b = p_i45344_1_;
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   public int getMetadata(int damage) {
      return damage | 4;
   }

   public int getColorFromItemStack(ItemStack stack, int renderPass) {
      return this.field_150940_b.getRenderColor(this.field_150940_b.getStateFromMeta(stack.getMetadata()));
   }

   public String getUnlocalizedName(ItemStack stack) {
      return super.getUnlocalizedName() + "." + this.field_150940_b.func_176233_b(stack.getMetadata()).func_176840_c();
   }
}
