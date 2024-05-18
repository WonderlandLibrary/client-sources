package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenMegaJungle extends WorldGenHugeTrees
{
    private static final String Âµá€ = "CL_00000420";
    
    public WorldGenMegaJungle(final boolean p_i45456_1_, final int p_i45456_2_, final int p_i45456_3_, final int p_i45456_4_, final int p_i45456_5_) {
        super(p_i45456_1_, p_i45456_2_, p_i45456_3_, p_i45456_4_, p_i45456_5_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        final int var4 = this.HorizonCode_Horizon_È(p_180709_2_);
        if (!this.HorizonCode_Horizon_È(worldIn, p_180709_2_, p_180709_3_, var4)) {
            return false;
        }
        this.Ý(worldIn, p_180709_3_.Â(var4), 2);
        for (int var5 = p_180709_3_.Â() + var4 - 2 - p_180709_2_.nextInt(4); var5 > p_180709_3_.Â() + var4 / 2; var5 -= 2 + p_180709_2_.nextInt(4)) {
            final float var6 = p_180709_2_.nextFloat() * 3.1415927f * 2.0f;
            int var7 = p_180709_3_.HorizonCode_Horizon_È() + (int)(0.5f + MathHelper.Â(var6) * 4.0f);
            int var8 = p_180709_3_.Ý() + (int)(0.5f + MathHelper.HorizonCode_Horizon_È(var6) * 4.0f);
            for (int var9 = 0; var9 < 5; ++var9) {
                var7 = p_180709_3_.HorizonCode_Horizon_È() + (int)(1.5f + MathHelper.Â(var6) * var9);
                var8 = p_180709_3_.Ý() + (int)(1.5f + MathHelper.HorizonCode_Horizon_È(var6) * var9);
                this.HorizonCode_Horizon_È(worldIn, new BlockPos(var7, var5 - 3 + var9 / 2, var8), Blocks.¥Æ, this.Â);
            }
            int var9 = 1 + p_180709_2_.nextInt(2);
            for (int var10 = var5, var11 = var5 - var9; var11 <= var10; ++var11) {
                final int var12 = var11 - var10;
                this.Â(worldIn, new BlockPos(var7, var11, var8), 1 - var12);
            }
        }
        for (int var13 = 0; var13 < var4; ++var13) {
            final BlockPos var14 = p_180709_3_.Â(var13);
            if (this.HorizonCode_Horizon_È(worldIn.Â(var14).Ý().Ó())) {
                this.HorizonCode_Horizon_È(worldIn, var14, Blocks.¥Æ, this.Â);
                if (var13 > 0) {
                    this.Â(worldIn, p_180709_2_, var14.Ø(), BlockVine.ˆáŠ);
                    this.Â(worldIn, p_180709_2_, var14.Ó(), BlockVine.È);
                }
            }
            if (var13 < var4 - 1) {
                final BlockPos var15 = var14.áŒŠÆ();
                if (this.HorizonCode_Horizon_È(worldIn.Â(var15).Ý().Ó())) {
                    this.HorizonCode_Horizon_È(worldIn, var15, Blocks.¥Æ, this.Â);
                    if (var13 > 0) {
                        this.Â(worldIn, p_180709_2_, var15.áŒŠÆ(), BlockVine.áŒŠ);
                        this.Â(worldIn, p_180709_2_, var15.Ó(), BlockVine.È);
                    }
                }
                final BlockPos var16 = var14.à().áŒŠÆ();
                if (this.HorizonCode_Horizon_È(worldIn.Â(var16).Ý().Ó())) {
                    this.HorizonCode_Horizon_È(worldIn, var16, Blocks.¥Æ, this.Â);
                    if (var13 > 0) {
                        this.Â(worldIn, p_180709_2_, var16.áŒŠÆ(), BlockVine.áŒŠ);
                        this.Â(worldIn, p_180709_2_, var16.à(), BlockVine.áŠ);
                    }
                }
                final BlockPos var17 = var14.à();
                if (this.HorizonCode_Horizon_È(worldIn.Â(var17).Ý().Ó())) {
                    this.HorizonCode_Horizon_È(worldIn, var17, Blocks.¥Æ, this.Â);
                    if (var13 > 0) {
                        this.Â(worldIn, p_180709_2_, var17.Ø(), BlockVine.ˆáŠ);
                        this.Â(worldIn, p_180709_2_, var17.à(), BlockVine.áŠ);
                    }
                }
            }
        }
        return true;
    }
    
    private boolean HorizonCode_Horizon_È(final Material p_175931_1_) {
        return p_175931_1_ == Material.HorizonCode_Horizon_È || p_175931_1_ == Material.áˆºÑ¢Õ;
    }
    
    private void Â(final World worldIn, final Random p_175932_2_, final BlockPos p_175932_3_, final int p_175932_4_) {
        if (p_175932_2_.nextInt(3) > 0 && worldIn.Ø­áŒŠá(p_175932_3_)) {
            this.HorizonCode_Horizon_È(worldIn, p_175932_3_, Blocks.ÇŽà, p_175932_4_);
        }
    }
    
    private void Ý(final World worldIn, final BlockPos p_175930_2_, final int p_175930_3_) {
        final byte var4 = 2;
        for (int var5 = -var4; var5 <= 0; ++var5) {
            this.HorizonCode_Horizon_È(worldIn, p_175930_2_.Â(var5), p_175930_3_ + 1 - var5);
        }
    }
}
