/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.encoding;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.ArraysKt;
import kotlin.io.encoding.ExperimentalEncodingApi;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000\u001e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0001\"\u0016\u0010\u0000\u001a\u00020\u00018\u0002X\u0083\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003\"\u0016\u0010\u0004\u001a\u00020\u00058\u0002X\u0083\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0003\"\u0016\u0010\u0007\u001a\u00020\u00018\u0002X\u0083\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\b\u0010\u0003\"\u0016\u0010\t\u001a\u00020\u00058\u0002X\u0083\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\n\u0010\u0003\u00a8\u0006\u000f"}, d2={"base64DecodeMap", "", "getBase64DecodeMap$annotations", "()V", "base64EncodeMap", "", "getBase64EncodeMap$annotations", "base64UrlDecodeMap", "getBase64UrlDecodeMap$annotations", "base64UrlEncodeMap", "getBase64UrlEncodeMap$annotations", "isInMimeAlphabet", "", "symbol", "", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nBase64.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Base64.kt\nkotlin/io/encoding/Base64Kt\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,649:1\n13384#2,3:650\n13384#2,3:653\n*S KotlinDebug\n*F\n+ 1 Base64.kt\nkotlin/io/encoding/Base64Kt\n*L\n584#1:650,3\n603#1:653,3\n*E\n"})
public final class Base64Kt {
    @NotNull
    private static final byte[] base64EncodeMap;
    @NotNull
    private static final int[] base64DecodeMap;
    @NotNull
    private static final byte[] base64UrlEncodeMap;
    @NotNull
    private static final int[] base64UrlDecodeMap;

    private static void getBase64EncodeMap$annotations() {
    }

    @ExperimentalEncodingApi
    private static void getBase64DecodeMap$annotations() {
    }

    private static void getBase64UrlEncodeMap$annotations() {
    }

    @ExperimentalEncodingApi
    private static void getBase64UrlDecodeMap$annotations() {
    }

    @SinceKotlin(version="1.8")
    @ExperimentalEncodingApi
    public static final boolean isInMimeAlphabet(int n) {
        return (0 <= n ? n < base64DecodeMap.length : false) && base64DecodeMap[n] != -1;
    }

    public static final byte[] access$getBase64UrlEncodeMap$p() {
        return base64UrlEncodeMap;
    }

    public static final byte[] access$getBase64EncodeMap$p() {
        return base64EncodeMap;
    }

    public static final int[] access$getBase64UrlDecodeMap$p() {
        return base64UrlDecodeMap;
    }

    public static final int[] access$getBase64DecodeMap$p() {
        return base64DecodeMap;
    }

    static {
        boolean bl;
        int n;
        byte by;
        Object[] objectArray = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        base64EncodeMap = objectArray;
        Object[] objectArray2 = objectArray = (Object[])new int[256];
        boolean bl2 = false;
        ArraysKt.fill$default((int[])objectArray2, -1, 0, 0, 6, null);
        objectArray2[61] = -2;
        byte[] byArray = base64EncodeMap;
        boolean bl3 = false;
        int n2 = 0;
        for (byte by2 : byArray) {
            int n3 = n2++;
            by = by2;
            n = n3;
            bl = false;
            objectArray2[by] = n;
        }
        base64DecodeMap = objectArray;
        objectArray = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        base64UrlEncodeMap = objectArray;
        objectArray2 = objectArray = (Object[])new int[256];
        bl2 = false;
        ArraysKt.fill$default((int[])objectArray2, -1, 0, 0, 6, null);
        objectArray2[61] = -2;
        byArray = base64UrlEncodeMap;
        bl3 = false;
        n2 = 0;
        for (byte by2 : byArray) {
            int n4 = n2++;
            by = by2;
            n = n4;
            bl = false;
            objectArray2[by] = n;
        }
        base64UrlDecodeMap = objectArray;
    }
}

