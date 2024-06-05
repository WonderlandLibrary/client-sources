package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumType implements IStringSerializable
{
    NORMAL("NORMAL", 0, 0, "cobblestone", "normal"), 
    MOSSY("MOSSY", 1, 1, "mossy_cobblestone", "mossy");
    
    private static final EnumType[] field_176666_c;
    private static final EnumType[] $VALUES;
    private final int field_176663_d;
    private final String field_176664_e;
    private String field_176661_f;
    
    private EnumType(final String p_i45673_1_, final int p_i45673_2_, final int p_i45673_3_, final String p_i45673_4_, final String p_i45673_5_) {
        this.field_176663_d = p_i45673_3_;
        this.field_176664_e = p_i45673_4_;
        this.field_176661_f = p_i45673_5_;
    }
    
    public static EnumType func_176660_a(int p_176660_0_) {
        if (p_176660_0_ < 0 || p_176660_0_ >= EnumType.field_176666_c.length) {
            p_176660_0_ = 0;
        }
        return EnumType.field_176666_c[p_176660_0_];
    }
    
    public int func_176657_a() {
        return this.field_176663_d;
    }
    
    @Override
    public String toString() {
        return this.field_176664_e;
    }
    
    @Override
    public String getName() {
        return this.field_176664_e;
    }
    
    public String func_176659_c() {
        return this.field_176661_f;
    }
    
    static {
        field_176666_c = new EnumType[values().length];
        $VALUES = new EnumType[] { EnumType.NORMAL, EnumType.MOSSY };
        for (final EnumType var4 : values()) {
            EnumType.field_176666_c[var4.func_176657_a()] = var4;
        }
    }
}
