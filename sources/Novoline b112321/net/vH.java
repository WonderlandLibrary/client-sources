package net;

import java.io.DataOutput;

public class vH {
   public static void a(DataOutput var0, int var1) {
      var0.writeInt(var1);
   }

   public static void c(DataOutput var0, int var1) {
      var0.writeByte(var1);
   }

   public static void a(DataOutput var0, byte[] var1) {
      var0.write(var1);
   }

   public static void a(DataOutput var0, String var1) {
      var0.writeUTF(var1);
   }

   public static void b(DataOutput var0, int var1) {
      var0.writeShort(var1);
   }

   public static void a(DataOutput var0, long var1) {
      var0.writeLong(var1);
   }

   public static void a(DataOutput var0, double var1) {
      var0.writeDouble(var1);
   }

   public static void a(DataOutput var0, float var1) {
      var0.writeFloat(var1);
   }
}
