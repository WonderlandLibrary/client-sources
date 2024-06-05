package net;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class g3 {
   public static ByteBuffer a(byte[] var0) {
      return ByteBuffer.wrap(var0);
   }

   public static ByteBuffer a(ByteBuffer var0, ByteOrder var1) {
      return var0.order(var1);
   }

   public static ShortBuffer f(ByteBuffer var0) {
      return var0.asShortBuffer();
   }

   public static ByteBuffer b(int var0) {
      return ByteBuffer.allocate(var0);
   }

   public static ByteBuffer a(ByteBuffer var0, int var1) {
      return var0.putInt(var1);
   }

   public static Buffer g(ByteBuffer var0) {
      return var0.flip();
   }

   public static Buffer b(ByteBuffer var0, int var1) {
      return var0.position(var1);
   }

   public static int a(ByteBuffer var0) {
      return var0.limit();
   }

   public static ByteBuffer c(ByteBuffer var0) {
      return var0.slice();
   }

   public static IntBuffer e(ByteBuffer var0) {
      return var0.asIntBuffer();
   }

   public static FloatBuffer h(ByteBuffer var0) {
      return var0.asFloatBuffer();
   }

   public static ByteBuffer b(ByteBuffer var0, byte[] var1) {
      return var0.get(var1);
   }

   public static Buffer c(ByteBuffer var0, int var1) {
      return var0.limit(var1);
   }

   public static ByteBuffer a(ByteBuffer var0, byte[] var1) {
      return var0.put(var1);
   }

   public static ByteBuffer a(int var0) {
      return ByteBuffer.allocateDirect(var0);
   }

   public static int d(ByteBuffer var0) {
      return var0.capacity();
   }

   public static ByteBuffer a(ByteBuffer var0, ByteBuffer var1) {
      return var0.put(var1);
   }

   public static Buffer b(ByteBuffer var0) {
      return var0.rewind();
   }

   public static ByteBuffer a(ByteBuffer var0, int var1, float var2) {
      return var0.putFloat(var1, var2);
   }

   public static ByteBuffer a(ByteBuffer var0, int var1, int var2) {
      return var0.putInt(var1, var2);
   }

   public static ByteBuffer a(ByteBuffer var0, int var1, short var2) {
      return var0.putShort(var1, var2);
   }

   public static ByteBuffer a(ByteBuffer var0, int var1, byte var2) {
      return var0.put(var1, var2);
   }
}
