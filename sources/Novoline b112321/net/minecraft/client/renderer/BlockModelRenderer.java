package net.minecraft.client.renderer;

import cc.novoline.modules.visual.XRay;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.GP;
import net.minecraft.block.Block;
import net.minecraft.block.Block$EnumOffsetType;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer$AmbientOcclusionFace;
import net.minecraft.client.renderer.BlockModelRenderer$BlockModelRenderer$1;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.optifine.BetterGrass;
import net.optifine.BetterSnow;
import net.optifine.Config;
import net.optifine.ConnectedTextures;
import net.optifine.CustomColors;
import net.optifine.NaturalTextures;
import net.optifine.Reflector;
import net.optifine.RenderEnv;

public class BlockModelRenderer {
   private static final String b = "CL_00002518";
   private static float aoLightValueOpaque = 0.2F;
   public static CopyOnWriteArrayList listBlocks = new CopyOnWriteArrayList();

   public static void updateAoLightValue() {
      aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
   }

   public BlockModelRenderer() {
      if(Reflector.ForgeModContainer_forgeLightPipelineEnabled.exists()) {
         Reflector.setFieldValue(Reflector.ForgeModContainer_forgeLightPipelineEnabled, Boolean.FALSE);
      }

   }

   public boolean renderModel(IBlockAccess var1, IBakedModel var2, IBlockState var3, BlockPos var4, WorldRenderer var5) {
      Block var6 = var3.getBlock();
      var6.setBlockBoundsBasedOnState(var1, var4);
      return this.renderModel(var1, var2, var3, var4, var5, true);
   }

   public boolean renderModel(IBlockAccess param1, IBakedModel param2, IBlockState param3, BlockPos param4, WorldRenderer param5, boolean param6) {
      // $FF: Couldn't be decompiled
   }

   public boolean renderModelAmbientOcclusion(IBlockAccess var1, IBakedModel var2, Block var3, BlockPos var4, WorldRenderer var5, boolean var6) {
      return this.renderModelAmbientOcclusion(var1, var2, var3, var1.getBlockState(var4), var4, var5, var6);
   }

   public boolean renderModelAmbientOcclusion(IBlockAccess var1, IBakedModel var2, Block var3, IBlockState var4, BlockPos var5, WorldRenderer var6, boolean var7) {
      boolean var8 = false;
      RenderEnv var9 = null;

      for(EnumFacing var13 : EnumFacing.VALUES) {
         List var14 = var2.getFaceQuads(var13);
         if(!var14.isEmpty()) {
            BlockPos var15 = var5.offset(var13);
            if(var3.shouldSideBeRendered(var1, var15, var13)) {
               if(XRay.isEnabled && XRay.showESP()) {
                  Block var16 = Minecraft.getInstance().world.getBlockState(var5).getBlock();
                  if(var16 instanceof BlockOre || var16 instanceof BlockRedstoneOre) {
                     double var17 = (double)var5.getX();
                     double var19 = (double)var5.getY();
                     double var21 = (double)var5.getZ();
                     if(Minecraft.getInstance().player.getDistance(var17, var21) <= (double)XRay.getDistance()) {
                        BlockPos var23 = new BlockPos(var17, var19, var21);
                        if(!XRay.blockPosList.contains(var23)) {
                           XRay.blockPosList.add(var23);
                        }
                     }
                  }
               }

               var9 = RenderEnv.getInstance(var1, var4, var5);
               if(!var9.isBreakingAnimation(var14) && Config.T()) {
                  var14 = BetterGrass.getFaceQuads(var1, var3, var5, var13, var14);
               }

               this.renderModelAmbientOcclusionQuads(var1, var3, var5, var6, var14, var9);
               var8 = true;
            }
         }
      }

      List var24 = var2.getGeneralQuads();
      if(!var24.isEmpty()) {
         var9 = RenderEnv.getInstance(var1, var4, var5);
         this.renderModelAmbientOcclusionQuads(var1, var3, var5, var6, var24, var9);
         var8 = true;
      }

      if(Config.isBetterSnow() && !var9.g() && BetterSnow.shouldRender(var1, var3, var4, var5)) {
         IBakedModel var25 = BetterSnow.getModelSnowLayer();
         IBlockState var26 = BetterSnow.getStateSnowLayer();
         this.renderModelAmbientOcclusion(var1, var25, var26.getBlock(), var26, var5, var6, true);
      }

      return var8;
   }

   public boolean renderModelStandard(IBlockAccess var1, IBakedModel var2, Block var3, BlockPos var4, WorldRenderer var5, boolean var6) {
      return this.renderModelStandard(var1, var2, var3, var1.getBlockState(var4), var4, var5, var6);
   }

