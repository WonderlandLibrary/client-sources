package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumType implements IStringSerializable
{
    NORTH_WEST("NORTH_WEST", 0, 1, "north_west"), 
    NORTH("NORTH", 1, 2, "north"), 
    NORTH_EAST("NORTH_EAST", 2, 3, "north_east"), 
    WEST("WEST", 3, 4, "west"), 
    CENTER("CENTER", 4, 5, "center"), 
    EAST("EAST", 5, 6, "east"), 
    SOUTH_WEST("SOUTH_WEST", 6, 7, "south_west"), 
    SOUTH("SOUTH", 7, 8, "south"), 
    SOUTH_EAST("SOUTH_EAST", 8, 9, "south_east"), 
    STEM("STEM", 9, 10, "stem"), 
    ALL_INSIDE("ALL_INSIDE", 10, 0, "all_inside"), 
    ALL_OUTSIDE("ALL_OUTSIDE", 11, 14, "all_outside"), 
    ALL_STEM("ALL_STEM", 12, 15, "all_stem");
    
    private static final EnumType[] field_176905_n;
    private static final EnumType[] $VALUES;
    private final int field_176906_o;
    private final String field_176914_p;
    
    private EnumType(final String p_i45710_1_, final int p_i45710_2_, final int p_i45710_3_, final String p_i45710_4_) {
        this.field_176906_o = p_i45710_3_;
        this.field_176914_p = p_i45710_4_;
    }
    
    public static EnumType func_176895_a(int p_176895_0_) {
        if (p_176895_0_ < 0 || p_176895_0_ >= EnumType.field_176905_n.length) {
            p_176895_0_ = 0;
        }
        final EnumType var1 = EnumType.field_176905_n[p_176895_0_];
        return (var1 == null) ? EnumType.field_176905_n[0] : var1;
    }
    
    public int func_176896_a() {
        return this.field_176906_o;
    }
    
    @Override
    public String toString() {
        return this.field_176914_p;
    }
    
    @Override
    public String getName() {
        return this.field_176914_p;
    }
    
    static {
        field_176905_n = new EnumType[16];
        $VALUES = new EnumType[] { EnumType.NORTH_WEST, EnumType.NORTH, EnumType.NORTH_EAST, EnumType.WEST, EnumType.CENTER, EnumType.EAST, EnumType.SOUTH_WEST, EnumType.SOUTH, EnumType.SOUTH_EAST, EnumType.STEM, EnumType.ALL_INSIDE, EnumType.ALL_OUTSIDE, EnumType.ALL_STEM };
        for (final EnumType var4 : values()) {
            EnumType.field_176905_n[var4.func_176896_a()] = var4;
        }
    }
}
