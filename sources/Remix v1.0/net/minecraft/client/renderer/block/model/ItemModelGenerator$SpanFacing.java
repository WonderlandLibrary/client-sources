package net.minecraft.client.renderer.block.model;

import net.minecraft.util.*;

enum SpanFacing
{
    UP("UP", 0, EnumFacing.UP, 0, -1), 
    DOWN("DOWN", 1, EnumFacing.DOWN, 0, 1), 
    LEFT("LEFT", 2, EnumFacing.EAST, -1, 0), 
    RIGHT("RIGHT", 3, EnumFacing.WEST, 1, 0);
    
    private static final SpanFacing[] $VALUES;
    private final EnumFacing field_178376_e;
    private final int field_178373_f;
    private final int field_178374_g;
    
    private SpanFacing(final String p_i46215_1_, final int p_i46215_2_, final EnumFacing p_i46215_3_, final int p_i46215_4_, final int p_i46215_5_) {
        this.field_178376_e = p_i46215_3_;
        this.field_178373_f = p_i46215_4_;
        this.field_178374_g = p_i46215_5_;
    }
    
    public EnumFacing func_178367_a() {
        return this.field_178376_e;
    }
    
    public int func_178372_b() {
        return this.field_178373_f;
    }
    
    public int func_178371_c() {
        return this.field_178374_g;
    }
    
    private boolean func_178369_d() {
        return this == SpanFacing.DOWN || this == SpanFacing.UP;
    }
    
    static {
        $VALUES = new SpanFacing[] { SpanFacing.UP, SpanFacing.DOWN, SpanFacing.LEFT, SpanFacing.RIGHT };
    }
}
