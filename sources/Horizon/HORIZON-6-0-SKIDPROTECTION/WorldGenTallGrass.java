package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenTallGrass extends WorldGenerator
{
    private final IBlockState HorizonCode_Horizon_È;
    private static final String Â = "CL_00000437";
    
    public WorldGenTallGrass(final BlockTallGrass.HorizonCode_Horizon_È p_i45629_1_) {
        this.HorizonCode_Horizon_È = Blocks.áƒ.¥à().HorizonCode_Horizon_È(BlockTallGrass.Õ, p_i45629_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        Block var4;
        while (((var4 = worldIn.Â(p_180709_3_).Ý()).Ó() == Material.HorizonCode_Horizon_È || var4.Ó() == Material.áˆºÑ¢Õ) && p_180709_3_.Â() > 0) {
            p_180709_3_ = p_180709_3_.Âµá€();
        }
        for (int var5 = 0; var5 < 128; ++var5) {
            final BlockPos var6 = p_180709_3_.Â(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (worldIn.Ø­áŒŠá(var6) && Blocks.áƒ.Ó(worldIn, var6, this.HorizonCode_Horizon_È)) {
                worldIn.HorizonCode_Horizon_È(var6, this.HorizonCode_Horizon_È, 2);
            }
        }
        return true;
    }
}
