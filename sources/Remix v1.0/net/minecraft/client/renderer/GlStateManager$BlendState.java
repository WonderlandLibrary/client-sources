package net.minecraft.client.renderer;

static class BlendState
{
    public BooleanState field_179213_a;
    public int field_179211_b;
    public int field_179212_c;
    public int field_179209_d;
    public int field_179210_e;
    
    private BlendState() {
        this.field_179213_a = new BooleanState(3042);
        this.field_179211_b = 1;
        this.field_179212_c = 0;
        this.field_179209_d = 1;
        this.field_179210_e = 0;
    }
    
    BlendState(final SwitchTexGen p_i46268_1_) {
        this();
    }
}
