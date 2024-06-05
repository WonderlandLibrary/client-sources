package net.minecraft.block;

public enum Sensitivity
{
    EVERYTHING("EVERYTHING", 0), 
    MOBS("MOBS", 1);
    
    private static final Sensitivity[] $VALUES;
    
    private Sensitivity(final String p_i45417_1_, final int p_i45417_2_) {
    }
    
    static {
        $VALUES = new Sensitivity[] { Sensitivity.EVERYTHING, Sensitivity.MOBS };
    }
}
