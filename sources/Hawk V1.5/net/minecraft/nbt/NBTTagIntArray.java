package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase {
   private int[] intArray;
   private static final String __OBFID = "CL_00001221";

   public String toString() {
      String var1 = "[";
      int[] var2 = this.intArray;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2[var4];
         var1 = String.valueOf((new StringBuilder(String.valueOf(var1))).append(var5).append(","));
      }

      return String.valueOf((new StringBuilder(String.valueOf(var1))).append("]"));
   }

   public NBTTagIntArray(int[] var1) {
      this.intArray = var1;
   }

   void write(DataOutput var1) throws IOException {
      var1.writeInt(this.intArray.length);

      for(int var2 = 0; var2 < this.intArray.length; ++var2) {
         var1.writeInt(this.intArray[var2]);
      }

   }

   public byte getId() {
      return 11;
   }

   public boolean equals(Object var1) {
      return super.equals(var1) ? Arrays.equals(this.intArray, ((NBTTagIntArray)var1).intArray) : false;
   }

   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.intArray);
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      int var4 = var1.readInt();
      var3.read((long)(32 * var4));
      this.intArray = new int[var4];

      for(int var5 = 0; var5 < var4; ++var5) {
         this.intArray[var5] = var1.readInt();
      }

   }

   public int[] getIntArray() {
      return this.intArray;
   }

   public NBTBase copy() {
      int[] var1 = new int[this.intArray.length];
      System.arraycopy(this.intArray, 0, var1, 0, this.intArray.length);
      return new NBTTagIntArray(var1);
   }

   NBTTagIntArray() {
   }
}
