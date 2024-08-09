/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H\u0000\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001d"}, d2={"doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib"})
@JvmName(name="UnsignedKt")
public final class UnsignedKt {
    @PublishedApi
    public static final int uintCompare(int n, int n2) {
        return Intrinsics.compare(n ^ Integer.MIN_VALUE, n2 ^ Integer.MIN_VALUE);
    }

    @PublishedApi
    public static final int ulongCompare(long l, long l2) {
        return Intrinsics.compare(l ^ Long.MIN_VALUE, l2 ^ Long.MIN_VALUE);
    }

    @PublishedApi
    public static final int uintDivide-J1ME1BU(int n, int n2) {
        return UInt.constructor-impl((int)(((long)n & 0xFFFFFFFFL) / ((long)n2 & 0xFFFFFFFFL)));
    }

    @PublishedApi
    public static final int uintRemainder-J1ME1BU(int n, int n2) {
        return UInt.constructor-impl((int)(((long)n & 0xFFFFFFFFL) % ((long)n2 & 0xFFFFFFFFL)));
    }

    @PublishedApi
    public static final long ulongDivide-eb3DHEI(long l, long l2) {
        long l3;
        long l4 = l;
        long l5 = l2;
        if (l5 < 0L) {
            return Long.compareUnsigned(l, l2) < 0 ? ULong.constructor-impl(0L) : ULong.constructor-impl(1L);
        }
        if (l4 >= 0L) {
            return ULong.constructor-impl(l4 / l5);
        }
        long l6 = l4 - (l3 = (l4 >>> 1) / l5 << 1) * l5;
        return ULong.constructor-impl(l3 + (long)(Long.compareUnsigned(ULong.constructor-impl(l6), ULong.constructor-impl(l5)) >= 0 ? 1 : 0));
    }

    @PublishedApi
    public static final long ulongRemainder-eb3DHEI(long l, long l2) {
        long l3;
        long l4 = l;
        long l5 = l2;
        if (l5 < 0L) {
            return Long.compareUnsigned(l, l2) < 0 ? l : ULong.constructor-impl(l - l2);
        }
        if (l4 >= 0L) {
            return ULong.constructor-impl(l4 % l5);
        }
        long l6 = (l4 >>> 1) / l5 << 1;
        return ULong.constructor-impl(l3 - (Long.compareUnsigned(ULong.constructor-impl(l3 = l4 - l6 * l5), ULong.constructor-impl(l5)) >= 0 ? l5 : 0L));
    }

    @PublishedApi
    public static final int doubleToUInt(double d) {
        return Double.isNaN(d) ? 0 : (d <= UnsignedKt.uintToDouble(0) ? 0 : (d >= UnsignedKt.uintToDouble(-1) ? -1 : (d <= 2.147483647E9 ? UInt.constructor-impl((int)d) : UInt.constructor-impl(UInt.constructor-impl((int)(d - (double)Integer.MAX_VALUE)) + UInt.constructor-impl(Integer.MAX_VALUE)))));
    }

    @PublishedApi
    public static final long doubleToULong(double d) {
        return Double.isNaN(d) ? 0L : (d <= UnsignedKt.ulongToDouble(0L) ? 0L : (d >= UnsignedKt.ulongToDouble(-1L) ? -1L : (d < 9.223372036854776E18 ? ULong.constructor-impl((long)d) : ULong.constructor-impl(ULong.constructor-impl((long)(d - 9.223372036854776E18)) + Long.MIN_VALUE))));
    }

    @PublishedApi
    public static final double uintToDouble(int n) {
        return (double)(n & Integer.MAX_VALUE) + (double)(n >>> 31 << 30) * (double)2;
    }

    @PublishedApi
    public static final double ulongToDouble(long l) {
        return (double)(l >>> 11) * (double)2048 + (double)(l & 0x7FFL);
    }

    @NotNull
    public static final String ulongToString(long l) {
        return UnsignedKt.ulongToString(l, 10);
    }

    @NotNull
    public static final String ulongToString(long l, int n) {
        if (l >= 0L) {
            String string = Long.toString(l, CharsKt.checkRadix(n));
            Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
            return string;
        }
        long l2 = (l >>> 1) / (long)n << 1;
        long l3 = l - l2 * (long)n;
        if (l3 >= (long)n) {
            l3 -= (long)n;
            ++l2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string = Long.toString(l2, CharsKt.checkRadix(n));
        Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
        StringBuilder stringBuilder2 = stringBuilder.append(string);
        String string2 = Long.toString(l3, CharsKt.checkRadix(n));
        Intrinsics.checkNotNullExpressionValue(string2, "toString(this, checkRadix(radix))");
        return stringBuilder2.append(string2).toString();
    }
}

