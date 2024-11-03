package net.augustus.events;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EventBlockBoundingBox extends Event {
   private AxisAlignedBB axisAlignedBB;
   private Block block;
   private BlockPos blockPos;

   public EventBlockBoundingBox(AxisAlignedBB axisAlignedBB, Block block, BlockPos blockPos) {
      this.axisAlignedBB = axisAlignedBB;
      this.block = block;
      this.blockPos = blockPos;
   }

   public AxisAlignedBB getAxisAlignedBB() {
      return this.axisAlignedBB;
   }

   public void setAxisAlignedBB(AxisAlignedBB axisAlignedBB) {
      this.axisAlignedBB = axisAlignedBB;
   }

   public Block getBlock() {
      return this.block;
   }

   public void setBlock(Block block) {
      this.block = block;
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }

   public void setBlockPos(BlockPos blockPos) {
      this.blockPos = blockPos;
   }
}
