package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumType implements IStringSerializable
{
    DEAD_BUSH("DEAD_BUSH", 0, 0, "dead_bush"), 
    GRASS("GRASS", 1, 1, "tall_grass"), 
    FERN("FERN", 2, 2, "fern");
    
    private static final EnumType[] field_177048_d;
    private static final EnumType[] $VALUES;
    private final int field_177049_e;
    private final String field_177046_f;
    
    private EnumType(final String p_i45676_1_, final int p_i45676_2_, final int p_i45676_3_, final String p_i45676_4_) {
        this.field_177049_e = p_i45676_3_;
        this.field_177046_f = p_i45676_4_;
    }
    
    public static EnumType func_177045_a(int p_177045_0_) {
        if (p_177045_0_ < 0 || p_177045_0_ >= EnumType.field_177048_d.length) {
            p_177045_0_ = 0;
        }
        return EnumType.field_177048_d[p_177045_0_];
    }
    
    public int func_177044_a() {
        return this.field_177049_e;
    }
    
    @Override
    public String toString() {
        return this.field_177046_f;
    }
    
    @Override
    public String getName() {
        return this.field_177046_f;
    }
    
    static {
        field_177048_d = new EnumType[values().length];
        $VALUES = new EnumType[] { EnumType.DEAD_BUSH, EnumType.GRASS, EnumType.FERN };
        for (final EnumType var4 : values()) {
            EnumType.field_177048_d[var4.func_177044_a()] = var4;
        }
    }
}
