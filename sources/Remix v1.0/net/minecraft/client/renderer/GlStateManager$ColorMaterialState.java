package net.minecraft.client.renderer;

static class ColorMaterialState
{
    public BooleanState field_179191_a;
    public int field_179189_b;
    public int field_179190_c;
    
    private ColorMaterialState() {
        this.field_179191_a = new BooleanState(2903);
        this.field_179189_b = 1032;
        this.field_179190_c = 5634;
    }
    
    ColorMaterialState(final SwitchTexGen p_i46262_1_) {
        this();
    }
}
