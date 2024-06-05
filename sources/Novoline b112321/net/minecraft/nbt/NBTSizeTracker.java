package net.minecraft.nbt;

import net.minecraft.nbt.NBTSizeTracker$1;

public class NBTSizeTracker {
   public static final NBTSizeTracker INFINITE = new NBTSizeTracker$1(0L);
   private final long max;
   private long read;

   public NBTSizeTracker(long var1) {
      this.max = var1;
   }

   public void read(long var1) {
      this.read += var1 / 8L;
      if(this.read > this.max) {
         throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.read + "bytes where max allowed: " + this.max);
      }
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
