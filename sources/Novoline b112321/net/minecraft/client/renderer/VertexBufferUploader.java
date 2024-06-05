package net.minecraft.client.renderer;

import net.HN;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexBuffer;

public class VertexBufferUploader extends HN {
   private VertexBuffer vertexBuffer = null;

   public void func_181679_a(WorldRenderer var1) {
      var1.reset();
      this.vertexBuffer.func_181722_a(var1.getByteBuffer());
   }

   public void setVertexBuffer(VertexBuffer var1) {
      this.vertexBuffer = var1;
   }
}
