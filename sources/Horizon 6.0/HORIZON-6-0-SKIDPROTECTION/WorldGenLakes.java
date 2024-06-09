package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenLakes extends WorldGenerator
{
    private Block HorizonCode_Horizon_È;
    private static final String Â = "CL_00000418";
    
    public WorldGenLakes(final Block p_i45455_1_) {
        this.HorizonCode_Horizon_È = p_i45455_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        for (p_180709_3_ = p_180709_3_.Â(-8, 0, -8); p_180709_3_.Â() > 5 && worldIn.Ø­áŒŠá(p_180709_3_); p_180709_3_ = p_180709_3_.Âµá€()) {}
        if (p_180709_3_.Â() <= 4) {
            return false;
        }
        p_180709_3_ = p_180709_3_.Ý(4);
        final boolean[] var4 = new boolean[2048];
        for (int var5 = p_180709_2_.nextInt(4) + 4, var6 = 0; var6 < var5; ++var6) {
            final double var7 = p_180709_2_.nextDouble() * 6.0 + 3.0;
            final double var8 = p_180709_2_.nextDouble() * 4.0 + 2.0;
            final double var9 = p_180709_2_.nextDouble() * 6.0 + 3.0;
            final double var10 = p_180709_2_.nextDouble() * (16.0 - var7 - 2.0) + 1.0 + var7 / 2.0;
            final double var11 = p_180709_2_.nextDouble() * (8.0 - var8 - 4.0) + 2.0 + var8 / 2.0;
            final double var12 = p_180709_2_.nextDouble() * (16.0 - var9 - 2.0) + 1.0 + var9 / 2.0;
            for (int var13 = 1; var13 < 15; ++var13) {
                for (int var14 = 1; var14 < 15; ++var14) {
                    for (int var15 = 1; var15 < 7; ++var15) {
                        final double var16 = (var13 - var10) / (var7 / 2.0);
                        final double var17 = (var15 - var11) / (var8 / 2.0);
                        final double var18 = (var14 - var12) / (var9 / 2.0);
                        final double var19 = var16 * var16 + var17 * var17 + var18 * var18;
                        if (var19 < 1.0) {
                            var4[(var13 * 16 + var14) * 8 + var15] = true;
                        }
                    }
                }
            }
        }
        for (int var6 = 0; var6 < 16; ++var6) {
            for (int var20 = 0; var20 < 16; ++var20) {
                for (int var21 = 0; var21 < 8; ++var21) {
                    final boolean var22 = !var4[(var6 * 16 + var20) * 8 + var21] && ((var6 < 15 && var4[((var6 + 1) * 16 + var20) * 8 + var21]) || (var6 > 0 && var4[((var6 - 1) * 16 + var20) * 8 + var21]) || (var20 < 15 && var4[(var6 * 16 + var20 + 1) * 8 + var21]) || (var20 > 0 && var4[(var6 * 16 + (var20 - 1)) * 8 + var21]) || (var21 < 7 && var4[(var6 * 16 + var20) * 8 + var21 + 1]) || (var21 > 0 && var4[(var6 * 16 + var20) * 8 + (var21 - 1)]));
                    if (var22) {
                        final Material var23 = worldIn.Â(p_180709_3_.Â(var6, var21, var20)).Ý().Ó();
                        if (var21 >= 4 && var23.HorizonCode_Horizon_È()) {
                            return false;
                        }
                        if (var21 < 4 && !var23.Â() && worldIn.Â(p_180709_3_.Â(var6, var21, var20)).Ý() != this.HorizonCode_Horizon_È) {
                            return false;
                        }
                    }
                }
            }
        }
        for (int var6 = 0; var6 < 16; ++var6) {
            for (int var20 = 0; var20 < 16; ++var20) {
                for (int var21 = 0; var21 < 8; ++var21) {
                    if (var4[(var6 * 16 + var20) * 8 + var21]) {
                        worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(var6, var21, var20), (var21 >= 4) ? Blocks.Â.¥à() : this.HorizonCode_Horizon_È.¥à(), 2);
                    }
                }
            }
        }
        for (int var6 = 0; var6 < 16; ++var6) {
            for (int var20 = 0; var20 < 16; ++var20) {
                for (int var21 = 4; var21 < 8; ++var21) {
                    if (var4[(var6 * 16 + var20) * 8 + var21]) {
                        final BlockPos var24 = p_180709_3_.Â(var6, var21 - 1, var20);
                        if (worldIn.Â(var24).Ý() == Blocks.Âµá€ && worldIn.Â(EnumSkyBlock.HorizonCode_Horizon_È, p_180709_3_.Â(var6, var21, var20)) > 0) {
                            final BiomeGenBase var25 = worldIn.Ý(var24);
                            if (var25.Ï­Ï­Ï.Ý() == Blocks.Œáƒ) {
                                worldIn.HorizonCode_Horizon_È(var24, Blocks.Œáƒ.¥à(), 2);
                            }
                            else {
                                worldIn.HorizonCode_Horizon_È(var24, Blocks.Ø­áŒŠá.¥à(), 2);
                            }
                        }
                    }
                }
            }
        }
        if (this.HorizonCode_Horizon_È.Ó() == Material.áŒŠÆ) {
            for (int var6 = 0; var6 < 16; ++var6) {
                for (int var20 = 0; var20 < 16; ++var20) {
                    for (int var21 = 0; var21 < 8; ++var21) {
                        final boolean var22 = !var4[(var6 * 16 + var20) * 8 + var21] && ((var6 < 15 && var4[((var6 + 1) * 16 + var20) * 8 + var21]) || (var6 > 0 && var4[((var6 - 1) * 16 + var20) * 8 + var21]) || (var20 < 15 && var4[(var6 * 16 + var20 + 1) * 8 + var21]) || (var20 > 0 && var4[(var6 * 16 + (var20 - 1)) * 8 + var21]) || (var21 < 7 && var4[(var6 * 16 + var20) * 8 + var21 + 1]) || (var21 > 0 && var4[(var6 * 16 + var20) * 8 + (var21 - 1)]));
                        if (var22 && (var21 < 4 || p_180709_2_.nextInt(2) != 0) && worldIn.Â(p_180709_3_.Â(var6, var21, var20)).Ý().Ó().Â()) {
                            worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(var6, var21, var20), Blocks.Ý.¥à(), 2);
                        }
                    }
                }
            }
        }
        if (this.HorizonCode_Horizon_È.Ó() == Material.Ø) {
            for (int var6 = 0; var6 < 16; ++var6) {
                for (int var20 = 0; var20 < 16; ++var20) {
                    final byte var26 = 4;
                    if (worldIn.µÕ(p_180709_3_.Â(var6, var26, var20))) {
                        worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(var6, var26, var20), Blocks.¥Ï.¥à(), 2);
                    }
                }
            }
        }
        return true;
    }
}
