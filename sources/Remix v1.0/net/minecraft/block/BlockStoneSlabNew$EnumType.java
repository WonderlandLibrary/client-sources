package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumType implements IStringSerializable
{
    RED_SANDSTONE("RED_SANDSTONE", 0, 0, "red_sandstone");
    
    private static final EnumType[] field_176921_b;
    private static final EnumType[] $VALUES;
    private final int field_176922_c;
    private final String field_176919_d;
    
    private EnumType(final String p_i45697_1_, final int p_i45697_2_, final int p_i45697_3_, final String p_i45697_4_) {
        this.field_176922_c = p_i45697_3_;
        this.field_176919_d = p_i45697_4_;
    }
    
    public static EnumType func_176916_a(int p_176916_0_) {
        if (p_176916_0_ < 0 || p_176916_0_ >= EnumType.field_176921_b.length) {
            p_176916_0_ = 0;
        }
        return EnumType.field_176921_b[p_176916_0_];
    }
    
    public int func_176915_a() {
        return this.field_176922_c;
    }
    
    @Override
    public String toString() {
        return this.field_176919_d;
    }
    
    @Override
    public String getName() {
        return this.field_176919_d;
    }
    
    public String func_176918_c() {
        return this.field_176919_d;
    }
    
    static {
        field_176921_b = new EnumType[values().length];
        $VALUES = new EnumType[] { EnumType.RED_SANDSTONE };
        for (final EnumType var4 : values()) {
            EnumType.field_176921_b[var4.func_176915_a()] = var4;
        }
    }
}
