/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.culling;

public class ClippingHelper {
    public float[][] frustum = new float[6][4];
    public float[] projectionMatrix = new float[16];
    public float[] modelviewMatrix = new float[16];
    public float[] clippingMatrix = new float[16];

    private float dot(float[] p_dot_1_, float p_dot_2_, float p_dot_3_, float p_dot_4_) {
        return p_dot_1_[0] * p_dot_2_ + p_dot_1_[1] * p_dot_3_ + p_dot_1_[2] * p_dot_4_ + p_dot_1_[3];
    }

    public boolean isBoxInFrustum(double p_78553_1_, double p_78553_3_, double p_78553_5_, double p_78553_7_, double p_78553_9_, double p_78553_11_) {
        float f = (float)p_78553_1_;
        float f1 = (float)p_78553_3_;
        float f2 = (float)p_78553_5_;
        float f3 = (float)p_78553_7_;
        float f4 = (float)p_78553_9_;
        float f5 = (float)p_78553_11_;
        for (int i = 0; i < 6; ++i) {
            float[] afloat = this.frustum[i];
            if (!(this.dot(afloat, f, f1, f2) <= 0.0f) || !(this.dot(afloat, f3, f1, f2) <= 0.0f) || !(this.dot(afloat, f, f4, f2) <= 0.0f) || !(this.dot(afloat, f3, f4, f2) <= 0.0f) || !(this.dot(afloat, f, f1, f5) <= 0.0f) || !(this.dot(afloat, f3, f1, f5) <= 0.0f) || !(this.dot(afloat, f, f4, f5) <= 0.0f) || !(this.dot(afloat, f3, f4, f5) <= 0.0f)) continue;
            return false;
        }
        return true;
    }
}

