package jaco.mp3.a;

final class c implements k
{
    private int[] a;
    private int b;
    private int[] c;
    private float[][][] d;
    private float[][][] e;
    private float[] f;
    private float[][] g;
    private float[][] h;
    private int[] i;
    private z j;
    private D k;
    private y l;
    private y m;
    private C n;
    private int o;
    private h p;
    private d q;
    private l[] r;
    private l[] s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;
    private float[] A;
    private float[] B;
    private final int[] C;
    private int[] D;
    private int[] E;
    private int[] F;
    private int[] G;
    private int[] H;
    private float[] I;
    private float[] J;
    private float[] K;
    private int L;
    private static final int[][] M;
    private static int[] N;
    private e[] O;
    private static float[] P;
    private static float[] Q;
    private static float[][] R;
    private static float[] S;
    private static int[][] T;
    private static final float[] U;
    private static final float[] V;
    private static float[][] W;
    private static int[][][] X;
    
    static {
        M = new int[][] { { 0, 0, 0, 0, 3, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4 }, { 0, 1, 2, 3, 0, 1, 2, 3, 1, 2, 3, 1, 2, 3, 2, 3 } };
        c.N = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 3, 3, 3, 2, 0 };
        c.P = new float[] { 1.0f, 0.70710677f, 0.5f, 0.35355338f, 0.25f, 0.17677669f, 0.125f, 0.088388346f, 0.0625f, 0.044194173f, 0.03125f, 0.022097087f, 0.015625f, 0.011048543f, 0.0078125f, 0.0055242716f, 0.00390625f, 0.0027621358f, 0.001953125f, 0.0013810679f, 9.765625E-4f, 6.9053395E-4f, 4.8828125E-4f, 3.4526698E-4f, 2.4414062E-4f, 1.7263349E-4f, 1.2207031E-4f, 8.6316744E-5f, 6.1035156E-5f, 4.3158372E-5f, 3.0517578E-5f, 2.1579186E-5f, 1.5258789E-5f, 1.0789593E-5f, 7.6293945E-6f, 5.3947965E-6f, 3.8146973E-6f, 2.6973983E-6f, 1.9073486E-6f, 1.3486991E-6f, 9.536743E-7f, 6.7434956E-7f, 4.7683716E-7f, 3.3717478E-7f, 2.3841858E-7f, 1.6858739E-7f, 1.1920929E-7f, 8.4293696E-8f, 5.9604645E-8f, 4.2146848E-8f, 2.9802322E-8f, 2.1073424E-8f, 1.4901161E-8f, 1.0536712E-8f, 7.4505806E-9f, 5.268356E-9f, 3.7252903E-9f, 2.634178E-9f, 1.8626451E-9f, 1.317089E-9f, 9.313226E-10f, 6.585445E-10f, 4.656613E-10f, 3.2927225E-10f };
        final float[] q = new float[8192];
        for (int i = 0; i < 8192; ++i) {
            q[i] = (float)Math.pow(i, 1.3333333333333333);
        }
        c.Q = q;
        c.R = new float[][] { { 1.0f, 0.8408964f, 0.70710677f, 0.59460354f, 0.5f, 0.4204482f, 0.35355338f, 0.29730177f, 0.25f, 0.2102241f, 0.17677669f, 0.14865088f, 0.125f, 0.10511205f, 0.088388346f, 0.07432544f, 0.0625f, 0.052556027f, 0.044194173f, 0.03716272f, 0.03125f, 0.026278013f, 0.022097087f, 0.01858136f, 0.015625f, 0.013139007f, 0.011048543f, 0.00929068f, 0.0078125f, 0.0065695033f, 0.0055242716f, 0.00464534f }, { 1.0f, 0.70710677f, 0.5f, 0.35355338f, 0.25f, 0.17677669f, 0.125f, 0.088388346f, 0.0625f, 0.044194173f, 0.03125f, 0.022097087f, 0.015625f, 0.011048543f, 0.0078125f, 0.0055242716f, 0.00390625f, 0.0027621358f, 0.001953125f, 0.0013810679f, 9.765625E-4f, 6.9053395E-4f, 4.8828125E-4f, 3.4526698E-4f, 2.4414062E-4f, 1.7263349E-4f, 1.2207031E-4f, 8.6316744E-5f, 6.1035156E-5f, 4.3158372E-5f, 3.0517578E-5f, 2.1579186E-5f } };
        c.S = new float[] { 0.0f, 0.2679492f, 0.57735026f, 1.0f, 1.7320508f, 3.732051f, 9.9999998E10f, -3.732051f, -1.7320508f, -1.0f, -0.57735026f, -0.2679492f, 0.0f, 0.2679492f, 0.57735026f, 1.0f };
        U = new float[] { 0.8574929f, 0.881742f, 0.94962865f, 0.9833146f, 0.9955178f, 0.9991606f, 0.9998992f, 0.99999315f };
        V = new float[] { -0.51449573f, -0.47173196f, -0.31337744f, -0.1819132f, -0.09457419f, -0.040965583f, -0.014198569f, -0.0036999746f };
        c.W = new float[][] { { -0.016141215f, -0.05360318f, -0.100707136f, -0.16280818f, -0.5f, -0.38388735f, -0.6206114f, -1.1659756f, -3.8720753f, -4.225629f, -1.519529f, -0.97416484f, -0.73744076f, -1.2071068f, -0.5163616f, -0.45426053f, -0.40715656f, -0.3696946f, -0.3387627f, -0.31242222f, -0.28939587f, -0.26880082f, -0.5f, -0.23251417f, -0.21596715f, -0.20004979f, -0.18449493f, -0.16905846f, -0.15350361f, -0.13758625f, -0.12103922f, -0.20710678f, -0.084752575f, -0.06415752f, -0.041131172f, -0.014790705f }, { -0.016141215f, -0.05360318f, -0.100707136f, -0.16280818f, -0.5f, -0.38388735f, -0.6206114f, -1.1659756f, -3.8720753f, -4.225629f, -1.519529f, -0.97416484f, -0.73744076f, -1.2071068f, -0.5163616f, -0.45426053f, -0.40715656f, -0.3696946f, -0.33908543f, -0.3151181f, -0.29642227f, -0.28184548f, -0.5411961f, -0.2621323f, -0.25387916f, -0.2329629f, -0.19852729f, -0.15233535f, -0.0964964f, -0.03342383f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f }, { -0.0483008f, -0.15715657f, -0.28325045f, -0.42953748f, -1.2071068f, -0.8242648f, -1.1451749f, -1.769529f, -4.5470223f, -3.489053f, -0.7329629f, -0.15076515f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f }, { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.15076514f, -0.7329629f, -3.489053f, -4.5470223f, -1.769529f, -1.1451749f, -0.8313774f, -1.306563f, -0.54142016f, -0.46528974f, -0.4106699f, -0.3700468f, -0.3387627f, -0.31242222f, -0.28939587f, -0.26880082f, -0.5f, -0.23251417f, -0.21596715f, -0.20004979f, -0.18449493f, -0.16905846f, -0.15350361f, -0.13758625f, -0.12103922f, -0.20710678f, -0.084752575f, -0.06415752f, -0.041131172f, -0.014790705f } };
        c.X = new int[][][] { { { 6, 5, 5, 5 }, { 9, 9, 9, 9 }, { 6, 9, 9, 9 } }, { { 6, 5, 7, 3 }, { 9, 9, 12, 6 }, { 6, 9, 12, 6 } }, { { 11, 10, 0, 0 }, { 18, 18, 0, 0 }, { 15, 18, 0, 0 } }, { { 7, 7, 7, 0 }, { 12, 12, 12, 0 }, { 6, 15, 12, 0 } }, { { 6, 6, 6, 3 }, { 12, 9, 9, 6 }, { 6, 12, 9, 6 } }, { { 8, 8, 5, 0 }, { 15, 12, 9, 0 }, { 6, 18, 9, 0 } } };
    }
    
    public c(final z j, final D k, final y l, final y m, final C n, int i) {
        this.b = 0;
        this.A = new float[32];
        this.B = new float[32];
        this.C = new int[4];
        this.D = new int[1];
        this.E = new int[1];
        this.F = new int[1];
        this.G = new int[1];
        this.H = new int[576];
        this.I = new float[576];
        this.J = new float[18];
        this.K = new float[36];
        this.L = 0;
        jaco.mp3.a.m.a();
        this.c = new int[580];
        this.d = new float[2][32][18];
        this.e = new float[2][32][18];
        this.f = new float[576];
        this.g = new float[2][576];
        this.h = new float[2][576];
        this.i = new int[2];
        (this.r = new l[2])[0] = new l();
        this.r[1] = new l();
        this.s = this.r;
        this.O = new e[9];
        final int[] array = { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464, 522, 576 };
        final int[] array2 = { 0, 4, 8, 12, 18, 24, 32, 42, 56, 74, 100, 132, 174, 192 };
        final int[] array3 = { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 114, 136, 162, 194, 232, 278, 330, 394, 464, 540, 576 };
        final int[] array4 = { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 136, 180, 192 };
        final int[] array5 = { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464, 522, 576 };
        final int[] array6 = { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 134, 174, 192 };
        final int[] array7 = { 0, 4, 8, 12, 16, 20, 24, 30, 36, 44, 52, 62, 74, 90, 110, 134, 162, 196, 238, 288, 342, 418, 576 };
        final int[] array8 = { 0, 4, 8, 12, 16, 22, 30, 40, 52, 66, 84, 106, 136, 192 };
        final int[] array9 = { 0, 4, 8, 12, 16, 20, 24, 30, 36, 42, 50, 60, 72, 88, 106, 128, 156, 190, 230, 276, 330, 384, 576 };
        final int[] array10 = { 0, 4, 8, 12, 16, 22, 28, 38, 50, 64, 80, 100, 126, 192 };
        final int[] array11 = { 0, 4, 8, 12, 16, 20, 24, 30, 36, 44, 54, 66, 82, 102, 126, 156, 194, 240, 296, 364, 448, 550, 576 };
        final int[] array12 = { 0, 4, 8, 12, 16, 22, 30, 42, 58, 78, 104, 138, 180, 192 };
        final int[] array13 = { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464, 522, 576 };
        final int[] array14 = { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 134, 174, 192 };
        final int[] array15 = { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464, 522, 576 };
        final int[] array16 = { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 134, 174, 192 };
        final int[] array17 = { 0, 12, 24, 36, 48, 60, 72, 88, 108, 132, 160, 192, 232, 280, 336, 400, 476, 566, 568, 570, 572, 574, 576 };
        final int[] array18 = { 0, 8, 16, 24, 36, 52, 72, 96, 124, 160, 162, 164, 166, 192 };
        this.O[0] = new e(array, array2);
        this.O[1] = new e(array3, array4);
        this.O[2] = new e(array5, array6);
        this.O[3] = new e(array7, array8);
        this.O[4] = new e(array9, array10);
        this.O[5] = new e(array11, array12);
        this.O[6] = new e(array13, array14);
        this.O[7] = new e(array15, array16);
        this.O[8] = new e(array17, array18);
        if (jaco.mp3.a.c.T == null) {
            jaco.mp3.a.c.T = new int[9][];
            for (i = 0; i < 9; ++i) {
                jaco.mp3.a.c.T[i] = a(this.O[i].b);
            }
        }
        new j(this, new int[] { 0, 6, 11, 16, 21 }, new int[] { 0, 6, 12 });
        this.a = new int[54];
        this.j = j;
        this.k = k;
        this.l = l;
        this.m = m;
        this.n = n;
        this.o = 0;
        this.u = 0;
        this.w = ((this.k.f() == 3) ? 1 : 2);
        this.t = ((this.k.a() == 1) ? 2 : 1);
        this.z = this.k.d() + ((this.k.a() == 1) ? 3 : ((this.k.a() == 2) ? 6 : 0));
        if (this.w == 2) {
            switch (this.o) {
                case 1:
                case 3: {
                    final int n2 = 0;
                    this.y = n2;
                    this.x = n2;
                    break;
                }
                case 2: {
                    final int n3 = 1;
                    this.y = n3;
                    this.x = n3;
                    break;
                }
                default: {
                    this.x = 0;
                    this.y = 1;
                    break;
                }
            }
        }
        else {
            final int n4 = 0;
            this.y = n4;
            this.x = n4;
        }
        for (int n5 = 0; n5 < 2; ++n5) {
            for (int n6 = 0; n6 < 576; ++n6) {
                this.g[n5][n6] = 0.0f;
            }
        }
        this.i[0] = (this.i[1] = 576);
        this.p = new h();
        this.q = new d();
    }
    
    @Override
    public final void a() {
        final int h = (this = this).k.h();
        Label_1746: {
            final c c;
            if ((c = this).k.a() == 1) {
                c.q.a = c.j.d(9);
                if (c.w == 1) {
                    c.j.d(5);
                }
                else {
                    c.j.d(3);
                }
                for (int i = 0; i < c.w; ++i) {
                    c.q.b[i].a[0] = c.j.d(1);
                    c.q.b[i].a[1] = c.j.d(1);
                    c.q.b[i].a[2] = c.j.d(1);
                    c.q.b[i].a[3] = c.j.d(1);
                }
                for (int j = 0; j < 2; ++j) {
                    for (int k = 0; k < c.w; ++k) {
                        c.q.b[k].b[j].a = c.j.d(12);
                        c.q.b[k].b[j].b = c.j.d(9);
                        c.q.b[k].b[j].c = c.j.d(8);
                        c.q.b[k].b[j].d = c.j.d(4);
                        c.q.b[k].b[j].e = c.j.d(1);
                        if (c.q.b[k].b[j].e != 0) {
                            c.q.b[k].b[j].f = c.j.d(2);
                            c.q.b[k].b[j].g = c.j.d(1);
                            c.q.b[k].b[j].h[0] = c.j.d(5);
                            c.q.b[k].b[j].h[1] = c.j.d(5);
                            c.q.b[k].b[j].i[0] = c.j.d(3);
                            c.q.b[k].b[j].i[1] = c.j.d(3);
                            c.q.b[k].b[j].i[2] = c.j.d(3);
                            if (c.q.b[k].b[j].f == 0) {
                                break Label_1746;
                            }
                            if (c.q.b[k].b[j].f == 2 && c.q.b[k].b[j].g == 0) {
                                c.q.b[k].b[j].j = 8;
                            }
                            else {
                                c.q.b[k].b[j].j = 7;
                            }
                            c.q.b[k].b[j].k = 20 - c.q.b[k].b[j].j;
                        }
                        else {
                            c.q.b[k].b[j].h[0] = c.j.d(5);
                            c.q.b[k].b[j].h[1] = c.j.d(5);
                            c.q.b[k].b[j].h[2] = c.j.d(5);
                            c.q.b[k].b[j].j = c.j.d(4);
                            c.q.b[k].b[j].k = c.j.d(3);
                            c.q.b[k].b[j].f = 0;
                        }
                        c.q.b[k].b[j].l = c.j.d(1);
                        c.q.b[k].b[j].m = c.j.d(1);
                        c.q.b[k].b[j].n = c.j.d(1);
                    }
                }
            }
            else {
                c.q.a = c.j.d(8);
                if (c.w == 1) {
                    c.j.d(1);
                }
                else {
                    c.j.d(2);
                }
                for (int l = 0; l < c.w; ++l) {
                    c.q.b[l].b[0].a = c.j.d(12);
                    c.q.b[l].b[0].b = c.j.d(9);
                    c.q.b[l].b[0].c = c.j.d(8);
                    c.q.b[l].b[0].d = c.j.d(9);
                    c.q.b[l].b[0].e = c.j.d(1);
                    if (c.q.b[l].b[0].e != 0) {
                        c.q.b[l].b[0].f = c.j.d(2);
                        c.q.b[l].b[0].g = c.j.d(1);
                        c.q.b[l].b[0].h[0] = c.j.d(5);
                        c.q.b[l].b[0].h[1] = c.j.d(5);
                        c.q.b[l].b[0].i[0] = c.j.d(3);
                        c.q.b[l].b[0].i[1] = c.j.d(3);
                        c.q.b[l].b[0].i[2] = c.j.d(3);
                        if (c.q.b[l].b[0].f == 0) {
                            break;
                        }
                        if (c.q.b[l].b[0].f == 2 && c.q.b[l].b[0].g == 0) {
                            c.q.b[l].b[0].j = 8;
                        }
                        else {
                            c.q.b[l].b[0].j = 7;
                            c.q.b[l].b[0].k = 20 - c.q.b[l].b[0].j;
                        }
                    }
                    else {
                        c.q.b[l].b[0].h[0] = c.j.d(5);
                        c.q.b[l].b[0].h[1] = c.j.d(5);
                        c.q.b[l].b[0].h[2] = c.j.d(5);
                        c.q.b[l].b[0].j = c.j.d(4);
                        c.q.b[l].b[0].k = c.j.d(3);
                        c.q.b[l].b[0].f = 0;
                    }
                    c.q.b[l].b[0].m = c.j.d(1);
                    c.q.b[l].b[0].n = c.j.d(1);
                }
            }
        }
        for (int n = 0; n < h; ++n) {
            this.p.b(this.j.d(8));
        }
        int n2 = this.p.a() >>> 3;
        final int n3;
        if ((n3 = (this.p.a() & 0x7)) != 0) {
            this.p.a(8 - n3);
            ++n2;
        }
        int n4 = this.u - n2 - this.q.a;
        final c c2 = this;
        c2.u += h;
        if (n4 >= 0) {
            if (n2 > 4096) {
                final c c3 = this;
                c3.u -= 4096;
                this.p.d(4096);
            }
            while (n4 > 0) {
                this.p.a(8);
                --n4;
            }
            for (int n5 = 0; n5 < this.t; ++n5) {
                for (int n6 = 0; n6 < this.w; ++n6) {
                    this.v = this.p.a();
                    if (this.k.a() == 1) {
                        final c c4 = this;
                        final int n7 = n6;
                        final int n8 = n5;
                        final int n9 = n7;
                        final c c5 = c4;
                        final n n10;
                        final int d = (n10 = c4.q.b[n9].b[n8]).d;
                        final int n11 = jaco.mp3.a.c.M[0][d];
                        final int n12 = jaco.mp3.a.c.M[1][d];
                        if (n10.e != 0 && n10.f == 2) {
                            if (n10.g != 0) {
                                for (int n13 = 0; n13 < 8; ++n13) {
                                    c5.s[n9].a[n13] = c5.p.a(jaco.mp3.a.c.M[0][n10.d]);
                                }
                                for (int n14 = 3; n14 < 6; ++n14) {
                                    for (int n15 = 0; n15 < 3; ++n15) {
                                        c5.s[n9].b[n15][n14] = c5.p.a(jaco.mp3.a.c.M[0][n10.d]);
                                    }
                                }
                                for (int n16 = 6; n16 < 12; ++n16) {
                                    for (int n17 = 0; n17 < 3; ++n17) {
                                        c5.s[n9].b[n17][n16] = c5.p.a(jaco.mp3.a.c.M[1][n10.d]);
                                    }
                                }
                                for (int n18 = 0; n18 < 3; ++n18) {
                                    c5.s[n9].b[n18][12] = 0;
                                }
                            }
                            else {
                                c5.s[n9].b[0][0] = c5.p.a(n11);
                                c5.s[n9].b[1][0] = c5.p.a(n11);
                                c5.s[n9].b[2][0] = c5.p.a(n11);
                                c5.s[n9].b[0][1] = c5.p.a(n11);
                                c5.s[n9].b[1][1] = c5.p.a(n11);
                                c5.s[n9].b[2][1] = c5.p.a(n11);
                                c5.s[n9].b[0][2] = c5.p.a(n11);
                                c5.s[n9].b[1][2] = c5.p.a(n11);
                                c5.s[n9].b[2][2] = c5.p.a(n11);
                                c5.s[n9].b[0][3] = c5.p.a(n11);
                                c5.s[n9].b[1][3] = c5.p.a(n11);
                                c5.s[n9].b[2][3] = c5.p.a(n11);
                                c5.s[n9].b[0][4] = c5.p.a(n11);
                                c5.s[n9].b[1][4] = c5.p.a(n11);
                                c5.s[n9].b[2][4] = c5.p.a(n11);
                                c5.s[n9].b[0][5] = c5.p.a(n11);
                                c5.s[n9].b[1][5] = c5.p.a(n11);
                                c5.s[n9].b[2][5] = c5.p.a(n11);
                                c5.s[n9].b[0][6] = c5.p.a(n12);
                                c5.s[n9].b[1][6] = c5.p.a(n12);
                                c5.s[n9].b[2][6] = c5.p.a(n12);
                                c5.s[n9].b[0][7] = c5.p.a(n12);
                                c5.s[n9].b[1][7] = c5.p.a(n12);
                                c5.s[n9].b[2][7] = c5.p.a(n12);
                                c5.s[n9].b[0][8] = c5.p.a(n12);
                                c5.s[n9].b[1][8] = c5.p.a(n12);
                                c5.s[n9].b[2][8] = c5.p.a(n12);
                                c5.s[n9].b[0][9] = c5.p.a(n12);
                                c5.s[n9].b[1][9] = c5.p.a(n12);
                                c5.s[n9].b[2][9] = c5.p.a(n12);
                                c5.s[n9].b[0][10] = c5.p.a(n12);
                                c5.s[n9].b[1][10] = c5.p.a(n12);
                                c5.s[n9].b[2][10] = c5.p.a(n12);
                                c5.s[n9].b[0][11] = c5.p.a(n12);
                                c5.s[n9].b[1][11] = c5.p.a(n12);
                                c5.s[n9].b[2][11] = c5.p.a(n12);
                                c5.s[n9].b[0][12] = 0;
                                c5.s[n9].b[1][12] = 0;
                                c5.s[n9].b[2][12] = 0;
                            }
                        }
                        else {
                            if (c5.q.b[n9].a[0] == 0 || n8 == 0) {
                                c5.s[n9].a[0] = c5.p.a(n11);
                                c5.s[n9].a[1] = c5.p.a(n11);
                                c5.s[n9].a[2] = c5.p.a(n11);
                                c5.s[n9].a[3] = c5.p.a(n11);
                                c5.s[n9].a[4] = c5.p.a(n11);
                                c5.s[n9].a[5] = c5.p.a(n11);
                            }
                            if (c5.q.b[n9].a[1] == 0 || n8 == 0) {
                                c5.s[n9].a[6] = c5.p.a(n11);
                                c5.s[n9].a[7] = c5.p.a(n11);
                                c5.s[n9].a[8] = c5.p.a(n11);
                                c5.s[n9].a[9] = c5.p.a(n11);
                                c5.s[n9].a[10] = c5.p.a(n11);
                            }
                            if (c5.q.b[n9].a[2] == 0 || n8 == 0) {
                                c5.s[n9].a[11] = c5.p.a(n12);
                                c5.s[n9].a[12] = c5.p.a(n12);
                                c5.s[n9].a[13] = c5.p.a(n12);
                                c5.s[n9].a[14] = c5.p.a(n12);
                                c5.s[n9].a[15] = c5.p.a(n12);
                            }
                            if (c5.q.b[n9].a[3] == 0 || n8 == 0) {
                                c5.s[n9].a[16] = c5.p.a(n12);
                                c5.s[n9].a[17] = c5.p.a(n12);
                                c5.s[n9].a[18] = c5.p.a(n12);
                                c5.s[n9].a[19] = c5.p.a(n12);
                                c5.s[n9].a[20] = c5.p.a(n12);
                            }
                            c5.s[n9].a[21] = 0;
                            c5.s[n9].a[22] = 0;
                        }
                    }
                    else {
                        final c c6 = this;
                        final int n19 = n6;
                        final int n20 = n5;
                        final int n21 = n19;
                        final c c7 = c6;
                        int n22 = 0;
                        final n n23 = c7.q.b[n21].b[n20];
                        final c c8 = c7;
                        final int n24 = n21;
                        final int n25 = n20;
                        final int n26 = n24;
                        final c c9 = c8;
                        final int m = c8.k.i();
                        int n27 = 0;
                        final n n28;
                        final int d2 = (n28 = c9.q.b[n26].b[n25]).d;
                        int n29;
                        if (n28.f == 2) {
                            if (n28.g == 0) {
                                n29 = 1;
                            }
                            else if (n28.g == 1) {
                                n29 = 2;
                            }
                            else {
                                n29 = 0;
                            }
                        }
                        else {
                            n29 = 0;
                        }
                        if ((m != 1 && m != 3) || n26 != 1) {
                            if (d2 < 400) {
                                c9.C[0] = (d2 >>> 4) / 5;
                                c9.C[1] = (d2 >>> 4) % 5;
                                c9.C[2] = (d2 & 0xF) >>> 2;
                                c9.C[3] = (d2 & 0x3);
                                c9.q.b[n26].b[n25].l = 0;
                                n27 = 0;
                            }
                            else if (d2 < 500) {
                                c9.C[0] = (d2 - 400 >>> 2) / 5;
                                c9.C[1] = (d2 - 400 >>> 2) % 5;
                                c9.C[2] = (d2 - 400 & 0x3);
                                c9.C[3] = 0;
                                c9.q.b[n26].b[n25].l = 0;
                                n27 = 1;
                            }
                            else if (d2 < 512) {
                                c9.C[0] = (d2 - 500) / 3;
                                c9.C[1] = (d2 - 500) % 3;
                                c9.C[2] = 0;
                                c9.C[3] = 0;
                                c9.q.b[n26].b[n25].l = 1;
                                n27 = 2;
                            }
                        }
                        if ((m == 1 || m == 3) && n26 == 1) {
                            final int n30;
                            if ((n30 = d2 >>> 1) < 180) {
                                c9.C[0] = n30 / 36;
                                c9.C[1] = n30 % 36 / 6;
                                c9.C[2] = n30 % 36 % 6;
                                c9.C[3] = 0;
                                c9.q.b[n26].b[n25].l = 0;
                                n27 = 3;
                            }
                            else if (n30 < 244) {
                                c9.C[0] = (n30 - 180 & 0x3F) >>> 4;
                                c9.C[1] = (n30 - 180 & 0xF) >>> 2;
                                c9.C[2] = (n30 - 180 & 0x3);
                                c9.C[3] = 0;
                                c9.q.b[n26].b[n25].l = 0;
                                n27 = 4;
                            }
                            else if (n30 < 255) {
                                c9.C[0] = (n30 - 244) / 3;
                                c9.C[1] = (n30 - 244) % 3;
                                c9.C[2] = 0;
                                c9.C[3] = 0;
                                c9.q.b[n26].b[n25].l = 0;
                                n27 = 5;
                            }
                        }
                        for (int n31 = 0; n31 < 45; ++n31) {
                            c9.a[n31] = 0;
                        }
                        int n32 = 0;
                        for (int n33 = 0; n33 < 4; ++n33) {
                            for (int n34 = 0; n34 < jaco.mp3.a.c.X[n27][n29][n33]; ++n34) {
                                c9.a[n32] = ((c9.C[n33] == 0) ? 0 : c9.p.a(c9.C[n33]));
                                ++n32;
                            }
                        }
                        if (n23.e != 0 && n23.f == 2) {
                            if (n23.g != 0) {
                                for (int n35 = 0; n35 < 8; ++n35) {
                                    c7.s[n21].a[n35] = c7.a[n22];
                                    ++n22;
                                }
                                for (int n36 = 3; n36 < 12; ++n36) {
                                    for (int n37 = 0; n37 < 3; ++n37) {
                                        c7.s[n21].b[n37][n36] = c7.a[n22];
                                        ++n22;
                                    }
                                }
                                for (int n38 = 0; n38 < 3; ++n38) {
                                    c7.s[n21].b[n38][12] = 0;
                                }
                            }
                            else {
                                for (int n39 = 0; n39 < 12; ++n39) {
                                    for (int n40 = 0; n40 < 3; ++n40) {
                                        c7.s[n21].b[n40][n39] = c7.a[n22];
                                        ++n22;
                                    }
                                }
                                for (int n41 = 0; n41 < 3; ++n41) {
                                    c7.s[n21].b[n41][12] = 0;
                                }
                            }
                        }
                        else {
                            for (int n42 = 0; n42 < 21; ++n42) {
                                c7.s[n21].a[n42] = c7.a[n22];
                                ++n22;
                            }
                            c7.s[n21].a[21] = 0;
                            c7.s[n21].a[22] = 0;
                        }
                    }
                    final c c10 = this;
                    final int n43 = n6;
                    final int n44 = n5;
                    final int n45 = n43;
                    final c c11 = c10;
                    c10.D[0] = 0;
                    c11.E[0] = 0;
                    c11.F[0] = 0;
                    c11.G[0] = 0;
                    final int n46 = c11.v + c11.q.b[n45].b[n44].a;
                    int n47;
                    int n48;
                    if (c11.q.b[n45].b[n44].e != 0 && c11.q.b[n45].b[n44].f == 2) {
                        n47 = ((c11.z == 8) ? 72 : 36);
                        n48 = 576;
                    }
                    else {
                        final int n50;
                        int n49;
                        if ((n49 = (n50 = c11.q.b[n45].b[n44].j + 1) + c11.q.b[n45].b[n44].k + 1) > c11.O[c11.z].a.length - 1) {
                            n49 = c11.O[c11.z].a.length - 1;
                        }
                        n47 = c11.O[c11.z].a[n50];
                        n48 = c11.O[c11.z].a[n49];
                    }
                    int n51 = 0;
                    for (int n52 = 0; n52 < c11.q.b[n45].b[n44].b << 1; n52 += 2) {
                        m m2;
                        if (n52 < n47) {
                            m2 = jaco.mp3.a.m.a[c11.q.b[n45].b[n44].h[0]];
                        }
                        else if (n52 < n48) {
                            m2 = jaco.mp3.a.m.a[c11.q.b[n45].b[n44].h[1]];
                        }
                        else {
                            m2 = jaco.mp3.a.m.a[c11.q.b[n45].b[n44].h[2]];
                        }
                        jaco.mp3.a.m.a(m2, c11.D, c11.E, c11.F, c11.G, c11.p);
                        c11.c[n51++] = c11.D[0];
                        c11.c[n51++] = c11.E[0];
                        c11.b = c11.b + c11.D[0] + c11.E[0];
                    }
                    final m m3 = jaco.mp3.a.m.a[c11.q.b[n45].b[n44].n + 32];
                    int n53;
                    for (n53 = c11.p.a(); n53 < n46 && n51 < 576; c11.c[n51++] = c11.F[0], c11.c[n51++] = c11.G[0], c11.c[n51++] = c11.D[0], c11.c[n51++] = c11.E[0], c11.b = c11.b + c11.F[0] + c11.G[0] + c11.D[0] + c11.E[0], n53 = c11.p.a()) {
                        jaco.mp3.a.m.a(m3, c11.D, c11.E, c11.F, c11.G, c11.p);
                    }
                    if (n53 > n46) {
                        c11.p.c(n53 - n46);
                        n51 -= 4;
                    }
                    final int a;
                    if ((a = c11.p.a()) < n46) {
                        c11.p.a(n46 - a);
                    }
                    if (n51 < 576) {
                        c11.i[n45] = n51;
                    }
                    else {
                        c11.i[n45] = 576;
                    }
                    if (n51 < 0) {
                        n51 = 0;
                    }
                    while (n51 < 576) {
                        c11.c[n51] = 0;
                        ++n51;
                    }
                    final c c12 = this;
                    final float[][] array = this.d[n6];
                    final int n54 = n6;
                    final int n55 = n5;
                    final int n56 = n54;
                    final float[][] array2 = array;
                    final c c13 = c12;
                    final n n57 = c12.q.b[n56].b[n55];
                    int n58 = 0;
                    int n59 = 0;
                    int n60 = 0;
                    int n61 = 0;
                    final float[][] array3 = array2;
                    int n62;
                    if (n57.e != 0 && n57.f == 2 && n57.g == 0) {
                        n62 = ((n60 = c13.O[c13.z].b[1]) << 2) - n60;
                        n59 = 0;
                    }
                    else {
                        n62 = c13.O[c13.z].a[1];
                    }
                    final float n63 = (float)Math.pow(2.0, 0.25 * (n57.c - 210.0));
                    for (int n64 = 0; n64 < c13.i[n56]; ++n64) {
                        final int n65 = n64 % 18;
                        final int n66 = (n64 - n65) / 18;
                        if (c13.c[n64] == 0) {
                            array3[n66][n65] = 0.0f;
                        }
                        else {
                            final int n67;
                            if ((n67 = c13.c[n64]) < jaco.mp3.a.c.Q.length) {
                                if (c13.c[n64] > 0) {
                                    array3[n66][n65] = n63 * jaco.mp3.a.c.Q[n67];
                                }
                                else if (-n67 < jaco.mp3.a.c.Q.length) {
                                    array3[n66][n65] = -n63 * jaco.mp3.a.c.Q[-n67];
                                }
                                else {
                                    array3[n66][n65] = -n63 * (float)Math.pow(-n67, 1.3333333333333333);
                                }
                            }
                            else if (c13.c[n64] > 0) {
                                array3[n66][n65] = n63 * (float)Math.pow(n67, 1.3333333333333333);
                            }
                            else {
                                array3[n66][n65] = -n63 * (float)Math.pow(-n67, 1.3333333333333333);
                            }
                        }
                    }
                    for (int n68 = 0; n68 < c13.i[n56]; ++n68) {
                        final int n69 = n68 % 18;
                        final int n70 = (n68 - n69) / 18;
                        Label_6296: {
                            if (n61 == n62) {
                                if (n57.e != 0 && n57.f == 2) {
                                    if (n57.g == 0) {
                                        final int n71 = c13.O[c13.z].b[++n58 + 1];
                                        n62 = (n71 << 2) - n71;
                                        final int n72 = c13.O[c13.z].b[n58];
                                        n60 = c13.O[c13.z].b[n58 + 1] - n72;
                                        n59 = (n72 << 2) - n72;
                                        break Label_6296;
                                    }
                                    if (n61 == c13.O[c13.z].a[8]) {
                                        final int n73 = c13.O[c13.z].b[4];
                                        n62 = (n73 << 2) - n73;
                                        n58 = 3;
                                        n60 = c13.O[c13.z].b[4] - c13.O[c13.z].b[3];
                                        final int n74 = c13.O[c13.z].b[3];
                                        n59 = (n74 << 2) - n74;
                                        break Label_6296;
                                    }
                                    if (n61 >= c13.O[c13.z].a[8]) {
                                        final int n75 = c13.O[c13.z].b[++n58 + 1];
                                        n62 = (n75 << 2) - n75;
                                        final int n76 = c13.O[c13.z].b[n58];
                                        n60 = c13.O[c13.z].b[n58 + 1] - n76;
                                        n59 = (n76 << 2) - n76;
                                        break Label_6296;
                                    }
                                }
                                n62 = c13.O[c13.z].a[++n58 + 1];
                            }
                        }
                        if (n57.e != 0 && ((n57.f == 2 && n57.g == 0) || (n57.f == 2 && n57.g != 0 && n68 >= 36))) {
                            final int n77 = (n61 - n59) / n60;
                            final int n78 = (c13.s[n56].b[n77][n58] << n57.m) + (n57.i[n77] << 2);
                            final float[] array4 = array3[n70];
                            final int n79 = n69;
                            array4[n79] *= jaco.mp3.a.c.P[n78];
                        }
                        else {
                            int n80 = c13.s[n56].a[n58];
                            if (n57.l != 0) {
                                n80 += jaco.mp3.a.c.N[n58];
                            }
                            final int n81 = n80 << n57.m;
                            final float[] array5 = array3[n70];
                            final int n82 = n69;
                            array5[n82] *= jaco.mp3.a.c.P[n81];
                        }
                        ++n61;
                    }
                    for (int n83 = c13.i[n56]; n83 < 576; ++n83) {
                        int n84 = n83 % 18;
                        int n85 = (n83 - n84) / 18;
                        if (n84 < 0) {
                            n84 = 0;
                        }
                        if (n85 < 0) {
                            n85 = 0;
                        }
                        array3[n85][n84] = 0.0f;
                    }
                }
                this.a(n5);
                if (this.o == 3 && this.w > 1) {
                    final c c14 = this;
                    for (int n86 = 0; n86 < 18; ++n86) {
                        for (int n87 = 0; n87 < 18; n87 += 3) {
                            c14.e[0][n86][n87] = (c14.e[0][n86][n87] + c14.e[1][n86][n87]) * 0.5f;
                            c14.e[0][n86][n87 + 1] = (c14.e[0][n86][n87 + 1] + c14.e[1][n86][n87 + 1]) * 0.5f;
                            c14.e[0][n86][n87 + 2] = (c14.e[0][n86][n87 + 2] + c14.e[1][n86][n87 + 2]) * 0.5f;
                        }
                    }
                }
                for (int x = this.x; x <= this.y; ++x) {
                    final c c15 = this;
                    final float[][] array6 = this.e[x];
                    final int n88 = x;
                    final int n89 = n5;
                    final int n90 = n88;
                    final float[][] array7 = array6;
                    final c c16 = c15;
                    final n n91 = c15.q.b[n90].b[n89];
                    final float[][] array8 = array7;
                    if (n91.e != 0 && n91.f == 2) {
                        for (int n92 = 0; n92 < 576; ++n92) {
                            c16.f[n92] = 0.0f;
                        }
                        if (n91.g != 0) {
                            for (int n93 = 0; n93 < 36; ++n93) {
                                final int n94 = n93 % 18;
                                c16.f[n93] = array8[(n93 - n94) / 18][n94];
                            }
                            for (int n95 = 3; n95 < 13; ++n95) {
                                final int n96 = c16.O[c16.z].b[n95];
                                final int n97 = c16.O[c16.z].b[n95 + 1] - n96;
                                final int n98 = (n96 << 2) - n96;
                                for (int n99 = 0, n100 = 0; n99 < n97; ++n99, n100 += 3) {
                                    final int n101 = n98 + n99;
                                    int n102 = n98 + n100;
                                    final int n103 = n101 % 18;
                                    c16.f[n102] = array8[(n101 - n103) / 18][n103];
                                    final int n104 = n101 + n97;
                                    ++n102;
                                    final int n105 = n104 % 18;
                                    c16.f[n102] = array8[(n104 - n105) / 18][n105];
                                    final int n106 = n104 + n97;
                                    ++n102;
                                    final int n107 = n106 % 18;
                                    c16.f[n102] = array8[(n106 - n107) / 18][n107];
                                }
                            }
                        }
                        else {
                            for (int n108 = 0; n108 < 576; ++n108) {
                                final int n110;
                                final int n109 = (n110 = jaco.mp3.a.c.T[c16.z][n108]) % 18;
                                c16.f[n108] = array8[(n110 - n109) / 18][n109];
                            }
                        }
                    }
                    else {
                        for (int n111 = 0; n111 < 576; ++n111) {
                            final int n112 = n111 % 18;
                            c16.f[n111] = array8[(n111 - n112) / 18][n112];
                        }
                    }
                    final c c17 = this;
                    final int n113 = x;
                    final int n114 = n5;
                    final int n115 = n113;
                    final c c18 = c17;
                    final n n116;
                    if ((n116 = c17.q.b[n115].b[n114]).e == 0 || n116.f != 2 || n116.g != 0) {
                        int n117;
                        if (n116.e != 0 && n116.g != 0 && n116.f == 2) {
                            n117 = 18;
                        }
                        else {
                            n117 = 558;
                        }
                        for (int n118 = 0; n118 < n117; n118 += 18) {
                            for (int n119 = 0; n119 < 8; ++n119) {
                                final int n120 = n118 + 17 - n119;
                                final int n121 = n118 + 18 + n119;
                                final float n122 = c18.f[n120];
                                final float n123 = c18.f[n121];
                                c18.f[n120] = n122 * jaco.mp3.a.c.U[n119] - n123 * jaco.mp3.a.c.V[n119];
                                c18.f[n121] = n123 * jaco.mp3.a.c.U[n119] + n122 * jaco.mp3.a.c.V[n119];
                            }
                        }
                    }
                    this.a(x, n5);
                    for (int n124 = 18; n124 < 576; n124 += 36) {
                        for (int n125 = 1; n125 < 18; n125 += 2) {
                            this.f[n124 + n125] = -this.f[n124 + n125];
                        }
                    }
                    if (x == 0 || this.o == 2) {
                        for (int n126 = 0; n126 < 18; ++n126) {
                            int n127 = 0;
                            for (int n128 = 0; n128 < 576; n128 += 18) {
                                this.A[n127] = this.f[n128 + n126];
                                ++n127;
                            }
                            this.l.a(this.A);
                            this.l.a(this.n);
                        }
                    }
                    else {
                        for (int n129 = 0; n129 < 18; ++n129) {
                            int n130 = 0;
                            for (int n131 = 0; n131 < 576; n131 += 18) {
                                this.B[n130] = this.f[n131 + n129];
                                ++n130;
                            }
                            this.m.a(this.B);
                            this.m.a(this.n);
                        }
                    }
                }
            }
            final c c19 = this;
            ++c19.L;
        }
    }
    
    private void a(final int n, final int n2, final int n3) {
        if (n == 0) {
            this.h[0][n3] = 1.0f;
            this.h[1][n3] = 1.0f;
            return;
        }
        if ((n & 0x1) != 0x0) {
            this.h[0][n3] = jaco.mp3.a.c.R[n2][n + 1 >>> 1];
            this.h[1][n3] = 1.0f;
            return;
        }
        this.h[0][n3] = 1.0f;
        this.h[1][n3] = jaco.mp3.a.c.R[n2][n >>> 1];
    }
    
    private void a(int i) {
        if (this.w == 1) {
            int j;
            for (i = 0; i < 32; ++i) {
                for (j = 0; j < 18; j += 3) {
                    this.e[0][i][j] = this.d[0][i][j];
                    this.e[0][i][j + 1] = this.d[0][i][j + 1];
                    this.e[0][i][j + 2] = this.d[0][i][j + 2];
                }
            }
            return;
        }
        final n n = this.q.b[0].b[i];
        final int k = this.k.i();
        final boolean b = this.k.f() == 1 && (k & 0x2) != 0x0;
        final boolean b2 = this.k.f() == 1 && (k & 0x1) != 0x0;
        final boolean b3 = this.k.a() == 0 || this.k.a() == 2;
        final int n2 = n.d & 0x1;
        for (int l = 0; l < 576; ++l) {
            this.H[l] = 7;
            this.I[l] = 0.0f;
        }
        if (b2) {
            if (n.e != 0 && n.f == 2) {
                if (n.g != 0) {
                    int n3 = 0;
                    for (int n4 = 0; n4 < 3; ++n4) {
                        int n5 = 2;
                        for (int n6 = 12; n6 >= 3; --n6) {
                            final int n7 = this.O[this.z].b[n6];
                            i = this.O[this.z].b[n6 + 1] - n7;
                            for (int n8 = (n7 << 2) - n7 + (n4 + 1) * i - 1; i > 0; --i, --n8) {
                                if (this.d[1][n8 / 18][n8 % 18] != 0.0f) {
                                    n5 = n6;
                                    n6 = -10;
                                    i = -10;
                                }
                            }
                        }
                        int n9;
                        if ((n9 = n5 + 1) > n3) {
                            n3 = n9;
                        }
                        while (n9 < 12) {
                            final int n10 = this.O[this.z].b[n9];
                            i = this.O[this.z].b[n9 + 1] - n10;
                            int n11 = (n10 << 2) - n10 + n4 * i;
                            while (i > 0) {
                                this.H[n11] = this.s[1].b[n4][n9];
                                if (this.H[n11] != 7) {
                                    if (b3) {
                                        this.a(this.H[n11], n2, n11);
                                    }
                                    else {
                                        this.I[n11] = jaco.mp3.a.c.S[this.H[n11]];
                                    }
                                }
                                ++n11;
                                --i;
                            }
                            ++n9;
                        }
                        final int n12 = this.O[this.z].b[10];
                        i = this.O[this.z].b[11] - n12;
                        final int n13 = (n12 << 2) - n12 + n4 * i;
                        final int n14 = this.O[this.z].b[11];
                        i = this.O[this.z].b[12] - n14;
                        int n15 = (n14 << 2) - n14 + n4 * i;
                        while (i > 0) {
                            this.H[n15] = this.H[n13];
                            if (b3) {
                                this.h[0][n15] = this.h[0][n13];
                                this.h[1][n15] = this.h[1][n13];
                            }
                            else {
                                this.I[n15] = this.I[n13];
                            }
                            ++n15;
                            --i;
                        }
                    }
                    if (n3 <= 3) {
                        int n16 = 2;
                        int n17 = 17;
                        i = -1;
                        while (n16 >= 0) {
                            if (this.d[1][n16][n17] != 0.0f) {
                                i = (n16 << 4) + (n16 << 1) + n17;
                                n16 = -1;
                            }
                            else {
                                if (--n17 >= 0) {
                                    continue;
                                }
                                --n16;
                                n17 = 17;
                            }
                        }
                        int n18;
                        for (n18 = 0; this.O[this.z].a[n18] <= i; ++n18) {}
                        int n19 = n18;
                        int n20 = this.O[this.z].a[n18];
                        while (n19 < 8) {
                            for (i = this.O[this.z].a[n19 + 1] - this.O[this.z].a[n19]; i > 0; --i) {
                                this.H[n20] = this.s[1].a[n19];
                                if (this.H[n20] != 7) {
                                    if (b3) {
                                        this.a(this.H[n20], n2, n20);
                                    }
                                    else {
                                        this.I[n20] = jaco.mp3.a.c.S[this.H[n20]];
                                    }
                                }
                                ++n20;
                            }
                            ++n19;
                        }
                    }
                }
                else {
                    for (int n21 = 0; n21 < 3; ++n21) {
                        int n22 = -1;
                        for (int n23 = 12; n23 >= 0; --n23) {
                            final int n24 = this.O[this.z].b[n23];
                            i = this.O[this.z].b[n23 + 1] - n24;
                            for (int n25 = (n24 << 2) - n24 + (n21 + 1) * i - 1; i > 0; --i, --n25) {
                                if (this.d[1][n25 / 18][n25 % 18] != 0.0f) {
                                    n22 = n23;
                                    n23 = -10;
                                    i = -10;
                                }
                            }
                        }
                        for (int n26 = n22 + 1; n26 < 12; ++n26) {
                            final int n27 = this.O[this.z].b[n26];
                            i = this.O[this.z].b[n26 + 1] - n27;
                            int n28 = (n27 << 2) - n27 + n21 * i;
                            while (i > 0) {
                                this.H[n28] = this.s[1].b[n21][n26];
                                if (this.H[n28] != 7) {
                                    if (b3) {
                                        this.a(this.H[n28], n2, n28);
                                    }
                                    else {
                                        this.I[n28] = jaco.mp3.a.c.S[this.H[n28]];
                                    }
                                }
                                ++n28;
                                --i;
                            }
                        }
                        final int n29 = this.O[this.z].b[10];
                        final int n30;
                        i = (n30 = this.O[this.z].b[11]) - n29;
                        final int n31 = (n29 << 2) - n29 + n21 * i;
                        i = this.O[this.z].b[12] - n30;
                        int n32 = (n30 << 2) - n30 + n21 * i;
                        while (i > 0) {
                            this.H[n32] = this.H[n31];
                            if (b3) {
                                this.h[0][n32] = this.h[0][n31];
                                this.h[1][n32] = this.h[1][n31];
                            }
                            else {
                                this.I[n32] = this.I[n31];
                            }
                            ++n32;
                            --i;
                        }
                    }
                }
            }
            else {
                int n33 = 31;
                int n34 = 17;
                i = 0;
                while (n33 >= 0) {
                    if (this.d[1][n33][n34] != 0.0f) {
                        i = (n33 << 4) + (n33 << 1) + n34;
                        n33 = -1;
                    }
                    else {
                        if (--n34 >= 0) {
                            continue;
                        }
                        --n33;
                        n34 = 17;
                    }
                }
                int n35;
                for (n35 = 0; this.O[this.z].a[n35] <= i; ++n35) {}
                int n36 = n35;
                int n37 = this.O[this.z].a[n35];
                while (n36 < 21) {
                    for (i = this.O[this.z].a[n36 + 1] - this.O[this.z].a[n36]; i > 0; --i) {
                        this.H[n37] = this.s[1].a[n36];
                        if (this.H[n37] != 7) {
                            if (b3) {
                                this.a(this.H[n37], n2, n37);
                            }
                            else {
                                this.I[n37] = jaco.mp3.a.c.S[this.H[n37]];
                            }
                        }
                        ++n37;
                    }
                    ++n36;
                }
                final int n38 = this.O[this.z].a[20];
                for (i = 576 - this.O[this.z].a[21]; i > 0 && n37 < 576; ++n37, --i) {
                    this.H[n37] = this.H[n38];
                    if (b3) {
                        this.h[0][n37] = this.h[0][n38];
                        this.h[1][n37] = this.h[1][n38];
                    }
                    else {
                        this.I[n37] = this.I[n38];
                    }
                }
            }
        }
        int n39 = 0;
        int n40;
        for (i = 0; i < 32; ++i) {
            for (n40 = 0; n40 < 18; ++n40) {
                if (this.H[n39] == 7) {
                    if (b) {
                        this.e[0][i][n40] = (this.d[0][i][n40] + this.d[1][i][n40]) * 0.70710677f;
                        this.e[1][i][n40] = (this.d[0][i][n40] - this.d[1][i][n40]) * 0.70710677f;
                    }
                    else {
                        this.e[0][i][n40] = this.d[0][i][n40];
                        this.e[1][i][n40] = this.d[1][i][n40];
                    }
                }
                else if (b2) {
                    if (b3) {
                        this.e[0][i][n40] = this.d[0][i][n40] * this.h[0][n39];
                        this.e[1][i][n40] = this.d[0][i][n40] * this.h[1][n39];
                    }
                    else {
                        this.e[1][i][n40] = this.d[0][i][n40] / (1.0f + this.I[n39]);
                        this.e[0][i][n40] = this.e[1][i][n40] * this.I[n39];
                    }
                }
                ++n39;
            }
        }
    }
    
    private void a(final int n, int n2) {
        final n n3 = this.q.b[n].b[n2];
        for (int i = 0; i < 576; i += 18) {
            n2 = ((n3.e != 0 && n3.g != 0 && i < 36) ? 0 : n3.f);
            final float[] f = this.f;
            for (int j = 0; j < 18; ++j) {
                this.J[j] = f[j + i];
            }
            final float[] k = this.J;
            final float[] l = this.K;
            final int n4 = n2;
            final float[] array = l;
            final float[] array2 = k;
            if (n4 == 2) {
                array[1] = (array[0] = 0.0f);
                array[3] = (array[2] = 0.0f);
                array[5] = (array[4] = 0.0f);
                array[7] = (array[6] = 0.0f);
                array[9] = (array[8] = 0.0f);
                array[11] = (array[10] = 0.0f);
                array[13] = (array[12] = 0.0f);
                array[15] = (array[14] = 0.0f);
                array[17] = (array[16] = 0.0f);
                array[19] = (array[18] = 0.0f);
                array[21] = (array[20] = 0.0f);
                array[23] = (array[22] = 0.0f);
                array[25] = (array[24] = 0.0f);
                array[27] = (array[26] = 0.0f);
                array[29] = (array[28] = 0.0f);
                array[31] = (array[30] = 0.0f);
                array[33] = (array[32] = 0.0f);
                array[35] = (array[34] = 0.0f);
                int n5 = 0;
                for (int n6 = 0; n6 < 3; ++n6) {
                    final float[] array3 = array2;
                    final int n7 = n6 + 15;
                    array3[n7] += array2[n6 + 12];
                    final float[] array4 = array2;
                    final int n8 = n6 + 12;
                    array4[n8] += array2[n6 + 9];
                    final float[] array5 = array2;
                    final int n9 = n6 + 9;
                    array5[n9] += array2[n6 + 6];
                    final float[] array6 = array2;
                    final int n10 = n6 + 6;
                    array6[n10] += array2[n6 + 3];
                    final float[] array7 = array2;
                    final int n11 = n6 + 3;
                    array7[n11] += array2[n6 + 0];
                    final float[] array8 = array2;
                    final int n12 = n6 + 15;
                    array8[n12] += array2[n6 + 9];
                    final float[] array9 = array2;
                    final int n13 = n6 + 9;
                    array9[n13] += array2[n6 + 3];
                    final float n14 = array2[n6 + 12] * 0.5f;
                    final float n15 = array2[n6 + 6] * 0.8660254f;
                    final float n16 = array2[n6 + 0] + n14;
                    final float n17 = array2[n6 + 0] - array2[n6 + 12];
                    final float n18 = n16 + n15;
                    final float n19 = n16 - n15;
                    final float n20 = array2[n6 + 15] * 0.5f;
                    final float n21 = array2[n6 + 9] * 0.8660254f;
                    final float n22 = array2[n6 + 3] + n20;
                    final float n23 = array2[n6 + 3] - array2[n6 + 15];
                    final float n24 = n22 + n21;
                    final float n25 = (n22 - n21) * 1.9318516f;
                    final float n26 = n23 * 0.70710677f;
                    final float n27 = n24 * 0.5176381f;
                    final float n28 = n18;
                    final float n29 = n18 + n27;
                    final float n30 = n28 - n27;
                    final float n31 = n17;
                    final float n32 = n17 + n26;
                    final float n33 = n31 - n26;
                    final float n34 = n19;
                    final float n35 = n19 + n25;
                    final float n36 = n34 - n25;
                    final float n37 = n29 * 0.5043145f;
                    final float n38 = n32 * 0.5411961f;
                    final float n39 = n35 * 0.6302362f;
                    final float n40 = n36 * 0.8213398f;
                    final float n41 = n33 * 1.306563f;
                    final float n42 = n30 * 3.830649f;
                    final float n43 = -n37 * 0.7933533f;
                    final float n44 = -n37 * 0.6087614f;
                    final float n45 = -n38 * 0.9238795f;
                    final float n46 = -n38 * 0.38268343f;
                    final float n47 = -n39 * 0.9914449f;
                    final float n48 = -n39 * 0.13052619f;
                    final float n49 = n40;
                    final float n50 = n41 * 0.38268343f;
                    final float n51 = n42 * 0.6087614f;
                    final float n52 = -n42 * 0.7933533f;
                    final float n53 = -n41 * 0.9238795f;
                    final float n54 = -n49 * 0.9914449f;
                    final float n55 = n49 * 0.13052619f;
                    final float[] array10 = array;
                    final int n56 = n5 + 6;
                    array10[n56] += n55;
                    final float[] array11 = array;
                    final int n57 = n5 + 7;
                    array11[n57] += n50;
                    final float[] array12 = array;
                    final int n58 = n5 + 8;
                    array12[n58] += n51;
                    final float[] array13 = array;
                    final int n59 = n5 + 9;
                    array13[n59] += n52;
                    final float[] array14 = array;
                    final int n60 = n5 + 10;
                    array14[n60] += n53;
                    final float[] array15 = array;
                    final int n61 = n5 + 11;
                    array15[n61] += n54;
                    final float[] array16 = array;
                    final int n62 = n5 + 12;
                    array16[n62] += n47;
                    final float[] array17 = array;
                    final int n63 = n5 + 13;
                    array17[n63] += n45;
                    final float[] array18 = array;
                    final int n64 = n5 + 14;
                    array18[n64] += n43;
                    final float[] array19 = array;
                    final int n65 = n5 + 15;
                    array19[n65] += n44;
                    final float[] array20 = array;
                    final int n66 = n5 + 16;
                    array20[n66] += n46;
                    final float[] array21 = array;
                    final int n67 = n5 + 17;
                    array21[n67] += n48;
                    n5 += 6;
                }
            }
            else {
                final float[] array22 = array2;
                final int n68 = 17;
                array22[n68] += array2[16];
                final float[] array23 = array2;
                final int n69 = 16;
                array23[n69] += array2[15];
                final float[] array24 = array2;
                final int n70 = 15;
                array24[n70] += array2[14];
                final float[] array25 = array2;
                final int n71 = 14;
                array25[n71] += array2[13];
                final float[] array26 = array2;
                final int n72 = 13;
                array26[n72] += array2[12];
                final float[] array27 = array2;
                final int n73 = 12;
                array27[n73] += array2[11];
                final float[] array28 = array2;
                final int n74 = 11;
                array28[n74] += array2[10];
                final float[] array29 = array2;
                final int n75 = 10;
                array29[n75] += array2[9];
                final float[] array30 = array2;
                final int n76 = 9;
                array30[n76] += array2[8];
                final float[] array31 = array2;
                final int n77 = 8;
                array31[n77] += array2[7];
                final float[] array32 = array2;
                final int n78 = 7;
                array32[n78] += array2[6];
                final float[] array33 = array2;
                final int n79 = 6;
                array33[n79] += array2[5];
                final float[] array34 = array2;
                final int n80 = 5;
                array34[n80] += array2[4];
                final float[] array35 = array2;
                final int n81 = 4;
                array35[n81] += array2[3];
                final float[] array36 = array2;
                final int n82 = 3;
                array36[n82] += array2[2];
                final float[] array37 = array2;
                final int n83 = 2;
                array37[n83] += array2[1];
                final float[] array38 = array2;
                final int n84 = 1;
                array38[n84] += array2[0];
                final float[] array39 = array2;
                final int n85 = 17;
                array39[n85] += array2[15];
                final float[] array40 = array2;
                final int n86 = 15;
                array40[n86] += array2[13];
                final float[] array41 = array2;
                final int n87 = 13;
                array41[n87] += array2[11];
                final float[] array42 = array2;
                final int n88 = 11;
                array42[n88] += array2[9];
                final float[] array43 = array2;
                final int n89 = 9;
                array43[n89] += array2[7];
                final float[] array44 = array2;
                final int n90 = 7;
                array44[n90] += array2[5];
                final float[] array45 = array2;
                final int n91 = 5;
                array45[n91] += array2[3];
                final float[] array46 = array2;
                final int n92 = 3;
                array46[n92] += array2[1];
                final float n95;
                final float n94;
                final float n93 = (n94 = (n95 = array2[0] + array2[0]) + array2[12]) + array2[4] * 1.8793852f + array2[8] * 1.5320889f + array2[16] * 0.34729636f;
                final float n96 = n95 + array2[4] - array2[8] - array2[12] - array2[12] - array2[16];
                final float n97 = n94 - array2[4] * 0.34729636f - array2[8] * 1.8793852f + array2[16] * 1.5320889f;
                final float n98 = n94 - array2[4] * 1.5320889f + array2[8] * 0.34729636f - array2[16] * 1.8793852f;
                final float n99 = array2[0] - array2[4] + array2[8] - array2[12] + array2[16];
                final float n100 = array2[6] * 1.7320508f;
                final float n101 = array2[2] * 1.9696155f + n100 + array2[10] * 1.2855753f + array2[14] * 0.6840403f;
                final float n102 = (array2[2] - array2[10] - array2[14]) * 1.7320508f;
                final float n103 = array2[2] * 1.2855753f - n100 - array2[10] * 0.6840403f + array2[14] * 1.9696155f;
                final float n104 = array2[2] * 0.6840403f - n100 + array2[10] * 1.9696155f - array2[14] * 1.2855753f;
                final float n107;
                final float n106;
                final float n105 = (n106 = (n107 = array2[1] + array2[1]) + array2[13]) + array2[5] * 1.8793852f + array2[9] * 1.5320889f + array2[17] * 0.34729636f;
                final float n108 = n107 + array2[5] - array2[9] - array2[13] - array2[13] - array2[17];
                final float n109 = n106 - array2[5] * 0.34729636f - array2[9] * 1.8793852f + array2[17] * 1.5320889f;
                final float n110 = n106 - array2[5] * 1.5320889f + array2[9] * 0.34729636f - array2[17] * 1.8793852f;
                final float n111 = (array2[1] - array2[5] + array2[9] - array2[13] + array2[17]) * 0.70710677f;
                final float n112 = array2[7] * 1.7320508f;
                final float n113 = array2[3] * 1.9696155f + n112 + array2[11] * 1.2855753f + array2[15] * 0.6840403f;
                final float n114 = (array2[3] - array2[11] - array2[15]) * 1.7320508f;
                final float n115 = array2[3] * 1.2855753f - n112 - array2[11] * 0.6840403f + array2[15] * 1.9696155f;
                final float n116 = array2[3] * 0.6840403f - n112 + array2[11] * 1.9696155f - array2[15] * 1.2855753f;
                final float n117 = n93 + n101;
                final float n118 = (n105 + n113) * 0.5019099f;
                final float n119 = n117 + n118;
                final float n120 = n117 - n118;
                final float n121 = n96 + n102;
                final float n122 = (n108 + n114) * 0.5176381f;
                final float n123 = n121 + n122;
                final float n124 = n121 - n122;
                final float n125 = n97 + n103;
                final float n126 = (n109 + n115) * 0.55168897f;
                final float n127 = n125 + n126;
                final float n128 = n125 - n126;
                final float n129 = n98 + n104;
                final float n130 = (n110 + n116) * 0.61038727f;
                final float n131 = n129 + n130;
                final float n132 = n129 - n130;
                final float n133 = n99 + n111;
                final float n134 = n99 - n111;
                final float n135 = n98 - n104;
                final float n136 = (n110 - n116) * 0.8717234f;
                final float n137 = n135 + n136;
                final float n138 = n135 - n136;
                final float n139 = n97 - n103;
                final float n140 = (n109 - n115) * 1.1831008f;
                final float n141 = n139 + n140;
                final float n142 = n139 - n140;
                final float n143 = n96 - n102;
                final float n144 = (n108 - n114) * 1.9318516f;
                final float n145 = n143 + n144;
                final float n146 = n143 - n144;
                final float n147 = n93 - n101;
                final float n148 = (n105 - n113) * 5.7368565f;
                final float n149 = n147 + n148;
                final float n150 = n147 - n148;
                final float[] array47 = jaco.mp3.a.c.W[n4];
                array[0] = -n150 * array47[0];
                array[1] = -n146 * array47[1];
                array[2] = -n142 * array47[2];
                array[3] = -n138 * array47[3];
                array[4] = -n134 * array47[4];
                array[5] = -n132 * array47[5];
                array[6] = -n128 * array47[6];
                array[7] = -n124 * array47[7];
                array[8] = -n120 * array47[8];
                array[9] = n120 * array47[9];
                array[10] = n124 * array47[10];
                array[11] = n128 * array47[11];
                array[12] = n132 * array47[12];
                array[13] = n134 * array47[13];
                array[14] = n138 * array47[14];
                array[15] = n142 * array47[15];
                array[16] = n146 * array47[16];
                array[17] = n150 * array47[17];
                array[18] = n149 * array47[18];
                array[19] = n145 * array47[19];
                array[20] = n141 * array47[20];
                array[21] = n137 * array47[21];
                array[22] = n133 * array47[22];
                array[23] = n131 * array47[23];
                array[24] = n127 * array47[24];
                array[25] = n123 * array47[25];
                array[26] = n119 * array47[26];
                array[27] = n119 * array47[27];
                array[28] = n123 * array47[28];
                array[29] = n127 * array47[29];
                array[30] = n131 * array47[30];
                array[31] = n133 * array47[31];
                array[32] = n137 * array47[32];
                array[33] = n141 * array47[33];
                array[34] = n145 * array47[34];
                array[35] = n149 * array47[35];
            }
            for (int n151 = 0; n151 < 18; ++n151) {
                f[n151 + i] = this.J[n151];
            }
            final float[][] g = this.g;
            f[i + 0] = this.K[0] + g[n][i];
            g[n][i] = this.K[18];
            f[i + 1] = this.K[1] + g[n][i + 1];
            g[n][i + 1] = this.K[19];
            f[i + 2] = this.K[2] + g[n][i + 2];
            g[n][i + 2] = this.K[20];
            f[i + 3] = this.K[3] + g[n][i + 3];
            g[n][i + 3] = this.K[21];
            f[i + 4] = this.K[4] + g[n][i + 4];
            g[n][i + 4] = this.K[22];
            f[i + 5] = this.K[5] + g[n][i + 5];
            g[n][i + 5] = this.K[23];
            f[i + 6] = this.K[6] + g[n][i + 6];
            g[n][i + 6] = this.K[24];
            f[i + 7] = this.K[7] + g[n][i + 7];
            g[n][i + 7] = this.K[25];
            f[i + 8] = this.K[8] + g[n][i + 8];
            g[n][i + 8] = this.K[26];
            f[i + 9] = this.K[9] + g[n][i + 9];
            g[n][i + 9] = this.K[27];
            f[i + 10] = this.K[10] + g[n][i + 10];
            g[n][i + 10] = this.K[28];
            f[i + 11] = this.K[11] + g[n][i + 11];
            g[n][i + 11] = this.K[29];
            f[i + 12] = this.K[12] + g[n][i + 12];
            g[n][i + 12] = this.K[30];
            f[i + 13] = this.K[13] + g[n][i + 13];
            g[n][i + 13] = this.K[31];
            f[i + 14] = this.K[14] + g[n][i + 14];
            g[n][i + 14] = this.K[32];
            f[i + 15] = this.K[15] + g[n][i + 15];
            g[n][i + 15] = this.K[33];
            f[i + 16] = this.K[16] + g[n][i + 16];
            g[n][i + 16] = this.K[34];
            f[i + 17] = this.K[17] + g[n][i + 17];
            g[n][i + 17] = this.K[35];
        }
    }
    
    private static int[] a(final int[] array) {
        int n = 0;
        final int[] array2 = new int[576];
        for (int i = 0; i < 13; ++i) {
            final int n2 = array[i];
            final int n3 = array[i + 1];
            for (int j = 0; j < 3; ++j) {
                for (int k = n2; k < n3; ++k) {
                    array2[k * 3 + j] = n++;
                }
            }
        }
        return array2;
    }
}
