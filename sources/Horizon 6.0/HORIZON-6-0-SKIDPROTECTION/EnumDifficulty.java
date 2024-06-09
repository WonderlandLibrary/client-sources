package HORIZON-6-0-SKIDPROTECTION;

public enum EnumDifficulty
{
    HorizonCode_Horizon_È("PEACEFUL", 0, "PEACEFUL", 0, 0, "options.difficulty.peaceful"), 
    Â("EASY", 1, "EASY", 1, 1, "options.difficulty.easy"), 
    Ý("NORMAL", 2, "NORMAL", 2, 2, "options.difficulty.normal"), 
    Ø­áŒŠá("HARD", 3, "HARD", 3, 3, "options.difficulty.hard");
    
    private static final EnumDifficulty[] Âµá€;
    private final int Ó;
    private final String à;
    private static final EnumDifficulty[] Ø;
    private static final String áŒŠÆ = "CL_00001510";
    
    static {
        áˆºÑ¢Õ = new EnumDifficulty[] { EnumDifficulty.HorizonCode_Horizon_È, EnumDifficulty.Â, EnumDifficulty.Ý, EnumDifficulty.Ø­áŒŠá };
        Âµá€ = new EnumDifficulty[values().length];
        Ø = new EnumDifficulty[] { EnumDifficulty.HorizonCode_Horizon_È, EnumDifficulty.Â, EnumDifficulty.Ý, EnumDifficulty.Ø­áŒŠá };
        for (final EnumDifficulty var4 : values()) {
            EnumDifficulty.Âµá€[var4.Ó] = var4;
        }
    }
    
    private EnumDifficulty(final String s, final int n, final String p_i45312_1_, final int p_i45312_2_, final int p_i45312_3_, final String p_i45312_4_) {
        this.Ó = p_i45312_3_;
        this.à = p_i45312_4_;
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ó;
    }
    
    public static EnumDifficulty HorizonCode_Horizon_È(final int p_151523_0_) {
        return EnumDifficulty.Âµá€[p_151523_0_ % EnumDifficulty.Âµá€.length];
    }
    
    public String Â() {
        return this.à;
    }
}
