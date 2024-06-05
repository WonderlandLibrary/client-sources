package net;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class aQ4 {
   private static boolean b;

   public static EnumFacing d(BakedQuad var0) {
      return var0.getFace();
   }

   public static int[] f(BakedQuad var0) {
      return var0.getVertexDataSingle();
   }

   public static TextureAtlasSprite b(BakedQuad var0) {
      return var0.getSprite();
   }

   public static int[] e(BakedQuad var0) {
      return var0.getVertexData();
   }

   public static boolean a(BakedQuad var0) {
      return var0.hasTintIndex();
   }

   public static int c(BakedQuad var0) {
      return var0.getTintIndex();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!a()) {
         b(true);
      }

   }
}
