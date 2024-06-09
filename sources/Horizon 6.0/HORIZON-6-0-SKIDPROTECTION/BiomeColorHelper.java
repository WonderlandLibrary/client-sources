package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

public class BiomeColorHelper
{
    private static final HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private static final HorizonCode_Horizon_È Â;
    private static final HorizonCode_Horizon_È Ý;
    private static final String Ø­áŒŠá = "CL_00002149";
    
    static {
        HorizonCode_Horizon_È = new HorizonCode_Horizon_È() {
            private static final String HorizonCode_Horizon_È = "CL_00002148";
            
            @Override
            public int HorizonCode_Horizon_È(final BiomeGenBase p_180283_1_, final BlockPos p_180283_2_) {
                return p_180283_1_.Â(p_180283_2_);
            }
        };
        Â = new HorizonCode_Horizon_È() {
            private static final String HorizonCode_Horizon_È = "CL_00002147";
            
            @Override
            public int HorizonCode_Horizon_È(final BiomeGenBase p_180283_1_, final BlockPos p_180283_2_) {
                return p_180283_1_.Ý(p_180283_2_);
            }
        };
        Ý = new HorizonCode_Horizon_È() {
            private static final String HorizonCode_Horizon_È = "CL_00002146";
            
            @Override
            public int HorizonCode_Horizon_È(final BiomeGenBase p_180283_1_, final BlockPos p_180283_2_) {
                return p_180283_1_.Ðƒá;
            }
        };
    }
    
    private static int HorizonCode_Horizon_È(final IBlockAccess p_180285_0_, final BlockPos p_180285_1_, final HorizonCode_Horizon_È p_180285_2_) {
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        for (final BlockPos.HorizonCode_Horizon_È var7 : BlockPos.Ý(p_180285_1_.Â(-1, 0, -1), p_180285_1_.Â(1, 0, 1))) {
            final int var8 = p_180285_2_.HorizonCode_Horizon_È(p_180285_0_.Ý(var7), var7);
            var3 += (var8 & 0xFF0000) >> 16;
            var4 += (var8 & 0xFF00) >> 8;
            var5 += (var8 & 0xFF);
        }
        return (var3 / 9 & 0xFF) << 16 | (var4 / 9 & 0xFF) << 8 | (var5 / 9 & 0xFF);
    }
    
    public static int HorizonCode_Horizon_È(final IBlockAccess p_180286_0_, final BlockPos p_180286_1_) {
        return HorizonCode_Horizon_È(p_180286_0_, p_180286_1_, BiomeColorHelper.HorizonCode_Horizon_È);
    }
    
    public static int Â(final IBlockAccess p_180287_0_, final BlockPos p_180287_1_) {
        return HorizonCode_Horizon_È(p_180287_0_, p_180287_1_, BiomeColorHelper.Â);
    }
    
    public static int Ý(final IBlockAccess p_180288_0_, final BlockPos p_180288_1_) {
        return HorizonCode_Horizon_È(p_180288_0_, p_180288_1_, BiomeColorHelper.Ý);
    }
    
    interface HorizonCode_Horizon_È
    {
        int HorizonCode_Horizon_È(final BiomeGenBase p0, final BlockPos p1);
    }
}
