package net.minecraft.world.biome;

public enum TempCategory
{
    OCEAN("OCEAN", 0), 
    COLD("COLD", 1), 
    MEDIUM("MEDIUM", 2), 
    WARM("WARM", 3);
    
    private static final TempCategory[] $VALUES;
    
    private TempCategory(final String p_i45372_1_, final int p_i45372_2_) {
    }
    
    static {
        $VALUES = new TempCategory[] { TempCategory.OCEAN, TempCategory.COLD, TempCategory.MEDIUM, TempCategory.WARM };
    }
}
