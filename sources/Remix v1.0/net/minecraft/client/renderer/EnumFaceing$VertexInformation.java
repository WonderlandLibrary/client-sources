package net.minecraft.client.renderer;

public static class VertexInformation
{
    public final int field_179184_a;
    public final int field_179182_b;
    public final int field_179183_c;
    
    private VertexInformation(final int p_i46270_1_, final int p_i46270_2_, final int p_i46270_3_) {
        this.field_179184_a = p_i46270_1_;
        this.field_179182_b = p_i46270_2_;
        this.field_179183_c = p_i46270_3_;
    }
    
    VertexInformation(final int p_i46271_1_, final int p_i46271_2_, final int p_i46271_3_, final Object p_i46271_4_) {
        this(p_i46271_1_, p_i46271_2_, p_i46271_3_);
    }
}
