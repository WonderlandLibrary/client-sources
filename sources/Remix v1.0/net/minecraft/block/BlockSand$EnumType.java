package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;

public enum EnumType implements IStringSerializable
{
    SAND("SAND", 0, 0, "sand", "default", MapColor.sandColor), 
    RED_SAND("RED_SAND", 1, 1, "red_sand", "red", MapColor.dirtColor);
    
    private static final EnumType[] field_176695_c;
    private static final EnumType[] $VALUES;
    private final int field_176692_d;
    private final String field_176693_e;
    private final MapColor field_176690_f;
    private final String field_176691_g;
    
    private EnumType(final String p_i45687_1_, final int p_i45687_2_, final int p_i45687_3_, final String p_i45687_4_, final String p_i45687_5_, final MapColor p_i45687_6_) {
        this.field_176692_d = p_i45687_3_;
        this.field_176693_e = p_i45687_4_;
        this.field_176690_f = p_i45687_6_;
        this.field_176691_g = p_i45687_5_;
    }
    
    public static EnumType func_176686_a(int p_176686_0_) {
        if (p_176686_0_ < 0 || p_176686_0_ >= EnumType.field_176695_c.length) {
            p_176686_0_ = 0;
        }
        return EnumType.field_176695_c[p_176686_0_];
    }
    
    public int func_176688_a() {
        return this.field_176692_d;
    }
    
    @Override
    public String toString() {
        return this.field_176693_e;
    }
    
    public MapColor func_176687_c() {
        return this.field_176690_f;
    }
    
    @Override
    public String getName() {
        return this.field_176693_e;
    }
    
    public String func_176685_d() {
        return this.field_176691_g;
    }
    
    static {
        field_176695_c = new EnumType[values().length];
        $VALUES = new EnumType[] { EnumType.SAND, EnumType.RED_SAND };
        for (final EnumType var4 : values()) {
            EnumType.field_176695_c[var4.func_176688_a()] = var4;
        }
    }
}
