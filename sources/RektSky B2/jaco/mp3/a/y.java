package jaco.mp3.a;

import java.math.*;

final class y
{
    private float[] a;
    private float[] b;
    private float[] c;
    private int d;
    private float[] e;
    private int f;
    private float g;
    private float[] h;
    private float[] i;
    private static final float j;
    private static final float k;
    private static final float l;
    private static final float m;
    private static final float n;
    private static final float o;
    private static final float p;
    private static final float q;
    private static final float r;
    private static final float s;
    private static final float t;
    private static final float u;
    private static final float v;
    private static final float w;
    private static final float x;
    private static final float y;
    private static final float z;
    private static final float A;
    private static final float B;
    private static final float C;
    private static final float D;
    private static final float E;
    private static final float F;
    private static final float G;
    private static final float H;
    private static final float I;
    private static final float J;
    private static final float K;
    private static final float L;
    private static final float M;
    private static final float N;
    private static float[] O;
    private static float[][] P;
    
    static {
        j = (float)(1.0 / (2.0 * Math.cos(0.04908738521234052)));
        k = (float)(1.0 / (2.0 * Math.cos(0.14726215563702155)));
        l = (float)(1.0 / (2.0 * Math.cos(0.2454369260617026)));
        m = (float)(1.0 / (2.0 * Math.cos(0.3436116964863836)));
        n = (float)(1.0 / (2.0 * Math.cos(0.44178646691106466)));
        o = (float)(1.0 / (2.0 * Math.cos(0.5399612373357456)));
        p = (float)(1.0 / (2.0 * Math.cos(0.6381360077604268)));
        q = (float)(1.0 / (2.0 * Math.cos(0.7363107781851077)));
        r = (float)(1.0 / (2.0 * Math.cos(0.8344855486097889)));
        s = (float)(1.0 / (2.0 * Math.cos(0.9326603190344698)));
        t = (float)(1.0 / (2.0 * Math.cos(1.030835089459151)));
        u = (float)(1.0 / (2.0 * Math.cos(1.1290098598838318)));
        v = (float)(1.0 / (2.0 * Math.cos(1.227184630308513)));
        w = (float)(1.0 / (2.0 * Math.cos(1.325359400733194)));
        x = (float)(1.0 / (2.0 * Math.cos(1.423534171157875)));
        y = (float)(1.0 / (2.0 * Math.cos(1.521708941582556)));
        z = (float)(1.0 / (2.0 * Math.cos(0.09817477042468103)));
        A = (float)(1.0 / (2.0 * Math.cos(0.2945243112740431)));
        B = (float)(1.0 / (2.0 * Math.cos(0.4908738521234052)));
        C = (float)(1.0 / (2.0 * Math.cos(0.6872233929727672)));
        D = (float)(1.0 / (2.0 * Math.cos(0.8835729338221293)));
        E = (float)(1.0 / (2.0 * Math.cos(1.0799224746714913)));
        F = (float)(1.0 / (2.0 * Math.cos(1.2762720155208536)));
        G = (float)(1.0 / (2.0 * Math.cos(1.4726215563702154)));
        H = (float)(1.0 / (2.0 * Math.cos(0.19634954084936207)));
        I = (float)(1.0 / (2.0 * Math.cos(0.5890486225480862)));
        J = (float)(1.0 / (2.0 * Math.cos(0.9817477042468103)));
        K = (float)(1.0 / (2.0 * Math.cos(1.3744467859455345)));
        L = (float)(1.0 / (2.0 * Math.cos(0.39269908169872414)));
        M = (float)(1.0 / (2.0 * Math.cos(1.1780972450961724)));
        N = (float)(1.0 / (2.0 * Math.cos(0.7853981633974483)));
        jaco.mp3.a.y.O = null;
        jaco.mp3.a.y.P = null;
    }
    
    public y(final int f, final float g) {
        this.i = new float[32];
        if (jaco.mp3.a.y.O == null) {
            jaco.mp3.a.y.P = a(jaco.mp3.a.y.O = b(), 16);
        }
        this.a = new float[512];
        this.b = new float[512];
        this.e = new float[32];
        this.f = f;
        this.g = g;
        this.b(this.h);
        this.a();
    }
    
    private void b(final float[] h) {
        this.h = h;
        if (this.h == null) {
            this.h = new float[32];
            for (int i = 0; i < 32; ++i) {
                this.h[i] = 1.0f;
            }
        }
        if (this.h.length < 32) {
            throw new IllegalArgumentException("eq0");
        }
    }
    
    private void a() {
        for (int i = 0; i < 512; ++i) {
            this.a[i] = (this.b[i] = 0.0f);
        }
        for (int j = 0; j < 32; ++j) {
            this.e[j] = 0.0f;
        }
        this.c = this.a;
        this.d = 15;
    }
    
    public final void a(final float n, final int n2) {
        this.e[n2] = this.h[n2] * n;
    }
    
    public final void a(final float[] array) {
        for (int i = 31; i >= 0; --i) {
            this.e[i] = array[i] * this.h[i];
        }
    }
    
