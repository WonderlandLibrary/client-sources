package net.minecraft.block;

import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone$EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

enum BlockSilverfish$EnumType$1 {
   BlockSilverfish$EnumType$1(int var3, String var4) {
   }

   public IBlockState getModelBlock() {
      return Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone$EnumType.STONE);
   }
}
