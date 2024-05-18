package net.minecraft.src;

public class GuiPlayerInfo
{
    public final String name;
    private final String nameinLowerCase;
    public int responseTime;
    
    public GuiPlayerInfo(final String par1Str) {
        this.name = par1Str;
        this.nameinLowerCase = par1Str.toLowerCase();
    }
}
