package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTBase$NBTPrimitive;
import net.minecraft.nbt.NBTSizeTracker;

public class NBTTagShort extends NBTBase$NBTPrimitive {
   private short data;

   public NBTTagShort() {
   }

   public NBTTagShort(short var1) {
      this.data = var1;
   }

   void write(DataOutput var1) throws IOException {
      var1.writeShort(this.data);
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.read(80L);
      this.data = var1.readShort();
   }

   public byte getId() {
      return (byte)2;
   }

   public String toString() {
      return "" + this.data + "s";
   }

   public NBTBase copy() {
      return new NBTTagShort(this.data);
   }

   public boolean equals(Object var1) {
      if(super.equals(var1)) {
         NBTTagShort var2 = (NBTTagShort)var1;
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
      return this.data;
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
