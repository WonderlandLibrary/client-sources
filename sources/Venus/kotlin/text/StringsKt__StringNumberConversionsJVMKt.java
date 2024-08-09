/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.CharsKt;
import kotlin.text.ScreenFloatValueRegEx;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringBuilderKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000X\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0005H\u0082\b\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a\r\u0010\b\u001a\u00020\t*\u00020\u0003H\u0087\b\u001a\u0015\u0010\b\u001a\u00020\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u000e\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u0003H\u0007\u001a\u0016\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\r\u0010\r\u001a\u00020\u000e*\u00020\u0003H\u0087\b\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007\u001a\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\u000f\u0010\u0012\u001a\u00020\u0013*\u0004\u0018\u00010\u0003H\u0087\b\u001a\r\u0010\u0014\u001a\u00020\u0015*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010\u0016\u001a\u00020\u0017*\u00020\u0003H\u0087\b\u001a\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0017*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u0019\u001a\r\u0010\u001a\u001a\u00020\u001b*\u00020\u0003H\u0087\b\u001a\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u001b*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u001d\u001a\r\u0010\u001e\u001a\u00020\u0010*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u001e\u001a\u00020\u0010*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010\u001f\u001a\u00020 *\u00020\u0003H\u0087\b\u001a\u0015\u0010\u001f\u001a\u00020 *\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010!\u001a\u00020\"*\u00020\u0003H\u0087\b\u001a\u0015\u0010!\u001a\u00020\"*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020 2\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\"2\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u00a8\u0006$"}, d2={"screenFloatValue", "T", "str", "", "parse", "Lkotlin/Function1;", "screenFloatValue$StringsKt__StringNumberConversionsJVMKt", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "toBigDecimal", "Ljava/math/BigDecimal;", "mathContext", "Ljava/math/MathContext;", "toBigDecimalOrNull", "toBigInteger", "Ljava/math/BigInteger;", "radix", "", "toBigIntegerOrNull", "toBoolean", "", "toByte", "", "toDouble", "", "toDoubleOrNull", "(Ljava/lang/String;)Ljava/lang/Double;", "toFloat", "", "toFloatOrNull", "(Ljava/lang/String;)Ljava/lang/Float;", "toInt", "toLong", "", "toShort", "", "toString", "kotlin-stdlib"}, xs="kotlin/text/StringsKt")
@SourceDebugExtension(value={"SMAP\nStringNumberConversionsJVM.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringNumberConversionsJVM.kt\nkotlin/text/StringsKt__StringNumberConversionsJVMKt\n*L\n1#1,274:1\n265#1,7:275\n265#1,7:282\n265#1,7:289\n265#1,7:296\n*S KotlinDebug\n*F\n+ 1 StringNumberConversionsJVM.kt\nkotlin/text/StringsKt__StringNumberConversionsJVMKt\n*L\n142#1:275,7\n149#1:282,7\n229#1:289,7\n240#1:296,7\n*E\n"})
class StringsKt__StringNumberConversionsJVMKt
extends StringsKt__StringBuilderKt {
    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String toString(byte by, int n) {
        String string = Integer.toString(by, CharsKt.checkRadix(CharsKt.checkRadix(n)));
        Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String toString(short s, int n) {
        String string = Integer.toString(s, CharsKt.checkRadix(CharsKt.checkRadix(n)));
        Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String toString(int n, int n2) {
        String string = Integer.toString(n, CharsKt.checkRadix(n2));
        Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String toString(long l, int n) {
        String string = Long.toString(l, CharsKt.checkRadix(n));
        Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
        return string;
    }

    @SinceKotlin(version="1.4")
    @InlineOnly
    private static final boolean toBoolean(String string) {
        return Boolean.parseBoolean(string);
    }

    @InlineOnly
    private static final byte toByte(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return Byte.parseByte(string);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final byte toByte(String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return Byte.parseByte(string, CharsKt.checkRadix(n));
    }

    @InlineOnly
    private static final short toShort(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return Short.parseShort(string);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final short toShort(String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return Short.parseShort(string, CharsKt.checkRadix(n));
    }

    @InlineOnly
    private static final int toInt(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return Integer.parseInt(string);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int toInt(String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return Integer.parseInt(string, CharsKt.checkRadix(n));
    }

    @InlineOnly
    private static final long toLong(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return Long.parseLong(string);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final long toLong(String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return Long.parseLong(string, CharsKt.checkRadix(n));
    }

    @InlineOnly
    private static final float toFloat(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return Float.parseFloat(string);
    }

    @InlineOnly
    private static final double toDouble(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return Double.parseDouble(string);
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Float toFloatOrNull(@NotNull String string) {
        String string2;
        Intrinsics.checkNotNullParameter(string, "<this>");
        boolean bl = false;
        try {
            Float f;
            if (ScreenFloatValueRegEx.value.matches(string)) {
                string2 = string;
                boolean bl2 = false;
                f = Float.valueOf(Float.parseFloat(string2));
            } else {
                f = null;
            }
            string2 = f;
        } catch (NumberFormatException numberFormatException) {
            string2 = null;
        }
        return string2;
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Double toDoubleOrNull(@NotNull String string) {
        String string2;
        Intrinsics.checkNotNullParameter(string, "<this>");
        boolean bl = false;
        try {
            Double d;
            if (ScreenFloatValueRegEx.value.matches(string)) {
                string2 = string;
                boolean bl2 = false;
                d = Double.parseDouble(string2);
            } else {
                d = null;
            }
            string2 = d;
        } catch (NumberFormatException numberFormatException) {
            string2 = null;
        }
        return string2;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return new BigInteger(string);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return new BigInteger(string, CharsKt.checkRadix(n));
    }

    @SinceKotlin(version="1.2")
    @Nullable
    public static final BigInteger toBigIntegerOrNull(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return StringsKt.toBigIntegerOrNull(string, 10);
    }

    @SinceKotlin(version="1.2")
    @Nullable
    public static final BigInteger toBigIntegerOrNull(@NotNull String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        CharsKt.checkRadix(n);
        int n2 = string.length();
        switch (n2) {
            case 0: {
                return null;
            }
            case 1: {
                if (CharsKt.digitOf(string.charAt(0), n) >= 0) break;
                return null;
            }
            default: {
                int n3;
                for (int i = n3 = string.charAt(0) == '-' ? 1 : 0; i < n2; ++i) {
                    if (CharsKt.digitOf(string.charAt(i), n) >= 0) continue;
                    return null;
                }
            }
        }
        return new BigInteger(string, CharsKt.checkRadix(n));
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return new BigDecimal(string);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(String string, MathContext mathContext) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(mathContext, "mathContext");
        return new BigDecimal(string, mathContext);
    }

    @SinceKotlin(version="1.2")
    @Nullable
    public static final BigDecimal toBigDecimalOrNull(@NotNull String string) {
        String string2;
        Intrinsics.checkNotNullParameter(string, "<this>");
        boolean bl = false;
        try {
            BigDecimal bigDecimal;
            if (ScreenFloatValueRegEx.value.matches(string)) {
                string2 = string;
                boolean bl2 = false;
                bigDecimal = new BigDecimal(string2);
            } else {
                bigDecimal = null;
            }
            string2 = bigDecimal;
        } catch (NumberFormatException numberFormatException) {
            string2 = null;
        }
        return string2;
    }

    @SinceKotlin(version="1.2")
    @Nullable
    public static final BigDecimal toBigDecimalOrNull(@NotNull String string, @NotNull MathContext mathContext) {
        BigDecimal bigDecimal;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(mathContext, "mathContext");
        boolean bl = false;
        try {
            BigDecimal bigDecimal2;
            if (ScreenFloatValueRegEx.value.matches(string)) {
                String string2 = string;
                boolean bl2 = false;
                bigDecimal2 = new BigDecimal(string2, mathContext);
            } else {
                bigDecimal2 = null;
            }
            bigDecimal = bigDecimal2;
        } catch (NumberFormatException numberFormatException) {
            bigDecimal = null;
        }
        return bigDecimal;
    }

    private static final <T> T screenFloatValue$StringsKt__StringNumberConversionsJVMKt(String string, Function1<? super String, ? extends T> function1) {
        Object var3_3;
        boolean bl = false;
        try {
            var3_3 = ScreenFloatValueRegEx.value.matches(string) ? function1.invoke(string) : null;
        } catch (NumberFormatException numberFormatException) {
            var3_3 = null;
        }
        return var3_3;
    }
}

