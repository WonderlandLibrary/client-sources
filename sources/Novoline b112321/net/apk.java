package net;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.renderer.WorldRenderer;

public class apk {
   public static void a(ModelBox var0, WorldRenderer var1, float var2) {
      var0.render(var1, var2);
   }

   public static ModelBox a(ModelBox var0, String var1) {
      return var0.setBoxName(var1);
   }
}
