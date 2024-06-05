package net;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class iX {
   private static final String d = "CL_00002568";
   private final int[] c;
   private final VertexFormat b;
   private TextureAtlasSprite[] a;

   public iX(int[] var1, VertexFormat var2, TextureAtlasSprite[] var3) {
      this.c = var1;
      this.b = var2;
      this.a = var3;
   }

   public iX(int[] var1, VertexFormat var2) {
      this.c = var1;
      this.b = var2;
   }

   public int[] b() {
      return this.c;
   }

   public int c() {
      return this.c.length / this.b.func_181719_f();
   }

   public VertexFormat a() {
      return this.b;
   }

   static TextureAtlasSprite[] a(iX var0) {
      return var0.a;
   }
}
