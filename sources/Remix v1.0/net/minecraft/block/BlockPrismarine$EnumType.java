package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumType implements IStringSerializable
{
    ROUGH("ROUGH", 0, 0, "prismarine", "rough"), 
    BRICKS("BRICKS", 1, 1, "prismarine_bricks", "bricks"), 
    DARK("DARK", 2, 2, "dark_prismarine", "dark");
    
    private static final EnumType[] field_176813_d;
    private static final EnumType[] $VALUES;
    private final int meta;
    private final String field_176811_f;
    private final String field_176812_g;
    
    private EnumType(final String p_i45692_1_, final int p_i45692_2_, final int p_i45692_3_, final String p_i45692_4_, final String p_i45692_5_) {
        this.meta = p_i45692_3_;
        this.field_176811_f = p_i45692_4_;
        this.field_176812_g = p_i45692_5_;
    }
    
    public static EnumType func_176810_a(int p_176810_0_) {
        if (p_176810_0_ < 0 || p_176810_0_ >= EnumType.field_176813_d.length) {
            p_176810_0_ = 0;
        }
        return EnumType.field_176813_d[p_176810_0_];
    }
    
    public int getMetadata() {
        return this.meta;
    }
    
    @Override
    public String toString() {
        return this.field_176811_f;
    }
    
    @Override
    public String getName() {
        return this.field_176811_f;
    }
    
    public String func_176809_c() {
        return this.field_176812_g;
    }
    
    static {
        field_176813_d = new EnumType[values().length];
        $VALUES = new EnumType[] { EnumType.ROUGH, EnumType.BRICKS, EnumType.DARK };
        for (final EnumType var4 : values()) {
            EnumType.field_176813_d[var4.getMetadata()] = var4;
        }
    }
}
