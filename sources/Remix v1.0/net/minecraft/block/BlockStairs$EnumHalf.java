package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumHalf implements IStringSerializable
{
    TOP("TOP", 0, "top"), 
    BOTTOM("BOTTOM", 1, "bottom");
    
    private static final EnumHalf[] $VALUES;
    private final String field_176709_c;
    
    private EnumHalf(final String p_i45683_1_, final int p_i45683_2_, final String p_i45683_3_) {
        this.field_176709_c = p_i45683_3_;
    }
    
    @Override
    public String toString() {
        return this.field_176709_c;
    }
    
    @Override
    public String getName() {
        return this.field_176709_c;
    }
    
    static {
        $VALUES = new EnumHalf[] { EnumHalf.TOP, EnumHalf.BOTTOM };
    }
}
