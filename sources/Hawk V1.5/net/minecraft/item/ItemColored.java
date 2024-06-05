package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemColored extends ItemBlock {
   private String[] field_150945_c;
   private final Block field_150944_b;
   private static final String __OBFID = "CL_00000003";

   public int getMetadata(int var1) {
      return var1;
   }

   public ItemColored(Block var1, boolean var2) {
      super(var1);
      this.field_150944_b = var1;
      if (var2) {
         this.setMaxDamage(0);
         this.setHasSubtypes(true);
      }

   }

   public String getUnlocalizedName(ItemStack var1) {
      if (this.field_150945_c == null) {
         return super.getUnlocalizedName(var1);
      } else {
         int var2 = var1.getMetadata();
         return var2 >= 0 && var2 < this.field_150945_c.length ? String.valueOf((new StringBuilder(String.valueOf(super.getUnlocalizedName(var1)))).append(".").append(this.field_150945_c[var2])) : super.getUnlocalizedName(var1);
      }
   }

   public int getColorFromItemStack(ItemStack var1, int var2) {
      return this.field_150944_b.getRenderColor(this.field_150944_b.getStateFromMeta(var1.getMetadata()));
   }

   public ItemColored func_150943_a(String[] var1) {
      this.field_150945_c = var1;
      return this;
   }
}
