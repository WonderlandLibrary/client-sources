package net.minecraft.src;

public class ColorizerGrass
{
    private static int[] grassBuffer;
    
    static {
        ColorizerGrass.grassBuffer = new int[65536];
    }
    
    public static void setGrassBiomeColorizer(final int[] par0ArrayOfInteger) {
        ColorizerGrass.grassBuffer = par0ArrayOfInteger;
    }
    
    public static int getGrassColor(final double par0, double par2) {
        par2 *= par0;
        final int var4 = (int)((1.0 - par0) * 255.0);
        final int var5 = (int)((1.0 - par2) * 255.0);
        return ColorizerGrass.grassBuffer[var5 << 8 | var4];
    }
}
