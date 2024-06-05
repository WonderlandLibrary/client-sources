package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumType implements IStringSerializable
{
    DEFAULT("DEFAULT", 0, 0, "default", "default"), 
    CHISELED("CHISELED", 1, 1, "chiseled", "chiseled"), 
    LINES_Y("LINES_Y", 2, 2, "lines_y", "lines"), 
    LINES_X("LINES_X", 3, 3, "lines_x", "lines"), 
    LINES_Z("LINES_Z", 4, 4, "lines_z", "lines");
    
    private static final EnumType[] TYPES_ARRAY;
    private static final EnumType[] $VALUES;
    private final int field_176798_g;
    private final String field_176805_h;
    private final String field_176806_i;
    
    private EnumType(final String p_i45691_1_, final int p_i45691_2_, final int p_i45691_3_, final String p_i45691_4_, final String p_i45691_5_) {
        this.field_176798_g = p_i45691_3_;
        this.field_176805_h = p_i45691_4_;
        this.field_176806_i = p_i45691_5_;
    }
    
    public static EnumType func_176794_a(int p_176794_0_) {
        if (p_176794_0_ < 0 || p_176794_0_ >= EnumType.TYPES_ARRAY.length) {
            p_176794_0_ = 0;
        }
        return EnumType.TYPES_ARRAY[p_176794_0_];
    }
    
    public int getMetaFromState() {
        return this.field_176798_g;
    }
    
    @Override
    public String toString() {
        return this.field_176806_i;
    }
    
    @Override
    public String getName() {
        return this.field_176805_h;
    }
    
    static {
        TYPES_ARRAY = new EnumType[values().length];
        $VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.CHISELED, EnumType.LINES_Y, EnumType.LINES_X, EnumType.LINES_Z };
        for (final EnumType var4 : values()) {
            EnumType.TYPES_ARRAY[var4.getMetaFromState()] = var4;
        }
    }
}
