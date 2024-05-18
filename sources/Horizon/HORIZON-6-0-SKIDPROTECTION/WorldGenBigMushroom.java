package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenBigMushroom extends WorldGenerator
{
    private int HorizonCode_Horizon_È;
    private static final String Â = "CL_00000415";
    
    public WorldGenBigMushroom(final int p_i2017_1_) {
        super(true);
        this.HorizonCode_Horizon_È = -1;
        this.HorizonCode_Horizon_È = p_i2017_1_;
    }
    
    public WorldGenBigMushroom() {
        super(false);
        this.HorizonCode_Horizon_È = -1;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        int var4 = p_180709_2_.nextInt(2);
        if (this.HorizonCode_Horizon_È >= 0) {
            var4 = this.HorizonCode_Horizon_È;
        }
        final int var5 = p_180709_2_.nextInt(3) + 4;
        boolean var6 = true;
        if (p_180709_3_.Â() < 1 || p_180709_3_.Â() + var5 + 1 >= 256) {
            return false;
        }
        for (int var7 = p_180709_3_.Â(); var7 <= p_180709_3_.Â() + 1 + var5; ++var7) {
            byte var8 = 3;
            if (var7 <= p_180709_3_.Â() + 3) {
                var8 = 0;
            }
            for (int var9 = p_180709_3_.HorizonCode_Horizon_È() - var8; var9 <= p_180709_3_.HorizonCode_Horizon_È() + var8 && var6; ++var9) {
                for (int var10 = p_180709_3_.Ý() - var8; var10 <= p_180709_3_.Ý() + var8 && var6; ++var10) {
                    if (var7 >= 0 && var7 < 256) {
                        final Block var11 = worldIn.Â(new BlockPos(var9, var7, var10)).Ý();
                        if (var11.Ó() != Material.HorizonCode_Horizon_È && var11.Ó() != Material.áˆºÑ¢Õ) {
                            var6 = false;
                        }
                    }
                    else {
                        var6 = false;
                    }
                }
            }
        }
        if (!var6) {
            return false;
        }
        final Block var12 = worldIn.Â(p_180709_3_.Âµá€()).Ý();
        if (var12 != Blocks.Âµá€ && var12 != Blocks.Ø­áŒŠá && var12 != Blocks.Œáƒ) {
            return false;
        }
        int var13 = p_180709_3_.Â() + var5;
        if (var4 == 1) {
            var13 = p_180709_3_.Â() + var5 - 3;
        }
        for (int var9 = var13; var9 <= p_180709_3_.Â() + var5; ++var9) {
            int var10 = 1;
            if (var9 < p_180709_3_.Â() + var5) {
                ++var10;
            }
            if (var4 == 0) {
                var10 = 3;
            }
            for (int var14 = p_180709_3_.HorizonCode_Horizon_È() - var10; var14 <= p_180709_3_.HorizonCode_Horizon_È() + var10; ++var14) {
                for (int var15 = p_180709_3_.Ý() - var10; var15 <= p_180709_3_.Ý() + var10; ++var15) {
                    int var16 = 5;
                    if (var14 == p_180709_3_.HorizonCode_Horizon_È() - var10) {
                        --var16;
                    }
                    if (var14 == p_180709_3_.HorizonCode_Horizon_È() + var10) {
                        ++var16;
                    }
                    if (var15 == p_180709_3_.Ý() - var10) {
                        var16 -= 3;
                    }
                    if (var15 == p_180709_3_.Ý() + var10) {
                        var16 += 3;
                    }
                    if (var4 == 0 || var9 < p_180709_3_.Â() + var5) {
                        if (var14 == p_180709_3_.HorizonCode_Horizon_È() - var10 || var14 == p_180709_3_.HorizonCode_Horizon_È() + var10) {
                            if (var15 == p_180709_3_.Ý() - var10) {
                                continue;
                            }
                            if (var15 == p_180709_3_.Ý() + var10) {
                                continue;
                            }
                        }
                        if (var14 == p_180709_3_.HorizonCode_Horizon_È() - (var10 - 1) && var15 == p_180709_3_.Ý() - var10) {
                            var16 = 1;
                        }
                        if (var14 == p_180709_3_.HorizonCode_Horizon_È() - var10 && var15 == p_180709_3_.Ý() - (var10 - 1)) {
                            var16 = 1;
                        }
                        if (var14 == p_180709_3_.HorizonCode_Horizon_È() + (var10 - 1) && var15 == p_180709_3_.Ý() - var10) {
                            var16 = 3;
                        }
                        if (var14 == p_180709_3_.HorizonCode_Horizon_È() + var10 && var15 == p_180709_3_.Ý() - (var10 - 1)) {
                            var16 = 3;
                        }
                        if (var14 == p_180709_3_.HorizonCode_Horizon_È() - (var10 - 1) && var15 == p_180709_3_.Ý() + var10) {
                            var16 = 7;
                        }
                        if (var14 == p_180709_3_.HorizonCode_Horizon_È() - var10 && var15 == p_180709_3_.Ý() + (var10 - 1)) {
                            var16 = 7;
                        }
                        if (var14 == p_180709_3_.HorizonCode_Horizon_È() + (var10 - 1) && var15 == p_180709_3_.Ý() + var10) {
                            var16 = 9;
                        }
                        if (var14 == p_180709_3_.HorizonCode_Horizon_È() + var10 && var15 == p_180709_3_.Ý() + (var10 - 1)) {
                            var16 = 9;
                        }
                    }
                    if (var16 == 5 && var9 < p_180709_3_.Â() + var5) {
                        var16 = 0;
                    }
                    if (var16 != 0 || p_180709_3_.Â() >= p_180709_3_.Â() + var5 - 1) {
                        final BlockPos var17 = new BlockPos(var14, var9, var15);
                        if (!worldIn.Â(var17).Ý().HorizonCode_Horizon_È()) {
                            this.HorizonCode_Horizon_È(worldIn, var17, Block.HorizonCode_Horizon_È(Block.HorizonCode_Horizon_È(Blocks.Ï­áˆºÓ) + var4), var16);
                        }
                    }
                }
            }
        }
        for (int var9 = 0; var9 < var5; ++var9) {
            final Block var18 = worldIn.Â(p_180709_3_.Â(var9)).Ý();
            if (!var18.HorizonCode_Horizon_È()) {
                this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(var9), Block.HorizonCode_Horizon_È(Block.HorizonCode_Horizon_È(Blocks.Ï­áˆºÓ) + var4), 10);
            }
        }
        return true;
    }
}
