package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumShape implements IStringSerializable
{
    STRAIGHT("STRAIGHT", 0, "straight"), 
    INNER_LEFT("INNER_LEFT", 1, "inner_left"), 
    INNER_RIGHT("INNER_RIGHT", 2, "inner_right"), 
    OUTER_LEFT("OUTER_LEFT", 3, "outer_left"), 
    OUTER_RIGHT("OUTER_RIGHT", 4, "outer_right");
    
    private static final EnumShape[] $VALUES;
    private final String field_176699_f;
    
    private EnumShape(final String p_i45682_1_, final int p_i45682_2_, final String p_i45682_3_) {
        this.field_176699_f = p_i45682_3_;
    }
    
    @Override
    public String toString() {
        return this.field_176699_f;
    }
    
    @Override
    public String getName() {
        return this.field_176699_f;
    }
    
    static {
        $VALUES = new EnumShape[] { EnumShape.STRAIGHT, EnumShape.INNER_LEFT, EnumShape.INNER_RIGHT, EnumShape.OUTER_LEFT, EnumShape.OUTER_RIGHT };
    }
}
