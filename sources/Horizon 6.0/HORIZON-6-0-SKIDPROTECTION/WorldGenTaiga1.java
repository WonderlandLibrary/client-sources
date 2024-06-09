package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenTaiga1 extends WorldGenAbstractTree
{
    private static final String HorizonCode_Horizon_È = "CL_00000427";
    
    public WorldGenTaiga1() {
        super(false);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        final int var4 = p_180709_2_.nextInt(5) + 7;
        final int var5 = var4 - p_180709_2_.nextInt(2) - 3;
        final int var6 = var4 - var5;
        final int var7 = 1 + p_180709_2_.nextInt(var6 + 1);
        boolean var8 = true;
        if (p_180709_3_.Â() < 1 || p_180709_3_.Â() + var4 + 1 > 256) {
            return false;
        }
        for (int var9 = p_180709_3_.Â(); var9 <= p_180709_3_.Â() + 1 + var4 && var8; ++var9) {
            final boolean var10 = true;
            int var11;
            if (var9 - p_180709_3_.Â() < var5) {
                var11 = 0;
            }
            else {
                var11 = var7;
            }
            for (int var12 = p_180709_3_.HorizonCode_Horizon_È() - var11; var12 <= p_180709_3_.HorizonCode_Horizon_È() + var11 && var8; ++var12) {
                for (int var13 = p_180709_3_.Ý() - var11; var13 <= p_180709_3_.Ý() + var11 && var8; ++var13) {
                    if (var9 >= 0 && var9 < 256) {
                        if (!this.HorizonCode_Horizon_È(worldIn.Â(new BlockPos(var12, var9, var13)).Ý())) {
                            var8 = false;
                        }
                    }
                    else {
                        var8 = false;
                    }
                }
            }
        }
        if (!var8) {
            return false;
        }
        final Block var14 = worldIn.Â(p_180709_3_.Âµá€()).Ý();
        if ((var14 == Blocks.Ø­áŒŠá || var14 == Blocks.Âµá€) && p_180709_3_.Â() < 256 - var4 - 1) {
            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Âµá€());
            int var11 = 0;
            for (int var12 = p_180709_3_.Â() + var4; var12 >= p_180709_3_.Â() + var5; --var12) {
                for (int var13 = p_180709_3_.HorizonCode_Horizon_È() - var11; var13 <= p_180709_3_.HorizonCode_Horizon_È() + var11; ++var13) {
                    final int var15 = var13 - p_180709_3_.HorizonCode_Horizon_È();
                    for (int var16 = p_180709_3_.Ý() - var11; var16 <= p_180709_3_.Ý() + var11; ++var16) {
                        final int var17 = var16 - p_180709_3_.Ý();
                        if (Math.abs(var15) != var11 || Math.abs(var17) != var11 || var11 <= 0) {
                            final BlockPos var18 = new BlockPos(var13, var12, var16);
                            if (!worldIn.Â(var18).Ý().HorizonCode_Horizon_È()) {
                                this.HorizonCode_Horizon_È(worldIn, var18, Blocks.µÕ, BlockPlanks.HorizonCode_Horizon_È.Â.Â());
                            }
                        }
                    }
                }
                if (var11 >= 1 && var12 == p_180709_3_.Â() + var5 + 1) {
                    --var11;
                }
                else if (var11 < var7) {
                    ++var11;
                }
            }
            for (int var12 = 0; var12 < var4 - 1; ++var12) {
                final Block var19 = worldIn.Â(p_180709_3_.Â(var12)).Ý();
                if (var19.Ó() == Material.HorizonCode_Horizon_È || var19.Ó() == Material.áˆºÑ¢Õ) {
                    this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(var12), Blocks.¥Æ, BlockPlanks.HorizonCode_Horizon_È.Â.Â());
                }
            }
            return true;
        }
        return false;
    }
}
