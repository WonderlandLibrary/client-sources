package jaco.mp3.a;

class t extends p
{
    public static final float[] a;
    public static final float[] b;
    protected int c;
    private int j;
    protected int d;
    protected float e;
    protected int f;
    protected float g;
    protected float h;
    protected float i;
    
    static {
        a = new float[] { 0.0f, 0.6666667f, 0.2857143f, 0.13333334f, 0.06451613f, 0.031746034f, 0.015748031f, 0.007843138f, 0.0039138943f, 0.0019550342f, 9.770396E-4f, 4.884005E-4f, 2.4417043E-4f, 1.2207776E-4f, 6.103702E-5f };
        b = new float[] { 0.0f, -0.6666667f, -0.8571429f, -0.9333334f, -0.9677419f, -0.98412704f, -0.992126f, -0.9960785f, -0.99804306f, -0.9990225f, -0.9995115f, -0.99975586f, -0.9998779f, -0.99993896f, -0.9999695f };
    }
    
    public t(final int c) {
        this.c = c;
        this.j = 0;
    }
    
    @Override
    public void a(final z z, final D d, final o o) {
        final int d2 = z.d(4);
        this.d = d2;
        if (d2 == 15) {
            throw new u(514, null);
        }
        if (o != null) {
            o.a(this.d, 4);
        }
        if (this.d != 0) {
            this.f = this.d + 1;
            this.h = t.a[this.d];
            this.i = t.b[this.d];
        }
    }
    
    @Override
    public void a(final z z, final D d) {
        if (this.d != 0) {
            this.e = t.m[z.d(6)];
        }
    }
    
    @Override
    public boolean a(final z z) {
        if (this.d != 0) {
            this.g = (float)z.d(this.f);
        }
        if (++this.j == 12) {
            this.j = 0;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean a(final int n, final y y, final y y2) {
        if (this.d != 0 && n != 2) {
            y.a((this.g * this.h + this.i) * this.e, this.c);
        }
        return true;
    }
}
