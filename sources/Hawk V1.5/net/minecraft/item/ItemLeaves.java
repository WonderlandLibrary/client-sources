package net.minecraft.item;

import net.minecraft.block.BlockLeaves;

public class ItemLeaves extends ItemBlock {
   private static final String __OBFID = "CL_00000046";
   private final BlockLeaves field_150940_b;

   public int getMetadata(int var1) {
      return var1 | 4;
   }

   public int getColorFromItemStack(ItemStack var1, int var2) {
      return this.field_150940_b.getRenderColor(this.field_150940_b.getStateFromMeta(var1.getMetadata()));
   }

   public ItemLeaves(BlockLeaves var1) {
      super(var1);
      this.field_150940_b = var1;
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   public String getUnlocalizedName(ItemStack var1) {
      return String.valueOf((new StringBuilder(String.valueOf(super.getUnlocalizedName()))).append(".").append(this.field_150940_b.func_176233_b(var1.getMetadata()).func_176840_c()));
   }
}
