package net.minecraft.client.renderer;

static class DepthState
{
    public BooleanState field_179052_a;
    public boolean field_179050_b;
    public int field_179051_c;
    
    private DepthState() {
        this.field_179052_a = new BooleanState(2929);
        this.field_179050_b = true;
        this.field_179051_c = 513;
    }
    
    DepthState(final SwitchTexGen p_i46260_1_) {
        this();
    }
}
