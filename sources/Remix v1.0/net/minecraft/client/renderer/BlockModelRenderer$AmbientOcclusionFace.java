package net.minecraft.client.renderer;

import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;

public static class AmbientOcclusionFace
{
    private final float[] field_178206_b;
    private final int[] field_178207_c;
    
    public AmbientOcclusionFace(final BlockModelRenderer bmr) {
        this.field_178206_b = new float[4];
        this.field_178207_c = new int[4];
    }
    
    public AmbientOcclusionFace() {
        this.field_178206_b = new float[4];
        this.field_178207_c = new int[4];
    }
    
    public void func_178204_a(final IBlockAccess blockAccessIn, final Block blockIn, final BlockPos blockPosIn, final EnumFacing facingIn, final float[] quadBounds, final BitSet boundsFlags) {
        final BlockPos var7 = boundsFlags.get(0) ? blockPosIn.offset(facingIn) : blockPosIn;
        final EnumNeighborInfo var8 = EnumNeighborInfo.func_178273_a(facingIn);
        final BlockPos var9 = var7.offset(var8.field_178276_g[0]);
        final BlockPos var10 = var7.offset(var8.field_178276_g[1]);
        final BlockPos var11 = var7.offset(var8.field_178276_g[2]);
        final BlockPos var12 = var7.offset(var8.field_178276_g[3]);
        final int var13 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var9);
        final int var14 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var10);
        final int var15 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var11);
        final int var16 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var12);
        final float var17 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(var9).getBlock().getAmbientOcclusionLightValue());
        final float var18 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(var10).getBlock().getAmbientOcclusionLightValue());
        final float var19 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(var11).getBlock().getAmbientOcclusionLightValue());
        final float var20 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(var12).getBlock().getAmbientOcclusionLightValue());
        final boolean var21 = blockAccessIn.getBlockState(var9.offset(facingIn)).getBlock().isTranslucent();
        final boolean var22 = blockAccessIn.getBlockState(var10.offset(facingIn)).getBlock().isTranslucent();
        final boolean var23 = blockAccessIn.getBlockState(var11.offset(facingIn)).getBlock().isTranslucent();
        final boolean var24 = blockAccessIn.getBlockState(var12.offset(facingIn)).getBlock().isTranslucent();
        float var25;
        int var26;
        if (!var23 && !var21) {
            var25 = var17;
            var26 = var13;
        }
        else {
            final BlockPos var27 = var9.offset(var8.field_178276_g[2]);
            var25 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(var27).getBlock().getAmbientOcclusionLightValue());
            var26 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var27);
        }
        float var28;
        int var29;
        if (!var24 && !var21) {
            var28 = var17;
            var29 = var13;
        }
        else {
            final BlockPos var27 = var9.offset(var8.field_178276_g[3]);
            var28 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(var27).getBlock().getAmbientOcclusionLightValue());
            var29 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var27);
        }
        float var30;
        int var31;
        if (!var23 && !var22) {
            var30 = var18;
            var31 = var14;
        }
        else {
            final BlockPos var27 = var10.offset(var8.field_178276_g[2]);
            var30 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(var27).getBlock().getAmbientOcclusionLightValue());
            var31 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var27);
        }
        float var32;
        int var33;
        if (!var24 && !var22) {
            var32 = var18;
            var33 = var14;
        }
        else {
            final BlockPos var27 = var10.offset(var8.field_178276_g[3]);
            var32 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(var27).getBlock().getAmbientOcclusionLightValue());
            var33 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var27);
        }
        int var34 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn);
        if (boundsFlags.get(0) || !blockAccessIn.getBlockState(blockPosIn.offset(facingIn)).getBlock().isOpaqueCube()) {
            var34 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn.offset(facingIn));
        }
        float var35 = boundsFlags.get(0) ? blockAccessIn.getBlockState(var7).getBlock().getAmbientOcclusionLightValue() : blockAccessIn.getBlockState(blockPosIn).getBlock().getAmbientOcclusionLightValue();
        var35 = BlockModelRenderer.fixAoLightValue(var35);
        final VertexTranslations var36 = VertexTranslations.func_178184_a(facingIn);
        if (boundsFlags.get(1) && var8.field_178289_i) {
            final float var37 = (var20 + var17 + var28 + var35) * 0.25f;
            final float var38 = (var19 + var17 + var25 + var35) * 0.25f;
            final float var39 = (var19 + var18 + var30 + var35) * 0.25f;
            final float var40 = (var20 + var18 + var32 + var35) * 0.25f;
            final float var41 = quadBounds[var8.field_178286_j[0].field_178229_m] * quadBounds[var8.field_178286_j[1].field_178229_m];
            final float var42 = quadBounds[var8.field_178286_j[2].field_178229_m] * quadBounds[var8.field_178286_j[3].field_178229_m];
            final float var43 = quadBounds[var8.field_178286_j[4].field_178229_m] * quadBounds[var8.field_178286_j[5].field_178229_m];
            final float var44 = quadBounds[var8.field_178286_j[6].field_178229_m] * quadBounds[var8.field_178286_j[7].field_178229_m];
            final float var45 = quadBounds[var8.field_178287_k[0].field_178229_m] * quadBounds[var8.field_178287_k[1].field_178229_m];
            final float var46 = quadBounds[var8.field_178287_k[2].field_178229_m] * quadBounds[var8.field_178287_k[3].field_178229_m];
            final float var47 = quadBounds[var8.field_178287_k[4].field_178229_m] * quadBounds[var8.field_178287_k[5].field_178229_m];
            final float var48 = quadBounds[var8.field_178287_k[6].field_178229_m] * quadBounds[var8.field_178287_k[7].field_178229_m];
            final float var49 = quadBounds[var8.field_178284_l[0].field_178229_m] * quadBounds[var8.field_178284_l[1].field_178229_m];
            final float var50 = quadBounds[var8.field_178284_l[2].field_178229_m] * quadBounds[var8.field_178284_l[3].field_178229_m];
            final float var51 = quadBounds[var8.field_178284_l[4].field_178229_m] * quadBounds[var8.field_178284_l[5].field_178229_m];
            final float var52 = quadBounds[var8.field_178284_l[6].field_178229_m] * quadBounds[var8.field_178284_l[7].field_178229_m];
            final float var53 = quadBounds[var8.field_178285_m[0].field_178229_m] * quadBounds[var8.field_178285_m[1].field_178229_m];
            final float var54 = quadBounds[var8.field_178285_m[2].field_178229_m] * quadBounds[var8.field_178285_m[3].field_178229_m];
            final float var55 = quadBounds[var8.field_178285_m[4].field_178229_m] * quadBounds[var8.field_178285_m[5].field_178229_m];
            final float var56 = quadBounds[var8.field_178285_m[6].field_178229_m] * quadBounds[var8.field_178285_m[7].field_178229_m];
            this.field_178206_b[var36.field_178191_g] = var37 * var41 + var38 * var42 + var39 * var43 + var40 * var44;
            this.field_178206_b[var36.field_178200_h] = var37 * var45 + var38 * var46 + var39 * var47 + var40 * var48;
            this.field_178206_b[var36.field_178201_i] = var37 * var49 + var38 * var50 + var39 * var51 + var40 * var52;
            this.field_178206_b[var36.field_178198_j] = var37 * var53 + var38 * var54 + var39 * var55 + var40 * var56;
            final int var57 = this.getAoBrightness(var16, var13, var29, var34);
            final int var58 = this.getAoBrightness(var15, var13, var26, var34);
            final int var59 = this.getAoBrightness(var15, var14, var31, var34);
            final int var60 = this.getAoBrightness(var16, var14, var33, var34);
            this.field_178207_c[var36.field_178191_g] = this.func_178203_a(var57, var58, var59, var60, var41, var42, var43, var44);
            this.field_178207_c[var36.field_178200_h] = this.func_178203_a(var57, var58, var59, var60, var45, var46, var47, var48);
            this.field_178207_c[var36.field_178201_i] = this.func_178203_a(var57, var58, var59, var60, var49, var50, var51, var52);
            this.field_178207_c[var36.field_178198_j] = this.func_178203_a(var57, var58, var59, var60, var53, var54, var55, var56);
        }
        else {
            final float var37 = (var20 + var17 + var28 + var35) * 0.25f;
            final float var38 = (var19 + var17 + var25 + var35) * 0.25f;
            final float var39 = (var19 + var18 + var30 + var35) * 0.25f;
            final float var40 = (var20 + var18 + var32 + var35) * 0.25f;
            this.field_178207_c[var36.field_178191_g] = this.getAoBrightness(var16, var13, var29, var34);
            this.field_178207_c[var36.field_178200_h] = this.getAoBrightness(var15, var13, var26, var34);
            this.field_178207_c[var36.field_178201_i] = this.getAoBrightness(var15, var14, var31, var34);
            this.field_178207_c[var36.field_178198_j] = this.getAoBrightness(var16, var14, var33, var34);
            this.field_178206_b[var36.field_178191_g] = var37;
            this.field_178206_b[var36.field_178200_h] = var38;
            this.field_178206_b[var36.field_178201_i] = var39;
            this.field_178206_b[var36.field_178198_j] = var40;
        }
    }
    
    private int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, final int p_147778_4_) {
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
    
    private int func_178203_a(final int p_178203_1_, final int p_178203_2_, final int p_178203_3_, final int p_178203_4_, final float p_178203_5_, final float p_178203_6_, final float p_178203_7_, final float p_178203_8_) {
        final int var9 = (int)((p_178203_1_ >> 16 & 0xFF) * p_178203_5_ + (p_178203_2_ >> 16 & 0xFF) * p_178203_6_ + (p_178203_3_ >> 16 & 0xFF) * p_178203_7_ + (p_178203_4_ >> 16 & 0xFF) * p_178203_8_) & 0xFF;
        final int var10 = (int)((p_178203_1_ & 0xFF) * p_178203_5_ + (p_178203_2_ & 0xFF) * p_178203_6_ + (p_178203_3_ & 0xFF) * p_178203_7_ + (p_178203_4_ & 0xFF) * p_178203_8_) & 0xFF;
        return var9 << 16 | var10;
    }
}
