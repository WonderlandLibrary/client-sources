/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.world;

public enum EnumSkyBlock {
    SKY("SKY", 0, 15),
    BLOCK("BLOCK", 1, 0);
    
    public final int defaultLightValue;
    private static final EnumSkyBlock[] $VALUES;
    private static final String __OBFID = "CL_00000151";

    static {
        $VALUES = new EnumSkyBlock[]{SKY, BLOCK};
    }

    private EnumSkyBlock(String p_i1961_1_, int p_i1961_2_, int p_i1961_3_) {
        this.defaultLightValue = p_i1961_3_;
    }
}

