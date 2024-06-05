package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.texture.*;

public class State
{
    private final int[] field_179019_b;
    private final int field_179020_c;
    private final int field_179017_d;
    private final VertexFormat field_179018_e;
    public TextureAtlasSprite[] stateQuadSprites;
    
    public State(final int[] buf, final int bufIndex, final int vertCount, final VertexFormat vertFormat, final TextureAtlasSprite[] quadSprites) {
        this.field_179019_b = buf;
        this.field_179020_c = bufIndex;
        this.field_179017_d = vertCount;
        this.field_179018_e = vertFormat;
        this.stateQuadSprites = quadSprites;
    }
    
    public State(final int[] p_i46274_2_, final int p_i46274_3_, final int p_i46274_4_, final VertexFormat p_i46274_5_) {
        this.field_179019_b = p_i46274_2_;
        this.field_179020_c = p_i46274_3_;
        this.field_179017_d = p_i46274_4_;
        this.field_179018_e = p_i46274_5_;
    }
    
    public int[] func_179013_a() {
        return this.field_179019_b;
    }
    
    public int getRawBufferIndex() {
        return this.field_179020_c;
    }
    
    public int getVertexCount() {
        return this.field_179017_d;
    }
    
    public VertexFormat func_179016_d() {
        return this.field_179018_e;
    }
}
