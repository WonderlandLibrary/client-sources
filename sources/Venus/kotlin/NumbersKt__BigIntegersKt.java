/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.Metadata;
import kotlin.NumbersKt__BigDecimalsKt;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0005\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\r\u0010\u0006\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0087\f\u001a\u0015\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0087\f\u001a\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0010\u001a\u00020\u0011*\u00020\u0001H\u0087\b\u001a!\u0010\u0010\u001a\u00020\u0011*\u00020\u00012\b\b\u0002\u0010\u0012\u001a\u00020\r2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\rH\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\u0016H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0018\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u00a8\u0006\u0019"}, d2={"and", "Ljava/math/BigInteger;", "other", "dec", "div", "inc", "inv", "minus", "or", "plus", "rem", "shl", "n", "", "shr", "times", "toBigDecimal", "Ljava/math/BigDecimal;", "scale", "mathContext", "Ljava/math/MathContext;", "toBigInteger", "", "unaryMinus", "xor", "kotlin-stdlib"}, xs="kotlin/NumbersKt")
class NumbersKt__BigIntegersKt
extends NumbersKt__BigDecimalsKt {
    @InlineOnly
    private static final BigInteger plus(BigInteger bigInteger, BigInteger bigInteger2) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        Intrinsics.checkNotNullParameter(bigInteger2, "other");
        BigInteger bigInteger3 = bigInteger.add(bigInteger2);
        Intrinsics.checkNotNullExpressionValue(bigInteger3, "this.add(other)");
        return bigInteger3;
    }

    @InlineOnly
    private static final BigInteger minus(BigInteger bigInteger, BigInteger bigInteger2) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        Intrinsics.checkNotNullParameter(bigInteger2, "other");
        BigInteger bigInteger3 = bigInteger.subtract(bigInteger2);
        Intrinsics.checkNotNullExpressionValue(bigInteger3, "this.subtract(other)");
        return bigInteger3;
    }

    @InlineOnly
    private static final BigInteger times(BigInteger bigInteger, BigInteger bigInteger2) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        Intrinsics.checkNotNullParameter(bigInteger2, "other");
        BigInteger bigInteger3 = bigInteger.multiply(bigInteger2);
        Intrinsics.checkNotNullExpressionValue(bigInteger3, "this.multiply(other)");
        return bigInteger3;
    }

    @InlineOnly
    private static final BigInteger div(BigInteger bigInteger, BigInteger bigInteger2) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        Intrinsics.checkNotNullParameter(bigInteger2, "other");
        BigInteger bigInteger3 = bigInteger.divide(bigInteger2);
        Intrinsics.checkNotNullExpressionValue(bigInteger3, "this.divide(other)");
        return bigInteger3;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final BigInteger rem(BigInteger bigInteger, BigInteger bigInteger2) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        Intrinsics.checkNotNullParameter(bigInteger2, "other");
        BigInteger bigInteger3 = bigInteger.remainder(bigInteger2);
        Intrinsics.checkNotNullExpressionValue(bigInteger3, "this.remainder(other)");
        return bigInteger3;
    }

    @InlineOnly
    private static final BigInteger unaryMinus(BigInteger bigInteger) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        BigInteger bigInteger2 = bigInteger.negate();
        Intrinsics.checkNotNullExpressionValue(bigInteger2, "this.negate()");
        return bigInteger2;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger inc(BigInteger bigInteger) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        BigInteger bigInteger2 = bigInteger.add(BigInteger.ONE);
        Intrinsics.checkNotNullExpressionValue(bigInteger2, "this.add(BigInteger.ONE)");
        return bigInteger2;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger dec(BigInteger bigInteger) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        BigInteger bigInteger2 = bigInteger.subtract(BigInteger.ONE);
        Intrinsics.checkNotNullExpressionValue(bigInteger2, "this.subtract(BigInteger.ONE)");
        return bigInteger2;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger inv(BigInteger bigInteger) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        BigInteger bigInteger2 = bigInteger.not();
        Intrinsics.checkNotNullExpressionValue(bigInteger2, "this.not()");
        return bigInteger2;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger and(BigInteger bigInteger, BigInteger bigInteger2) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        Intrinsics.checkNotNullParameter(bigInteger2, "other");
        BigInteger bigInteger3 = bigInteger.and(bigInteger2);
        Intrinsics.checkNotNullExpressionValue(bigInteger3, "this.and(other)");
        return bigInteger3;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger or(BigInteger bigInteger, BigInteger bigInteger2) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        Intrinsics.checkNotNullParameter(bigInteger2, "other");
        BigInteger bigInteger3 = bigInteger.or(bigInteger2);
        Intrinsics.checkNotNullExpressionValue(bigInteger3, "this.or(other)");
        return bigInteger3;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger xor(BigInteger bigInteger, BigInteger bigInteger2) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        Intrinsics.checkNotNullParameter(bigInteger2, "other");
        BigInteger bigInteger3 = bigInteger.xor(bigInteger2);
        Intrinsics.checkNotNullExpressionValue(bigInteger3, "this.xor(other)");
        return bigInteger3;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger shl(BigInteger bigInteger, int n) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        BigInteger bigInteger2 = bigInteger.shiftLeft(n);
        Intrinsics.checkNotNullExpressionValue(bigInteger2, "this.shiftLeft(n)");
        return bigInteger2;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger shr(BigInteger bigInteger, int n) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        BigInteger bigInteger2 = bigInteger.shiftRight(n);
        Intrinsics.checkNotNullExpressionValue(bigInteger2, "this.shiftRight(n)");
        return bigInteger2;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(int n) {
        BigInteger bigInteger = BigInteger.valueOf(n);
        Intrinsics.checkNotNullExpressionValue(bigInteger, "valueOf(this.toLong())");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(long l) {
        BigInteger bigInteger = BigInteger.valueOf(l);
        Intrinsics.checkNotNullExpressionValue(bigInteger, "valueOf(this)");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(BigInteger bigInteger) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        return new BigDecimal(bigInteger);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(BigInteger bigInteger, int n, MathContext mathContext) {
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        Intrinsics.checkNotNullParameter(mathContext, "mathContext");
        return new BigDecimal(bigInteger, n, mathContext);
    }

    static BigDecimal toBigDecimal$default(BigInteger bigInteger, int n, MathContext mathContext, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        if ((n2 & 2) != 0) {
            MathContext mathContext2 = MathContext.UNLIMITED;
            Intrinsics.checkNotNullExpressionValue(mathContext2, "UNLIMITED");
            mathContext = mathContext2;
        }
        Intrinsics.checkNotNullParameter(bigInteger, "<this>");
        Intrinsics.checkNotNullParameter(mathContext, "mathContext");
        return new BigDecimal(bigInteger, n, mathContext);
    }
}

