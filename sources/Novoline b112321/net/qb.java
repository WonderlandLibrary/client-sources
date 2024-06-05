package net;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;

public class qb {
   public static void a(EntityFX var0, float var1) {
      var0.setAlphaF(var1);
   }

   public static void a(EntityFX var0, float var1, float var2, float var3) {
      var0.setRBGColorF(var1, var2, var3);
   }

   public static void a(EntityFX var0, int var1) {
      var0.setParticleTextureIndex(var1);
   }

   public static EntityFX c(EntityFX var0, float var1) {
      return var0.multiplyVelocity(var1);
   }

   public static float g(EntityFX var0) {
      return var0.getRedColorF();
   }

   public static float b(EntityFX var0) {
      return var0.getGreenColorF();
   }

   public static float d(EntityFX var0) {
      return var0.getBlueColorF();
   }

   public static void f(EntityFX var0) {
      var0.nextTextureIndexX();
   }

   public static int c(EntityFX var0) {
      return var0.getFXLayer();
   }

   public static float e(EntityFX var0) {
      return var0.getAlpha();
   }

   public static void a(EntityFX var0) {
      var0.onUpdate();
   }

   public static void a(EntityFX var0, WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      var0.renderParticle(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public static EntityFX b(EntityFX var0, float var1) {
      return var0.multipleParticleScaleBy(var1);
   }
}
