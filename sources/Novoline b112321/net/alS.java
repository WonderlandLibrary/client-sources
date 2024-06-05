package net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class alS {
   private static int[] b;

   public static int m(ByteBuf var0) {
      return var0.readableBytes();
   }

   public static ByteBuf b(ByteBuf var0, byte[] var1) {
      return var0.readBytes(var1);
   }

   public static ByteBuf a(ByteBuf var0, byte[] var1) {
      return var0.writeBytes(var1);
   }

   public static ByteBuf f(ByteBuf var0, int var1) {
      return var0.writeByte(var1);
   }

   public static short i(ByteBuf var0) {
      return var0.readShort();
   }

   public static ByteBuf i(ByteBuf var0, int var1) {
      return var0.writeShort(var1);
   }

   public static ByteBufAllocator t(ByteBuf var0) {
      return var0.alloc();
   }

   public static boolean h(ByteBuf var0) {
      return var0.release();
   }

   public static ByteBuf a(ByteBuf var0, ByteBuf var1) {
      return var0.writeBytes(var1);
   }

   public static boolean d(ByteBuf var0) {
      return var0.isReadable();
   }

   public static ByteBuf s(ByteBuf var0) {
      return var0.clear();
   }

   public static short p(ByteBuf var0) {
      return var0.readUnsignedByte();
   }

   public static byte c(ByteBuf var0) {
      return var0.readByte();
   }

   public static boolean g(ByteBuf var0) {
      return var0.readBoolean();
   }

   public static long e(ByteBuf var0) {
      return var0.readLong();
   }

   public static ByteBuf a(ByteBuf var0, boolean var1) {
      return var0.writeBoolean(var1);
   }

   public static ByteBuf a(ByteBuf var0, long var1) {
      return var0.writeLong(var1);
   }

   public static int a(ByteBuf var0) {
      return var0.readInt();
   }

   public static ByteBuf b(ByteBuf var0, int var1) {
      return var0.writeInt(var1);
   }

   public static ByteBuf a(ByteBuf var0, int var1) {
      return var0.readerIndex(var1);
   }

   public static int r(ByteBuf var0) {
      return var0.readerIndex();
   }

   public static ByteBuf n(ByteBuf var0) {
      return var0.retain();
   }

   public static ByteBuf a(ByteBuf var0, byte[] var1, int var2, int var3) {
      return var0.writeBytes(var1, var2, var3);
   }

   public static int j(ByteBuf var0) {
      return var0.readUnsignedShort();
   }

   public static ByteBuf k(ByteBuf var0) {
      return var0.markReaderIndex();
   }

   public static ByteBuf o(ByteBuf var0) {
      return var0.resetReaderIndex();
   }

   public static ByteBuf j(ByteBuf var0, int var1) {
      return var0.readBytes(var1);
   }

   public static String a(ByteBuf var0, int var1, int var2, Charset var3) {
      return var0.toString(var1, var2, var3);
   }

   public static ByteBuf c(ByteBuf var0, int var1) {
      return var0.skipBytes(var1);
   }

   public static ByteBuf a(ByteBuf var0, ByteBuf var1, int var2) {
      return var0.writeBytes(var1, var2);
   }

   public static boolean e(ByteBuf var0, int var1) {
      return var0.isReadable(var1);
   }

   public static double l(ByteBuf var0) {
      return var0.readDouble();
   }

   public static ByteBuf a(ByteBuf var0, double var1) {
      return var0.writeDouble(var1);
   }

   public static byte[] b(ByteBuf var0) {
      return var0.array();
   }

   public static ByteBuf h(ByteBuf var0, int var1) {
      return var0.writeChar(var1);
   }

   public static ByteBuf b(ByteBuf var0, byte[] var1, int var2, int var3) {
      return var0.readBytes(var1, var2, var3);
   }

   public static int q(ByteBuf var0) {
      return var0.arrayOffset();
   }

   public static ByteBuf g(ByteBuf var0, int var1) {
      return var0.writerIndex(var1);
   }

   public static String a(ByteBuf var0, Charset var1) {
      return var0.toString(var1);
   }

   public static ByteBuf d(ByteBuf var0, int var1) {
      return var0.readSlice(var1);
   }

   public static ByteBuf a(ByteBuf var0, ByteOrder var1) {
      return var0.order(var1);
   }

   public static float f(ByteBuf var0) {
      return var0.readFloat();
   }

   public static ByteBuf a(ByteBuf var0, float var1) {
      return var0.writeFloat(var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[1]);
      }

   }
}
