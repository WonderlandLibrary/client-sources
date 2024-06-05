package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumType implements IStringSerializable
{
    DEFAULT("DEFAULT", 0, 0, "stonebrick", "default"), 
    MOSSY("MOSSY", 1, 1, "mossy_stonebrick", "mossy"), 
    CRACKED("CRACKED", 2, 2, "cracked_stonebrick", "cracked"), 
    CHISELED("CHISELED", 3, 3, "chiseled_stonebrick", "chiseled");
    
    private static final EnumType[] TYPES_ARRAY;
    private static final EnumType[] $VALUES;
    private final int field_176615_f;
    private final String field_176616_g;
    private final String field_176622_h;
    
    private EnumType(final String p_i45679_1_, final int p_i45679_2_, final int p_i45679_3_, final String p_i45679_4_, final String p_i45679_5_) {
        this.field_176615_f = p_i45679_3_;
        this.field_176616_g = p_i45679_4_;
        this.field_176622_h = p_i45679_5_;
    }
    
    public static EnumType getStateFromMeta(int p_176613_0_) {
        if (p_176613_0_ < 0 || p_176613_0_ >= EnumType.TYPES_ARRAY.length) {
            p_176613_0_ = 0;
        }
        return EnumType.TYPES_ARRAY[p_176613_0_];
    }
    
    public int getMetaFromState() {
        return this.field_176615_f;
    }
    
    @Override
    public String toString() {
        return this.field_176616_g;
    }
    
    @Override
    public String getName() {
        return this.field_176616_g;
    }
    
    public String getVariantName() {
        return this.field_176622_h;
    }
    
    static {
        TYPES_ARRAY = new EnumType[values().length];
        $VALUES = new EnumType[] { EnumType.DEFAULT, EnumType.MOSSY, EnumType.CRACKED, EnumType.CHISELED };
        for (final EnumType var4 : values()) {
            EnumType.TYPES_ARRAY[var4.getMetaFromState()] = var4;
        }
    }
}
