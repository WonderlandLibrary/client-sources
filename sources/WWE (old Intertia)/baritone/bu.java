package baritone;

import java.util.*;
import baritone.api.utils.*;
import baritone.api.*;

public final class bu extends ad
{
    private final List<awt> a;
    private final ISchematic a;
    private final int c;
    private final int d;
    private final int e;
    private bs a;
    
    public bu(final bs a) {
        this.a = a;
        super(bs.a(a), true);
        this.a = a.a(9);
        this.a = bs.a(a);
        this.c = bs.a(a).p();
        this.d = bs.a(a).q();
        this.e = bs.a(a).r();
        this.e += 10.0;
        this.d = 1.0;
    }
    
    private awt b(final int n, final int n2, final int n3) {
        if (this.a.inSchematic(n - this.c, n2 - this.d, n3 - this.e)) {
            return this.a.desiredState(n - this.c, n2 - this.d, n3 - this.e);
        }
        return null;
    }
    
    @Override
    public final double a(final int n, final int n2, final int n3) {
        if (!this.a.b(n, n3)) {
            return 1000000.0;
        }
        final awt b;
        if ((b = this.b(n, n2, n3)) != null) {
            if (b.u() == aox.a) {
                return this.a * 2.0;
            }
            if (this.a.contains(b)) {
                return 0.0;
            }
            if (!this.c) {
                return 1000000.0;
            }
            return this.a * 3.0;
        }
        else {
            if (this.c) {
                return this.a;
            }
            return 1000000.0;
        }
    }
    
    @Override
    public final double b(final int n, final int n2, final int n3) {
        if (!this.e) {
            return 1000000.0;
        }
        final awt b;
        if ((b = this.b(n, n2, n3)) == null) {
            return 1.0;
        }
        if (b.u() == aox.a) {
            return 1.0;
        }
        if (bs.a(this.a.a(n, n2, n3), b)) {
            return 3.0;
        }
        return 1.0;
    }
    
    static awt a(final bu bu, final int n, final int n2, final int n3) {
        return bu.b(n, n2, n3);
    }
}
