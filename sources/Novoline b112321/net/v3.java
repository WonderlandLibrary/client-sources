package net;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository$Entry;

public class v3 {
   public static IResourcePack e(ResourcePackRepository$Entry var0) {
      return var0.getResourcePack();
   }

   public static String a(ResourcePackRepository$Entry var0) {
      return var0.getResourcePackName();
   }

   public static int d(ResourcePackRepository$Entry var0) {
      return var0.func_183027_f();
   }

   public static void b(ResourcePackRepository$Entry var0) {
      var0.updateResourcePack();
   }

   public static void c(ResourcePackRepository$Entry var0) {
      var0.closeResourcePack();
   }

   public static void a(ResourcePackRepository$Entry var0, TextureManager var1) {
      var0.bindTexturePackIcon(var1);
   }

   public static String f(ResourcePackRepository$Entry var0) {
      return var0.getTexturePackDescription();
   }
}
