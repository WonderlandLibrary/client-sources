package net.minecraft.client.renderer;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.optifine.CustomColors;
import net.optifine.RenderEnv;

public class BlockFluidRenderer {
   private TextureAtlasSprite[] atlasSpritesLava = new TextureAtlasSprite[2];
   private TextureAtlasSprite[] atlasSpritesWater = new TextureAtlasSprite[2];
   private static final String a = "CL_00002519";

   public BlockFluidRenderer() {
      this.initAtlasSprites();
   }

   protected void initAtlasSprites() {
      TextureMap var1 = Minecraft.getInstance().getTextureMapBlocks();
      this.atlasSpritesLava[0] = var1.getAtlasSprite("minecraft:blocks/lava_still");
      this.atlasSpritesLava[1] = var1.getAtlasSprite("minecraft:blocks/lava_flow");
      this.atlasSpritesWater[0] = var1.getAtlasSprite("minecraft:blocks/water_still");
      this.atlasSpritesWater[1] = var1.getAtlasSprite("minecraft:blocks/water_flow");
   }

   public boolean renderFluid(IBlockAccess var1, IBlockState var2, BlockPos var3, WorldRenderer var4) {
      BlockLiquid var5 = (BlockLiquid)var2.getBlock();
      var5.setBlockBoundsBasedOnState(var1, var3);
      TextureAtlasSprite[] var6 = var5.getMaterial() == Material.lava?this.atlasSpritesLava:this.atlasSpritesWater;
      RenderEnv var7 = RenderEnv.getInstance(var1, var2, var3);
      int var8 = CustomColors.getFluidColor(var1, var2, var3, var7);
      float var9 = (float)(var8 >> 16 & 255) / 255.0F;
      float var10 = (float)(var8 >> 8 & 255) / 255.0F;
      float var11 = (float)(var8 & 255) / 255.0F;
      boolean var12 = var5.shouldSideBeRendered(var1, var3.up(), EnumFacing.UP);
      boolean var13 = var5.shouldSideBeRendered(var1, var3.down(), EnumFacing.DOWN);
      boolean[] var14 = var7.h();
      var14[0] = var5.shouldSideBeRendered(var1, var3.north(), EnumFacing.NORTH);
      var14[1] = var5.shouldSideBeRendered(var1, var3.south(), EnumFacing.SOUTH);
      var14[2] = var5.shouldSideBeRendered(var1, var3.west(), EnumFacing.WEST);
      var14[3] = var5.shouldSideBeRendered(var1, var3.east(), EnumFacing.EAST);
      if(!var14[0] && !var14[1] && !var14[2] && !var14[3]) {
         return false;
      } else {
         boolean var15 = false;
         float var16 = 0.5F;
         float var17 = 1.0F;
         float var18 = 0.8F;
         float var19 = 0.6F;
         Material var20 = var5.getMaterial();
         float var21 = this.getFluidHeight(var1, var3, var20);
         float var22 = this.getFluidHeight(var1, var3.south(), var20);
         float var23 = this.getFluidHeight(var1, var3.east().south(), var20);
         float var24 = this.getFluidHeight(var1, var3.east(), var20);
         double var25 = (double)var3.getX();
         double var27 = (double)var3.getY();
         double var29 = (double)var3.getZ();
         float var31 = 0.001F;
         var15 = true;
         TextureAtlasSprite var32 = var6[0];
         float var33 = (float)BlockLiquid.getFlowDirection(var1, var3, var20);
         if(var33 > -999.0F) {
            var32 = var6[1];
         }

         var4.setSprite(var32);
         var21 = var21 - var31;
         var22 = var22 - var31;
         var23 = var23 - var31;
         var24 = var24 - var31;
         float var34;
         float var35;
         float var36;
         float var37;
         float var38;
         float var39;
         float var40;
         float var41;
         if(var33 < -999.0F) {
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

         int var80 = var5.getMixedBrightnessForBlock(var1, var3);
         int var81 = var80 >> 16 & '\uffff';
         int var82 = var80 & '\uffff';
         float var45 = var17 * var9;
         float var46 = var17 * var10;
         float var47 = var17 * var11;
         var4.pos(var25 + 0.0D, var27 + (double)var21, var29 + 0.0D).color(var45, var46, var47, 1.0F).tex((double)var34, (double)var38).lightmap(var81, var82).endVertex();
         var4.pos(var25 + 0.0D, var27 + (double)var22, var29 + 1.0D).color(var45, var46, var47, 1.0F).tex((double)var35, (double)var39).lightmap(var81, var82).endVertex();
         var4.pos(var25 + 1.0D, var27 + (double)var23, var29 + 1.0D).color(var45, var46, var47, 1.0F).tex((double)var36, (double)var40).lightmap(var81, var82).endVertex();
         var4.pos(var25 + 1.0D, var27 + (double)var24, var29 + 0.0D).color(var45, var46, var47, 1.0F).tex((double)var37, (double)var41).lightmap(var81, var82).endVertex();
         if(var5.func_176364_g(var1, var3.up())) {
            var4.pos(var25 + 0.0D, var27 + (double)var21, var29 + 0.0D).color(var45, var46, var47, 1.0F).tex((double)var34, (double)var38).lightmap(var81, var82).endVertex();
            var4.pos(var25 + 1.0D, var27 + (double)var24, var29 + 0.0D).color(var45, var46, var47, 1.0F).tex((double)var37, (double)var41).lightmap(var81, var82).endVertex();
            var4.pos(var25 + 1.0D, var27 + (double)var23, var29 + 1.0D).color(var45, var46, var47, 1.0F).tex((double)var36, (double)var40).lightmap(var81, var82).endVertex();
            var4.pos(var25 + 0.0D, var27 + (double)var22, var29 + 1.0D).color(var45, var46, var47, 1.0F).tex((double)var35, (double)var39).lightmap(var81, var82).endVertex();
         }

         float var65 = var6[0].getMinU();
         var33 = var6[0].getMaxU();
         var34 = var6[0].getMinV();
         var35 = var6[0].getMaxV();
         int var74 = var5.getMixedBrightnessForBlock(var1, var3.down());
         int var76 = var74 >> 16 & '\uffff';
         int var78 = var74 & '\uffff';
         var4.pos(var25, var27, var29 + 1.0D).color(var16, var16, var16, 1.0F).tex((double)var65, (double)var35).lightmap(var76, var78).endVertex();
         var4.pos(var25, var27, var29).color(var16, var16, var16, 1.0F).tex((double)var65, (double)var34).lightmap(var76, var78).endVertex();
         var4.pos(var25 + 1.0D, var27, var29).color(var16, var16, var16, 1.0F).tex((double)var33, (double)var34).lightmap(var76, var78).endVertex();
         var4.pos(var25 + 1.0D, var27, var29 + 1.0D).color(var16, var16, var16, 1.0F).tex((double)var33, (double)var35).lightmap(var76, var78).endVertex();
         var15 = true;

         for(int var66 = 0; var66 < 4; ++var66) {
            int var68 = 0;
            int var70 = 0;
            --var70;
            if(var66 == 1) {
               ++var70;
            }

            if(var66 == 2) {
               --var68;
            }

            if(var66 == 3) {
               ++var68;
            }

            BlockPos var73 = var3.a(var68, 0, var70);
            TextureAtlasSprite var75 = var6[1];
            var4.setSprite(var75);
            if(var14[var66]) {
               double var77 = var25 + 1.0D;
               double var83 = var29 + (double)var31;
               double var79 = var29 + (double)var31;
               var15 = true;
               var47 = var75.getInterpolatedU(0.0D);
               float var48 = var75.getInterpolatedU(8.0D);
               float var49 = var75.getInterpolatedV((double)((1.0F - var21) * 16.0F * 0.5F));
               float var50 = var75.getInterpolatedV((double)((1.0F - var24) * 16.0F * 0.5F));
               float var51 = var75.getInterpolatedV(8.0D);
               int var52 = var5.getMixedBrightnessForBlock(var1, var73);
               int var53 = var52 >> 16 & '\uffff';
               int var54 = var52 & '\uffff';
               float var55 = var66 < 2?var18:var19;
               float var56 = var17 * var55 * var9;
               float var57 = var17 * var55 * var10;
               float var58 = var17 * var55 * var11;
               var4.pos(var25, var27 + (double)var21, var83).color(var56, var57, var58, 1.0F).tex((double)var47, (double)var49).lightmap(var53, var54).endVertex();
               var4.pos(var77, var27 + (double)var24, var79).color(var56, var57, var58, 1.0F).tex((double)var48, (double)var50).lightmap(var53, var54).endVertex();
               var4.pos(var77, var27 + 0.0D, var79).color(var56, var57, var58, 1.0F).tex((double)var48, (double)var51).lightmap(var53, var54).endVertex();
               var4.pos(var25, var27 + 0.0D, var83).color(var56, var57, var58, 1.0F).tex((double)var47, (double)var51).lightmap(var53, var54).endVertex();
               var4.pos(var25, var27 + 0.0D, var83).color(var56, var57, var58, 1.0F).tex((double)var47, (double)var51).lightmap(var53, var54).endVertex();
               var4.pos(var77, var27 + 0.0D, var79).color(var56, var57, var58, 1.0F).tex((double)var48, (double)var51).lightmap(var53, var54).endVertex();
               var4.pos(var77, var27 + (double)var24, var79).color(var56, var57, var58, 1.0F).tex((double)var48, (double)var50).lightmap(var53, var54).endVertex();
               var4.pos(var25, var27 + (double)var21, var83).color(var56, var57, var58, 1.0F).tex((double)var47, (double)var49).lightmap(var53, var54).endVertex();
            }
         }

         var4.setSprite((TextureAtlasSprite)null);
         return var15;
      }
   }

   private float getFluidHeight(IBlockAccess var1, BlockPos var2, Material var3) {
      int var11 = 0;
      float var5 = 0.0F;

      for(int var6 = 0; var6 < 4; ++var6) {
         BlockPos var7 = var2.a(-(var6 & 1), 0, -(var6 >> 1 & 1));
         if(var1.getBlockState(var7.up()).getBlock().getMaterial() == var3) {
            return 1.0F;
         }

         IBlockState var8 = var1.getBlockState(var7);
         Material var9 = var8.getBlock().getMaterial();
         if(var9 != var3) {
            if(!var9.isSolid()) {
               ++var5;
               ++var11;
            }
         } else {
            int var10 = ((Integer)var8.getValue(BlockLiquid.P)).intValue();
            if(var10 < 8) {
               ;
            }

            var5 = var5 + BlockLiquid.getLiquidHeightPercent(var10) * 10.0F;
            var11 += 10;
            var5 = var5 + BlockLiquid.getLiquidHeightPercent(var10);
            ++var11;
         }
      }

      return 1.0F - var5 / (float)var11;
   }
}
