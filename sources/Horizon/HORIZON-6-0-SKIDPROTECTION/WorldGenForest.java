package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenForest extends WorldGenAbstractTree
{
    private boolean HorizonCode_Horizon_È;
    private static final String Â = "CL_00000401";
    
    public WorldGenForest(final boolean p_i45449_1_, final boolean p_i45449_2_) {
        super(p_i45449_1_);
        this.HorizonCode_Horizon_È = p_i45449_2_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        int var4 = p_180709_2_.nextInt(3) + 5;
        if (this.HorizonCode_Horizon_È) {
            var4 += p_180709_2_.nextInt(7);
        }
        boolean var5 = true;
        if (p_180709_3_.Â() < 1 || p_180709_3_.Â() + var4 + 1 > 256) {
            return false;
        }
        for (int var6 = p_180709_3_.Â(); var6 <= p_180709_3_.Â() + 1 + var4; ++var6) {
            byte var7 = 1;
            if (var6 == p_180709_3_.Â()) {
                var7 = 0;
            }
            if (var6 >= p_180709_3_.Â() + 1 + var4 - 2) {
                var7 = 2;
            }
            for (int var8 = p_180709_3_.HorizonCode_Horizon_È() - var7; var8 <= p_180709_3_.HorizonCode_Horizon_È() + var7 && var5; ++var8) {
                for (int var9 = p_180709_3_.Ý() - var7; var9 <= p_180709_3_.Ý() + var7 && var5; ++var9) {
                    if (var6 >= 0 && var6 < 256) {
                        if (!this.HorizonCode_Horizon_È(worldIn.Â(new BlockPos(var8, var6, var9)).Ý())) {
                            var5 = false;
                        }
                    }
                    else {
                        var5 = false;
                    }
                }
            }
        }
        if (!var5) {
            return false;
        }
        final Block var10 = worldIn.Â(p_180709_3_.Âµá€()).Ý();
        if ((var10 == Blocks.Ø­áŒŠá || var10 == Blocks.Âµá€ || var10 == Blocks.£Â) && p_180709_3_.Â() < 256 - var4 - 1) {
            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Âµá€());
            for (int var11 = p_180709_3_.Â() - 3 + var4; var11 <= p_180709_3_.Â() + var4; ++var11) {
                final int var8 = var11 - (p_180709_3_.Â() + var4);
                for (int var9 = 1 - var8 / 2, var12 = p_180709_3_.HorizonCode_Horizon_È() - var9; var12 <= p_180709_3_.HorizonCode_Horizon_È() + var9; ++var12) {
                    final int var13 = var12 - p_180709_3_.HorizonCode_Horizon_È();
                    for (int var14 = p_180709_3_.Ý() - var9; var14 <= p_180709_3_.Ý() + var9; ++var14) {
                        final int var15 = var14 - p_180709_3_.Ý();
                        if (Math.abs(var13) != var9 || Math.abs(var15) != var9 || (p_180709_2_.nextInt(2) != 0 && var8 != 0)) {
                            final BlockPos var16 = new BlockPos(var12, var11, var14);
                            final Block var17 = worldIn.Â(var16).Ý();
                            if (var17.Ó() == Material.HorizonCode_Horizon_È || var17.Ó() == Material.áˆºÑ¢Õ) {
                                this.HorizonCode_Horizon_È(worldIn, var16, Blocks.µÕ, BlockPlanks.HorizonCode_Horizon_È.Ý.Â());
                            }
                        }
                    }
                }
            }
            for (int var11 = 0; var11 < var4; ++var11) {
                final Block var18 = worldIn.Â(p_180709_3_.Â(var11)).Ý();
                if (var18.Ó() == Material.HorizonCode_Horizon_È || var18.Ó() == Material.áˆºÑ¢Õ) {
                    this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(var11), Blocks.¥Æ, BlockPlanks.HorizonCode_Horizon_È.Ý.Â());
                }
            }
            return true;
        }
        return false;
    }
}
