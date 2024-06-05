package net.minecraft.client.renderer;

import net.minecraft.util.*;

enum VertexTranslations
{
    DOWN("DOWN", 0, "DOWN", 0, 0, 1, 2, 3), 
    UP("UP", 1, "UP", 1, 2, 3, 0, 1), 
    NORTH("NORTH", 2, "NORTH", 2, 3, 0, 1, 2), 
    SOUTH("SOUTH", 3, "SOUTH", 3, 0, 1, 2, 3), 
    WEST("WEST", 4, "WEST", 4, 3, 0, 1, 2), 
    EAST("EAST", 5, "EAST", 5, 1, 2, 3, 0);
    
    private static final VertexTranslations[] field_178199_k;
    private static final VertexTranslations[] $VALUES;
    private final int field_178191_g;
    private final int field_178200_h;
    private final int field_178201_i;
    private final int field_178198_j;
    
    private VertexTranslations(final String p_i46382_1_, final int p_i46382_2_, final String p_i46234_1_, final int p_i46234_2_, final int p_i46234_3_, final int p_i46234_4_, final int p_i46234_5_, final int p_i46234_6_) {
        this.field_178191_g = p_i46234_3_;
        this.field_178200_h = p_i46234_4_;
        this.field_178201_i = p_i46234_5_;
        this.field_178198_j = p_i46234_6_;
    }
    
    public static VertexTranslations func_178184_a(final EnumFacing p_178184_0_) {
        return VertexTranslations.field_178199_k[p_178184_0_.getIndex()];
    }
    
    static {
        field_178199_k = new VertexTranslations[6];
        $VALUES = new VertexTranslations[] { VertexTranslations.DOWN, VertexTranslations.UP, VertexTranslations.NORTH, VertexTranslations.SOUTH, VertexTranslations.WEST, VertexTranslations.EAST };
        VertexTranslations.field_178199_k[EnumFacing.DOWN.getIndex()] = VertexTranslations.DOWN;
        VertexTranslations.field_178199_k[EnumFacing.UP.getIndex()] = VertexTranslations.UP;
        VertexTranslations.field_178199_k[EnumFacing.NORTH.getIndex()] = VertexTranslations.NORTH;
        VertexTranslations.field_178199_k[EnumFacing.SOUTH.getIndex()] = VertexTranslations.SOUTH;
        VertexTranslations.field_178199_k[EnumFacing.WEST.getIndex()] = VertexTranslations.WEST;
        VertexTranslations.field_178199_k[EnumFacing.EAST.getIndex()] = VertexTranslations.EAST;
    }
}
