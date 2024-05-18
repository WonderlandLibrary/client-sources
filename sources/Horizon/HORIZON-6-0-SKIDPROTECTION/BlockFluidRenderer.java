package HORIZON-6-0-SKIDPROTECTION;

public class BlockFluidRenderer
{
    private TextureAtlasSprite[] HorizonCode_Horizon_È;
    private TextureAtlasSprite[] Â;
    private static final String Ý = "CL_00002519";
    
    public BlockFluidRenderer() {
        this.HorizonCode_Horizon_È = new TextureAtlasSprite[2];
        this.Â = new TextureAtlasSprite[2];
        this.HorizonCode_Horizon_È();
    }
    
    protected void HorizonCode_Horizon_È() {
        final TextureMap var1 = Minecraft.áŒŠà().áŠ();
        this.HorizonCode_Horizon_È[0] = var1.HorizonCode_Horizon_È("minecraft:blocks/lava_still");
        this.HorizonCode_Horizon_È[1] = var1.HorizonCode_Horizon_È("minecraft:blocks/lava_flow");
        this.Â[0] = var1.HorizonCode_Horizon_È("minecraft:blocks/water_still");
        this.Â[1] = var1.HorizonCode_Horizon_È("minecraft:blocks/water_flow");
    }
    
    public boolean HorizonCode_Horizon_È(final IBlockAccess p_178270_1_, final IBlockState p_178270_2_, final BlockPos p_178270_3_, final WorldRenderer p_178270_4_) {
        final BlockLiquid var5 = (BlockLiquid)p_178270_2_.Ý();
        var5.Ý(p_178270_1_, p_178270_3_);
        final TextureAtlasSprite[] var6 = (var5.Ó() == Material.áŒŠÆ) ? this.HorizonCode_Horizon_È : this.Â;
        final int var7 = CustomColorizer.HorizonCode_Horizon_È(var5, p_178270_1_, p_178270_3_);
        final float var8 = (var7 >> 16 & 0xFF) / 255.0f;
        final float var9 = (var7 >> 8 & 0xFF) / 255.0f;
        final float var10 = (var7 & 0xFF) / 255.0f;
        final boolean var11 = var5.HorizonCode_Horizon_È(p_178270_1_, p_178270_3_.Ø­áŒŠá(), EnumFacing.Â);
        final boolean var12 = var5.HorizonCode_Horizon_È(p_178270_1_, p_178270_3_.Âµá€(), EnumFacing.HorizonCode_Horizon_È);
        final RenderEnv renderEnv = RenderEnv.HorizonCode_Horizon_È(p_178270_1_, p_178270_2_, p_178270_3_);
        final boolean[] var13 = renderEnv.áŒŠÆ();
        var13[0] = var5.HorizonCode_Horizon_È(p_178270_1_, p_178270_3_.Ó(), EnumFacing.Ý);
        var13[1] = var5.HorizonCode_Horizon_È(p_178270_1_, p_178270_3_.à(), EnumFacing.Ø­áŒŠá);
        var13[2] = var5.HorizonCode_Horizon_È(p_178270_1_, p_178270_3_.Ø(), EnumFacing.Âµá€);
        var13[3] = var5.HorizonCode_Horizon_È(p_178270_1_, p_178270_3_.áŒŠÆ(), EnumFacing.Ó);
        if (!var11 && !var12 && !var13[0] && !var13[1] && !var13[2] && !var13[3]) {
            return false;
        }
        boolean var14 = false;
        final float var15 = 0.5f;
        final float var16 = 1.0f;
        final float var17 = 0.8f;
        final float var18 = 0.6f;
        final Material var19 = var5.Ó();
        float var20 = this.HorizonCode_Horizon_È(p_178270_1_, p_178270_3_, var19);
        float var21 = this.HorizonCode_Horizon_È(p_178270_1_, p_178270_3_.à(), var19);
        float var22 = this.HorizonCode_Horizon_È(p_178270_1_, p_178270_3_.áŒŠÆ().à(), var19);
        float var23 = this.HorizonCode_Horizon_È(p_178270_1_, p_178270_3_.áŒŠÆ(), var19);
        final double var24 = p_178270_3_.HorizonCode_Horizon_È();
        final double var25 = p_178270_3_.Â();
        final double var26 = p_178270_3_.Ý();
        final float var27 = 0.001f;
        if (var11) {
            var14 = true;
            TextureAtlasSprite var28 = var6[0];
            final float var29 = (float)BlockLiquid.HorizonCode_Horizon_È(p_178270_1_, p_178270_3_, var19);
            if (var29 > -999.0f) {
                var28 = var6[1];
            }
            var20 -= var27;
            var21 -= var27;
            var22 -= var27;
            var23 -= var27;
            float var30;
            float var31;
            float var32;
            float var33;
            float var34;
            float var35;
            float var36;
            float var37;
            if (var29 < -999.0f) {
                var30 = var28.HorizonCode_Horizon_È(0.0);
                var31 = var28.Â(0.0);
                var32 = var30;
                var33 = var28.Â(16.0);
                var34 = var28.HorizonCode_Horizon_È(16.0);
                var35 = var33;
                var36 = var34;
                var37 = var31;
            }
            else {
                final float var38 = MathHelper.HorizonCode_Horizon_È(var29) * 0.25f;
                final float var39 = MathHelper.Â(var29) * 0.25f;
                final float var40 = 8.0f;
                var30 = var28.HorizonCode_Horizon_È((double)(8.0f + (-var39 - var38) * 16.0f));
                var31 = var28.Â((double)(8.0f + (-var39 + var38) * 16.0f));
                var32 = var28.HorizonCode_Horizon_È((double)(8.0f + (-var39 + var38) * 16.0f));
                var33 = var28.Â((double)(8.0f + (var39 + var38) * 16.0f));
                var34 = var28.HorizonCode_Horizon_È((double)(8.0f + (var39 + var38) * 16.0f));
                var35 = var28.Â((double)(8.0f + (var39 - var38) * 16.0f));
                var36 = var28.HorizonCode_Horizon_È((double)(8.0f + (var39 - var38) * 16.0f));
                var37 = var28.Â((double)(8.0f + (-var39 - var38) * 16.0f));
            }
            p_178270_4_.Â(var5.Â(p_178270_1_, p_178270_3_));
            p_178270_4_.Â(var16 * var8, var16 * var9, var16 * var10);
            p_178270_4_.HorizonCode_Horizon_È(var24 + 0.0, var25 + var20, var26 + 0.0, var30, var31);
            p_178270_4_.HorizonCode_Horizon_È(var24 + 0.0, var25 + var21, var26 + 1.0, var32, var33);
            p_178270_4_.HorizonCode_Horizon_È(var24 + 1.0, var25 + var22, var26 + 1.0, var34, var35);
            p_178270_4_.HorizonCode_Horizon_È(var24 + 1.0, var25 + var23, var26 + 0.0, var36, var37);
            if (var5.à(p_178270_1_, p_178270_3_.Ø­áŒŠá())) {
                p_178270_4_.HorizonCode_Horizon_È(var24 + 0.0, var25 + var20, var26 + 0.0, var30, var31);
                p_178270_4_.HorizonCode_Horizon_È(var24 + 1.0, var25 + var23, var26 + 0.0, var36, var37);
                p_178270_4_.HorizonCode_Horizon_È(var24 + 1.0, var25 + var22, var26 + 1.0, var34, var35);
                p_178270_4_.HorizonCode_Horizon_È(var24 + 0.0, var25 + var21, var26 + 1.0, var32, var33);
            }
        }
        if (var12) {
            p_178270_4_.Â(var5.Â(p_178270_1_, p_178270_3_.Âµá€()));
            p_178270_4_.Â(var15, var15, var15);
            final float var29 = var6[0].Âµá€();
            final float var30 = var6[0].Ó();
            final float var32 = var6[0].à();
            final float var34 = var6[0].Ø();
            p_178270_4_.HorizonCode_Horizon_È(var24, var25, var26 + 1.0, var29, var34);
            p_178270_4_.HorizonCode_Horizon_È(var24, var25, var26, var29, var32);
            p_178270_4_.HorizonCode_Horizon_È(var24 + 1.0, var25, var26, var30, var32);
            p_178270_4_.HorizonCode_Horizon_È(var24 + 1.0, var25, var26 + 1.0, var30, var34);
            var14 = true;
        }
        for (int var41 = 0; var41 < 4; ++var41) {
            int var42 = 0;
            int var43 = 0;
            if (var41 == 0) {
                --var43;
            }
            if (var41 == 1) {
                ++var43;
            }
            if (var41 == 2) {
                --var42;
            }
            if (var41 == 3) {
                ++var42;
            }
            final BlockPos var44 = p_178270_3_.Â(var42, 0, var43);
            final TextureAtlasSprite var28 = var6[1];
            if (var13[var41]) {
                float var31;
                float var36;
                double var45;
                double var46;
                double var47;
                double var48;
                if (var41 == 0) {
                    var36 = var20;
                    var31 = var23;
                    var45 = var24;
                    var46 = var24 + 1.0;
                    var47 = var26 + var27;
                    var48 = var26 + var27;
                }
                else if (var41 == 1) {
                    var36 = var22;
                    var31 = var21;
                    var45 = var24 + 1.0;
                    var46 = var24;
                    var47 = var26 + 1.0 - var27;
                    var48 = var26 + 1.0 - var27;
                }
                else if (var41 == 2) {
                    var36 = var21;
                    var31 = var20;
                    var45 = var24 + var27;
                    var46 = var24 + var27;
                    var47 = var26 + 1.0;
                    var48 = var26;
                }
                else {
                    var36 = var23;
                    var31 = var22;
                    var45 = var24 + 1.0 - var27;
                    var46 = var24 + 1.0 - var27;
                    var47 = var26;
                    var48 = var26 + 1.0;
                }
                var14 = true;
                final float var49 = var28.HorizonCode_Horizon_È(0.0);
                final float var50 = var28.HorizonCode_Horizon_È(8.0);
                final float var51 = var28.Â((double)((1.0f - var36) * 16.0f * 0.5f));
                final float var52 = var28.Â((double)((1.0f - var31) * 16.0f * 0.5f));
                final float var53 = var28.Â(8.0);
                p_178270_4_.Â(var5.Â(p_178270_1_, var44));
                float var54 = 1.0f;
                var54 *= ((var41 < 2) ? var17 : var18);
                p_178270_4_.Â(var16 * var54 * var8, var16 * var54 * var9, var16 * var54 * var10);
                p_178270_4_.HorizonCode_Horizon_È(var45, var25 + var36, var47, var49, var51);
                p_178270_4_.HorizonCode_Horizon_È(var46, var25 + var31, var48, var50, var52);
                p_178270_4_.HorizonCode_Horizon_È(var46, var25 + 0.0, var48, var50, var53);
                p_178270_4_.HorizonCode_Horizon_È(var45, var25 + 0.0, var47, var49, var53);
                p_178270_4_.HorizonCode_Horizon_È(var45, var25 + 0.0, var47, var49, var53);
                p_178270_4_.HorizonCode_Horizon_È(var46, var25 + 0.0, var48, var50, var53);
                p_178270_4_.HorizonCode_Horizon_È(var46, var25 + var31, var48, var50, var52);
                p_178270_4_.HorizonCode_Horizon_È(var45, var25 + var36, var47, var49, var51);
            }
        }
        return var14;
    }
    
    private float HorizonCode_Horizon_È(final IBlockAccess p_178269_1_, final BlockPos p_178269_2_, final Material p_178269_3_) {
        int var4 = 0;
        float var5 = 0.0f;
        for (int var6 = 0; var6 < 4; ++var6) {
            final BlockPos var7 = p_178269_2_.Â(-(var6 & 0x1), 0, -(var6 >> 1 & 0x1));
            if (p_178269_1_.Â(var7.Ø­áŒŠá()).Ý().Ó() == p_178269_3_) {
                return 1.0f;
            }
            final IBlockState var8 = p_178269_1_.Â(var7);
            final Material var9 = var8.Ý().Ó();
            if (var9 == p_178269_3_) {
                final int var10 = (int)var8.HorizonCode_Horizon_È(BlockLiquid.à¢);
                if (var10 >= 8 || var10 == 0) {
                    var5 += BlockLiquid.Âµá€(var10) * 10.0f;
                    var4 += 10;
                }
                var5 += BlockLiquid.Âµá€(var10);
                ++var4;
            }
            else if (!var9.Â()) {
                ++var5;
                ++var4;
            }
        }
        return 1.0f - var5 / var4;
    }
}
