package net.minecraft.block.state;

import com.google.common.base.Predicate;
import net.minecraft.block.state.BlockWorldState;

final class BlockWorldState$1 implements Predicate {
   final Predicate val$p_177510_0_;

   BlockWorldState$1(Predicate var1) {
      this.val$p_177510_0_ = var1;
   }

   public boolean apply(BlockWorldState var1) {
      return this.val$p_177510_0_.apply(var1.getBlockState());
   }
}
