/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.encoding;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.AbstractList;
import kotlin.io.encoding.Base64Kt;
import kotlin.io.encoding.ExperimentalEncodingApi;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0017\u0018\u0000 22\u00020\u0001:\u00012B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0000\u00a2\u0006\u0002\b\rJ%\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0000\u00a2\u0006\u0002\b\u0013J \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0011H\u0002J%\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0000\u00a2\u0006\u0002\b\u001bJ\"\u0010\u001c\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011J\"\u0010\u001c\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011J0\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0002J4\u0010\u001f\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\f2\b\b\u0002\u0010\u0017\u001a\u00020\u00112\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011J4\u0010\u001f\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\f2\b\b\u0002\u0010\u0017\u001a\u00020\u00112\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011J \u0010 \u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0002J\"\u0010!\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011J4\u0010\"\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\f2\b\b\u0002\u0010\u0017\u001a\u00020\u00112\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011J5\u0010#\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0000\u00a2\u0006\u0002\b$J\u0010\u0010%\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0011H\u0002J=\u0010&\u001a\u0002H'\"\f\b\u0000\u0010'*\u00060(j\u0002`)2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001e\u001a\u0002H'2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0002\u0010*J\"\u0010+\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0011J%\u0010,\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0000\u00a2\u0006\u0002\b-J(\u0010.\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010/\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u00100\u001a\u00020\u0011H\u0002J \u00101\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0002R\u0014\u0010\u0004\u001a\u00020\u0003X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u00063"}, d2={"Lkotlin/io/encoding/Base64;", "", "isUrlSafe", "", "isMimeScheme", "(ZZ)V", "isMimeScheme$kotlin_stdlib", "()Z", "isUrlSafe$kotlin_stdlib", "bytesToStringImpl", "", "source", "", "bytesToStringImpl$kotlin_stdlib", "charsToBytesImpl", "", "startIndex", "", "endIndex", "charsToBytesImpl$kotlin_stdlib", "checkDestinationBounds", "", "destinationSize", "destinationOffset", "capacityNeeded", "checkSourceBounds", "sourceSize", "checkSourceBounds$kotlin_stdlib", "decode", "decodeImpl", "destination", "decodeIntoByteArray", "decodeSize", "encode", "encodeIntoByteArray", "encodeIntoByteArrayImpl", "encodeIntoByteArrayImpl$kotlin_stdlib", "encodeSize", "encodeToAppendable", "A", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "([BLjava/lang/Appendable;II)Ljava/lang/Appendable;", "encodeToByteArray", "encodeToByteArrayImpl", "encodeToByteArrayImpl$kotlin_stdlib", "handlePaddingSymbol", "padIndex", "byteStart", "skipIllegalSymbolsIfMime", "Default", "kotlin-stdlib"})
@SinceKotlin(version="1.8")
@ExperimentalEncodingApi
public class Base64 {
    @NotNull
    public static final Default Default = new Default(null);
    private final boolean isUrlSafe;
    private final boolean isMimeScheme;
    private static final int bitsPerByte = 8;
    private static final int bitsPerSymbol = 6;
    public static final int bytesPerGroup = 3;
    public static final int symbolsPerGroup = 4;
    public static final byte padSymbol = 61;
    public static final int mimeLineLength = 76;
    private static final int mimeGroupsPerLine = 19;
    @NotNull
    private static final byte[] mimeLineSeparatorSymbols;
    @NotNull
    private static final Base64 UrlSafe;
    @NotNull
    private static final Base64 Mime;

    private Base64(boolean bl, boolean bl2) {
        boolean bl3;
        this.isUrlSafe = bl;
        this.isMimeScheme = bl2;
        boolean bl4 = bl3 = !this.isUrlSafe || !this.isMimeScheme;
        if (!bl3) {
            String string = "Failed requirement.";
            throw new IllegalArgumentException(string.toString());
        }
    }

