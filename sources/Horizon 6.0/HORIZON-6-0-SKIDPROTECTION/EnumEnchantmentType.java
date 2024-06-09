package HORIZON-6-0-SKIDPROTECTION;

public enum EnumEnchantmentType
{
    HorizonCode_Horizon_È("ALL", 0, "ALL", 0), 
    Â("ARMOR", 1, "ARMOR", 1), 
    Ý("ARMOR_FEET", 2, "ARMOR_FEET", 2), 
    Ø­áŒŠá("ARMOR_LEGS", 3, "ARMOR_LEGS", 3), 
    Âµá€("ARMOR_TORSO", 4, "ARMOR_TORSO", 4), 
    Ó("ARMOR_HEAD", 5, "ARMOR_HEAD", 5), 
    à("WEAPON", 6, "WEAPON", 6), 
    Ø("DIGGER", 7, "DIGGER", 7), 
    áŒŠÆ("FISHING_ROD", 8, "FISHING_ROD", 8), 
    áˆºÑ¢Õ("BREAKABLE", 9, "BREAKABLE", 9), 
    ÂµÈ("BOW", 10, "BOW", 10);
    
    private static final EnumEnchantmentType[] á;
    private static final String ˆÏ­ = "CL_00000106";
    
    static {
        £á = new EnumEnchantmentType[] { EnumEnchantmentType.HorizonCode_Horizon_È, EnumEnchantmentType.Â, EnumEnchantmentType.Ý, EnumEnchantmentType.Ø­áŒŠá, EnumEnchantmentType.Âµá€, EnumEnchantmentType.Ó, EnumEnchantmentType.à, EnumEnchantmentType.Ø, EnumEnchantmentType.áŒŠÆ, EnumEnchantmentType.áˆºÑ¢Õ, EnumEnchantmentType.ÂµÈ };
        á = new EnumEnchantmentType[] { EnumEnchantmentType.HorizonCode_Horizon_È, EnumEnchantmentType.Â, EnumEnchantmentType.Ý, EnumEnchantmentType.Ø­áŒŠá, EnumEnchantmentType.Âµá€, EnumEnchantmentType.Ó, EnumEnchantmentType.à, EnumEnchantmentType.Ø, EnumEnchantmentType.áŒŠÆ, EnumEnchantmentType.áˆºÑ¢Õ, EnumEnchantmentType.ÂµÈ };
    }
    
    private EnumEnchantmentType(final String s, final int n, final String p_i1927_1_, final int p_i1927_2_) {
    }
    
    public boolean HorizonCode_Horizon_È(final Item_1028566121 p_77557_1_) {
        if (this == EnumEnchantmentType.HorizonCode_Horizon_È) {
            return true;
        }
        if (this == EnumEnchantmentType.áˆºÑ¢Õ && p_77557_1_.Ø­áŒŠá()) {
            return true;
        }
        if (!(p_77557_1_ instanceof ItemArmor)) {
            return (p_77557_1_ instanceof ItemSword) ? (this == EnumEnchantmentType.à) : ((p_77557_1_ instanceof ItemTool) ? (this == EnumEnchantmentType.Ø) : ((p_77557_1_ instanceof ItemBow) ? (this == EnumEnchantmentType.ÂµÈ) : (p_77557_1_ instanceof ItemFishingRod && this == EnumEnchantmentType.áŒŠÆ)));
        }
        if (this == EnumEnchantmentType.Â) {
            return true;
        }
        final ItemArmor var2 = (ItemArmor)p_77557_1_;
        return (var2.Ø == 0) ? (this == EnumEnchantmentType.Ó) : ((var2.Ø == 2) ? (this == EnumEnchantmentType.Ø­áŒŠá) : ((var2.Ø == 1) ? (this == EnumEnchantmentType.Âµá€) : (var2.Ø == 3 && this == EnumEnchantmentType.Ý)));
    }
}
