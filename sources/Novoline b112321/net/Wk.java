package net;

import java.util.BitSet;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer$AmbientOcclusionFace;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.optifine.BlockPosM;
import net.optifine.RenderEnv;

public class Wk {
   public static IBlockState a(RenderEnv var0) {
      return var0.getBlockState();
   }

   public static int h(RenderEnv var0) {
      return var0.getMetadata();
   }

   public static BlockPosM d(RenderEnv var0) {
      return var0.getColorizerBlockPosM();
   }

   public static RenderEnv a(IBlockAccess var0, IBlockState var1, BlockPos var2) {
      return RenderEnv.getInstance(var0, var1, var2);
   }

   public static int f(RenderEnv var0) {
      return var0.getBlockId();
   }

   public static boolean[] b(RenderEnv var0) {
      return var0.h();
   }

   public static boolean a(RenderEnv var0, List var1) {
      return var0.isBreakingAnimation(var1);
   }

   public static boolean g(RenderEnv var0) {
      return var0.g();
   }

   public static float[] c(RenderEnv var0) {
      return var0.getQuadBounds();
   }

   public static BitSet e(RenderEnv var0) {
      return var0.getBoundsFlags();
   }

   public static BlockModelRenderer$AmbientOcclusionFace i(RenderEnv var0) {
      return var0.getAoFace();
   }

   public static boolean a(RenderEnv var0, BakedQuad var1) {
      return var0.a(var1);
   }
}
