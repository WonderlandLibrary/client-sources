package net.minecraft.client.renderer;

static class TexGenState
{
    public TexGenCoord field_179064_a;
    public TexGenCoord field_179062_b;
    public TexGenCoord field_179063_c;
    public TexGenCoord field_179061_d;
    
    private TexGenState() {
        this.field_179064_a = new TexGenCoord(8192, 3168);
        this.field_179062_b = new TexGenCoord(8193, 3169);
        this.field_179063_c = new TexGenCoord(8194, 3170);
        this.field_179061_d = new TexGenCoord(8195, 3171);
    }
    
    TexGenState(final SwitchTexGen p_i46253_1_) {
        this();
    }
}
