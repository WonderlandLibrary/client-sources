package net.augustus.utils;

import net.augustus.utils.interfaces.MC;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public class EdgeCosts implements MC {
   public static boolean hasBlockCollision(BlockPos blockPos) {
      IBlockState iBlockState = mc.theWorld.getBlockState(blockPos);
      Block block = iBlockState.getBlock();
      return block.getCollisionBoundingBox(mc.theWorld, blockPos, iBlockState) != null;
   }
}
