// 
// Decompiled by Procyon v0.5.30
// 

package jaco.mp3.resources;

import java.math.BigDecimal;

final class SynthesisFilter
{
    private float[] v1;
    private float[] v2;
    private float[] actual_v;
    private int actual_write_pos;
    private float[] samples;
    private int channel;
    private float scalefactor;
    private float[] eq;
    private float[] _tmpOut;
    private static final double MY_PI = 3.141592653589793;
    private static final float cos1_64;
    private static final float cos3_64;
    private static final float cos5_64;
    private static final float cos7_64;
    private static final float cos9_64;
    private static final float cos11_64;
    private static final float cos13_64;
    private static final float cos15_64;
    private static final float cos17_64;
    private static final float cos19_64;
    private static final float cos21_64;
    private static final float cos23_64;
    private static final float cos25_64;
    private static final float cos27_64;
    private static final float cos29_64;
    private static final float cos31_64;
    private static final float cos1_32;
    private static final float cos3_32;
    private static final float cos5_32;
    private static final float cos7_32;
    private static final float cos9_32;
    private static final float cos11_32;
    private static final float cos13_32;
    private static final float cos15_32;
    private static final float cos1_16;
    private static final float cos3_16;
    private static final float cos5_16;
    private static final float cos7_16;
    private static final float cos1_8;
    private static final float cos3_8;
    private static final float cos1_4;
    private static float[] d;
    private static float[][] d16;
    
    static {
        cos1_64 = (float)(1.0 / (2.0 * Math.cos(0.04908738521234052)));
        cos3_64 = (float)(1.0 / (2.0 * Math.cos(0.14726215563702155)));
        cos5_64 = (float)(1.0 / (2.0 * Math.cos(0.2454369260617026)));
        cos7_64 = (float)(1.0 / (2.0 * Math.cos(0.3436116964863836)));
        cos9_64 = (float)(1.0 / (2.0 * Math.cos(0.44178646691106466)));
        cos11_64 = (float)(1.0 / (2.0 * Math.cos(0.5399612373357456)));
        cos13_64 = (float)(1.0 / (2.0 * Math.cos(0.6381360077604268)));
        cos15_64 = (float)(1.0 / (2.0 * Math.cos(0.7363107781851077)));
        cos17_64 = (float)(1.0 / (2.0 * Math.cos(0.8344855486097889)));
        cos19_64 = (float)(1.0 / (2.0 * Math.cos(0.9326603190344698)));
        cos21_64 = (float)(1.0 / (2.0 * Math.cos(1.030835089459151)));
        cos23_64 = (float)(1.0 / (2.0 * Math.cos(1.1290098598838318)));
        cos25_64 = (float)(1.0 / (2.0 * Math.cos(1.227184630308513)));
        cos27_64 = (float)(1.0 / (2.0 * Math.cos(1.325359400733194)));
        cos29_64 = (float)(1.0 / (2.0 * Math.cos(1.423534171157875)));
        cos31_64 = (float)(1.0 / (2.0 * Math.cos(1.521708941582556)));
        cos1_32 = (float)(1.0 / (2.0 * Math.cos(0.09817477042468103)));
        cos3_32 = (float)(1.0 / (2.0 * Math.cos(0.2945243112740431)));
        cos5_32 = (float)(1.0 / (2.0 * Math.cos(0.4908738521234052)));
        cos7_32 = (float)(1.0 / (2.0 * Math.cos(0.6872233929727672)));
        cos9_32 = (float)(1.0 / (2.0 * Math.cos(0.8835729338221293)));
        cos11_32 = (float)(1.0 / (2.0 * Math.cos(1.0799224746714913)));
        cos13_32 = (float)(1.0 / (2.0 * Math.cos(1.2762720155208536)));
        cos15_32 = (float)(1.0 / (2.0 * Math.cos(1.4726215563702154)));
        cos1_16 = (float)(1.0 / (2.0 * Math.cos(0.19634954084936207)));
        cos3_16 = (float)(1.0 / (2.0 * Math.cos(0.5890486225480862)));
        cos5_16 = (float)(1.0 / (2.0 * Math.cos(0.9817477042468103)));
        cos7_16 = (float)(1.0 / (2.0 * Math.cos(1.3744467859455345)));
        cos1_8 = (float)(1.0 / (2.0 * Math.cos(0.39269908169872414)));
        cos3_8 = (float)(1.0 / (2.0 * Math.cos(1.1780972450961724)));
        cos1_4 = (float)(1.0 / (2.0 * Math.cos(0.7853981633974483)));
        SynthesisFilter.d = null;
        SynthesisFilter.d16 = null;
    }
    
    public SynthesisFilter(final int channelnumber, final float factor, final float[] eq0) {
        this._tmpOut = new float[32];
        if (SynthesisFilter.d == null) {
            SynthesisFilter.d = load_d();
            SynthesisFilter.d16 = splitArray(SynthesisFilter.d, 16);
        }
        this.v1 = new float[512];
        this.v2 = new float[512];
        this.samples = new float[32];
        this.channel = channelnumber;
        this.scalefactor = factor;
        this.setEQ(this.eq);
        this.reset();
    }
    
    public void setEQ(final float[] eq0) {
        this.eq = eq0;
        if (this.eq == null) {
            this.eq = new float[32];
            for (int i = 0; i < 32; ++i) {
                this.eq[i] = 1.0f;
            }
        }
        if (this.eq.length < 32) {
            throw new IllegalArgumentException("eq0");
        }
    }
    
    public void reset() {
        for (int p = 0; p < 512; ++p) {
            this.v1[p] = (this.v2[p] = 0.0f);
        }
        for (int p2 = 0; p2 < 32; ++p2) {
            this.samples[p2] = 0.0f;
        }
        this.actual_v = this.v1;
        this.actual_write_pos = 15;
    }
    
    public void input_sample(final float sample, final int subbandnumber) {
        this.samples[subbandnumber] = this.eq[subbandnumber] * sample;
    }
    
    public void input_samples(final float[] s) {
        for (int i = 31; i >= 0; --i) {
            this.samples[i] = s[i] * this.eq[i];
        }
    }
    
