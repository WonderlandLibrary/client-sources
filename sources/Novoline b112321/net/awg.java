package net;

import java.util.Random;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class awg {
   public static void a(ModelBase var0, Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      var0.render(var1, var2, var3, var4, var5, var6, var7);
   }

   public static TextureOffset a(ModelBase var0, String var1) {
      return var0.getTextureOffset(var1);
   }

   public static void a(ModelBase var0, ModelBase var1) {
      var0.setModelAttributes(var1);
   }

   public static void a(ModelBase var0, EntityLivingBase var1, float var2, float var3, float var4) {
      var0.setLivingAnimations(var1, var2, var3, var4);
   }

   public static void a(ModelBase var0, float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      var0.setRotationAngles(var1, var2, var3, var4, var5, var6, var7);
   }

   public static ModelRenderer a(ModelBase var0, Random var1) {
      return var0.getRandomModelBox(var1);
   }
}
