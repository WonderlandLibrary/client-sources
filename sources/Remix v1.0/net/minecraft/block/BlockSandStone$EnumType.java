package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumType implements IStringSerializable
{
    DEFAULT("DEFAULT", 0, 0, "sandstone", "default"), 
    CHISELED("CHISELED", 1, 1, "chiseled_sandstone", "chiseled"), 
    SMOOTH("SMOOTH", 2, 2, "smooth_sandstone", "smooth");
    
    private static final EnumType[] field_176679_d;
    private static final EnumType[] $VALUES;
    private final int field_176680_e;
    private final String field_176677_f;
    private final String field_176678_g;
    
    private EnumType(final String p_i45686_1_, final int p_i45686_2_, final int p_i45686_3_, final String p_i45686_4_, final String p_i45686_5_) {
        this.field_176680_e = p_i45686_3_;
        this.field_176677_f = p_i45686_4_;
        this.field_176678_g = p_i45686_5_;
    }
    
    public static EnumType func_176673_a(int p_176673_0_) {
        if (p_176673_0_ < 0 || p_176673_0_ >= EnumType.field_176679_d.length) {
            p_176673_0_ = 0;
        }
        return EnumType.field_176679_d[p_176673_0_];
    }
    
    public int func_176675_a() {
        return this.field_176680_e;
    }
    
    @Override
    public String toString() {
        return this.field_176677_f;
    }
    
    @Override
    public String getName() {
        return this.field_176677_f;
    }
    
    public String func_176676_c() {
        return this.field_176678_g;
    }
    
    static {
        field_176679_d = new EnumType[values().length];
        $VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.CHISELED, EnumType.SMOOTH };
        for (final EnumType var4 : values()) {
            EnumType.field_176679_d[var4.func_176675_a()] = var4;
        }
    }
}
