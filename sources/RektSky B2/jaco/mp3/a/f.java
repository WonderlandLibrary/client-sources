package jaco.mp3.a;

public final class f
{
    private final float[] a;
    
    static {
        new f();
    }
    
    public f() {
        this.a = new float[32];
    }
    
    public final void a(final f f) {
        if (f != this) {
            final f f2 = this;
            final float[] a = f.a;
            this = f2;
            final f f3 = f2;
            for (int i = 0; i < 32; ++i) {
                f3.a[i] = 0.0f;
            }
            for (int n = (a.length > 32) ? 32 : a.length, j = 0; j < n; ++j) {
                final float[] a2 = this.a;
                final int n2 = j;
                float n4 = 0.0f;
                Label_0100: {
                    final float n3;
                    if ((n3 = a[j]) != Float.NEGATIVE_INFINITY) {
                        if (n3 > 1.0f) {
                            n4 = 1.0f;
                            break Label_0100;
                        }
                        if (n3 < -1.0f) {
                            n4 = -1.0f;
                            break Label_0100;
                        }
                    }
                    n4 = n3;
                }
                a2[n2] = n4;
            }
        }
    }
    
    final float[] a() {
        final float[] array = new float[32];
        for (int i = 0; i < 32; ++i) {
            final float[] array2 = array;
            final int n = i;
            final float n2;
            float n3 = 0.0f;
            if ((n2 = this.a[i]) != Float.NEGATIVE_INFINITY) {
                n3 = (float)Math.pow(2.0, n2);
            }
            array2[n] = n3;
        }
        return array;
    }
}
