package jaco.mp3.a;

final class a extends t
{
    private float j;
    
    public a(final int n) {
        super(n);
    }
    
    @Override
    public final void a(final z z, final D d, final o o) {
        super.a(z, d, o);
    }
    
    @Override
    public final void a(final z z, final D d) {
        if (this.d != 0) {
            this.e = a.m[z.d(6)];
            this.j = a.m[z.d(6)];
        }
    }
    
    @Override
    public final boolean a(final z z) {
        return super.a(z);
    }
    
    @Override
    public final boolean a(final int n, final y y, final y y2) {
        if (this.d != 0) {
            this.g = this.g * this.h + this.i;
            if (n == 0) {
                final float n2 = this.g * this.e;
                final float n3 = this.g * this.j;
                y.a(n2, this.c);
                y2.a(n3, this.c);
            }
            else if (n == 1) {
                y.a(this.g * this.e, this.c);
            }
            else {
                y.a(this.g * this.j, this.c);
            }
        }
        return true;
    }
}
