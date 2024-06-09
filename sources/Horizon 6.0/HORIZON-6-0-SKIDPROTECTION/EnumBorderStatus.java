package HORIZON-6-0-SKIDPROTECTION;

public enum EnumBorderStatus
{
    HorizonCode_Horizon_È("GROWING", 0, "GROWING", 0, 4259712), 
    Â("SHRINKING", 1, "SHRINKING", 1, 16724016), 
    Ý("STATIONARY", 2, "STATIONARY", 2, 2138367);
    
    private final int Ø­áŒŠá;
    private static final EnumBorderStatus[] Âµá€;
    private static final String Ó = "CL_00002013";
    
    static {
        à = new EnumBorderStatus[] { EnumBorderStatus.HorizonCode_Horizon_È, EnumBorderStatus.Â, EnumBorderStatus.Ý };
        Âµá€ = new EnumBorderStatus[] { EnumBorderStatus.HorizonCode_Horizon_È, EnumBorderStatus.Â, EnumBorderStatus.Ý };
    }
    
    private EnumBorderStatus(final String s, final int n, final String p_i45647_1_, final int p_i45647_2_, final int id) {
        this.Ø­áŒŠá = id;
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
}
