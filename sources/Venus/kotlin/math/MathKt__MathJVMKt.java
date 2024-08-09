/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.math;

import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.math.Constants;
import kotlin.math.MathKt;
import kotlin.math.MathKt__MathHKt;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\"\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b8\u001a\u0011\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\tH\u0087\b\u001a\u0011\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\fH\u0087\b\u001a\u0011\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0019\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0019\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010 \u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010 \u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010!\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010!\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\"\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\"\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010#\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010#\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010$\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010$\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010%\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010%\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010&\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010&\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010'\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010'\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0019\u0010(\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u0001H\u0087\b\u001a\u0019\u0010(\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010)\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010)\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010*\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010*\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0018\u0010+\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u0001H\u0007\u001a\u0018\u0010+\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\u0006H\u0007\u001a\u0011\u0010-\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010-\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010.\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0010\u0010.\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0007\u001a\u0019\u0010/\u001a\u00020\u00012\u0006\u00100\u001a\u00020\u00012\u0006\u00101\u001a\u00020\u0001H\u0087\b\u001a\u0019\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u0006H\u0087\b\u001a\u0019\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020\t2\u0006\u00101\u001a\u00020\tH\u0087\b\u001a\u0019\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\f2\u0006\u00101\u001a\u00020\fH\u0087\b\u001a\u0019\u00102\u001a\u00020\u00012\u0006\u00100\u001a\u00020\u00012\u0006\u00101\u001a\u00020\u0001H\u0087\b\u001a\u0019\u00102\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u0006H\u0087\b\u001a\u0019\u00102\u001a\u00020\t2\u0006\u00100\u001a\u00020\t2\u0006\u00101\u001a\u00020\tH\u0087\b\u001a\u0019\u00102\u001a\u00020\f2\u0006\u00100\u001a\u00020\f2\u0006\u00101\u001a\u00020\fH\u0087\b\u001a\u0011\u00103\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00103\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00104\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00104\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00105\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00105\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00106\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00106\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00107\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00107\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00108\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00108\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u00109\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0010\u00109\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0007\u001a\u0015\u0010:\u001a\u00020\u0001*\u00020\u00012\u0006\u0010;\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010:\u001a\u00020\u0006*\u00020\u00062\u0006\u0010;\u001a\u00020\u0006H\u0087\b\u001a\r\u0010<\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010<\u001a\u00020\u0006*\u00020\u0006H\u0087\b\u001a\u0015\u0010=\u001a\u00020\u0001*\u00020\u00012\u0006\u0010>\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010=\u001a\u00020\u0006*\u00020\u00062\u0006\u0010>\u001a\u00020\u0006H\u0087\b\u001a\r\u0010?\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010?\u001a\u00020\u0006*\u00020\u0006H\u0087\b\u001a\u0015\u0010@\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010@\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0018\u001a\u00020\tH\u0087\b\u001a\u0015\u0010@\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010@\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0018\u001a\u00020\tH\u0087\b\u001a\f\u0010A\u001a\u00020\t*\u00020\u0001H\u0007\u001a\f\u0010A\u001a\u00020\t*\u00020\u0006H\u0007\u001a\f\u0010B\u001a\u00020\f*\u00020\u0001H\u0007\u001a\f\u0010B\u001a\u00020\f*\u00020\u0006H\u0007\u001a\u0015\u0010C\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010C\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\tH\u0087\b\u001a\u0015\u0010C\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010C\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u000f\u001a\u00020\tH\u0087\b\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00018\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"\u001f\u0010\u0000\u001a\u00020\u0006*\u00020\u00068\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\u0007\u001a\u0004\b\u0004\u0010\b\"\u001f\u0010\u0000\u001a\u00020\t*\u00020\t8\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\n\u001a\u0004\b\u0004\u0010\u000b\"\u001f\u0010\u0000\u001a\u00020\f*\u00020\f8\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\r\u001a\u0004\b\u0004\u0010\u000e\"\u001f\u0010\u000f\u001a\u00020\u0001*\u00020\u00018\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0010\u0010\u0003\u001a\u0004\b\u0011\u0010\u0005\"\u001f\u0010\u000f\u001a\u00020\u0006*\u00020\u00068\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0010\u0010\u0007\u001a\u0004\b\u0011\u0010\b\"\u001e\u0010\u000f\u001a\u00020\t*\u00020\t8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0010\u0010\n\u001a\u0004\b\u0011\u0010\u000b\"\u001e\u0010\u000f\u001a\u00020\t*\u00020\f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0010\u0010\r\u001a\u0004\b\u0011\u0010\u0012\"\u001f\u0010\u0013\u001a\u00020\u0001*\u00020\u00018\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0014\u0010\u0003\u001a\u0004\b\u0015\u0010\u0005\"\u001f\u0010\u0013\u001a\u00020\u0006*\u00020\u00068\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0014\u0010\u0007\u001a\u0004\b\u0015\u0010\b\u00a8\u0006D"}, d2={"absoluteValue", "", "getAbsoluteValue$annotations", "(D)V", "getAbsoluteValue", "(D)D", "", "(F)V", "(F)F", "", "(I)V", "(I)I", "", "(J)V", "(J)J", "sign", "getSign$annotations", "getSign", "(J)I", "ulp", "getUlp$annotations", "getUlp", "abs", "x", "n", "acos", "acosh", "asin", "asinh", "atan", "atan2", "y", "atanh", "cbrt", "ceil", "cos", "cosh", "exp", "expm1", "floor", "hypot", "ln", "ln1p", "log", "base", "log10", "log2", "max", "a", "b", "min", "round", "sin", "sinh", "sqrt", "tan", "tanh", "truncate", "IEEErem", "divisor", "nextDown", "nextTowards", "to", "nextUp", "pow", "roundToInt", "roundToLong", "withSign", "kotlin-stdlib"}, xs="kotlin/math/MathKt")
class MathKt__MathJVMKt
extends MathKt__MathHKt {
    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double sin(double d) {
        return Math.sin(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double cos(double d) {
        return Math.cos(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double tan(double d) {
        return Math.tan(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double asin(double d) {
        return Math.asin(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double acos(double d) {
        return Math.acos(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double atan(double d) {
        return Math.atan(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double atan2(double d, double d2) {
        return Math.atan2(d, d2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double sinh(double d) {
        return Math.sinh(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double cosh(double d) {
        return Math.cosh(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double tanh(double d) {
        return Math.tanh(d);
    }

    @SinceKotlin(version="1.2")
    public static final double asinh(double d) {
        double d2;
        if (d >= Constants.taylor_n_bound) {
            d2 = d > Constants.upper_taylor_n_bound ? (d > Constants.upper_taylor_2_bound ? Math.log(d) + Constants.LN2 : Math.log(d * (double)2 + 1.0 / (d * (double)2))) : Math.log(d + Math.sqrt(d * d + 1.0));
        } else if (d <= -Constants.taylor_n_bound) {
            d2 = -MathKt.asinh(-d);
        } else {
            double d3 = d;
            if (Math.abs(d) >= Constants.taylor_2_bound) {
                d3 -= d * d * d / (double)6;
            }
            d2 = d3;
        }
        return d2;
    }

    @SinceKotlin(version="1.2")
    public static final double acosh(double d) {
        double d2;
        if (d < 1.0) {
            d2 = Double.NaN;
        } else if (d > Constants.upper_taylor_2_bound) {
            d2 = Math.log(d) + Constants.LN2;
        } else if (d - 1.0 >= Constants.taylor_n_bound) {
            d2 = Math.log(d + Math.sqrt(d * d - 1.0));
        } else {
            double d3;
            double d4 = d3 = Math.sqrt(d - 1.0);
            if (d3 >= Constants.taylor_2_bound) {
                d4 -= d3 * d3 * d3 / (double)12;
            }
            d2 = Math.sqrt(2.0) * d4;
        }
        return d2;
    }

    @SinceKotlin(version="1.2")
    public static final double atanh(double d) {
        if (Math.abs(d) < Constants.taylor_n_bound) {
            double d2 = d;
            if (Math.abs(d) > Constants.taylor_2_bound) {
                d2 += d * d * d / (double)3;
            }
            return d2;
        }
        return Math.log((1.0 + d) / (1.0 - d)) / (double)2;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double hypot(double d, double d2) {
        return Math.hypot(d, d2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double sqrt(double d) {
        return Math.sqrt(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double exp(double d) {
        return Math.exp(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double expm1(double d) {
        return Math.expm1(d);
    }

    @SinceKotlin(version="1.2")
    public static final double log(double d, double d2) {
        if (d2 <= 0.0 || d2 == 1.0) {
            return Double.NaN;
        }
        return Math.log(d) / Math.log(d2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double ln(double d) {
        return Math.log(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double log10(double d) {
        return Math.log10(d);
    }

    @SinceKotlin(version="1.2")
    public static final double log2(double d) {
        return Math.log(d) / Constants.LN2;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double ln1p(double d) {
        return Math.log1p(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double ceil(double d) {
        return Math.ceil(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double floor(double d) {
        return Math.floor(d);
    }

    @SinceKotlin(version="1.2")
    public static final double truncate(double d) {
        return Double.isNaN(d) || Double.isInfinite(d) ? d : (d > 0.0 ? Math.floor(d) : Math.ceil(d));
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double round(double d) {
        return Math.rint(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double abs(double d) {
        return Math.abs(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double sign(double d) {
        return Math.signum(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double min(double d, double d2) {
        return Math.min(d, d2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double max(double d, double d2) {
        return Math.max(d, d2);
    }

    @SinceKotlin(version="1.8")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final double cbrt(double d) {
        return Math.cbrt(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double pow(double d, double d2) {
        return Math.pow(d, d2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double pow(double d, int n) {
        return Math.pow(d, n);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double IEEErem(double d, double d2) {
        return Math.IEEEremainder(d, d2);
    }

    private static final double getAbsoluteValue(double d) {
        return Math.abs(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static void getAbsoluteValue$annotations(double d) {
    }

    private static final double getSign(double d) {
        return Math.signum(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static void getSign$annotations(double d) {
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double withSign(double d, double d2) {
        return Math.copySign(d, d2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double withSign(double d, int n) {
        return Math.copySign(d, (double)n);
    }

    private static final double getUlp(double d) {
        return Math.ulp(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static void getUlp$annotations(double d) {
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double nextUp(double d) {
        return Math.nextUp(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double nextDown(double d) {
        return Math.nextAfter(d, Double.NEGATIVE_INFINITY);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double nextTowards(double d, double d2) {
        return Math.nextAfter(d, d2);
    }

    @SinceKotlin(version="1.2")
    public static final int roundToInt(double d) {
        if (Double.isNaN(d)) {
            throw new IllegalArgumentException("Cannot round NaN value.");
        }
        return d > 2.147483647E9 ? Integer.MAX_VALUE : (d < -2.147483648E9 ? Integer.MIN_VALUE : (int)Math.round(d));
    }

    @SinceKotlin(version="1.2")
    public static final long roundToLong(double d) {
        if (Double.isNaN(d)) {
            throw new IllegalArgumentException("Cannot round NaN value.");
        }
        return Math.round(d);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float sin(float f) {
        return (float)Math.sin(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float cos(float f) {
        return (float)Math.cos(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float tan(float f) {
        return (float)Math.tan(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float asin(float f) {
        return (float)Math.asin(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float acos(float f) {
        return (float)Math.acos(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float atan(float f) {
        return (float)Math.atan(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float atan2(float f, float f2) {
        return (float)Math.atan2(f, f2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float sinh(float f) {
        return (float)Math.sinh(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float cosh(float f) {
        return (float)Math.cosh(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float tanh(float f) {
        return (float)Math.tanh(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float asinh(float f) {
        return (float)MathKt.asinh((double)f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float acosh(float f) {
        return (float)MathKt.acosh((double)f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float atanh(float f) {
        return (float)MathKt.atanh((double)f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float hypot(float f, float f2) {
        return (float)Math.hypot(f, f2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float sqrt(float f) {
        return (float)Math.sqrt(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float exp(float f) {
        return (float)Math.exp(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float expm1(float f) {
        return (float)Math.expm1(f);
    }

    @SinceKotlin(version="1.2")
    public static final float log(float f, float f2) {
        if (f2 <= 0.0f || f2 == 1.0f) {
            return Float.NaN;
        }
        return (float)(Math.log(f) / Math.log(f2));
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float ln(float f) {
        return (float)Math.log(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float log10(float f) {
        return (float)Math.log10(f);
    }

    @SinceKotlin(version="1.2")
    public static final float log2(float f) {
        return (float)(Math.log(f) / Constants.LN2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float ln1p(float f) {
        return (float)Math.log1p(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float ceil(float f) {
        return (float)Math.ceil(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float floor(float f) {
        return (float)Math.floor(f);
    }

    @SinceKotlin(version="1.2")
    public static final float truncate(float f) {
        return Float.isNaN(f) || Float.isInfinite(f) ? f : (f > 0.0f ? (float)Math.floor(f) : (float)Math.ceil(f));
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float round(float f) {
        return (float)Math.rint(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float abs(float f) {
        return Math.abs(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float sign(float f) {
        return Math.signum(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float min(float f, float f2) {
        return Math.min(f, f2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float max(float f, float f2) {
        return Math.max(f, f2);
    }

    @SinceKotlin(version="1.8")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final float cbrt(float f) {
        return (float)Math.cbrt(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float pow(float f, float f2) {
        return (float)Math.pow(f, f2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float pow(float f, int n) {
        return (float)Math.pow(f, n);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float IEEErem(float f, float f2) {
        return (float)Math.IEEEremainder(f, f2);
    }

    private static final float getAbsoluteValue(float f) {
        return Math.abs(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static void getAbsoluteValue$annotations(float f) {
    }

    private static final float getSign(float f) {
        return Math.signum(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static void getSign$annotations(float f) {
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float withSign(float f, float f2) {
        return Math.copySign(f, f2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float withSign(float f, int n) {
        return Math.copySign(f, n);
    }

    private static final float getUlp(float f) {
        return Math.ulp(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static void getUlp$annotations(float f) {
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float nextUp(float f) {
        return Math.nextUp(f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float nextDown(float f) {
        return Math.nextAfter(f, Double.NEGATIVE_INFINITY);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float nextTowards(float f, float f2) {
        return Math.nextAfter(f, (double)f2);
    }

    @SinceKotlin(version="1.2")
    public static final int roundToInt(float f) {
        if (Float.isNaN(f)) {
            throw new IllegalArgumentException("Cannot round NaN value.");
        }
        return Math.round(f);
    }

    @SinceKotlin(version="1.2")
    public static final long roundToLong(float f) {
        return MathKt.roundToLong((double)f);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final int abs(int n) {
        return Math.abs(n);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final int min(int n, int n2) {
        return Math.min(n, n2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final int max(int n, int n2) {
        return Math.max(n, n2);
    }

    private static final int getAbsoluteValue(int n) {
        return Math.abs(n);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static void getAbsoluteValue$annotations(int n) {
    }

    public static final int getSign(int n) {
        return n < 0 ? -1 : (n > 0 ? 1 : 0);
    }

    @SinceKotlin(version="1.2")
    public static void getSign$annotations(int n) {
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final long abs(long l) {
        return Math.abs(l);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final long min(long l, long l2) {
        return Math.min(l, l2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final long max(long l, long l2) {
        return Math.max(l, l2);
    }

    private static final long getAbsoluteValue(long l) {
        return Math.abs(l);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static void getAbsoluteValue$annotations(long l) {
    }

    public static final int getSign(long l) {
        return l < 0L ? -1 : (l > 0L ? 1 : 0);
    }

    @SinceKotlin(version="1.2")
    public static void getSign$annotations(long l) {
    }
}