   public boolean renderModelStandard(IBlockAccess var1, IBakedModel var2, Block var3, IBlockState var4, BlockPos var5, WorldRenderer var6, boolean var7) {
      boolean var8 = false;
      RenderEnv var9 = null;

      for(EnumFacing var13 : EnumFacing.VALUES) {
         List var14 = var2.getFaceQuads(var13);
         if(!var14.isEmpty()) {
            BlockPos var15 = var5.offset(var13);
            if(var3.shouldSideBeRendered(var1, var15, var13)) {
               var9 = RenderEnv.getInstance(var1, var4, var5);
               if(!var9.isBreakingAnimation(var14) && Config.T()) {
                  var14 = BetterGrass.getFaceQuads(var1, var3, var5, var13, var14);
               }

               int var16 = var3.getMixedBrightnessForBlock(var1, var15);
               this.renderModelStandardQuads(var1, var3, var5, var13, var16, false, var6, var14, var9);
               var8 = true;
            }
         }
      }

      List var17 = var2.getGeneralQuads();
      if(!var17.isEmpty()) {
         var9 = RenderEnv.getInstance(var1, var4, var5);
         this.renderModelStandardQuads(var1, var3, var5, (EnumFacing)null, -1, true, var6, var17, var9);
         var8 = true;
      }

      if(Config.isBetterSnow() && !var9.g() && BetterSnow.shouldRender(var1, var3, var4, var5) && BetterSnow.shouldRender(var1, var3, var4, var5)) {
         IBakedModel var18 = BetterSnow.getModelSnowLayer();
         IBlockState var19 = BetterSnow.getStateSnowLayer();
         this.renderModelStandard(var1, var18, var19.getBlock(), var19, var5, var6, true);
      }

      return var8;
   }

