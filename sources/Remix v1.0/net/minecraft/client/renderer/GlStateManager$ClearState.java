package net.minecraft.client.renderer;

static class ClearState
{
    public double field_179205_a;
    public Color field_179203_b;
    public int field_179204_c;
    
    private ClearState() {
        this.field_179205_a = 1.0;
        this.field_179203_b = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        this.field_179204_c = 0;
    }
    
    ClearState(final SwitchTexGen p_i46266_1_) {
        this();
    }
}
