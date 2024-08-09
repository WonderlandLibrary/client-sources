/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin;

import kotlin.Metadata;
import kotlin.NumbersKt__BigIntegersKt;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.internal.IntrinsicConstEvaluation;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000 \n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\t\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0005*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0007H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\bH\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0007H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\bH\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0005*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0002*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0005*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0002*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0006\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u00a8\u0006\t"}, d2={"floorDiv", "", "", "other", "", "", "mod", "", "", "kotlin-stdlib"}, xs="kotlin/NumbersKt")
class NumbersKt__FloorDivModKt
extends NumbersKt__BigIntegersKt {
    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int floorDiv(byte by, byte by2) {
        byte by3 = by;
        byte by4 = by2;
        int n = by3 / by4;
        if ((by3 ^ by4) < 0 && n * by4 != by3) {
            --n;
        }
        return n;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final byte mod(byte by, byte by2) {
        byte by3 = by;
        byte by4 = by2;
        int n = by3 % by4;
        return (byte)(n + (by4 & ((n ^ by4) & (n | -n)) >> 31));
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int floorDiv(byte by, short s) {
        byte by2 = by;
        short s2 = s;
        int n = by2 / s2;
        if ((by2 ^ s2) < 0 && n * s2 != by2) {
            --n;
        }
        return n;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final short mod(byte by, short s) {
        byte by2 = by;
        short s2 = s;
        int n = by2 % s2;
        return (short)(n + (s2 & ((n ^ s2) & (n | -n)) >> 31));
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int floorDiv(byte by, int n) {
        byte by2 = by;
        int n2 = by2 / n;
        if ((by2 ^ n) < 0 && n2 * n != by2) {
            --n2;
        }
        return n2;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int mod(byte by, int n) {
        int n2 = by % n;
        return n2 + (n & ((n2 ^ n) & (n2 | -n2)) >> 31);
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final long floorDiv(byte by, long l) {
        long l2 = by;
        long l3 = l2 / l;
        if ((l2 ^ l) < 0L && l3 * l != l2) {
            l3 += -1L;
        }
        return l3;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final long mod(byte by, long l) {
        long l2 = (long)by % l;
        return l2 + (l & ((l2 ^ l) & (l2 | -l2)) >> 63);
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int floorDiv(short s, byte by) {
        short s2 = s;
        byte by2 = by;
        int n = s2 / by2;
        if ((s2 ^ by2) < 0 && n * by2 != s2) {
            --n;
        }
        return n;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final byte mod(short s, byte by) {
        short s2 = s;
        byte by2 = by;
        int n = s2 % by2;
        return (byte)(n + (by2 & ((n ^ by2) & (n | -n)) >> 31));
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int floorDiv(short s, short s2) {
        short s3 = s;
        short s4 = s2;
        int n = s3 / s4;
        if ((s3 ^ s4) < 0 && n * s4 != s3) {
            --n;
        }
        return n;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final short mod(short s, short s2) {
        short s3 = s;
        short s4 = s2;
        int n = s3 % s4;
        return (short)(n + (s4 & ((n ^ s4) & (n | -n)) >> 31));
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int floorDiv(short s, int n) {
        short s2 = s;
        int n2 = s2 / n;
        if ((s2 ^ n) < 0 && n2 * n != s2) {
            --n2;
        }
        return n2;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int mod(short s, int n) {
        int n2 = s % n;
        return n2 + (n & ((n2 ^ n) & (n2 | -n2)) >> 31);
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final long floorDiv(short s, long l) {
        long l2 = s;
        long l3 = l2 / l;
        if ((l2 ^ l) < 0L && l3 * l != l2) {
            l3 += -1L;
        }
        return l3;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final long mod(short s, long l) {
        long l2 = (long)s % l;
        return l2 + (l & ((l2 ^ l) & (l2 | -l2)) >> 63);
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int floorDiv(int n, byte by) {
        int n2 = n;
        byte by2 = by;
        int n3 = n2 / by2;
        if ((n2 ^ by2) < 0 && n3 * by2 != n2) {
            --n3;
        }
        return n3;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final byte mod(int n, byte by) {
        int n2 = n;
        byte by2 = by;
        int n3 = n2 % by2;
        return (byte)(n3 + (by2 & ((n3 ^ by2) & (n3 | -n3)) >> 31));
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int floorDiv(int n, short s) {
        int n2 = n;
        short s2 = s;
        int n3 = n2 / s2;
        if ((n2 ^ s2) < 0 && n3 * s2 != n2) {
            --n3;
        }
        return n3;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final short mod(int n, short s) {
        int n2 = n;
        short s2 = s;
        int n3 = n2 % s2;
        return (short)(n3 + (s2 & ((n3 ^ s2) & (n3 | -n3)) >> 31));
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int floorDiv(int n, int n2) {
        int n3 = n / n2;
        if ((n ^ n2) < 0 && n3 * n2 != n) {
            --n3;
        }
        return n3;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int mod(int n, int n2) {
        int n3 = n % n2;
        return n3 + (n2 & ((n3 ^ n2) & (n3 | -n3)) >> 31);
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final long floorDiv(int n, long l) {
        long l2 = n;
        long l3 = l2 / l;
        if ((l2 ^ l) < 0L && l3 * l != l2) {
            l3 += -1L;
        }
        return l3;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final long mod(int n, long l) {
        long l2 = (long)n % l;
        return l2 + (l & ((l2 ^ l) & (l2 | -l2)) >> 63);
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final long floorDiv(long l, byte by) {
        long l2 = l;
        long l3 = by;
        long l4 = l2 / l3;
        if ((l2 ^ l3) < 0L && l4 * l3 != l2) {
            l4 += -1L;
        }
        return l4;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final byte mod(long l, byte by) {
        long l2 = l;
        long l3 = by;
        long l4 = l2 % l3;
        return (byte)(l4 + (l3 & ((l4 ^ l3) & (l4 | -l4)) >> 63));
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final long floorDiv(long l, short s) {
        long l2 = l;
        long l3 = s;
        long l4 = l2 / l3;
        if ((l2 ^ l3) < 0L && l4 * l3 != l2) {
            l4 += -1L;
        }
        return l4;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final short mod(long l, short s) {
        long l2 = l;
        long l3 = s;
        long l4 = l2 % l3;
        return (short)(l4 + (l3 & ((l4 ^ l3) & (l4 | -l4)) >> 63));
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final long floorDiv(long l, int n) {
        long l2 = l;
        long l3 = n;
        long l4 = l2 / l3;
        if ((l2 ^ l3) < 0L && l4 * l3 != l2) {
            l4 += -1L;
        }
        return l4;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final int mod(long l, int n) {
        long l2 = l;
        long l3 = n;
        long l4 = l2 % l3;
        return (int)(l4 + (l3 & ((l4 ^ l3) & (l4 | -l4)) >> 63));
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final long floorDiv(long l, long l2) {
        long l3 = l / l2;
        if ((l ^ l2) < 0L && l3 * l2 != l) {
            long l4 = l3;
            l3 = l4 + -1L;
        }
        return l3;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final long mod(long l, long l2) {
        long l3 = l % l2;
        return l3 + (l2 & ((l3 ^ l2) & (l3 | -l3)) >> 63);
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final float mod(float f, float f2) {
        float f3 = f % f2;
        return !(f3 == 0.0f) && !(Math.signum(f3) == Math.signum(f2)) ? f3 + f2 : f3;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final double mod(float f, double d) {
        double d2 = (double)f % d;
        return !(d2 == 0.0) && !(Math.signum(d2) == Math.signum(d)) ? d2 + d : d2;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final double mod(double d, float f) {
        double d2 = d;
        double d3 = f;
        double d4 = d2 % d3;
        return !(d4 == 0.0) && !(Math.signum(d4) == Math.signum(d3)) ? d4 + d3 : d4;
    }

    @SinceKotlin(version="1.5")
    @InlineOnly
    @IntrinsicConstEvaluation
    private static final double mod(double d, double d2) {
        double d3 = d % d2;
        return !(d3 == 0.0) && !(Math.signum(d3) == Math.signum(d2)) ? d3 + d2 : d3;
    }
}

