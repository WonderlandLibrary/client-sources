package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenShrub extends WorldGenTrees
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00000411";
    
    public WorldGenShrub(final int p_i2015_1_, final int p_i2015_2_) {
        super(false);
        this.Â = p_i2015_1_;
        this.HorizonCode_Horizon_È = p_i2015_2_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        Block var4;
        while (((var4 = worldIn.Â(p_180709_3_).Ý()).Ó() == Material.HorizonCode_Horizon_È || var4.Ó() == Material.áˆºÑ¢Õ) && p_180709_3_.Â() > 0) {
            p_180709_3_ = p_180709_3_.Âµá€();
        }
        final Block var5 = worldIn.Â(p_180709_3_).Ý();
        if (var5 == Blocks.Âµá€ || var5 == Blocks.Ø­áŒŠá) {
            p_180709_3_ = p_180709_3_.Ø­áŒŠá();
            this.HorizonCode_Horizon_È(worldIn, p_180709_3_, Blocks.¥Æ, this.Â);
            for (int var6 = p_180709_3_.Â(); var6 <= p_180709_3_.Â() + 2; ++var6) {
                final int var7 = var6 - p_180709_3_.Â();
                for (int var8 = 2 - var7, var9 = p_180709_3_.HorizonCode_Horizon_È() - var8; var9 <= p_180709_3_.HorizonCode_Horizon_È() + var8; ++var9) {
                    final int var10 = var9 - p_180709_3_.HorizonCode_Horizon_È();
                    for (int var11 = p_180709_3_.Ý() - var8; var11 <= p_180709_3_.Ý() + var8; ++var11) {
                        final int var12 = var11 - p_180709_3_.Ý();
                        if (Math.abs(var10) != var8 || Math.abs(var12) != var8 || p_180709_2_.nextInt(2) != 0) {
                            final BlockPos var13 = new BlockPos(var9, var6, var11);
                            if (!worldIn.Â(var13).Ý().HorizonCode_Horizon_È()) {
                                this.HorizonCode_Horizon_È(worldIn, var13, Blocks.µÕ, this.HorizonCode_Horizon_È);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
