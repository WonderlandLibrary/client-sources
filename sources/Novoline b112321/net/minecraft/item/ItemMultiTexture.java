package net.minecraft.item;

import com.google.common.base.Function;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture$1;
import net.minecraft.item.ItemStack;

public class ItemMultiTexture extends ItemBlock {
   protected final Block theBlock;
   protected final Function nameFunction;

   public ItemMultiTexture(Block var1, Block var2, Function var3) {
      super(var1);
      this.theBlock = var2;
      this.nameFunction = var3;
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   public ItemMultiTexture(Block var1, Block var2, String[] var3) {
      this(var1, var2, (Function)(new ItemMultiTexture$1(var3)));
   }

   public int getMetadata(int var1) {
      return var1;
   }

   public String getUnlocalizedName(ItemStack var1) {
      return super.getUnlocalizedName() + "." + (String)this.nameFunction.apply(var1);
   }
}
