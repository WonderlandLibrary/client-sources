package net;

import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.WorldRenderer;

public class ae5 {
   public static void a(TexturedQuad var0) {
      var0.flipFace();
   }

   public static void a(TexturedQuad var0, WorldRenderer var1, float var2) {
      var0.draw(var1, var2);
   }
}
