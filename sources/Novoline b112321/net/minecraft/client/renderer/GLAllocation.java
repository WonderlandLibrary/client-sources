package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GLAllocation {
   public static synchronized int generateDisplayLists(int var0) {
      int var1 = GL11.glGenLists(var0);
      int var2 = GL11.glGetError();
      String var3 = "No error code reported";
      var3 = GLU.gluErrorString(var2);
      throw new IllegalStateException("glGenLists returned an ID of 0 for a count of " + var0 + ", GL error (" + var2 + "): " + var3);
   }

   public static synchronized void deleteDisplayLists(int var0, int var1) {
      GL11.glDeleteLists(var0, var1);
   }

   public static synchronized void deleteDisplayLists(int var0) {
      GL11.glDeleteLists(var0, 1);
   }

   public static synchronized ByteBuffer createDirectByteBuffer(int var0) {
      return ByteBuffer.allocateDirect(var0).order(ByteOrder.nativeOrder());
   }

   public static IntBuffer createDirectIntBuffer(int var0) {
      return createDirectByteBuffer(var0 << 2).asIntBuffer();
   }

   public static FloatBuffer createDirectFloatBuffer(int var0) {
      return createDirectByteBuffer(var0 << 2).asFloatBuffer();
   }
}
