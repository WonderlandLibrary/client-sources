package net;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.vertex.VertexBuffer;

public class A5 {
   public static void a(VertexBuffer var0, ByteBuffer var1) {
      var0.func_181722_a(var1);
   }

   public static void a(VertexBuffer var0) {
      var0.deleteGlBuffers();
   }

   public static void c(VertexBuffer var0) {
      var0.bindBuffer();
   }

   public static void a(VertexBuffer var0, int var1) {
      var0.drawArrays(var1);
   }

   public static void b(VertexBuffer var0) {
      var0.unbindBuffer();
   }
}
