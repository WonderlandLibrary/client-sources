/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.comparisons.ComparisonsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmInline;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.ranges.LongRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlin.time.DurationJvmKt;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlin.time.DurationUnitKt;
import kotlin.time.ExperimentalTime;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@JvmInline
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0087@\u0018\u0000 \u00a6\u00012\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002\u00a6\u0001B\u0014\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005J%\u0010D\u001a\u00020\u00002\u0006\u0010E\u001a\u00020\u00032\u0006\u0010F\u001a\u00020\u0003H\u0002\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\bG\u0010HJ\u001b\u0010I\u001a\u00020\t2\u0006\u0010J\u001a\u00020\u0000H\u0096\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\bK\u0010LJ\u001e\u0010M\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\u000fH\u0086\u0002\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\bO\u0010PJ\u001e\u0010M\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\tH\u0086\u0002\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\bO\u0010QJ\u001b\u0010M\u001a\u00020\u000f2\u0006\u0010J\u001a\u00020\u0000H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\bR\u0010SJ\u001a\u0010T\u001a\u00020U2\b\u0010J\u001a\u0004\u0018\u00010VH\u00d6\u0003\u00a2\u0006\u0004\bW\u0010XJ\u0010\u0010Y\u001a\u00020\tH\u00d6\u0001\u00a2\u0006\u0004\bZ\u0010\rJ\r\u0010[\u001a\u00020U\u00a2\u0006\u0004\b\\\u0010]J\u000f\u0010^\u001a\u00020UH\u0002\u00a2\u0006\u0004\b_\u0010]J\u000f\u0010`\u001a\u00020UH\u0002\u00a2\u0006\u0004\ba\u0010]J\r\u0010b\u001a\u00020U\u00a2\u0006\u0004\bc\u0010]J\r\u0010d\u001a\u00020U\u00a2\u0006\u0004\be\u0010]J\r\u0010f\u001a\u00020U\u00a2\u0006\u0004\bg\u0010]J\u001b\u0010h\u001a\u00020\u00002\u0006\u0010J\u001a\u00020\u0000H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\bi\u0010jJ\u001b\u0010k\u001a\u00020\u00002\u0006\u0010J\u001a\u00020\u0000H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\bl\u0010jJ\u001e\u0010m\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\u000fH\u0086\u0002\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\bn\u0010PJ\u001e\u0010m\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\tH\u0086\u0002\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\bn\u0010QJ\u009d\u0001\u0010o\u001a\u0002Hp\"\u0004\b\u0000\u0010p2u\u0010q\u001aq\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(u\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(v\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(w\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0rH\u0086\b\u00f8\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0004\bz\u0010{J\u0088\u0001\u0010o\u001a\u0002Hp\"\u0004\b\u0000\u0010p2`\u0010q\u001a\\\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(v\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(w\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0|H\u0086\b\u00f8\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0004\bz\u0010}Js\u0010o\u001a\u0002Hp\"\u0004\b\u0000\u0010p2K\u0010q\u001aG\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(w\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0~H\u0086\b\u00f8\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0004\bz\u0010\u007fJ`\u0010o\u001a\u0002Hp\"\u0004\b\u0000\u0010p27\u0010q\u001a3\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0\u0080\u0001H\u0086\b\u00f8\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0005\bz\u0010\u0081\u0001J\u0019\u0010\u0082\u0001\u001a\u00020\u000f2\u0007\u0010\u0083\u0001\u001a\u00020=\u00a2\u0006\u0006\b\u0084\u0001\u0010\u0085\u0001J\u0019\u0010\u0086\u0001\u001a\u00020\t2\u0007\u0010\u0083\u0001\u001a\u00020=\u00a2\u0006\u0006\b\u0087\u0001\u0010\u0088\u0001J\u0011\u0010\u0089\u0001\u001a\u00030\u008a\u0001\u00a2\u0006\u0006\b\u008b\u0001\u0010\u008c\u0001J\u0019\u0010\u008d\u0001\u001a\u00020\u00032\u0007\u0010\u0083\u0001\u001a\u00020=\u00a2\u0006\u0006\b\u008e\u0001\u0010\u008f\u0001J\u0011\u0010\u0090\u0001\u001a\u00020\u0003H\u0007\u00a2\u0006\u0005\b\u0091\u0001\u0010\u0005J\u0011\u0010\u0092\u0001\u001a\u00020\u0003H\u0007\u00a2\u0006\u0005\b\u0093\u0001\u0010\u0005J\u0013\u0010\u0094\u0001\u001a\u00030\u008a\u0001H\u0016\u00a2\u0006\u0006\b\u0095\u0001\u0010\u008c\u0001J%\u0010\u0094\u0001\u001a\u00030\u008a\u00012\u0007\u0010\u0083\u0001\u001a\u00020=2\t\b\u0002\u0010\u0096\u0001\u001a\u00020\t\u00a2\u0006\u0006\b\u0095\u0001\u0010\u0097\u0001J!\u0010\u0098\u0001\u001a\u00020\u00002\u0007\u0010\u0083\u0001\u001a\u00020=H\u0000\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0006\b\u0099\u0001\u0010\u008f\u0001J\u0018\u0010\u009a\u0001\u001a\u00020\u0000H\u0086\u0002\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0005\b\u009b\u0001\u0010\u0005JK\u0010\u009c\u0001\u001a\u00030\u009d\u0001*\b0\u009e\u0001j\u0003`\u009f\u00012\u0007\u0010\u00a0\u0001\u001a\u00020\t2\u0007\u0010\u00a1\u0001\u001a\u00020\t2\u0007\u0010\u00a2\u0001\u001a\u00020\t2\b\u0010\u0083\u0001\u001a\u00030\u008a\u00012\u0007\u0010\u00a3\u0001\u001a\u00020UH\u0002\u00a2\u0006\u0006\b\u00a4\u0001\u0010\u00a5\u0001R\u0017\u0010\u0006\u001a\u00020\u00008F\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u000f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0014\u0010\u000b\u001a\u0004\b\u0015\u0010\u0012R\u001a\u0010\u0016\u001a\u00020\u000f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0018\u0010\u0012R\u001a\u0010\u0019\u001a\u00020\u000f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u001a\u0010\u000b\u001a\u0004\b\u001b\u0010\u0012R\u001a\u0010\u001c\u001a\u00020\u000f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\u0012R\u001a\u0010\u001f\u001a\u00020\u000f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\u0012R\u001a\u0010\"\u001a\u00020\u000f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b#\u0010\u000b\u001a\u0004\b$\u0010\u0012R\u0011\u0010%\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b&\u0010\u0005R\u0011\u0010'\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b(\u0010\u0005R\u0011\u0010)\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b*\u0010\u0005R\u0011\u0010+\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b,\u0010\u0005R\u0011\u0010-\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b.\u0010\u0005R\u0011\u0010/\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b0\u0010\u0005R\u0011\u00101\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b2\u0010\u0005R\u001a\u00103\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b4\u0010\u000b\u001a\u0004\b5\u0010\rR\u001a\u00106\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b7\u0010\u000b\u001a\u0004\b8\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u00109\u001a\u00020\t8@X\u0081\u0004\u00a2\u0006\f\u0012\u0004\b:\u0010\u000b\u001a\u0004\b;\u0010\rR\u0014\u0010<\u001a\u00020=8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b>\u0010?R\u0015\u0010@\u001a\u00020\t8\u00c2\u0002X\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\bA\u0010\rR\u0014\u0010B\u001a\u00020\u00038BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\bC\u0010\u0005\u0088\u0001\u0002\u0092\u0001\u00020\u0003\u00f8\u0001\u0000\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u009920\u0001\u00a8\u0006\u00a7\u0001"}, d2={"Lkotlin/time/Duration;", "", "rawValue", "", "constructor-impl", "(J)J", "absoluteValue", "getAbsoluteValue-UwyO8pc", "hoursComponent", "", "getHoursComponent$annotations", "()V", "getHoursComponent-impl", "(J)I", "inDays", "", "getInDays$annotations", "getInDays-impl", "(J)D", "inHours", "getInHours$annotations", "getInHours-impl", "inMicroseconds", "getInMicroseconds$annotations", "getInMicroseconds-impl", "inMilliseconds", "getInMilliseconds$annotations", "getInMilliseconds-impl", "inMinutes", "getInMinutes$annotations", "getInMinutes-impl", "inNanoseconds", "getInNanoseconds$annotations", "getInNanoseconds-impl", "inSeconds", "getInSeconds$annotations", "getInSeconds-impl", "inWholeDays", "getInWholeDays-impl", "inWholeHours", "getInWholeHours-impl", "inWholeMicroseconds", "getInWholeMicroseconds-impl", "inWholeMilliseconds", "getInWholeMilliseconds-impl", "inWholeMinutes", "getInWholeMinutes-impl", "inWholeNanoseconds", "getInWholeNanoseconds-impl", "inWholeSeconds", "getInWholeSeconds-impl", "minutesComponent", "getMinutesComponent$annotations", "getMinutesComponent-impl", "nanosecondsComponent", "getNanosecondsComponent$annotations", "getNanosecondsComponent-impl", "secondsComponent", "getSecondsComponent$annotations", "getSecondsComponent-impl", "storageUnit", "Lkotlin/time/DurationUnit;", "getStorageUnit-impl", "(J)Lkotlin/time/DurationUnit;", "unitDiscriminator", "getUnitDiscriminator-impl", "value", "getValue-impl", "addValuesMixedRanges", "thisMillis", "otherNanos", "addValuesMixedRanges-UwyO8pc", "(JJJ)J", "compareTo", "other", "compareTo-LRDsOJo", "(JJ)I", "div", "scale", "div-UwyO8pc", "(JD)J", "(JI)J", "div-LRDsOJo", "(JJ)D", "equals", "", "", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "isFinite", "isFinite-impl", "(J)Z", "isInMillis", "isInMillis-impl", "isInNanos", "isInNanos-impl", "isInfinite", "isInfinite-impl", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "minus", "minus-LRDsOJo", "(JJ)J", "plus", "plus-LRDsOJo", "times", "times-UwyO8pc", "toComponents", "T", "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(JLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(JLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(JLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "(JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "toDouble", "unit", "toDouble-impl", "(JLkotlin/time/DurationUnit;)D", "toInt", "toInt-impl", "(JLkotlin/time/DurationUnit;)I", "toIsoString", "", "toIsoString-impl", "(J)Ljava/lang/String;", "toLong", "toLong-impl", "(JLkotlin/time/DurationUnit;)J", "toLongMilliseconds", "toLongMilliseconds-impl", "toLongNanoseconds", "toLongNanoseconds-impl", "toString", "toString-impl", "decimals", "(JLkotlin/time/DurationUnit;I)Ljava/lang/String;", "truncateTo", "truncateTo-UwyO8pc$kotlin_stdlib", "unaryMinus", "unaryMinus-UwyO8pc", "appendFractional", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "whole", "fractional", "fractionalSize", "isoZeroes", "appendFractional-impl", "(JLjava/lang/StringBuilder;IIILjava/lang/String;Z)V", "Companion", "kotlin-stdlib"})
@SinceKotlin(version="1.6")
@WasExperimental(markerClass={ExperimentalTime.class})
@SourceDebugExtension(value={"SMAP\nDuration.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Duration.kt\nkotlin/time/Duration\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1495:1\n38#1:1496\n38#1:1497\n38#1:1498\n38#1:1499\n38#1:1500\n683#1,2:1501\n700#1,2:1510\n163#2,6:1503\n1#3:1509\n*S KotlinDebug\n*F\n+ 1 Duration.kt\nkotlin/time/Duration\n*L\n39#1:1496\n40#1:1497\n458#1:1498\n478#1:1499\n662#1:1500\n979#1:1501,2\n1070#1:1510,2\n1021#1:1503,6\n*E\n"})
public final class Duration
implements Comparable<Duration> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final long rawValue;
    private static final long ZERO = Duration.constructor-impl(0L);
    private static final long INFINITE = DurationKt.access$durationOfMillis(0x3FFFFFFFFFFFFFFFL);
    private static final long NEG_INFINITE = DurationKt.access$durationOfMillis(-4611686018427387903L);

    private static final long getValue-impl(long l) {
        return l >> 1;
    }

    private static final int getUnitDiscriminator-impl(long l) {
        boolean bl = false;
        return (int)l & 1;
    }

    private static final boolean isInNanos-impl(long l) {
        boolean bl = false;
        return ((int)l & 1) == 0;
    }

    private static final boolean isInMillis-impl(long l) {
        boolean bl = false;
        return ((int)l & 1) == 1;
    }

    private static final DurationUnit getStorageUnit-impl(long l) {
        return Duration.isInNanos-impl(l) ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS;
    }

    public static final long unaryMinus-UwyO8pc(long l) {
        boolean bl = false;
        return DurationKt.access$durationOf(-Duration.getValue-impl(l), (int)l & 1);
    }

    public static final long plus-LRDsOJo(long l, long l2) {
        long l3;
        if (Duration.isInfinite-impl(l)) {
            if (Duration.isFinite-impl(l2) || (l ^ l2) >= 0L) {
                return l;
            }
            throw new IllegalArgumentException("Summing infinite durations of different signs yields an undefined result.");
        }
        if (Duration.isInfinite-impl(l2)) {
            return l2;
        }
        boolean bl = false;
        bl = false;
        if (((int)l & 1) == ((int)l2 & 1)) {
            long l4 = Duration.getValue-impl(l) + Duration.getValue-impl(l2);
            l3 = Duration.isInNanos-impl(l) ? DurationKt.access$durationOfNanosNormalized(l4) : DurationKt.access$durationOfMillisNormalized(l4);
        } else {
            l3 = Duration.isInMillis-impl(l) ? Duration.addValuesMixedRanges-UwyO8pc(l, Duration.getValue-impl(l), Duration.getValue-impl(l2)) : Duration.addValuesMixedRanges-UwyO8pc(l, Duration.getValue-impl(l2), Duration.getValue-impl(l));
        }
        return l3;
    }

    private static final long addValuesMixedRanges-UwyO8pc(long l, long l2, long l3) {
        long l4;
        long l5 = DurationKt.access$nanosToMillis(l3);
        long l6 = l2 + l5;
        if (new LongRange(-4611686018426L, 4611686018426L).contains(l6)) {
            long l7 = l3 - DurationKt.access$millisToNanos(l5);
            l4 = DurationKt.access$durationOfNanos(DurationKt.access$millisToNanos(l6) + l7);
        } else {
            l4 = DurationKt.access$durationOfMillis(RangesKt.coerceIn(l6, -4611686018427387903L, 0x3FFFFFFFFFFFFFFFL));
        }
        return l4;
    }

    public static final long minus-LRDsOJo(long l, long l2) {
        return Duration.plus-LRDsOJo(l, Duration.unaryMinus-UwyO8pc(l2));
    }

    public static final long times-UwyO8pc(long l, int n) {
        long l2;
        if (Duration.isInfinite-impl(l)) {
            if (n == 0) {
                throw new IllegalArgumentException("Multiplying infinite duration by zero yields an undefined result.");
            }
            return n > 0 ? l : Duration.unaryMinus-UwyO8pc(l);
        }
        if (n == 0) {
            return ZERO;
        }
        long l3 = Duration.getValue-impl(l);
        long l4 = l3 * (long)n;
        if (Duration.isInNanos-impl(l)) {
            if (new LongRange(-2147483647L, Integer.MAX_VALUE).contains(l3)) {
                l2 = DurationKt.access$durationOfNanos(l4);
            } else if (l4 / (long)n == l3) {
                l2 = DurationKt.access$durationOfNanosNormalized(l4);
            } else {
                long l5 = DurationKt.access$nanosToMillis(l3);
                long l6 = l3 - DurationKt.access$millisToNanos(l5);
                long l7 = l5 * (long)n;
                long l8 = l7 + DurationKt.access$nanosToMillis(l6 * (long)n);
                l2 = l7 / (long)n == l5 && (l8 ^ l7) >= 0L ? DurationKt.access$durationOfMillis(RangesKt.coerceIn(l8, new LongRange(-4611686018427387903L, 0x3FFFFFFFFFFFFFFFL))) : (MathKt.getSign(l3) * MathKt.getSign(n) > 0 ? INFINITE : NEG_INFINITE);
            }
        } else {
            l2 = l4 / (long)n == l3 ? DurationKt.access$durationOfMillis(RangesKt.coerceIn(l4, new LongRange(-4611686018427387903L, 0x3FFFFFFFFFFFFFFFL))) : (MathKt.getSign(l3) * MathKt.getSign(n) > 0 ? INFINITE : NEG_INFINITE);
        }
        return l2;
    }

    public static final long times-UwyO8pc(long l, double d) {
        int n = MathKt.roundToInt(d);
        if ((double)n == d) {
            return Duration.times-UwyO8pc(l, n);
        }
        DurationUnit durationUnit = Duration.getStorageUnit-impl(l);
        double d2 = Duration.toDouble-impl(l, durationUnit) * d;
        return DurationKt.toDuration(d2, durationUnit);
    }

    public static final long div-UwyO8pc(long l, int n) {
        if (n == 0) {
            long l2;
            if (Duration.isPositive-impl(l)) {
                l2 = INFINITE;
            } else if (Duration.isNegative-impl(l)) {
                l2 = NEG_INFINITE;
            } else {
                throw new IllegalArgumentException("Dividing zero duration by zero yields an undefined result.");
            }
            return l2;
        }
        if (Duration.isInNanos-impl(l)) {
            return DurationKt.access$durationOfNanos(Duration.getValue-impl(l) / (long)n);
        }
        if (Duration.isInfinite-impl(l)) {
            return Duration.times-UwyO8pc(l, MathKt.getSign(n));
        }
        long l3 = Duration.getValue-impl(l) / (long)n;
        if (new LongRange(-4611686018426L, 4611686018426L).contains(l3)) {
            long l4 = DurationKt.access$millisToNanos(Duration.getValue-impl(l) - l3 * (long)n) / (long)n;
            return DurationKt.access$durationOfNanos(DurationKt.access$millisToNanos(l3) + l4);
        }
        return DurationKt.access$durationOfMillis(l3);
    }

    public static final long div-UwyO8pc(long l, double d) {
        int n = MathKt.roundToInt(d);
        if ((double)n == d && n != 0) {
            return Duration.div-UwyO8pc(l, n);
        }
        DurationUnit durationUnit = Duration.getStorageUnit-impl(l);
        double d2 = Duration.toDouble-impl(l, durationUnit) / d;
        return DurationKt.toDuration(d2, durationUnit);
    }

    public static final double div-LRDsOJo(long l, long l2) {
        DurationUnit durationUnit = (DurationUnit)((Object)ComparisonsKt.maxOf((Comparable)((Object)Duration.getStorageUnit-impl(l)), (Comparable)((Object)Duration.getStorageUnit-impl(l2))));
        return Duration.toDouble-impl(l, durationUnit) / Duration.toDouble-impl(l2, durationUnit);
    }

    public static final long truncateTo-UwyO8pc$kotlin_stdlib(long l, @NotNull DurationUnit durationUnit) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        DurationUnit durationUnit2 = Duration.getStorageUnit-impl(l);
        if (durationUnit.compareTo((Enum)durationUnit2) <= 0 || Duration.isInfinite-impl(l)) {
            return l;
        }
        long l2 = DurationUnitKt.convertDurationUnit(1L, durationUnit, durationUnit2);
        long l3 = Duration.getValue-impl(l) - Duration.getValue-impl(l) % l2;
        return DurationKt.toDuration(l3, durationUnit2);
    }

    public static final boolean isNegative-impl(long l) {
        return l < 0L;
    }

    public static final boolean isPositive-impl(long l) {
        return l > 0L;
    }

    public static final boolean isInfinite-impl(long l) {
        return l == INFINITE || l == NEG_INFINITE;
    }

    public static final boolean isFinite-impl(long l) {
        return !Duration.isInfinite-impl(l);
    }

    public static final long getAbsoluteValue-UwyO8pc(long l) {
        return Duration.isNegative-impl(l) ? Duration.unaryMinus-UwyO8pc(l) : l;
    }

    public static int compareTo-LRDsOJo(long l, long l2) {
        long l3 = l ^ l2;
        if (l3 < 0L || ((int)l3 & 1) == 0) {
            return Intrinsics.compare(l, l2);
        }
        boolean bl = false;
        bl = false;
        int n = ((int)l & 1) - ((int)l2 & 1);
        return Duration.isNegative-impl(l) ? -n : n;
    }

    public int compareTo-LRDsOJo(long l) {
        return Duration.compareTo-LRDsOJo(this.rawValue, l);
    }

    public static final <T> T toComponents-impl(long l, @NotNull Function5<? super Long, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> function5) {
        Intrinsics.checkNotNullParameter(function5, "action");
        boolean bl = false;
        return function5.invoke(Duration.getInWholeDays-impl(l), Duration.getHoursComponent-impl(l), Duration.getMinutesComponent-impl(l), Duration.getSecondsComponent-impl(l), Duration.getNanosecondsComponent-impl(l));
    }

    public static final <T> T toComponents-impl(long l, @NotNull Function4<? super Long, ? super Integer, ? super Integer, ? super Integer, ? extends T> function4) {
        Intrinsics.checkNotNullParameter(function4, "action");
        boolean bl = false;
        return function4.invoke(Duration.getInWholeHours-impl(l), Duration.getMinutesComponent-impl(l), Duration.getSecondsComponent-impl(l), Duration.getNanosecondsComponent-impl(l));
    }

    public static final <T> T toComponents-impl(long l, @NotNull Function3<? super Long, ? super Integer, ? super Integer, ? extends T> function3) {
        Intrinsics.checkNotNullParameter(function3, "action");
        boolean bl = false;
        return function3.invoke(Duration.getInWholeMinutes-impl(l), Duration.getSecondsComponent-impl(l), Duration.getNanosecondsComponent-impl(l));
    }

    public static final <T> T toComponents-impl(long l, @NotNull Function2<? super Long, ? super Integer, ? extends T> function2) {
        Intrinsics.checkNotNullParameter(function2, "action");
        boolean bl = false;
        return function2.invoke(Duration.getInWholeSeconds-impl(l), Duration.getNanosecondsComponent-impl(l));
    }

    public static final int getHoursComponent-impl(long l) {
        return Duration.isInfinite-impl(l) ? 0 : (int)(Duration.getInWholeHours-impl(l) % (long)24);
    }

    @PublishedApi
    public static void getHoursComponent$annotations() {
    }

    public static final int getMinutesComponent-impl(long l) {
        return Duration.isInfinite-impl(l) ? 0 : (int)(Duration.getInWholeMinutes-impl(l) % (long)60);
    }

    @PublishedApi
    public static void getMinutesComponent$annotations() {
    }

    public static final int getSecondsComponent-impl(long l) {
        return Duration.isInfinite-impl(l) ? 0 : (int)(Duration.getInWholeSeconds-impl(l) % (long)60);
    }

    @PublishedApi
    public static void getSecondsComponent$annotations() {
    }

    public static final int getNanosecondsComponent-impl(long l) {
        return Duration.isInfinite-impl(l) ? 0 : (Duration.isInMillis-impl(l) ? (int)DurationKt.access$millisToNanos(Duration.getValue-impl(l) % (long)1000) : (int)(Duration.getValue-impl(l) % (long)1000000000));
    }

    @PublishedApi
    public static void getNanosecondsComponent$annotations() {
    }

    public static final double toDouble-impl(long l, @NotNull DurationUnit durationUnit) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        long l2 = l;
        return l2 == INFINITE ? Double.POSITIVE_INFINITY : (l2 == NEG_INFINITE ? Double.NEGATIVE_INFINITY : DurationUnitKt.convertDurationUnit((double)Duration.getValue-impl(l), Duration.getStorageUnit-impl(l), durationUnit));
    }

    public static final long toLong-impl(long l, @NotNull DurationUnit durationUnit) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        long l2 = l;
        return l2 == INFINITE ? Long.MAX_VALUE : (l2 == NEG_INFINITE ? Long.MIN_VALUE : DurationUnitKt.convertDurationUnit(Duration.getValue-impl(l), Duration.getStorageUnit-impl(l), durationUnit));
    }

    public static final int toInt-impl(long l, @NotNull DurationUnit durationUnit) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        return (int)RangesKt.coerceIn(Duration.toLong-impl(l, durationUnit), Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static final double getInDays-impl(long l) {
        return Duration.toDouble-impl(l, DurationUnit.DAYS);
    }

    @Deprecated(message="Use inWholeDays property instead or convert toDouble(DAYS) if a double value is required.", replaceWith=@ReplaceWith(expression="toDouble(DurationUnit.DAYS)", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @ExperimentalTime
    public static void getInDays$annotations() {
    }

    public static final double getInHours-impl(long l) {
        return Duration.toDouble-impl(l, DurationUnit.HOURS);
    }

    @Deprecated(message="Use inWholeHours property instead or convert toDouble(HOURS) if a double value is required.", replaceWith=@ReplaceWith(expression="toDouble(DurationUnit.HOURS)", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @ExperimentalTime
    public static void getInHours$annotations() {
    }

    public static final double getInMinutes-impl(long l) {
        return Duration.toDouble-impl(l, DurationUnit.MINUTES);
    }

    @Deprecated(message="Use inWholeMinutes property instead or convert toDouble(MINUTES) if a double value is required.", replaceWith=@ReplaceWith(expression="toDouble(DurationUnit.MINUTES)", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @ExperimentalTime
    public static void getInMinutes$annotations() {
    }

    public static final double getInSeconds-impl(long l) {
        return Duration.toDouble-impl(l, DurationUnit.SECONDS);
    }

    @Deprecated(message="Use inWholeSeconds property instead or convert toDouble(SECONDS) if a double value is required.", replaceWith=@ReplaceWith(expression="toDouble(DurationUnit.SECONDS)", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @ExperimentalTime
    public static void getInSeconds$annotations() {
    }

    public static final double getInMilliseconds-impl(long l) {
        return Duration.toDouble-impl(l, DurationUnit.MILLISECONDS);
    }

    @Deprecated(message="Use inWholeMilliseconds property instead or convert toDouble(MILLISECONDS) if a double value is required.", replaceWith=@ReplaceWith(expression="toDouble(DurationUnit.MILLISECONDS)", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @ExperimentalTime
    public static void getInMilliseconds$annotations() {
    }

    public static final double getInMicroseconds-impl(long l) {
        return Duration.toDouble-impl(l, DurationUnit.MICROSECONDS);
    }

    @Deprecated(message="Use inWholeMicroseconds property instead or convert toDouble(MICROSECONDS) if a double value is required.", replaceWith=@ReplaceWith(expression="toDouble(DurationUnit.MICROSECONDS)", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @ExperimentalTime
    public static void getInMicroseconds$annotations() {
    }

    public static final double getInNanoseconds-impl(long l) {
        return Duration.toDouble-impl(l, DurationUnit.NANOSECONDS);
    }

    @Deprecated(message="Use inWholeNanoseconds property instead or convert toDouble(NANOSECONDS) if a double value is required.", replaceWith=@ReplaceWith(expression="toDouble(DurationUnit.NANOSECONDS)", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @ExperimentalTime
    public static void getInNanoseconds$annotations() {
    }

    public static final long getInWholeDays-impl(long l) {
        return Duration.toLong-impl(l, DurationUnit.DAYS);
    }

    public static final long getInWholeHours-impl(long l) {
        return Duration.toLong-impl(l, DurationUnit.HOURS);
    }

    public static final long getInWholeMinutes-impl(long l) {
        return Duration.toLong-impl(l, DurationUnit.MINUTES);
    }

    public static final long getInWholeSeconds-impl(long l) {
        return Duration.toLong-impl(l, DurationUnit.SECONDS);
    }

    public static final long getInWholeMilliseconds-impl(long l) {
        return Duration.isInMillis-impl(l) && Duration.isFinite-impl(l) ? Duration.getValue-impl(l) : Duration.toLong-impl(l, DurationUnit.MILLISECONDS);
    }

    public static final long getInWholeMicroseconds-impl(long l) {
        return Duration.toLong-impl(l, DurationUnit.MICROSECONDS);
    }

    public static final long getInWholeNanoseconds-impl(long l) {
        long l2 = Duration.getValue-impl(l);
        return Duration.isInNanos-impl(l) ? l2 : (l2 > 9223372036854L ? Long.MAX_VALUE : (l2 < -9223372036854L ? Long.MIN_VALUE : DurationKt.access$millisToNanos(l2)));
    }

    @Deprecated(message="Use inWholeNanoseconds property instead.", replaceWith=@ReplaceWith(expression="this.inWholeNanoseconds", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @ExperimentalTime
    public static final long toLongNanoseconds-impl(long l) {
        return Duration.getInWholeNanoseconds-impl(l);
    }

    @Deprecated(message="Use inWholeMilliseconds property instead.", replaceWith=@ReplaceWith(expression="this.inWholeMilliseconds", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @ExperimentalTime
    public static final long toLongMilliseconds-impl(long l) {
        return Duration.getInWholeMilliseconds-impl(l);
    }

    @NotNull
    public static String toString-impl(long l) {
        String string;
        long l2 = l;
        if (l2 == 0L) {
            string = "0s";
        } else if (l2 == INFINITE) {
            string = "Infinity";
        } else if (l2 == NEG_INFINITE) {
            string = "-Infinity";
        } else {
            StringBuilder stringBuilder;
            boolean bl = Duration.isNegative-impl(l);
            StringBuilder stringBuilder2 = stringBuilder = new StringBuilder();
            boolean bl2 = false;
            if (bl) {
                stringBuilder2.append('-');
            }
            long l3 = Duration.getAbsoluteValue-UwyO8pc(l);
            boolean bl3 = false;
            int n = Duration.getNanosecondsComponent-impl(l3);
            int n2 = Duration.getSecondsComponent-impl(l3);
            int n3 = Duration.getMinutesComponent-impl(l3);
            int n4 = Duration.getHoursComponent-impl(l3);
            long l4 = Duration.getInWholeDays-impl(l3);
            boolean bl4 = false;
            boolean bl5 = l4 != 0L;
            boolean bl6 = n4 != 0;
            boolean bl7 = n3 != 0;
            boolean bl8 = n2 != 0 || n != 0;
            int n5 = 0;
            if (bl5) {
                stringBuilder2.append(l4).append('d');
                ++n5;
            }
            if (bl6 || bl5 && (bl7 || bl8)) {
                if (n5++ > 0) {
                    stringBuilder2.append(' ');
                }
                stringBuilder2.append(n4).append('h');
            }
            if (bl7 || bl8 && (bl6 || bl5)) {
                if (n5++ > 0) {
                    stringBuilder2.append(' ');
                }
                stringBuilder2.append(n3).append('m');
            }
            if (bl8) {
                if (n5++ > 0) {
                    stringBuilder2.append(' ');
                }
                if (n2 != 0 || bl5 || bl6 || bl7) {
                    Duration.appendFractional-impl(l, stringBuilder2, n2, n, 9, "s", false);
                } else if (n >= 1000000) {
                    Duration.appendFractional-impl(l, stringBuilder2, n / 1000000, n % 1000000, 6, "ms", false);
                } else if (n >= 1000) {
                    Duration.appendFractional-impl(l, stringBuilder2, n / 1000, n % 1000, 3, "us", false);
                } else {
                    stringBuilder2.append(n).append("ns");
                }
            }
            if (bl && n5 > 1) {
                stringBuilder2.insert(1, '(').append(')');
            }
            String string2 = stringBuilder.toString();
            string = string2;
            Intrinsics.checkNotNullExpressionValue(string2, "StringBuilder().apply(builderAction).toString()");
        }
        return string;
    }

    @NotNull
    public String toString() {
        return Duration.toString-impl(this.rawValue);
    }

    private static final void appendFractional-impl(long l, StringBuilder stringBuilder, int n, int n2, int n3, String string, boolean bl) {
        stringBuilder.append(n);
        if (n2 != 0) {
            int n4;
            String string2;
            block5: {
                stringBuilder.append('.');
                string2 = StringsKt.padStart(String.valueOf(n2), n3, '0');
                CharSequence charSequence = string2;
                boolean bl2 = false;
                int n5 = charSequence.length() + -1;
                if (0 <= n5) {
                    do {
                        int n6 = n5--;
                        char c = charSequence.charAt(n6);
                        boolean bl3 = false;
                        if (!(c != '0')) continue;
                        n4 = n6;
                        break block5;
                    } while (0 <= n5);
                }
                n4 = -1;
            }
            int n7 = n4 + 1;
            if (!bl && n7 < 3) {
                Intrinsics.checkNotNullExpressionValue(stringBuilder.append(string2, 0, n7), "this.append(value, startIndex, endIndex)");
            } else {
                Intrinsics.checkNotNullExpressionValue(stringBuilder.append(string2, 0, (n7 + 2) / 3 * 3), "this.append(value, startIndex, endIndex)");
            }
        }
        stringBuilder.append(string);
    }

    @NotNull
    public static final String toString-impl(long l, @NotNull DurationUnit durationUnit, int n) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            boolean bl3 = false;
            String string = "decimals must be not negative, but was " + n;
            throw new IllegalArgumentException(string.toString());
        }
        double d = Duration.toDouble-impl(l, durationUnit);
        if (Double.isInfinite(d)) {
            return String.valueOf(d);
        }
        return DurationJvmKt.formatToExactDecimals(d, RangesKt.coerceAtMost(n, 12)) + DurationUnitKt.shortName(durationUnit);
    }

    public static String toString-impl$default(long l, DurationUnit durationUnit, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        return Duration.toString-impl(l, durationUnit, n);
    }

    @NotNull
    public static final String toIsoString-impl(long l) {
        boolean bl;
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2 = stringBuilder = new StringBuilder();
        boolean bl2 = false;
        if (Duration.isNegative-impl(l)) {
            stringBuilder2.append('-');
        }
        stringBuilder2.append("PT");
        long l2 = Duration.getAbsoluteValue-UwyO8pc(l);
        boolean bl3 = false;
        int n = Duration.getNanosecondsComponent-impl(l2);
        int n2 = Duration.getSecondsComponent-impl(l2);
        int n3 = Duration.getMinutesComponent-impl(l2);
        long l3 = Duration.getInWholeHours-impl(l2);
        boolean bl4 = false;
        long l4 = l3;
        if (Duration.isInfinite-impl(l)) {
            l4 = 9999999999999L;
        }
        boolean bl5 = l4 != 0L;
        boolean bl6 = n2 != 0 || n != 0;
        boolean bl7 = bl = n3 != 0 || bl6 && bl5;
        if (bl5) {
            stringBuilder2.append(l4).append('H');
        }
        if (bl) {
            stringBuilder2.append(n3).append('M');
        }
        if (bl6 || !bl5 && !bl) {
            Duration.appendFractional-impl(l, stringBuilder2, n2, n, 9, "S", true);
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public static int hashCode-impl(long l) {
        return Long.hashCode(l);
    }

    public int hashCode() {
        return Duration.hashCode-impl(this.rawValue);
    }

    public static boolean equals-impl(long l, Object object) {
        if (!(object instanceof Duration)) {
            return true;
        }
        long l2 = ((Duration)object).unbox-impl();
        return l != l2;
    }

    public boolean equals(Object object) {
        return Duration.equals-impl(this.rawValue, object);
    }

    private Duration(long l) {
        this.rawValue = l;
    }

    public static long constructor-impl(long l) {
        long l2 = l;
        if (DurationJvmKt.getDurationAssertionsEnabled()) {
            if (Duration.isInNanos-impl(l2)) {
                if (!new LongRange(-4611686018426999999L, 4611686018426999999L).contains(Duration.getValue-impl(l2))) {
                    throw new AssertionError((Object)(Duration.getValue-impl(l2) + " ns is out of nanoseconds range"));
                }
            } else {
                if (!new LongRange(-4611686018427387903L, 0x3FFFFFFFFFFFFFFFL).contains(Duration.getValue-impl(l2))) {
                    throw new AssertionError((Object)(Duration.getValue-impl(l2) + " ms is out of milliseconds range"));
                }
                if (new LongRange(-4611686018426L, 4611686018426L).contains(Duration.getValue-impl(l2))) {
                    throw new AssertionError((Object)(Duration.getValue-impl(l2) + " ms is denormalized"));
                }
            }
        }
        return l2;
    }

    public static final Duration box-impl(long l) {
        return new Duration(l);
    }

    public final long unbox-impl() {
        return this.rawValue;
    }

    public static final boolean equals-impl0(long l, long l2) {
        return l == l2;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo-LRDsOJo(((Duration)object).unbox-impl());
    }

    public static final long access$getZERO$cp() {
        return ZERO;
    }

    public static final long access$getINFINITE$cp() {
        return INFINITE;
    }

    public static final long access$getNEG_INFINITE$cp() {
        return NEG_INFINITE;
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010*\u001a\u00020\r2\u0006\u0010+\u001a\u00020\r2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-H\u0007J\u001d\u0010\f\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b/\u0010\u0011J\u001d\u0010\f\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b/\u0010\u0014J\u001d\u0010\f\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b/\u0010\u0017J\u001d\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b0\u0010\u0011J\u001d\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b0\u0010\u0014J\u001d\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b0\u0010\u0017J\u001d\u0010\u001b\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b1\u0010\u0011J\u001d\u0010\u001b\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b1\u0010\u0014J\u001d\u0010\u001b\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b1\u0010\u0017J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b2\u0010\u0011J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b2\u0010\u0014J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b2\u0010\u0017J\u001d\u0010!\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b3\u0010\u0011J\u001d\u0010!\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b3\u0010\u0014J\u001d\u0010!\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b3\u0010\u0017J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b4\u0010\u0011J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b4\u0010\u0014J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b4\u0010\u0017J\u001b\u00105\u001a\u00020\u00042\u0006\u0010+\u001a\u000206\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b7\u00108J\u001b\u00109\u001a\u00020\u00042\u0006\u0010+\u001a\u000206\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b:\u00108J\u001b\u0010;\u001a\u0004\u0018\u00010\u00042\u0006\u0010+\u001a\u000206\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0002\b<J\u001b\u0010=\u001a\u0004\u0018\u00010\u00042\u0006\u0010+\u001a\u000206\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0002\b>J\u001d\u0010'\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b?\u0010\u0011J\u001d\u0010'\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b?\u0010\u0014J\u001d\u0010'\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b?\u0010\u0017R\u0019\u0010\u0003\u001a\u00020\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\b\u001a\u00020\u0004X\u0080\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006R\u0019\u0010\n\u001a\u00020\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u000b\u0010\u0006R%\u0010\f\u001a\u00020\u0004*\u00020\r8\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R%\u0010\f\u001a\u00020\u0004*\u00020\u00128\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u000e\u0010\u0013\u001a\u0004\b\u0010\u0010\u0014R%\u0010\f\u001a\u00020\u0004*\u00020\u00158\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u000e\u0010\u0016\u001a\u0004\b\u0010\u0010\u0017R%\u0010\u0018\u001a\u00020\u0004*\u00020\r8\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u0019\u0010\u000f\u001a\u0004\b\u001a\u0010\u0011R%\u0010\u0018\u001a\u00020\u0004*\u00020\u00128\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u0019\u0010\u0013\u001a\u0004\b\u001a\u0010\u0014R%\u0010\u0018\u001a\u00020\u0004*\u00020\u00158\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u0019\u0010\u0016\u001a\u0004\b\u001a\u0010\u0017R%\u0010\u001b\u001a\u00020\u0004*\u00020\r8\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u001c\u0010\u000f\u001a\u0004\b\u001d\u0010\u0011R%\u0010\u001b\u001a\u00020\u0004*\u00020\u00128\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u001c\u0010\u0013\u001a\u0004\b\u001d\u0010\u0014R%\u0010\u001b\u001a\u00020\u0004*\u00020\u00158\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u001c\u0010\u0016\u001a\u0004\b\u001d\u0010\u0017R%\u0010\u001e\u001a\u00020\u0004*\u00020\r8\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u001f\u0010\u000f\u001a\u0004\b \u0010\u0011R%\u0010\u001e\u001a\u00020\u0004*\u00020\u00128\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u001f\u0010\u0013\u001a\u0004\b \u0010\u0014R%\u0010\u001e\u001a\u00020\u0004*\u00020\u00158\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\u001f\u0010\u0016\u001a\u0004\b \u0010\u0017R%\u0010!\u001a\u00020\u0004*\u00020\r8\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\"\u0010\u000f\u001a\u0004\b#\u0010\u0011R%\u0010!\u001a\u00020\u0004*\u00020\u00128\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\"\u0010\u0013\u001a\u0004\b#\u0010\u0014R%\u0010!\u001a\u00020\u0004*\u00020\u00158\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b\"\u0010\u0016\u001a\u0004\b#\u0010\u0017R%\u0010$\u001a\u00020\u0004*\u00020\r8\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b%\u0010\u000f\u001a\u0004\b&\u0010\u0011R%\u0010$\u001a\u00020\u0004*\u00020\u00128\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b%\u0010\u0013\u001a\u0004\b&\u0010\u0014R%\u0010$\u001a\u00020\u0004*\u00020\u00158\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b%\u0010\u0016\u001a\u0004\b&\u0010\u0017R%\u0010'\u001a\u00020\u0004*\u00020\r8\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b(\u0010\u000f\u001a\u0004\b)\u0010\u0011R%\u0010'\u001a\u00020\u0004*\u00020\u00128\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b(\u0010\u0013\u001a\u0004\b)\u0010\u0014R%\u0010'\u001a\u00020\u0004*\u00020\u00158\u00c6\u0002X\u0087\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\f\u0012\u0004\b(\u0010\u0016\u001a\u0004\b)\u0010\u0017\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!\u00a8\u0006@"}, d2={"Lkotlin/time/Duration$Companion;", "", "()V", "INFINITE", "Lkotlin/time/Duration;", "getINFINITE-UwyO8pc", "()J", "J", "NEG_INFINITE", "getNEG_INFINITE-UwyO8pc$kotlin_stdlib", "ZERO", "getZERO-UwyO8pc", "days", "", "getDays-UwyO8pc$annotations", "(D)V", "getDays-UwyO8pc", "(D)J", "", "(I)V", "(I)J", "", "(J)V", "(J)J", "hours", "getHours-UwyO8pc$annotations", "getHours-UwyO8pc", "microseconds", "getMicroseconds-UwyO8pc$annotations", "getMicroseconds-UwyO8pc", "milliseconds", "getMilliseconds-UwyO8pc$annotations", "getMilliseconds-UwyO8pc", "minutes", "getMinutes-UwyO8pc$annotations", "getMinutes-UwyO8pc", "nanoseconds", "getNanoseconds-UwyO8pc$annotations", "getNanoseconds-UwyO8pc", "seconds", "getSeconds-UwyO8pc$annotations", "getSeconds-UwyO8pc", "convert", "value", "sourceUnit", "Lkotlin/time/DurationUnit;", "targetUnit", "days-UwyO8pc", "hours-UwyO8pc", "microseconds-UwyO8pc", "milliseconds-UwyO8pc", "minutes-UwyO8pc", "nanoseconds-UwyO8pc", "parse", "", "parse-UwyO8pc", "(Ljava/lang/String;)J", "parseIsoString", "parseIsoString-UwyO8pc", "parseIsoStringOrNull", "parseIsoStringOrNull-FghU774", "parseOrNull", "parseOrNull-FghU774", "seconds-UwyO8pc", "kotlin-stdlib"})
    public static final class Companion {
        private Companion() {
        }

        public final long getZERO-UwyO8pc() {
            return Duration.access$getZERO$cp();
        }

        public final long getINFINITE-UwyO8pc() {
            return Duration.access$getINFINITE$cp();
        }

        public final long getNEG_INFINITE-UwyO8pc$kotlin_stdlib() {
            return Duration.access$getNEG_INFINITE$cp();
        }

        @ExperimentalTime
        public final double convert(double d, @NotNull DurationUnit durationUnit, @NotNull DurationUnit durationUnit2) {
            Intrinsics.checkNotNullParameter((Object)durationUnit, "sourceUnit");
            Intrinsics.checkNotNullParameter((Object)durationUnit2, "targetUnit");
            return DurationUnitKt.convertDurationUnit(d, durationUnit, durationUnit2);
        }

        private final long getNanoseconds-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.NANOSECONDS);
        }

        @InlineOnly
        public static void getNanoseconds-UwyO8pc$annotations(int n) {
        }

        private final long getNanoseconds-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.NANOSECONDS);
        }

        @InlineOnly
        public static void getNanoseconds-UwyO8pc$annotations(long l) {
        }

        private final long getNanoseconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.NANOSECONDS);
        }

        @InlineOnly
        public static void getNanoseconds-UwyO8pc$annotations(double d) {
        }

        private final long getMicroseconds-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.MICROSECONDS);
        }

        @InlineOnly
        public static void getMicroseconds-UwyO8pc$annotations(int n) {
        }

        private final long getMicroseconds-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.MICROSECONDS);
        }

        @InlineOnly
        public static void getMicroseconds-UwyO8pc$annotations(long l) {
        }

        private final long getMicroseconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.MICROSECONDS);
        }

        @InlineOnly
        public static void getMicroseconds-UwyO8pc$annotations(double d) {
        }

        private final long getMilliseconds-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.MILLISECONDS);
        }

        @InlineOnly
        public static void getMilliseconds-UwyO8pc$annotations(int n) {
        }

        private final long getMilliseconds-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.MILLISECONDS);
        }

        @InlineOnly
        public static void getMilliseconds-UwyO8pc$annotations(long l) {
        }

        private final long getMilliseconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.MILLISECONDS);
        }

        @InlineOnly
        public static void getMilliseconds-UwyO8pc$annotations(double d) {
        }

        private final long getSeconds-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.SECONDS);
        }

        @InlineOnly
        public static void getSeconds-UwyO8pc$annotations(int n) {
        }

        private final long getSeconds-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.SECONDS);
        }

        @InlineOnly
        public static void getSeconds-UwyO8pc$annotations(long l) {
        }

        private final long getSeconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.SECONDS);
        }

        @InlineOnly
        public static void getSeconds-UwyO8pc$annotations(double d) {
        }

        private final long getMinutes-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.MINUTES);
        }

        @InlineOnly
        public static void getMinutes-UwyO8pc$annotations(int n) {
        }

        private final long getMinutes-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.MINUTES);
        }

        @InlineOnly
        public static void getMinutes-UwyO8pc$annotations(long l) {
        }

        private final long getMinutes-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.MINUTES);
        }

        @InlineOnly
        public static void getMinutes-UwyO8pc$annotations(double d) {
        }

        private final long getHours-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.HOURS);
        }

        @InlineOnly
        public static void getHours-UwyO8pc$annotations(int n) {
        }

        private final long getHours-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.HOURS);
        }

        @InlineOnly
        public static void getHours-UwyO8pc$annotations(long l) {
        }

        private final long getHours-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.HOURS);
        }

        @InlineOnly
        public static void getHours-UwyO8pc$annotations(double d) {
        }

        private final long getDays-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.DAYS);
        }

        @InlineOnly
        public static void getDays-UwyO8pc$annotations(int n) {
        }

        private final long getDays-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.DAYS);
        }

        @InlineOnly
        public static void getDays-UwyO8pc$annotations(long l) {
        }

        private final long getDays-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.DAYS);
        }

        @InlineOnly
        public static void getDays-UwyO8pc$annotations(double d) {
        }

        @Deprecated(message="Use 'Int.nanoseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.nanoseconds", imports={"kotlin.time.Duration.Companion.nanoseconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long nanoseconds-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.NANOSECONDS);
        }

        @Deprecated(message="Use 'Long.nanoseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.nanoseconds", imports={"kotlin.time.Duration.Companion.nanoseconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long nanoseconds-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.NANOSECONDS);
        }

        @Deprecated(message="Use 'Double.nanoseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.nanoseconds", imports={"kotlin.time.Duration.Companion.nanoseconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long nanoseconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.NANOSECONDS);
        }

        @Deprecated(message="Use 'Int.microseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.microseconds", imports={"kotlin.time.Duration.Companion.microseconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long microseconds-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.MICROSECONDS);
        }

        @Deprecated(message="Use 'Long.microseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.microseconds", imports={"kotlin.time.Duration.Companion.microseconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long microseconds-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.MICROSECONDS);
        }

        @Deprecated(message="Use 'Double.microseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.microseconds", imports={"kotlin.time.Duration.Companion.microseconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long microseconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.MICROSECONDS);
        }

        @Deprecated(message="Use 'Int.milliseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.milliseconds", imports={"kotlin.time.Duration.Companion.milliseconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long milliseconds-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.MILLISECONDS);
        }

        @Deprecated(message="Use 'Long.milliseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.milliseconds", imports={"kotlin.time.Duration.Companion.milliseconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long milliseconds-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.MILLISECONDS);
        }

        @Deprecated(message="Use 'Double.milliseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.milliseconds", imports={"kotlin.time.Duration.Companion.milliseconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long milliseconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.MILLISECONDS);
        }

        @Deprecated(message="Use 'Int.seconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.seconds", imports={"kotlin.time.Duration.Companion.seconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long seconds-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.SECONDS);
        }

        @Deprecated(message="Use 'Long.seconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.seconds", imports={"kotlin.time.Duration.Companion.seconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long seconds-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.SECONDS);
        }

        @Deprecated(message="Use 'Double.seconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.seconds", imports={"kotlin.time.Duration.Companion.seconds"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long seconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.SECONDS);
        }

        @Deprecated(message="Use 'Int.minutes' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.minutes", imports={"kotlin.time.Duration.Companion.minutes"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long minutes-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.MINUTES);
        }

        @Deprecated(message="Use 'Long.minutes' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.minutes", imports={"kotlin.time.Duration.Companion.minutes"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long minutes-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.MINUTES);
        }

        @Deprecated(message="Use 'Double.minutes' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.minutes", imports={"kotlin.time.Duration.Companion.minutes"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long minutes-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.MINUTES);
        }

        @Deprecated(message="Use 'Int.hours' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.hours", imports={"kotlin.time.Duration.Companion.hours"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long hours-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.HOURS);
        }

        @Deprecated(message="Use 'Long.hours' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.hours", imports={"kotlin.time.Duration.Companion.hours"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long hours-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.HOURS);
        }

        @Deprecated(message="Use 'Double.hours' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.hours", imports={"kotlin.time.Duration.Companion.hours"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long hours-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.HOURS);
        }

        @Deprecated(message="Use 'Int.days' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.days", imports={"kotlin.time.Duration.Companion.days"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long days-UwyO8pc(int n) {
            return DurationKt.toDuration(n, DurationUnit.DAYS);
        }

        @Deprecated(message="Use 'Long.days' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.days", imports={"kotlin.time.Duration.Companion.days"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long days-UwyO8pc(long l) {
            return DurationKt.toDuration(l, DurationUnit.DAYS);
        }

        @Deprecated(message="Use 'Double.days' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="value.days", imports={"kotlin.time.Duration.Companion.days"}))
        @DeprecatedSinceKotlin(warningSince="1.6", errorSince="1.8", hiddenSince="1.9")
        @SinceKotlin(version="1.5")
        @ExperimentalTime
        public final long days-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.DAYS);
        }

        public final long parse-UwyO8pc(@NotNull String string) {
            long l;
            Intrinsics.checkNotNullParameter(string, "value");
            try {
                l = DurationKt.access$parseDuration(string, false);
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new IllegalArgumentException("Invalid duration string format: '" + string + "'.", illegalArgumentException);
            }
            return l;
        }

        public final long parseIsoString-UwyO8pc(@NotNull String string) {
            long l;
            Intrinsics.checkNotNullParameter(string, "value");
            try {
                l = DurationKt.access$parseDuration(string, true);
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new IllegalArgumentException("Invalid ISO duration string format: '" + string + "'.", illegalArgumentException);
            }
            return l;
        }

        @Nullable
        public final Duration parseOrNull-FghU774(@NotNull String string) {
            Duration duration;
            Intrinsics.checkNotNullParameter(string, "value");
            try {
                duration = Duration.box-impl(DurationKt.access$parseDuration(string, false));
            } catch (IllegalArgumentException illegalArgumentException) {
                duration = null;
            }
            return duration;
        }

        @Nullable
        public final Duration parseIsoStringOrNull-FghU774(@NotNull String string) {
            Duration duration;
            Intrinsics.checkNotNullParameter(string, "value");
            try {
                duration = Duration.box-impl(DurationKt.access$parseDuration(string, true));
            } catch (IllegalArgumentException illegalArgumentException) {
                duration = null;
            }
            return duration;
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}

