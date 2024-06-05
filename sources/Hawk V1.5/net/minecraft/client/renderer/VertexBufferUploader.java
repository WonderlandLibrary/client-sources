package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.VertexBuffer;

public class VertexBufferUploader extends WorldVertexBufferUploader {
   private VertexBuffer field_178179_a = null;
   private static final String __OBFID = "CL_00002532";

   public int draw(WorldRenderer var1, int var2) {
      var1.reset();
      this.field_178179_a.func_177360_a(var1.func_178966_f(), var1.func_178976_e());
      return var2;
   }

   public void func_178178_a(VertexBuffer var1) {
      this.field_178179_a = var1;
   }
}
