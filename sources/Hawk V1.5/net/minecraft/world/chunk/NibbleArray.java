package net.minecraft.world.chunk;

public class NibbleArray {
   private static final String __OBFID = "CL_00000371";
   private final byte[] data;

   public void setIndex(int var1, int var2) {
      int var3 = this.func_177478_c(var1);
      if (this.func_177479_b(var1)) {
         this.data[var3] = (byte)(this.data[var3] & 240 | var2 & 15);
      } else {
         this.data[var3] = (byte)(this.data[var3] & 15 | (var2 & 15) << 4);
      }

   }

   public int getFromIndex(int var1) {
      int var2 = this.func_177478_c(var1);
      return this.func_177479_b(var1) ? this.data[var2] & 15 : this.data[var2] >> 4 & 15;
   }

   public NibbleArray(byte[] var1) {
      this.data = var1;
      if (var1.length != 2048) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder("ChunkNibbleArrays should be 2048 bytes not: ")).append(var1.length)));
      }
   }

   private boolean func_177479_b(int var1) {
      return (var1 & 1) == 0;
   }

   public void set(int var1, int var2, int var3, int var4) {
      this.setIndex(this.getCoordinateIndex(var1, var2, var3), var4);
   }

   private int getCoordinateIndex(int var1, int var2, int var3) {
      return var2 << 8 | var3 << 4 | var1;
   }

   public int get(int var1, int var2, int var3) {
      return this.getFromIndex(this.getCoordinateIndex(var1, var2, var3));
   }

   public NibbleArray() {
      this.data = new byte[2048];
   }

   private int func_177478_c(int var1) {
      return var1 >> 1;
   }

   public byte[] getData() {
      return this.data;
   }
}
