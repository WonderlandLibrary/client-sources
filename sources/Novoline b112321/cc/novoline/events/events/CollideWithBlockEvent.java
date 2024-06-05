package cc.novoline.events.events;

import cc.novoline.events.events.callables.CancellableEvent;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class CollideWithBlockEvent extends CancellableEvent {
   public AxisAlignedBB boundingBox;
   private Block block;
   private BlockPos blockPos;

   public CollideWithBlockEvent(Block var1, BlockPos var2, AxisAlignedBB var3) {
      this.block = var1;
      this.blockPos = var2;
      this.boundingBox = var3;
   }

   public Block getBlock() {
      return this.block;
   }

   public void setBlock(Block var1) {
      this.block = var1;
   }

   public BlockPos getPos() {
      return this.blockPos;
   }

   public AxisAlignedBB getBoundingBox() {
      return this.boundingBox;
   }

   public void setBoundingBox(AxisAlignedBB var1) {
      this.boundingBox = var1;
   }

   public void setBlockPos(BlockPos var1) {
      this.blockPos = var1;
   }
}
