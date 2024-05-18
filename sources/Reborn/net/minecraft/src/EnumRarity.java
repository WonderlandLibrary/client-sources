package net.minecraft.src;

public enum EnumRarity
{
    common("common", 0, 15, "Common"), 
    uncommon("uncommon", 1, 14, "Uncommon"), 
    rare("rare", 2, 11, "Rare"), 
    epic("epic", 3, 13, "Epic");
    
    public final int rarityColor;
    public final String rarityName;
    
    private EnumRarity(final String s, final int n, final int par3, final String par4Str) {
        this.rarityColor = par3;
        this.rarityName = par4Str;
    }
}
