package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;

public class NBTTagInt extends NBTBase.NBTPrimitive {
   private int data;

   NBTTagInt() {
   }

   public NBTTagInt(int data) {
      this.data = data;
   }

   public boolean equals(Object p_equals_1_) {
      if(super.equals(p_equals_1_)) {
         NBTTagInt nbttagint = (NBTTagInt)p_equals_1_;
         return this.data == nbttagint.data;
      } else {
         return false;
      }
   }

   public String toString() {
      return "" + this.data;
   }

   public int hashCode() {
      return super.hashCode() ^ this.data;
   }

   public byte getByte() {
      return (byte)(this.data & 255);
   }

   public short getShort() {
      return (short)(this.data & '\uffff');
   }

   public int getInt() {
      return this.data;
   }

   public long getLong() {
      return (long)this.data;
   }

   public float getFloat() {
      return (float)this.data;
   }

   public double getDouble() {
      return (double)this.data;
   }

   void write(DataOutput output) throws IOException {
      output.writeInt(this.data);
   }

   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
      sizeTracker.read(96L);
      this.data = input.readInt();
   }

   public byte getId() {
      return (byte)3;
   }

   public NBTBase copy() {
      return new NBTTagInt(this.data);
   }
}
