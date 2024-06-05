package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagEnd;

public class CompressedStreamTools {
   public static NBTTagCompound readCompressed(InputStream var0) throws IOException {
      DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(var0)));
      DataInputStream var10000 = var1;

      NBTTagCompound var2;
      try {
         var2 = read(var10000, NBTSizeTracker.INFINITE);
      } finally {
         var1.close();
      }

      return var2;
   }

   public static void writeCompressed(NBTTagCompound var0, OutputStream var1) throws IOException {
      DataOutputStream var2 = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(var1)));
      NBTTagCompound var10000 = var0;
      DataOutputStream var10001 = var2;

      try {
         write(var10000, (DataOutput)var10001);
      } finally {
         var2.close();
      }

   }

   public static void safeWrite(NBTTagCompound var0, File var1) throws IOException {
      File var2 = new File(var1.getAbsolutePath() + "_tmp");
      if(var2.exists()) {
         var2.delete();
      }

      write(var0, var2);
      if(var1.exists()) {
         var1.delete();
      }

      if(var1.exists()) {
         throw new IOException("Failed to delete " + var1);
      } else {
         var2.renameTo(var1);
      }
   }

   public static void write(NBTTagCompound var0, File var1) throws IOException {
      DataOutputStream var2 = new DataOutputStream(new FileOutputStream(var1));
      NBTTagCompound var10000 = var0;
      DataOutputStream var10001 = var2;

      try {
         write(var10000, (DataOutput)var10001);
      } finally {
         var2.close();
      }

   }

   public static NBTTagCompound read(File var0) throws IOException {
      if(!var0.exists()) {
         return null;
      } else {
         DataInputStream var1 = new DataInputStream(new FileInputStream(var0));
         DataInputStream var10000 = var1;

         NBTTagCompound var2;
         try {
            var2 = read(var10000, NBTSizeTracker.INFINITE);
         } finally {
            var1.close();
         }

         return var2;
      }
   }

   public static NBTTagCompound read(DataInputStream var0) throws IOException {
      return read(var0, NBTSizeTracker.INFINITE);
   }

   public static NBTTagCompound read(DataInput var0, NBTSizeTracker var1) throws IOException {
      NBTBase var2 = a(var0, 0, var1);
      if(var2 instanceof NBTTagCompound) {
         return (NBTTagCompound)var2;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void write(NBTTagCompound var0, DataOutput var1) throws IOException {
      writeTag(var0, var1);
   }

   private static void writeTag(NBTBase var0, DataOutput var1) throws IOException {
      var1.writeByte(var0.getId());
      if(var0.getId() != 0) {
         var1.writeUTF("");
         var0.write(var1);
      }

   }

   private static NBTBase a(DataInput var0, int var1, NBTSizeTracker var2) throws IOException {
      byte var3 = var0.readByte();
      return new NBTTagEnd();
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
