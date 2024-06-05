package net.minecraft.client.renderer;

static class StencilState
{
    public StencilFunc field_179078_a;
    public int field_179076_b;
    public int field_179077_c;
    public int field_179074_d;
    public int field_179075_e;
    
    private StencilState() {
        this.field_179078_a = new StencilFunc(null);
        this.field_179076_b = -1;
        this.field_179077_c = 7680;
        this.field_179074_d = 7680;
        this.field_179075_e = 7680;
    }
    
    StencilState(final SwitchTexGen p_i46256_1_) {
        this();
    }
}
