package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumAxis implements IStringSerializable
{
    X("X", 0, "x"), 
    Y("Y", 1, "y"), 
    Z("Z", 2, "z"), 
    NONE("NONE", 3, "none");
    
    private static final EnumAxis[] $VALUES;
    private final String field_176874_e;
    
    private EnumAxis(final String p_i45708_1_, final int p_i45708_2_, final String p_i45708_3_) {
        this.field_176874_e = p_i45708_3_;
    }
    
    public static EnumAxis func_176870_a(final EnumFacing.Axis p_176870_0_) {
        switch (SwitchAxis.field_180167_a[p_176870_0_.ordinal()]) {
            case 1: {
                return EnumAxis.X;
            }
            case 2: {
                return EnumAxis.Y;
            }
            case 3: {
                return EnumAxis.Z;
            }
            default: {
                return EnumAxis.NONE;
            }
        }
    }
    
    @Override
    public String toString() {
        return this.field_176874_e;
    }
    
    @Override
    public String getName() {
        return this.field_176874_e;
    }
    
    static {
        $VALUES = new EnumAxis[] { EnumAxis.X, EnumAxis.Y, EnumAxis.Z, EnumAxis.NONE };
    }
}
