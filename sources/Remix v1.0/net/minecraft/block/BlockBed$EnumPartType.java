package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumPartType implements IStringSerializable
{
    HEAD("HEAD", 0, "head"), 
    FOOT("FOOT", 1, "foot");
    
    private static final EnumPartType[] $VALUES;
    private final String field_177036_c;
    
    private EnumPartType(final String p_i45735_1_, final int p_i45735_2_, final String p_i45735_3_) {
        this.field_177036_c = p_i45735_3_;
    }
    
    @Override
    public String toString() {
        return this.field_177036_c;
    }
    
    @Override
    public String getName() {
        return this.field_177036_c;
    }
    
    static {
        $VALUES = new EnumPartType[] { EnumPartType.HEAD, EnumPartType.FOOT };
    }
}
