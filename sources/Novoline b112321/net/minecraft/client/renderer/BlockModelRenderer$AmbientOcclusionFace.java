package net.minecraft.client.renderer;

import java.util.BitSet;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockModelRenderer$EnumNeighborInfo;
import net.minecraft.client.renderer.BlockModelRenderer$VertexTranslations;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockModelRenderer$AmbientOcclusionFace {
   private final float[] vertexColorMultiplier = new float[4];
   private final int[] vertexBrightness = new int[4];
   private static final String b = "CL_00002515";

   public BlockModelRenderer$AmbientOcclusionFace(BlockModelRenderer var1) {
   }

   public BlockModelRenderer$AmbientOcclusionFace() {
   }

   public void updateVertexBrightness(IBlockAccess var1, Block var2, BlockPos var3, EnumFacing var4, float[] var5, BitSet var6) {
      BlockPos var7 = var6.get(0)?var3.offset(var4):var3;
      BlockModelRenderer$EnumNeighborInfo var8 = BlockModelRenderer$EnumNeighborInfo.getNeighbourInfo(var4);
      BlockPos var9 = var7.offset(var8.field_178276_g[0]);
      BlockPos var10 = var7.offset(var8.field_178276_g[1]);
      BlockPos var11 = var7.offset(var8.field_178276_g[2]);
      BlockPos var12 = var7.offset(var8.field_178276_g[3]);
      int var13 = var2.getMixedBrightnessForBlock(var1, var9);
      int var14 = var2.getMixedBrightnessForBlock(var1, var10);
      int var15 = var2.getMixedBrightnessForBlock(var1, var11);
      int var16 = var2.getMixedBrightnessForBlock(var1, var12);
      float var17 = BlockModelRenderer.fixAoLightValue(var1.getBlockState(var9).getBlock().getAmbientOcclusionLightValue());
      float var18 = BlockModelRenderer.fixAoLightValue(var1.getBlockState(var10).getBlock().getAmbientOcclusionLightValue());
      float var19 = BlockModelRenderer.fixAoLightValue(var1.getBlockState(var11).getBlock().getAmbientOcclusionLightValue());
      float var20 = BlockModelRenderer.fixAoLightValue(var1.getBlockState(var12).getBlock().getAmbientOcclusionLightValue());
      boolean var21 = var1.getBlockState(var9.offset(var4)).getBlock().isTranslucent();
      boolean var22 = var1.getBlockState(var10.offset(var4)).getBlock().isTranslucent();
      boolean var23 = var1.getBlockState(var11.offset(var4)).getBlock().isTranslucent();
      boolean var24 = var1.getBlockState(var12.offset(var4)).getBlock().isTranslucent();
      int var33 = var2.getMixedBrightnessForBlock(var1, var3);
      if(var6.get(0) || !var1.getBlockState(var3.offset(var4)).getBlock().isOpaqueCube()) {
         var33 = var2.getMixedBrightnessForBlock(var1, var3.offset(var4));
      }

      float var34 = var6.get(0)?var1.getBlockState(var7).getBlock().getAmbientOcclusionLightValue():var1.getBlockState(var3).getBlock().getAmbientOcclusionLightValue();
      var34 = BlockModelRenderer.fixAoLightValue(var34);
      BlockModelRenderer$VertexTranslations var35 = BlockModelRenderer$VertexTranslations.getVertexTranslations(var4);
      float var36 = (var20 + var17 + var17 + var34) * 0.25F;
      float var37 = (var19 + var17 + var17 + var34) * 0.25F;
      float var38 = (var19 + var18 + var18 + var34) * 0.25F;
      float var39 = (var20 + var18 + var18 + var34) * 0.25F;
      if(var6.get(1) && var8.field_178289_i) {
         float var40 = var5[var8.field_178286_j[0].field_178229_m] * var5[var8.field_178286_j[1].field_178229_m];
         float var41 = var5[var8.field_178286_j[2].field_178229_m] * var5[var8.field_178286_j[3].field_178229_m];
         float var42 = var5[var8.field_178286_j[4].field_178229_m] * var5[var8.field_178286_j[5].field_178229_m];
         float var43 = var5[var8.field_178286_j[6].field_178229_m] * var5[var8.field_178286_j[7].field_178229_m];
         float var44 = var5[var8.field_178287_k[0].field_178229_m] * var5[var8.field_178287_k[1].field_178229_m];
         float var45 = var5[var8.field_178287_k[2].field_178229_m] * var5[var8.field_178287_k[3].field_178229_m];
         float var46 = var5[var8.field_178287_k[4].field_178229_m] * var5[var8.field_178287_k[5].field_178229_m];
         float var47 = var5[var8.field_178287_k[6].field_178229_m] * var5[var8.field_178287_k[7].field_178229_m];
         float var48 = var5[var8.field_178284_l[0].field_178229_m] * var5[var8.field_178284_l[1].field_178229_m];
         float var49 = var5[var8.field_178284_l[2].field_178229_m] * var5[var8.field_178284_l[3].field_178229_m];
         float var50 = var5[var8.field_178284_l[4].field_178229_m] * var5[var8.field_178284_l[5].field_178229_m];
         float var51 = var5[var8.field_178284_l[6].field_178229_m] * var5[var8.field_178284_l[7].field_178229_m];
         float var52 = var5[var8.field_178285_m[0].field_178229_m] * var5[var8.field_178285_m[1].field_178229_m];
         float var53 = var5[var8.field_178285_m[2].field_178229_m] * var5[var8.field_178285_m[3].field_178229_m];
         float var54 = var5[var8.field_178285_m[4].field_178229_m] * var5[var8.field_178285_m[5].field_178229_m];
         float var55 = var5[var8.field_178285_m[6].field_178229_m] * var5[var8.field_178285_m[7].field_178229_m];
         this.vertexColorMultiplier[BlockModelRenderer$VertexTranslations.access$200(var35)] = var36 * var40 + var37 * var41 + var38 * var42 + var39 * var43;
         this.vertexColorMultiplier[BlockModelRenderer$VertexTranslations.access$300(var35)] = var36 * var44 + var37 * var45 + var38 * var46 + var39 * var47;
         this.vertexColorMultiplier[BlockModelRenderer$VertexTranslations.access$400(var35)] = var36 * var48 + var37 * var49 + var38 * var50 + var39 * var51;
         this.vertexColorMultiplier[BlockModelRenderer$VertexTranslations.access$500(var35)] = var36 * var52 + var37 * var53 + var38 * var54 + var39 * var55;
         int var56 = this.getAoBrightness(var16, var13, var13, var33);
         int var57 = this.getAoBrightness(var15, var13, var13, var33);
         int var58 = this.getAoBrightness(var15, var14, var14, var33);
         int var59 = this.getAoBrightness(var16, var14, var14, var33);
         this.vertexBrightness[BlockModelRenderer$VertexTranslations.access$200(var35)] = this.getVertexBrightness(var56, var57, var58, var59, var40, var41, var42, var43);
         this.vertexBrightness[BlockModelRenderer$VertexTranslations.access$300(var35)] = this.getVertexBrightness(var56, var57, var58, var59, var44, var45, var46, var47);
         this.vertexBrightness[BlockModelRenderer$VertexTranslations.access$400(var35)] = this.getVertexBrightness(var56, var57, var58, var59, var48, var49, var50, var51);
         this.vertexBrightness[BlockModelRenderer$VertexTranslations.access$500(var35)] = this.getVertexBrightness(var56, var57, var58, var59, var52, var53, var54, var55);
      } else {
         this.vertexBrightness[BlockModelRenderer$VertexTranslations.access$200(var35)] = this.getAoBrightness(var16, var13, var13, var33);
         this.vertexBrightness[BlockModelRenderer$VertexTranslations.access$300(var35)] = this.getAoBrightness(var15, var13, var13, var33);
         this.vertexBrightness[BlockModelRenderer$VertexTranslations.access$400(var35)] = this.getAoBrightness(var15, var14, var14, var33);
         this.vertexBrightness[BlockModelRenderer$VertexTranslations.access$500(var35)] = this.getAoBrightness(var16, var14, var14, var33);
         this.vertexColorMultiplier[BlockModelRenderer$VertexTranslations.access$200(var35)] = var36;
         this.vertexColorMultiplier[BlockModelRenderer$VertexTranslations.access$300(var35)] = var37;
         this.vertexColorMultiplier[BlockModelRenderer$VertexTranslations.access$400(var35)] = var38;
         this.vertexColorMultiplier[BlockModelRenderer$VertexTranslations.access$500(var35)] = var39;
      }

   }

   private int getAoBrightness(int var1, int var2, int var3, int var4) {
      return var4 + var4 + var4 + var4 >> 2 & 16711935;
   }

   private int getVertexBrightness(int var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      int var9 = (int)((float)(var1 >> 16 & 255) * var5 + (float)(var2 >> 16 & 255) * var6 + (float)(var3 >> 16 & 255) * var7 + (float)(var4 >> 16 & 255) * var8) & 255;
      int var10 = (int)((float)(var1 & 255) * var5 + (float)(var2 & 255) * var6 + (float)(var3 & 255) * var7 + (float)(var4 & 255) * var8) & 255;
      return var9 << 16 | var10;
   }

   static int[] access$000(BlockModelRenderer$AmbientOcclusionFace var0) {
      return var0.vertexBrightness;
   }

   static float[] access$100(BlockModelRenderer$AmbientOcclusionFace var0) {
      return var0.vertexColorMultiplier;
   }
}
