package net.minecraft.block.state;

import java.util.Comparator;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;

class BlockState$2 implements Comparator {
   final BlockState this$0;

   BlockState$2(BlockState var1) {
      this.this$0 = var1;
   }

   public int compare(IProperty var1, IProperty var2) {
      return var1.getName().compareTo(var2.getName());
   }
}
