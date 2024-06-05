package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.MathHelper;

public class NBTTagFloat extends NBTBase.NBTPrimitive {
   private float data;
   private static final String __OBFID = "CL_00001220";

   public int getInt() {
      return MathHelper.floor_float(this.data);
   }

   void write(DataOutput var1) throws IOException {
      var1.writeFloat(this.data);
   }

   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagFloat var2 = (NBTTagFloat)var1;
         return this.data == var2.data;
      } else {
         return false;
      }
   }

   public double getDouble() {
      return (double)this.data;
   }

   public long getLong() {
      return (long)this.data;
   }

   public String toString() {
      return String.valueOf((new StringBuilder()).append(this.data).append("f"));
   }

   public int hashCode() {
      return super.hashCode() ^ Float.floatToIntBits(this.data);
   }

   public NBTTagFloat(float var1) {
      this.data = var1;
   }

   NBTTagFloat() {
   }

   public float getFloat() {
      return this.data;
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.read(32L);
      this.data = var1.readFloat();
   }

   public NBTBase copy() {
      return new NBTTagFloat(this.data);
   }

   public byte getId() {
      return 5;
   }

   public byte getByte() {
      return (byte)(MathHelper.floor_float(this.data) & 255);
   }

   public short getShort() {
      return (short)(MathHelper.floor_float(this.data) & '\uffff');
   }
}
