package net.minecraft.client.renderer;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import optifine.CustomColors;
import optifine.RenderEnv;

public class BlockFluidRenderer {
   private TextureAtlasSprite[] field_178271_b = new TextureAtlasSprite[2];
   private static final String __OBFID = "CL_00002519";
   private TextureAtlasSprite[] field_178272_a = new TextureAtlasSprite[2];

   public BlockFluidRenderer() {
      this.func_178268_a();
   }

   protected void func_178268_a() {
      TextureMap var1 = Minecraft.getMinecraft().getTextureMapBlocks();
      this.field_178272_a[0] = var1.getAtlasSprite("minecraft:blocks/lava_still");
      this.field_178272_a[1] = var1.getAtlasSprite("minecraft:blocks/lava_flow");
      this.field_178271_b[0] = var1.getAtlasSprite("minecraft:blocks/water_still");
      this.field_178271_b[1] = var1.getAtlasSprite("minecraft:blocks/water_flow");
   }

   public boolean func_178270_a(IBlockAccess var1, IBlockState var2, BlockPos var3, WorldRenderer var4) {
      BlockLiquid var5 = (BlockLiquid)var2.getBlock();
      var5.setBlockBoundsBasedOnState(var1, var3);
      TextureAtlasSprite[] var6 = var5.getMaterial() == Material.lava ? this.field_178272_a : this.field_178271_b;
      RenderEnv var7 = RenderEnv.getInstance(var1, var2, var3);
      int var8 = CustomColors.getFluidColor(var1, var2, var3, var7);
      float var9 = (float)(var8 >> 16 & 255) / 255.0F;
      float var10 = (float)(var8 >> 8 & 255) / 255.0F;
      float var11 = (float)(var8 & 255) / 255.0F;
      boolean var12 = var5.shouldSideBeRendered(var1, var3.offsetUp(), EnumFacing.UP);
      boolean var13 = var5.shouldSideBeRendered(var1, var3.offsetDown(), EnumFacing.DOWN);
      boolean[] var14 = var7.getBorderFlags();
      var14[0] = var5.shouldSideBeRendered(var1, var3.offsetNorth(), EnumFacing.NORTH);
      var14[1] = var5.shouldSideBeRendered(var1, var3.offsetSouth(), EnumFacing.SOUTH);
      var14[2] = var5.shouldSideBeRendered(var1, var3.offsetWest(), EnumFacing.WEST);
      var14[3] = var5.shouldSideBeRendered(var1, var3.offsetEast(), EnumFacing.EAST);
      if (!var12 && !var13 && !var14[0] && !var14[1] && !var14[2] && !var14[3]) {
         return false;
      } else {
         boolean var15 = false;
         float var16 = 0.5F;
         float var17 = 1.0F;
         float var18 = 0.8F;
         float var19 = 0.6F;
         Material var20 = var5.getMaterial();
         float var21 = this.func_178269_a(var1, var3, var20);
         float var22 = this.func_178269_a(var1, var3.offsetSouth(), var20);
         float var23 = this.func_178269_a(var1, var3.offsetEast().offsetSouth(), var20);
         float var24 = this.func_178269_a(var1, var3.offsetEast(), var20);
         double var25 = (double)var3.getX();
         double var27 = (double)var3.getY();
         double var29 = (double)var3.getZ();
         float var31 = 0.001F;
         TextureAtlasSprite var32;
         float var33;
         float var34;
         float var35;
         float var36;
         float var37;
         float var38;
         if (var12) {
            var15 = true;
            var32 = var6[0];
            var33 = (float)BlockLiquid.func_180689_a(var1, var3, var20);
            if (var33 > -999.0F) {
               var32 = var6[1];
            }

            var4.setSprite(var32);
            var21 -= var31;
            var22 -= var31;
            var23 -= var31;
            var24 -= var31;
            float var39;
            float var40;
            float var41;
            if (var33 < -999.0F) {
               var34 = var32.getInterpolatedU(0.0D);
               var38 = var32.getInterpolatedV(0.0D);
               var35 = var34;
               var39 = var32.getInterpolatedV(16.0D);
               var36 = var32.getInterpolatedU(16.0D);
               var40 = var39;
               var37 = var36;
               var41 = var38;
            } else {
               float var42 = MathHelper.sin(var33) * 0.25F;
               float var43 = MathHelper.cos(var33) * 0.25F;
               float var44 = 8.0F;
               var34 = var32.getInterpolatedU((double)(8.0F + (-var43 - var42) * 16.0F));
               var38 = var32.getInterpolatedV((double)(8.0F + (-var43 + var42) * 16.0F));
               var35 = var32.getInterpolatedU((double)(8.0F + (-var43 + var42) * 16.0F));
               var39 = var32.getInterpolatedV((double)(8.0F + (var43 + var42) * 16.0F));
               var36 = var32.getInterpolatedU((double)(8.0F + (var43 + var42) * 16.0F));
               var40 = var32.getInterpolatedV((double)(8.0F + (var43 - var42) * 16.0F));
               var37 = var32.getInterpolatedU((double)(8.0F + (var43 - var42) * 16.0F));
               var41 = var32.getInterpolatedV((double)(8.0F + (-var43 - var42) * 16.0F));
            }

            var4.func_178963_b(var5.getMixedBrightnessForBlock(var1, var3));
            var4.func_178986_b(var17 * var9, var17 * var10, var17 * var11);
            var4.addVertexWithUV(var25 + 0.0D, var27 + (double)var21, var29 + 0.0D, (double)var34, (double)var38);
            var4.addVertexWithUV(var25 + 0.0D, var27 + (double)var22, var29 + 1.0D, (double)var35, (double)var39);
            var4.addVertexWithUV(var25 + 1.0D, var27 + (double)var23, var29 + 1.0D, (double)var36, (double)var40);
            var4.addVertexWithUV(var25 + 1.0D, var27 + (double)var24, var29 + 0.0D, (double)var37, (double)var41);
            if (var5.func_176364_g(var1, var3.offsetUp())) {
               var4.addVertexWithUV(var25 + 0.0D, var27 + (double)var21, var29 + 0.0D, (double)var34, (double)var38);
               var4.addVertexWithUV(var25 + 1.0D, var27 + (double)var24, var29 + 0.0D, (double)var37, (double)var41);
               var4.addVertexWithUV(var25 + 1.0D, var27 + (double)var23, var29 + 1.0D, (double)var36, (double)var40);
               var4.addVertexWithUV(var25 + 0.0D, var27 + (double)var22, var29 + 1.0D, (double)var35, (double)var39);
            }
         }

         if (var13) {
            var4.func_178963_b(var5.getMixedBrightnessForBlock(var1, var3.offsetDown()));
            var4.func_178986_b(var16, var16, var16);
            var33 = var6[0].getMinU();
            var34 = var6[0].getMaxU();
            var35 = var6[0].getMinV();
            var36 = var6[0].getMaxV();
            var4.addVertexWithUV(var25, var27, var29 + 1.0D, (double)var33, (double)var36);
            var4.addVertexWithUV(var25, var27, var29, (double)var33, (double)var35);
            var4.addVertexWithUV(var25 + 1.0D, var27, var29, (double)var34, (double)var35);
            var4.addVertexWithUV(var25 + 1.0D, var27, var29 + 1.0D, (double)var34, (double)var36);
            var15 = true;
         }

         for(int var57 = 0; var57 < 4; ++var57) {
            int var58 = 0;
            int var59 = 0;
            if (var57 == 0) {
               --var59;
            }

            if (var57 == 1) {
               ++var59;
            }

            if (var57 == 2) {
               --var58;
            }

            if (var57 == 3) {
               ++var58;
            }

            BlockPos var60 = var3.add(var58, 0, var59);
            var32 = var6[1];
            var4.setSprite(var32);
            if (var14[var57]) {
               double var45;
               double var47;
               double var49;
               double var61;
               if (var57 == 0) {
                  var37 = var21;
                  var38 = var24;
                  var61 = var25;
                  var47 = var25 + 1.0D;
                  var45 = var29 + (double)var31;
                  var49 = var29 + (double)var31;
               } else if (var57 == 1) {
                  var37 = var23;
                  var38 = var22;
                  var61 = var25 + 1.0D;
                  var47 = var25;
                  var45 = var29 + 1.0D - (double)var31;
                  var49 = var29 + 1.0D - (double)var31;
               } else if (var57 == 2) {
                  var37 = var22;
                  var38 = var21;
                  var61 = var25 + (double)var31;
                  var47 = var25 + (double)var31;
                  var45 = var29 + 1.0D;
                  var49 = var29;
               } else {
                  var37 = var24;
                  var38 = var23;
                  var61 = var25 + 1.0D - (double)var31;
                  var47 = var25 + 1.0D - (double)var31;
                  var45 = var29;
                  var49 = var29 + 1.0D;
               }

               var15 = true;
               float var51 = var32.getInterpolatedU(0.0D);
               float var52 = var32.getInterpolatedU(8.0D);
               float var53 = var32.getInterpolatedV((double)((1.0F - var37) * 16.0F * 0.5F));
               float var54 = var32.getInterpolatedV((double)((1.0F - var38) * 16.0F * 0.5F));
               float var55 = var32.getInterpolatedV(8.0D);
               var4.func_178963_b(var5.getMixedBrightnessForBlock(var1, var60));
               float var56 = 1.0F;
               var56 *= var57 < 2 ? var18 : var19;
               var4.func_178986_b(var17 * var56 * var9, var17 * var56 * var10, var17 * var56 * var11);
               var4.addVertexWithUV(var61, var27 + (double)var37, var45, (double)var51, (double)var53);
               var4.addVertexWithUV(var47, var27 + (double)var38, var49, (double)var52, (double)var54);
               var4.addVertexWithUV(var47, var27 + 0.0D, var49, (double)var52, (double)var55);
               var4.addVertexWithUV(var61, var27 + 0.0D, var45, (double)var51, (double)var55);
               var4.addVertexWithUV(var61, var27 + 0.0D, var45, (double)var51, (double)var55);
               var4.addVertexWithUV(var47, var27 + 0.0D, var49, (double)var52, (double)var55);
               var4.addVertexWithUV(var47, var27 + (double)var38, var49, (double)var52, (double)var54);
               var4.addVertexWithUV(var61, var27 + (double)var37, var45, (double)var51, (double)var53);
            }
         }

         var4.setSprite((TextureAtlasSprite)null);
         return var15;
      }
   }

   private float func_178269_a(IBlockAccess var1, BlockPos var2, Material var3) {
      int var4 = 0;
      float var5 = 0.0F;

      for(int var6 = 0; var6 < 4; ++var6) {
         BlockPos var7 = var2.add(-(var6 & 1), 0, -(var6 >> 1 & 1));
         if (var1.getBlockState(var7.offsetUp()).getBlock().getMaterial() == var3) {
            return 1.0F;
         }

         IBlockState var8 = var1.getBlockState(var7);
         Material var9 = var8.getBlock().getMaterial();
         if (var9 == var3) {
            int var10 = (Integer)var8.getValue(BlockLiquid.LEVEL);
            if (var10 >= 8 || var10 == 0) {
               var5 += BlockLiquid.getLiquidHeightPercent(var10) * 10.0F;
               var4 += 10;
            }

            var5 += BlockLiquid.getLiquidHeightPercent(var10);
            ++var4;
         } else if (!var9.isSolid()) {
            ++var5;
            ++var4;
         }
      }

      return 1.0F - var5 / (float)var4;
   }
}
