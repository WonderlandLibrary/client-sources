package net.minecraft.client.renderer;

static class AlphaState
{
    public BooleanState field_179208_a;
    public int field_179206_b;
    public float field_179207_c;
    
    private AlphaState() {
        this.field_179208_a = new BooleanState(3008);
        this.field_179206_b = 519;
        this.field_179207_c = -1.0f;
    }
    
    AlphaState(final SwitchTexGen p_i46269_1_) {
        this();
    }
}
