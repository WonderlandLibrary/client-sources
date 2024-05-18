package baritone.api.utils;

public final class BetterBlockPos extends et
{
    public final int a;
    public final int b;
    public final int c;
    
    public BetterBlockPos(final int a, final int b, final int c) {
        super(a, b, c);
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public BetterBlockPos(final double n, final double n2, final double n3) {
        this(rk.c(n), rk.c(n2), rk.c(n3));
    }
    
    public BetterBlockPos(final et et) {
        this(et.p(), et.q(), et.r());
    }
    
    public final int hashCode() {
        return (int)longHash(this.a, this.b, this.c);
    }
    
    public static long longHash(final BetterBlockPos betterBlockPos) {
        return longHash(betterBlockPos.a, betterBlockPos.b, betterBlockPos.c);
    }
    
    public static long longHash(final int n, final int n2, final int n3) {
        return 2873465L * (8734625L * (11206370049L + n) + n2) + n3;
    }
    
    public final boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof BetterBlockPos) {
            final BetterBlockPos betterBlockPos;
            return (betterBlockPos = (BetterBlockPos)o).a == this.a && betterBlockPos.b == this.b && betterBlockPos.c == this.c;
        }
        final et et;
        return (et = (et)o).p() == this.a && et.q() == this.b && et.r() == this.c;
    }
    
    public final BetterBlockPos up() {
        return new BetterBlockPos(this.a, this.b + 1, this.c);
    }
    
    public final BetterBlockPos up(final int n) {
        return this;
    }
    
    public final BetterBlockPos down() {
        return new BetterBlockPos(this.a, this.b - 1, this.c);
    }
    
    public final BetterBlockPos down(final int n) {
        return this;
    }
    
    public final BetterBlockPos offset(final fa fa) {
        final fq n = fa.n();
        return new BetterBlockPos(this.a + n.p(), this.b + n.q(), this.c + n.r());
    }
    
    public final BetterBlockPos offset(final fa fa, final int n) {
        return this;
    }
    
    public final BetterBlockPos north() {
        return new BetterBlockPos(this.a, this.b, this.c - 1);
    }
    
    public final BetterBlockPos north(final int n) {
        return this;
    }
    
    public final BetterBlockPos south() {
        return new BetterBlockPos(this.a, this.b, this.c + 1);
    }
    
    public final BetterBlockPos south(final int n) {
        return this;
    }
    
    public final BetterBlockPos east() {
        return new BetterBlockPos(this.a + 1, this.b, this.c);
    }
    
    public final BetterBlockPos east(final int n) {
        return this;
    }
    
    public final BetterBlockPos west() {
        return new BetterBlockPos(this.a - 1, this.b, this.c);
    }
    
    public final BetterBlockPos west(final int n) {
        return this;
    }
    
    public final et a(final fa fa, final int n) {
        return this.offset(fa, n);
    }
    
    public final et a(final fa fa) {
        return this.offset(fa);
    }
    
    public final et g(final int n) {
        return this.east(n);
    }
    
    public final et f() {
        return this.east();
    }
    
    public final et f(final int n) {
        return this.west(n);
    }
    
    public final et e() {
        return this.west();
    }
    
    public final et e(final int n) {
        return this.south(n);
    }
    
    public final et d() {
        return this.south();
    }
    
    public final et d(final int n) {
        return this.north(n);
    }
    
    public final et c() {
        return this.north();
    }
    
    public final et c(final int n) {
        return this.down(n);
    }
    
    public final et b() {
        return this.down();
    }
    
    public final et b(final int n) {
        return this.up(n);
    }
    
    public final et a() {
        return this.up();
    }
    
    public final fq d(final fq fq) {
        return (fq)super.c(fq);
    }
    
    public final int compareTo(final Object o) {
        return super.l((fq)o);
    }
}
