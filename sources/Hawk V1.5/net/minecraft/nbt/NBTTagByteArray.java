package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {
   private static final String __OBFID = "CL_00001213";
   private byte[] data;

   public String toString() {
      return String.valueOf((new StringBuilder("[")).append(this.data.length).append(" bytes]"));
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      int var4 = var1.readInt();
      var3.read((long)(8 * var4));
      this.data = new byte[var4];
      var1.readFully(this.data);
   }

   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.data);
   }

   public byte[] getByteArray() {
      return this.data;
   }

   public byte getId() {
      return 7;
   }

   NBTTagByteArray() {
   }

   public NBTTagByteArray(byte[] var1) {
      this.data = var1;
   }

   public boolean equals(Object var1) {
      return super.equals(var1) ? Arrays.equals(this.data, ((NBTTagByteArray)var1).data) : false;
   }

   public NBTBase copy() {
      byte[] var1 = new byte[this.data.length];
      System.arraycopy(this.data, 0, var1, 0, this.data.length);
      return new NBTTagByteArray(var1);
   }

   void write(DataOutput var1) throws IOException {
      var1.writeInt(this.data.length);
      var1.write(this.data);
   }
}
