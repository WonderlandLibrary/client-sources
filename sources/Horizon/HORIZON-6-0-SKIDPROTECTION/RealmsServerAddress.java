package HORIZON-6-0-SKIDPROTECTION;

public class RealmsServerAddress
{
    private final String HorizonCode_Horizon_È;
    private final int Â;
    private static final String Ý = "CL_00001864";
    
    protected RealmsServerAddress(final String p_i1121_1_, final int p_i1121_2_) {
        this.HorizonCode_Horizon_È = p_i1121_1_;
        this.Â = p_i1121_2_;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public static RealmsServerAddress HorizonCode_Horizon_È(final String p_parseString_0_) {
        final ServerAddress var1 = ServerAddress.HorizonCode_Horizon_È(p_parseString_0_);
        return new RealmsServerAddress(var1.HorizonCode_Horizon_È(), var1.Â());
    }
}
