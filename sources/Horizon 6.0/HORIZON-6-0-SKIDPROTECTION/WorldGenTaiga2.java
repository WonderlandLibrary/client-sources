package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenTaiga2 extends WorldGenAbstractTree
{
    private static final String HorizonCode_Horizon_È = "CL_00000435";
    
    public WorldGenTaiga2(final boolean p_i2025_1_) {
        super(p_i2025_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        final int var4 = p_180709_2_.nextInt(4) + 6;
        final int var5 = 1 + p_180709_2_.nextInt(2);
        final int var6 = var4 - var5;
        final int var7 = 2 + p_180709_2_.nextInt(2);
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
                        final Block var14 = worldIn.Â(new BlockPos(var12, var9, var13)).Ý();
                        if (var14.Ó() != Material.HorizonCode_Horizon_È && var14.Ó() != Material.áˆºÑ¢Õ) {
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
        final Block var15 = worldIn.Â(p_180709_3_.Âµá€()).Ý();
        if ((var15 == Blocks.Ø­áŒŠá || var15 == Blocks.Âµá€ || var15 == Blocks.£Â) && p_180709_3_.Â() < 256 - var4 - 1) {
            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Âµá€());
            int var11 = p_180709_2_.nextInt(2);
            int var12 = 1;
            byte var16 = 0;
            for (int var17 = 0; var17 <= var6; ++var17) {
                final int var18 = p_180709_3_.Â() + var4 - var17;
                for (int var19 = p_180709_3_.HorizonCode_Horizon_È() - var11; var19 <= p_180709_3_.HorizonCode_Horizon_È() + var11; ++var19) {
                    final int var20 = var19 - p_180709_3_.HorizonCode_Horizon_È();
                    for (int var21 = p_180709_3_.Ý() - var11; var21 <= p_180709_3_.Ý() + var11; ++var21) {
                        final int var22 = var21 - p_180709_3_.Ý();
                        if (Math.abs(var20) != var11 || Math.abs(var22) != var11 || var11 <= 0) {
                            final BlockPos var23 = new BlockPos(var19, var18, var21);
                            if (!worldIn.Â(var23).Ý().HorizonCode_Horizon_È()) {
                                this.HorizonCode_Horizon_È(worldIn, var23, Blocks.µÕ, BlockPlanks.HorizonCode_Horizon_È.Â.Â());
                            }
                        }
                    }
                }
                if (var11 >= var12) {
                    var11 = var16;
                    var16 = 1;
                    if (++var12 > var7) {
                        var12 = var7;
                    }
                }
                else {
                    ++var11;
                }
            }
            int var17;
            for (var17 = p_180709_2_.nextInt(3), int var18 = 0; var18 < var4 - var17; ++var18) {
                final Block var24 = worldIn.Â(p_180709_3_.Â(var18)).Ý();
                if (var24.Ó() == Material.HorizonCode_Horizon_È || var24.Ó() == Material.áˆºÑ¢Õ) {
                    this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(var18), Blocks.¥Æ, BlockPlanks.HorizonCode_Horizon_È.Â.Â());
                }
            }
            return true;
        }
        return false;
    }
}
