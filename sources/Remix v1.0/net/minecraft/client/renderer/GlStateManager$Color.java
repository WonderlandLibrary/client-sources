package net.minecraft.client.renderer;

static class Color
{
    public float field_179195_a;
    public float green;
    public float blue;
    public float alpha;
    
    public Color() {
        this.field_179195_a = 1.0f;
        this.green = 1.0f;
        this.blue = 1.0f;
        this.alpha = 1.0f;
    }
    
    public Color(final float p_i46265_1_, final float p_i46265_2_, final float p_i46265_3_, final float p_i46265_4_) {
        this.field_179195_a = 1.0f;
        this.green = 1.0f;
        this.blue = 1.0f;
        this.alpha = 1.0f;
        this.field_179195_a = p_i46265_1_;
        this.green = p_i46265_2_;
        this.blue = p_i46265_3_;
        this.alpha = p_i46265_4_;
    }
}
