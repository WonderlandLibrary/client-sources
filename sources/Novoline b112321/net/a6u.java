package net;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

public class a6u {
   private static String b;

   public static IntBuffer b(int var0) {
      return BufferUtils.createIntBuffer(var0);
   }

   public static ByteBuffer a(int var0) {
      return BufferUtils.createByteBuffer(var0);
   }

   public static FloatBuffer c(int var0) {
      return BufferUtils.createFloatBuffer(var0);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("iRYP5b");
      }

   }
}
