package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumPlantType implements IStringSerializable
{
    SUNFLOWER("SUNFLOWER", 0, 0, "sunflower"), 
    SYRINGA("SYRINGA", 1, 1, "syringa"), 
    GRASS("GRASS", 2, 2, "double_grass", "grass"), 
    FERN("FERN", 3, 3, "double_fern", "fern"), 
    ROSE("ROSE", 4, 4, "double_rose", "rose"), 
    PAEONIA("PAEONIA", 5, 5, "paeonia");
    
    private static final EnumPlantType[] field_176941_g;
    private static final EnumPlantType[] $VALUES;
    private final int field_176949_h;
    private final String field_176950_i;
    private final String field_176947_j;
    
    private EnumPlantType(final String p_i45722_1_, final int p_i45722_2_, final int p_i45722_3_, final String p_i45722_4_) {
        this(p_i45722_1_, p_i45722_2_, p_i45722_3_, p_i45722_4_, p_i45722_4_);
    }
    
    private EnumPlantType(final String p_i45723_1_, final int p_i45723_2_, final int p_i45723_3_, final String p_i45723_4_, final String p_i45723_5_) {
        this.field_176949_h = p_i45723_3_;
        this.field_176950_i = p_i45723_4_;
        this.field_176947_j = p_i45723_5_;
    }
    
    public static EnumPlantType func_176938_a(int p_176938_0_) {
        if (p_176938_0_ < 0 || p_176938_0_ >= EnumPlantType.field_176941_g.length) {
            p_176938_0_ = 0;
        }
        return EnumPlantType.field_176941_g[p_176938_0_];
    }
    
    public int func_176936_a() {
        return this.field_176949_h;
    }
    
    @Override
    public String toString() {
        return this.field_176950_i;
    }
    
    @Override
    public String getName() {
        return this.field_176950_i;
    }
    
    public String func_176939_c() {
        return this.field_176947_j;
    }
    
    static {
        field_176941_g = new EnumPlantType[values().length];
        $VALUES = new EnumPlantType[] { EnumPlantType.SUNFLOWER, EnumPlantType.SYRINGA, EnumPlantType.GRASS, EnumPlantType.FERN, EnumPlantType.ROSE, EnumPlantType.PAEONIA };
        for (final EnumPlantType var4 : values()) {
            EnumPlantType.field_176941_g[var4.func_176936_a()] = var4;
        }
    }
}
