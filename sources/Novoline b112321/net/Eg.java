package net;

import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.IResourceManager;
import net.shadersmod.client.MultiTexID;

public class Eg {
   public static void a(ITextureObject var0, boolean var1, boolean var2) {
      var0.setBlurMipmap(var1, var2);
   }

   public static void c(ITextureObject var0) {
      var0.restoreLastBlurMipmap();
   }

   public static int b(ITextureObject var0) {
      return var0.getGlTextureId();
   }

   public static MultiTexID a(ITextureObject var0) {
      return var0.getMultiTexID();
   }

   public static void a(ITextureObject var0, IResourceManager var1) {
      var0.loadTexture(var1);
   }
}
