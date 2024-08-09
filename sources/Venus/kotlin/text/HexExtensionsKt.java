/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import java.util.Arrays;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.ULong;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.HexFormat;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000N\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\n\n\u0002\b\u0004\u001a \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002\u001a@\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0000\u001a@\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0000\u001a \u0010\u0017\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000bH\u0002\u001a,\u0010\u0018\u001a\u00020\u000b*\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u0005H\u0002\u001a,\u0010\u001d\u001a\u00020\u001e*\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\"H\u0002\u001a\u001c\u0010#\u001a\u00020\u000b*\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u000bH\u0002\u001a\u0014\u0010$\u001a\u00020\u000b*\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u000bH\u0002\u001a*\u0010%\u001a\u00020&*\u00020\u00052\b\b\u0002\u0010\u001f\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u000b2\b\b\u0002\u0010'\u001a\u00020(H\u0003\u001a\u0016\u0010%\u001a\u00020&*\u00020\u00052\b\b\u0002\u0010'\u001a\u00020(H\u0007\u001a*\u0010)\u001a\u00020**\u00020\u00052\b\b\u0002\u0010\u001f\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u000b2\b\b\u0002\u0010'\u001a\u00020(H\u0003\u001a\u0016\u0010)\u001a\u00020**\u00020\u00052\b\b\u0002\u0010'\u001a\u00020(H\u0007\u001a*\u0010+\u001a\u00020\u000b*\u00020\u00052\b\b\u0002\u0010\u001f\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u000b2\b\b\u0002\u0010'\u001a\u00020(H\u0003\u001a\u0016\u0010+\u001a\u00020\u000b*\u00020\u00052\b\b\u0002\u0010'\u001a\u00020(H\u0007\u001a*\u0010,\u001a\u00020\b*\u00020\u00052\b\b\u0002\u0010\u001f\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u000b2\b\b\u0002\u0010'\u001a\u00020(H\u0003\u001a\u0016\u0010,\u001a\u00020\b*\u00020\u00052\b\b\u0002\u0010'\u001a\u00020(H\u0007\u001a0\u0010-\u001a\u00020\b*\u00020\u00052\b\b\u0002\u0010\u001f\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020(2\u0006\u0010 \u001a\u00020\u000bH\u0003\u001a*\u0010.\u001a\u00020/*\u00020\u00052\b\b\u0002\u0010\u001f\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u000b2\b\b\u0002\u0010'\u001a\u00020(H\u0003\u001a\u0016\u0010.\u001a\u00020/*\u00020\u00052\b\b\u0002\u0010'\u001a\u00020(H\u0007\u001a\u0016\u00100\u001a\u00020\u0005*\u00020&2\b\b\u0002\u0010'\u001a\u00020(H\u0007\u001a*\u00100\u001a\u00020\u0005*\u00020*2\b\b\u0002\u0010\u001f\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u000b2\b\b\u0002\u0010'\u001a\u00020(H\u0007\u001a\u0016\u00100\u001a\u00020\u0005*\u00020*2\b\b\u0002\u0010'\u001a\u00020(H\u0007\u001a\u0016\u00100\u001a\u00020\u0005*\u00020\u000b2\b\b\u0002\u0010'\u001a\u00020(H\u0007\u001a\u0016\u00100\u001a\u00020\u0005*\u00020\b2\b\b\u0002\u0010'\u001a\u00020(H\u0007\u001a\u0016\u00100\u001a\u00020\u0005*\u00020/2\b\b\u0002\u0010'\u001a\u00020(H\u0007\u001a\u001c\u00101\u001a\u00020\u0005*\u00020\b2\u0006\u0010'\u001a\u00020(2\u0006\u00102\u001a\u00020\u000bH\u0003\"\u0016\u0010\u0000\u001a\u00020\u00018\u0002X\u0083\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u00063"}, d2={"HEX_DIGITS_TO_DECIMAL", "", "getHEX_DIGITS_TO_DECIMAL$annotations", "()V", "LOWER_CASE_HEX_DIGITS", "", "UPPER_CASE_HEX_DIGITS", "charsPerSet", "", "charsPerElement", "elementsPerSet", "", "elementSeparatorLength", "formattedStringLength", "totalBytes", "bytesPerLine", "bytesPerGroup", "groupSeparatorLength", "byteSeparatorLength", "bytePrefixLength", "byteSuffixLength", "parsedByteArrayMaxSize", "stringLength", "wholeElementsPerSet", "checkContainsAt", "part", "index", "endIndex", "partName", "checkHexLength", "", "startIndex", "maxDigits", "requireMaxLength", "", "checkNewLineAt", "decimalFromHexDigitAt", "hexToByte", "", "format", "Lkotlin/text/HexFormat;", "hexToByteArray", "", "hexToInt", "hexToLong", "hexToLongImpl", "hexToShort", "", "toHexString", "toHexStringImpl", "bits", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nHexExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HexExtensions.kt\nkotlin/text/HexExtensionsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,594:1\n1#2:595\n1183#3,3:596\n1183#3,3:599\n*S KotlinDebug\n*F\n+ 1 HexExtensions.kt\nkotlin/text/HexExtensionsKt\n*L\n16#1:596,3\n17#1:599,3\n*E\n"})
public final class HexExtensionsKt {
    @NotNull
    private static final String LOWER_CASE_HEX_DIGITS = "0123456789abcdef";
    @NotNull
    private static final String UPPER_CASE_HEX_DIGITS = "0123456789ABCDEF";
    @NotNull
    private static final int[] HEX_DIGITS_TO_DECIMAL;

    private static void getHEX_DIGITS_TO_DECIMAL$annotations() {
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @NotNull
    public static final String toHexString(@NotNull byte[] byArray, @NotNull HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(byArray, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(byArray, 0, byArray.length, hexFormat);
    }

    public static String toHexString$default(byte[] byArray, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.toHexString(byArray, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @NotNull
    public static final String toHexString(@NotNull byte[] byArray, int n, int n2, @NotNull HexFormat hexFormat) {
        int n3;
        StringBuilder stringBuilder;
        Intrinsics.checkNotNullParameter(byArray, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n, n2, byArray.length);
        if (n == n2) {
            return "";
        }
        String string = hexFormat.getUpperCase() ? UPPER_CASE_HEX_DIGITS : LOWER_CASE_HEX_DIGITS;
        HexFormat.BytesHexFormat bytesHexFormat = hexFormat.getBytes();
        int n4 = bytesHexFormat.getBytesPerLine();
        int n5 = bytesHexFormat.getBytesPerGroup();
        String string2 = bytesHexFormat.getBytePrefix();
        String string3 = bytesHexFormat.getByteSuffix();
        String string4 = bytesHexFormat.getByteSeparator();
        String string5 = bytesHexFormat.getGroupSeparator();
        int n6 = HexExtensionsKt.formattedStringLength(n2 - n, n4, n5, string5.length(), string4.length(), string2.length(), string3.length());
        int n7 = 0;
        int n8 = 0;
        StringBuilder stringBuilder2 = stringBuilder = new StringBuilder(n6);
        boolean bl = false;
        for (n3 = n; n3 < n2; ++n3) {
            int n9 = byArray[n3] & 0xFF;
            if (n7 == n4) {
                stringBuilder2.append('\n');
                n7 = 0;
                n8 = 0;
            } else if (n8 == n5) {
                stringBuilder2.append(string5);
                n8 = 0;
            }
            if (n8 != 0) {
                stringBuilder2.append(string4);
            }
            stringBuilder2.append(string2);
            stringBuilder2.append(string.charAt(n9 >> 4));
            stringBuilder2.append(string.charAt(n9 & 0xF));
            stringBuilder2.append(string3);
            ++n8;
            ++n7;
        }
        int n10 = n3 = n6 == stringBuilder2.length() ? 1 : 0;
        if (n3 == 0) {
            String string6 = "Check failed.";
            throw new IllegalStateException(string6.toString());
        }
        String string7 = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string7, "StringBuilder(capacity).\u2026builderAction).toString()");
        return string7;
    }

    public static String toHexString$default(byte[] byArray, int n, int n2, HexFormat hexFormat, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = byArray.length;
        }
        if ((n3 & 4) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.toHexString(byArray, n, n2, hexFormat);
    }

    public static final int formattedStringLength(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8;
        int n9;
        int n10 = n9 = n > 0 ? 1 : 0;
        if (n9 == 0) {
            String string = "Failed requirement.";
            throw new IllegalArgumentException(string.toString());
        }
        n9 = (n - 1) / n2;
        int n11 = 0;
        int n12 = (n2 - 1) / n3;
        int n13 = n8 = n % n2;
        boolean bl = false;
        int n14 = n13 == 0 ? n2 : n13;
        n8 = (n14 - 1) / n3;
        int n15 = n9 * n12 + n8;
        n11 = n - 1 - n9 - n15;
        long l = (long)n9 + (long)n15 * (long)n4 + (long)n11 * (long)n5 + (long)n * ((long)n6 + 2L + (long)n7);
        if (!RangesKt.intRangeContains((ClosedRange<Integer>)new IntRange(0, Integer.MAX_VALUE), l)) {
            throw new IllegalArgumentException("The resulting string length is too big: " + ULong.toString-impl(ULong.constructor-impl(l)));
        }
        return (int)l;
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @NotNull
    public static final byte[] hexToByteArray(@NotNull String string, @NotNull HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.hexToByteArray(string, 0, string.length(), hexFormat);
    }

    public static byte[] hexToByteArray$default(String string, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.hexToByteArray(string, hexFormat);
    }

    @ExperimentalStdlibApi
    private static final byte[] hexToByteArray(String string, int n, int n2, HexFormat hexFormat) {
        byte[] byArray;
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n, n2, string.length());
        if (n == n2) {
            return new byte[0];
        }
        HexFormat.BytesHexFormat bytesHexFormat = hexFormat.getBytes();
        int n3 = bytesHexFormat.getBytesPerLine();
        int n4 = bytesHexFormat.getBytesPerGroup();
        String string2 = bytesHexFormat.getBytePrefix();
        String string3 = bytesHexFormat.getByteSuffix();
        String string4 = bytesHexFormat.getByteSeparator();
        String string5 = bytesHexFormat.getGroupSeparator();
        int n5 = HexExtensionsKt.parsedByteArrayMaxSize(n2 - n, n3, n4, string5.length(), string4.length(), string2.length(), string3.length());
        byte[] byArray2 = new byte[n5];
        int n6 = n;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        while (n6 < n2) {
            if (n8 == n3) {
                n6 = HexExtensionsKt.checkNewLineAt(string, n6, n2);
                n8 = 0;
                n9 = 0;
            } else if (n9 == n4) {
                n6 = HexExtensionsKt.checkContainsAt(string, string5, n6, n2, "group separator");
                n9 = 0;
            } else if (n9 != 0) {
                n6 = HexExtensionsKt.checkContainsAt(string, string4, n6, n2, "byte separator");
            }
            ++n8;
            ++n9;
            n6 = HexExtensionsKt.checkContainsAt(string, string2, n6, n2, "byte prefix");
            HexExtensionsKt.checkHexLength(string, n6, RangesKt.coerceAtMost(n6 + 2, n2), 2, true);
            byArray2[n7++] = (byte)(HexExtensionsKt.decimalFromHexDigitAt(string, n6++) << 4 | HexExtensionsKt.decimalFromHexDigitAt(string, n6++));
            n6 = HexExtensionsKt.checkContainsAt(string, string3, n6, n2, "byte suffix");
        }
        if (n7 == byArray2.length) {
            byArray = byArray2;
        } else {
            byte[] byArray3 = Arrays.copyOf(byArray2, n7);
            byArray = byArray3;
            Intrinsics.checkNotNullExpressionValue(byArray3, "copyOf(this, newSize)");
        }
        return byArray;
    }

    static byte[] hexToByteArray$default(String string, int n, int n2, HexFormat hexFormat, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = string.length();
        }
        if ((n3 & 4) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.hexToByteArray(string, n, n2, hexFormat);
    }

    public static final int parsedByteArrayMaxSize(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        long l;
        boolean bl;
        boolean bl2 = bl = n > 0;
        if (!bl) {
            String string = "Failed requirement.";
            throw new IllegalArgumentException(string.toString());
        }
        long l2 = (long)n6 + 2L + (long)n7;
        long l3 = HexExtensionsKt.charsPerSet(l2, n3, n5);
        if (n2 <= n3) {
            l = HexExtensionsKt.charsPerSet(l2, n2, n5);
        } else {
            int n8 = n2 / n3;
            long l4 = HexExtensionsKt.charsPerSet(l3, n8, n4);
            int n9 = n2 % n3;
            if (n9 != 0) {
                l4 += (long)n4;
                l4 += HexExtensionsKt.charsPerSet(l2, n9, n5);
            }
            l = l4;
        }
        long l5 = l;
        long l6 = n;
        long l7 = HexExtensionsKt.wholeElementsPerSet(l6, l5, 1);
        long l8 = HexExtensionsKt.wholeElementsPerSet(l6 -= l7 * (l5 + 1L), l3, n4);
        long l9 = HexExtensionsKt.wholeElementsPerSet(l6 -= l8 * (l3 + (long)n4), l2, n5);
        int n10 = (l6 -= l9 * (l2 + (long)n5)) > 0L ? 1 : 0;
        return (int)(l7 * (long)n2 + l8 * (long)n3 + l9 + (long)n10);
    }

    private static final long charsPerSet(long l, int n, int n2) {
        boolean bl;
        boolean bl2 = bl = n > 0;
        if (!bl) {
            String string = "Failed requirement.";
            throw new IllegalArgumentException(string.toString());
        }
        return l * (long)n + (long)n2 * ((long)n - 1L);
    }

    private static final long wholeElementsPerSet(long l, long l2, int n) {
        return l <= 0L || l2 <= 0L ? 0L : (l + (long)n) / (l2 + (long)n);
    }

    private static final int checkNewLineAt(String string, int n, int n2) {
        int n3;
        if (string.charAt(n) == '\r') {
            n3 = n + 1 < n2 && string.charAt(n + 1) == '\n' ? n + 2 : n + 1;
        } else if (string.charAt(n) == '\n') {
            n3 = n + 1;
        } else {
            throw new NumberFormatException("Expected a new line at index " + n + ", but was " + string.charAt(n));
        }
        return n3;
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @NotNull
    public static final String toHexString(byte by, @NotNull HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexStringImpl(by, hexFormat, 8);
    }

    public static String toHexString$default(byte by, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.toHexString(by, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    public static final byte hexToByte(@NotNull String string, @NotNull HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.hexToByte(string, 0, string.length(), hexFormat);
    }

    public static byte hexToByte$default(String string, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.hexToByte(string, hexFormat);
    }

    @ExperimentalStdlibApi
    private static final byte hexToByte(String string, int n, int n2, HexFormat hexFormat) {
        return (byte)HexExtensionsKt.hexToLongImpl(string, n, n2, hexFormat, 2);
    }

    static byte hexToByte$default(String string, int n, int n2, HexFormat hexFormat, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = string.length();
        }
        if ((n3 & 4) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.hexToByte(string, n, n2, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @NotNull
    public static final String toHexString(short s, @NotNull HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexStringImpl(s, hexFormat, 16);
    }

    public static String toHexString$default(short s, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.toHexString(s, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    public static final short hexToShort(@NotNull String string, @NotNull HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.hexToShort(string, 0, string.length(), hexFormat);
    }

    public static short hexToShort$default(String string, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.hexToShort(string, hexFormat);
    }

    @ExperimentalStdlibApi
    private static final short hexToShort(String string, int n, int n2, HexFormat hexFormat) {
        return (short)HexExtensionsKt.hexToLongImpl(string, n, n2, hexFormat, 4);
    }

    static short hexToShort$default(String string, int n, int n2, HexFormat hexFormat, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = string.length();
        }
        if ((n3 & 4) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.hexToShort(string, n, n2, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @NotNull
    public static final String toHexString(int n, @NotNull HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexStringImpl(n, hexFormat, 32);
    }

    public static String toHexString$default(int n, HexFormat hexFormat, int n2, Object object) {
        if ((n2 & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.toHexString(n, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    public static final int hexToInt(@NotNull String string, @NotNull HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.hexToInt(string, 0, string.length(), hexFormat);
    }

    public static int hexToInt$default(String string, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.hexToInt(string, hexFormat);
    }

    @ExperimentalStdlibApi
    private static final int hexToInt(String string, int n, int n2, HexFormat hexFormat) {
        return (int)HexExtensionsKt.hexToLongImpl(string, n, n2, hexFormat, 8);
    }

    static int hexToInt$default(String string, int n, int n2, HexFormat hexFormat, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = string.length();
        }
        if ((n3 & 4) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.hexToInt(string, n, n2, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @NotNull
    public static final String toHexString(long l, @NotNull HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexStringImpl(l, hexFormat, 64);
    }

    public static String toHexString$default(long l, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.toHexString(l, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    public static final long hexToLong(@NotNull String string, @NotNull HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.hexToLong(string, 0, string.length(), hexFormat);
    }

    public static long hexToLong$default(String string, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.hexToLong(string, hexFormat);
    }

    @ExperimentalStdlibApi
    private static final long hexToLong(String string, int n, int n2, HexFormat hexFormat) {
        return HexExtensionsKt.hexToLongImpl(string, n, n2, hexFormat, 16);
    }

    static long hexToLong$default(String string, int n, int n2, HexFormat hexFormat, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = string.length();
        }
        if ((n3 & 4) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        return HexExtensionsKt.hexToLong(string, n, n2, hexFormat);
    }

    @ExperimentalStdlibApi
    private static final String toHexStringImpl(long l, HexFormat hexFormat, int n) {
        StringBuilder stringBuilder;
        boolean bl;
        boolean bl2 = bl = (n & 3) == 0;
        if (!bl) {
            String string = "Failed requirement.";
            throw new IllegalArgumentException(string.toString());
        }
        String string = hexFormat.getUpperCase() ? UPPER_CASE_HEX_DIGITS : LOWER_CASE_HEX_DIGITS;
        long l2 = l;
        String string2 = hexFormat.getNumber().getPrefix();
        String string3 = hexFormat.getNumber().getSuffix();
        int n2 = string2.length() + (n >> 2) + string3.length();
        boolean bl3 = false;
        bl3 = hexFormat.getNumber().getRemoveLeadingZeros();
        StringBuilder stringBuilder2 = stringBuilder = new StringBuilder(n2);
        boolean bl4 = false;
        stringBuilder2.append(string2);
        int n3 = n;
        while (n3 > 0) {
            int n4 = (int)(l2 >> (n3 -= 4) & 0xFL);
            if (bl3 = bl3 && n4 == 0 && n3 > 0) continue;
            stringBuilder2.append(string.charAt(n4));
        }
        stringBuilder2.append(string3);
        String string4 = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string4, "StringBuilder(capacity).\u2026builderAction).toString()");
        return string4;
    }

    @ExperimentalStdlibApi
    private static final long hexToLongImpl(String string, int n, int n2, HexFormat hexFormat, int n3) {
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n, n2, string.length());
        String string2 = hexFormat.getNumber().getPrefix();
        String string3 = hexFormat.getNumber().getSuffix();
        if (string2.length() + string3.length() >= n2 - n) {
            StringBuilder stringBuilder = new StringBuilder().append("Expected a hexadecimal number with prefix \"").append(string2).append("\" and suffix \"").append(string3).append("\", but was ");
            String string4 = string;
            Intrinsics.checkNotNull(string4, "null cannot be cast to non-null type java.lang.String");
            String string5 = string4.substring(n, n2);
            Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            throw new NumberFormatException(stringBuilder.append(string5).toString());
        }
        int n4 = HexExtensionsKt.checkContainsAt(string, string2, n, n2, "prefix");
        int n5 = n2 - string3.length();
        HexExtensionsKt.checkContainsAt(string, string3, n5, n2, "suffix");
        HexExtensionsKt.checkHexLength(string, n4, n5, n3, false);
        long l = 0L;
        for (int i = n4; i < n5; ++i) {
            l = l << 4 | (long)HexExtensionsKt.decimalFromHexDigitAt(string, i);
        }
        return l;
    }

    static long hexToLongImpl$default(String string, int n, int n2, HexFormat hexFormat, int n3, int n4, Object object) {
        if ((n4 & 1) != 0) {
            n = 0;
        }
        if ((n4 & 2) != 0) {
            n2 = string.length();
        }
        return HexExtensionsKt.hexToLongImpl(string, n, n2, hexFormat, n3);
    }

    private static final int checkContainsAt(String string, String string2, int n, int n2, String string3) {
        int n3 = n + string2.length();
        if (n3 > n2 || !StringsKt.regionMatches(string, n, string2, 0, string2.length(), true)) {
            StringBuilder stringBuilder = new StringBuilder().append("Expected ").append(string3).append(" \"").append(string2).append("\" at index ").append(n).append(", but was ");
            String string4 = string;
            int n4 = RangesKt.coerceAtMost(n3, n2);
            Intrinsics.checkNotNull(string4, "null cannot be cast to non-null type java.lang.String");
            String string5 = string4.substring(n, n4);
            Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            throw new NumberFormatException(stringBuilder.append(string5).toString());
        }
        return n3;
    }

    private static final void checkHexLength(String string, int n, int n2, int n3, boolean bl) {
        boolean bl2;
        int n4 = n2 - n;
        boolean bl3 = bl ? n4 == n3 : (bl2 = n4 <= n3);
        if (!bl2) {
            String string2 = bl ? "exactly" : "at most";
            String string3 = string;
            Intrinsics.checkNotNull(string3, "null cannot be cast to non-null type java.lang.String");
            String string4 = string3.substring(n, n2);
            Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            String string5 = string4;
            throw new NumberFormatException("Expected " + string2 + ' ' + n3 + " hexadecimal digits at index " + n + ", but was " + string5 + " of length " + n4);
        }
    }

    private static final int decimalFromHexDigitAt(String string, int n) {
        char c = string.charAt(n);
        if (c > '\u007f' || HEX_DIGITS_TO_DECIMAL[c] < 0) {
            throw new NumberFormatException("Expected a hexadecimal digit at index " + n + ", but was " + string.charAt(n));
        }
        return HEX_DIGITS_TO_DECIMAL[c];
    }

    static {
        boolean bl;
        int n;
        char c;
        char c2;
        int n2;
        int[] nArray;
        int n3;
        int n4 = 0;
        int[] nArray2 = new int[128];
        while (n4 < 128) {
            n3 = n4++;
            nArray2[n3] = -1;
        }
        nArray2 = nArray = nArray2;
        n3 = 0;
        CharSequence charSequence = LOWER_CASE_HEX_DIGITS;
        boolean bl2 = false;
        int n5 = 0;
        for (n2 = 0; n2 < charSequence.length(); ++n2) {
            c2 = charSequence.charAt(n2);
            int n6 = n5++;
            c = c2;
            n = n6;
            bl = false;
            nArray2[c] = n;
        }
        charSequence = UPPER_CASE_HEX_DIGITS;
        bl2 = false;
        n5 = 0;
        for (n2 = 0; n2 < charSequence.length(); ++n2) {
            c2 = charSequence.charAt(n2);
            int n7 = n5++;
            c = c2;
            n = n7;
            bl = false;
            nArray2[c] = n;
        }
        HEX_DIGITS_TO_DECIMAL = nArray;
    }
}

