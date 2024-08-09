/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import kotlin.ExperimentalStdlibApi;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.HexExtensionsKt;
import kotlin.text.HexFormat;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\f\u001a\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0005\u001a\u001f\u0010\u0006\u001a\u00020\u0007*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\b\u001a\u001f\u0010\t\u001a\u00020\n*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u001a\u001f\u0010\f\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000e\u001a\u001f\u0010\u000f\u001a\u00020\u0010*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011\u001a!\u0010\u0012\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a5\u0010\u0012\u001a\u00020\u0002*\u00020\u00072\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00162\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019\u001a!\u0010\u0012\u001a\u00020\u0002*\u00020\u00072\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\u001b\u001a!\u0010\u0012\u001a\u00020\u0002*\u00020\n2\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001a!\u0010\u0012\u001a\u00020\u0002*\u00020\r2\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001f\u001a!\u0010\u0012\u001a\u00020\u0002*\u00020\u00102\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b \u0010!\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\""}, d2={"hexToUByte", "Lkotlin/UByte;", "", "format", "Lkotlin/text/HexFormat;", "(Ljava/lang/String;Lkotlin/text/HexFormat;)B", "hexToUByteArray", "Lkotlin/UByteArray;", "(Ljava/lang/String;Lkotlin/text/HexFormat;)[B", "hexToUInt", "Lkotlin/UInt;", "(Ljava/lang/String;Lkotlin/text/HexFormat;)I", "hexToULong", "Lkotlin/ULong;", "(Ljava/lang/String;Lkotlin/text/HexFormat;)J", "hexToUShort", "Lkotlin/UShort;", "(Ljava/lang/String;Lkotlin/text/HexFormat;)S", "toHexString", "toHexString-ZQbaR00", "(BLkotlin/text/HexFormat;)Ljava/lang/String;", "startIndex", "", "endIndex", "toHexString-lZCiFrA", "([BIILkotlin/text/HexFormat;)Ljava/lang/String;", "toHexString-zHuV2wU", "([BLkotlin/text/HexFormat;)Ljava/lang/String;", "toHexString-8M7LxHw", "(ILkotlin/text/HexFormat;)Ljava/lang/String;", "toHexString-8UJCm-I", "(JLkotlin/text/HexFormat;)Ljava/lang/String;", "toHexString-r3ox_E0", "(SLkotlin/text/HexFormat;)Ljava/lang/String;", "kotlin-stdlib"})
public final class UHexExtensionsKt {
    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final String toHexString-zHuV2wU(byte[] byArray, HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(byArray, "$this$toHexString");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(byArray, hexFormat);
    }

    static String toHexString-zHuV2wU$default(byte[] byArray, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        Intrinsics.checkNotNullParameter(byArray, "$this$toHexString");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(byArray, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final String toHexString-lZCiFrA(byte[] byArray, int n, int n2, HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(byArray, "$this$toHexString");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(byArray, n, n2, hexFormat);
    }

    static String toHexString-lZCiFrA$default(byte[] byArray, int n, int n2, HexFormat hexFormat, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = UByteArray.getSize-impl(byArray);
        }
        if ((n3 & 4) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        Intrinsics.checkNotNullParameter(byArray, "$this$toHexString");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(byArray, n, n2, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final byte[] hexToUByteArray(String string, HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return UByteArray.constructor-impl(HexExtensionsKt.hexToByteArray(string, hexFormat));
    }

    static byte[] hexToUByteArray$default(String string, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return UByteArray.constructor-impl(HexExtensionsKt.hexToByteArray(string, hexFormat));
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @InlineOnly
    private static final String toHexString-ZQbaR00(byte by, HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(by, hexFormat);
    }

    static String toHexString-ZQbaR00$default(byte by, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(by, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @InlineOnly
    private static final byte hexToUByte(String string, HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return UByte.constructor-impl(HexExtensionsKt.hexToByte(string, hexFormat));
    }

    static byte hexToUByte$default(String string, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return UByte.constructor-impl(HexExtensionsKt.hexToByte(string, hexFormat));
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @InlineOnly
    private static final String toHexString-r3ox_E0(short s, HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(s, hexFormat);
    }

    static String toHexString-r3ox_E0$default(short s, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(s, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @InlineOnly
    private static final short hexToUShort(String string, HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return UShort.constructor-impl(HexExtensionsKt.hexToShort(string, hexFormat));
    }

    static short hexToUShort$default(String string, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return UShort.constructor-impl(HexExtensionsKt.hexToShort(string, hexFormat));
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @InlineOnly
    private static final String toHexString-8M7LxHw(int n, HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(n, hexFormat);
    }

    static String toHexString-8M7LxHw$default(int n, HexFormat hexFormat, int n2, Object object) {
        if ((n2 & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(n, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @InlineOnly
    private static final int hexToUInt(String string, HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return UInt.constructor-impl(HexExtensionsKt.hexToInt(string, hexFormat));
    }

    static int hexToUInt$default(String string, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return UInt.constructor-impl(HexExtensionsKt.hexToInt(string, hexFormat));
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @InlineOnly
    private static final String toHexString-8UJCm-I(long l, HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(l, hexFormat);
    }

    static String toHexString-8UJCm-I$default(long l, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return HexExtensionsKt.toHexString(l, hexFormat);
    }

    @ExperimentalStdlibApi
    @SinceKotlin(version="1.9")
    @InlineOnly
    private static final long hexToULong(String string, HexFormat hexFormat) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return ULong.constructor-impl(HexExtensionsKt.hexToLong(string, hexFormat));
    }

    static long hexToULong$default(String string, HexFormat hexFormat, int n, Object object) {
        if ((n & 1) != 0) {
            hexFormat = HexFormat.Companion.getDefault();
        }
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(hexFormat, "format");
        return ULong.constructor-impl(HexExtensionsKt.hexToLong(string, hexFormat));
    }
}

