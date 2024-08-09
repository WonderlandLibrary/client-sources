/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.CharRange;
import kotlin.ranges.IntRange;
import kotlin.text.CharsKt;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text._OneToManyTitlecaseMappingsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\u001e\n\u0000\n\u0002\u0010\f\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0007\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u001a\f\u0010\u0004\u001a\u00020\u0002*\u00020\u0001H\u0007\u001a\u0014\u0010\u0004\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u001a\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0002*\u00020\u0001H\u0007\u00a2\u0006\u0002\u0010\u0006\u001a\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u00a2\u0006\u0002\u0010\u0007\u001a\u001c\u0010\b\u001a\u00020\t*\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\b\b\u0002\u0010\u000b\u001a\u00020\t\u001a\n\u0010\f\u001a\u00020\t*\u00020\u0001\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\u00012\u0006\u0010\n\u001a\u00020\u000eH\u0087\n\u001a\f\u0010\u000f\u001a\u00020\u000e*\u00020\u0001H\u0007\u00a8\u0006\u0010"}, d2={"digitToChar", "", "", "radix", "digitToInt", "digitToIntOrNull", "(C)Ljava/lang/Integer;", "(CI)Ljava/lang/Integer;", "equals", "", "other", "ignoreCase", "isSurrogate", "plus", "", "titlecase", "kotlin-stdlib"}, xs="kotlin/text/CharsKt")
@SourceDebugExtension(value={"SMAP\nChar.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Char.kt\nkotlin/text/CharsKt__CharKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,344:1\n1#2:345\n*E\n"})
class CharsKt__CharKt
extends CharsKt__CharJVMKt {
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final int digitToInt(char c) {
        int n;
        int n2 = n = CharsKt.digitOf(c, 10);
        boolean bl = false;
        if (n2 < 0) {
            throw new IllegalArgumentException("Char " + c + " is not a decimal digit");
        }
        return n;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final int digitToInt(char c, int n) {
        Integer n2 = CharsKt.digitToIntOrNull(c, n);
        if (n2 == null) {
            throw new IllegalArgumentException("Char " + c + " is not a digit in the given radix=" + n);
        }
        return n2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @Nullable
    public static final Integer digitToIntOrNull(char c) {
        Integer n = CharsKt.digitOf(c, 10);
        int n2 = ((Number)n).intValue();
        boolean bl = false;
        return n2 >= 0 ? n : null;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @Nullable
    public static final Integer digitToIntOrNull(char c, int n) {
        CharsKt.checkRadix(n);
        Integer n2 = CharsKt.digitOf(c, n);
        int n3 = ((Number)n2).intValue();
        boolean bl = false;
        return n3 >= 0 ? n2 : null;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final char digitToChar(int n) {
        if (new IntRange(0, 9).contains(n)) {
            return (char)(48 + n);
        }
        throw new IllegalArgumentException("Int " + n + " is not a decimal digit");
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final char digitToChar(int n, int n2) {
        if (!new IntRange(2, 36).contains(n2)) {
            throw new IllegalArgumentException("Invalid radix: " + n2 + ". Valid radix values are in range 2..36");
        }
        if (n < 0 || n >= n2) {
            throw new IllegalArgumentException("Digit " + n + " does not represent a valid digit in radix " + n2);
        }
        return n < 10 ? (char)(48 + n) : (char)((char)(65 + n) - 10);
    }

    @SinceKotlin(version="1.5")
    @NotNull
    public static final String titlecase(char c) {
        return _OneToManyTitlecaseMappingsKt.titlecaseImpl(c);
    }

    @InlineOnly
    private static final String plus(char c, String string) {
        Intrinsics.checkNotNullParameter(string, "other");
        return c + string;
    }

    public static final boolean equals(char c, char c2, boolean bl) {
        char c3;
        if (c == c2) {
            return false;
        }
        if (!bl) {
            return true;
        }
        char c4 = Character.toUpperCase(c);
        return c4 == (c3 = Character.toUpperCase(c2)) || Character.toLowerCase(c4) == Character.toLowerCase(c3);
    }

    public static boolean equals$default(char c, char c2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return CharsKt.equals(c, c2, bl);
    }

    public static final boolean isSurrogate(char c) {
        return new CharRange('\ud800', '\udfff').contains(c);
    }
}

