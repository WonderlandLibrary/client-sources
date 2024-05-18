package baritone;

import baritone.api.pathing.goals.*;
import java.util.*;
import baritone.api.pathing.calc.*;
import baritone.api.utils.*;

public final class z extends aa
{
    private final dg a;
    private final ad a;
    
    public z(final int n, final int n2, final int n3, final Goal goal, final dg a, final ad a2) {
        super(n, n2, n3, goal, a2);
        this.a = a;
        this.a = a2;
    }
    
    @Override
    protected final Optional<IPath> a(long n2, long n4) {
        this.a = this.a(this.a, this.b, this.c, BetterBlockPos.longHash(this.a, this.b, this.c));
        this.a.b = 0.0;
        this.a.c = this.a.a;
        final ac ac;
        (ac = new ac()).a(this.a);
        final double[] array = new double[7];
        for (int i = 0; i < 7; ++i) {
            array[i] = this.a.a;
            this.a[i] = this.a;
        }
        final dh dh = new dh();
        final df df = new df(this.a.a.al());
        final long currentTimeMillis = System.currentTimeMillis();
        final boolean booleanValue;
        if (booleanValue = baritone.a.a().slowPath.value) {
            this.a("slowPath is on, path timeout will be " + baritone.a.a().slowPathTimeoutMS.value + "ms instead of " + n + "ms");
        }
        final long n5 = currentTimeMillis + (booleanValue ? baritone.a.a().slowPathTimeoutMS.value : n);
        final long n6 = currentTimeMillis + (booleanValue ? baritone.a.a().slowPathTimeoutMS.value : n3);
        n2 = 1;
        int n7 = 0;
        n4 = 0;
        int n8 = 0;
        final boolean b = !this.a.a.isEmpty();
        final int intValue = baritone.a.a().pathingMaxChunkBorderFetch.value;
        final double n9 = baritone.a.a().minimumImprovementRepropagation.value ? 0.01 : 0.0;
        long currentTimeMillis2;
        while (ac.a != 0 && n8 < intValue && !this.a && ((n7 & 0x3F) != 0x0 || ((currentTimeMillis2 = System.currentTimeMillis()) - n6 < 0L && currentTimeMillis2 - n5 < 0L))) {
            if (booleanValue) {
                try {
                    Thread.sleep(baritone.a.a().slowPathTimeDelayMS.value);
                }
                catch (InterruptedException ex) {}
            }
            final g a = ac.a();
            this.b = a;
            ++n7;
            if (this.a.isInGoal(a.a, a.b, a.c)) {
                this.a("Took " + (System.currentTimeMillis() - currentTimeMillis) + "ms, " + (int)n4 + " movements considered");
                return (Optional<IPath>)Optional.of(new ab(this.a, a, n7, this.a, this.a));
            }
            aj[] values;
            for (int length = (values = aj.values()).length, j = 0; j < length; ++j) {
                final aj aj = values[j];
                final int n10 = a.a + aj.a;
                final int n11 = a.c + aj.c;
                if ((n10 >> 4 != a.a >> 4 || n11 >> 4 != a.c >> 4) && !this.a.a.b(n10, n11)) {
                    if (!aj.a) {
                        ++n8;
                    }
                }
                else if ((aj.a || df.a(n10, n11)) && a.b + aj.b <= 256 && a.b + aj.b >= 0) {
                    dh.a();
                    aj.a(this.a, a.a, a.b, a.c, dh);
                    ++n4;
                    double a2;
                    if ((a2 = dh.a) < 1000000.0) {
                        if (a2 <= 0.0 || Double.isNaN(a2)) {
                            throw new IllegalStateException(aj + " calculated implausible cost " + a2);
                        }
                        if (!aj.a || df.a(dh.a, dh.c)) {
                            if (!aj.a && (dh.a != n10 || dh.c != n11)) {
                                throw new IllegalStateException(aj + " " + dh.a + " " + n10 + " " + dh.c + " " + n11);
                            }
                            if (!aj.b && dh.b != a.b + aj.b) {
                                throw new IllegalStateException(aj + " " + dh.b + " " + (a.b + aj.b));
                            }
                            final long longHash = BetterBlockPos.longHash(dh.a, dh.b, dh.c);
                            if (b) {
                                a2 *= this.a.a.get(longHash);
                            }
                            final g a3 = this.a(dh.a, dh.b, dh.c, longHash);
                            final double b2 = a.b + a2;
                            if (a3.b - b2 > n9) {
                                a3.a = a;
                                a3.b = b2;
                                a3.c = b2 + a3.a;
                                if (a3.d != -1) {
                                    ac.b(a3);
                                }
                                else {
                                    ac.a(a3);
                                }
                                for (int k = 0; k < 7; ++k) {
                                    final double n12 = a3.a + a3.b / z.a[k];
                                    if (array[k] - n12 > n9) {
                                        array[k] = n12;
                                        this.a[k] = a3;
                                        if (n2 != 0 && this.a(a3) > 25.0) {
                                            n2 = 0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.a) {
            return Optional.empty();
        }
        System.out.println((int)n4 + " movements considered");
        System.out.println("Open set size: " + ac.a);
        System.out.println("PathNode map size: " + super.a.size());
        System.out.println((int)(n7 / (double)((System.currentTimeMillis() - currentTimeMillis) / 1000.0f)) + " nodes per second");
        final Optional<IPath> a4;
        if ((a4 = this.a(true, n7)).isPresent()) {
            this.a("Took " + (System.currentTimeMillis() - currentTimeMillis) + "ms, " + (int)n4 + " movements considered");
        }
        return a4;
    }
}