    private void compute_new_v() {
        float new_v32;
        float new_v31;
        float new_v30;
        float new_v29;
        float new_v28;
        float new_v27;
        float new_v26;
        float new_v25;
        float new_v24;
        float new_v23;
        float new_v22;
        float new_v21;
        float new_v20;
        float new_v19;
        float new_v18;
        float new_v17;
        float new_v16;
        float new_v15;
        float new_v14;
        float new_v13;
        float new_v12;
        float new_v11;
        float new_v10;
        float new_v9;
        float new_v8;
        float new_v7;
        float new_v6;
        float new_v5;
        float new_v4;
        float new_v3;
        float new_v2;
        float new_v1 = new_v2 = (new_v3 = (new_v4 = (new_v5 = (new_v6 = (new_v7 = (new_v8 = (new_v9 = (new_v10 = (new_v11 = (new_v12 = (new_v13 = (new_v14 = (new_v15 = (new_v16 = (new_v17 = (new_v18 = (new_v19 = (new_v20 = (new_v21 = (new_v22 = (new_v23 = (new_v24 = (new_v25 = (new_v26 = (new_v27 = (new_v28 = (new_v29 = (new_v30 = (new_v31 = (new_v32 = 0.0f))))))))))))))))))))))))))))));
        final float[] s = this.samples;
        final float s2 = s[0];
        final float s3 = s[1];
        final float s4 = s[2];
        final float s5 = s[3];
        final float s6 = s[4];
        final float s7 = s[5];
        final float s8 = s[6];
        final float s9 = s[7];
        final float s10 = s[8];
        final float s11 = s[9];
        final float s12 = s[10];
        final float s13 = s[11];
        final float s14 = s[12];
        final float s15 = s[13];
        final float s16 = s[14];
        final float s17 = s[15];
        final float s18 = s[16];
        final float s19 = s[17];
        final float s20 = s[18];
        final float s21 = s[19];
        final float s22 = s[20];
        final float s23 = s[21];
        final float s24 = s[22];
        final float s25 = s[23];
        final float s26 = s[24];
        final float s27 = s[25];
        final float s28 = s[26];
        final float s29 = s[27];
        final float s30 = s[28];
        final float s31 = s[29];
        final float s32 = s[30];
        final float s33 = s[31];
        float p0 = s2 + s33;
        float p2 = s3 + s32;
        float p3 = s4 + s31;
        float p4 = s5 + s30;
        float p5 = s6 + s29;
        float p6 = s7 + s28;
        float p7 = s8 + s27;
        float p8 = s9 + s26;
        float p9 = s10 + s25;
        float p10 = s11 + s24;
        float p11 = s12 + s23;
        float p12 = s13 + s22;
        float p13 = s14 + s21;
        float p14 = s15 + s20;
        float p15 = s16 + s19;
        float p16 = s17 + s18;
        float pp0 = p0 + p16;
        float pp2 = p2 + p15;
        float pp3 = p3 + p14;
        float pp4 = p4 + p13;
        float pp5 = p5 + p12;
        float pp6 = p6 + p11;
        float pp7 = p7 + p10;
        float pp8 = p8 + p9;
        float pp9 = (p0 - p16) * SynthesisFilter.cos1_32;
        float pp10 = (p2 - p15) * SynthesisFilter.cos3_32;
        float pp11 = (p3 - p14) * SynthesisFilter.cos5_32;
        float pp12 = (p4 - p13) * SynthesisFilter.cos7_32;
        float pp13 = (p5 - p12) * SynthesisFilter.cos9_32;
        float pp14 = (p6 - p11) * SynthesisFilter.cos11_32;
        float pp15 = (p7 - p10) * SynthesisFilter.cos13_32;
        float pp16 = (p8 - p9) * SynthesisFilter.cos15_32;
        p0 = pp0 + pp8;
        p2 = pp2 + pp7;
        p3 = pp3 + pp6;
        p4 = pp4 + pp5;
        p5 = (pp0 - pp8) * SynthesisFilter.cos1_16;
        p6 = (pp2 - pp7) * SynthesisFilter.cos3_16;
        p7 = (pp3 - pp6) * SynthesisFilter.cos5_16;
        p8 = (pp4 - pp5) * SynthesisFilter.cos7_16;
        p9 = pp9 + pp16;
        p10 = pp10 + pp15;
        p11 = pp11 + pp14;
        p12 = pp12 + pp13;
        p13 = (pp9 - pp16) * SynthesisFilter.cos1_16;
        p14 = (pp10 - pp15) * SynthesisFilter.cos3_16;
        p15 = (pp11 - pp14) * SynthesisFilter.cos5_16;
        p16 = (pp12 - pp13) * SynthesisFilter.cos7_16;
        pp0 = p0 + p4;
        pp2 = p2 + p3;
        pp3 = (p0 - p4) * SynthesisFilter.cos1_8;
        pp4 = (p2 - p3) * SynthesisFilter.cos3_8;
        pp5 = p5 + p8;
        pp6 = p6 + p7;
        pp7 = (p5 - p8) * SynthesisFilter.cos1_8;
        pp8 = (p6 - p7) * SynthesisFilter.cos3_8;
        pp9 = p9 + p12;
        pp10 = p10 + p11;
        pp11 = (p9 - p12) * SynthesisFilter.cos1_8;
        pp12 = (p10 - p11) * SynthesisFilter.cos3_8;
        pp13 = p13 + p16;
        pp14 = p14 + p15;
        pp15 = (p13 - p16) * SynthesisFilter.cos1_8;
        pp16 = (p14 - p15) * SynthesisFilter.cos3_8;
        p0 = pp0 + pp2;
        p2 = (pp0 - pp2) * SynthesisFilter.cos1_4;
        p3 = pp3 + pp4;
        p4 = (pp3 - pp4) * SynthesisFilter.cos1_4;
        p5 = pp5 + pp6;
        p6 = (pp5 - pp6) * SynthesisFilter.cos1_4;
        p7 = pp7 + pp8;
        p8 = (pp7 - pp8) * SynthesisFilter.cos1_4;
        p9 = pp9 + pp10;
        p10 = (pp9 - pp10) * SynthesisFilter.cos1_4;
        p11 = pp11 + pp12;
        p12 = (pp11 - pp12) * SynthesisFilter.cos1_4;
        p13 = pp13 + pp14;
        p14 = (pp13 - pp14) * SynthesisFilter.cos1_4;
        p15 = pp15 + pp16;
        p16 = (pp15 - pp16) * SynthesisFilter.cos1_4;
        new_v20 = -(new_v5 = (new_v13 = p8) + p6) - p7;
        new_v28 = -p7 - p8 - p5;
        new_v7 = (new_v11 = (new_v15 = p16) + p12) + p14;
        new_v18 = -(new_v3 = p16 + p14 + p10) - p15;
        float tmp1;
        new_v22 = (tmp1 = -p15 - p16 - p11 - p12) - p14;
        new_v30 = -p15 - p16 - p13 - p9;
        new_v26 = tmp1 - p13;
        new_v32 = -p0;
        new_v2 = p2;
        new_v24 = -(new_v9 = p4) - p3;
        p0 = (s2 - s33) * SynthesisFilter.cos1_64;
        p2 = (s3 - s32) * SynthesisFilter.cos3_64;
        p3 = (s4 - s31) * SynthesisFilter.cos5_64;
        p4 = (s5 - s30) * SynthesisFilter.cos7_64;
        p5 = (s6 - s29) * SynthesisFilter.cos9_64;
        p6 = (s7 - s28) * SynthesisFilter.cos11_64;
        p7 = (s8 - s27) * SynthesisFilter.cos13_64;
        p8 = (s9 - s26) * SynthesisFilter.cos15_64;
        p9 = (s10 - s25) * SynthesisFilter.cos17_64;
        p10 = (s11 - s24) * SynthesisFilter.cos19_64;
        p11 = (s12 - s23) * SynthesisFilter.cos21_64;
        p12 = (s13 - s22) * SynthesisFilter.cos23_64;
        p13 = (s14 - s21) * SynthesisFilter.cos25_64;
        p14 = (s15 - s20) * SynthesisFilter.cos27_64;
        p15 = (s16 - s19) * SynthesisFilter.cos29_64;
        p16 = (s17 - s18) * SynthesisFilter.cos31_64;
        pp0 = p0 + p16;
        pp2 = p2 + p15;
        pp3 = p3 + p14;
        pp4 = p4 + p13;
        pp5 = p5 + p12;
        pp6 = p6 + p11;
        pp7 = p7 + p10;
        pp8 = p8 + p9;
        pp9 = (p0 - p16) * SynthesisFilter.cos1_32;
        pp10 = (p2 - p15) * SynthesisFilter.cos3_32;
        pp11 = (p3 - p14) * SynthesisFilter.cos5_32;
        pp12 = (p4 - p13) * SynthesisFilter.cos7_32;
        pp13 = (p5 - p12) * SynthesisFilter.cos9_32;
        pp14 = (p6 - p11) * SynthesisFilter.cos11_32;
        pp15 = (p7 - p10) * SynthesisFilter.cos13_32;
        pp16 = (p8 - p9) * SynthesisFilter.cos15_32;
        p0 = pp0 + pp8;
        p2 = pp2 + pp7;
        p3 = pp3 + pp6;
        p4 = pp4 + pp5;
        p5 = (pp0 - pp8) * SynthesisFilter.cos1_16;
        p6 = (pp2 - pp7) * SynthesisFilter.cos3_16;
        p7 = (pp3 - pp6) * SynthesisFilter.cos5_16;
        p8 = (pp4 - pp5) * SynthesisFilter.cos7_16;
        p9 = pp9 + pp16;
        p10 = pp10 + pp15;
        p11 = pp11 + pp14;
        p12 = pp12 + pp13;
        p13 = (pp9 - pp16) * SynthesisFilter.cos1_16;
        p14 = (pp10 - pp15) * SynthesisFilter.cos3_16;
        p15 = (pp11 - pp14) * SynthesisFilter.cos5_16;
        p16 = (pp12 - pp13) * SynthesisFilter.cos7_16;
        pp0 = p0 + p4;
        pp2 = p2 + p3;
        pp3 = (p0 - p4) * SynthesisFilter.cos1_8;
        pp4 = (p2 - p3) * SynthesisFilter.cos3_8;
        pp5 = p5 + p8;
        pp6 = p6 + p7;
        pp7 = (p5 - p8) * SynthesisFilter.cos1_8;
        pp8 = (p6 - p7) * SynthesisFilter.cos3_8;
        pp9 = p9 + p12;
        pp10 = p10 + p11;
        pp11 = (p9 - p12) * SynthesisFilter.cos1_8;
        pp12 = (p10 - p11) * SynthesisFilter.cos3_8;
        pp13 = p13 + p16;
        pp14 = p14 + p15;
        pp15 = (p13 - p16) * SynthesisFilter.cos1_8;
        pp16 = (p14 - p15) * SynthesisFilter.cos3_8;
        p0 = pp0 + pp2;
        p2 = (pp0 - pp2) * SynthesisFilter.cos1_4;
        p3 = pp3 + pp4;
        p4 = (pp3 - pp4) * SynthesisFilter.cos1_4;
        p5 = pp5 + pp6;
        p6 = (pp5 - pp6) * SynthesisFilter.cos1_4;
        p7 = pp7 + pp8;
        p8 = (pp7 - pp8) * SynthesisFilter.cos1_4;
        p9 = pp9 + pp10;
        p10 = (pp9 - pp10) * SynthesisFilter.cos1_4;
        p11 = pp11 + pp12;
        p12 = (pp11 - pp12) * SynthesisFilter.cos1_4;
        p13 = pp13 + pp14;
        p14 = (pp13 - pp14) * SynthesisFilter.cos1_4;
        p15 = pp15 + pp16;
        p16 = (pp15 - pp16) * SynthesisFilter.cos1_4;
        new_v6 = (new_v12 = (new_v14 = (new_v16 = p16) + p8) + p12) + p6 + p14;
        new_v8 = (new_v10 = p16 + p12 + p4) + p14;
        new_v17 = -(new_v1 = (tmp1 = p14 + p16 + p10) + p2) - p15;
        new_v19 = -(new_v4 = tmp1 + p6 + p8) - p7 - p15;
        new_v23 = (tmp1 = -p11 - p12 - p15 - p16) - p14 - p3 - p4;
        new_v21 = tmp1 - p14 - p6 - p7 - p8;
        new_v25 = tmp1 - p13 - p3 - p4;
        final float tmp2;
        new_v27 = tmp1 - p13 - (tmp2 = p5 + p7 + p8);
        new_v31 = (tmp1 = -p9 - p13 - p15 - p16) - p0;
        new_v29 = tmp1 - tmp2;
        float[] dest = this.actual_v;
        final int pos = this.actual_write_pos;
        dest[0 + pos] = new_v2;
        dest[16 + pos] = new_v1;
        dest[32 + pos] = new_v3;
        dest[48 + pos] = new_v4;
        dest[64 + pos] = new_v5;
        dest[80 + pos] = new_v6;
        dest[96 + pos] = new_v7;
        dest[112 + pos] = new_v8;
        dest[128 + pos] = new_v9;
        dest[144 + pos] = new_v10;
        dest[160 + pos] = new_v11;
        dest[176 + pos] = new_v12;
        dest[192 + pos] = new_v13;
        dest[208 + pos] = new_v14;
        dest[224 + pos] = new_v15;
        dest[240 + pos] = new_v16;
        dest[256 + pos] = 0.0f;
        dest[272 + pos] = -new_v16;
        dest[288 + pos] = -new_v15;
        dest[304 + pos] = -new_v14;
        dest[320 + pos] = -new_v13;
        dest[336 + pos] = -new_v12;
        dest[352 + pos] = -new_v11;
        dest[368 + pos] = -new_v10;
        dest[384 + pos] = -new_v9;
        dest[400 + pos] = -new_v8;
        dest[416 + pos] = -new_v7;
        dest[432 + pos] = -new_v6;
        dest[448 + pos] = -new_v5;
        dest[464 + pos] = -new_v4;
        dest[480 + pos] = -new_v3;
        dest[496 + pos] = -new_v1;
        dest = ((this.actual_v == this.v1) ? this.v2 : this.v1);
        dest[0 + pos] = -new_v2;
        dest[16 + pos] = new_v17;
        dest[32 + pos] = new_v18;
        dest[48 + pos] = new_v19;
        dest[64 + pos] = new_v20;
        dest[80 + pos] = new_v21;
        dest[96 + pos] = new_v22;
        dest[112 + pos] = new_v23;
        dest[128 + pos] = new_v24;
        dest[144 + pos] = new_v25;
        dest[160 + pos] = new_v26;
        dest[176 + pos] = new_v27;
        dest[192 + pos] = new_v28;
        dest[208 + pos] = new_v29;
        dest[224 + pos] = new_v30;
        dest[240 + pos] = new_v31;
        dest[256 + pos] = new_v32;
        dest[272 + pos] = new_v31;
        dest[288 + pos] = new_v30;
        dest[304 + pos] = new_v29;
        dest[320 + pos] = new_v28;
        dest[336 + pos] = new_v27;
        dest[352 + pos] = new_v26;
        dest[368 + pos] = new_v25;
        dest[384 + pos] = new_v24;
        dest[400 + pos] = new_v23;
        dest[416 + pos] = new_v22;
        dest[432 + pos] = new_v21;
        dest[448 + pos] = new_v20;
        dest[464 + pos] = new_v19;
        dest[480 + pos] = new_v18;
        dest[496 + pos] = new_v17;
    }
    
