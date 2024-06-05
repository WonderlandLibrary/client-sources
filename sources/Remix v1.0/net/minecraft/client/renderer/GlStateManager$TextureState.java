package net.minecraft.client.renderer;

static class TextureState
{
    public BooleanState field_179060_a;
    public int field_179059_b;
    
    private TextureState() {
        this.field_179060_a = new BooleanState(3553);
        this.field_179059_b = 0;
    }
    
    TextureState(final SwitchTexGen p_i46252_1_) {
        this();
    }
}
