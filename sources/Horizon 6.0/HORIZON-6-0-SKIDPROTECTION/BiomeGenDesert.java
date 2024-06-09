package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BiomeGenDesert extends BiomeGenBase
{
    private static final String Ñ¢à = "CL_00000167";
    
    public BiomeGenDesert(final int p_i1977_1_) {
        super(p_i1977_1_);
        this.ÇªÂµÕ.clear();
        this.Ï­Ï­Ï = Blocks.£á.¥à();
        this.£Â = Blocks.£á.¥à();
        this.ˆÏ.Ñ¢á = -999;
        this.ˆÏ.Ê = 2;
        this.ˆÏ.ˆá = 50;
        this.ˆÏ.ÇŽÕ = 10;
        this.ÇªÂµÕ.clear();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        super.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_);
        if (p_180624_2_.nextInt(1000) == 0) {
            final int var4 = p_180624_2_.nextInt(16) + 8;
            final int var5 = p_180624_2_.nextInt(16) + 8;
            final BlockPos var6 = worldIn.£á(p_180624_3_.Â(var4, 0, var5)).Ø­áŒŠá();
            new WorldGenDesertWells().HorizonCode_Horizon_È(worldIn, p_180624_2_, var6);
        }
    }
}
