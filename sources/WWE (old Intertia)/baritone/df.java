package baritone;

public final class df
{
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    
    public df(final axn axn) {
        super();
        this.a = axn.b();
        this.b = axn.d();
        this.c = axn.c();
        this.d = axn.e();
    }
    
    public final boolean a(final int n, final int n2) {
        return n + 1 > this.a && n < this.b && n2 + 1 > this.c && n2 < this.d;
    }
    
    public final boolean b(final int n, final int n2) {
        return n > this.a && n + 1 < this.b && n2 > this.c && n2 + 1 < this.d;
    }
}
