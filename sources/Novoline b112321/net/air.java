package net;

import net.minecraft.client.renderer.RenderGlobal$ContainerLocalRenderInformation;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.optifine.RenderInfoLazy;

public class air {
   public static void a(RenderInfoLazy var0, RenderChunk var1) {
      var0.setRenderChunk(var1);
   }

   public static RenderGlobal$ContainerLocalRenderInformation a(RenderInfoLazy var0) {
      return var0.getRenderInfo();
   }
}