    private void compute_new_v_old() {
        final float[] new_v = new float[32];
        final float[] p = new float[16];
        final float[] pp = new float[16];
        for (int i = 31; i >= 0; --i) {
            new_v[i] = 0.0f;
        }
        float[] x1 = this.samples;
        p[0] = x1[0] + x1[31];
        p[1] = x1[1] + x1[30];
        p[2] = x1[2] + x1[29];
        p[3] = x1[3] + x1[28];
        p[4] = x1[4] + x1[27];
        p[5] = x1[5] + x1[26];
        p[6] = x1[6] + x1[25];
        p[7] = x1[7] + x1[24];
        p[8] = x1[8] + x1[23];
        p[9] = x1[9] + x1[22];
        p[10] = x1[10] + x1[21];
        p[11] = x1[11] + x1[20];
        p[12] = x1[12] + x1[19];
        p[13] = x1[13] + x1[18];
        p[14] = x1[14] + x1[17];
        p[15] = x1[15] + x1[16];
        pp[0] = p[0] + p[15];
        pp[1] = p[1] + p[14];
        pp[2] = p[2] + p[13];
        pp[3] = p[3] + p[12];
        pp[4] = p[4] + p[11];
        pp[5] = p[5] + p[10];
        pp[6] = p[6] + p[9];
        pp[7] = p[7] + p[8];
        pp[8] = (p[0] - p[15]) * SynthesisFilter.cos1_32;
        pp[9] = (p[1] - p[14]) * SynthesisFilter.cos3_32;
        pp[10] = (p[2] - p[13]) * SynthesisFilter.cos5_32;
        pp[11] = (p[3] - p[12]) * SynthesisFilter.cos7_32;
        pp[12] = (p[4] - p[11]) * SynthesisFilter.cos9_32;
        pp[13] = (p[5] - p[10]) * SynthesisFilter.cos11_32;
        pp[14] = (p[6] - p[9]) * SynthesisFilter.cos13_32;
        pp[15] = (p[7] - p[8]) * SynthesisFilter.cos15_32;
        p[0] = pp[0] + pp[7];
        p[1] = pp[1] + pp[6];
        p[2] = pp[2] + pp[5];
        p[3] = pp[3] + pp[4];
        p[4] = (pp[0] - pp[7]) * SynthesisFilter.cos1_16;
        p[5] = (pp[1] - pp[6]) * SynthesisFilter.cos3_16;
        p[6] = (pp[2] - pp[5]) * SynthesisFilter.cos5_16;
        p[7] = (pp[3] - pp[4]) * SynthesisFilter.cos7_16;
        p[8] = pp[8] + pp[15];
        p[9] = pp[9] + pp[14];
        p[10] = pp[10] + pp[13];
        p[11] = pp[11] + pp[12];
        p[12] = (pp[8] - pp[15]) * SynthesisFilter.cos1_16;
        p[13] = (pp[9] - pp[14]) * SynthesisFilter.cos3_16;
        p[14] = (pp[10] - pp[13]) * SynthesisFilter.cos5_16;
        p[15] = (pp[11] - pp[12]) * SynthesisFilter.cos7_16;
        pp[0] = p[0] + p[3];
        pp[1] = p[1] + p[2];
        pp[2] = (p[0] - p[3]) * SynthesisFilter.cos1_8;
        pp[3] = (p[1] - p[2]) * SynthesisFilter.cos3_8;
        pp[4] = p[4] + p[7];
        pp[5] = p[5] + p[6];
        pp[6] = (p[4] - p[7]) * SynthesisFilter.cos1_8;
        pp[7] = (p[5] - p[6]) * SynthesisFilter.cos3_8;
        pp[8] = p[8] + p[11];
        pp[9] = p[9] + p[10];
        pp[10] = (p[8] - p[11]) * SynthesisFilter.cos1_8;
        pp[11] = (p[9] - p[10]) * SynthesisFilter.cos3_8;
        pp[12] = p[12] + p[15];
        pp[13] = p[13] + p[14];
        pp[14] = (p[12] - p[15]) * SynthesisFilter.cos1_8;
        pp[15] = (p[13] - p[14]) * SynthesisFilter.cos3_8;
        p[0] = pp[0] + pp[1];
        p[1] = (pp[0] - pp[1]) * SynthesisFilter.cos1_4;
        p[2] = pp[2] + pp[3];
        p[3] = (pp[2] - pp[3]) * SynthesisFilter.cos1_4;
        p[4] = pp[4] + pp[5];
        p[5] = (pp[4] - pp[5]) * SynthesisFilter.cos1_4;
        p[6] = pp[6] + pp[7];
        p[7] = (pp[6] - pp[7]) * SynthesisFilter.cos1_4;
        p[8] = pp[8] + pp[9];
        p[9] = (pp[8] - pp[9]) * SynthesisFilter.cos1_4;
        p[10] = pp[10] + pp[11];
        p[11] = (pp[10] - pp[11]) * SynthesisFilter.cos1_4;
        p[12] = pp[12] + pp[13];
        p[13] = (pp[12] - pp[13]) * SynthesisFilter.cos1_4;
        p[14] = pp[14] + pp[15];
        p[15] = (pp[14] - pp[15]) * SynthesisFilter.cos1_4;
        final float[] array = new_v;
        final int n = 19;
        final float[] array2 = new_v;
        final int n2 = 4;
        final float[] array3 = new_v;
        final int n3 = 12;
        final float n4 = p[7];
        array3[n3] = n4;
        final float n5 = n4 + p[5];
        array2[n2] = n5;
        array[n] = -n5 - p[6];
        new_v[27] = -p[6] - p[7] - p[4];
        final float[] array4 = new_v;
        final int n6 = 6;
        final float[] array5 = new_v;
        final int n7 = 10;
        final float[] array6 = new_v;
        final int n8 = 14;
        final float n9 = p[15];
        array6[n8] = n9;
        final float n10 = n9 + p[11];
        array5[n7] = n10;
        array4[n6] = n10 + p[13];
        final float[] array7 = new_v;
        final int n11 = 17;
        final float[] array8 = new_v;
        final int n12 = 2;
        final float n13 = p[15] + p[13] + p[9];
        array8[n12] = n13;
        array7[n11] = -n13 - p[14];
        float tmp1;
        new_v[21] = (tmp1 = -p[14] - p[15] - p[10] - p[11]) - p[13];
        new_v[29] = -p[14] - p[15] - p[12] - p[8];
        new_v[25] = tmp1 - p[12];
        new_v[31] = -p[0];
        new_v[0] = p[1];
        final float[] array9 = new_v;
        final int n14 = 23;
        final float[] array10 = new_v;
        final int n15 = 8;
        final float n16 = p[3];
        array10[n15] = n16;
        array9[n14] = -n16 - p[2];
        p[0] = (x1[0] - x1[31]) * SynthesisFilter.cos1_64;
        p[1] = (x1[1] - x1[30]) * SynthesisFilter.cos3_64;
        p[2] = (x1[2] - x1[29]) * SynthesisFilter.cos5_64;
        p[3] = (x1[3] - x1[28]) * SynthesisFilter.cos7_64;
        p[4] = (x1[4] - x1[27]) * SynthesisFilter.cos9_64;
        p[5] = (x1[5] - x1[26]) * SynthesisFilter.cos11_64;
        p[6] = (x1[6] - x1[25]) * SynthesisFilter.cos13_64;
        p[7] = (x1[7] - x1[24]) * SynthesisFilter.cos15_64;
        p[8] = (x1[8] - x1[23]) * SynthesisFilter.cos17_64;
        p[9] = (x1[9] - x1[22]) * SynthesisFilter.cos19_64;
        p[10] = (x1[10] - x1[21]) * SynthesisFilter.cos21_64;
        p[11] = (x1[11] - x1[20]) * SynthesisFilter.cos23_64;
        p[12] = (x1[12] - x1[19]) * SynthesisFilter.cos25_64;
        p[13] = (x1[13] - x1[18]) * SynthesisFilter.cos27_64;
        p[14] = (x1[14] - x1[17]) * SynthesisFilter.cos29_64;
        p[15] = (x1[15] - x1[16]) * SynthesisFilter.cos31_64;
        pp[0] = p[0] + p[15];
        pp[1] = p[1] + p[14];
        pp[2] = p[2] + p[13];
        pp[3] = p[3] + p[12];
        pp[4] = p[4] + p[11];
        pp[5] = p[5] + p[10];
        pp[6] = p[6] + p[9];
        pp[7] = p[7] + p[8];
        pp[8] = (p[0] - p[15]) * SynthesisFilter.cos1_32;
        pp[9] = (p[1] - p[14]) * SynthesisFilter.cos3_32;
        pp[10] = (p[2] - p[13]) * SynthesisFilter.cos5_32;
        pp[11] = (p[3] - p[12]) * SynthesisFilter.cos7_32;
        pp[12] = (p[4] - p[11]) * SynthesisFilter.cos9_32;
        pp[13] = (p[5] - p[10]) * SynthesisFilter.cos11_32;
        pp[14] = (p[6] - p[9]) * SynthesisFilter.cos13_32;
        pp[15] = (p[7] - p[8]) * SynthesisFilter.cos15_32;
        p[0] = pp[0] + pp[7];
        p[1] = pp[1] + pp[6];
        p[2] = pp[2] + pp[5];
        p[3] = pp[3] + pp[4];
        p[4] = (pp[0] - pp[7]) * SynthesisFilter.cos1_16;
        p[5] = (pp[1] - pp[6]) * SynthesisFilter.cos3_16;
        p[6] = (pp[2] - pp[5]) * SynthesisFilter.cos5_16;
        p[7] = (pp[3] - pp[4]) * SynthesisFilter.cos7_16;
        p[8] = pp[8] + pp[15];
        p[9] = pp[9] + pp[14];
        p[10] = pp[10] + pp[13];
        p[11] = pp[11] + pp[12];
        p[12] = (pp[8] - pp[15]) * SynthesisFilter.cos1_16;
        p[13] = (pp[9] - pp[14]) * SynthesisFilter.cos3_16;
        p[14] = (pp[10] - pp[13]) * SynthesisFilter.cos5_16;
        p[15] = (pp[11] - pp[12]) * SynthesisFilter.cos7_16;
        pp[0] = p[0] + p[3];
        pp[1] = p[1] + p[2];
        pp[2] = (p[0] - p[3]) * SynthesisFilter.cos1_8;
        pp[3] = (p[1] - p[2]) * SynthesisFilter.cos3_8;
        pp[4] = p[4] + p[7];
        pp[5] = p[5] + p[6];
        pp[6] = (p[4] - p[7]) * SynthesisFilter.cos1_8;
        pp[7] = (p[5] - p[6]) * SynthesisFilter.cos3_8;
        pp[8] = p[8] + p[11];
        pp[9] = p[9] + p[10];
        pp[10] = (p[8] - p[11]) * SynthesisFilter.cos1_8;
        pp[11] = (p[9] - p[10]) * SynthesisFilter.cos3_8;
        pp[12] = p[12] + p[15];
        pp[13] = p[13] + p[14];
        pp[14] = (p[12] - p[15]) * SynthesisFilter.cos1_8;
        pp[15] = (p[13] - p[14]) * SynthesisFilter.cos3_8;
        p[0] = pp[0] + pp[1];
        p[1] = (pp[0] - pp[1]) * SynthesisFilter.cos1_4;
        p[2] = pp[2] + pp[3];
        p[3] = (pp[2] - pp[3]) * SynthesisFilter.cos1_4;
        p[4] = pp[4] + pp[5];
        p[5] = (pp[4] - pp[5]) * SynthesisFilter.cos1_4;
        p[6] = pp[6] + pp[7];
        p[7] = (pp[6] - pp[7]) * SynthesisFilter.cos1_4;
        p[8] = pp[8] + pp[9];
        p[9] = (pp[8] - pp[9]) * SynthesisFilter.cos1_4;
        p[10] = pp[10] + pp[11];
        p[11] = (pp[10] - pp[11]) * SynthesisFilter.cos1_4;
        p[12] = pp[12] + pp[13];
        p[13] = (pp[12] - pp[13]) * SynthesisFilter.cos1_4;
        p[14] = pp[14] + pp[15];
        p[15] = (pp[14] - pp[15]) * SynthesisFilter.cos1_4;
        final float[] array11 = new_v;
        final int n17 = 5;
        final float[] array12 = new_v;
        final int n18 = 11;
        final float[] array13 = new_v;
        final int n19 = 13;
        final float[] array14 = new_v;
        final int n20 = 15;
        final float n21 = p[15];
        array14[n20] = n21;
        final float n22 = n21 + p[7];
        array13[n19] = n22;
        final float n23 = n22 + p[11];
        array12[n18] = n23;
        array11[n17] = n23 + p[5] + p[13];
        final float[] array15 = new_v;
        final int n24 = 7;
        final float[] array16 = new_v;
        final int n25 = 9;
        final float n26 = p[15] + p[11] + p[3];
        array16[n25] = n26;
        array15[n24] = n26 + p[13];
        final float[] array17 = new_v;
        final int n27 = 16;
        final float[] array18 = new_v;
        final int n28 = 1;
        final float n29 = (tmp1 = p[13] + p[15] + p[9]) + p[1];
        array18[n28] = n29;
        array17[n27] = -n29 - p[14];
        final float[] array19 = new_v;
        final int n30 = 18;
        final float[] array20 = new_v;
        final int n31 = 3;
        final float n32 = tmp1 + p[5] + p[7];
        array20[n31] = n32;
        array19[n30] = -n32 - p[6] - p[14];
        new_v[22] = (tmp1 = -p[10] - p[11] - p[14] - p[15]) - p[13] - p[2] - p[3];
        new_v[20] = tmp1 - p[13] - p[5] - p[6] - p[7];
        new_v[24] = tmp1 - p[12] - p[2] - p[3];
        final float tmp2;
        new_v[26] = tmp1 - p[12] - (tmp2 = p[4] + p[6] + p[7]);
        new_v[30] = (tmp1 = -p[8] - p[12] - p[14] - p[15]) - p[0];
        new_v[28] = tmp1 - tmp2;
        x1 = new_v;
        final float[] dest = this.actual_v;
        dest[0 + this.actual_write_pos] = x1[0];
        dest[16 + this.actual_write_pos] = x1[1];
        dest[32 + this.actual_write_pos] = x1[2];
        dest[48 + this.actual_write_pos] = x1[3];
        dest[64 + this.actual_write_pos] = x1[4];
        dest[80 + this.actual_write_pos] = x1[5];
        dest[96 + this.actual_write_pos] = x1[6];
        dest[112 + this.actual_write_pos] = x1[7];
        dest[128 + this.actual_write_pos] = x1[8];
        dest[144 + this.actual_write_pos] = x1[9];
        dest[160 + this.actual_write_pos] = x1[10];
        dest[176 + this.actual_write_pos] = x1[11];
        dest[192 + this.actual_write_pos] = x1[12];
        dest[208 + this.actual_write_pos] = x1[13];
        dest[224 + this.actual_write_pos] = x1[14];
        dest[240 + this.actual_write_pos] = x1[15];
        dest[256 + this.actual_write_pos] = 0.0f;
        dest[272 + this.actual_write_pos] = -x1[15];
        dest[288 + this.actual_write_pos] = -x1[14];
        dest[304 + this.actual_write_pos] = -x1[13];
        dest[320 + this.actual_write_pos] = -x1[12];
        dest[336 + this.actual_write_pos] = -x1[11];
        dest[352 + this.actual_write_pos] = -x1[10];
        dest[368 + this.actual_write_pos] = -x1[9];
        dest[384 + this.actual_write_pos] = -x1[8];
        dest[400 + this.actual_write_pos] = -x1[7];
        dest[416 + this.actual_write_pos] = -x1[6];
        dest[432 + this.actual_write_pos] = -x1[5];
        dest[448 + this.actual_write_pos] = -x1[4];
        dest[464 + this.actual_write_pos] = -x1[3];
        dest[480 + this.actual_write_pos] = -x1[2];
        dest[496 + this.actual_write_pos] = -x1[1];
    }
    