    public final void a(final C c) {
        final float[] e;
        final float n = (e = this.e)[0];
        final float n2 = e[1];
        final float n3 = e[2];
        final float n4 = e[3];
        final float n5 = e[4];
        final float n6 = e[5];
        final float n7 = e[6];
        final float n8 = e[7];
        final float n9 = e[8];
        final float n10 = e[9];
        final float n11 = e[10];
        final float n12 = e[11];
        final float n13 = e[12];
        final float n14 = e[13];
        final float n15 = e[14];
        final float n16 = e[15];
        final float n17 = e[16];
        final float n18 = e[17];
        final float n19 = e[18];
        final float n20 = e[19];
        final float n21 = e[20];
        final float n22 = e[21];
        final float n23 = e[22];
        final float n24 = e[23];
        final float n25 = e[24];
        final float n26 = e[25];
        final float n27 = e[26];
        final float n28 = e[27];
        final float n29 = e[28];
        final float n30 = e[29];
        final float n31 = e[30];
        final float n32 = e[31];
        final float n33 = n + n32;
        final float n34 = n2 + n31;
        final float n35 = n3 + n30;
        final float n36 = n4 + n29;
        final float n37 = n5 + n28;
        final float n38 = n6 + n27;
        final float n39 = n7 + n26;
        final float n40 = n8 + n25;
        final float n41 = n9 + n24;
        final float n42 = n10 + n23;
        final float n43 = n11 + n22;
        final float n44 = n12 + n21;
        final float n45 = n13 + n20;
        final float n46 = n14 + n19;
        final float n47 = n15 + n18;
        final float n48 = n16 + n17;
        final float n49 = n33 + n48;
        final float n50 = n34 + n47;
        final float n51 = n35 + n46;
        final float n52 = n36 + n45;
        final float n53 = n37 + n44;
        final float n54 = n38 + n43;
        final float n55 = n39 + n42;
        final float n56 = n40 + n41;
        final float n57 = (n33 - n48) * jaco.mp3.a.y.z;
        final float n58 = (n34 - n47) * jaco.mp3.a.y.A;
        final float n59 = (n35 - n46) * jaco.mp3.a.y.B;
        final float n60 = (n36 - n45) * jaco.mp3.a.y.C;
        final float n61 = (n37 - n44) * jaco.mp3.a.y.D;
        final float n62 = (n38 - n43) * jaco.mp3.a.y.E;
        final float n63 = (n39 - n42) * jaco.mp3.a.y.F;
        final float n64 = (n40 - n41) * jaco.mp3.a.y.G;
        final float n65 = n49 + n56;
        final float n66 = n50 + n55;
        final float n67 = n51 + n54;
        final float n68 = n52 + n53;
        final float n69 = (n49 - n56) * jaco.mp3.a.y.H;
        final float n70 = (n50 - n55) * jaco.mp3.a.y.I;
        final float n71 = (n51 - n54) * jaco.mp3.a.y.J;
        final float n72 = (n52 - n53) * jaco.mp3.a.y.K;
        final float n73 = n57 + n64;
        final float n74 = n58 + n63;
        final float n75 = n59 + n62;
        final float n76 = n60 + n61;
        final float n77 = (n57 - n64) * jaco.mp3.a.y.H;
        final float n78 = (n58 - n63) * jaco.mp3.a.y.I;
        final float n79 = (n59 - n62) * jaco.mp3.a.y.J;
        final float n80 = (n60 - n61) * jaco.mp3.a.y.K;
        final float n81 = n65 + n68;
        final float n82 = n66 + n67;
        final float n83 = (n65 - n68) * jaco.mp3.a.y.L;
        final float n84 = (n66 - n67) * jaco.mp3.a.y.M;
        final float n85 = n69 + n72;
        final float n86 = n70 + n71;
        final float n87 = (n69 - n72) * jaco.mp3.a.y.L;
        final float n88 = (n70 - n71) * jaco.mp3.a.y.M;
        final float n89 = n73 + n76;
        final float n90 = n74 + n75;
        final float n91 = (n73 - n76) * jaco.mp3.a.y.L;
        final float n92 = (n74 - n75) * jaco.mp3.a.y.M;
        final float n93 = n77 + n80;
        final float n94 = n78 + n79;
        final float n95 = (n77 - n80) * jaco.mp3.a.y.L;
        final float n96 = (n78 - n79) * jaco.mp3.a.y.M;
        final float n97 = n81 + n82;
        final float n98 = (n81 - n82) * jaco.mp3.a.y.N;
        final float n99 = n83 + n84;
        final float n100 = (n83 - n84) * jaco.mp3.a.y.N;
        final float n101 = n85 + n86;
        final float n102 = (n85 - n86) * jaco.mp3.a.y.N;
        final float n103 = n87 + n88;
        final float n104 = (n87 - n88) * jaco.mp3.a.y.N;
        final float n105 = n89 + n90;
        final float n106 = (n89 - n90) * jaco.mp3.a.y.N;
        final float n107 = n91 + n92;
        final float n108 = (n91 - n92) * jaco.mp3.a.y.N;
        final float n109 = n93 + n94;
        final float n110 = (n93 - n94) * jaco.mp3.a.y.N;
        final float n111 = n95 + n96;
        final float n112 = (n95 - n96) * jaco.mp3.a.y.N;
        final float n115;
        final float n114;
        final float n113 = -(n114 = (n115 = n104) + n102) - n103;
        final float n116 = -n103 - n115 - n101;
        final float n119;
        final float n118;
        final float n117 = (n118 = (n119 = n112) + n108) + n110;
        final float n121;
        final float n120 = -(n121 = n119 + n110 + n106) - n111;
        final float n123;
        final float n122 = (n123 = -n111 - n119 - n107 - n108) - n110;
        final float n124 = -n111 - n119 - n109 - n105;
        final float n125 = n123 - n109;
        final float n126 = -n97;
        final float n127 = n98;
        final float n129;
        final float n128 = -(n129 = n100) - n99;
        final float n130 = (n - n32) * jaco.mp3.a.y.j;
        final float n131 = (n2 - n31) * jaco.mp3.a.y.k;
        final float n132 = (n3 - n30) * jaco.mp3.a.y.l;
        final float n133 = (n4 - n29) * jaco.mp3.a.y.m;
        final float n134 = (n5 - n28) * jaco.mp3.a.y.n;
        final float n135 = (n6 - n27) * jaco.mp3.a.y.o;
        final float n136 = (n7 - n26) * jaco.mp3.a.y.p;
        final float n137 = (n8 - n25) * jaco.mp3.a.y.q;
        final float n138 = (n9 - n24) * jaco.mp3.a.y.r;
        final float n139 = (n10 - n23) * jaco.mp3.a.y.s;
        final float n140 = (n11 - n22) * jaco.mp3.a.y.t;
        final float n141 = (n12 - n21) * jaco.mp3.a.y.u;
        final float n142 = (n13 - n20) * jaco.mp3.a.y.v;
        final float n143 = (n14 - n19) * jaco.mp3.a.y.w;
        final float n144 = (n15 - n18) * jaco.mp3.a.y.x;
        final float n145 = (n16 - n17) * jaco.mp3.a.y.y;
        final float n146 = n130 + n145;
        final float n147 = n131 + n144;
        final float n148 = n132 + n143;
        final float n149 = n133 + n142;
        final float n150 = n134 + n141;
        final float n151 = n135 + n140;
        final float n152 = n136 + n139;
        final float n153 = n137 + n138;
        final float n154 = (n130 - n145) * jaco.mp3.a.y.z;
        final float n155 = (n131 - n144) * jaco.mp3.a.y.A;
        final float n156 = (n132 - n143) * jaco.mp3.a.y.B;
        final float n157 = (n133 - n142) * jaco.mp3.a.y.C;
        final float n158 = (n134 - n141) * jaco.mp3.a.y.D;
        final float n159 = (n135 - n140) * jaco.mp3.a.y.E;
        final float n160 = (n136 - n139) * jaco.mp3.a.y.F;
        final float n161 = (n137 - n138) * jaco.mp3.a.y.G;
        final float n162 = n146 + n153;
        final float n163 = n147 + n152;
        final float n164 = n148 + n151;
        final float n165 = n149 + n150;
        final float n166 = (n146 - n153) * jaco.mp3.a.y.H;
        final float n167 = (n147 - n152) * jaco.mp3.a.y.I;
        final float n168 = (n148 - n151) * jaco.mp3.a.y.J;
        final float n169 = (n149 - n150) * jaco.mp3.a.y.K;
        final float n170 = n154 + n161;
        final float n171 = n155 + n160;
        final float n172 = n156 + n159;
        final float n173 = n157 + n158;
        final float n174 = (n154 - n161) * jaco.mp3.a.y.H;
        final float n175 = (n155 - n160) * jaco.mp3.a.y.I;
        final float n176 = (n156 - n159) * jaco.mp3.a.y.J;
        final float n177 = (n157 - n158) * jaco.mp3.a.y.K;
        final float n178 = n162 + n165;
        final float n179 = n163 + n164;
        final float n180 = (n162 - n165) * jaco.mp3.a.y.L;
        final float n181 = (n163 - n164) * jaco.mp3.a.y.M;
        final float n182 = n166 + n169;
        final float n183 = n167 + n168;
        final float n184 = (n166 - n169) * jaco.mp3.a.y.L;
        final float n185 = (n167 - n168) * jaco.mp3.a.y.M;
        final float n186 = n170 + n173;
        final float n187 = n171 + n172;
        final float n188 = (n170 - n173) * jaco.mp3.a.y.L;
        final float n189 = (n171 - n172) * jaco.mp3.a.y.M;
        final float n190 = n174 + n177;
        final float n191 = n175 + n176;
        final float n192 = (n174 - n177) * jaco.mp3.a.y.L;
        final float n193 = (n175 - n176) * jaco.mp3.a.y.M;
        final float n194 = n178 + n179;
        final float n195 = (n178 - n179) * jaco.mp3.a.y.N;
        final float n196 = n180 + n181;
        final float n197 = (n180 - n181) * jaco.mp3.a.y.N;
        final float n198 = n182 + n183;
        final float n199 = (n182 - n183) * jaco.mp3.a.y.N;
        final float n200 = n184 + n185;
        final float n201 = (n184 - n185) * jaco.mp3.a.y.N;
        final float n202 = n186 + n187;
        final float n203 = (n186 - n187) * jaco.mp3.a.y.N;
        final float n204 = n188 + n189;
        final float n205 = (n188 - n189) * jaco.mp3.a.y.N;
        final float n206 = n190 + n191;
        final float n207 = (n190 - n191) * jaco.mp3.a.y.N;
        final float n208 = n192 + n193;
        final float n212;
        final float n211;
        final float n210;
        final float n209 = (n210 = (n211 = (n212 = (n192 - n193) * jaco.mp3.a.y.N) + n201) + n205) + n199 + n207;
        final float n214;
        final float n213 = (n214 = n212 + n205 + n197) + n207;
        final float n217;
        final float n216;
        final float n215 = -(n216 = (n217 = n207 + n212 + n203) + n195) - n208;
        final float n219;
        final float n218 = -(n219 = n217 + n199 + n201) - n200 - n208;
        final float n221;
        final float n220 = (n221 = -n204 - n205 - n208 - n212) - n207 - n196 - n197;
        final float n222 = n221 - n207 - n199 - n200 - n201;
        final float n223 = n221 - n206 - n196 - n197;
        final float n225;
        final float n224 = n221 - n206 - (n225 = n198 + n200 + n201);
        final float n227;
        final float n226 = (n227 = -n202 - n206 - n208 - n212) - n194;
        final float n228 = n227 - n225;
        final float[] c2 = this.c;
        final int d = this.d;
        c2[d + 0] = n127;
        c2[d + 16] = n216;
        c2[d + 32] = n121;
        c2[d + 48] = n219;
        c2[d + 64] = n114;
        c2[d + 80] = n209;
        c2[d + 96] = n117;
        c2[d + 112] = n213;
        c2[d + 128] = n129;
        c2[d + 144] = n214;
        c2[d + 160] = n118;
        c2[d + 176] = n210;
        c2[d + 192] = n115;
        c2[d + 208] = n211;
        c2[d + 224] = n119;
        c2[d + 240] = n212;
        c2[d + 256] = 0.0f;
        c2[d + 272] = -n212;
        c2[d + 288] = -n119;
        c2[d + 304] = -n211;
        c2[d + 320] = -n115;
        c2[d + 336] = -n210;
        c2[d + 352] = -n118;
        c2[d + 368] = -n214;
        c2[d + 384] = -n129;
        c2[d + 400] = -n213;
        c2[d + 416] = -n117;
        c2[d + 432] = -n209;
        c2[d + 448] = -n114;
        c2[d + 464] = -n219;
        c2[d + 480] = -n121;
        c2[d + 496] = -n216;
        final float[] array;
        (array = ((this.c == this.a) ? this.b : this.a))[d + 0] = -n127;
        array[d + 16] = n215;
        array[d + 32] = n120;
        array[d + 48] = n218;
        array[d + 64] = n113;
        array[d + 80] = n222;
        array[d + 96] = n122;
        array[d + 112] = n220;
        array[d + 128] = n128;
        array[d + 144] = n223;
        array[d + 160] = n125;
        array[d + 176] = n224;
        array[d + 192] = n116;
        array[d + 208] = n228;
        array[d + 224] = n124;
        array[d + 240] = n226;
        array[d + 256] = n126;
        array[d + 272] = n226;
        array[d + 288] = n124;
        array[d + 304] = n228;
        array[d + 320] = n116;
        array[d + 336] = n224;
        array[d + 352] = n125;
        array[d + 368] = n223;
        array[d + 384] = n128;
        array[d + 400] = n220;
        array[d + 416] = n122;
        array[d + 432] = n222;
        array[d + 448] = n113;
        array[d + 464] = n218;
        array[d + 480] = n120;
        array[d + 496] = n215;
        switch (this.d) {
            case 0: {
                final float[] c3 = this.c;
                final float[] i = this.i;
                int n229 = 0;
                for (int j = 0; j < 32; ++j) {
                    final float[] array2 = jaco.mp3.a.y.P[j];
                    i[j] = (c3[n229 + 0] * array2[0] + c3[n229 + 15] * array2[1] + c3[n229 + 14] * array2[2] + c3[n229 + 13] * array2[3] + c3[n229 + 12] * array2[4] + c3[n229 + 11] * array2[5] + c3[n229 + 10] * array2[6] + c3[n229 + 9] * array2[7] + c3[n229 + 8] * array2[8] + c3[n229 + 7] * array2[9] + c3[n229 + 6] * array2[10] + c3[n229 + 5] * array2[11] + c3[n229 + 4] * array2[12] + c3[n229 + 3] * array2[13] + c3[n229 + 2] * array2[14] + c3[n229 + 1] * array2[15]) * this.g;
                    n229 += 16;
                }
                break;
            }
            case 1: {
                final float[] c4 = this.c;
                final float[] k = this.i;
                int n230 = 0;
                for (int l = 0; l < 32; ++l) {
                    final float[] array3 = jaco.mp3.a.y.P[l];
                    k[l] = (c4[n230 + 1] * array3[0] + c4[n230 + 0] * array3[1] + c4[n230 + 15] * array3[2] + c4[n230 + 14] * array3[3] + c4[n230 + 13] * array3[4] + c4[n230 + 12] * array3[5] + c4[n230 + 11] * array3[6] + c4[n230 + 10] * array3[7] + c4[n230 + 9] * array3[8] + c4[n230 + 8] * array3[9] + c4[n230 + 7] * array3[10] + c4[n230 + 6] * array3[11] + c4[n230 + 5] * array3[12] + c4[n230 + 4] * array3[13] + c4[n230 + 3] * array3[14] + c4[n230 + 2] * array3[15]) * this.g;
                    n230 += 16;
                }
                break;
            }
            case 2: {
                final float[] c5 = this.c;
                final float[] m = this.i;
                int n231 = 0;
                for (int n232 = 0; n232 < 32; ++n232) {
                    final float[] array4 = jaco.mp3.a.y.P[n232];
                    m[n232] = (c5[n231 + 2] * array4[0] + c5[n231 + 1] * array4[1] + c5[n231 + 0] * array4[2] + c5[n231 + 15] * array4[3] + c5[n231 + 14] * array4[4] + c5[n231 + 13] * array4[5] + c5[n231 + 12] * array4[6] + c5[n231 + 11] * array4[7] + c5[n231 + 10] * array4[8] + c5[n231 + 9] * array4[9] + c5[n231 + 8] * array4[10] + c5[n231 + 7] * array4[11] + c5[n231 + 6] * array4[12] + c5[n231 + 5] * array4[13] + c5[n231 + 4] * array4[14] + c5[n231 + 3] * array4[15]) * this.g;
                    n231 += 16;
                }
                break;
            }
            case 3: {
                final float[] c6 = this.c;
                final float[] i2 = this.i;
                int n233 = 0;
                for (int n234 = 0; n234 < 32; ++n234) {
                    final float[] array5 = jaco.mp3.a.y.P[n234];
                    i2[n234] = (c6[n233 + 3] * array5[0] + c6[n233 + 2] * array5[1] + c6[n233 + 1] * array5[2] + c6[n233 + 0] * array5[3] + c6[n233 + 15] * array5[4] + c6[n233 + 14] * array5[5] + c6[n233 + 13] * array5[6] + c6[n233 + 12] * array5[7] + c6[n233 + 11] * array5[8] + c6[n233 + 10] * array5[9] + c6[n233 + 9] * array5[10] + c6[n233 + 8] * array5[11] + c6[n233 + 7] * array5[12] + c6[n233 + 6] * array5[13] + c6[n233 + 5] * array5[14] + c6[n233 + 4] * array5[15]) * this.g;
                    n233 += 16;
                }
                break;
            }
            case 4: {
                final float[] c7 = this.c;
                final float[] i3 = this.i;
                int n235 = 0;
                for (int n236 = 0; n236 < 32; ++n236) {
                    final float[] array6 = jaco.mp3.a.y.P[n236];
                    i3[n236] = (c7[n235 + 4] * array6[0] + c7[n235 + 3] * array6[1] + c7[n235 + 2] * array6[2] + c7[n235 + 1] * array6[3] + c7[n235 + 0] * array6[4] + c7[n235 + 15] * array6[5] + c7[n235 + 14] * array6[6] + c7[n235 + 13] * array6[7] + c7[n235 + 12] * array6[8] + c7[n235 + 11] * array6[9] + c7[n235 + 10] * array6[10] + c7[n235 + 9] * array6[11] + c7[n235 + 8] * array6[12] + c7[n235 + 7] * array6[13] + c7[n235 + 6] * array6[14] + c7[n235 + 5] * array6[15]) * this.g;
                    n235 += 16;
                }
                break;
            }
            case 5: {
                final float[] c8 = this.c;
                final float[] i4 = this.i;
                int n237 = 0;
                for (int n238 = 0; n238 < 32; ++n238) {
                    final float[] array7 = jaco.mp3.a.y.P[n238];
                    i4[n238] = (c8[n237 + 5] * array7[0] + c8[n237 + 4] * array7[1] + c8[n237 + 3] * array7[2] + c8[n237 + 2] * array7[3] + c8[n237 + 1] * array7[4] + c8[n237 + 0] * array7[5] + c8[n237 + 15] * array7[6] + c8[n237 + 14] * array7[7] + c8[n237 + 13] * array7[8] + c8[n237 + 12] * array7[9] + c8[n237 + 11] * array7[10] + c8[n237 + 10] * array7[11] + c8[n237 + 9] * array7[12] + c8[n237 + 8] * array7[13] + c8[n237 + 7] * array7[14] + c8[n237 + 6] * array7[15]) * this.g;
                    n237 += 16;
                }
                break;
            }
            case 6: {
                final float[] c9 = this.c;
                final float[] i5 = this.i;
                int n239 = 0;
                for (int n240 = 0; n240 < 32; ++n240) {
                    final float[] array8 = jaco.mp3.a.y.P[n240];
                    i5[n240] = (c9[n239 + 6] * array8[0] + c9[n239 + 5] * array8[1] + c9[n239 + 4] * array8[2] + c9[n239 + 3] * array8[3] + c9[n239 + 2] * array8[4] + c9[n239 + 1] * array8[5] + c9[n239 + 0] * array8[6] + c9[n239 + 15] * array8[7] + c9[n239 + 14] * array8[8] + c9[n239 + 13] * array8[9] + c9[n239 + 12] * array8[10] + c9[n239 + 11] * array8[11] + c9[n239 + 10] * array8[12] + c9[n239 + 9] * array8[13] + c9[n239 + 8] * array8[14] + c9[n239 + 7] * array8[15]) * this.g;
                    n239 += 16;
                }
                break;
            }
            case 7: {
                final float[] c10 = this.c;
                final float[] i6 = this.i;
                int n241 = 0;
                for (int n242 = 0; n242 < 32; ++n242) {
                    final float[] array9 = jaco.mp3.a.y.P[n242];
                    i6[n242] = (c10[n241 + 7] * array9[0] + c10[n241 + 6] * array9[1] + c10[n241 + 5] * array9[2] + c10[n241 + 4] * array9[3] + c10[n241 + 3] * array9[4] + c10[n241 + 2] * array9[5] + c10[n241 + 1] * array9[6] + c10[n241 + 0] * array9[7] + c10[n241 + 15] * array9[8] + c10[n241 + 14] * array9[9] + c10[n241 + 13] * array9[10] + c10[n241 + 12] * array9[11] + c10[n241 + 11] * array9[12] + c10[n241 + 10] * array9[13] + c10[n241 + 9] * array9[14] + c10[n241 + 8] * array9[15]) * this.g;
                    n241 += 16;
                }
                break;
            }
            case 8: {
                final float[] c11 = this.c;
                final float[] i7 = this.i;
                int n243 = 0;
                for (int n244 = 0; n244 < 32; ++n244) {
                    final float[] array10 = jaco.mp3.a.y.P[n244];
                    i7[n244] = (c11[n243 + 8] * array10[0] + c11[n243 + 7] * array10[1] + c11[n243 + 6] * array10[2] + c11[n243 + 5] * array10[3] + c11[n243 + 4] * array10[4] + c11[n243 + 3] * array10[5] + c11[n243 + 2] * array10[6] + c11[n243 + 1] * array10[7] + c11[n243 + 0] * array10[8] + c11[n243 + 15] * array10[9] + c11[n243 + 14] * array10[10] + c11[n243 + 13] * array10[11] + c11[n243 + 12] * array10[12] + c11[n243 + 11] * array10[13] + c11[n243 + 10] * array10[14] + c11[n243 + 9] * array10[15]) * this.g;
                    n243 += 16;
                }
                break;
            }
            case 9: {
                final float[] c12 = this.c;
                final float[] i8 = this.i;
                int n245 = 0;
                for (int n246 = 0; n246 < 32; ++n246) {
                    final float[] array11 = jaco.mp3.a.y.P[n246];
                    i8[n246] = (c12[n245 + 9] * array11[0] + c12[n245 + 8] * array11[1] + c12[n245 + 7] * array11[2] + c12[n245 + 6] * array11[3] + c12[n245 + 5] * array11[4] + c12[n245 + 4] * array11[5] + c12[n245 + 3] * array11[6] + c12[n245 + 2] * array11[7] + c12[n245 + 1] * array11[8] + c12[n245 + 0] * array11[9] + c12[n245 + 15] * array11[10] + c12[n245 + 14] * array11[11] + c12[n245 + 13] * array11[12] + c12[n245 + 12] * array11[13] + c12[n245 + 11] * array11[14] + c12[n245 + 10] * array11[15]) * this.g;
                    n245 += 16;
                }
                break;
            }
            case 10: {
                final float[] c13 = this.c;
                final float[] i9 = this.i;
                int n247 = 0;
                for (int n248 = 0; n248 < 32; ++n248) {
                    final float[] array12 = jaco.mp3.a.y.P[n248];
                    i9[n248] = (c13[n247 + 10] * array12[0] + c13[n247 + 9] * array12[1] + c13[n247 + 8] * array12[2] + c13[n247 + 7] * array12[3] + c13[n247 + 6] * array12[4] + c13[n247 + 5] * array12[5] + c13[n247 + 4] * array12[6] + c13[n247 + 3] * array12[7] + c13[n247 + 2] * array12[8] + c13[n247 + 1] * array12[9] + c13[n247 + 0] * array12[10] + c13[n247 + 15] * array12[11] + c13[n247 + 14] * array12[12] + c13[n247 + 13] * array12[13] + c13[n247 + 12] * array12[14] + c13[n247 + 11] * array12[15]) * this.g;
                    n247 += 16;
                }
                break;
            }
            case 11: {
                final float[] c14 = this.c;
                final float[] i10 = this.i;
                int n249 = 0;
                for (int n250 = 0; n250 < 32; ++n250) {
                    final float[] array13 = jaco.mp3.a.y.P[n250];
                    i10[n250] = (c14[n249 + 11] * array13[0] + c14[n249 + 10] * array13[1] + c14[n249 + 9] * array13[2] + c14[n249 + 8] * array13[3] + c14[n249 + 7] * array13[4] + c14[n249 + 6] * array13[5] + c14[n249 + 5] * array13[6] + c14[n249 + 4] * array13[7] + c14[n249 + 3] * array13[8] + c14[n249 + 2] * array13[9] + c14[n249 + 1] * array13[10] + c14[n249 + 0] * array13[11] + c14[n249 + 15] * array13[12] + c14[n249 + 14] * array13[13] + c14[n249 + 13] * array13[14] + c14[n249 + 12] * array13[15]) * this.g;
                    n249 += 16;
                }
                break;
            }
            case 12: {
                final float[] c15 = this.c;
                final float[] i11 = this.i;
                int n251 = 0;
                for (int n252 = 0; n252 < 32; ++n252) {
                    final float[] array14 = jaco.mp3.a.y.P[n252];
                    i11[n252] = (c15[n251 + 12] * array14[0] + c15[n251 + 11] * array14[1] + c15[n251 + 10] * array14[2] + c15[n251 + 9] * array14[3] + c15[n251 + 8] * array14[4] + c15[n251 + 7] * array14[5] + c15[n251 + 6] * array14[6] + c15[n251 + 5] * array14[7] + c15[n251 + 4] * array14[8] + c15[n251 + 3] * array14[9] + c15[n251 + 2] * array14[10] + c15[n251 + 1] * array14[11] + c15[n251 + 0] * array14[12] + c15[n251 + 15] * array14[13] + c15[n251 + 14] * array14[14] + c15[n251 + 13] * array14[15]) * this.g;
                    n251 += 16;
                }
                break;
            }
            case 13: {
                final float[] c16 = this.c;
                final float[] i12 = this.i;
                int n253 = 0;
                for (int n254 = 0; n254 < 32; ++n254) {
                    final float[] array15 = jaco.mp3.a.y.P[n254];
                    i12[n254] = (c16[n253 + 13] * array15[0] + c16[n253 + 12] * array15[1] + c16[n253 + 11] * array15[2] + c16[n253 + 10] * array15[3] + c16[n253 + 9] * array15[4] + c16[n253 + 8] * array15[5] + c16[n253 + 7] * array15[6] + c16[n253 + 6] * array15[7] + c16[n253 + 5] * array15[8] + c16[n253 + 4] * array15[9] + c16[n253 + 3] * array15[10] + c16[n253 + 2] * array15[11] + c16[n253 + 1] * array15[12] + c16[n253 + 0] * array15[13] + c16[n253 + 15] * array15[14] + c16[n253 + 14] * array15[15]) * this.g;
                    n253 += 16;
                }
                break;
            }
            case 14: {
                final float[] c17 = this.c;
                final float[] i13 = this.i;
                int n255 = 0;
                for (int n256 = 0; n256 < 32; ++n256) {
                    final float[] array16 = jaco.mp3.a.y.P[n256];
                    i13[n256] = (c17[n255 + 14] * array16[0] + c17[n255 + 13] * array16[1] + c17[n255 + 12] * array16[2] + c17[n255 + 11] * array16[3] + c17[n255 + 10] * array16[4] + c17[n255 + 9] * array16[5] + c17[n255 + 8] * array16[6] + c17[n255 + 7] * array16[7] + c17[n255 + 6] * array16[8] + c17[n255 + 5] * array16[9] + c17[n255 + 4] * array16[10] + c17[n255 + 3] * array16[11] + c17[n255 + 2] * array16[12] + c17[n255 + 1] * array16[13] + c17[n255 + 0] * array16[14] + c17[n255 + 15] * array16[15]) * this.g;
                    n255 += 16;
                }
                break;
            }
            case 15: {
                final float[] c18 = this.c;
                final float[] i14 = this.i;
                int n257 = 0;
                for (int n258 = 0; n258 < 32; ++n258) {
                    final float[] array17 = jaco.mp3.a.y.P[n258];
                    i14[n258] = (c18[n257 + 15] * array17[0] + c18[n257 + 14] * array17[1] + c18[n257 + 13] * array17[2] + c18[n257 + 12] * array17[3] + c18[n257 + 11] * array17[4] + c18[n257 + 10] * array17[5] + c18[n257 + 9] * array17[6] + c18[n257 + 8] * array17[7] + c18[n257 + 7] * array17[8] + c18[n257 + 6] * array17[9] + c18[n257 + 5] * array17[10] + c18[n257 + 4] * array17[11] + c18[n257 + 3] * array17[12] + c18[n257 + 2] * array17[13] + c18[n257 + 1] * array17[14] + c18[n257 + 0] * array17[15]) * this.g;
                    n257 += 16;
                }
                break;
            }
        }
        if (c != null) {
            c.a(this.f, this.i);
        }
        this.d = (this.d + 1 & 0xF);
        this.c = ((this.c == this.a) ? this.b : this.a);
        for (int n259 = 0; n259 < 32; ++n259) {
            this.e[n259] = 0.0f;
        }
    }
    
