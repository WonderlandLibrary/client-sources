package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class NextTickListEntry implements Comparable {
   private static long nextTickEntryID;
   private final Block block;
   public final BlockPos position;
   public long scheduledTime;
   public int priority;
   private long tickEntryID;

   public NextTickListEntry(BlockPos var1, Block var2) {
      this.tickEntryID = (long)(nextTickEntryID++);
      this.position = var1;
      this.block = var2;
   }

   public boolean equals(Object var1) {
      if(!(var1 instanceof NextTickListEntry)) {
         return false;
      } else {
         NextTickListEntry var2 = (NextTickListEntry)var1;
         return this.position.equals(var2.position) && Block.isEqualTo(this.block, var2.block);
      }
   }

   public int hashCode() {
      return this.position.hashCode();
   }

   public NextTickListEntry setScheduledTime(long var1) {
      this.scheduledTime = var1;
      return this;
   }

   public void setPriority(int var1) {
      this.priority = var1;
   }

   public int compareTo(NextTickListEntry var1) {
      return this.scheduledTime < var1.scheduledTime?-1:(this.scheduledTime > var1.scheduledTime?1:(this.priority != var1.priority?this.priority - var1.priority:(this.tickEntryID < var1.tickEntryID?-1:(this.tickEntryID > var1.tickEntryID?1:0))));
   }

   public String toString() {
      return Block.getIdFromBlock(this.block) + ": " + this.position + ", " + this.scheduledTime + ", " + this.priority + ", " + this.tickEntryID;
   }

   public Block getBlock() {
      return this.block;
   }
}
