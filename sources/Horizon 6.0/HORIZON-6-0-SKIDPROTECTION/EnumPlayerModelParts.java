package HORIZON-6-0-SKIDPROTECTION;

public enum EnumPlayerModelParts
{
    HorizonCode_Horizon_È("CAPE", 0, "CAPE", 0, 0, "cape"), 
    Â("JACKET", 1, "JACKET", 1, 1, "jacket"), 
    Ý("LEFT_SLEEVE", 2, "LEFT_SLEEVE", 2, 2, "left_sleeve"), 
    Ø­áŒŠá("RIGHT_SLEEVE", 3, "RIGHT_SLEEVE", 3, 3, "right_sleeve"), 
    Âµá€("LEFT_PANTS_LEG", 4, "LEFT_PANTS_LEG", 4, 4, "left_pants_leg"), 
    Ó("RIGHT_PANTS_LEG", 5, "RIGHT_PANTS_LEG", 5, 5, "right_pants_leg"), 
    à("HAT", 6, "HAT", 6, 6, "hat");
    
    private final int Ø;
    private final int áŒŠÆ;
    private final String áˆºÑ¢Õ;
    private final IChatComponent ÂµÈ;
    private static final EnumPlayerModelParts[] á;
    private static final String ˆÏ­ = "CL_00002187";
    
    static {
        £á = new EnumPlayerModelParts[] { EnumPlayerModelParts.HorizonCode_Horizon_È, EnumPlayerModelParts.Â, EnumPlayerModelParts.Ý, EnumPlayerModelParts.Ø­áŒŠá, EnumPlayerModelParts.Âµá€, EnumPlayerModelParts.Ó, EnumPlayerModelParts.à };
        á = new EnumPlayerModelParts[] { EnumPlayerModelParts.HorizonCode_Horizon_È, EnumPlayerModelParts.Â, EnumPlayerModelParts.Ý, EnumPlayerModelParts.Ø­áŒŠá, EnumPlayerModelParts.Âµá€, EnumPlayerModelParts.Ó, EnumPlayerModelParts.à };
    }
    
    private EnumPlayerModelParts(final String s, final int n, final String p_i45809_1_, final int p_i45809_2_, final int p_i45809_3_, final String p_i45809_4_) {
        this.Ø = p_i45809_3_;
        this.áŒŠÆ = 1 << p_i45809_3_;
        this.áˆºÑ¢Õ = p_i45809_4_;
        this.ÂµÈ = new ChatComponentTranslation("options.modelPart." + p_i45809_4_, new Object[0]);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.áŒŠÆ;
    }
    
    public int Â() {
        return this.Ø;
    }
    
    public String Ý() {
        return this.áˆºÑ¢Õ;
    }
    
    public IChatComponent Ø­áŒŠá() {
        return this.ÂµÈ;
    }
}
