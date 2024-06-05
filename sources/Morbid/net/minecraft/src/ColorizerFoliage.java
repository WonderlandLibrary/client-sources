package net.minecraft.src;

public class ColorizerFoliage
{
    private static int[] foliageBuffer;
    
    static {
        ColorizerFoliage.foliageBuffer = new int[65536];
    }
    
    public static void setFoliageBiomeColorizer(final int[] par0ArrayOfInteger) {
        ColorizerFoliage.foliageBuffer = par0ArrayOfInteger;
    }
    
    public static int getFoliageColor(final double par0, double par2) {
        par2 *= par0;
        final int var4 = (int)((1.0 - par0) * 255.0);
        final int var5 = (int)((1.0 - par2) * 255.0);
        return ColorizerFoliage.foliageBuffer[var5 << 8 | var4];
    }
    
    public static int getFoliageColorPine() {
        return 6396257;
    }
    
    public static int getFoliageColorBirch() {
        return 8431445;
    }
    
    public static int getFoliageColorBasic() {
        return 4764952;
    }
}
