package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenTrees extends WorldGenAbstractTree
{
    private final int HorizonCode_Horizon_È;
    private final boolean Â;
    private final int Ý;
    private final int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000438";
    
    public WorldGenTrees(final boolean p_i2027_1_) {
        this(p_i2027_1_, 4, 0, 0, false);
    }
    
    public WorldGenTrees(final boolean p_i2028_1_, final int p_i2028_2_, final int p_i2028_3_, final int p_i2028_4_, final boolean p_i2028_5_) {
        super(p_i2028_1_);
        this.HorizonCode_Horizon_È = p_i2028_2_;
        this.Ý = p_i2028_3_;
        this.Ø­áŒŠá = p_i2028_4_;
        this.Â = p_i2028_5_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        final int var4 = p_180709_2_.nextInt(3) + this.HorizonCode_Horizon_È;
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
            final byte var7 = 3;
            final byte var11 = 0;
            for (int var9 = p_180709_3_.Â() - var7 + var4; var9 <= p_180709_3_.Â() + var4; ++var9) {
                final int var12 = var9 - (p_180709_3_.Â() + var4);
                for (int var13 = var11 + 1 - var12 / 2, var14 = p_180709_3_.HorizonCode_Horizon_È() - var13; var14 <= p_180709_3_.HorizonCode_Horizon_È() + var13; ++var14) {
                    final int var15 = var14 - p_180709_3_.HorizonCode_Horizon_È();
                    for (int var16 = p_180709_3_.Ý() - var13; var16 <= p_180709_3_.Ý() + var13; ++var16) {
                        final int var17 = var16 - p_180709_3_.Ý();
                        if (Math.abs(var15) != var13 || Math.abs(var17) != var13 || (p_180709_2_.nextInt(2) != 0 && var12 != 0)) {
                            final BlockPos var18 = new BlockPos(var14, var9, var16);
                            final Block var19 = worldIn.Â(var18).Ý();
                            if (var19.Ó() == Material.HorizonCode_Horizon_È || var19.Ó() == Material.áˆºÑ¢Õ || var19.Ó() == Material.á) {
                                this.HorizonCode_Horizon_È(worldIn, var18, Blocks.µÕ, this.Ø­áŒŠá);
                            }
                        }
                    }
                }
            }
            for (int var9 = 0; var9 < var4; ++var9) {
                final Block var20 = worldIn.Â(p_180709_3_.Â(var9)).Ý();
                if (var20.Ó() == Material.HorizonCode_Horizon_È || var20.Ó() == Material.áˆºÑ¢Õ || var20.Ó() == Material.á) {
                    this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(var9), Blocks.¥Æ, this.Ý);
                    if (this.Â && var9 > 0) {
                        if (p_180709_2_.nextInt(3) > 0 && worldIn.Ø­áŒŠá(p_180709_3_.Â(-1, var9, 0))) {
                            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(-1, var9, 0), Blocks.ÇŽà, BlockVine.ˆáŠ);
                        }
                        if (p_180709_2_.nextInt(3) > 0 && worldIn.Ø­áŒŠá(p_180709_3_.Â(1, var9, 0))) {
                            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(1, var9, 0), Blocks.ÇŽà, BlockVine.áŒŠ);
                        }
                        if (p_180709_2_.nextInt(3) > 0 && worldIn.Ø­áŒŠá(p_180709_3_.Â(0, var9, -1))) {
                            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(0, var9, -1), Blocks.ÇŽà, BlockVine.È);
                        }
                        if (p_180709_2_.nextInt(3) > 0 && worldIn.Ø­áŒŠá(p_180709_3_.Â(0, var9, 1))) {
                            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(0, var9, 1), Blocks.ÇŽà, BlockVine.áŠ);
                        }
                    }
                }
            }
            if (this.Â) {
                for (int var9 = p_180709_3_.Â() - 3 + var4; var9 <= p_180709_3_.Â() + var4; ++var9) {
                    final int var12 = var9 - (p_180709_3_.Â() + var4);
                    for (int var13 = 2 - var12 / 2, var14 = p_180709_3_.HorizonCode_Horizon_È() - var13; var14 <= p_180709_3_.HorizonCode_Horizon_È() + var13; ++var14) {
                        for (int var15 = p_180709_3_.Ý() - var13; var15 <= p_180709_3_.Ý() + var13; ++var15) {
                            final BlockPos var21 = new BlockPos(var14, var9, var15);
                            if (worldIn.Â(var21).Ý().Ó() == Material.áˆºÑ¢Õ) {
                                final BlockPos var22 = var21.Ø();
                                final BlockPos var18 = var21.áŒŠÆ();
                                final BlockPos var23 = var21.Ó();
                                final BlockPos var24 = var21.à();
                                if (p_180709_2_.nextInt(4) == 0 && worldIn.Â(var22).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                                    this.HorizonCode_Horizon_È(worldIn, var22, BlockVine.ˆáŠ);
                                }
                                if (p_180709_2_.nextInt(4) == 0 && worldIn.Â(var18).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                                    this.HorizonCode_Horizon_È(worldIn, var18, BlockVine.áŒŠ);
                                }
                                if (p_180709_2_.nextInt(4) == 0 && worldIn.Â(var23).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                                    this.HorizonCode_Horizon_È(worldIn, var23, BlockVine.È);
                                }
                                if (p_180709_2_.nextInt(4) == 0 && worldIn.Â(var24).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                                    this.HorizonCode_Horizon_È(worldIn, var24, BlockVine.áŠ);
                                }
                            }
                        }
                    }
                }
                if (p_180709_2_.nextInt(5) == 0 && var4 > 5) {
                    for (int var9 = 0; var9 < 2; ++var9) {
                        for (int var12 = 0; var12 < 4; ++var12) {
                            if (p_180709_2_.nextInt(4 - var9) == 0) {
                                final int var13 = p_180709_2_.nextInt(3);
                                final EnumFacing var25 = EnumFacing.Â(var12).Âµá€();
                                this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(var25.Ø(), var4 - 5 + var9, var25.áˆºÑ¢Õ()), Blocks.µ, var13 << 2 | EnumFacing.Â(var12).Ý());
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void HorizonCode_Horizon_È(final World worldIn, BlockPos p_175923_2_, final int p_175923_3_) {
        this.HorizonCode_Horizon_È(worldIn, p_175923_2_, Blocks.ÇŽà, p_175923_3_);
        int var4;
        for (var4 = 4, p_175923_2_ = p_175923_2_.Âµá€(); worldIn.Â(p_175923_2_).Ý().Ó() == Material.HorizonCode_Horizon_È && var4 > 0; p_175923_2_ = p_175923_2_.Âµá€(), --var4) {
            this.HorizonCode_Horizon_È(worldIn, p_175923_2_, Blocks.ÇŽà, p_175923_3_);
        }
    }
}
