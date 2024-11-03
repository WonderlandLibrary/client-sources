package net.augustus.utils;

import net.minecraft.util.BlockPos;

public class Node {
   private final BlockPos blockPos;

   public Node(BlockPos blockPos) {
      this.blockPos = blockPos;
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }
}
