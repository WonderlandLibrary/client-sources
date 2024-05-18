package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenSpikes extends WorldGenerator
{
    private Block HorizonCode_Horizon_È;
    private static final String Â = "CL_00000433";
    
    public WorldGenSpikes(final Block p_i45464_1_) {
        this.HorizonCode_Horizon_È = p_i45464_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        if (worldIn.Ø­áŒŠá(p_180709_3_) && worldIn.Â(p_180709_3_.Âµá€()).Ý() == this.HorizonCode_Horizon_È) {
            final int var4 = p_180709_2_.nextInt(32) + 6;
            final int var5 = p_180709_2_.nextInt(4) + 1;
            for (int var6 = p_180709_3_.HorizonCode_Horizon_È() - var5; var6 <= p_180709_3_.HorizonCode_Horizon_È() + var5; ++var6) {
                for (int var7 = p_180709_3_.Ý() - var5; var7 <= p_180709_3_.Ý() + var5; ++var7) {
                    final int var8 = var6 - p_180709_3_.HorizonCode_Horizon_È();
                    final int var9 = var7 - p_180709_3_.Ý();
                    if (var8 * var8 + var9 * var9 <= var5 * var5 + 1 && worldIn.Â(new BlockPos(var6, p_180709_3_.Â() - 1, var7)).Ý() != this.HorizonCode_Horizon_È) {
                        return false;
                    }
                }
            }
            for (int var6 = p_180709_3_.Â(); var6 < p_180709_3_.Â() + var4 && var6 < 256; ++var6) {
                for (int var7 = p_180709_3_.HorizonCode_Horizon_È() - var5; var7 <= p_180709_3_.HorizonCode_Horizon_È() + var5; ++var7) {
                    for (int var8 = p_180709_3_.Ý() - var5; var8 <= p_180709_3_.Ý() + var5; ++var8) {
                        final int var9 = var7 - p_180709_3_.HorizonCode_Horizon_È();
                        final int var10 = var8 - p_180709_3_.Ý();
                        if (var9 * var9 + var10 * var10 <= var5 * var5 + 1) {
                            worldIn.HorizonCode_Horizon_È(new BlockPos(var7, var6, var8), Blocks.ÇŽá€.¥à(), 2);
                        }
                    }
                }
            }
            final EntityEnderCrystal var11 = new EntityEnderCrystal(worldIn);
            var11.Â(p_180709_3_.HorizonCode_Horizon_È() + 0.5f, p_180709_3_.Â() + var4, p_180709_3_.Ý() + 0.5f, p_180709_2_.nextFloat() * 360.0f, 0.0f);
            worldIn.HorizonCode_Horizon_È(var11);
            worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(var4), Blocks.áŒŠÆ.¥à(), 2);
            return true;
        }
        return false;
    }
}
