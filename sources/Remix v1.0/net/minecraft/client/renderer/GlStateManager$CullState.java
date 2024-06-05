package net.minecraft.client.renderer;

static class CullState
{
    public BooleanState field_179054_a;
    public int field_179053_b;
    
    private CullState() {
        this.field_179054_a = new BooleanState(2884);
        this.field_179053_b = 1029;
    }
    
    CullState(final SwitchTexGen p_i46261_1_) {
        this();
    }
}
