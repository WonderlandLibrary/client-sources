package net;

import java.io.RandomAccessFile;

public class s2 {
   public static long a(RandomAccessFile var0) {
      return var0.length();
   }

   public static void b(RandomAccessFile var0, int var1) {
      var0.writeInt(var1);
   }

   public static void a(RandomAccessFile var0, int var1) {
      var0.write(var1);
   }

   public static void a(RandomAccessFile var0, long var1) {
      var0.seek(var1);
   }

   public static int c(RandomAccessFile var0) {
      return var0.readInt();
   }

   public static byte b(RandomAccessFile var0) {
      return var0.readByte();
   }

   public static int a(RandomAccessFile var0, byte[] var1) {
      return var0.read(var1);
   }

   public static void b(RandomAccessFile var0, byte[] var1) {
      var0.write(var1);
   }

   public static void c(RandomAccessFile var0, int var1) {
      var0.writeByte(var1);
   }

   public static void a(RandomAccessFile var0, byte[] var1, int var2, int var3) {
      var0.write(var1, var2, var3);
   }
}
