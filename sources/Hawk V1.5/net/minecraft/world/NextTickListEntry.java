package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class NextTickListEntry implements Comparable {
   private long tickEntryID;
   public int priority;
   private final Block field_151352_g;
   public final BlockPos field_180282_a;
   private static long nextTickEntryID;
   private static final String __OBFID = "CL_00000156";
   public long scheduledTime;

   public int hashCode() {
      return this.field_180282_a.hashCode();
   }

   public int compareTo(NextTickListEntry var1) {
      return this.scheduledTime < var1.scheduledTime ? -1 : (this.scheduledTime > var1.scheduledTime ? 1 : (this.priority != var1.priority ? this.priority - var1.priority : (this.tickEntryID < var1.tickEntryID ? -1 : (this.tickEntryID > var1.tickEntryID ? 1 : 0))));
   }

   public String toString() {
      return String.valueOf((new StringBuilder(String.valueOf(Block.getIdFromBlock(this.field_151352_g)))).append(": ").append(this.field_180282_a).append(", ").append(this.scheduledTime).append(", ").append(this.priority).append(", ").append(this.tickEntryID));
   }

   public NextTickListEntry setScheduledTime(long var1) {
      this.scheduledTime = var1;
      return this;
   }

   public NextTickListEntry(BlockPos var1, Block var2) {
      this.tickEntryID = (long)(nextTickEntryID++);
      this.field_180282_a = var1;
      this.field_151352_g = var2;
   }

   public void setPriority(int var1) {
      this.priority = var1;
   }

   public int compareTo(Object var1) {
      return this.compareTo((NextTickListEntry)var1);
   }

   public Block func_151351_a() {
      return this.field_151352_g;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof NextTickListEntry)) {
         return false;
      } else {
         NextTickListEntry var2 = (NextTickListEntry)var1;
         return this.field_180282_a.equals(var2.field_180282_a) && Block.isEqualTo(this.field_151352_g, var2.field_151352_g);
      }
   }
}
