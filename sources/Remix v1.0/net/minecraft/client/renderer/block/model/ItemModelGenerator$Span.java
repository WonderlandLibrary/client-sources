package net.minecraft.client.renderer.block.model;

static class Span
{
    private final SpanFacing field_178389_a;
    private final int field_178386_d;
    private int field_178387_b;
    private int field_178388_c;
    
    public Span(final SpanFacing p_i46216_1_, final int p_i46216_2_, final int p_i46216_3_) {
        this.field_178389_a = p_i46216_1_;
        this.field_178387_b = p_i46216_2_;
        this.field_178388_c = p_i46216_2_;
        this.field_178386_d = p_i46216_3_;
    }
    
    public void func_178382_a(final int p_178382_1_) {
        if (p_178382_1_ < this.field_178387_b) {
            this.field_178387_b = p_178382_1_;
        }
        else if (p_178382_1_ > this.field_178388_c) {
            this.field_178388_c = p_178382_1_;
        }
    }
    
    public SpanFacing func_178383_a() {
        return this.field_178389_a;
    }
    
    public int func_178385_b() {
        return this.field_178387_b;
    }
    
    public int func_178384_c() {
        return this.field_178388_c;
    }
    
    public int func_178381_d() {
        return this.field_178386_d;
    }
}
