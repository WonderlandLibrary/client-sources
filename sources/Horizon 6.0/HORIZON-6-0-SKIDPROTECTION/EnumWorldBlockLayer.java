package HORIZON-6-0-SKIDPROTECTION;

public enum EnumWorldBlockLayer
{
    HorizonCode_Horizon_È("SOLID", 0, "SOLID", 0, "Solid"), 
    Â("CUTOUT_MIPPED", 1, "CUTOUT_MIPPED", 1, "Mipped Cutout"), 
    Ý("CUTOUT", 2, "CUTOUT", 2, "Cutout"), 
    Ø­áŒŠá("TRANSLUCENT", 3, "TRANSLUCENT", 3, "Translucent");
    
    private final String Âµá€;
    private static final EnumWorldBlockLayer[] Ó;
    private static final String à = "CL_00002152";
    
    static {
        Ø = new EnumWorldBlockLayer[] { EnumWorldBlockLayer.HorizonCode_Horizon_È, EnumWorldBlockLayer.Â, EnumWorldBlockLayer.Ý, EnumWorldBlockLayer.Ø­áŒŠá };
        Ó = new EnumWorldBlockLayer[] { EnumWorldBlockLayer.HorizonCode_Horizon_È, EnumWorldBlockLayer.Â, EnumWorldBlockLayer.Ý, EnumWorldBlockLayer.Ø­áŒŠá };
    }
    
    private EnumWorldBlockLayer(final String s, final int n, final String p_i45755_1_, final int p_i45755_2_, final String p_i45755_3_) {
        this.Âµá€ = p_i45755_3_;
    }
    
    @Override
    public String toString() {
        return this.Âµá€;
    }
}
