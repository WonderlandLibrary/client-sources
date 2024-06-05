package net.minecraft.client.renderer;

static class PolygonOffsetState
{
    public BooleanState field_179044_a;
    public BooleanState field_179042_b;
    public float field_179043_c;
    public float field_179041_d;
    
    private PolygonOffsetState() {
        this.field_179044_a = new BooleanState(32823);
        this.field_179042_b = new BooleanState(10754);
        this.field_179043_c = 0.0f;
        this.field_179041_d = 0.0f;
    }
    
    PolygonOffsetState(final SwitchTexGen p_i46258_1_) {
        this();
    }
}
