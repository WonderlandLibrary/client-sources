package net.minecraft.client.renderer;

static class TexGenCoord
{
    public BooleanState field_179067_a;
    public int field_179065_b;
    public int field_179066_c;
    
    public TexGenCoord(final int p_i46254_1_, final int p_i46254_2_) {
        this.field_179066_c = -1;
        this.field_179065_b = p_i46254_1_;
        this.field_179067_a = new BooleanState(p_i46254_2_);
    }
}
