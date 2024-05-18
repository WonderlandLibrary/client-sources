package HORIZON-6-0-SKIDPROTECTION;

public enum EnumDyeColor implements IStringSerializable
{
    HorizonCode_Horizon_È("WHITE", 0, "WHITE", 0, 0, 15, "white", "white", MapColor.áˆºÑ¢Õ, EnumChatFormatting.£à), 
    Â("ORANGE", 1, "ORANGE", 1, 1, 14, "orange", "orange", MapColor.µà, EnumChatFormatting.à), 
    Ý("MAGENTA", 2, "MAGENTA", 2, 2, 13, "magenta", "magenta", MapColor.ˆà, EnumChatFormatting.á), 
    Ø­áŒŠá("LIGHT_BLUE", 3, "LIGHT_BLUE", 3, 3, 12, "light_blue", "lightBlue", MapColor.¥Æ, EnumChatFormatting.áˆºÑ¢Õ), 
    Âµá€("YELLOW", 4, "YELLOW", 4, 4, 11, "yellow", "yellow", MapColor.Ø­à, EnumChatFormatting.Å), 
    Ó("LIME", 5, "LIME", 5, 5, 10, "lime", "lime", MapColor.µÕ, EnumChatFormatting.ÂµÈ), 
    à("PINK", 6, "PINK", 6, 6, 9, "pink", "pink", MapColor.Æ, EnumChatFormatting.£á), 
    Ø("GRAY", 7, "GRAY", 7, 7, 8, "gray", "gray", MapColor.Šáƒ, EnumChatFormatting.áŒŠÆ), 
    áŒŠÆ("SILVER", 8, "SILVER", 8, 8, 7, "silver", "silver", MapColor.Ï­Ðƒà, EnumChatFormatting.Ø), 
    áˆºÑ¢Õ("CYAN", 9, "CYAN", 9, 9, 6, "cyan", "cyan", MapColor.áŒŠà, EnumChatFormatting.Ø­áŒŠá), 
    ÂµÈ("PURPLE", 10, "PURPLE", 10, 10, 5, "purple", "purple", MapColor.ŠÄ, EnumChatFormatting.Ó), 
    á("BLUE", 11, "BLUE", 11, 11, 4, "blue", "blue", MapColor.Ñ¢á, EnumChatFormatting.Â), 
    ˆÏ­("BROWN", 12, "BROWN", 12, 12, 3, "brown", "brown", MapColor.ŒÏ, EnumChatFormatting.à), 
    £á("GREEN", 13, "GREEN", 13, 13, 2, "green", "green", MapColor.Çªà¢, EnumChatFormatting.Ý), 
    Å("RED", 14, "RED", 14, 14, 1, "red", "red", MapColor.Ê, EnumChatFormatting.Âµá€), 
    £à("BLACK", 15, "BLACK", 15, 15, 0, "black", "black", MapColor.ÇŽÉ, EnumChatFormatting.HorizonCode_Horizon_È);
    
    private static final EnumDyeColor[] µà;
    private static final EnumDyeColor[] ˆà;
    private final int ¥Æ;
    private final int Ø­à;
    private final String µÕ;
    private final String Æ;
    private final MapColor Šáƒ;
    private final EnumChatFormatting Ï­Ðƒà;
    private static final EnumDyeColor[] áŒŠà;
    private static final String ŠÄ = "CL_00002180";
    
    static {
        Ñ¢á = new EnumDyeColor[] { EnumDyeColor.HorizonCode_Horizon_È, EnumDyeColor.Â, EnumDyeColor.Ý, EnumDyeColor.Ø­áŒŠá, EnumDyeColor.Âµá€, EnumDyeColor.Ó, EnumDyeColor.à, EnumDyeColor.Ø, EnumDyeColor.áŒŠÆ, EnumDyeColor.áˆºÑ¢Õ, EnumDyeColor.ÂµÈ, EnumDyeColor.á, EnumDyeColor.ˆÏ­, EnumDyeColor.£á, EnumDyeColor.Å, EnumDyeColor.£à };
        µà = new EnumDyeColor[values().length];
        ˆà = new EnumDyeColor[values().length];
        áŒŠà = new EnumDyeColor[] { EnumDyeColor.HorizonCode_Horizon_È, EnumDyeColor.Â, EnumDyeColor.Ý, EnumDyeColor.Ø­áŒŠá, EnumDyeColor.Âµá€, EnumDyeColor.Ó, EnumDyeColor.à, EnumDyeColor.Ø, EnumDyeColor.áŒŠÆ, EnumDyeColor.áˆºÑ¢Õ, EnumDyeColor.ÂµÈ, EnumDyeColor.á, EnumDyeColor.ˆÏ­, EnumDyeColor.£á, EnumDyeColor.Å, EnumDyeColor.£à };
        for (final EnumDyeColor var4 : values()) {
            EnumDyeColor.µà[var4.Â()] = var4;
            EnumDyeColor.ˆà[var4.Ý()] = var4;
        }
    }
    
    private EnumDyeColor(final String s, final int n, final String p_i45786_1_, final int p_i45786_2_, final int p_i45786_3_, final int p_i45786_4_, final String p_i45786_5_, final String p_i45786_6_, final MapColor p_i45786_7_, final EnumChatFormatting p_i45786_8_) {
        this.¥Æ = p_i45786_3_;
        this.Ø­à = p_i45786_4_;
        this.µÕ = p_i45786_5_;
        this.Æ = p_i45786_6_;
        this.Šáƒ = p_i45786_7_;
        this.Ï­Ðƒà = p_i45786_8_;
    }
    
    public int Â() {
        return this.¥Æ;
    }
    
    public int Ý() {
        return this.Ø­à;
    }
    
    public String Ø­áŒŠá() {
        return this.Æ;
    }
    
    public MapColor Âµá€() {
        return this.Šáƒ;
    }
    
    public static EnumDyeColor HorizonCode_Horizon_È(int p_176766_0_) {
        if (p_176766_0_ < 0 || p_176766_0_ >= EnumDyeColor.ˆà.length) {
            p_176766_0_ = 0;
        }
        return EnumDyeColor.ˆà[p_176766_0_];
    }
    
    public static EnumDyeColor Â(int p_176764_0_) {
        if (p_176764_0_ < 0 || p_176764_0_ >= EnumDyeColor.µà.length) {
            p_176764_0_ = 0;
        }
        return EnumDyeColor.µà[p_176764_0_];
    }
    
    @Override
    public String toString() {
        return this.Æ;
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return this.µÕ;
    }
}
