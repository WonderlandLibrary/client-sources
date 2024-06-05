package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase.NBTPrimitive {
   private long data;
   private static final String __OBFID = "CL_00001225";

   public String toString() {
      return String.valueOf((new StringBuilder()).append(this.data).append("L"));
   }

   void write(DataOutput var1) throws IOException {
      var1.writeLong(this.data);
   }

   public double getDouble() {
      return (double)this.data;
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.read(64L);
      this.data = var1.readLong();
   }

   public float getFloat() {
      return (float)this.data;
   }

   public NBTTagLong(long var1) {
      this.data = var1;
   }

   public int getInt() {
      return (int)(this.data & -1L);
   }

   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagLong var2 = (NBTTagLong)var1;
         return this.data == var2.data;
      } else {
         return false;
      }
   }

   public byte getByte() {
      return (byte)((int)(this.data & 255L));
   }

   NBTTagLong() {
   }

   public NBTBase copy() {
      return new NBTTagLong(this.data);
   }

   public byte getId() {
      return 4;
   }

   public short getShort() {
      return (short)((int)(this.data & 65535L));
   }

   public int hashCode() {
      return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
   }

   public long getLong() {
      return this.data;
   }
}
