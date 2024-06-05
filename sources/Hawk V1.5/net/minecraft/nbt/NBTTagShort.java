package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase.NBTPrimitive {
   private static final String __OBFID = "CL_00001227";
   private short data;

   public float getFloat() {
      return (float)this.data;
   }

   public String toString() {
      return String.valueOf((new StringBuilder()).append(this.data).append("s"));
   }

   public byte getByte() {
      return (byte)(this.data & 255);
   }

   public int hashCode() {
      return super.hashCode() ^ this.data;
   }

   void write(DataOutput var1) throws IOException {
      var1.writeShort(this.data);
   }

   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagShort var2 = (NBTTagShort)var1;
         return this.data == var2.data;
      } else {
         return false;
      }
   }

   public NBTTagShort(short var1) {
      this.data = var1;
   }

   public double getDouble() {
      return (double)this.data;
   }

   public byte getId() {
      return 2;
   }

   public NBTTagShort() {
   }

   public NBTBase copy() {
      return new NBTTagShort(this.data);
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.read(16L);
      this.data = var1.readShort();
   }

   public long getLong() {
      return (long)this.data;
   }

   public short getShort() {
      return this.data;
   }

   public int getInt() {
      return this.data;
   }
}