    public final boolean isUrlSafe$kotlin_stdlib() {
        return this.isUrlSafe;
    }

    public final boolean isMimeScheme$kotlin_stdlib() {
        return this.isMimeScheme;
    }

    @NotNull
    public final byte[] encodeToByteArray(@NotNull byte[] byArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(byArray, "source");
        return this.encodeToByteArrayImpl$kotlin_stdlib(byArray, n, n2);
    }

    public static byte[] encodeToByteArray$default(Base64 base64, byte[] byArray, int n, int n2, int n3, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encodeToByteArray");
        }
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = byArray.length;
        }
        return base64.encodeToByteArray(byArray, n, n2);
    }

    public final int encodeIntoByteArray(@NotNull byte[] byArray, @NotNull byte[] byArray2, int n, int n2, int n3) {
        Intrinsics.checkNotNullParameter(byArray, "source");
        Intrinsics.checkNotNullParameter(byArray2, "destination");
        return this.encodeIntoByteArrayImpl$kotlin_stdlib(byArray, byArray2, n, n2, n3);
    }

    public static int encodeIntoByteArray$default(Base64 base64, byte[] byArray, byte[] byArray2, int n, int n2, int n3, int n4, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encodeIntoByteArray");
        }
        if ((n4 & 4) != 0) {
            n = 0;
        }
        if ((n4 & 8) != 0) {
            n2 = 0;
        }
        if ((n4 & 0x10) != 0) {
            n3 = byArray.length;
        }
        return base64.encodeIntoByteArray(byArray, byArray2, n, n2, n3);
    }

    @NotNull
    public final String encode(@NotNull byte[] byArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(byArray, "source");
        byte[] byArray2 = this.encodeToByteArrayImpl$kotlin_stdlib(byArray, n, n2);
        return new String(byArray2, Charsets.ISO_8859_1);
    }

    public static String encode$default(Base64 base64, byte[] byArray, int n, int n2, int n3, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encode");
        }
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = byArray.length;
        }
        return base64.encode(byArray, n, n2);
    }

    @NotNull
    public final <A extends Appendable> A encodeToAppendable(@NotNull byte[] byArray, @NotNull A a, int n, int n2) {
        Intrinsics.checkNotNullParameter(byArray, "source");
        Intrinsics.checkNotNullParameter(a, "destination");
        byte[] byArray2 = this.encodeToByteArrayImpl$kotlin_stdlib(byArray, n, n2);
        String string = new String(byArray2, Charsets.ISO_8859_1);
        a.append(string);
        return a;
    }

    public static Appendable encodeToAppendable$default(Base64 base64, byte[] byArray, Appendable appendable, int n, int n2, int n3, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encodeToAppendable");
        }
        if ((n3 & 4) != 0) {
            n = 0;
        }
        if ((n3 & 8) != 0) {
            n2 = byArray.length;
        }
        return base64.encodeToAppendable(byArray, appendable, n, n2);
    }

    @NotNull
    public final byte[] decode(@NotNull byte[] byArray, int n, int n2) {
        boolean bl;
        Intrinsics.checkNotNullParameter(byArray, "source");
        this.checkSourceBounds$kotlin_stdlib(byArray.length, n, n2);
        int n3 = this.decodeSize(byArray, n, n2);
        byte[] byArray2 = new byte[n3];
        int n4 = this.decodeImpl(byArray, byArray2, 0, n, n2);
        boolean bl2 = bl = n4 == byArray2.length;
        if (!bl) {
            String string = "Check failed.";
            throw new IllegalStateException(string.toString());
        }
        return byArray2;
    }

    public static byte[] decode$default(Base64 base64, byte[] byArray, int n, int n2, int n3, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decode");
        }
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = byArray.length;
        }
        return base64.decode(byArray, n, n2);
    }

    public final int decodeIntoByteArray(@NotNull byte[] byArray, @NotNull byte[] byArray2, int n, int n2, int n3) {
        Intrinsics.checkNotNullParameter(byArray, "source");
        Intrinsics.checkNotNullParameter(byArray2, "destination");
        this.checkSourceBounds$kotlin_stdlib(byArray.length, n2, n3);
        this.checkDestinationBounds(byArray2.length, n, this.decodeSize(byArray, n2, n3));
        return this.decodeImpl(byArray, byArray2, n, n2, n3);
    }

    public static int decodeIntoByteArray$default(Base64 base64, byte[] byArray, byte[] byArray2, int n, int n2, int n3, int n4, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decodeIntoByteArray");
        }
        if ((n4 & 4) != 0) {
            n = 0;
        }
        if ((n4 & 8) != 0) {
            n2 = 0;
        }
        if ((n4 & 0x10) != 0) {
            n3 = byArray.length;
        }
        return base64.decodeIntoByteArray(byArray, byArray2, n, n2, n3);
    }

    @NotNull
    public final byte[] decode(@NotNull CharSequence charSequence, int n, int n2) {
        byte[] byArray;
        Intrinsics.checkNotNullParameter(charSequence, "source");
        Base64 base64 = this;
        if (charSequence instanceof String) {
            base64.checkSourceBounds$kotlin_stdlib(charSequence.length(), n, n2);
            String string = ((String)charSequence).substring(n, n2);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            String string2 = string;
            Charset charset = Charsets.ISO_8859_1;
            Intrinsics.checkNotNull(string2, "null cannot be cast to non-null type java.lang.String");
            byte[] byArray2 = string2.getBytes(charset);
            byArray = byArray2;
            Intrinsics.checkNotNullExpressionValue(byArray2, "this as java.lang.String).getBytes(charset)");
        } else {
            byArray = base64.charsToBytesImpl$kotlin_stdlib(charSequence, n, n2);
        }
        byte[] byArray3 = byArray;
        return Base64.decode$default(this, byArray3, 0, 0, 6, null);
    }

    public static byte[] decode$default(Base64 base64, CharSequence charSequence, int n, int n2, int n3, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decode");
        }
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = charSequence.length();
        }
        return base64.decode(charSequence, n, n2);
    }

    public final int decodeIntoByteArray(@NotNull CharSequence charSequence, @NotNull byte[] byArray, int n, int n2, int n3) {
        byte[] byArray2;
        Intrinsics.checkNotNullParameter(charSequence, "source");
        Intrinsics.checkNotNullParameter(byArray, "destination");
        Base64 base64 = this;
        if (charSequence instanceof String) {
            base64.checkSourceBounds$kotlin_stdlib(charSequence.length(), n2, n3);
            String string = ((String)charSequence).substring(n2, n3);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            String string2 = string;
            Charset charset = Charsets.ISO_8859_1;
            Intrinsics.checkNotNull(string2, "null cannot be cast to non-null type java.lang.String");
            byte[] byArray3 = string2.getBytes(charset);
            byArray2 = byArray3;
            Intrinsics.checkNotNullExpressionValue(byArray3, "this as java.lang.String).getBytes(charset)");
        } else {
            byArray2 = base64.charsToBytesImpl$kotlin_stdlib(charSequence, n2, n3);
        }
        byte[] byArray4 = byArray2;
        return Base64.decodeIntoByteArray$default(this, byArray4, byArray, n, 0, 0, 24, null);
    }

    public static int decodeIntoByteArray$default(Base64 base64, CharSequence charSequence, byte[] byArray, int n, int n2, int n3, int n4, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decodeIntoByteArray");
        }
        if ((n4 & 4) != 0) {
            n = 0;
        }
        if ((n4 & 8) != 0) {
            n2 = 0;
        }
        if ((n4 & 0x10) != 0) {
            n3 = charSequence.length();
        }
        return base64.decodeIntoByteArray(charSequence, byArray, n, n2, n3);
    }

    @NotNull
    public final byte[] encodeToByteArrayImpl$kotlin_stdlib(@NotNull byte[] byArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(byArray, "source");
        this.checkSourceBounds$kotlin_stdlib(byArray.length, n, n2);
        int n3 = this.encodeSize(n2 - n);
        byte[] byArray2 = new byte[n3];
        this.encodeIntoByteArrayImpl$kotlin_stdlib(byArray, byArray2, 0, n, n2);
        return byArray2;
    }

    public final int encodeIntoByteArrayImpl$kotlin_stdlib(@NotNull byte[] byArray, @NotNull byte[] byArray2, int n, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        Intrinsics.checkNotNullParameter(byArray, "source");
        Intrinsics.checkNotNullParameter(byArray2, "destination");
        this.checkSourceBounds$kotlin_stdlib(byArray.length, n2, n3);
        this.checkDestinationBounds(byArray2.length, n, this.encodeSize(n3 - n2));
        byte[] byArray3 = this.isUrlSafe ? Base64Kt.access$getBase64UrlEncodeMap$p() : Base64Kt.access$getBase64EncodeMap$p();
        int n9 = n2;
        int n10 = n;
        int n11 = n8 = this.isMimeScheme ? 19 : Integer.MAX_VALUE;
        while (n9 + 2 < n3) {
            n7 = Math.min((n3 - n9) / 3, n8);
            for (n6 = 0; n6 < n7; ++n6) {
                n5 = byArray[n9++] & 0xFF;
                n4 = byArray[n9++] & 0xFF;
                int n12 = byArray[n9++] & 0xFF;
                int n13 = n5 << 16 | n4 << 8 | n12;
                byArray2[n10++] = byArray3[n13 >>> 18];
                byArray2[n10++] = byArray3[n13 >>> 12 & 0x3F];
                byArray2[n10++] = byArray3[n13 >>> 6 & 0x3F];
                byArray2[n10++] = byArray3[n13 & 0x3F];
            }
            if (n7 != n8 || n9 == n3) continue;
            byArray2[n10++] = mimeLineSeparatorSymbols[0];
            byArray2[n10++] = mimeLineSeparatorSymbols[1];
        }
        switch (n3 - n9) {
            case 1: {
                n6 = byArray[n9++] & 0xFF;
                n5 = n6 << 4;
                byArray2[n10++] = byArray3[n5 >>> 6];
                byArray2[n10++] = byArray3[n5 & 0x3F];
                byArray2[n10++] = 61;
                byArray2[n10++] = 61;
                break;
            }
            case 2: {
                n6 = byArray[n9++] & 0xFF;
                n5 = byArray[n9++] & 0xFF;
                n4 = n6 << 10 | n5 << 2;
                byArray2[n10++] = byArray3[n4 >>> 12];
                byArray2[n10++] = byArray3[n4 >>> 6 & 0x3F];
                byArray2[n10++] = byArray3[n4 & 0x3F];
                byArray2[n10++] = 61;
            }
        }
        int n14 = n7 = n9 == n3 ? 1 : 0;
        if (n7 == 0) {
            String string = "Check failed.";
            throw new IllegalStateException(string.toString());
        }
        return n10 - n;
    }

    private final int encodeSize(int n) {
        int n2 = (n + 3 - 1) / 3;
        int n3 = this.isMimeScheme ? (n2 - 1) / 19 : 0;
        int n4 = n2 * 4 + n3 * 2;
        if (n4 < 0) {
            throw new IllegalArgumentException("Input is too big");
        }
        return n4;
    }

    private final int decodeImpl(byte[] byArray, byte[] byArray2, int n, int n2, int n3) {
        int n4;
        int[] nArray = this.isUrlSafe ? Base64Kt.access$getBase64UrlDecodeMap$p() : Base64Kt.access$getBase64DecodeMap$p();
        int n5 = 0;
        int n6 = -8;
        int n7 = n2;
        int n8 = n;
        while (n7 < n3) {
            int n9;
            if (n6 == -8 && n7 + 3 < n3) {
                int n10;
                int n11;
                int n12;
                n4 = nArray[byArray[n7++] & 0xFF];
                n9 = nArray[byArray[n7++] & 0xFF];
                if ((n12 = n4 << 18 | n9 << 12 | (n11 = nArray[byArray[n7++] & 0xFF]) << 6 | (n10 = nArray[byArray[n7++] & 0xFF])) >= 0) {
                    byArray2[n8++] = (byte)(n12 >> 16);
                    byArray2[n8++] = (byte)(n12 >> 8);
                    byArray2[n8++] = (byte)n12;
                    continue;
                }
                n7 -= 4;
            }
            if ((n9 = nArray[n4 = byArray[n7] & 0xFF]) < 0) {
                if (n9 == -2) {
                    n7 = this.handlePaddingSymbol(byArray, n7, n3, n6);
                    break;
                }
                if (this.isMimeScheme) {
                    ++n7;
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder().append("Invalid symbol '").append((char)n4).append("'(");
                String string = Integer.toString(n4, CharsKt.checkRadix(8));
                Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
                throw new IllegalArgumentException(stringBuilder.append(string).append(") at index ").append(n7).toString());
            }
            ++n7;
            n5 = n5 << 6 | n9;
            if ((n6 += 6) < 0) continue;
            byArray2[n8++] = (byte)(n5 >>> n6);
            n5 &= (1 << n6) - 1;
            n6 -= 8;
        }
        if (n6 == -2) {
            throw new IllegalArgumentException("The last unit of input does not have enough bits");
        }
        if ((n7 = this.skipIllegalSymbolsIfMime(byArray, n7, n3)) < n3) {
            n4 = byArray[n7] & 0xFF;
            StringBuilder stringBuilder = new StringBuilder().append("Symbol '").append((char)n4).append("'(");
            String string = Integer.toString(n4, CharsKt.checkRadix(8));
            Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
            throw new IllegalArgumentException(stringBuilder.append(string).append(") at index ").append(n7 - 1).append(" is prohibited after the pad character").toString());
        }
        return n8 - n;
    }

    private final int decodeSize(byte[] byArray, int n, int n2) {
        int n3 = n2 - n;
        if (n3 == 0) {
            return 1;
        }
        if (n3 == 1) {
            throw new IllegalArgumentException("Input should have at list 2 symbols for Base64 decoding, startIndex: " + n + ", endIndex: " + n2);
        }
        if (this.isMimeScheme) {
            for (int i = n; i < n2; ++i) {
                int n4 = byArray[i] & 0xFF;
                int n5 = Base64Kt.access$getBase64DecodeMap$p()[n4];
                if (n5 >= 0) continue;
                if (n5 == -2) {
                    n3 -= n2 - i;
                    break;
                }
                --n3;
            }
        } else if (byArray[n2 - 1] == 61) {
            --n3;
            if (byArray[n2 - 2] == 61) {
                --n3;
            }
        }
        return (int)((long)n3 * (long)6 / (long)8);
    }

    @NotNull
    public final byte[] charsToBytesImpl$kotlin_stdlib(@NotNull CharSequence charSequence, int n, int n2) {
        Intrinsics.checkNotNullParameter(charSequence, "source");
        this.checkSourceBounds$kotlin_stdlib(charSequence.length(), n, n2);
        byte[] byArray = new byte[n2 - n];
        int n3 = 0;
        for (int i = n; i < n2; ++i) {
            char c = charSequence.charAt(i);
            byArray[n3++] = c <= '\u00ff' ? (int)c : 63;
        }
        return byArray;
    }

    @NotNull
    public final String bytesToStringImpl$kotlin_stdlib(@NotNull byte[] byArray) {
        Intrinsics.checkNotNullParameter(byArray, "source");
        StringBuilder stringBuilder = new StringBuilder(byArray.length);
        for (byte by : byArray) {
            stringBuilder.append((char)by);
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "stringBuilder.toString()");
        return string;
    }

    private final int handlePaddingSymbol(byte[] byArray, int n, int n2, int n3) {
        int n4;
        switch (n3) {
            case -8: {
                throw new IllegalArgumentException("Redundant pad character at index " + n);
            }
            case -2: {
                n4 = n + 1;
                break;
            }
            case -4: {
                int n5 = this.skipIllegalSymbolsIfMime(byArray, n + 1, n2);
                if (n5 == n2 || byArray[n5] != 61) {
                    throw new IllegalArgumentException("Missing one pad character at index " + n5);
                }
                n4 = n5 + 1;
                break;
            }
            case -6: {
                n4 = n + 1;
                break;
            }
            default: {
                throw new IllegalStateException("Unreachable".toString());
            }
        }
        return n4;
    }

    private final int skipIllegalSymbolsIfMime(byte[] byArray, int n, int n2) {
        int n3;
        if (!this.isMimeScheme) {
            return n;
        }
        for (n3 = n; n3 < n2; ++n3) {
            int n4 = byArray[n3] & 0xFF;
            if (Base64Kt.access$getBase64DecodeMap$p()[n4] == -1) continue;
            return n3;
        }
        return n3;
    }

    public final void checkSourceBounds$kotlin_stdlib(int n, int n2, int n3) {
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n2, n3, n);
    }

    private final void checkDestinationBounds(int n, int n2, int n3) {
        if (n2 < 0 || n2 > n) {
            throw new IndexOutOfBoundsException("destination offset: " + n2 + ", destination size: " + n);
        }
        int n4 = n2 + n3;
        if (n4 < 0 || n4 > n) {
            throw new IndexOutOfBoundsException("The destination array does not have enough capacity, destination offset: " + n2 + ", destination size: " + n + ", capacity needed: " + n3);
        }
    }

    public Base64(boolean bl, boolean bl2, DefaultConstructorMarker defaultConstructorMarker) {
        this(bl, bl2);
    }

    public static final byte[] access$getMimeLineSeparatorSymbols$cp() {
        return mimeLineSeparatorSymbols;
    }

    public static final Base64 access$getUrlSafe$cp() {
        return UrlSafe;
    }

    public static final Base64 access$getMime$cp() {
        return Mime;
    }

    static {
        byte[] byArray = new byte[]{13, 10};
        mimeLineSeparatorSymbols = byArray;
        UrlSafe = new Base64(true, false);
        Mime = new Base64(false, true);
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0005R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0080T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0080T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000fX\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0080T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tX\u0080T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lkotlin/io/encoding/Base64$Default;", "Lkotlin/io/encoding/Base64;", "()V", "Mime", "getMime", "()Lkotlin/io/encoding/Base64;", "UrlSafe", "getUrlSafe", "bitsPerByte", "", "bitsPerSymbol", "bytesPerGroup", "mimeGroupsPerLine", "mimeLineLength", "mimeLineSeparatorSymbols", "", "getMimeLineSeparatorSymbols$kotlin_stdlib", "()[B", "padSymbol", "", "symbolsPerGroup", "kotlin-stdlib"})
    public static final class Default
    extends Base64 {
        private Default() {
            super(false, false, null);
        }

        @NotNull
        public final byte[] getMimeLineSeparatorSymbols$kotlin_stdlib() {
            return Base64.access$getMimeLineSeparatorSymbols$cp();
        }

        @NotNull
        public final Base64 getUrlSafe() {
            return Base64.access$getUrlSafe$cp();
        }

        @NotNull
        public final Base64 getMime() {
            return Base64.access$getMime$cp();
        }

        public Default(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}

