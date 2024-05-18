package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.BitSet;
import java.util.List;

public class BlockModelRenderer
{
    private static final String HorizonCode_Horizon_È = "CL_00002518";
    private static float Â;
    
    static {
        BlockModelRenderer.Â = 0.2f;
    }
    
    public static void HorizonCode_Horizon_È() {
        BlockModelRenderer.Â = 1.0f - Config.Ç() * 0.8f;
    }
    
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccessIn, final IBakedModel modelIn, final IBlockState blockStateIn, final BlockPos blockPosIn, final WorldRenderer worldRendererIn) {
        final Block var6 = blockStateIn.Ý();
        var6.Ý(blockAccessIn, blockPosIn);
        return this.HorizonCode_Horizon_È(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, true);
    }
    
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccessIn, final IBakedModel modelIn, final IBlockState blockStateIn, final BlockPos blockPosIn, final WorldRenderer worldRendererIn, final boolean checkSides) {
        final boolean var7 = Minecraft.Ï­Ðƒà() && blockStateIn.Ý().Ø­áŒŠá() == 0 && modelIn.Â();
        try {
            final Block var8 = blockStateIn.Ý();
            return var7 ? this.HorizonCode_Horizon_È(blockAccessIn, modelIn, var8, blockStateIn, blockPosIn, worldRendererIn, checkSides) : this.Â(blockAccessIn, modelIn, var8, blockStateIn, blockPosIn, worldRendererIn, checkSides);
        }
        catch (Throwable var10) {
            final CrashReport var9 = CrashReport.HorizonCode_Horizon_È(var10, "Tesselating block model");
            final CrashReportCategory var11 = var9.HorizonCode_Horizon_È("Block model being tesselated");
            CrashReportCategory.HorizonCode_Horizon_È(var11, blockPosIn, blockStateIn);
            var11.HorizonCode_Horizon_È("Using AO", var7);
            throw new ReportedException(var9);
        }
    }
    
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccessIn, final IBakedModel modelIn, final Block blockIn, final BlockPos blockPosIn, final WorldRenderer worldRendererIn, final boolean checkSides) {
        return this.HorizonCode_Horizon_È(blockAccessIn, modelIn, blockIn, blockAccessIn.Â(blockPosIn), blockPosIn, worldRendererIn, checkSides);
    }
    
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccessIn, final IBakedModel modelIn, final Block blockIn, final IBlockState blockStateIn, final BlockPos blockPosIn, final WorldRenderer worldRendererIn, final boolean checkSides) {
        boolean var7 = false;
        worldRendererIn.Â(983055);
        RenderEnv renderEnv = null;
        for (final EnumFacing modelSnow : EnumFacing.à) {
            List stateSnow = modelIn.HorizonCode_Horizon_È(modelSnow);
            if (!stateSnow.isEmpty()) {
                final BlockPos var11 = blockPosIn.HorizonCode_Horizon_È(modelSnow);
                if (!checkSides || blockIn.HorizonCode_Horizon_È(blockAccessIn, var11, modelSnow)) {
                    if (renderEnv == null) {
                        renderEnv = RenderEnv.HorizonCode_Horizon_È(blockAccessIn, blockStateIn, blockPosIn);
                    }
                    if (!renderEnv.HorizonCode_Horizon_È(stateSnow) && Config.Ï­à()) {
                        stateSnow = BetterGrass.HorizonCode_Horizon_È(blockAccessIn, blockIn, blockPosIn, modelSnow, stateSnow);
                    }
                    this.HorizonCode_Horizon_È(blockAccessIn, blockIn, blockPosIn, worldRendererIn, stateSnow, renderEnv);
                    var7 = true;
                }
            }
        }
        final List var12 = modelIn.HorizonCode_Horizon_È();
        if (var12.size() > 0) {
            if (renderEnv == null) {
                renderEnv = RenderEnv.HorizonCode_Horizon_È(blockAccessIn, blockStateIn, blockPosIn);
            }
            this.HorizonCode_Horizon_È(blockAccessIn, blockIn, blockPosIn, worldRendererIn, var12, renderEnv);
            var7 = true;
        }
        if (renderEnv != null && Config.£Â() && !renderEnv.Ó() && BetterSnow.HorizonCode_Horizon_È(blockAccessIn, blockIn, blockStateIn, blockPosIn)) {
            final IBakedModel var13 = BetterSnow.Â();
            final IBlockState var14 = BetterSnow.Ý();
            this.HorizonCode_Horizon_È(blockAccessIn, var13, var14.Ý(), var14, blockPosIn, worldRendererIn, true);
        }
        return var7;
    }
    
    public boolean Â(final IBlockAccess blockAccessIn, final IBakedModel modelIn, final Block blockIn, final BlockPos blockPosIn, final WorldRenderer worldRendererIn, final boolean checkSides) {
        return this.Â(blockAccessIn, modelIn, blockIn, blockAccessIn.Â(blockPosIn), blockPosIn, worldRendererIn, checkSides);
    }
    
    public boolean Â(final IBlockAccess blockAccessIn, final IBakedModel modelIn, final Block blockIn, final IBlockState blockStateIn, final BlockPos blockPosIn, final WorldRenderer worldRendererIn, final boolean checkSides) {
        boolean var7 = false;
        RenderEnv renderEnv = null;
        for (final EnumFacing modelSnow : EnumFacing.à) {
            List stateSnow = modelIn.HorizonCode_Horizon_È(modelSnow);
            if (!stateSnow.isEmpty()) {
                final BlockPos var11 = blockPosIn.HorizonCode_Horizon_È(modelSnow);
                if (!checkSides || blockIn.HorizonCode_Horizon_È(blockAccessIn, var11, modelSnow)) {
                    if (renderEnv == null) {
                        renderEnv = RenderEnv.HorizonCode_Horizon_È(blockAccessIn, blockStateIn, blockPosIn);
                    }
                    if (!renderEnv.HorizonCode_Horizon_È(stateSnow) && Config.Ï­à()) {
                        stateSnow = BetterGrass.HorizonCode_Horizon_È(blockAccessIn, blockIn, blockPosIn, modelSnow, stateSnow);
                    }
                    final int var12 = blockIn.Â(blockAccessIn, var11);
                    this.HorizonCode_Horizon_È(blockAccessIn, blockIn, blockPosIn, modelSnow, var12, false, worldRendererIn, stateSnow, renderEnv);
                    var7 = true;
                }
            }
        }
        final List var13 = modelIn.HorizonCode_Horizon_È();
        if (var13.size() > 0) {
            if (renderEnv == null) {
                renderEnv = RenderEnv.HorizonCode_Horizon_È(blockAccessIn, blockStateIn, blockPosIn);
            }
            this.HorizonCode_Horizon_È(blockAccessIn, blockIn, blockPosIn, null, -1, true, worldRendererIn, var13, renderEnv);
            var7 = true;
        }
        if (renderEnv != null && Config.£Â() && !renderEnv.Ó() && BetterSnow.HorizonCode_Horizon_È(blockAccessIn, blockIn, blockStateIn, blockPosIn) && BetterSnow.HorizonCode_Horizon_È(blockAccessIn, blockIn, blockStateIn, blockPosIn)) {
            final IBakedModel var14 = BetterSnow.Â();
            final IBlockState var15 = BetterSnow.Ý();
            this.Â(blockAccessIn, var14, var15.Ý(), var15, blockPosIn, worldRendererIn, true);
        }
        return var7;
    }
    
    private void HorizonCode_Horizon_È(final IBlockAccess blockAccessIn, final Block blockIn, final BlockPos blockPosIn, final WorldRenderer worldRendererIn, final List listQuadsIn, final RenderEnv renderEnv) {
        final float[] quadBounds = renderEnv.Ý();
        final BitSet boundsFlags = renderEnv.Ø­áŒŠá();
        final HorizonCode_Horizon_È aoFaceIn = renderEnv.Âµá€();
        final IBlockState blockStateIn = renderEnv.à();
        double var9 = blockPosIn.HorizonCode_Horizon_È();
        double var10 = blockPosIn.Â();
        double var11 = blockPosIn.Ý();
        final Block.HorizonCode_Horizon_È var12 = blockIn.Âµà();
        if (var12 != Block.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final long var13 = MathHelper.HorizonCode_Horizon_È(blockPosIn);
            var9 += ((var13 >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
            var11 += ((var13 >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
            if (var12 == Block.HorizonCode_Horizon_È.Ý) {
                var10 += ((var13 >> 20 & 0xFL) / 15.0f - 1.0) * 0.2;
            }
        }
        for (BakedQuad var15 : listQuadsIn) {
            if (var15.HorizonCode_Horizon_È() != null && Config.ÇªÔ()) {
                var15 = ConnectedTextures.HorizonCode_Horizon_È(blockAccessIn, blockStateIn, blockPosIn, var15, renderEnv);
            }
            this.HorizonCode_Horizon_È(blockIn, var15.Â(), var15.Âµá€(), quadBounds, boundsFlags);
            aoFaceIn.HorizonCode_Horizon_È(blockAccessIn, blockIn, blockPosIn, var15.Âµá€(), quadBounds, boundsFlags);
            worldRendererIn.HorizonCode_Horizon_È(var15.Â());
            worldRendererIn.HorizonCode_Horizon_È(aoFaceIn.Â[0], aoFaceIn.Â[1], aoFaceIn.Â[2], aoFaceIn.Â[3]);
            final int colorMultiplier = CustomColorizer.HorizonCode_Horizon_È(var15, blockIn, blockAccessIn, blockPosIn, renderEnv);
            if (!var15.Ý() && colorMultiplier < 0) {
                worldRendererIn.HorizonCode_Horizon_È(aoFaceIn.HorizonCode_Horizon_È[0], aoFaceIn.HorizonCode_Horizon_È[0], aoFaceIn.HorizonCode_Horizon_È[0], 4);
                worldRendererIn.HorizonCode_Horizon_È(aoFaceIn.HorizonCode_Horizon_È[1], aoFaceIn.HorizonCode_Horizon_È[1], aoFaceIn.HorizonCode_Horizon_È[1], 3);
                worldRendererIn.HorizonCode_Horizon_È(aoFaceIn.HorizonCode_Horizon_È[2], aoFaceIn.HorizonCode_Horizon_È[2], aoFaceIn.HorizonCode_Horizon_È[2], 2);
                worldRendererIn.HorizonCode_Horizon_È(aoFaceIn.HorizonCode_Horizon_È[3], aoFaceIn.HorizonCode_Horizon_È[3], aoFaceIn.HorizonCode_Horizon_È[3], 1);
            }
            else {
                int var16;
                if (colorMultiplier >= 0) {
                    var16 = colorMultiplier;
                }
                else {
                    var16 = blockIn.HorizonCode_Horizon_È(blockAccessIn, blockPosIn, var15.Ø­áŒŠá());
                }
                if (EntityRenderer.HorizonCode_Horizon_È) {
                    var16 = TextureUtil.Ý(var16);
                }
                final float var17 = (var16 >> 16 & 0xFF) / 255.0f;
                final float var18 = (var16 >> 8 & 0xFF) / 255.0f;
                final float var19 = (var16 & 0xFF) / 255.0f;
                worldRendererIn.HorizonCode_Horizon_È(aoFaceIn.HorizonCode_Horizon_È[0] * var17, aoFaceIn.HorizonCode_Horizon_È[0] * var18, aoFaceIn.HorizonCode_Horizon_È[0] * var19, 4);
                worldRendererIn.HorizonCode_Horizon_È(aoFaceIn.HorizonCode_Horizon_È[1] * var17, aoFaceIn.HorizonCode_Horizon_È[1] * var18, aoFaceIn.HorizonCode_Horizon_È[1] * var19, 3);
                worldRendererIn.HorizonCode_Horizon_È(aoFaceIn.HorizonCode_Horizon_È[2] * var17, aoFaceIn.HorizonCode_Horizon_È[2] * var18, aoFaceIn.HorizonCode_Horizon_È[2] * var19, 2);
                worldRendererIn.HorizonCode_Horizon_È(aoFaceIn.HorizonCode_Horizon_È[3] * var17, aoFaceIn.HorizonCode_Horizon_È[3] * var18, aoFaceIn.HorizonCode_Horizon_È[3] * var19, 1);
            }
            worldRendererIn.HorizonCode_Horizon_È(var9, var10, var11);
        }
    }
    
    private void HorizonCode_Horizon_È(final Block blockIn, final int[] vertexData, final EnumFacing facingIn, final float[] quadBounds, final BitSet boundsFlags) {
        float var6 = 32.0f;
        float var7 = 32.0f;
        float var8 = 32.0f;
        float var9 = -32.0f;
        float var10 = -32.0f;
        float var11 = -32.0f;
        for (int var12 = 0; var12 < 4; ++var12) {
            final float var13 = Float.intBitsToFloat(vertexData[var12 * 7]);
            final float var14 = Float.intBitsToFloat(vertexData[var12 * 7 + 1]);
            final float var15 = Float.intBitsToFloat(vertexData[var12 * 7 + 2]);
            var6 = Math.min(var6, var13);
            var7 = Math.min(var7, var14);
            var8 = Math.min(var8, var15);
            var9 = Math.max(var9, var13);
            var10 = Math.max(var10, var14);
            var11 = Math.max(var11, var15);
        }
        if (quadBounds != null) {
            quadBounds[EnumFacing.Âµá€.Â()] = var6;
            quadBounds[EnumFacing.Ó.Â()] = var9;
            quadBounds[EnumFacing.HorizonCode_Horizon_È.Â()] = var7;
            quadBounds[EnumFacing.Â.Â()] = var10;
            quadBounds[EnumFacing.Ý.Â()] = var8;
            quadBounds[EnumFacing.Ø­áŒŠá.Â()] = var11;
            quadBounds[EnumFacing.Âµá€.Â() + EnumFacing.à.length] = 1.0f - var6;
            quadBounds[EnumFacing.Ó.Â() + EnumFacing.à.length] = 1.0f - var9;
            quadBounds[EnumFacing.HorizonCode_Horizon_È.Â() + EnumFacing.à.length] = 1.0f - var7;
            quadBounds[EnumFacing.Â.Â() + EnumFacing.à.length] = 1.0f - var10;
            quadBounds[EnumFacing.Ý.Â() + EnumFacing.à.length] = 1.0f - var8;
            quadBounds[EnumFacing.Ø­áŒŠá.Â() + EnumFacing.à.length] = 1.0f - var11;
        }
        final float var16 = 1.0E-4f;
        final float var13 = 0.9999f;
        switch (Ø­áŒŠá.HorizonCode_Horizon_È[facingIn.ordinal()]) {
            case 1: {
                boundsFlags.set(1, var6 >= 1.0E-4f || var8 >= 1.0E-4f || var9 <= 0.9999f || var11 <= 0.9999f);
                boundsFlags.set(0, (var7 < 1.0E-4f || blockIn.áˆºÑ¢Õ()) && var7 == var10);
                break;
            }
            case 2: {
                boundsFlags.set(1, var6 >= 1.0E-4f || var8 >= 1.0E-4f || var9 <= 0.9999f || var11 <= 0.9999f);
                boundsFlags.set(0, (var10 > 0.9999f || blockIn.áˆºÑ¢Õ()) && var7 == var10);
                break;
            }
            case 3: {
                boundsFlags.set(1, var6 >= 1.0E-4f || var7 >= 1.0E-4f || var9 <= 0.9999f || var10 <= 0.9999f);
                boundsFlags.set(0, (var8 < 1.0E-4f || blockIn.áˆºÑ¢Õ()) && var8 == var11);
                break;
            }
            case 4: {
                boundsFlags.set(1, var6 >= 1.0E-4f || var7 >= 1.0E-4f || var9 <= 0.9999f || var10 <= 0.9999f);
                boundsFlags.set(0, (var11 > 0.9999f || blockIn.áˆºÑ¢Õ()) && var8 == var11);
                break;
            }
            case 5: {
                boundsFlags.set(1, var7 >= 1.0E-4f || var8 >= 1.0E-4f || var10 <= 0.9999f || var11 <= 0.9999f);
                boundsFlags.set(0, (var6 < 1.0E-4f || blockIn.áˆºÑ¢Õ()) && var6 == var9);
                break;
            }
            case 6: {
                boundsFlags.set(1, var7 >= 1.0E-4f || var8 >= 1.0E-4f || var10 <= 0.9999f || var11 <= 0.9999f);
                boundsFlags.set(0, (var9 > 0.9999f || blockIn.áˆºÑ¢Õ()) && var6 == var9);
                break;
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final IBlockAccess blockAccessIn, final Block blockIn, final BlockPos blockPosIn, final EnumFacing faceIn, int brightnessIn, final boolean ownBrightness, final WorldRenderer worldRendererIn, final List listQuadsIn, final RenderEnv renderEnv) {
        final BitSet boundsFlags = renderEnv.Ø­áŒŠá();
        final IBlockState blockStateIn = renderEnv.à();
        double var10 = blockPosIn.HorizonCode_Horizon_È();
        double var11 = blockPosIn.Â();
        double var12 = blockPosIn.Ý();
        final Block.HorizonCode_Horizon_È var13 = blockIn.Âµà();
        if (var13 != Block.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final int var14 = blockPosIn.HorizonCode_Horizon_È();
            final int var15 = blockPosIn.Ý();
            long colorMultiplier = var14 * 3129871 ^ var15 * 116129781L;
            colorMultiplier = colorMultiplier * colorMultiplier * 42317861L + colorMultiplier * 11L;
            var10 += ((colorMultiplier >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
            var12 += ((colorMultiplier >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
            if (var13 == Block.HorizonCode_Horizon_È.Ý) {
                var11 += ((colorMultiplier >> 20 & 0xFL) / 15.0f - 1.0) * 0.2;
            }
        }
        for (BakedQuad var17 : listQuadsIn) {
            if (var17.HorizonCode_Horizon_È() != null && Config.ÇªÔ()) {
                var17 = ConnectedTextures.HorizonCode_Horizon_È(blockAccessIn, blockStateIn, blockPosIn, var17, renderEnv);
            }
            if (ownBrightness) {
                this.HorizonCode_Horizon_È(blockIn, var17.Â(), var17.Âµá€(), null, boundsFlags);
                brightnessIn = (boundsFlags.get(0) ? blockIn.Â(blockAccessIn, blockPosIn.HorizonCode_Horizon_È(var17.Âµá€())) : blockIn.Â(blockAccessIn, blockPosIn));
            }
            worldRendererIn.HorizonCode_Horizon_È(var17.Â());
            worldRendererIn.HorizonCode_Horizon_È(brightnessIn, brightnessIn, brightnessIn, brightnessIn);
            final int colorMultiplier2 = CustomColorizer.HorizonCode_Horizon_È(var17, blockIn, blockAccessIn, blockPosIn, renderEnv);
            if (var17.Ý() || colorMultiplier2 >= 0) {
                int var18;
                if (colorMultiplier2 >= 0) {
                    var18 = colorMultiplier2;
                }
                else {
                    var18 = blockIn.HorizonCode_Horizon_È(blockAccessIn, blockPosIn, var17.Ø­áŒŠá());
                }
                if (EntityRenderer.HorizonCode_Horizon_È) {
                    var18 = TextureUtil.Ý(var18);
                }
                final float var19 = (var18 >> 16 & 0xFF) / 255.0f;
                final float var20 = (var18 >> 8 & 0xFF) / 255.0f;
                final float var21 = (var18 & 0xFF) / 255.0f;
                worldRendererIn.HorizonCode_Horizon_È(var19, var20, var21, 4);
                worldRendererIn.HorizonCode_Horizon_È(var19, var20, var21, 3);
                worldRendererIn.HorizonCode_Horizon_È(var19, var20, var21, 2);
                worldRendererIn.HorizonCode_Horizon_È(var19, var20, var21, 1);
            }
            worldRendererIn.HorizonCode_Horizon_È(var10, var11, var12);
        }
    }
    
    public void HorizonCode_Horizon_È(final IBakedModel p_178262_1_, final float p_178262_2_, final float p_178262_3_, final float p_178262_4_, final float p_178262_5_) {
        for (final EnumFacing var9 : EnumFacing.à) {
            this.HorizonCode_Horizon_È(p_178262_2_, p_178262_3_, p_178262_4_, p_178262_5_, p_178262_1_.HorizonCode_Horizon_È(var9));
        }
        this.HorizonCode_Horizon_È(p_178262_2_, p_178262_3_, p_178262_4_, p_178262_5_, p_178262_1_.HorizonCode_Horizon_È());
    }
    
    public void HorizonCode_Horizon_È(final IBakedModel p_178266_1_, final IBlockState p_178266_2_, final float p_178266_3_, final boolean p_178266_4_) {
        final Block var5 = p_178266_2_.Ý();
        var5.ŠÄ();
        GlStateManager.Â(90.0f, 0.0f, 1.0f, 0.0f);
        int var6 = var5.Âµá€(var5.à(p_178266_2_));
        if (EntityRenderer.HorizonCode_Horizon_È) {
            var6 = TextureUtil.Ý(var6);
        }
        final float var7 = (var6 >> 16 & 0xFF) / 255.0f;
        final float var8 = (var6 >> 8 & 0xFF) / 255.0f;
        final float var9 = (var6 & 0xFF) / 255.0f;
        if (!p_178266_4_) {
            GlStateManager.Ý(p_178266_3_, p_178266_3_, p_178266_3_, 1.0f);
        }
        this.HorizonCode_Horizon_È(p_178266_1_, p_178266_3_, var7, var8, var9);
    }
    
    private void HorizonCode_Horizon_È(final float p_178264_1_, final float p_178264_2_, final float p_178264_3_, final float p_178264_4_, final List p_178264_5_) {
        final Tessellator var6 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var7 = var6.Ý();
        for (final BakedQuad var9 : p_178264_5_) {
            var7.Â();
            var7.HorizonCode_Horizon_È(DefaultVertexFormats.Â);
            var7.HorizonCode_Horizon_È(var9.Â());
            if (var9.Ý()) {
                var7.Âµá€(p_178264_2_ * p_178264_1_, p_178264_3_ * p_178264_1_, p_178264_4_ * p_178264_1_);
            }
            else {
                var7.Âµá€(p_178264_1_, p_178264_1_, p_178264_1_);
            }
            final Vec3i var10 = var9.Âµá€().ˆÏ­();
            var7.Ø­áŒŠá(var10.HorizonCode_Horizon_È(), var10.Â(), var10.Ý());
            var6.Â();
        }
    }
    
    public static float HorizonCode_Horizon_È(final float val) {
        return (val == 0.2f) ? BlockModelRenderer.Â : val;
    }
    
    public enum Â
    {
        HorizonCode_Horizon_È("DOWN", 0, "DOWN", 0, "DOWN", 0, new EnumFacing[] { EnumFacing.Âµá€, EnumFacing.Ó, EnumFacing.Ý, EnumFacing.Ø­áŒŠá }, 0.5f, false, new Ý[0], new Ý[0], new Ý[0], new Ý[0]), 
        Â("UP", 1, "UP", 1, "UP", 1, new EnumFacing[] { EnumFacing.Ó, EnumFacing.Âµá€, EnumFacing.Ý, EnumFacing.Ø­áŒŠá }, 1.0f, false, new Ý[0], new Ý[0], new Ý[0], new Ý[0]), 
        Ý("NORTH", 2, "NORTH", 2, "NORTH", 2, new EnumFacing[] { EnumFacing.Â, EnumFacing.HorizonCode_Horizon_È, EnumFacing.Ó, EnumFacing.Âµá€ }, 0.8f, true, new Ý[] { BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.ÂµÈ, BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.Âµá€, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.Âµá€, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.ÂµÈ }, new Ý[] { BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.á, BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.Ó, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.Ó, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.á }, new Ý[] { BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.á, BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.Ó, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.Ó, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.á }, new Ý[] { BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.ÂµÈ, BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.Âµá€, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.Âµá€, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.ÂµÈ }), 
        Ø­áŒŠá("SOUTH", 3, "SOUTH", 3, "SOUTH", 3, new EnumFacing[] { EnumFacing.Âµá€, EnumFacing.Ó, EnumFacing.HorizonCode_Horizon_È, EnumFacing.Â }, 0.8f, true, new Ý[] { BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.ÂµÈ, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.ÂµÈ, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.Âµá€, BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.Âµá€ }, new Ý[] { BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.ÂµÈ, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.ÂµÈ, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.Âµá€, BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.Âµá€ }, new Ý[] { BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.á, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.á, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.Ó, BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.Ó }, new Ý[] { BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.á, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.á, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.Ó, BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.Ó }), 
        Âµá€("WEST", 4, "WEST", 4, "WEST", 4, new EnumFacing[] { EnumFacing.Â, EnumFacing.HorizonCode_Horizon_È, EnumFacing.Ý, EnumFacing.Ø­áŒŠá }, 0.6f, true, new Ý[] { BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.Ø­áŒŠá, BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.áˆºÑ¢Õ, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.áˆºÑ¢Õ, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.Ø­áŒŠá }, new Ý[] { BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.Ý, BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.áŒŠÆ, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.áŒŠÆ, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.Ý }, new Ý[] { BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.Ý, BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.áŒŠÆ, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.áŒŠÆ, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.Ý }, new Ý[] { BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.Ø­áŒŠá, BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.áˆºÑ¢Õ, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.áˆºÑ¢Õ, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.Ø­áŒŠá }), 
        Ó("EAST", 5, "EAST", 5, "EAST", 5, new EnumFacing[] { EnumFacing.HorizonCode_Horizon_È, EnumFacing.Â, EnumFacing.Ý, EnumFacing.Ø­áŒŠá }, 0.6f, true, new Ý[] { BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.Ø­áŒŠá, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.áˆºÑ¢Õ, BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.áˆºÑ¢Õ, BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.Ø­áŒŠá }, new Ý[] { BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.Ý, BlockModelRenderer.Ý.à, BlockModelRenderer.Ý.áŒŠÆ, BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.áŒŠÆ, BlockModelRenderer.Ý.HorizonCode_Horizon_È, BlockModelRenderer.Ý.Ý }, new Ý[] { BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.Ý, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.áŒŠÆ, BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.áŒŠÆ, BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.Ý }, new Ý[] { BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.Ø­áŒŠá, BlockModelRenderer.Ý.Ø, BlockModelRenderer.Ý.áˆºÑ¢Õ, BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.áˆºÑ¢Õ, BlockModelRenderer.Ý.Â, BlockModelRenderer.Ý.Ø­áŒŠá });
        
        protected final EnumFacing[] à;
        protected final float Ø;
        protected final boolean áŒŠÆ;
        protected final Ý[] áˆºÑ¢Õ;
        protected final Ý[] ÂµÈ;
        protected final Ý[] á;
        protected final Ý[] ˆÏ­;
        private static final Â[] £á;
        private static final Â[] Å;
        private static final String £à = "CL_00002516";
        private static final Â[] µà;
        
        static {
            ˆà = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó };
            £á = new Â[6];
            Å = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó };
            µà = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó };
            Â.£á[EnumFacing.HorizonCode_Horizon_È.Â()] = Â.HorizonCode_Horizon_È;
            Â.£á[EnumFacing.Â.Â()] = Â.Â;
            Â.£á[EnumFacing.Ý.Â()] = Â.Ý;
            Â.£á[EnumFacing.Ø­áŒŠá.Â()] = Â.Ø­áŒŠá;
            Â.£á[EnumFacing.Âµá€.Â()] = Â.Âµá€;
            Â.£á[EnumFacing.Ó.Â()] = Â.Ó;
        }
        
        private Â(final String s, final int n, final String p_i46387_1_, final int p_i46387_2_, final String p_i46236_1_, final int p_i46236_2_, final EnumFacing[] p_i46236_3_, final float p_i46236_4_, final boolean p_i46236_5_, final Ý[] p_i46236_6_, final Ý[] p_i46236_7_, final Ý[] p_i46236_8_, final Ý[] p_i46236_9_) {
            this.à = p_i46236_3_;
            this.Ø = p_i46236_4_;
            this.áŒŠÆ = p_i46236_5_;
            this.áˆºÑ¢Õ = p_i46236_6_;
            this.ÂµÈ = p_i46236_7_;
            this.á = p_i46236_8_;
            this.ˆÏ­ = p_i46236_9_;
        }
        
        public static Â HorizonCode_Horizon_È(final EnumFacing p_178273_0_) {
            return Â.£á[p_178273_0_.Â()];
        }
    }
    
    public enum Ý
    {
        HorizonCode_Horizon_È("DOWN", 0, "DOWN", 0, "DOWN", 0, EnumFacing.HorizonCode_Horizon_È, false), 
        Â("UP", 1, "UP", 1, "UP", 1, EnumFacing.Â, false), 
        Ý("NORTH", 2, "NORTH", 2, "NORTH", 2, EnumFacing.Ý, false), 
        Ø­áŒŠá("SOUTH", 3, "SOUTH", 3, "SOUTH", 3, EnumFacing.Ø­áŒŠá, false), 
        Âµá€("WEST", 4, "WEST", 4, "WEST", 4, EnumFacing.Âµá€, false), 
        Ó("EAST", 5, "EAST", 5, "EAST", 5, EnumFacing.Ó, false), 
        à("FLIP_DOWN", 6, "FLIP_DOWN", 6, "FLIP_DOWN", 6, EnumFacing.HorizonCode_Horizon_È, true), 
        Ø("FLIP_UP", 7, "FLIP_UP", 7, "FLIP_UP", 7, EnumFacing.Â, true), 
        áŒŠÆ("FLIP_NORTH", 8, "FLIP_NORTH", 8, "FLIP_NORTH", 8, EnumFacing.Ý, true), 
        áˆºÑ¢Õ("FLIP_SOUTH", 9, "FLIP_SOUTH", 9, "FLIP_SOUTH", 9, EnumFacing.Ø­áŒŠá, true), 
        ÂµÈ("FLIP_WEST", 10, "FLIP_WEST", 10, "FLIP_WEST", 10, EnumFacing.Âµá€, true), 
        á("FLIP_EAST", 11, "FLIP_EAST", 11, "FLIP_EAST", 11, EnumFacing.Ó, true);
        
        protected final int ˆÏ­;
        private static final Ý[] £á;
        private static final String Å = "CL_00002513";
        private static final Ý[] £à;
        
        static {
            µà = new Ý[] { Ý.HorizonCode_Horizon_È, Ý.Â, Ý.Ý, Ý.Ø­áŒŠá, Ý.Âµá€, Ý.Ó, Ý.à, Ý.Ø, Ý.áŒŠÆ, Ý.áˆºÑ¢Õ, Ý.ÂµÈ, Ý.á };
            £á = new Ý[] { Ý.HorizonCode_Horizon_È, Ý.Â, Ý.Ý, Ý.Ø­áŒŠá, Ý.Âµá€, Ý.Ó, Ý.à, Ý.Ø, Ý.áŒŠÆ, Ý.áˆºÑ¢Õ, Ý.ÂµÈ, Ý.á };
            £à = new Ý[] { Ý.HorizonCode_Horizon_È, Ý.Â, Ý.Ý, Ý.Ø­áŒŠá, Ý.Âµá€, Ý.Ó, Ý.à, Ý.Ø, Ý.áŒŠÆ, Ý.áˆºÑ¢Õ, Ý.ÂµÈ, Ý.á };
        }
        
        private Ý(final String s, final int n, final String p_i46389_1_, final int p_i46389_2_, final String p_i46233_1_, final int p_i46233_2_, final EnumFacing p_i46233_3_, final boolean p_i46233_4_) {
            this.ˆÏ­ = p_i46233_3_.Â() + (p_i46233_4_ ? EnumFacing.values().length : 0);
        }
    }
    
    enum Âµá€
    {
        HorizonCode_Horizon_È("DOWN", 0, "DOWN", 0, "DOWN", 0, 0, 1, 2, 3), 
        Â("UP", 1, "UP", 1, "UP", 1, 2, 3, 0, 1), 
        Ý("NORTH", 2, "NORTH", 2, "NORTH", 2, 3, 0, 1, 2), 
        Ø­áŒŠá("SOUTH", 3, "SOUTH", 3, "SOUTH", 3, 0, 1, 2, 3), 
        Âµá€("WEST", 4, "WEST", 4, "WEST", 4, 3, 0, 1, 2), 
        Ó("EAST", 5, "EAST", 5, "EAST", 5, 1, 2, 3, 0);
        
        private final int à;
        private final int Ø;
        private final int áŒŠÆ;
        private final int áˆºÑ¢Õ;
        private static final Âµá€[] ÂµÈ;
        private static final Âµá€[] á;
        private static final String ˆÏ­ = "CL_00002514";
        private static final Âµá€[] £á;
        
        static {
            Å = new Âµá€[] { Âµá€.HorizonCode_Horizon_È, Âµá€.Â, Âµá€.Ý, Âµá€.Ø­áŒŠá, Âµá€.Âµá€, Âµá€.Ó };
            ÂµÈ = new Âµá€[6];
            á = new Âµá€[] { Âµá€.HorizonCode_Horizon_È, Âµá€.Â, Âµá€.Ý, Âµá€.Ø­áŒŠá, Âµá€.Âµá€, Âµá€.Ó };
            £á = new Âµá€[] { Âµá€.HorizonCode_Horizon_È, Âµá€.Â, Âµá€.Ý, Âµá€.Ø­áŒŠá, Âµá€.Âµá€, Âµá€.Ó };
            Âµá€.ÂµÈ[EnumFacing.HorizonCode_Horizon_È.Â()] = Âµá€.HorizonCode_Horizon_È;
            Âµá€.ÂµÈ[EnumFacing.Â.Â()] = Âµá€.Â;
            Âµá€.ÂµÈ[EnumFacing.Ý.Â()] = Âµá€.Ý;
            Âµá€.ÂµÈ[EnumFacing.Ø­áŒŠá.Â()] = Âµá€.Ø­áŒŠá;
            Âµá€.ÂµÈ[EnumFacing.Âµá€.Â()] = Âµá€.Âµá€;
            Âµá€.ÂµÈ[EnumFacing.Ó.Â()] = Âµá€.Ó;
        }
        
        private Âµá€(final String s, final int n, final String p_i46388_1_, final int p_i46388_2_, final String p_i46234_1_, final int p_i46234_2_, final int p_i46234_3_, final int p_i46234_4_, final int p_i46234_5_, final int p_i46234_6_) {
            this.à = p_i46234_3_;
            this.Ø = p_i46234_4_;
            this.áŒŠÆ = p_i46234_5_;
            this.áˆºÑ¢Õ = p_i46234_6_;
        }
        
        public static Âµá€ HorizonCode_Horizon_È(final EnumFacing p_178184_0_) {
            return Âµá€.ÂµÈ[p_178184_0_.Â()];
        }
    }
    
    public static class HorizonCode_Horizon_È
    {
        private final float[] HorizonCode_Horizon_È;
        private final int[] Â;
        private static final String Ý = "CL_00002515";
        
        public HorizonCode_Horizon_È(final BlockModelRenderer bmr) {
            this.HorizonCode_Horizon_È = new float[4];
            this.Â = new int[4];
        }
        
        public HorizonCode_Horizon_È() {
            this.HorizonCode_Horizon_È = new float[4];
            this.Â = new int[4];
        }
        
        public void HorizonCode_Horizon_È(final IBlockAccess blockAccessIn, final Block blockIn, final BlockPos blockPosIn, final EnumFacing facingIn, final float[] quadBounds, final BitSet boundsFlags) {
            final BlockPos var7 = boundsFlags.get(0) ? blockPosIn.HorizonCode_Horizon_È(facingIn) : blockPosIn;
            final Â var8 = BlockModelRenderer.Â.HorizonCode_Horizon_È(facingIn);
            final BlockPos var9 = var7.HorizonCode_Horizon_È(var8.à[0]);
            final BlockPos var10 = var7.HorizonCode_Horizon_È(var8.à[1]);
            final BlockPos var11 = var7.HorizonCode_Horizon_È(var8.à[2]);
            final BlockPos var12 = var7.HorizonCode_Horizon_È(var8.à[3]);
            final int var13 = blockIn.Â(blockAccessIn, var9);
            final int var14 = blockIn.Â(blockAccessIn, var10);
            final int var15 = blockIn.Â(blockAccessIn, var11);
            final int var16 = blockIn.Â(blockAccessIn, var12);
            final float var17 = BlockModelRenderer.HorizonCode_Horizon_È(blockAccessIn.Â(var9).Ý().ÇŽÕ());
            final float var18 = BlockModelRenderer.HorizonCode_Horizon_È(blockAccessIn.Â(var10).Ý().ÇŽÕ());
            final float var19 = BlockModelRenderer.HorizonCode_Horizon_È(blockAccessIn.Â(var11).Ý().ÇŽÕ());
            final float var20 = BlockModelRenderer.HorizonCode_Horizon_È(blockAccessIn.Â(var12).Ý().ÇŽÕ());
            final boolean var21 = blockAccessIn.Â(var9.HorizonCode_Horizon_È(facingIn)).Ý().Ý();
            final boolean var22 = blockAccessIn.Â(var10.HorizonCode_Horizon_È(facingIn)).Ý().Ý();
            final boolean var23 = blockAccessIn.Â(var11.HorizonCode_Horizon_È(facingIn)).Ý().Ý();
            final boolean var24 = blockAccessIn.Â(var12.HorizonCode_Horizon_È(facingIn)).Ý().Ý();
            float var25;
            int var26;
            if (!var23 && !var21) {
                var25 = var17;
                var26 = var13;
            }
            else {
                final BlockPos var27 = var9.HorizonCode_Horizon_È(var8.à[2]);
                var25 = BlockModelRenderer.HorizonCode_Horizon_È(blockAccessIn.Â(var27).Ý().ÇŽÕ());
                var26 = blockIn.Â(blockAccessIn, var27);
            }
            float var28;
            int var29;
            if (!var24 && !var21) {
                var28 = var17;
                var29 = var13;
            }
            else {
                final BlockPos var27 = var9.HorizonCode_Horizon_È(var8.à[3]);
                var28 = BlockModelRenderer.HorizonCode_Horizon_È(blockAccessIn.Â(var27).Ý().ÇŽÕ());
                var29 = blockIn.Â(blockAccessIn, var27);
            }
            float var30;
            int var31;
            if (!var23 && !var22) {
                var30 = var18;
                var31 = var14;
            }
            else {
                final BlockPos var27 = var10.HorizonCode_Horizon_È(var8.à[2]);
                var30 = BlockModelRenderer.HorizonCode_Horizon_È(blockAccessIn.Â(var27).Ý().ÇŽÕ());
                var31 = blockIn.Â(blockAccessIn, var27);
            }
            float var32;
            int var33;
            if (!var24 && !var22) {
                var32 = var18;
                var33 = var14;
            }
            else {
                final BlockPos var27 = var10.HorizonCode_Horizon_È(var8.à[3]);
                var32 = BlockModelRenderer.HorizonCode_Horizon_È(blockAccessIn.Â(var27).Ý().ÇŽÕ());
                var33 = blockIn.Â(blockAccessIn, var27);
            }
            int var34 = blockIn.Â(blockAccessIn, blockPosIn);
            if (boundsFlags.get(0) || !blockAccessIn.Â(blockPosIn.HorizonCode_Horizon_È(facingIn)).Ý().Å()) {
                var34 = blockIn.Â(blockAccessIn, blockPosIn.HorizonCode_Horizon_È(facingIn));
            }
            float var35 = boundsFlags.get(0) ? blockAccessIn.Â(var7).Ý().ÇŽÕ() : blockAccessIn.Â(blockPosIn).Ý().ÇŽÕ();
            var35 = BlockModelRenderer.HorizonCode_Horizon_È(var35);
            final Âµá€ var36 = Âµá€.HorizonCode_Horizon_È(facingIn);
            if (boundsFlags.get(1) && var8.áŒŠÆ) {
                final float var37 = (var20 + var17 + var28 + var35) * 0.25f;
                final float var38 = (var19 + var17 + var25 + var35) * 0.25f;
                final float var39 = (var19 + var18 + var30 + var35) * 0.25f;
                final float var40 = (var20 + var18 + var32 + var35) * 0.25f;
                final float var41 = quadBounds[var8.áˆºÑ¢Õ[0].ˆÏ­] * quadBounds[var8.áˆºÑ¢Õ[1].ˆÏ­];
                final float var42 = quadBounds[var8.áˆºÑ¢Õ[2].ˆÏ­] * quadBounds[var8.áˆºÑ¢Õ[3].ˆÏ­];
                final float var43 = quadBounds[var8.áˆºÑ¢Õ[4].ˆÏ­] * quadBounds[var8.áˆºÑ¢Õ[5].ˆÏ­];
                final float var44 = quadBounds[var8.áˆºÑ¢Õ[6].ˆÏ­] * quadBounds[var8.áˆºÑ¢Õ[7].ˆÏ­];
                final float var45 = quadBounds[var8.ÂµÈ[0].ˆÏ­] * quadBounds[var8.ÂµÈ[1].ˆÏ­];
                final float var46 = quadBounds[var8.ÂµÈ[2].ˆÏ­] * quadBounds[var8.ÂµÈ[3].ˆÏ­];
                final float var47 = quadBounds[var8.ÂµÈ[4].ˆÏ­] * quadBounds[var8.ÂµÈ[5].ˆÏ­];
                final float var48 = quadBounds[var8.ÂµÈ[6].ˆÏ­] * quadBounds[var8.ÂµÈ[7].ˆÏ­];
                final float var49 = quadBounds[var8.á[0].ˆÏ­] * quadBounds[var8.á[1].ˆÏ­];
                final float var50 = quadBounds[var8.á[2].ˆÏ­] * quadBounds[var8.á[3].ˆÏ­];
                final float var51 = quadBounds[var8.á[4].ˆÏ­] * quadBounds[var8.á[5].ˆÏ­];
                final float var52 = quadBounds[var8.á[6].ˆÏ­] * quadBounds[var8.á[7].ˆÏ­];
                final float var53 = quadBounds[var8.ˆÏ­[0].ˆÏ­] * quadBounds[var8.ˆÏ­[1].ˆÏ­];
                final float var54 = quadBounds[var8.ˆÏ­[2].ˆÏ­] * quadBounds[var8.ˆÏ­[3].ˆÏ­];
                final float var55 = quadBounds[var8.ˆÏ­[4].ˆÏ­] * quadBounds[var8.ˆÏ­[5].ˆÏ­];
                final float var56 = quadBounds[var8.ˆÏ­[6].ˆÏ­] * quadBounds[var8.ˆÏ­[7].ˆÏ­];
                this.HorizonCode_Horizon_È[var36.à] = var37 * var41 + var38 * var42 + var39 * var43 + var40 * var44;
                this.HorizonCode_Horizon_È[var36.Ø] = var37 * var45 + var38 * var46 + var39 * var47 + var40 * var48;
                this.HorizonCode_Horizon_È[var36.áŒŠÆ] = var37 * var49 + var38 * var50 + var39 * var51 + var40 * var52;
                this.HorizonCode_Horizon_È[var36.áˆºÑ¢Õ] = var37 * var53 + var38 * var54 + var39 * var55 + var40 * var56;
                final int var57 = this.HorizonCode_Horizon_È(var16, var13, var29, var34);
                final int var58 = this.HorizonCode_Horizon_È(var15, var13, var26, var34);
                final int var59 = this.HorizonCode_Horizon_È(var15, var14, var31, var34);
                final int var60 = this.HorizonCode_Horizon_È(var16, var14, var33, var34);
                this.Â[var36.à] = this.HorizonCode_Horizon_È(var57, var58, var59, var60, var41, var42, var43, var44);
                this.Â[var36.Ø] = this.HorizonCode_Horizon_È(var57, var58, var59, var60, var45, var46, var47, var48);
                this.Â[var36.áŒŠÆ] = this.HorizonCode_Horizon_È(var57, var58, var59, var60, var49, var50, var51, var52);
                this.Â[var36.áˆºÑ¢Õ] = this.HorizonCode_Horizon_È(var57, var58, var59, var60, var53, var54, var55, var56);
            }
            else {
                final float var37 = (var20 + var17 + var28 + var35) * 0.25f;
                final float var38 = (var19 + var17 + var25 + var35) * 0.25f;
                final float var39 = (var19 + var18 + var30 + var35) * 0.25f;
                final float var40 = (var20 + var18 + var32 + var35) * 0.25f;
                this.Â[var36.à] = this.HorizonCode_Horizon_È(var16, var13, var29, var34);
                this.Â[var36.Ø] = this.HorizonCode_Horizon_È(var15, var13, var26, var34);
                this.Â[var36.áŒŠÆ] = this.HorizonCode_Horizon_È(var15, var14, var31, var34);
                this.Â[var36.áˆºÑ¢Õ] = this.HorizonCode_Horizon_È(var16, var14, var33, var34);
                this.HorizonCode_Horizon_È[var36.à] = var37;
                this.HorizonCode_Horizon_È[var36.Ø] = var38;
                this.HorizonCode_Horizon_È[var36.áŒŠÆ] = var39;
                this.HorizonCode_Horizon_È[var36.áˆºÑ¢Õ] = var40;
            }
        }
        
        private int HorizonCode_Horizon_È(int p_147778_1_, int p_147778_2_, int p_147778_3_, final int p_147778_4_) {
            if (p_147778_1_ == 0) {
                p_147778_1_ = p_147778_4_;
            }
            if (p_147778_2_ == 0) {
                p_147778_2_ = p_147778_4_;
            }
            if (p_147778_3_ == 0) {
                p_147778_3_ = p_147778_4_;
            }
            return p_147778_1_ + p_147778_2_ + p_147778_3_ + p_147778_4_ >> 2 & 0xFF00FF;
        }
        
        private int HorizonCode_Horizon_È(final int p_178203_1_, final int p_178203_2_, final int p_178203_3_, final int p_178203_4_, final float p_178203_5_, final float p_178203_6_, final float p_178203_7_, final float p_178203_8_) {
            final int var9 = (int)((p_178203_1_ >> 16 & 0xFF) * p_178203_5_ + (p_178203_2_ >> 16 & 0xFF) * p_178203_6_ + (p_178203_3_ >> 16 & 0xFF) * p_178203_7_ + (p_178203_4_ >> 16 & 0xFF) * p_178203_8_) & 0xFF;
            final int var10 = (int)((p_178203_1_ & 0xFF) * p_178203_5_ + (p_178203_2_ & 0xFF) * p_178203_6_ + (p_178203_3_ & 0xFF) * p_178203_7_ + (p_178203_4_ & 0xFF) * p_178203_8_) & 0xFF;
            return var9 << 16 | var10;
        }
    }
    
    static final class Ø­áŒŠá
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002517";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                Ø­áŒŠá.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Ø­áŒŠá.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                Ø­áŒŠá.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                Ø­áŒŠá.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                Ø­áŒŠá.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                Ø­áŒŠá.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
