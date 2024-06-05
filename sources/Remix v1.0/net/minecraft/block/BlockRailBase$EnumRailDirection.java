package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumRailDirection implements IStringSerializable
{
    NORTH_SOUTH("NORTH_SOUTH", 0, 0, "north_south"), 
    EAST_WEST("EAST_WEST", 1, 1, "east_west"), 
    ASCENDING_EAST("ASCENDING_EAST", 2, 2, "ascending_east"), 
    ASCENDING_WEST("ASCENDING_WEST", 3, 3, "ascending_west"), 
    ASCENDING_NORTH("ASCENDING_NORTH", 4, 4, "ascending_north"), 
    ASCENDING_SOUTH("ASCENDING_SOUTH", 5, 5, "ascending_south"), 
    SOUTH_EAST("SOUTH_EAST", 6, 6, "south_east"), 
    SOUTH_WEST("SOUTH_WEST", 7, 7, "south_west"), 
    NORTH_WEST("NORTH_WEST", 8, 8, "north_west"), 
    NORTH_EAST("NORTH_EAST", 9, 9, "north_east");
    
    private static final EnumRailDirection[] field_177030_k;
    private static final EnumRailDirection[] $VALUES;
    private final int field_177027_l;
    private final String field_177028_m;
    
    private EnumRailDirection(final String p_i45738_1_, final int p_i45738_2_, final int p_i45738_3_, final String p_i45738_4_) {
        this.field_177027_l = p_i45738_3_;
        this.field_177028_m = p_i45738_4_;
    }
    
    public static EnumRailDirection func_177016_a(int p_177016_0_) {
        if (p_177016_0_ < 0 || p_177016_0_ >= EnumRailDirection.field_177030_k.length) {
            p_177016_0_ = 0;
        }
        return EnumRailDirection.field_177030_k[p_177016_0_];
    }
    
    public int func_177015_a() {
        return this.field_177027_l;
    }
    
    @Override
    public String toString() {
        return this.field_177028_m;
    }
    
    public boolean func_177018_c() {
        return this == EnumRailDirection.ASCENDING_NORTH || this == EnumRailDirection.ASCENDING_EAST || this == EnumRailDirection.ASCENDING_SOUTH || this == EnumRailDirection.ASCENDING_WEST;
    }
    
    @Override
    public String getName() {
        return this.field_177028_m;
    }
    
    static {
        field_177030_k = new EnumRailDirection[values().length];
        $VALUES = new EnumRailDirection[] { EnumRailDirection.NORTH_SOUTH, EnumRailDirection.EAST_WEST, EnumRailDirection.ASCENDING_EAST, EnumRailDirection.ASCENDING_WEST, EnumRailDirection.ASCENDING_NORTH, EnumRailDirection.ASCENDING_SOUTH, EnumRailDirection.SOUTH_EAST, EnumRailDirection.SOUTH_WEST, EnumRailDirection.NORTH_WEST, EnumRailDirection.NORTH_EAST };
        for (final EnumRailDirection var4 : values()) {
            EnumRailDirection.field_177030_k[var4.func_177015_a()] = var4;
        }
    }
}