    private void compute_pcm_samples0(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[0 + dvp] * dp[0] + vp[15 + dvp] * dp[1] + vp[14 + dvp] * dp[2] + vp[13 + dvp] * dp[3] + vp[12 + dvp] * dp[4] + vp[11 + dvp] * dp[5] + vp[10 + dvp] * dp[6] + vp[9 + dvp] * dp[7] + vp[8 + dvp] * dp[8] + vp[7 + dvp] * dp[9] + vp[6 + dvp] * dp[10] + vp[5 + dvp] * dp[11] + vp[4 + dvp] * dp[12] + vp[3 + dvp] * dp[13] + vp[2 + dvp] * dp[14] + vp[1 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples1(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[1 + dvp] * dp[0] + vp[0 + dvp] * dp[1] + vp[15 + dvp] * dp[2] + vp[14 + dvp] * dp[3] + vp[13 + dvp] * dp[4] + vp[12 + dvp] * dp[5] + vp[11 + dvp] * dp[6] + vp[10 + dvp] * dp[7] + vp[9 + dvp] * dp[8] + vp[8 + dvp] * dp[9] + vp[7 + dvp] * dp[10] + vp[6 + dvp] * dp[11] + vp[5 + dvp] * dp[12] + vp[4 + dvp] * dp[13] + vp[3 + dvp] * dp[14] + vp[2 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples2(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[2 + dvp] * dp[0] + vp[1 + dvp] * dp[1] + vp[0 + dvp] * dp[2] + vp[15 + dvp] * dp[3] + vp[14 + dvp] * dp[4] + vp[13 + dvp] * dp[5] + vp[12 + dvp] * dp[6] + vp[11 + dvp] * dp[7] + vp[10 + dvp] * dp[8] + vp[9 + dvp] * dp[9] + vp[8 + dvp] * dp[10] + vp[7 + dvp] * dp[11] + vp[6 + dvp] * dp[12] + vp[5 + dvp] * dp[13] + vp[4 + dvp] * dp[14] + vp[3 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples3(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final int idx = 0;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[3 + dvp] * dp[0] + vp[2 + dvp] * dp[1] + vp[1 + dvp] * dp[2] + vp[0 + dvp] * dp[3] + vp[15 + dvp] * dp[4] + vp[14 + dvp] * dp[5] + vp[13 + dvp] * dp[6] + vp[12 + dvp] * dp[7] + vp[11 + dvp] * dp[8] + vp[10 + dvp] * dp[9] + vp[9 + dvp] * dp[10] + vp[8 + dvp] * dp[11] + vp[7 + dvp] * dp[12] + vp[6 + dvp] * dp[13] + vp[5 + dvp] * dp[14] + vp[4 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples4(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[4 + dvp] * dp[0] + vp[3 + dvp] * dp[1] + vp[2 + dvp] * dp[2] + vp[1 + dvp] * dp[3] + vp[0 + dvp] * dp[4] + vp[15 + dvp] * dp[5] + vp[14 + dvp] * dp[6] + vp[13 + dvp] * dp[7] + vp[12 + dvp] * dp[8] + vp[11 + dvp] * dp[9] + vp[10 + dvp] * dp[10] + vp[9 + dvp] * dp[11] + vp[8 + dvp] * dp[12] + vp[7 + dvp] * dp[13] + vp[6 + dvp] * dp[14] + vp[5 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples5(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[5 + dvp] * dp[0] + vp[4 + dvp] * dp[1] + vp[3 + dvp] * dp[2] + vp[2 + dvp] * dp[3] + vp[1 + dvp] * dp[4] + vp[0 + dvp] * dp[5] + vp[15 + dvp] * dp[6] + vp[14 + dvp] * dp[7] + vp[13 + dvp] * dp[8] + vp[12 + dvp] * dp[9] + vp[11 + dvp] * dp[10] + vp[10 + dvp] * dp[11] + vp[9 + dvp] * dp[12] + vp[8 + dvp] * dp[13] + vp[7 + dvp] * dp[14] + vp[6 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples6(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[6 + dvp] * dp[0] + vp[5 + dvp] * dp[1] + vp[4 + dvp] * dp[2] + vp[3 + dvp] * dp[3] + vp[2 + dvp] * dp[4] + vp[1 + dvp] * dp[5] + vp[0 + dvp] * dp[6] + vp[15 + dvp] * dp[7] + vp[14 + dvp] * dp[8] + vp[13 + dvp] * dp[9] + vp[12 + dvp] * dp[10] + vp[11 + dvp] * dp[11] + vp[10 + dvp] * dp[12] + vp[9 + dvp] * dp[13] + vp[8 + dvp] * dp[14] + vp[7 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples7(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[7 + dvp] * dp[0] + vp[6 + dvp] * dp[1] + vp[5 + dvp] * dp[2] + vp[4 + dvp] * dp[3] + vp[3 + dvp] * dp[4] + vp[2 + dvp] * dp[5] + vp[1 + dvp] * dp[6] + vp[0 + dvp] * dp[7] + vp[15 + dvp] * dp[8] + vp[14 + dvp] * dp[9] + vp[13 + dvp] * dp[10] + vp[12 + dvp] * dp[11] + vp[11 + dvp] * dp[12] + vp[10 + dvp] * dp[13] + vp[9 + dvp] * dp[14] + vp[8 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples8(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[8 + dvp] * dp[0] + vp[7 + dvp] * dp[1] + vp[6 + dvp] * dp[2] + vp[5 + dvp] * dp[3] + vp[4 + dvp] * dp[4] + vp[3 + dvp] * dp[5] + vp[2 + dvp] * dp[6] + vp[1 + dvp] * dp[7] + vp[0 + dvp] * dp[8] + vp[15 + dvp] * dp[9] + vp[14 + dvp] * dp[10] + vp[13 + dvp] * dp[11] + vp[12 + dvp] * dp[12] + vp[11 + dvp] * dp[13] + vp[10 + dvp] * dp[14] + vp[9 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples9(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[9 + dvp] * dp[0] + vp[8 + dvp] * dp[1] + vp[7 + dvp] * dp[2] + vp[6 + dvp] * dp[3] + vp[5 + dvp] * dp[4] + vp[4 + dvp] * dp[5] + vp[3 + dvp] * dp[6] + vp[2 + dvp] * dp[7] + vp[1 + dvp] * dp[8] + vp[0 + dvp] * dp[9] + vp[15 + dvp] * dp[10] + vp[14 + dvp] * dp[11] + vp[13 + dvp] * dp[12] + vp[12 + dvp] * dp[13] + vp[11 + dvp] * dp[14] + vp[10 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples10(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[10 + dvp] * dp[0] + vp[9 + dvp] * dp[1] + vp[8 + dvp] * dp[2] + vp[7 + dvp] * dp[3] + vp[6 + dvp] * dp[4] + vp[5 + dvp] * dp[5] + vp[4 + dvp] * dp[6] + vp[3 + dvp] * dp[7] + vp[2 + dvp] * dp[8] + vp[1 + dvp] * dp[9] + vp[0 + dvp] * dp[10] + vp[15 + dvp] * dp[11] + vp[14 + dvp] * dp[12] + vp[13 + dvp] * dp[13] + vp[12 + dvp] * dp[14] + vp[11 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples11(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[11 + dvp] * dp[0] + vp[10 + dvp] * dp[1] + vp[9 + dvp] * dp[2] + vp[8 + dvp] * dp[3] + vp[7 + dvp] * dp[4] + vp[6 + dvp] * dp[5] + vp[5 + dvp] * dp[6] + vp[4 + dvp] * dp[7] + vp[3 + dvp] * dp[8] + vp[2 + dvp] * dp[9] + vp[1 + dvp] * dp[10] + vp[0 + dvp] * dp[11] + vp[15 + dvp] * dp[12] + vp[14 + dvp] * dp[13] + vp[13 + dvp] * dp[14] + vp[12 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples12(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[12 + dvp] * dp[0] + vp[11 + dvp] * dp[1] + vp[10 + dvp] * dp[2] + vp[9 + dvp] * dp[3] + vp[8 + dvp] * dp[4] + vp[7 + dvp] * dp[5] + vp[6 + dvp] * dp[6] + vp[5 + dvp] * dp[7] + vp[4 + dvp] * dp[8] + vp[3 + dvp] * dp[9] + vp[2 + dvp] * dp[10] + vp[1 + dvp] * dp[11] + vp[0 + dvp] * dp[12] + vp[15 + dvp] * dp[13] + vp[14 + dvp] * dp[14] + vp[13 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples13(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[13 + dvp] * dp[0] + vp[12 + dvp] * dp[1] + vp[11 + dvp] * dp[2] + vp[10 + dvp] * dp[3] + vp[9 + dvp] * dp[4] + vp[8 + dvp] * dp[5] + vp[7 + dvp] * dp[6] + vp[6 + dvp] * dp[7] + vp[5 + dvp] * dp[8] + vp[4 + dvp] * dp[9] + vp[3 + dvp] * dp[10] + vp[2 + dvp] * dp[11] + vp[1 + dvp] * dp[12] + vp[0 + dvp] * dp[13] + vp[15 + dvp] * dp[14] + vp[14 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples14(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[14 + dvp] * dp[0] + vp[13 + dvp] * dp[1] + vp[12 + dvp] * dp[2] + vp[11 + dvp] * dp[3] + vp[10 + dvp] * dp[4] + vp[9 + dvp] * dp[5] + vp[8 + dvp] * dp[6] + vp[7 + dvp] * dp[7] + vp[6 + dvp] * dp[8] + vp[5 + dvp] * dp[9] + vp[4 + dvp] * dp[10] + vp[3 + dvp] * dp[11] + vp[2 + dvp] * dp[12] + vp[1 + dvp] * dp[13] + vp[0 + dvp] * dp[14] + vp[15 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples15(final Obuffer buffer) {
        final float[] vp = this.actual_v;
        final float[] tmpOut = this._tmpOut;
        int dvp = 0;
        for (int i = 0; i < 32; ++i) {
            final float[] dp = SynthesisFilter.d16[i];
            final float pcm_sample = (vp[15 + dvp] * dp[0] + vp[14 + dvp] * dp[1] + vp[13 + dvp] * dp[2] + vp[12 + dvp] * dp[3] + vp[11 + dvp] * dp[4] + vp[10 + dvp] * dp[5] + vp[9 + dvp] * dp[6] + vp[8 + dvp] * dp[7] + vp[7 + dvp] * dp[8] + vp[6 + dvp] * dp[9] + vp[5 + dvp] * dp[10] + vp[4 + dvp] * dp[11] + vp[3 + dvp] * dp[12] + vp[2 + dvp] * dp[13] + vp[1 + dvp] * dp[14] + vp[0 + dvp] * dp[15]) * this.scalefactor;
            tmpOut[i] = pcm_sample;
            dvp += 16;
        }
    }
    
    private void compute_pcm_samples(final Obuffer buffer) {
        switch (this.actual_write_pos) {
            case 0: {
                this.compute_pcm_samples0(buffer);
                break;
            }
            case 1: {
                this.compute_pcm_samples1(buffer);
                break;
            }
            case 2: {
                this.compute_pcm_samples2(buffer);
                break;
            }
            case 3: {
                this.compute_pcm_samples3(buffer);
                break;
            }
            case 4: {
                this.compute_pcm_samples4(buffer);
                break;
            }
            case 5: {
                this.compute_pcm_samples5(buffer);
                break;
            }
            case 6: {
                this.compute_pcm_samples6(buffer);
                break;
            }
            case 7: {
                this.compute_pcm_samples7(buffer);
                break;
            }
            case 8: {
                this.compute_pcm_samples8(buffer);
                break;
            }
            case 9: {
                this.compute_pcm_samples9(buffer);
                break;
            }
            case 10: {
                this.compute_pcm_samples10(buffer);
                break;
            }
            case 11: {
                this.compute_pcm_samples11(buffer);
                break;
            }
            case 12: {
                this.compute_pcm_samples12(buffer);
                break;
            }
            case 13: {
                this.compute_pcm_samples13(buffer);
                break;
            }
            case 14: {
                this.compute_pcm_samples14(buffer);
                break;
            }
            case 15: {
                this.compute_pcm_samples15(buffer);
                break;
            }
        }
        if (buffer != null) {
            buffer.appendSamples(this.channel, this._tmpOut);
        }
    }
    
    public void calculate_pcm_samples(final Obuffer buffer) {
        this.compute_new_v();
        this.compute_pcm_samples(buffer);
        this.actual_write_pos = (this.actual_write_pos + 1 & 0xF);
        this.actual_v = ((this.actual_v == this.v1) ? this.v2 : this.v1);
        for (int p = 0; p < 32; ++p) {
            this.samples[p] = 0.0f;
        }
    }
    
    private static float[] load_d() {
        final String s = "0,-0.000442504999227821826934814453125,0.0032501220703125,-0.0070037841796875,0.0310821533203125,-0.0786285400390625,0.100311279296875,-0.5720367431640625,1.144989013671875,0.5720367431640625,0.100311279296875,0.0786285400390625,0.0310821533203125,0.0070037841796875,0.0032501220703125,0.000442504999227821826934814453125,-0.0000152590000652708113193511962890625,-0.0004730219952762126922607421875,0.003326416015625,-0.007919312454760074615478515625,0.030517578125,-0.0841827392578125,0.0909271240234375,-0.6002197265625,1.144287109375,0.5438232421875,0.108856201171875,0.07305908203125,0.0314788818359375,0.0061187739484012126922607421875,0.00317382789216935634613037109375,0.00039672901039011776447296142578125,-0.0000152590000652708113193511962890625,-0.0005340580246411263942718505859375,0.00338745093904435634613037109375,-0.0088653564453125,0.02978515625,-0.0897064208984375,0.0806884765625,-0.6282958984375,1.1422119140625,0.5156097412109375,0.1165771484375,0.0675201416015625,0.03173828125,0.0052947998046875,0.0030822749249637126922607421875,0.0003662109957076609134674072265625,-0.0000152590000652708113193511962890625,-0.000579833984375,0.0034332280047237873077392578125,-0.0098419189453125,0.0288848876953125,-0.0951690673828125,0.0695953369140625,-0.656219482421875,1.138763427734375,0.4874725341796875,0.12347412109375,0.0619964599609375,0.0318450927734375,0.004486083984375,0.00299072288908064365386962890625,0.00032043500686995685100555419921875,-0.0000152590000652708113193511962890625,-0.000625610002316534519195556640625,0.00346374488435685634613037109375,-0.0108489990234375,0.027801513671875,-0.1005401611328125,0.0576171875,-0.6839141845703125,1.1339263916015625,0.45947265625,0.12957763671875,0.0565338134765625,0.0318145751953125,0.0037231449969112873077392578125,0.002899169921875,0.0002899169921875,-0.0000152590000652708113193511962890625,-0.0006866459734737873077392578125,0.00347900390625,-0.0118865966796875,0.0265350341796875,-0.1058197021484375,0.0447845458984375,-0.7113189697265625,1.12774658203125,0.4316558837890625,0.1348876953125,0.0511322021484375,0.0316619873046875,0.0030059809796512126922607421875,0.0027923579327762126922607421875,0.0002593990066088736057281494140625,-0.0000152590000652708113193511962890625,-0.000747681013308465480804443359375,0.00347900390625,-0.012939453125,0.02508544921875,-0.1109466552734375,0.0310821533203125,-0.738372802734375,1.1202239990234375,0.404083251953125,0.1394500732421875,0.04583740234375,0.0313873291015625,0.00233459495939314365386962890625,0.00268554710783064365386962890625,0.00024414100334979593753814697265625,-0.000030518000130541622638702392578125,-0.0008087159949354827404022216796875,0.00346374488435685634613037109375,-0.0140228271484375,0.0234222412109375,-0.1159210205078125,0.016510009765625,-0.7650299072265625,1.1113739013671875,0.376800537109375,0.1432647705078125,0.0406341552734375,0.031005859375,0.0016937260515987873077392578125,0.0025787348859012126922607421875,0.000213623003219254314899444580078125,-0.000030518000130541622638702392578125,-0.00088500999845564365386962890625,0.00341796898283064365386962890625,-0.0151214599609375,0.021575927734375,-0.120697021484375,0.00106811500154435634613037109375,-0.7912139892578125,1.1012115478515625,0.3498687744140625,0.1463623046875,0.035552978515625,0.0305328369140625,0.00109863304533064365386962890625,0.0024566650390625,0.00019836399587802588939666748046875,-0.000030518000130541622638702392578125,-0.0009613040019758045673370361328125,0.0033721919171512126922607421875,-0.0162353515625,0.01953125,-0.1252593994140625,-0.015228270553052425384521484375,-0.816864013671875,1.08978271484375,0.3233184814453125,0.148773193359375,0.030609130859375,0.029937744140625,0.0005493159987963736057281494140625,0.0023498539812862873077392578125,0.00016784699982963502407073974609375,-0.000030518000130541622638702392578125,-0.001037598005495965480804443359375,0.0032806401140987873077392578125,-0.0173492431640625,0.0172576904296875,-0.1295623779296875,-0.032379150390625,-0.841949462890625,1.077117919921875,0.297210693359375,0.1504974365234375,0.02581787109375,0.0292816162109375,0.000030518000130541622638702392578125,0.0022430419921875,0.000152588007040321826934814453125,-0.00004577599975164048373699188232421875,-0.001113891950808465480804443359375,0.00317382789216935634613037109375,-0.018463134765625,0.014801025390625,-0.1335906982421875,-0.05035400390625,-0.866363525390625,1.0632171630859375,0.2715911865234375,0.1515960693359375,0.02117919921875,0.028533935546875,-0.000442504999227821826934814453125,0.00212097191251814365386962890625,0.000137328999699093401432037353515625,-0.00004577599975164048373699188232421875,-0.001205443986691534519195556640625,0.00305175804533064365386962890625,-0.0195770263671875,0.012115479446947574615478515625,-0.137298583984375,-0.0691680908203125,-0.8900909423828125,1.04815673828125,0.2465057373046875,0.152069091796875,0.0167083740234375,0.0277252197265625,-0.0008697509765625,0.00201415992341935634613037109375,0.0001220699996338225901126861572265625,-0.00006103499981691129505634307861328125,-0.001296996953897178173065185546875,0.00288391089998185634613037109375,-0.02069091796875,0.0092315673828125,-0.1406707763671875,-0.088775634765625,-0.913055419921875,1.0319366455078125,0.22198486328125,0.1519622802734375,0.012420654296875,0.0268402099609375,-0.0012664790265262126922607421875,0.001907348982058465480804443359375,0.0001068119963747449219226837158203125,-0.00006103499981691129505634307861328125,-0.00138855003751814365386962890625,0.00270080589689314365386962890625,-0.02178955078125,0.006134033203125,-0.1436767578125,-0.109161376953125,-0.9351959228515625,1.014617919921875,0.19805908203125,0.15130615234375,0.0083160400390625,0.025909423828125,-0.001617431989870965480804443359375,0.001785277971066534519195556640625,0.0001068119963747449219226837158203125,-0.0000762940035201609134674072265625,-0.0014801030047237873077392578125,0.0024871830828487873077392578125,-0.022857666015625,0.0028228759765625,-0.1462554931640625,-0.13031005859375,-0.95648193359375,0.996246337890625,0.1747894287109375,0.150115966796875,0.0043945307843387126922607421875,0.024932861328125,-0.00193786597810685634613037109375,0.0016937260515987873077392578125,0.0000915530035854317247867584228515625,-0.0000762940035201609134674072265625,-0.001586913946084678173065185546875,0.00222778297029435634613037109375,-0.0239105224609375,-0.0006866459734737873077392578125,-0.1484222412109375,-0.1522064208984375,-0.9768524169921875,0.9768524169921875,0.1522064208984375,0.1484222412109375,0.0006866459734737873077392578125,0.0239105224609375,-0.00222778297029435634613037109375,0.001586913946084678173065185546875,0.0000762940035201609134674072265625,-0.0000915530035854317247867584228515625,-0.0016937260515987873077392578125,0.00193786597810685634613037109375,-0.024932861328125,-0.0043945307843387126922607421875,-0.150115966796875,-0.1747894287109375,-0.996246337890625,0.95648193359375,0.13031005859375,0.1462554931640625,-0.0028228759765625,0.022857666015625,-0.0024871830828487873077392578125,0.0014801030047237873077392578125,0.0000762940035201609134674072265625,-0.0001068119963747449219226837158203125,-0.001785277971066534519195556640625,0.001617431989870965480804443359375,-0.025909423828125,-0.0083160400390625,-0.15130615234375,-0.19805908203125,-1.014617919921875,0.9351959228515625,0.109161376953125,0.1436767578125,-0.006134033203125,0.02178955078125,-0.00270080589689314365386962890625,0.00138855003751814365386962890625,0.00006103499981691129505634307861328125,-0.0001068119963747449219226837158203125,-0.001907348982058465480804443359375,0.0012664790265262126922607421875,-0.0268402099609375,-0.012420654296875,-0.1519622802734375,-0.22198486328125,-1.0319366455078125,0.913055419921875,0.088775634765625,0.1406707763671875,-0.0092315673828125,0.02069091796875,-0.00288391089998185634613037109375,0.001296996953897178173065185546875,0.00006103499981691129505634307861328125,-0.0001220699996338225901126861572265625,-0.00201415992341935634613037109375,0.0008697509765625,-0.0277252197265625,-0.0167083740234375,-0.152069091796875,-0.2465057373046875,-1.04815673828125,0.8900909423828125,0.0691680908203125,0.137298583984375,-0.012115479446947574615478515625,0.0195770263671875,-0.00305175804533064365386962890625,0.001205443986691534519195556640625,0.00004577599975164048373699188232421875,-0.000137328999699093401432037353515625,-0.00212097191251814365386962890625,0.000442504999227821826934814453125,-0.028533935546875,-0.02117919921875,-0.1515960693359375,-0.2715911865234375,-1.0632171630859375,0.866363525390625,0.05035400390625,0.1335906982421875,-0.014801025390625,0.018463134765625,-0.00317382789216935634613037109375,0.001113891950808465480804443359375,0.00004577599975164048373699188232421875,-0.000152588007040321826934814453125,-0.0022430419921875,-0.000030518000130541622638702392578125,-0.0292816162109375,-0.02581787109375,-0.1504974365234375,-0.297210693359375,-1.077117919921875,0.841949462890625,0.032379150390625,0.1295623779296875,-0.0172576904296875,0.0173492431640625,-0.0032806401140987873077392578125,0.001037598005495965480804443359375,0.000030518000130541622638702392578125,-0.00016784699982963502407073974609375,-0.0023498539812862873077392578125,-0.0005493159987963736057281494140625,-0.029937744140625,-0.030609130859375,-0.148773193359375,-0.3233184814453125,-1.08978271484375,0.816864013671875,0.015228270553052425384521484375,0.1252593994140625,-0.01953125,0.0162353515625,-0.0033721919171512126922607421875,0.0009613040019758045673370361328125,0.000030518000130541622638702392578125,-0.00019836399587802588939666748046875,-0.0024566650390625,-0.00109863304533064365386962890625,-0.0305328369140625,-0.035552978515625,-0.1463623046875,-0.3498687744140625,-1.1012115478515625,0.7912139892578125,-0.00106811500154435634613037109375,0.120697021484375,-0.021575927734375,0.0151214599609375,-0.00341796898283064365386962890625,0.00088500999845564365386962890625,0.000030518000130541622638702392578125,-0.000213623003219254314899444580078125,-0.0025787348859012126922607421875,-0.0016937260515987873077392578125,-0.031005859375,-0.0406341552734375,-0.1432647705078125,-0.376800537109375,-1.1113739013671875,0.7650299072265625,-0.016510009765625,0.1159210205078125,-0.0234222412109375,0.0140228271484375,-0.00346374488435685634613037109375,0.0008087159949354827404022216796875,0.000030518000130541622638702392578125,-0.00024414100334979593753814697265625,-0.00268554710783064365386962890625,-0.00233459495939314365386962890625,-0.0313873291015625,-0.04583740234375,-0.1394500732421875,-0.404083251953125,-1.1202239990234375,0.738372802734375,-0.0310821533203125,0.1109466552734375,-0.02508544921875,0.012939453125,-0.00347900390625,0.000747681013308465480804443359375,0.0000152590000652708113193511962890625,-0.0002593990066088736057281494140625,-0.0027923579327762126922607421875,-0.0030059809796512126922607421875,-0.0316619873046875,-0.0511322021484375,-0.1348876953125,-0.4316558837890625,-1.12774658203125,0.7113189697265625,-0.0447845458984375,0.1058197021484375,-0.0265350341796875,0.0118865966796875,-0.00347900390625,0.0006866459734737873077392578125,0.0000152590000652708113193511962890625,-0.0002899169921875,-0.002899169921875,-0.0037231449969112873077392578125,-0.0318145751953125,-0.0565338134765625,-0.12957763671875,-0.45947265625,-1.1339263916015625,0.6839141845703125,-0.0576171875,0.1005401611328125,-0.027801513671875,0.0108489990234375,-0.00346374488435685634613037109375,0.000625610002316534519195556640625,0.0000152590000652708113193511962890625,-0.00032043500686995685100555419921875,-0.00299072288908064365386962890625,-0.004486083984375,-0.0318450927734375,-0.0619964599609375,-0.12347412109375,-0.4874725341796875,-1.138763427734375,0.656219482421875,-0.0695953369140625,0.0951690673828125,-0.0288848876953125,0.0098419189453125,-0.0034332280047237873077392578125,0.000579833984375,0.0000152590000652708113193511962890625,-0.0003662109957076609134674072265625,-0.0030822749249637126922607421875,-0.0052947998046875,-0.03173828125,-0.0675201416015625,-0.1165771484375,-0.5156097412109375,-1.1422119140625,0.6282958984375,-0.0806884765625,0.0897064208984375,-0.02978515625,0.0088653564453125,-0.00338745093904435634613037109375,0.0005340580246411263942718505859375,0.0000152590000652708113193511962890625,-0.00039672901039011776447296142578125,-0.00317382789216935634613037109375,-0.0061187739484012126922607421875,-0.0314788818359375,-0.07305908203125,-0.108856201171875,-0.5438232421875,-1.144287109375,0.6002197265625,-0.0909271240234375,0.0841827392578125,-0.030517578125,0.007919312454760074615478515625,-0.003326416015625,0.0004730219952762126922607421875,0.0000152590000652708113193511962890625";
        final String[] ss = s.split(",");
        final float[] ff = new float[ss.length];
        for (int i = 0; i < ff.length; ++i) {
            ff[i] = new BigDecimal(ss[i]).floatValue();
        }
        return ff;
    }
    
    private static float[][] splitArray(final float[] array, final int blockSize) {
        final int size = array.length / blockSize;
        final float[][] split = new float[size][];
        for (int i = 0; i < size; ++i) {
            split[i] = subArray(array, i * blockSize, blockSize);
        }
        return split;
    }
    
    private static float[] subArray(final float[] array, final int offs, int len) {
        if (offs + len > array.length) {
            len = array.length - offs;
        }
        if (len < 0) {
            len = 0;
        }
        final float[] subarray = new float[len];
        for (int i = 0; i < len; ++i) {
            subarray[i] = array[offs + i];
        }
        return subarray;
    }
}
