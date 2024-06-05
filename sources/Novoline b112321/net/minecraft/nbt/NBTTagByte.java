package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTBase$NBTPrimitive;
import net.minecraft.nbt.NBTSizeTracker;

public class NBTTagByte extends NBTBase$NBTPrimitive {
   private byte data;

   NBTTagByte() {
   }

   public NBTTagByte(byte var1) {
      this.data = var1;
   }

   void write(DataOutput var1) throws IOException {
      var1.writeByte(this.data);
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.read(72L);
      this.data = var1.readByte();
   }

   public byte getId() {
      return (byte)1;
   }

   public String toString() {
      return "" + this.data + "b";
   }

   public NBTBase copy() {
      return new NBTTagByte(this.data);
   }

   public boolean equals(Object var1) {
      if(super.equals(var1)) {
         NBTTagByte var2 = (NBTTagByte)var1;
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
      return (short)this.data;
   }

   public byte getByte() {
      return this.data;
   }

   public double getDouble() {
      return (double)this.data;
   }

   public float getFloat() {
      return (float)this.data;
   }
}
