/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin;

import kotlin.ExperimentalStdlibApi;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.internal.IntrinsicConstEvaluation;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@JvmInline
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 {2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001{B\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0000H\u0097\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u000bJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0005J\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\u000fJ\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001b\u0010\u000bJ\u001b\u0010\u0019\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001e\u0010\u0016J\u001a\u0010\u001f\u001a\u00020 2\b\u0010\t\u001a\u0004\u0018\u00010!H\u00d6\u0003\u00a2\u0006\u0004\b\"\u0010#J\u001b\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b%\u0010\u000fJ\u001b\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b&\u0010\u000bJ\u001b\u0010$\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b'\u0010\u001dJ\u001b\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b(\u0010\u0016J\u0010\u0010)\u001a\u00020\u0003H\u00d6\u0001\u00a2\u0006\u0004\b*\u0010\u0005J\u0016\u0010+\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b,\u0010\u0005J\u0016\u0010-\u001a\u00020\u0000H\u0087\b\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b.\u0010\u0005J\u001b\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b0\u0010\u000fJ\u001b\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b1\u0010\u000bJ\u001b\u0010/\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b2\u0010\u001dJ\u001b\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b3\u0010\u0016J\u001b\u00104\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\rH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b5\u00106J\u001b\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b7\u0010\u000bJ\u001b\u00104\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b8\u0010\u001dJ\u001b\u00104\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b9\u0010:J\u001b\u0010;\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f\u00f8\u0001\u0000\u00a2\u0006\u0004\b<\u0010\u000bJ\u001b\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b>\u0010\u000fJ\u001b\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b?\u0010\u000bJ\u001b\u0010=\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b@\u0010\u001dJ\u001b\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bA\u0010\u0016J\u001b\u0010B\u001a\u00020C2\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bD\u0010EJ\u001b\u0010F\u001a\u00020C2\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bG\u0010EJ\u001b\u0010H\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bI\u0010\u000fJ\u001b\u0010H\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bJ\u0010\u000bJ\u001b\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bK\u0010\u001dJ\u001b\u0010H\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bL\u0010\u0016J\u001e\u0010M\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\u0003H\u0087\f\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\bO\u0010\u000bJ\u001e\u0010P\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\u0003H\u0087\f\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\bQ\u0010\u000bJ\u001b\u0010R\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bS\u0010\u000fJ\u001b\u0010R\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bT\u0010\u000bJ\u001b\u0010R\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bU\u0010\u001dJ\u001b\u0010R\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bV\u0010\u0016J\u0010\u0010W\u001a\u00020XH\u0087\b\u00a2\u0006\u0004\bY\u0010ZJ\u0010\u0010[\u001a\u00020\\H\u0087\b\u00a2\u0006\u0004\b]\u0010^J\u0010\u0010_\u001a\u00020`H\u0087\b\u00a2\u0006\u0004\ba\u0010bJ\u0010\u0010c\u001a\u00020\u0003H\u0087\b\u00a2\u0006\u0004\bd\u0010\u0005J\u0010\u0010e\u001a\u00020fH\u0087\b\u00a2\u0006\u0004\bg\u0010hJ\u0010\u0010i\u001a\u00020jH\u0087\b\u00a2\u0006\u0004\bk\u0010lJ\u000f\u0010m\u001a\u00020nH\u0016\u00a2\u0006\u0004\bo\u0010pJ\u0016\u0010q\u001a\u00020\rH\u0087\b\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\br\u0010ZJ\u0016\u0010s\u001a\u00020\u0000H\u0087\b\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\bt\u0010\u0005J\u0016\u0010u\u001a\u00020\u0011H\u0087\b\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\bv\u0010hJ\u0016\u0010w\u001a\u00020\u0014H\u0087\b\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\bx\u0010lJ\u001b\u0010y\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f\u00f8\u0001\u0000\u00a2\u0006\u0004\bz\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003\u00f8\u0001\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!\u00a8\u0006|"}, d2={"Lkotlin/UInt;", "", "data", "", "constructor-impl", "(I)I", "getData$annotations", "()V", "and", "other", "and-WZ4Q5Ns", "(II)I", "compareTo", "Lkotlin/UByte;", "compareTo-7apg3OU", "(IB)I", "compareTo-WZ4Q5Ns", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(IJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(IS)I", "dec", "dec-pVg5ArA", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(IJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(ILjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "inc", "inc-pVg5ArA", "inv", "inv-pVg5ArA", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(IB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(IS)S", "or", "or-WZ4Q5Ns", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-WZ4Q5Ns", "(II)Lkotlin/ranges/UIntRange;", "rangeUntil", "rangeUntil-WZ4Q5Ns", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-pVg5ArA", "shr", "shr-pVg5ArA", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(I)B", "toDouble", "", "toDouble-impl", "(I)D", "toFloat", "", "toFloat-impl", "(I)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(I)J", "toShort", "", "toShort-impl", "(I)S", "toString", "", "toString-impl", "(I)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-WZ4Q5Ns", "Companion", "kotlin-stdlib"})
@SinceKotlin(version="1.5")
@WasExperimental(markerClass={ExperimentalUnsignedTypes.class})
public final class UInt
implements Comparable<UInt> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int data;
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = -1;
    public static final int SIZE_BYTES = 4;
    public static final int SIZE_BITS = 32;

    @PublishedApi
    public static void getData$annotations() {
    }

    @InlineOnly
    private static final int compareTo-7apg3OU(int n, byte by) {
        return Integer.compareUnsigned(n, UInt.constructor-impl(by & 0xFF));
    }

    @InlineOnly
    private static final int compareTo-xj2QHRw(int n, short s) {
        return Integer.compareUnsigned(n, UInt.constructor-impl(s & 0xFFFF));
    }

    @InlineOnly
    private static int compareTo-WZ4Q5Ns(int n, int n2) {
        return UnsignedKt.uintCompare(n, n2);
    }

    @InlineOnly
    private int compareTo-WZ4Q5Ns(int n) {
        return UnsignedKt.uintCompare(this.unbox-impl(), n);
    }

    @InlineOnly
    private static final int compareTo-VKZWuLQ(int n, long l) {
        return Long.compareUnsigned(ULong.constructor-impl((long)n & 0xFFFFFFFFL), l);
    }

    @InlineOnly
    private static final int plus-7apg3OU(int n, byte by) {
        return UInt.constructor-impl(n + UInt.constructor-impl(by & 0xFF));
    }

    @InlineOnly
    private static final int plus-xj2QHRw(int n, short s) {
        return UInt.constructor-impl(n + UInt.constructor-impl(s & 0xFFFF));
    }

    @InlineOnly
    private static final int plus-WZ4Q5Ns(int n, int n2) {
        return UInt.constructor-impl(n + n2);
    }

    @InlineOnly
    private static final long plus-VKZWuLQ(int n, long l) {
        return ULong.constructor-impl(ULong.constructor-impl((long)n & 0xFFFFFFFFL) + l);
    }

    @InlineOnly
    private static final int minus-7apg3OU(int n, byte by) {
        return UInt.constructor-impl(n - UInt.constructor-impl(by & 0xFF));
    }

    @InlineOnly
    private static final int minus-xj2QHRw(int n, short s) {
        return UInt.constructor-impl(n - UInt.constructor-impl(s & 0xFFFF));
    }

    @InlineOnly
    private static final int minus-WZ4Q5Ns(int n, int n2) {
        return UInt.constructor-impl(n - n2);
    }

    @InlineOnly
    private static final long minus-VKZWuLQ(int n, long l) {
        return ULong.constructor-impl(ULong.constructor-impl((long)n & 0xFFFFFFFFL) - l);
    }

    @InlineOnly
    private static final int times-7apg3OU(int n, byte by) {
        return UInt.constructor-impl(n * UInt.constructor-impl(by & 0xFF));
    }

    @InlineOnly
    private static final int times-xj2QHRw(int n, short s) {
        return UInt.constructor-impl(n * UInt.constructor-impl(s & 0xFFFF));
    }

    @InlineOnly
    private static final int times-WZ4Q5Ns(int n, int n2) {
        return UInt.constructor-impl(n * n2);
    }

    @InlineOnly
    private static final long times-VKZWuLQ(int n, long l) {
        return ULong.constructor-impl(ULong.constructor-impl((long)n & 0xFFFFFFFFL) * l);
    }

    @InlineOnly
    private static final int div-7apg3OU(int n, byte by) {
        return Integer.divideUnsigned(n, UInt.constructor-impl(by & 0xFF));
    }

    @InlineOnly
    private static final int div-xj2QHRw(int n, short s) {
        return Integer.divideUnsigned(n, UInt.constructor-impl(s & 0xFFFF));
    }

    @InlineOnly
    private static final int div-WZ4Q5Ns(int n, int n2) {
        return UnsignedKt.uintDivide-J1ME1BU(n, n2);
    }

    @InlineOnly
    private static final long div-VKZWuLQ(int n, long l) {
        return Long.divideUnsigned(ULong.constructor-impl((long)n & 0xFFFFFFFFL), l);
    }

    @InlineOnly
    private static final int rem-7apg3OU(int n, byte by) {
        return Integer.remainderUnsigned(n, UInt.constructor-impl(by & 0xFF));
    }

    @InlineOnly
    private static final int rem-xj2QHRw(int n, short s) {
        return Integer.remainderUnsigned(n, UInt.constructor-impl(s & 0xFFFF));
    }

    @InlineOnly
    private static final int rem-WZ4Q5Ns(int n, int n2) {
        return UnsignedKt.uintRemainder-J1ME1BU(n, n2);
    }

    @InlineOnly
    private static final long rem-VKZWuLQ(int n, long l) {
        return Long.remainderUnsigned(ULong.constructor-impl((long)n & 0xFFFFFFFFL), l);
    }

    @InlineOnly
    private static final int floorDiv-7apg3OU(int n, byte by) {
        return Integer.divideUnsigned(n, UInt.constructor-impl(by & 0xFF));
    }

    @InlineOnly
    private static final int floorDiv-xj2QHRw(int n, short s) {
        return Integer.divideUnsigned(n, UInt.constructor-impl(s & 0xFFFF));
    }

    @InlineOnly
    private static final int floorDiv-WZ4Q5Ns(int n, int n2) {
        return Integer.divideUnsigned(n, n2);
    }

    @InlineOnly
    private static final long floorDiv-VKZWuLQ(int n, long l) {
        return Long.divideUnsigned(ULong.constructor-impl((long)n & 0xFFFFFFFFL), l);
    }

    @InlineOnly
    private static final byte mod-7apg3OU(int n, byte by) {
        return UByte.constructor-impl((byte)Integer.remainderUnsigned(n, UInt.constructor-impl(by & 0xFF)));
    }

    @InlineOnly
    private static final short mod-xj2QHRw(int n, short s) {
        return UShort.constructor-impl((short)Integer.remainderUnsigned(n, UInt.constructor-impl(s & 0xFFFF)));
    }

    @InlineOnly
    private static final int mod-WZ4Q5Ns(int n, int n2) {
        return Integer.remainderUnsigned(n, n2);
    }

    @InlineOnly
    private static final long mod-VKZWuLQ(int n, long l) {
        return Long.remainderUnsigned(ULong.constructor-impl((long)n & 0xFFFFFFFFL), l);
    }

    @InlineOnly
    private static final int inc-pVg5ArA(int n) {
        return UInt.constructor-impl(n + 1);
    }

    @InlineOnly
    private static final int dec-pVg5ArA(int n) {
        return UInt.constructor-impl(n + -1);
    }

    @InlineOnly
    private static final UIntRange rangeTo-WZ4Q5Ns(int n, int n2) {
        return new UIntRange(n, n2, null);
    }

    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final UIntRange rangeUntil-WZ4Q5Ns(int n, int n2) {
        return URangesKt.until-J1ME1BU(n, n2);
    }

    @InlineOnly
    private static final int shl-pVg5ArA(int n, int n2) {
        return UInt.constructor-impl(n << n2);
    }

    @InlineOnly
    private static final int shr-pVg5ArA(int n, int n2) {
        return UInt.constructor-impl(n >>> n2);
    }

    @InlineOnly
    private static final int and-WZ4Q5Ns(int n, int n2) {
        return UInt.constructor-impl(n & n2);
    }

    @InlineOnly
    private static final int or-WZ4Q5Ns(int n, int n2) {
        return UInt.constructor-impl(n | n2);
    }

    @InlineOnly
    private static final int xor-WZ4Q5Ns(int n, int n2) {
        return UInt.constructor-impl(n ^ n2);
    }

    @InlineOnly
    private static final int inv-pVg5ArA(int n) {
        return UInt.constructor-impl(~n);
    }

    @InlineOnly
    private static final byte toByte-impl(int n) {
        return (byte)n;
    }

    @InlineOnly
    private static final short toShort-impl(int n) {
        return (short)n;
    }

    @InlineOnly
    private static final int toInt-impl(int n) {
        return n;
    }

    @InlineOnly
    private static final long toLong-impl(int n) {
        return (long)n & 0xFFFFFFFFL;
    }

    @InlineOnly
    private static final byte toUByte-w2LRezQ(int n) {
        return UByte.constructor-impl((byte)n);
    }

    @InlineOnly
    private static final short toUShort-Mh2AYeg(int n) {
        return UShort.constructor-impl((short)n);
    }

    @InlineOnly
    private static final int toUInt-pVg5ArA(int n) {
        return n;
    }

    @InlineOnly
    private static final long toULong-s-VKNKU(int n) {
        return ULong.constructor-impl((long)n & 0xFFFFFFFFL);
    }

    @InlineOnly
    private static final float toFloat-impl(int n) {
        return (float)UnsignedKt.uintToDouble(n);
    }

    @InlineOnly
    private static final double toDouble-impl(int n) {
        return UnsignedKt.uintToDouble(n);
    }

    @NotNull
    public static String toString-impl(int n) {
        return String.valueOf((long)n & 0xFFFFFFFFL);
    }

    @NotNull
    public String toString() {
        return UInt.toString-impl(this.data);
    }

    public static int hashCode-impl(int n) {
        return Integer.hashCode(n);
    }

    public int hashCode() {
        return UInt.hashCode-impl(this.data);
    }

    public static boolean equals-impl(int n, Object object) {
        if (!(object instanceof UInt)) {
            return true;
        }
        int n2 = ((UInt)object).unbox-impl();
        return n != n2;
    }

    public boolean equals(Object object) {
        return UInt.equals-impl(this.data, object);
    }

    @IntrinsicConstEvaluation
    @PublishedApi
    private UInt(int n) {
        this.data = n;
    }

    @IntrinsicConstEvaluation
    @PublishedApi
    public static int constructor-impl(int n) {
        return n;
    }

    public static final UInt box-impl(int n) {
        return new UInt(n);
    }

    public final int unbox-impl() {
        return this.data;
    }

    public static final boolean equals-impl0(int n, int n2) {
        return n == n2;
    }

    @Override
    public int compareTo(Object object) {
        UInt uInt = this;
        int n = ((UInt)object).unbox-impl();
        return UnsignedKt.uintCompare(uInt.unbox-impl(), n);
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004X\u0086T\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004X\u0086T\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!\u00a8\u0006\n"}, d2={"Lkotlin/UInt$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UInt;", "I", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"})
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}

