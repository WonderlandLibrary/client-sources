package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class BlockEventData {
   private final BlockPos position;
   private final Block blockType;
   private final int eventID;
   private final int eventParameter;

   public BlockEventData(BlockPos var1, Block var2, int var3, int var4) {
      this.position = var1;
      this.eventID = var3;
      this.eventParameter = var4;
      this.blockType = var2;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public int getEventID() {
      return this.eventID;
   }

   public int getEventParameter() {
      return this.eventParameter;
   }

   public Block getBlock() {
      return this.blockType;
   }

   public boolean equals(Object var1) {
      if(!(var1 instanceof BlockEventData)) {
         return false;
      } else {
         BlockEventData var2 = (BlockEventData)var1;
         return this.position.equals(var2.position) && this.eventID == var2.eventID && this.eventParameter == var2.eventParameter && this.blockType == var2.blockType;
      }
   }

   public String toString() {
      return "TE(" + this.position + ")," + this.eventID + "," + this.eventParameter + "," + this.blockType;
   }
}