   private void renderModelAmbientOcclusionQuads(IBlockAccess var1, Block var2, BlockPos var3, WorldRenderer var4, List var5, RenderEnv var6) {
      float[] var7 = var6.getQuadBounds();
      BitSet var8 = var6.getBoundsFlags();
      BlockModelRenderer$AmbientOcclusionFace var9 = var6.getAoFace();
      IBlockState var10 = var6.getBlockState();
      double var11 = (double)var3.getX();
      double var13 = (double)var3.getY();
      double var15 = (double)var3.getZ();
      Block$EnumOffsetType var17 = var2.getOffsetType();
      if(var17 != Block$EnumOffsetType.NONE) {
         long var18 = MathHelper.getPositionRandom(var3);
         var11 += ((double)((float)(var18 >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
         var15 += ((double)((float)(var18 >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
         if(var17 == Block$EnumOffsetType.XYZ) {
            var13 += ((double)((float)(var18 >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
         }
      }

      for(Object var19 : var5) {
         BakedQuad var20 = (BakedQuad)var19;
         if(!var6.a(var20)) {
            BakedQuad var21 = var20;
            if(Config.k()) {
               var20 = ConnectedTextures.a(var1, var10, var3, var20, var6);
            }

            if(var20 == var21 && Config.isNaturalTextures()) {
               var20 = NaturalTextures.a(var3, var20);
            }
         }

         this.fillQuadBounds(var2, var20.getVertexData(), var20.getFace(), var7, var8);
         GP.a(var9, var1, var2, var3, var20.getFace(), var7, var8);
         if(var4.isMultiTexture()) {
            var4.addVertexData(var20.getVertexDataSingle());
            var4.putSprite(var20.getSprite());
         } else {
            var4.addVertexData(var20.getVertexData());
         }

         var4.putBrightness4(BlockModelRenderer$AmbientOcclusionFace.access$000(var9)[0], BlockModelRenderer$AmbientOcclusionFace.access$000(var9)[1], BlockModelRenderer$AmbientOcclusionFace.access$000(var9)[2], BlockModelRenderer$AmbientOcclusionFace.access$000(var9)[3]);
         int var27 = CustomColors.getColorMultiplier(var20, var2, var1, var3, var6);
         if(!var20.hasTintIndex() && var27 == -1) {
            var4.putColorMultiplier(BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[0], BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[0], BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[0], 4);
            var4.putColorMultiplier(BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[1], BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[1], BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[1], 3);
            var4.putColorMultiplier(BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[2], BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[2], BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[2], 2);
            var4.putColorMultiplier(BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[3], BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[3], BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[3], 1);
         } else {
            int var22;
            if(var27 != -1) {
               var22 = var27;
            } else {
               var22 = var2.colorMultiplier(var1, var3, var20.getTintIndex());
            }

            if(EntityRenderer.anaglyphEnable) {
               var22 = TextureUtil.anaglyphColor(var22);
            }

            float var23 = (float)(var22 >> 16 & 255) / 255.0F;
            float var24 = (float)(var22 >> 8 & 255) / 255.0F;
            float var25 = (float)(var22 & 255) / 255.0F;
            var4.putColorMultiplier(BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[0] * var23, BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[0] * var24, BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[0] * var25, 4);
            var4.putColorMultiplier(BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[1] * var23, BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[1] * var24, BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[1] * var25, 3);
            var4.putColorMultiplier(BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[2] * var23, BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[2] * var24, BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[2] * var25, 2);
            var4.putColorMultiplier(BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[3] * var23, BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[3] * var24, BlockModelRenderer$AmbientOcclusionFace.access$100(var9)[3] * var25, 1);
         }

         var4.putPosition(var11, var13, var15);
      }

   }

   private void fillQuadBounds(Block var1, int[] var2, EnumFacing var3, float[] var4, BitSet var5) {
      float var6 = 32.0F;
      float var7 = 32.0F;
      float var8 = 32.0F;
      float var9 = -32.0F;
      float var10 = -32.0F;
      float var11 = -32.0F;
      int var12 = var2.length / 4;

      for(int var13 = 0; var13 < 4; ++var13) {
         float var14 = Float.intBitsToFloat(var2[var13 * var12]);
         float var15 = Float.intBitsToFloat(var2[var13 * var12 + 1]);
         float var16 = Float.intBitsToFloat(var2[var13 * var12 + 2]);
         var6 = Math.min(var6, var14);
         var7 = Math.min(var7, var15);
         var8 = Math.min(var8, var16);
         var9 = Math.max(var9, var14);
         var10 = Math.max(var10, var15);
         var11 = Math.max(var11, var16);
      }

      var4[EnumFacing.WEST.getIndex()] = var6;
      var4[EnumFacing.EAST.getIndex()] = var9;
      var4[EnumFacing.DOWN.getIndex()] = var7;
      var4[EnumFacing.UP.getIndex()] = var10;
      var4[EnumFacing.NORTH.getIndex()] = var8;
      var4[EnumFacing.SOUTH.getIndex()] = var11;
      var4[EnumFacing.WEST.getIndex() + EnumFacing.VALUES.length] = 1.0F - var6;
      var4[EnumFacing.EAST.getIndex() + EnumFacing.VALUES.length] = 1.0F - var9;
      var4[EnumFacing.DOWN.getIndex() + EnumFacing.VALUES.length] = 1.0F - var7;
      var4[EnumFacing.UP.getIndex() + EnumFacing.VALUES.length] = 1.0F - var10;
      var4[EnumFacing.NORTH.getIndex() + EnumFacing.VALUES.length] = 1.0F - var8;
      var4[EnumFacing.SOUTH.getIndex() + EnumFacing.VALUES.length] = 1.0F - var11;
      float var17 = 1.0E-4F;
      float var18 = 0.9999F;
      switch(BlockModelRenderer$BlockModelRenderer$1.field_178290_a[var3.ordinal()]) {
      case 1:
         var5.set(1, var6 >= 1.0E-4F || var8 >= 1.0E-4F || var9 <= 0.9999F || var11 <= 0.9999F);
         var5.set(0, (var7 < 1.0E-4F || var1.isFullCube()) && var7 == var10);
         break;
      case 2:
         var5.set(1, var6 >= 1.0E-4F || var8 >= 1.0E-4F || var9 <= 0.9999F || var11 <= 0.9999F);
         var5.set(0, (var10 > 0.9999F || var1.isFullCube()) && var7 == var10);
         break;
      case 3:
         var5.set(1, var6 >= 1.0E-4F || var7 >= 1.0E-4F || var9 <= 0.9999F || var10 <= 0.9999F);
         var5.set(0, (var8 < 1.0E-4F || var1.isFullCube()) && var8 == var11);
         break;
      case 4:
         var5.set(1, var6 >= 1.0E-4F || var7 >= 1.0E-4F || var9 <= 0.9999F || var10 <= 0.9999F);
         var5.set(0, (var11 > 0.9999F || var1.isFullCube()) && var8 == var11);
         break;
      case 5:
         var5.set(1, var7 >= 1.0E-4F || var8 >= 1.0E-4F || var10 <= 0.9999F || var11 <= 0.9999F);
         var5.set(0, (var6 < 1.0E-4F || var1.isFullCube()) && var6 == var9);
         break;
      case 6:
         var5.set(1, var7 >= 1.0E-4F || var8 >= 1.0E-4F || var10 <= 0.9999F || var11 <= 0.9999F);
         var5.set(0, (var9 > 0.9999F || var1.isFullCube()) && var6 == var9);
      }

   }

   private void renderModelStandardQuads(IBlockAccess var1, Block var2, BlockPos var3, EnumFacing var4, int var5, boolean var6, WorldRenderer var7, List var8, RenderEnv var9) {
      BitSet var10 = var9.getBoundsFlags();
      IBlockState var11 = var9.getBlockState();
      double var12 = (double)var3.getX();
      double var14 = (double)var3.getY();
      double var16 = (double)var3.getZ();
      Block$EnumOffsetType var18 = var2.getOffsetType();
      if(var18 != Block$EnumOffsetType.NONE) {
         int var19 = var3.getX();
         int var20 = var3.getZ();
         long var21 = (long)(var19 * 3129871) ^ (long)var20 * 116129781L;
         var21 = var21 * var21 * 42317861L + var21 * 11L;
         var12 += ((double)((float)(var21 >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
         var16 += ((double)((float)(var21 >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
         if(var18 == Block$EnumOffsetType.XYZ) {
            var14 += ((double)((float)(var21 >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
         }
      }

      for(Object var29 : var8) {
         BakedQuad var31 = (BakedQuad)var29;
         if(!var9.a(var31)) {
            BakedQuad var22 = var31;
            if(Config.k()) {
               var31 = ConnectedTextures.a(var1, var11, var3, var31, var9);
            }

            if(var31 == var22 && Config.isNaturalTextures()) {
               var31 = NaturalTextures.a(var3, var31);
            }
         }

         this.fillQuadBounds(var2, var31.getVertexData(), var31.getFace(), (float[])null, var10);
         var5 = var10.get(0)?var2.getMixedBrightnessForBlock(var1, var3.offset(var31.getFace())):var2.getMixedBrightnessForBlock(var1, var3);
         if(var7.isMultiTexture()) {
            var7.addVertexData(var31.getVertexDataSingle());
            var7.putSprite(var31.getSprite());
         } else {
            var7.addVertexData(var31.getVertexData());
         }

         var7.putBrightness4(var5, var5, var5, var5);
         int var32 = CustomColors.getColorMultiplier(var31, var2, var1, var3, var9);
         if(var31.hasTintIndex() || var32 != -1) {
            int var23;
            if(var32 != -1) {
               var23 = var32;
            } else {
               var23 = var2.colorMultiplier(var1, var3, var31.getTintIndex());
            }

            if(EntityRenderer.anaglyphEnable) {
               var23 = TextureUtil.anaglyphColor(var23);
            }

            float var24 = (float)(var23 >> 16 & 255) / 255.0F;
            float var25 = (float)(var23 >> 8 & 255) / 255.0F;
            float var26 = (float)(var23 & 255) / 255.0F;
            var7.putColorMultiplier(var24, var25, var26, 4);
            var7.putColorMultiplier(var24, var25, var26, 3);
            var7.putColorMultiplier(var24, var25, var26, 2);
            var7.putColorMultiplier(var24, var25, var26, 1);
         }

         var7.putPosition(var12, var14, var16);
      }

   }

   public void renderModelBrightnessColor(IBakedModel var1, float var2, float var3, float var4, float var5) {
      for(EnumFacing var9 : EnumFacing.VALUES) {
         this.renderModelBrightnessColorQuads(var2, var3, var4, var5, var1.getFaceQuads(var9));
      }

      this.renderModelBrightnessColorQuads(var2, var3, var4, var5, var1.getGeneralQuads());
   }

   public void renderModelBrightness(IBakedModel var1, IBlockState var2, float var3, boolean var4) {
      Block var5 = var2.getBlock();
      var5.setBlockBoundsForItemRender();
      GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
      int var6 = var5.getRenderColor(var5.getStateForEntityRender(var2));
      if(EntityRenderer.anaglyphEnable) {
         var6 = TextureUtil.anaglyphColor(var6);
      }

      float var7 = (float)(var6 >> 16 & 255) / 255.0F;
      float var8 = (float)(var6 >> 8 & 255) / 255.0F;
      float var9 = (float)(var6 & 255) / 255.0F;
      GlStateManager.color(var3, var3, var3, 1.0F);
      this.renderModelBrightnessColor(var1, var3, var7, var8, var9);
   }

   private void renderModelBrightnessColorQuads(float var1, float var2, float var3, float var4, List var5) {
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();

      for(Object var9 : var5) {
         BakedQuad var10 = (BakedQuad)var9;
         var7.begin(7, DefaultVertexFormats.ITEM);
         var7.addVertexData(var10.getVertexData());
         if(var10.hasTintIndex()) {
            var7.putColorRGB_F4(var2 * var1, var3 * var1, var4 * var1);
         } else {
            var7.putColorRGB_F4(var1, var1, var1);
         }

         Vec3i var11 = var10.getFace().getDirectionVec();
         var7.putNormal((float)var11.getX(), (float)var11.getY(), (float)var11.getZ());
         var6.draw();
      }

   }

   public static float fixAoLightValue(float var0) {
      return var0 == 0.2F?aoLightValueOpaque:var0;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
