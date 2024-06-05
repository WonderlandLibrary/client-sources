package net.minecraft.world.chunk.storage;

public class NibbleArrayReader {
   public final byte[] data;
   private final int depthBits;
   private final int depthBitsPlusFour;

   public NibbleArrayReader(byte[] var1, int var2) {
      this.data = var1;
      this.depthBits = var2;
      this.depthBitsPlusFour = var2 + 4;
   }

   public int get(int var1, int var2, int var3) {
      int var4 = var1 << this.depthBitsPlusFour | var3 << this.depthBits | var2;
      int var5 = var4 >> 1;
      int var6 = var4 & 1;
      return this.data[var5] & 15;
   }
}
