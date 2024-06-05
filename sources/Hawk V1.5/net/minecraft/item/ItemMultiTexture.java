package net.minecraft.item;

import com.google.common.base.Function;
import net.minecraft.block.Block;

public class ItemMultiTexture extends ItemBlock {
   protected final Block theBlock;
   private static final String __OBFID = "CL_00000051";
   protected final Function nameFunction;

   public ItemMultiTexture(Block var1, Block var2, Function var3) {
      super(var1);
      this.theBlock = var2;
      this.nameFunction = var3;
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   public ItemMultiTexture(Block var1, Block var2, String[] var3) {
      this(var1, var2, new Function(var3) {
         private static final String __OBFID = "CL_00002161";
         private final String[] val$p_i45346_3_;

         {
            this.val$p_i45346_3_ = var1;
         }

         public String apply(ItemStack var1) {
            int var2 = var1.getMetadata();
            if (var2 < 0 || var2 >= this.val$p_i45346_3_.length) {
               var2 = 0;
            }

            return this.val$p_i45346_3_[var2];
         }

         public Object apply(Object var1) {
            return this.apply((ItemStack)var1);
         }
      });
   }

   public String getUnlocalizedName(ItemStack var1) {
      return String.valueOf((new StringBuilder(String.valueOf(super.getUnlocalizedName()))).append(".").append((String)this.nameFunction.apply(var1)));
   }

   public int getMetadata(int var1) {
      return var1;
   }
}
