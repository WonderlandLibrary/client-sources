package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenIceSpike extends WorldGenerator
{
    private static final String HorizonCode_Horizon_È = "CL_00000417";
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        while (worldIn.Ø­áŒŠá(p_180709_3_) && p_180709_3_.Â() > 2) {
            p_180709_3_ = p_180709_3_.Âµá€();
        }
        if (worldIn.Â(p_180709_3_).Ý() != Blocks.ˆà¢) {
            return false;
        }
        p_180709_3_ = p_180709_3_.Â(p_180709_2_.nextInt(4));
        final int var4 = p_180709_2_.nextInt(4) + 7;
        final int var5 = var4 / 4 + p_180709_2_.nextInt(2);
        if (var5 > 1 && p_180709_2_.nextInt(60) == 0) {
            p_180709_3_ = p_180709_3_.Â(10 + p_180709_2_.nextInt(30));
        }
        for (int var6 = 0; var6 < var4; ++var6) {
            final float var7 = (1.0f - var6 / var4) * var5;
            for (int var8 = MathHelper.Ó(var7), var9 = -var8; var9 <= var8; ++var9) {
                final float var10 = MathHelper.HorizonCode_Horizon_È(var9) - 0.25f;
                for (int var11 = -var8; var11 <= var8; ++var11) {
                    final float var12 = MathHelper.HorizonCode_Horizon_È(var11) - 0.25f;
                    if (((var9 == 0 && var11 == 0) || var10 * var10 + var12 * var12 <= var7 * var7) && ((var9 != -var8 && var9 != var8 && var11 != -var8 && var11 != var8) || p_180709_2_.nextFloat() <= 0.75f)) {
                        Block var13 = worldIn.Â(p_180709_3_.Â(var9, var6, var11)).Ý();
                        if (var13.Ó() == Material.HorizonCode_Horizon_È || var13 == Blocks.Âµá€ || var13 == Blocks.ˆà¢ || var13 == Blocks.¥Ï) {
                            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(var9, var6, var11), Blocks.ŠÂµÏ);
                        }
                        if (var6 != 0 && var8 > 1) {
                            var13 = worldIn.Â(p_180709_3_.Â(var9, -var6, var11)).Ý();
                            if (var13.Ó() == Material.HorizonCode_Horizon_È || var13 == Blocks.Âµá€ || var13 == Blocks.ˆà¢ || var13 == Blocks.¥Ï) {
                                this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(var9, -var6, var11), Blocks.ŠÂµÏ);
                            }
                        }
                    }
                }
            }
        }
        int var6 = var5 - 1;
        if (var6 < 0) {
            var6 = 0;
        }
        else if (var6 > 1) {
            var6 = 1;
        }
        for (int var14 = -var6; var14 <= var6; ++var14) {
            for (int var8 = -var6; var8 <= var6; ++var8) {
                BlockPos var15 = p_180709_3_.Â(var14, -1, var8);
                int var16 = 50;
                if (Math.abs(var14) == 1 && Math.abs(var8) == 1) {
                    var16 = p_180709_2_.nextInt(5);
                }
                while (var15.Â() > 50) {
                    final Block var17 = worldIn.Â(var15).Ý();
                    if (var17.Ó() != Material.HorizonCode_Horizon_È && var17 != Blocks.Âµá€ && var17 != Blocks.ˆà¢ && var17 != Blocks.¥Ï && var17 != Blocks.ŠÂµÏ) {
                        break;
                    }
                    this.HorizonCode_Horizon_È(worldIn, var15, Blocks.ŠÂµÏ);
                    var15 = var15.Âµá€();
                    if (--var16 > 0) {
                        continue;
                    }
                    var15 = var15.Ý(p_180709_2_.nextInt(5) + 1);
                    var16 = p_180709_2_.nextInt(5);
                }
            }
        }
        return true;
    }
}