    private static float[] b() {
        final String[] split;
        final float[] array = new float[(split = "0,-0.000442504999227821826934814453125,0.0032501220703125,-0.0070037841796875,0.0310821533203125,-0.0786285400390625,0.100311279296875,-0.5720367431640625,1.144989013671875,0.5720367431640625,0.100311279296875,0.0786285400390625,0.0310821533203125,0.0070037841796875,0.0032501220703125,0.000442504999227821826934814453125,-0.0000152590000652708113193511962890625,-0.0004730219952762126922607421875,0.003326416015625,-0.007919312454760074615478515625,0.030517578125,-0.0841827392578125,0.0909271240234375,-0.6002197265625,1.144287109375,0.5438232421875,0.108856201171875,0.07305908203125,0.0314788818359375,0.0061187739484012126922607421875,0.00317382789216935634613037109375,0.00039672901039011776447296142578125,-0.0000152590000652708113193511962890625,-0.0005340580246411263942718505859375,0.00338745093904435634613037109375,-0.0088653564453125,0.02978515625,-0.0897064208984375,0.0806884765625,-0.6282958984375,1.1422119140625,0.5156097412109375,0.1165771484375,0.0675201416015625,0.03173828125,0.0052947998046875,0.0030822749249637126922607421875,0.0003662109957076609134674072265625,-0.0000152590000652708113193511962890625,-0.000579833984375,0.0034332280047237873077392578125,-0.0098419189453125,0.0288848876953125,-0.0951690673828125,0.0695953369140625,-0.656219482421875,1.138763427734375,0.4874725341796875,0.12347412109375,0.0619964599609375,0.0318450927734375,0.004486083984375,0.00299072288908064365386962890625,0.00032043500686995685100555419921875,-0.0000152590000652708113193511962890625,-0.000625610002316534519195556640625,0.00346374488435685634613037109375,-0.0108489990234375,0.027801513671875,-0.1005401611328125,0.0576171875,-0.6839141845703125,1.1339263916015625,0.45947265625,0.12957763671875,0.0565338134765625,0.0318145751953125,0.0037231449969112873077392578125,0.002899169921875,0.0002899169921875,-0.0000152590000652708113193511962890625,-0.0006866459734737873077392578125,0.00347900390625,-0.0118865966796875,0.0265350341796875,-0.1058197021484375,0.0447845458984375,-0.7113189697265625,1.12774658203125,0.4316558837890625,0.1348876953125,0.0511322021484375,0.0316619873046875,0.0030059809796512126922607421875,0.0027923579327762126922607421875,0.0002593990066088736057281494140625,-0.0000152590000652708113193511962890625,-0.000747681013308465480804443359375,0.00347900390625,-0.012939453125,0.02508544921875,-0.1109466552734375,0.0310821533203125,-0.738372802734375,1.1202239990234375,0.404083251953125,0.1394500732421875,0.04583740234375,0.0313873291015625,0.00233459495939314365386962890625,0.00268554710783064365386962890625,0.00024414100334979593753814697265625,-0.000030518000130541622638702392578125,-0.0008087159949354827404022216796875,0.00346374488435685634613037109375,-0.0140228271484375,0.0234222412109375,-0.1159210205078125,0.016510009765625,-0.7650299072265625,1.1113739013671875,0.376800537109375,0.1432647705078125,0.0406341552734375,0.031005859375,0.0016937260515987873077392578125,0.0025787348859012126922607421875,0.000213623003219254314899444580078125,-0.000030518000130541622638702392578125,-0.00088500999845564365386962890625,0.00341796898283064365386962890625,-0.0151214599609375,0.021575927734375,-0.120697021484375,0.00106811500154435634613037109375,-0.7912139892578125,1.1012115478515625,0.3498687744140625,0.1463623046875,0.035552978515625,0.0305328369140625,0.00109863304533064365386962890625,0.0024566650390625,0.00019836399587802588939666748046875,-0.000030518000130541622638702392578125,-0.0009613040019758045673370361328125,0.0033721919171512126922607421875,-0.0162353515625,0.01953125,-0.1252593994140625,-0.015228270553052425384521484375,-0.816864013671875,1.08978271484375,0.3233184814453125,0.148773193359375,0.030609130859375,0.029937744140625,0.0005493159987963736057281494140625,0.0023498539812862873077392578125,0.00016784699982963502407073974609375,-0.000030518000130541622638702392578125,-0.001037598005495965480804443359375,0.0032806401140987873077392578125,-0.0173492431640625,0.0172576904296875,-0.1295623779296875,-0.032379150390625,-0.841949462890625,1.077117919921875,0.297210693359375,0.1504974365234375,0.02581787109375,0.0292816162109375,0.000030518000130541622638702392578125,0.0022430419921875,0.000152588007040321826934814453125,-0.00004577599975164048373699188232421875,-0.001113891950808465480804443359375,0.00317382789216935634613037109375,-0.018463134765625,0.014801025390625,-0.1335906982421875,-0.05035400390625,-0.866363525390625,1.0632171630859375,0.2715911865234375,0.1515960693359375,0.02117919921875,0.028533935546875,-0.000442504999227821826934814453125,0.00212097191251814365386962890625,0.000137328999699093401432037353515625,-0.00004577599975164048373699188232421875,-0.001205443986691534519195556640625,0.00305175804533064365386962890625,-0.0195770263671875,0.012115479446947574615478515625,-0.137298583984375,-0.0691680908203125,-0.8900909423828125,1.04815673828125,0.2465057373046875,0.152069091796875,0.0167083740234375,0.0277252197265625,-0.0008697509765625,0.00201415992341935634613037109375,0.0001220699996338225901126861572265625,-0.00006103499981691129505634307861328125,-0.001296996953897178173065185546875,0.00288391089998185634613037109375,-0.02069091796875,0.0092315673828125,-0.1406707763671875,-0.088775634765625,-0.913055419921875,1.0319366455078125,0.22198486328125,0.1519622802734375,0.012420654296875,0.0268402099609375,-0.0012664790265262126922607421875,0.001907348982058465480804443359375,0.0001068119963747449219226837158203125,-0.00006103499981691129505634307861328125,-0.00138855003751814365386962890625,0.00270080589689314365386962890625,-0.02178955078125,0.006134033203125,-0.1436767578125,-0.109161376953125,-0.9351959228515625,1.014617919921875,0.19805908203125,0.15130615234375,0.0083160400390625,0.025909423828125,-0.001617431989870965480804443359375,0.001785277971066534519195556640625,0.0001068119963747449219226837158203125,-0.0000762940035201609134674072265625,-0.0014801030047237873077392578125,0.0024871830828487873077392578125,-0.022857666015625,0.0028228759765625,-0.1462554931640625,-0.13031005859375,-0.95648193359375,0.996246337890625,0.1747894287109375,0.150115966796875,0.0043945307843387126922607421875,0.024932861328125,-0.00193786597810685634613037109375,0.0016937260515987873077392578125,0.0000915530035854317247867584228515625,-0.0000762940035201609134674072265625,-0.001586913946084678173065185546875,0.00222778297029435634613037109375,-0.0239105224609375,-0.0006866459734737873077392578125,-0.1484222412109375,-0.1522064208984375,-0.9768524169921875,0.9768524169921875,0.1522064208984375,0.1484222412109375,0.0006866459734737873077392578125,0.0239105224609375,-0.00222778297029435634613037109375,0.001586913946084678173065185546875,0.0000762940035201609134674072265625,-0.0000915530035854317247867584228515625,-0.0016937260515987873077392578125,0.00193786597810685634613037109375,-0.024932861328125,-0.0043945307843387126922607421875,-0.150115966796875,-0.1747894287109375,-0.996246337890625,0.95648193359375,0.13031005859375,0.1462554931640625,-0.0028228759765625,0.022857666015625,-0.0024871830828487873077392578125,0.0014801030047237873077392578125,0.0000762940035201609134674072265625,-0.0001068119963747449219226837158203125,-0.001785277971066534519195556640625,0.001617431989870965480804443359375,-0.025909423828125,-0.0083160400390625,-0.15130615234375,-0.19805908203125,-1.014617919921875,0.9351959228515625,0.109161376953125,0.1436767578125,-0.006134033203125,0.02178955078125,-0.00270080589689314365386962890625,0.00138855003751814365386962890625,0.00006103499981691129505634307861328125,-0.0001068119963747449219226837158203125,-0.001907348982058465480804443359375,0.0012664790265262126922607421875,-0.0268402099609375,-0.012420654296875,-0.1519622802734375,-0.22198486328125,-1.0319366455078125,0.913055419921875,0.088775634765625,0.1406707763671875,-0.0092315673828125,0.02069091796875,-0.00288391089998185634613037109375,0.001296996953897178173065185546875,0.00006103499981691129505634307861328125,-0.0001220699996338225901126861572265625,-0.00201415992341935634613037109375,0.0008697509765625,-0.0277252197265625,-0.0167083740234375,-0.152069091796875,-0.2465057373046875,-1.04815673828125,0.8900909423828125,0.0691680908203125,0.137298583984375,-0.012115479446947574615478515625,0.0195770263671875,-0.00305175804533064365386962890625,0.001205443986691534519195556640625,0.00004577599975164048373699188232421875,-0.000137328999699093401432037353515625,-0.00212097191251814365386962890625,0.000442504999227821826934814453125,-0.028533935546875,-0.02117919921875,-0.1515960693359375,-0.2715911865234375,-1.0632171630859375,0.866363525390625,0.05035400390625,0.1335906982421875,-0.014801025390625,0.018463134765625,-0.00317382789216935634613037109375,0.001113891950808465480804443359375,0.00004577599975164048373699188232421875,-0.000152588007040321826934814453125,-0.0022430419921875,-0.000030518000130541622638702392578125,-0.0292816162109375,-0.02581787109375,-0.1504974365234375,-0.297210693359375,-1.077117919921875,0.841949462890625,0.032379150390625,0.1295623779296875,-0.0172576904296875,0.0173492431640625,-0.0032806401140987873077392578125,0.001037598005495965480804443359375,0.000030518000130541622638702392578125,-0.00016784699982963502407073974609375,-0.0023498539812862873077392578125,-0.0005493159987963736057281494140625,-0.029937744140625,-0.030609130859375,-0.148773193359375,-0.3233184814453125,-1.08978271484375,0.816864013671875,0.015228270553052425384521484375,0.1252593994140625,-0.01953125,0.0162353515625,-0.0033721919171512126922607421875,0.0009613040019758045673370361328125,0.000030518000130541622638702392578125,-0.00019836399587802588939666748046875,-0.0024566650390625,-0.00109863304533064365386962890625,-0.0305328369140625,-0.035552978515625,-0.1463623046875,-0.3498687744140625,-1.1012115478515625,0.7912139892578125,-0.00106811500154435634613037109375,0.120697021484375,-0.021575927734375,0.0151214599609375,-0.00341796898283064365386962890625,0.00088500999845564365386962890625,0.000030518000130541622638702392578125,-0.000213623003219254314899444580078125,-0.0025787348859012126922607421875,-0.0016937260515987873077392578125,-0.031005859375,-0.0406341552734375,-0.1432647705078125,-0.376800537109375,-1.1113739013671875,0.7650299072265625,-0.016510009765625,0.1159210205078125,-0.0234222412109375,0.0140228271484375,-0.00346374488435685634613037109375,0.0008087159949354827404022216796875,0.000030518000130541622638702392578125,-0.00024414100334979593753814697265625,-0.00268554710783064365386962890625,-0.00233459495939314365386962890625,-0.0313873291015625,-0.04583740234375,-0.1394500732421875,-0.404083251953125,-1.1202239990234375,0.738372802734375,-0.0310821533203125,0.1109466552734375,-0.02508544921875,0.012939453125,-0.00347900390625,0.000747681013308465480804443359375,0.0000152590000652708113193511962890625,-0.0002593990066088736057281494140625,-0.0027923579327762126922607421875,-0.0030059809796512126922607421875,-0.0316619873046875,-0.0511322021484375,-0.1348876953125,-0.4316558837890625,-1.12774658203125,0.7113189697265625,-0.0447845458984375,0.1058197021484375,-0.0265350341796875,0.0118865966796875,-0.00347900390625,0.0006866459734737873077392578125,0.0000152590000652708113193511962890625,-0.0002899169921875,-0.002899169921875,-0.0037231449969112873077392578125,-0.0318145751953125,-0.0565338134765625,-0.12957763671875,-0.45947265625,-1.1339263916015625,0.6839141845703125,-0.0576171875,0.1005401611328125,-0.027801513671875,0.0108489990234375,-0.00346374488435685634613037109375,0.000625610002316534519195556640625,0.0000152590000652708113193511962890625,-0.00032043500686995685100555419921875,-0.00299072288908064365386962890625,-0.004486083984375,-0.0318450927734375,-0.0619964599609375,-0.12347412109375,-0.4874725341796875,-1.138763427734375,0.656219482421875,-0.0695953369140625,0.0951690673828125,-0.0288848876953125,0.0098419189453125,-0.0034332280047237873077392578125,0.000579833984375,0.0000152590000652708113193511962890625,-0.0003662109957076609134674072265625,-0.0030822749249637126922607421875,-0.0052947998046875,-0.03173828125,-0.0675201416015625,-0.1165771484375,-0.5156097412109375,-1.1422119140625,0.6282958984375,-0.0806884765625,0.0897064208984375,-0.02978515625,0.0088653564453125,-0.00338745093904435634613037109375,0.0005340580246411263942718505859375,0.0000152590000652708113193511962890625,-0.00039672901039011776447296142578125,-0.00317382789216935634613037109375,-0.0061187739484012126922607421875,-0.0314788818359375,-0.07305908203125,-0.108856201171875,-0.5438232421875,-1.144287109375,0.6002197265625,-0.0909271240234375,0.0841827392578125,-0.030517578125,0.007919312454760074615478515625,-0.003326416015625,0.0004730219952762126922607421875,0.0000152590000652708113193511962890625".split(",")).length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = new BigDecimal(split[i]).floatValue();
        }
        return array;
    }
    
    private static float[][] a(final float[] array, int n) {
        final float[][] array2 = new float[n = array.length / 16][];
        for (int i = 0; i < n; ++i) {
            final float[][] array3 = array2;
            final int n2 = i;
            final int n3 = i << 4;
            int n4 = 16;
            final int n5 = n3;
            if (n5 + n4 > array.length) {
                n4 = array.length - n5;
            }
            if (n4 < 0) {
                n4 = 0;
            }
            final float[] array4 = new float[n4];
            for (int j = 0; j < n4; ++j) {
                array4[j] = array[n5 + j];
            }
            array3[n2] = array4;
        }
        return array2;
    }
}
