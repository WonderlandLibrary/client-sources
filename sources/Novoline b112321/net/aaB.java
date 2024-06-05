package net;

import net.acE;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class aaB {
   private static int b;

   public static void a(EffectRenderer var0, BlockPos var1, EnumFacing var2) {
      var0.addBlockHitEffects(var1, var2);
   }

   public static void b(EffectRenderer var0) {
      var0.updateEffects();
   }

   public static void a(EffectRenderer var0, World var1) {
      var0.clearEffects(var1);
   }

   public static void b(EffectRenderer var0, EntityFX var1) {
      var0.addEffect(var1);
   }

   public static void b(EffectRenderer var0, Entity var1, float var2) {
      var0.renderLitParticles(var1, var2);
   }

   public static void a(EffectRenderer var0, Entity var1, float var2) {
      var0.renderParticles(var1, var2);
   }

   public static void a(EffectRenderer var0, Entity var1, EnumParticleTypes var2) {
      var0.emitParticleAtEntity(var1, var2);
   }

   public static void c(EffectRenderer var0, EntityFX var1) {
      var0.moveToAlphaLayer(var1);
   }

   public static void a(EffectRenderer var0, EntityFX var1) {
      var0.moveToNoAlphaLayer(var1);
   }

   public static EntityFX a(EffectRenderer var0, int var1, double var2, double var4, double var6, double var8, double var10, double var12, int[] var14) {
      int var15 = c();
      EntityFX var10000 = var0.a(var1, var2, var4, var6, var8, var10, var12, var14);
      if(acE.b() == null) {
         ++var15;
         b(var15);
      }

      return var10000;
   }

   public static void a(EffectRenderer var0, BlockPos var1, IBlockState var2) {
      var0.addBlockDestroyEffects(var1, var2);
   }

   public static String a(EffectRenderer var0) {
      return var0.getStatistics();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 96;
   }

   static {
      if(b() != 0) {
         b(112);
      }

   }
}
