package net;

import java.nio.Buffer;
import java.nio.IntBuffer;

public class aw9 {
   public static IntBuffer b(IntBuffer var0, int[] var1) {
      return var0.get(var1);
   }

   public static IntBuffer a(IntBuffer var0, int var1, int var2) {
      return var0.put(var1, var2);
   }

   public static int a(IntBuffer var0) {
      return var0.position();
   }

   public static int g(IntBuffer var0) {
      return var0.limit();
   }

   public static int d(IntBuffer var0) {
      return var0.capacity();
   }

   public static int b(IntBuffer var0, int var1) {
      return var0.get(var1);
   }

   public static Buffer d(IntBuffer var0, int var1) {
      return var0.position(var1);
   }

   public static Buffer a(IntBuffer var0, int var1) {
      return var0.limit(var1);
   }

   public static int h(IntBuffer var0) {
      return var0.get();
   }

   public static Buffer i(IntBuffer var0) {
      return var0.flip();
   }

   public static Buffer f(IntBuffer var0) {
      return var0.clear();
   }

   public static IntBuffer c(IntBuffer var0, int var1) {
      return var0.put(var1);
   }

   public static IntBuffer a(IntBuffer var0, int[] var1, int var2, int var3) {
      return var0.put(var1, var2, var3);
   }

   public static IntBuffer a(IntBuffer var0, int[] var1) {
      return var0.put(var1);
   }

   public static int e(IntBuffer var0) {
      return var0.remaining();
   }

   public static IntBuffer c(IntBuffer var0) {
      return var0.slice();
   }

   public static IntBuffer a(IntBuffer var0, IntBuffer var1) {
      return var0.put(var1);
   }

   public static Buffer b(IntBuffer var0) {
      return var0.rewind();
   }
}
