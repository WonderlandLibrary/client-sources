package net.minecraft.src;

public enum EnumSkyBlock
{
    Sky("Sky", 0, 15), 
    Block("Block", 1, 0);
    
    public final int defaultLightValue;
    
    private EnumSkyBlock(final String s, final int n, final int par3) {
        this.defaultLightValue = par3;
    }
}
