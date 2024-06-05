package jaco.mp3.a;

import java.io.*;

public final class z
{
    static byte a;
    static byte b;
    private final int[] c;
    private int d;
    private byte[] e;
    private int f;
    private int g;
    private int h;
    private boolean i;
    private final int[] j;
    private final PushbackInputStream k;
    private final D l;
    private final byte[] m;
    private o[] n;
    private byte[] o;
    private boolean p;
    
    static {
        z.a = 0;
        z.b = 1;
    }
    
    public z(InputStream inputStream) {
        this.c = new int[433];
        this.e = new byte[1732];
        this.j = new int[] { 0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071 };
        this.l = new D();
        this.m = new byte[4];
        this.n = new o[1];
        this.o = null;
        this.p = true;
        if (inputStream == null) {
            throw new NullPointerException("in");
        }
        final BufferedInputStream bufferedInputStream;
        inputStream = (bufferedInputStream = new BufferedInputStream(inputStream));
        int n = -1;
        try {
            bufferedInputStream.mark(10);
            final BufferedInputStream bufferedInputStream2 = bufferedInputStream;
            final byte[] array = new byte[4];
            int n2 = -10;
            bufferedInputStream2.read(array, 0, 3);
            if (array[0] == 73 && array[1] == 68 && array[2] == 51) {
                bufferedInputStream2.read(array, 0, 3);
                bufferedInputStream2.read(array, 0, 4);
                n2 = (array[0] << 21) + (array[1] << 14) + (array[2] << 7) + array[3];
            }
            n = n2 + 10;
        }
        catch (IOException ex) {
            try {
                bufferedInputStream.reset();
            }
            catch (IOException ex2) {}
        }
        finally {
            try {
                bufferedInputStream.reset();
            }
            catch (IOException ex3) {}
        }
        try {
            bufferedInputStream.reset();
        }
        catch (IOException ex4) {}
        try {
            if (n > 0) {
                bufferedInputStream.read(this.o = new byte[n], 0, this.o.length);
            }
        }
        catch (IOException ex5) {}
        final z z;
        z.p = true;
        z.k = new PushbackInputStream(inputStream, 1732);
        z.d();
    }
    
    public final void a() {
        try {
            this.k.close();
        }
        catch (IOException ex) {
            throw a(258, ex);
        }
    }
    
    public final D b() {
        D d = null;
        try {
            d = this.f();
            if (this.p) {
                d.a(this.e);
                this.p = false;
            }
        }
        catch (s s2) {
            final s s = s2;
            if (s2.a() == 261) {
                try {
                    this.d();
                    d = this.f();
                    return d;
                }
                catch (s s4) {
                    final s s3 = s4;
                    if (s4.a() != 260) {
                        throw a(s3.a(), s3);
                    }
                    return d;
                }
            }
            if (s.a() != 260) {
                throw a(s.a(), s);
            }
        }
        return d;
    }
    
    private D f() {
        if (this.d == -1) {
            this.l.a(this, this.n);
        }
        return this.l;
    }
    
    public final void c() {
        if (this.f == -1 && this.g == -1 && this.d > 0) {
            try {
                this.k.unread(this.e, 0, this.d);
            }
            catch (IOException ex) {
                throw b(258);
            }
        }
    }
    
    public final void d() {
        this.d = -1;
        this.f = -1;
        this.g = -1;
    }
    
    public final boolean a(final int n) {
        final int b = this.b(this.m, 0, 4);
        final int n2 = (this.m[0] << 24 & 0xFF000000) | (this.m[1] << 16 & 0xFF0000) | (this.m[2] << 8 & 0xFF00) | (this.m[3] & 0xFF);
        try {
            this.k.unread(this.m, 0, b);
        }
        catch (IOException ex) {}
        boolean a = false;
        switch (b) {
            case 0: {
                a = true;
                break;
            }
            case 4: {
                a = this.a(n2, n, this.h);
                break;
            }
        }
        return a;
    }
    
