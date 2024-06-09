package HORIZON-6-0-SKIDPROTECTION;

public enum EnumAction
{
    HorizonCode_Horizon_È("NONE", 0, "NONE", 0), 
    Â("EAT", 1, "EAT", 1), 
    Ý("DRINK", 2, "DRINK", 2), 
    Ø­áŒŠá("BLOCK", 3, "BLOCK", 3), 
    Âµá€("BOW", 4, "BOW", 4);
    
    private static final EnumAction[] Ó;
    private static final String à = "CL_00000073";
    
    static {
        Ø = new EnumAction[] { EnumAction.HorizonCode_Horizon_È, EnumAction.Â, EnumAction.Ý, EnumAction.Ø­áŒŠá, EnumAction.Âµá€ };
        Ó = new EnumAction[] { EnumAction.HorizonCode_Horizon_È, EnumAction.Â, EnumAction.Ý, EnumAction.Ø­áŒŠá, EnumAction.Âµá€ };
    }
    
    private EnumAction(final String s, final int n, final String p_i1910_1_, final int p_i1910_2_) {
    }
}
