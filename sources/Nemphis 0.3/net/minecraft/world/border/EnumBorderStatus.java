/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.world.border;

public enum EnumBorderStatus {
    GROWING("GROWING", 0, 4259712),
    SHRINKING("SHRINKING", 1, 16724016),
    STATIONARY("STATIONARY", 2, 2138367);
    
    private final int id;
    private static final EnumBorderStatus[] $VALUES;
    private static final String __OBFID = "CL_00002013";

    static {
        $VALUES = new EnumBorderStatus[]{GROWING, SHRINKING, STATIONARY};
    }

    private EnumBorderStatus(String p_i45647_1_, int p_i45647_2_, String id, int n2, int n3) {
        this.id = id;
    }

    public int func_177766_a() {
        return this.id;
    }
}

