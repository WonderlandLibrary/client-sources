package net;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.optifine.CustomColors;
import net.optifine.RenderEnv;

public class ahx {
   public static int a(ItemStack var0, int var1, int var2) {
      return CustomColors.getColorFromItemStack(var0, var1, var2);
   }

   public static void a() {
      CustomColors.updateUseDefaultGrassFoliageColors();
   }

   public static void b() {
      CustomColors.update();
   }

   public static Vec3 a(Vec3 var0, World var1, Entity var2, float var3) {
      return CustomColors.getWorldSkyColor(var0, var1, var2, var3);
   }

   public static int c(int var0) {
      return CustomColors.d(var0);
   }

   public static boolean a(World var0, float var1, int[] var2, boolean var3) {
      return CustomColors.a(var0, var1, var2, var3);
   }

   public static Vec3 a(Vec3 var0, WorldClient var1, Entity var2, float var3) {
      return CustomColors.getWorldFogColor(var0, var1, var2, var3);
   }

   public static Vec3 a(IBlockAccess var0, double var1, double var3, double var5) {
      return CustomColors.getUnderwaterColor(var0, var1, var3, var5);
   }

   public static float[] a(EnumDyeColor var0, float[] var1) {
      return CustomColors.getWolfCollarColors(var0, var1);
   }

   public static int a(int var0, int var1) {
      return CustomColors.a(var0, var1);
   }

   public static int b(int var0, int var1) {
      return CustomColors.b(var0, var1);
   }

   public static int a(float var0) {
      return CustomColors.getXpOrbColor(var0);
   }

   public static int a(IBlockAccess var0, IBlockState var1, BlockPos var2, RenderEnv var3) {
      return CustomColors.getFluidColor(var0, var1, var2, var3);
   }

   public static Vec3 a(Vec3 var0, IBlockAccess var1, double var2, double var4, double var6) {
      return CustomColors.getSkyColor(var0, var1, var2, var4, var6);
   }

   public static void a(EntityFX var0, IBlockAccess var1, double var2, double var4, double var6) {
      CustomColors.b(var0, var1, var2, var4, var6);
   }

   public static void b(EntityFX var0) {
      CustomColors.a(var0);
   }

   public static void a(EntityFX var0) {
      CustomColors.b(var0);
   }

   public static void b(EntityFX var0, IBlockAccess var1, double var2, double var4, double var6) {
      CustomColors.updateReddustFX(var0, var1, var2, var4, var6);
   }

   public static int a(BakedQuad var0, Block var1, IBlockAccess var2, BlockPos var3, RenderEnv var4) {
      return CustomColors.getColorMultiplier(var0, var1, var2, var3, var4);
   }

   public static float[] b(EnumDyeColor var0, float[] var1) {
      return CustomColors.getSheepColors(var0, var1);
   }

   public static int a(int var0) {
      return CustomColors.a(var0);
   }

   public static int b(int var0) {
      return CustomColors.c(var0);
   }
}
