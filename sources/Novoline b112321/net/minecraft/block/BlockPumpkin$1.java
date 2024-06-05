package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

final class BlockPumpkin$1 implements Predicate {
   public boolean apply(IBlockState var1) {
      return var1.getBlock() == Blocks.pumpkin || var1.getBlock() == Blocks.lit_pumpkin;
   }
}
