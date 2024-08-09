/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000.\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u0006\u001a\u001b\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\t\u001a\u0013\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u000b\u001a\u001b\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\f\u001a\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u000f\u001a\u001b\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\u0010\u001a\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u0013\u001a\u001b\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0015"}, d2={"numberFormatError", "", "input", "", "toByteOrNull", "", "(Ljava/lang/String;)Ljava/lang/Byte;", "radix", "", "(Ljava/lang/String;I)Ljava/lang/Byte;", "toIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "(Ljava/lang/String;I)Ljava/lang/Integer;", "toLongOrNull", "", "(Ljava/lang/String;)Ljava/lang/Long;", "(Ljava/lang/String;I)Ljava/lang/Long;", "toShortOrNull", "", "(Ljava/lang/String;)Ljava/lang/Short;", "(Ljava/lang/String;I)Ljava/lang/Short;", "kotlin-stdlib"}, xs="kotlin/text/StringsKt")
class StringsKt__StringNumberConversionsKt
extends StringsKt__StringNumberConversionsJVMKt {
    @SinceKotlin(version="1.1")
    @Nullable
    public static final Byte toByteOrNull(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return StringsKt.toByteOrNull(string, 10);
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Byte toByteOrNull(@NotNull String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Integer n2 = StringsKt.toIntOrNull(string, n);
        if (n2 == null) {
            return null;
        }
        int n3 = n2;
        if (n3 < -128 || n3 > 127) {
            return null;
        }
        return (byte)n3;
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Short toShortOrNull(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return StringsKt.toShortOrNull(string, 10);
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Short toShortOrNull(@NotNull String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Integer n2 = StringsKt.toIntOrNull(string, n);
        if (n2 == null) {
            return null;
        }
        int n3 = n2;
        if (n3 < Short.MIN_VALUE || n3 > Short.MAX_VALUE) {
            return null;
        }
        return (short)n3;
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Integer toIntOrNull(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return StringsKt.toIntOrNull(string, 10);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SinceKotlin(version="1.1")
    @Nullable
    public static final Integer toIntOrNull(@NotNull String string, int n) {
        int n2;
        Intrinsics.checkNotNullParameter(string, "<this>");
        CharsKt.checkRadix(n);
        int n3 = string.length();
        if (n3 == 0) {
            return null;
        }
        int n4 = 0;
        boolean bl = false;
        int n5 = 0;
        char c = string.charAt(0);
        if (Intrinsics.compare(c, 48) < 0) {
            if (n3 == 1) {
                return null;
            }
            n4 = 1;
            if (c == '-') {
                bl = true;
                n5 = Integer.MIN_VALUE;
            } else {
                if (c != '+') return null;
                bl = false;
                n5 = -2147483647;
            }
        } else {
            n4 = 0;
            bl = false;
            n5 = -2147483647;
        }
        int n6 = n2 = -59652323;
        int n7 = 0;
        for (int i = n4; i < n3; ++i) {
            int n8 = CharsKt.digitOf(string.charAt(i), n);
            if (n8 < 0) {
                return null;
            }
            if (n7 < n6) {
                if (n6 != n2) return null;
                n6 = n5 / n;
                if (n7 < n6) {
                    return null;
                }
            }
            if ((n7 *= n) < n5 + n8) {
                return null;
            }
            n7 -= n8;
        }
        return bl ? Integer.valueOf(n7) : Integer.valueOf(-n7);
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Long toLongOrNull(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return StringsKt.toLongOrNull(string, 10);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SinceKotlin(version="1.1")
    @Nullable
    public static final Long toLongOrNull(@NotNull String string, int n) {
        long l;
        Intrinsics.checkNotNullParameter(string, "<this>");
        CharsKt.checkRadix(n);
        int n2 = string.length();
        if (n2 == 0) {
            return null;
        }
        int n3 = 0;
        boolean bl = false;
        long l2 = 0L;
        char c = string.charAt(0);
        if (Intrinsics.compare(c, 48) < 0) {
            if (n2 == 1) {
                return null;
            }
            n3 = 1;
            if (c == '-') {
                bl = true;
                l2 = Long.MIN_VALUE;
            } else {
                if (c != '+') return null;
                bl = false;
                l2 = -9223372036854775807L;
            }
        } else {
            n3 = 0;
            bl = false;
            l2 = -9223372036854775807L;
        }
        long l3 = l = -256204778801521550L;
        long l4 = 0L;
        for (int i = n3; i < n2; ++i) {
            int n4 = CharsKt.digitOf(string.charAt(i), n);
            if (n4 < 0) {
                return null;
            }
            if (l4 < l3) {
                if (l3 != l) return null;
                l3 = l2 / (long)n;
                if (l4 < l3) {
                    return null;
                }
            }
            if ((l4 *= (long)n) < l2 + (long)n4) {
                return null;
            }
            l4 -= (long)n4;
        }
        return bl ? Long.valueOf(l4) : Long.valueOf(-l4);
    }

    @NotNull
    public static final Void numberFormatError(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "input");
        throw new NumberFormatException("Invalid number format: '" + string + '\'');
    }
}

