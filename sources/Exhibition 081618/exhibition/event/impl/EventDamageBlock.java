package exhibition.event.impl;

import exhibition.event.Event;
import net.minecraft.util.BlockPos;

public class EventDamageBlock extends Event {
   private BlockPos currentBlock;

   public void fire(BlockPos b) {
      this.setCurrentBlock(b);
      super.fire();
   }

   public BlockPos getCurrentBlock() {
      return this.currentBlock;
   }

   public void setCurrentBlock(BlockPos currentBlock) {
      this.currentBlock = currentBlock;
   }
}
