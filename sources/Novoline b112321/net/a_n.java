package net;

import net.minecraft.client.model.ModelRenderer;

public class a_n {
   private static boolean b;

   public static void a(ModelRenderer var0, float var1, float var2, float var3, int var4, int var5, int var6, float var7) {
      var0.addBox(var1, var2, var3, var4, var5, var6, var7);
   }

   public static void a(ModelRenderer var0, float var1, float var2, float var3) {
      var0.setRotationPoint(var1, var2, var3);
   }

   public static void c(ModelRenderer var0, float var1) {
      var0.render(var1);
   }

   public static ModelRenderer a(ModelRenderer var0, int var1, int var2) {
      return var0.setTextureSize(var1, var2);
   }

   public static void a(ModelRenderer var0, ModelRenderer var1) {
      var0.addChild(var1);
   }

   public static ModelRenderer a(ModelRenderer var0, float var1, float var2, float var3, int var4, int var5, int var6) {
      return var0.addBox(var1, var2, var3, var4, var5, var6);
   }

   public static void b(ModelRenderer var0, float var1) {
      var0.postRender(var1);
   }

   public static ModelRenderer b(ModelRenderer var0, int var1, int var2) {
      return var0.setTextureOffset(var1, var2);
   }

   public static void b(ModelRenderer var0, float var1, float var2, float var3, int var4, int var5, int var6, float var7) {
      var0.addSprite(var1, var2, var3, var4, var5, var6, var7);
   }

   public static ModelRenderer a(ModelRenderer var0, String var1, float var2, float var3, float var4, int var5, int var6, int var7) {
      return var0.addBox(var1, var2, var3, var4, var5, var6, var7);
   }

   public static ModelRenderer a(ModelRenderer var0, float var1, float var2, float var3, int var4, int var5, int var6, boolean var7) {
      return var0.addBox(var1, var2, var3, var4, var5, var6, var7);
   }

   public static void a(ModelRenderer var0, float var1) {
      var0.renderWithRotation(var1);
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
      if(b()) {
         b(true);
      }

   }
}
