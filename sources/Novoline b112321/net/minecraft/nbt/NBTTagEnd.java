package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;

public class NBTTagEnd extends NBTBase {
   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.read(64L);
   }

   void write(DataOutput var1) throws IOException {
   }

   public byte getId() {
      return (byte)0;
   }

   public String toString() {
      return "END";
   }

   public NBTBase copy() {
      return new NBTTagEnd();
   }
}
