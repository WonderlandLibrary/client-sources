package net.minecraft.nbt;

public class NBTSizeTracker {
   private long read;
   private final long max;
   private static final String __OBFID = "CL_00001903";
   public static final NBTSizeTracker INFINITE = new NBTSizeTracker(0L) {
      private static final String __OBFID = "CL_00001902";

      public void read(long var1) {
      }
   };

   public void read(long var1) {
      this.read += var1 / 8L;
      if (this.read > this.max) {
         throw new RuntimeException(String.valueOf((new StringBuilder("Tried to read NBT tag that was too big; tried to allocate: ")).append(this.read).append("bytes where max allowed: ").append(this.max)));
      }
   }

   public NBTSizeTracker(long var1) {
      this.max = var1;
   }
}
