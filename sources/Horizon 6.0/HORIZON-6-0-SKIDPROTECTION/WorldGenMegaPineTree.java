package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenMegaPineTree extends WorldGenHugeTrees
{
    private boolean Âµá€;
    private static final String Ó = "CL_00000421";
    
    public WorldGenMegaPineTree(final boolean p_i45457_1_, final boolean p_i45457_2_) {
        super(p_i45457_1_, 13, 15, BlockPlanks.HorizonCode_Horizon_È.Â.Â(), BlockPlanks.HorizonCode_Horizon_È.Â.Â());
        this.Âµá€ = p_i45457_2_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        final int var4 = this.HorizonCode_Horizon_È(p_180709_2_);
        if (!this.HorizonCode_Horizon_È(worldIn, p_180709_2_, p_180709_3_, var4)) {
            return false;
        }
        this.HorizonCode_Horizon_È(worldIn, p_180709_3_.HorizonCode_Horizon_È(), p_180709_3_.Ý(), p_180709_3_.Â() + var4, 0, p_180709_2_);
        for (int var5 = 0; var5 < var4; ++var5) {
            Block var6 = worldIn.Â(p_180709_3_.Â(var5)).Ý();
            if (var6.Ó() == Material.HorizonCode_Horizon_È || var6.Ó() == Material.áˆºÑ¢Õ) {
                this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(var5), Blocks.¥Æ, this.Â);
            }
            if (var5 < var4 - 1) {
                var6 = worldIn.Â(p_180709_3_.Â(1, var5, 0)).Ý();
                if (var6.Ó() == Material.HorizonCode_Horizon_È || var6.Ó() == Material.áˆºÑ¢Õ) {
                    this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(1, var5, 0), Blocks.¥Æ, this.Â);
                }
                var6 = worldIn.Â(p_180709_3_.Â(1, var5, 1)).Ý();
                if (var6.Ó() == Material.HorizonCode_Horizon_È || var6.Ó() == Material.áˆºÑ¢Õ) {
                    this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(1, var5, 1), Blocks.¥Æ, this.Â);
                }
                var6 = worldIn.Â(p_180709_3_.Â(0, var5, 1)).Ý();
                if (var6.Ó() == Material.HorizonCode_Horizon_È || var6.Ó() == Material.áˆºÑ¢Õ) {
                    this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(0, var5, 1), Blocks.¥Æ, this.Â);
                }
            }
        }
        return true;
    }
    
    private void HorizonCode_Horizon_È(final World worldIn, final int p_150541_2_, final int p_150541_3_, final int p_150541_4_, final int p_150541_5_, final Random p_150541_6_) {
        final int var7 = p_150541_6_.nextInt(5) + (this.Âµá€ ? this.HorizonCode_Horizon_È : 3);
        int var8 = 0;
        for (int var9 = p_150541_4_ - var7; var9 <= p_150541_4_; ++var9) {
            final int var10 = p_150541_4_ - var9;
            final int var11 = p_150541_5_ + MathHelper.Ø­áŒŠá(var10 / var7 * 3.5f);
            this.HorizonCode_Horizon_È(worldIn, new BlockPos(p_150541_2_, var9, p_150541_3_), var11 + ((var10 > 0 && var11 == var8 && (var9 & 0x1) == 0x0) ? 1 : 0));
            var8 = var11;
        }
    }
    
    @Override
    public void Â(final World worldIn, final Random p_180711_2_, final BlockPos p_180711_3_) {
        this.Â(worldIn, p_180711_3_.Ø().Ó());
        this.Â(worldIn, p_180711_3_.à(2).Ó());
        this.Â(worldIn, p_180711_3_.Ø().Âµá€(2));
        this.Â(worldIn, p_180711_3_.à(2).Âµá€(2));
        for (int var4 = 0; var4 < 5; ++var4) {
            final int var5 = p_180711_2_.nextInt(64);
            final int var6 = var5 % 8;
            final int var7 = var5 / 8;
            if (var6 == 0 || var6 == 7 || var7 == 0 || var7 == 7) {
                this.Â(worldIn, p_180711_3_.Â(-3 + var6, 0, -3 + var7));
            }
        }
    }
    
    private void Â(final World worldIn, final BlockPos p_175933_2_) {
        for (int var3 = -2; var3 <= 2; ++var3) {
            for (int var4 = -2; var4 <= 2; ++var4) {
                if (Math.abs(var3) != 2 || Math.abs(var4) != 2) {
                    this.Ý(worldIn, p_175933_2_.Â(var3, 0, var4));
                }
            }
        }
    }
    
    private void Ý(final World worldIn, final BlockPos p_175934_2_) {
        for (int var3 = 2; var3 >= -3; --var3) {
            final BlockPos var4 = p_175934_2_.Â(var3);
            final Block var5 = worldIn.Â(var4).Ý();
            if (var5 == Blocks.Ø­áŒŠá || var5 == Blocks.Âµá€) {
                this.HorizonCode_Horizon_È(worldIn, var4, Blocks.Âµá€, BlockDirt.HorizonCode_Horizon_È.Ý.Â());
                break;
            }
            if (var5.Ó() != Material.HorizonCode_Horizon_È && var3 < 0) {
                break;
            }
        }
    }
}
