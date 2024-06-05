package net;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.shadersmod.client.SVertexBuilder;

public class y3 {
   public static void g(WorldRenderer var0) {
      SVertexBuilder.calcNormalChunkLayer(var0);
   }

   public static void a(IBlockState var0, BlockPos var1, IBlockAccess var2, WorldRenderer var3) {
      SVertexBuilder.pushEntity(var0, var1, var2, var3);
   }

   public static void b(WorldRenderer var0) {
      SVertexBuilder.popEntity(var0);
   }

   public static void f(WorldRenderer var0) {
      SVertexBuilder.initVertexBuilder(var0);
   }

   public static void c(WorldRenderer var0) {
      SVertexBuilder.endSetVertexFormat(var0);
   }

   public static void a(WorldRenderer var0, int[] var1) {
      SVertexBuilder.beginAddVertexData(var0, var1);
   }

   public static void e(WorldRenderer var0) {
      SVertexBuilder.endAddVertexData(var0);
   }

   public static void a(WorldRenderer var0) {
      SVertexBuilder.endAddVertex(var0);
   }

   public static void d(WorldRenderer var0) {
      SVertexBuilder.beginAddVertex(var0);
   }

   public static void a(int var0, int var1, int var2, WorldRenderer var3) {
      SVertexBuilder.drawArrays(var0, var1, var2, var3);
   }
}
