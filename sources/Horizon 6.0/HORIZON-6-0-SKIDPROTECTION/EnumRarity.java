package HORIZON-6-0-SKIDPROTECTION;

public enum EnumRarity
{
    HorizonCode_Horizon_È("COMMON", 0, "COMMON", 0, EnumChatFormatting.£à, "Common"), 
    Â("UNCOMMON", 1, "UNCOMMON", 1, EnumChatFormatting.Å, "Uncommon"), 
    Ý("RARE", 2, "RARE", 2, EnumChatFormatting.á, "Rare"), 
    Ø­áŒŠá("EPIC", 3, "EPIC", 3, EnumChatFormatting.£á, "Epic");
    
    public final EnumChatFormatting Âµá€;
    public final String Ó;
    private static final EnumRarity[] à;
    private static final String Ø = "CL_00000056";
    
    static {
        áŒŠÆ = new EnumRarity[] { EnumRarity.HorizonCode_Horizon_È, EnumRarity.Â, EnumRarity.Ý, EnumRarity.Ø­áŒŠá };
        à = new EnumRarity[] { EnumRarity.HorizonCode_Horizon_È, EnumRarity.Â, EnumRarity.Ý, EnumRarity.Ø­áŒŠá };
    }
    
    private EnumRarity(final String s, final int n, final String p_i45349_1_, final int p_i45349_2_, final EnumChatFormatting p_i45349_3_, final String p_i45349_4_) {
        this.Âµá€ = p_i45349_3_;
        this.Ó = p_i45349_4_;
    }
}
