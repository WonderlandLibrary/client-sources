package jaco.mp3.a;

final class h
{
    private int a;
    private int b;
    private int c;
    private final int[] d;
    
    h() {
        this.d = new int[32768];
        this.a = 0;
        this.b = 0;
        this.c = 0;
    }
    
    public final int a() {
        return this.b;
    }
    
    public final int a(int n) {
        this.b += n;
        int n2 = 0;
        int c;
        if ((c = this.c) + n < 32768) {
            while (n-- > 0) {
                n2 = (n2 << 1 | ((this.d[c++] != 0) ? 1 : 0));
            }
        }
        else {
            while (n-- > 0) {
                n2 = (n2 << 1 | ((this.d[c] != 0) ? 1 : 0));
                c = (c + 1 & 0x7FFF);
            }
        }
        this.c = c;
        return n2;
    }
    
    public final int b() {
        ++this.b;
        final int n = this.d[this.c];
        this.c = (this.c + 1 & 0x7FFF);
        return n;
    }
    
    public final void b(final int n) {
        int a = this.a;
        this.d[a++] = (n & 0x80);
        this.d[a++] = (n & 0x40);
        this.d[a++] = (n & 0x20);
        this.d[a++] = (n & 0x10);
        this.d[a++] = (n & 0x8);
        this.d[a++] = (n & 0x4);
        this.d[a++] = (n & 0x2);
        this.d[a++] = (n & 0x1);
        if (a == 32768) {
            this.a = 0;
            return;
        }
        this.a = a;
    }
    
    public final void c(final int n) {
        this.b -= n;
        this.c -= n;
        if (this.c < 0) {
            this.c += 32768;
        }
    }
    
    public final void d(int n) {
        n = 4096 << 3;
        this.b -= n;
        this.c -= n;
        if (this.c < 0) {
            this.c += 32768;
        }
    }
}
