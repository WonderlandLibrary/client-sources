package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase {
   private static final String __OBFID = "CL_00001219";

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
   }

   public NBTBase copy() {
      return new NBTTagEnd();
   }

   public String toString() {
      return "END";
   }

   public byte getId() {
      return 0;
   }

   void write(DataOutput var1) throws IOException {
   }
}