    protected static s b(final int n) {
        return new s(n, null);
    }
    
    private static s a(final int n, final Throwable t) {
        return new s(n, t);
    }
    
    final int a(final byte b) {
        if (this.b(this.m, 0, 3) != 3) {
            throw a(260, null);
        }
        int n = (this.m[0] << 16 & 0xFF0000) | (this.m[1] << 8 & 0xFF00) | (this.m[2] & 0xFF);
        do {
            final int n2 = n << 8;
            if (this.b(this.m, 3, 1) != 1) {
                throw a(260, null);
            }
            n = (n2 | (this.m[3] & 0xFF));
        } while (!this.a(n, b, this.h));
        return n;
    }
    
    private boolean a(final int n, final int n2, final int n3) {
        boolean b;
        if (n2 == 0) {
            b = ((n & 0xFFE00000) == 0xFFE00000);
        }
        else {
            b = ((n & 0xFFF80C00) == n3 && (n & 0xC0) == 0xC0 == this.i);
        }
        if (b) {
            b = ((n >>> 10 & 0x3) != 0x3);
        }
        if (b) {
            b = ((n >>> 17 & 0x3) != 0x0);
        }
        if (b) {
            b = ((n >>> 19 & 0x3) != 0x1);
        }
        return b;
    }
    
    final int c(final int d) {
        final int a = this.a(this.e, 0, d);
        this.d = d;
        this.f = -1;
        this.g = -1;
        return a;
    }
    
    final void e() {
        int n = 0;
        final byte[] e = this.e;
        for (int d = this.d, i = 0; i < d; i += 4) {
            int n2 = 0;
            int n3 = 0;
            byte b = 0;
            final byte b2 = e[i];
            if (i + 1 < d) {
                n2 = e[i + 1];
            }
            if (i + 2 < d) {
                n3 = e[i + 2];
            }
            if (i + 3 < d) {
                b = e[i + 3];
            }
            this.c[n++] = ((b2 << 24 & 0xFF000000) | (n2 << 16 & 0xFF0000) | (n3 << 8 & 0xFF00) | (b & 0xFF));
        }
        this.f = 0;
        this.g = 0;
    }
    
    public final int d(final int n) {
        final int n2 = this.g + n;
        if (this.f < 0) {
            this.f = 0;
        }
        if (n2 <= 32) {
            final int n3 = this.c[this.f] >>> 32 - n2 & this.j[n];
            if ((this.g += n) == 32) {
                this.g = 0;
                ++this.f;
            }
            return n3;
        }
        final int n4 = this.c[this.f] & 0xFFFF;
        ++this.f;
        final int n5 = ((n4 << 16 & 0xFFFF0000) | ((this.c[this.f] & 0xFFFF0000) >>> 16 & 0xFFFF)) >>> 48 - n2 & this.j[n];
        this.g = n2 - 32;
        return n5;
    }
    
    final void e(final int n) {
        this.h = (n & 0xFFFFFF3F);
        this.i = ((n & 0xC0) == 0xC0);
    }
    
    private int a(final byte[] array, int n, int i) {
        int n2 = 0;
        try {
            while (i > 0) {
                final int read;
                if ((read = this.k.read(array, n, i)) == -1) {
                    while (i-- > 0) {
                        array[n++] = 0;
                    }
                    break;
                }
                n2 += read;
                n += read;
                i -= read;
            }
        }
        catch (IOException ex) {
            throw a(258, ex);
        }
        return n2;
    }
    
    private int b(final byte[] array, int n, int i) {
        int n2 = 0;
        try {
            while (i > 0) {
                final int read;
                if ((read = this.k.read(array, n, i)) == -1) {
                    break;
                }
                n2 += read;
                n += read;
                i -= read;
            }
        }
        catch (IOException ex) {
            throw a(258, ex);
        }
        return n2;
    }
}
