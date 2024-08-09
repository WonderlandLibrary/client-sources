/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.time.Duration;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000.\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\u001a\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u001a\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u001c\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\b\u00a8\u0006\u0011"}, d2={"durationAssertionsEnabled", "", "getDurationAssertionsEnabled", "()Z", "precisionFormats", "", "Ljava/lang/ThreadLocal;", "Ljava/text/DecimalFormat;", "[Ljava/lang/ThreadLocal;", "createFormatForDecimals", "decimals", "", "formatToExactDecimals", "", "value", "", "formatUpToDecimals", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nDurationJvm.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DurationJvm.kt\nkotlin/time/DurationJvmKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,33:1\n1#2:34\n*E\n"})
public final class DurationJvmKt {
    private static final boolean durationAssertionsEnabled = Duration.class.desiredAssertionStatus();
    @NotNull
    private static final ThreadLocal<DecimalFormat>[] precisionFormats;

    public static final boolean getDurationAssertionsEnabled() {
        return durationAssertionsEnabled;
    }

    private static final DecimalFormat createFormatForDecimals(int n) {
        DecimalFormat decimalFormat;
        DecimalFormat decimalFormat2 = decimalFormat = new DecimalFormat("0");
        boolean bl = false;
        if (n > 0) {
            decimalFormat2.setMinimumFractionDigits(n);
        }
        decimalFormat2.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat;
    }

    @NotNull
    public static final String formatToExactDecimals(double d, int n) {
        DecimalFormat decimalFormat;
        if (n < precisionFormats.length) {
            DecimalFormat decimalFormat2;
            DecimalFormat decimalFormat3;
            ThreadLocal<DecimalFormat> threadLocal = precisionFormats[n];
            DecimalFormat decimalFormat4 = decimalFormat3 = threadLocal.get();
            if (decimalFormat4 == null) {
                DecimalFormat decimalFormat5;
                boolean bl = false;
                DecimalFormat decimalFormat6 = decimalFormat5 = DurationJvmKt.createFormatForDecimals(n);
                threadLocal.set(decimalFormat6);
                decimalFormat2 = decimalFormat5;
            } else {
                Intrinsics.checkNotNullExpressionValue(decimalFormat4, "get() ?: default().also(this::set)");
                decimalFormat2 = decimalFormat3;
            }
            decimalFormat = decimalFormat2;
        } else {
            decimalFormat = DurationJvmKt.createFormatForDecimals(n);
        }
        DecimalFormat decimalFormat7 = decimalFormat;
        String string = decimalFormat7.format(d);
        Intrinsics.checkNotNullExpressionValue(string, "format.format(value)");
        return string;
    }

    @NotNull
    public static final String formatUpToDecimals(double d, int n) {
        DecimalFormat decimalFormat;
        DecimalFormat decimalFormat2 = decimalFormat = DurationJvmKt.createFormatForDecimals(0);
        boolean bl = false;
        decimalFormat2.setMaximumFractionDigits(n);
        String string = decimalFormat.format(d);
        Intrinsics.checkNotNullExpressionValue(string, "createFormatForDecimals(\u2026 }\n        .format(value)");
        return string;
    }

    static {
        int n = 0;
        ThreadLocal[] threadLocalArray = new ThreadLocal[4];
        while (n < 4) {
            int n2 = n++;
            threadLocalArray[n2] = new ThreadLocal();
        }
        precisionFormats = threadLocalArray;
    }
}

