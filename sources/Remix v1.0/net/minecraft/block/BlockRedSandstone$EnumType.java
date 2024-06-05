package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumType implements IStringSerializable
{
    DEFAULT("DEFAULT", 0, 0, "red_sandstone", "default"), 
    CHISELED("CHISELED", 1, 1, "chiseled_red_sandstone", "chiseled"), 
    SMOOTH("SMOOTH", 2, 2, "smooth_red_sandstone", "smooth");
    
    private static final EnumType[] field_176831_d;
    private static final EnumType[] $VALUES;
    private final int field_176832_e;
    private final String field_176829_f;
    private final String field_176830_g;
    
    private EnumType(final String p_i45690_1_, final int p_i45690_2_, final int p_i45690_3_, final String p_i45690_4_, final String p_i45690_5_) {
        this.field_176832_e = p_i45690_3_;
        this.field_176829_f = p_i45690_4_;
        this.field_176830_g = p_i45690_5_;
    }
    
    public static EnumType func_176825_a(int p_176825_0_) {
        if (p_176825_0_ < 0 || p_176825_0_ >= EnumType.field_176831_d.length) {
            p_176825_0_ = 0;
        }
        return EnumType.field_176831_d[p_176825_0_];
    }
    
    public int getMetaFromState() {
        return this.field_176832_e;
    }
    
    @Override
    public String toString() {
        return this.field_176829_f;
    }
    
    @Override
    public String getName() {
        return this.field_176829_f;
    }
    
    public String func_176828_c() {
        return this.field_176830_g;
    }
    
    static {
        field_176831_d = new EnumType[values().length];
        $VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.CHISELED, EnumType.SMOOTH };
        for (final EnumType var4 : values()) {
            EnumType.field_176831_d[var4.getMetaFromState()] = var4;
        }
    }
}
