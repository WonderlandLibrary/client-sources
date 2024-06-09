package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenSwamp extends WorldGenAbstractTree
{
    private static final String HorizonCode_Horizon_È = "CL_00000436";
    
    public WorldGenSwamp() {
        super(false);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        final int var4 = p_180709_2_.nextInt(4) + 5;
        while (worldIn.Â(p_180709_3_.Âµá€()).Ý().Ó() == Material.Ø) {
            p_180709_3_ = p_180709_3_.Âµá€();
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
                var7 = 3;
            }
            for (int var8 = p_180709_3_.HorizonCode_Horizon_È() - var7; var8 <= p_180709_3_.HorizonCode_Horizon_È() + var7 && var5; ++var8) {
                for (int var9 = p_180709_3_.Ý() - var7; var9 <= p_180709_3_.Ý() + var7 && var5; ++var9) {
                    if (var6 >= 0 && var6 < 256) {
                        final Block var10 = worldIn.Â(new BlockPos(var8, var6, var9)).Ý();
                        if (var10.Ó() != Material.HorizonCode_Horizon_È && var10.Ó() != Material.áˆºÑ¢Õ) {
                            if (var10 != Blocks.ÂµÈ && var10 != Blocks.áˆºÑ¢Õ) {
                                var5 = false;
                            }
                            else if (var6 > p_180709_3_.Â()) {
                                var5 = false;
                            }
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
        final Block var11 = worldIn.Â(p_180709_3_.Âµá€()).Ý();
        if ((var11 == Blocks.Ø­áŒŠá || var11 == Blocks.Âµá€) && p_180709_3_.Â() < 256 - var4 - 1) {
            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Âµá€());
            for (int var12 = p_180709_3_.Â() - 3 + var4; var12 <= p_180709_3_.Â() + var4; ++var12) {
                final int var8 = var12 - (p_180709_3_.Â() + var4);
                for (int var9 = 2 - var8 / 2, var13 = p_180709_3_.HorizonCode_Horizon_È() - var9; var13 <= p_180709_3_.HorizonCode_Horizon_È() + var9; ++var13) {
                    final int var14 = var13 - p_180709_3_.HorizonCode_Horizon_È();
                    for (int var15 = p_180709_3_.Ý() - var9; var15 <= p_180709_3_.Ý() + var9; ++var15) {
                        final int var16 = var15 - p_180709_3_.Ý();
                        if (Math.abs(var14) != var9 || Math.abs(var16) != var9 || (p_180709_2_.nextInt(2) != 0 && var8 != 0)) {
                            final BlockPos var17 = new BlockPos(var13, var12, var15);
                            if (!worldIn.Â(var17).Ý().HorizonCode_Horizon_È()) {
                                this.HorizonCode_Horizon_È(worldIn, var17, Blocks.µÕ);
                            }
                        }
                    }
                }
            }
            for (int var12 = 0; var12 < var4; ++var12) {
                final Block var18 = worldIn.Â(p_180709_3_.Â(var12)).Ý();
                if (var18.Ó() == Material.HorizonCode_Horizon_È || var18.Ó() == Material.áˆºÑ¢Õ || var18 == Blocks.áˆºÑ¢Õ || var18 == Blocks.ÂµÈ) {
                    this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(var12), Blocks.¥Æ);
                }
            }
            for (int var12 = p_180709_3_.Â() - 3 + var4; var12 <= p_180709_3_.Â() + var4; ++var12) {
                final int var8 = var12 - (p_180709_3_.Â() + var4);
                for (int var9 = 2 - var8 / 2, var13 = p_180709_3_.HorizonCode_Horizon_È() - var9; var13 <= p_180709_3_.HorizonCode_Horizon_È() + var9; ++var13) {
                    for (int var14 = p_180709_3_.Ý() - var9; var14 <= p_180709_3_.Ý() + var9; ++var14) {
                        final BlockPos var19 = new BlockPos(var13, var12, var14);
                        if (worldIn.Â(var19).Ý().Ó() == Material.áˆºÑ¢Õ) {
                            final BlockPos var20 = var19.Ø();
                            final BlockPos var17 = var19.áŒŠÆ();
                            final BlockPos var21 = var19.Ó();
                            final BlockPos var22 = var19.à();
                            if (p_180709_2_.nextInt(4) == 0 && worldIn.Â(var20).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                                this.HorizonCode_Horizon_È(worldIn, var20, BlockVine.ˆáŠ);
                            }
                            if (p_180709_2_.nextInt(4) == 0 && worldIn.Â(var17).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                                this.HorizonCode_Horizon_È(worldIn, var17, BlockVine.áŒŠ);
                            }
                            if (p_180709_2_.nextInt(4) == 0 && worldIn.Â(var21).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                                this.HorizonCode_Horizon_È(worldIn, var21, BlockVine.È);
                            }
                            if (p_180709_2_.nextInt(4) == 0 && worldIn.Â(var22).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                                this.HorizonCode_Horizon_È(worldIn, var22, BlockVine.áŠ);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void HorizonCode_Horizon_È(final World worldIn, BlockPos p_175922_2_, final int p_175922_3_) {
        this.HorizonCode_Horizon_È(worldIn, p_175922_2_, Blocks.ÇŽà, p_175922_3_);
        int var4;
        for (var4 = 4, p_175922_2_ = p_175922_2_.Âµá€(); worldIn.Â(p_175922_2_).Ý().Ó() == Material.HorizonCode_Horizon_È && var4 > 0; p_175922_2_ = p_175922_2_.Âµá€(), --var4) {
            this.HorizonCode_Horizon_È(worldIn, p_175922_2_, Blocks.ÇŽà, p_175922_3_);
        }
    }
}
