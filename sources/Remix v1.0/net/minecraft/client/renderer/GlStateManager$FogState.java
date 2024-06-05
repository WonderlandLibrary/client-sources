package net.minecraft.client.renderer;

static class FogState
{
    public BooleanState field_179049_a;
    public int field_179047_b;
    public float field_179048_c;
    public float field_179045_d;
    public float field_179046_e;
    
    private FogState() {
        this.field_179049_a = new BooleanState(2912);
        this.field_179047_b = 2048;
        this.field_179048_c = 1.0f;
        this.field_179045_d = 0.0f;
        this.field_179046_e = 1.0f;
    }
    
    FogState(final SwitchTexGen p_i46259_1_) {
        this();
    }
}
