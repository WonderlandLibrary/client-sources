package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;

class BlockRedstoneComparator$1 implements Predicate {
   final EnumFacing val$facing;
   final BlockRedstoneComparator this$0;

   BlockRedstoneComparator$1(BlockRedstoneComparator var1, EnumFacing var2) {
      this.this$0 = var1;
      this.val$facing = var2;
   }

   public boolean apply(Entity var1) {
      return var1.getHorizontalFacing() == this.val$facing;
   }
}
