package net.minecraft.item;

import com.google.common.base.Function;
import net.minecraft.item.ItemStack;

class ItemMultiTexture$1 implements Function {
   final String[] val$namesByMeta;

   ItemMultiTexture$1(String[] var1) {
      this.val$namesByMeta = var1;
   }

   public String apply(ItemStack var1) {
      int var2 = var1.getMetadata();
      if(var2 >= this.val$namesByMeta.length) {
         var2 = 0;
      }

      return this.val$namesByMeta[var2];
   }
}
