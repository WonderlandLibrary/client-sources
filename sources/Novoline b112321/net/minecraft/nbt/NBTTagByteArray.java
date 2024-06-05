package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;

public class NBTTagByteArray extends NBTBase {
   private byte[] data;

   NBTTagByteArray() {
   }

   public NBTTagByteArray(byte[] var1) {
      this.data = var1;
   }

   void write(DataOutput var1) throws IOException {
      var1.writeInt(this.data.length);
      var1.write(this.data);
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.read(192L);
      int var4 = var1.readInt();
      var3.read((long)(8 * var4));
      this.data = new byte[var4];
      var1.readFully(this.data);
   }

   public byte getId() {
      return (byte)7;
   }

   public String toString() {
      return "[" + this.data.length + " bytes]";
   }

   public NBTBase copy() {
      byte[] var1 = new byte[this.data.length];
      System.arraycopy(this.data, 0, var1, 0, this.data.length);
      return new NBTTagByteArray(var1);
   }

   public boolean equals(Object var1) {
      return super.equals(var1) && Arrays.equals(this.data, ((NBTTagByteArray)var1).data);
   }

   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.data);
   }

   public byte[] getByteArray() {
      return this.data;
   }
}
