/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.util;

public class MouseFilter {
    private float field_76336_a;
    private float field_76334_b;
    private float field_76335_c;
    private static final String __OBFID = "CL_00001500";

    public float smooth(float p_76333_1_, float p_76333_2_) {
        this.field_76336_a += p_76333_1_;
        p_76333_1_ = (this.field_76336_a - this.field_76334_b) * p_76333_2_;
        this.field_76335_c += (p_76333_1_ - this.field_76335_c) * 0.5f;
        if (p_76333_1_ > 0.0f && p_76333_1_ > this.field_76335_c || p_76333_1_ < 0.0f && p_76333_1_ < this.field_76335_c) {
            p_76333_1_ = this.field_76335_c;
        }
        this.field_76334_b += p_76333_1_;
        return p_76333_1_;
    }

    public void func_180179_a() {
        this.field_76336_a = 0.0f;
        this.field_76334_b = 0.0f;
        this.field_76335_c = 0.0f;
    }
}

