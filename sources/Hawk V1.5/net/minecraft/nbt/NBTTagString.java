package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {
   private static final String __OBFID = "CL_00001228";
   private String data;

   public boolean equals(Object var1) {
      if (!super.equals(var1)) {
         return false;
      } else {
         NBTTagString var2 = (NBTTagString)var1;
         return this.data == null && var2.data == null || this.data != null && this.data.equals(var2.data);
      }
   }

   public byte getId() {
      return 8;
   }

   public NBTTagString(String var1) {
      this.data = var1;
      if (var1 == null) {
         throw new IllegalArgumentException("Empty string not allowed");
      }
   }

   public NBTTagString() {
      this.data = "";
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      this.data = var1.readUTF();
      var3.read((long)(16 * this.data.length()));
   }

   void write(DataOutput var1) throws IOException {
      var1.writeUTF(this.data);
   }

   public NBTBase copy() {
      return new NBTTagString(this.data);
   }

   public String toString() {
      return String.valueOf((new StringBuilder("\"")).append(this.data.replace("\"", "\\\"")).append("\""));
   }

   public int hashCode() {
      return super.hashCode() ^ this.data.hashCode();
   }

   public boolean hasNoTags() {
      return this.data.isEmpty();
   }

   public String getString() {
      return this.data;
   }
}
