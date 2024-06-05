package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTBase$NBTPrimitive;
import net.minecraft.nbt.NBTSizeTracker;

public class NBTTagInt extends NBTBase$NBTPrimitive {
   private int data;

   NBTTagInt() {
   }

   public NBTTagInt(int var1) {
      this.data = var1;
   }

   void write(DataOutput var1) throws IOException {
      var1.writeInt(this.data);
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.read(96L);
      this.data = var1.readInt();
   }

   public byte getId() {
      return (byte)3;
   }

   public String toString() {
      return "" + this.data;
   }

   public NBTBase copy() {
      return new NBTTagInt(this.data);
   }

   public boolean equals(Object var1) {
      if(super.equals(var1)) {
         NBTTagInt var2 = (NBTTagInt)var1;
         return this.data == var2.data;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return super.hashCode() ^ this.data;
   }

   public long getLong() {
      return (long)this.data;
   }

   public int getInt() {
      return this.data;
   }

   public short getShort() {
      return (short)(this.data & '\uffff');
   }

   public byte getByte() {
      return (byte)(this.data & 255);
   }

   public double getDouble() {
      return (double)this.data;
   }

   public float getFloat() {
      return (float)this.data;
   }
}
