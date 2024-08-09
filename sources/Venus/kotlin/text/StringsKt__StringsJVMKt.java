/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringNumberConversionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000~\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0010\f\n\u0002\b\u0011\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\u0019\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a)\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a\f\u0010\u0017\u001a\u00020\u0002*\u00020\u0002H\u0007\u001a\u0014\u0010\u0017\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\u0015\u0010\u001a\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u0015\u0010\u001c\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010\u001d\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010 \u001a\u00020\u0011*\u00020\u00022\u0006\u0010!\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\f\u0010$\u001a\u00020\u0002*\u00020\u0014H\u0007\u001a \u0010$\u001a\u00020\u0002*\u00020\u00142\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\u0019\u0010&\u001a\u00020#*\u0004\u0018\u00010'2\b\u0010!\u001a\u0004\u0018\u00010'H\u0087\u0004\u001a \u0010&\u001a\u00020#*\u0004\u0018\u00010'2\b\u0010!\u001a\u0004\u0018\u00010'2\u0006\u0010\"\u001a\u00020#H\u0007\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010\n\u001a\u00020\tH\u0087\b\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010(\u001a\u00020'H\u0087\b\u001a\f\u0010)\u001a\u00020\u0002*\u00020\u0002H\u0007\u001a\u0014\u0010)\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\f\u0010*\u001a\u00020\u0002*\u00020\rH\u0007\u001a*\u0010*\u001a\u00020\u0002*\u00020\r2\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\f\u0010,\u001a\u00020\r*\u00020\u0002H\u0007\u001a*\u0010,\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\u001c\u0010-\u001a\u00020#*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a \u0010/\u001a\u00020#*\u0004\u0018\u00010\u00022\b\u0010!\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a4\u00100\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00104\u001a*\u00100\u001a\u00020\u0002*\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00105\u001a<\u00100\u001a\u00020\u0002*\u00020\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00106\u001a2\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00107\u001a\r\u00108\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\n\u00109\u001a\u00020#*\u00020'\u001a\r\u0010:\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010:\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\u001d\u0010;\u001a\u00020\u0011*\u00020\u00022\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010;\u001a\u00020\u0011*\u00020\u00022\u0006\u0010?\u001a\u00020\u00022\u0006\u0010>\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010@\u001a\u00020\u0011*\u00020\u00022\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010@\u001a\u00020\u0011*\u00020\u00022\u0006\u0010?\u001a\u00020\u00022\u0006\u0010>\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010A\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010B\u001a\u00020\u0011H\u0087\b\u001a4\u0010C\u001a\u00020#*\u00020'2\u0006\u0010D\u001a\u00020\u00112\u0006\u0010!\u001a\u00020'2\u0006\u0010E\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a4\u0010C\u001a\u00020#*\u00020\u00022\u0006\u0010D\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00022\u0006\u0010E\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0012\u0010F\u001a\u00020\u0002*\u00020'2\u0006\u0010G\u001a\u00020\u0011\u001a$\u0010H\u001a\u00020\u0002*\u00020\u00022\u0006\u0010I\u001a\u00020=2\u0006\u0010J\u001a\u00020=2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010H\u001a\u00020\u0002*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\u0006\u0010L\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010M\u001a\u00020\u0002*\u00020\u00022\u0006\u0010I\u001a\u00020=2\u0006\u0010J\u001a\u00020=2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010M\u001a\u00020\u0002*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\u0006\u0010L\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\"\u0010N\u001a\b\u0012\u0004\u0012\u00020\u00020O*\u00020'2\u0006\u0010P\u001a\u00020Q2\b\b\u0002\u0010R\u001a\u00020\u0011\u001a\u001c\u0010S\u001a\u00020#*\u00020\u00022\u0006\u0010T\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010S\u001a\u00020#*\u00020\u00022\u0006\u0010T\u001a\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0015\u0010U\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010U\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u0017\u0010V\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\r\u0010W\u001a\u00020\u0014*\u00020\u0002H\u0087\b\u001a3\u0010W\u001a\u00020\u0014*\u00020\u00022\u0006\u0010X\u001a\u00020\u00142\b\b\u0002\u0010Y\u001a\u00020\u00112\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a \u0010W\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\r\u0010Z\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010Z\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\u0017\u0010[\u001a\u00020Q*\u00020\u00022\b\b\u0002\u0010\\\u001a\u00020\u0011H\u0087\b\u001a\r\u0010]\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010]\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\r\u0010^\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010^\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\"%\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003*\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006_"}, d2={"CASE_INSENSITIVE_ORDER", "Ljava/util/Comparator;", "", "Lkotlin/Comparator;", "Lkotlin/String$Companion;", "getCASE_INSENSITIVE_ORDER", "(Lkotlin/jvm/internal/StringCompanionObject;)Ljava/util/Comparator;", "String", "stringBuffer", "Ljava/lang/StringBuffer;", "stringBuilder", "Ljava/lang/StringBuilder;", "bytes", "", "charset", "Ljava/nio/charset/Charset;", "offset", "", "length", "chars", "", "codePoints", "", "capitalize", "locale", "Ljava/util/Locale;", "codePointAt", "index", "codePointBefore", "codePointCount", "beginIndex", "endIndex", "compareTo", "other", "ignoreCase", "", "concatToString", "startIndex", "contentEquals", "", "charSequence", "decapitalize", "decodeToString", "throwOnInvalidSequence", "encodeToByteArray", "endsWith", "suffix", "equals", "format", "args", "", "", "(Ljava/lang/String;Ljava/util/Locale;[Ljava/lang/Object;)Ljava/lang/String;", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "intern", "isBlank", "lowercase", "nativeIndexOf", "ch", "", "fromIndex", "str", "nativeLastIndexOf", "offsetByCodePoints", "codePointOffset", "regionMatches", "thisOffset", "otherOffset", "repeat", "n", "replace", "oldChar", "newChar", "oldValue", "newValue", "replaceFirst", "split", "", "regex", "Ljava/util/regex/Pattern;", "limit", "startsWith", "prefix", "substring", "toByteArray", "toCharArray", "destination", "destinationOffset", "toLowerCase", "toPattern", "flags", "toUpperCase", "uppercase", "kotlin-stdlib"}, xs="kotlin/text/StringsKt")
@SourceDebugExtension(value={"SMAP\nStringsJVM.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringsJVM.kt\nkotlin/text/StringsKt__StringsJVMKt\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,805:1\n1174#2,2:806\n1#3:808\n1726#4,3:809\n*S KotlinDebug\n*F\n+ 1 StringsJVM.kt\nkotlin/text/StringsKt__StringsJVMKt\n*L\n73#1:806,2\n600#1:809,3\n*E\n"})
class StringsKt__StringsJVMKt
extends StringsKt__StringNumberConversionsKt {
    @InlineOnly
    private static final int nativeIndexOf(String string, char c, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return string.indexOf(c, n);
    }

    @InlineOnly
    private static final int nativeIndexOf(String string, String string2, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "str");
        return string.indexOf(string2, n);
    }

    @InlineOnly
    private static final int nativeLastIndexOf(String string, char c, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return string.lastIndexOf(c, n);
    }

    @InlineOnly
    private static final int nativeLastIndexOf(String string, String string2, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "str");
        return string.lastIndexOf(string2, n);
    }

    public static final boolean equals(@Nullable String string, @Nullable String string2, boolean bl) {
        if (string == null) {
            return string2 == null;
        }
        return !bl ? string.equals(string2) : string.equalsIgnoreCase(string2);
    }

    public static boolean equals$default(String string, String string2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.equals(string, string2, bl);
    }

    @NotNull
    public static final String replace(@NotNull String string, char c, char c2, boolean bl) {
        StringBuilder stringBuilder;
        Intrinsics.checkNotNullParameter(string, "<this>");
        if (!bl) {
            String string2 = string.replace(c, c2);
            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String\u2026replace(oldChar, newChar)");
            return string2;
        }
        int n = string.length();
        StringBuilder stringBuilder2 = stringBuilder = new StringBuilder(n);
        boolean bl2 = false;
        CharSequence charSequence = string;
        boolean bl3 = false;
        for (int i = 0; i < charSequence.length(); ++i) {
            char c3;
            char c4 = c3 = charSequence.charAt(i);
            boolean bl4 = false;
            stringBuilder2.append(CharsKt.equals(c4, c, bl) ? c2 : c4);
        }
        String string3 = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string3, "StringBuilder(capacity).\u2026builderAction).toString()");
        return string3;
    }

    public static String replace$default(String string, char c, char c2, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        return StringsKt.replace(string, c, c2, bl);
    }

    @NotNull
    public static final String replace(@NotNull String string, @NotNull String string2, @NotNull String string3, boolean bl) {
        String string4;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "oldValue");
        Intrinsics.checkNotNullParameter(string3, "newValue");
        String string5 = string4 = string;
        boolean bl2 = false;
        int n = StringsKt.indexOf((CharSequence)string5, string2, 0, bl);
        if (n < 0) {
            return string5;
        }
        int n2 = string2.length();
        int n3 = RangesKt.coerceAtLeast(n2, 1);
        int n4 = string5.length() - n2 + string3.length();
        if (n4 < 0) {
            throw new OutOfMemoryError();
        }
        StringBuilder stringBuilder = new StringBuilder(n4);
        int n5 = 0;
        do {
            stringBuilder.append(string5, n5, n).append(string3);
            n5 = n + n2;
        } while (n < string5.length() && (n = StringsKt.indexOf((CharSequence)string5, string2, n + n3, bl)) > 0);
        String string6 = stringBuilder.append(string5, n5, string5.length()).toString();
        Intrinsics.checkNotNullExpressionValue(string6, "stringBuilder.append(this, i, length).toString()");
        return string6;
    }

    public static String replace$default(String string, String string2, String string3, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        return StringsKt.replace(string, string2, string3, bl);
    }

    @NotNull
    public static final String replaceFirst(@NotNull String string, char c, char c2, boolean bl) {
        String string2;
        Intrinsics.checkNotNullParameter(string, "<this>");
        int n = StringsKt.indexOf$default((CharSequence)string, c, 0, bl, 2, null);
        if (n < 0) {
            string2 = string;
        } else {
            String string3 = string;
            int n2 = n + 1;
            CharSequence charSequence = String.valueOf(c2);
            string2 = ((Object)StringsKt.replaceRange((CharSequence)string3, n, n2, charSequence)).toString();
        }
        return string2;
    }

    public static String replaceFirst$default(String string, char c, char c2, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        return StringsKt.replaceFirst(string, c, c2, bl);
    }

    @NotNull
    public static final String replaceFirst(@NotNull String string, @NotNull String string2, @NotNull String string3, boolean bl) {
        String string4;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "oldValue");
        Intrinsics.checkNotNullParameter(string3, "newValue");
        int n = StringsKt.indexOf$default((CharSequence)string, string2, 0, bl, 2, null);
        if (n < 0) {
            string4 = string;
        } else {
            String string5 = string;
            int n2 = n + string2.length();
            string4 = ((Object)StringsKt.replaceRange((CharSequence)string5, n, n2, (CharSequence)string3)).toString();
        }
        return string4;
    }

    public static String replaceFirst$default(String string, String string2, String string3, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        return StringsKt.replaceFirst(string, string2, string3, bl);
    }

    @Deprecated(message="Use uppercase() instead.", replaceWith=@ReplaceWith(expression="uppercase(Locale.getDefault())", imports={"java.util.Locale"}))
    @DeprecatedSinceKotlin(warningSince="1.5")
    @InlineOnly
    private static final String toUpperCase(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        String string2 = string.toUpperCase();
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toUpperCase()");
        return string2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final String uppercase(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        String string2 = string.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        return string2;
    }

    @Deprecated(message="Use lowercase() instead.", replaceWith=@ReplaceWith(expression="lowercase(Locale.getDefault())", imports={"java.util.Locale"}))
    @DeprecatedSinceKotlin(warningSince="1.5")
    @InlineOnly
    private static final String toLowerCase(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        String string2 = string.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase()");
        return string2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final String lowercase(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        String string2 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return string2;
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @NotNull
    public static final String concatToString(@NotNull char[] cArray) {
        Intrinsics.checkNotNullParameter(cArray, "<this>");
        return new String(cArray);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @NotNull
    public static final String concatToString(@NotNull char[] cArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(cArray, "<this>");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n, n2, cArray.length);
        return new String(cArray, n, n2 - n);
    }

    public static String concatToString$default(char[] cArray, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = cArray.length;
        }
        return StringsKt.concatToString(cArray, n, n2);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @NotNull
    public static final char[] toCharArray(@NotNull String string, int n, int n2) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n, n2, string.length());
        String string2 = string;
        char[] cArray = new char[n2 - n];
        int n3 = 0;
        string2.getChars(n, n2, cArray, n3);
        return cArray;
    }

    public static char[] toCharArray$default(String string, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = string.length();
        }
        return StringsKt.toCharArray(string, n, n2);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @NotNull
    public static final String decodeToString(@NotNull byte[] byArray) {
        Intrinsics.checkNotNullParameter(byArray, "<this>");
        return new String(byArray, Charsets.UTF_8);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @NotNull
    public static final String decodeToString(@NotNull byte[] byArray, int n, int n2, boolean bl) {
        Intrinsics.checkNotNullParameter(byArray, "<this>");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n, n2, byArray.length);
        if (!bl) {
            return new String(byArray, n, n2 - n, Charsets.UTF_8);
        }
        CharsetDecoder charsetDecoder = Charsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        String string = charsetDecoder.decode(ByteBuffer.wrap(byArray, n, n2 - n)).toString();
        Intrinsics.checkNotNullExpressionValue(string, "decoder.decode(ByteBuffe\u2026- startIndex)).toString()");
        return string;
    }

    public static String decodeToString$default(byte[] byArray, int n, int n2, boolean bl, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = byArray.length;
        }
        if ((n3 & 4) != 0) {
            bl = false;
        }
        return StringsKt.decodeToString(byArray, n, n2, bl);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @NotNull
    public static final byte[] encodeToByteArray(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        String string2 = string;
        byte[] byArray = string2.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
        return byArray;
    }

    /*
     * Enabled aggressive block sorting
     */
    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @NotNull
    public static final byte[] encodeToByteArray(@NotNull String string, int n, int n2, boolean bl) {
        byte[] byArray;
        byte[] byArray2;
        Intrinsics.checkNotNullParameter(string, "<this>");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n, n2, string.length());
        if (!bl) {
            String string2 = string.substring(n, n2);
            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            String string3 = string2;
            Charset charset = Charsets.UTF_8;
            Intrinsics.checkNotNull(string3, "null cannot be cast to non-null type java.lang.String");
            byte[] byArray3 = string3.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byArray3, "this as java.lang.String).getBytes(charset)");
            return byArray3;
        }
        CharsetEncoder charsetEncoder = Charsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        ByteBuffer byteBuffer = charsetEncoder.encode(CharBuffer.wrap(string, n, n2));
        if (byteBuffer.hasArray() && byteBuffer.arrayOffset() == 0) {
            int n3 = byteBuffer.remaining();
            byte[] byArray4 = byteBuffer.array();
            Intrinsics.checkNotNull(byArray4);
            if (n3 == byArray4.length) {
                byte[] byArray5 = byteBuffer.array();
                byArray2 = byArray5;
                Intrinsics.checkNotNullExpressionValue(byArray5, "{\n        byteBuffer.array()\n    }");
                return byArray2;
            }
        }
        byte[] byArray6 = byArray = new byte[byteBuffer.remaining()];
        boolean bl2 = false;
        byteBuffer.get(byArray6);
        byArray2 = byArray;
        return byArray2;
    }

    public static byte[] encodeToByteArray$default(String string, int n, int n2, boolean bl, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = string.length();
        }
        if ((n3 & 4) != 0) {
            bl = false;
        }
        return StringsKt.encodeToByteArray(string, n, n2, bl);
    }

    @InlineOnly
    private static final char[] toCharArray(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        char[] cArray = string.toCharArray();
        Intrinsics.checkNotNullExpressionValue(cArray, "this as java.lang.String).toCharArray()");
        return cArray;
    }

    @InlineOnly
    private static final char[] toCharArray(String string, char[] cArray, int n, int n2, int n3) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "destination");
        string.getChars(n2, n3, cArray, n);
        return cArray;
    }

    static char[] toCharArray$default(String string, char[] cArray, int n, int n2, int n3, int n4, Object object) {
        if ((n4 & 2) != 0) {
            n = 0;
        }
        if ((n4 & 4) != 0) {
            n2 = 0;
        }
        if ((n4 & 8) != 0) {
            n3 = string.length();
        }
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "destination");
        string.getChars(n2, n3, cArray, n);
        return cArray;
    }

    @InlineOnly
    private static final String format(String string, Object ... objectArray) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(objectArray, "args");
        String string2 = String.format(string, Arrays.copyOf(objectArray, objectArray.length));
        Intrinsics.checkNotNullExpressionValue(string2, "format(this, *args)");
        return string2;
    }

    @InlineOnly
    private static final String format(StringCompanionObject stringCompanionObject, String string, Object ... objectArray) {
        Intrinsics.checkNotNullParameter(stringCompanionObject, "<this>");
        Intrinsics.checkNotNullParameter(string, "format");
        Intrinsics.checkNotNullParameter(objectArray, "args");
        String string2 = String.format(string, Arrays.copyOf(objectArray, objectArray.length));
        Intrinsics.checkNotNullExpressionValue(string2, "format(format, *args)");
        return string2;
    }

    @SinceKotlin(version="1.4")
    @InlineOnly
    private static final String format(String string, Locale locale, Object ... objectArray) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(objectArray, "args");
        String string2 = String.format(locale, string, Arrays.copyOf(objectArray, objectArray.length));
        Intrinsics.checkNotNullExpressionValue(string2, "format(locale, this, *args)");
        return string2;
    }

    @SinceKotlin(version="1.4")
    @InlineOnly
    private static final String format(StringCompanionObject stringCompanionObject, Locale locale, String string, Object ... objectArray) {
        Intrinsics.checkNotNullParameter(stringCompanionObject, "<this>");
        Intrinsics.checkNotNullParameter(string, "format");
        Intrinsics.checkNotNullParameter(objectArray, "args");
        String string2 = String.format(locale, string, Arrays.copyOf(objectArray, objectArray.length));
        Intrinsics.checkNotNullExpressionValue(string2, "format(locale, format, *args)");
        return string2;
    }

    @NotNull
    public static final List<String> split(@NotNull CharSequence charSequence, @NotNull Pattern pattern, int n) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(pattern, "regex");
        StringsKt.requireNonNegativeLimit(n);
        String[] stringArray = pattern.split(charSequence, n == 0 ? -1 : n);
        Intrinsics.checkNotNullExpressionValue(stringArray, "regex.split(this, if (limit == 0) -1 else limit)");
        return ArraysKt.asList((Object[])stringArray);
    }

    public static List split$default(CharSequence charSequence, Pattern pattern, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        return StringsKt.split(charSequence, pattern, n);
    }

    @InlineOnly
    private static final String substring(String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        String string2 = string.substring(n);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).substring(startIndex)");
        return string2;
    }

    @InlineOnly
    private static final String substring(String string, int n, int n2) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        String string2 = string.substring(n, n2);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        return string2;
    }

    public static final boolean startsWith(@NotNull String string, @NotNull String string2, boolean bl) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "prefix");
        if (!bl) {
            return string.startsWith(string2);
        }
        return StringsKt.regionMatches(string, 0, string2, 0, string2.length(), bl);
    }

    public static boolean startsWith$default(String string, String string2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.startsWith(string, string2, bl);
    }

    public static final boolean startsWith(@NotNull String string, @NotNull String string2, int n, boolean bl) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "prefix");
        if (!bl) {
            return string.startsWith(string2, n);
        }
        return StringsKt.regionMatches(string, n, string2, 0, string2.length(), bl);
    }

    public static boolean startsWith$default(String string, String string2, int n, boolean bl, int n2, Object object) {
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return StringsKt.startsWith(string, string2, n, bl);
    }

    public static final boolean endsWith(@NotNull String string, @NotNull String string2, boolean bl) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "suffix");
        if (!bl) {
            return string.endsWith(string2);
        }
        return StringsKt.regionMatches(string, string.length() - string2.length(), string2, 0, string2.length(), true);
    }

    public static boolean endsWith$default(String string, String string2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.endsWith(string, string2, bl);
    }

    @InlineOnly
    private static final String String(byte[] byArray, int n, int n2, Charset charset) {
        Intrinsics.checkNotNullParameter(byArray, "bytes");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new String(byArray, n, n2, charset);
    }

    @InlineOnly
    private static final String String(byte[] byArray, Charset charset) {
        Intrinsics.checkNotNullParameter(byArray, "bytes");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new String(byArray, charset);
    }

    @InlineOnly
    private static final String String(byte[] byArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(byArray, "bytes");
        return new String(byArray, n, n2, Charsets.UTF_8);
    }

    @InlineOnly
    private static final String String(byte[] byArray) {
        Intrinsics.checkNotNullParameter(byArray, "bytes");
        return new String(byArray, Charsets.UTF_8);
    }

    @InlineOnly
    private static final String String(char[] cArray) {
        Intrinsics.checkNotNullParameter(cArray, "chars");
        return new String(cArray);
    }

    @InlineOnly
    private static final String String(char[] cArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(cArray, "chars");
        return new String(cArray, n, n2);
    }

    @InlineOnly
    private static final String String(int[] nArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(nArray, "codePoints");
        return new String(nArray, n, n2);
    }

    @InlineOnly
    private static final String String(StringBuffer stringBuffer) {
        Intrinsics.checkNotNullParameter(stringBuffer, "stringBuffer");
        return new String(stringBuffer);
    }

    @InlineOnly
    private static final String String(StringBuilder stringBuilder) {
        Intrinsics.checkNotNullParameter(stringBuilder, "stringBuilder");
        return new String(stringBuilder);
    }

    @InlineOnly
    private static final int codePointAt(String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return string.codePointAt(n);
    }

    @InlineOnly
    private static final int codePointBefore(String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return string.codePointBefore(n);
    }

    @InlineOnly
    private static final int codePointCount(String string, int n, int n2) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return string.codePointCount(n, n2);
    }

    public static final int compareTo(@NotNull String string, @NotNull String string2, boolean bl) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "other");
        if (bl) {
            return string.compareToIgnoreCase(string2);
        }
        return string.compareTo(string2);
    }

    public static int compareTo$default(String string, String string2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return StringsKt.compareTo(string, string2, bl);
    }

    @InlineOnly
    private static final boolean contentEquals(String string, CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "charSequence");
        return string.contentEquals(charSequence);
    }

    @InlineOnly
    private static final boolean contentEquals(String string, StringBuffer stringBuffer) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(stringBuffer, "stringBuilder");
        return string.contentEquals(stringBuffer);
    }

    @SinceKotlin(version="1.5")
    public static final boolean contentEquals(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2) {
        return charSequence instanceof String && charSequence2 != null ? ((String)charSequence).contentEquals(charSequence2) : StringsKt.contentEqualsImpl(charSequence, charSequence2);
    }

    @SinceKotlin(version="1.5")
    public static final boolean contentEquals(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2, boolean bl) {
        return bl ? StringsKt.contentEqualsIgnoreCaseImpl(charSequence, charSequence2) : StringsKt.contentEquals(charSequence, charSequence2);
    }

    @InlineOnly
    private static final String intern(String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        String string2 = string.intern();
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).intern()");
        return string2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final boolean isBlank(@NotNull CharSequence charSequence) {
        int n;
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        if (charSequence.length() == 0) return true;
        Iterable iterable = StringsKt.getIndices(charSequence);
        boolean bl = false;
        if (iterable instanceof Collection && ((Collection)iterable).isEmpty()) {
            return true;
        }
        Iterator iterator2 = iterable.iterator();
        do {
            int n2;
            if (!iterator2.hasNext()) return true;
            n = n2 = ((IntIterator)iterator2).nextInt();
            boolean bl2 = false;
        } while (CharsKt.isWhitespace(charSequence.charAt(n)));
        return false;
    }

    @InlineOnly
    private static final int offsetByCodePoints(String string, int n, int n2) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        return string.offsetByCodePoints(n, n2);
    }

    public static final boolean regionMatches(@NotNull CharSequence charSequence, int n, @NotNull CharSequence charSequence2, int n2, int n3, boolean bl) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(charSequence2, "other");
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return StringsKt.regionMatches((String)charSequence, n, (String)charSequence2, n2, n3, bl);
        }
        return StringsKt.regionMatchesImpl(charSequence, n, charSequence2, n2, n3, bl);
    }

    public static boolean regionMatches$default(CharSequence charSequence, int n, CharSequence charSequence2, int n2, int n3, boolean bl, int n4, Object object) {
        if ((n4 & 0x10) != 0) {
            bl = false;
        }
        return StringsKt.regionMatches(charSequence, n, charSequence2, n2, n3, bl);
    }

    public static final boolean regionMatches(@NotNull String string, int n, @NotNull String string2, int n2, int n3, boolean bl) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(string2, "other");
        return !bl ? string.regionMatches(n, string2, n2, n3) : string.regionMatches(bl, n, string2, n2, n3);
    }

    public static boolean regionMatches$default(String string, int n, String string2, int n2, int n3, boolean bl, int n4, Object object) {
        if ((n4 & 0x10) != 0) {
            bl = false;
        }
        return StringsKt.regionMatches(string, n, string2, n2, n3, bl);
    }

    @Deprecated(message="Use lowercase() instead.", replaceWith=@ReplaceWith(expression="lowercase(locale)", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5")
    @InlineOnly
    private static final String toLowerCase(String string, Locale locale) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        String string2 = string.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(locale)");
        return string2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final String lowercase(String string, Locale locale) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        String string2 = string.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(locale)");
        return string2;
    }

    @Deprecated(message="Use uppercase() instead.", replaceWith=@ReplaceWith(expression="uppercase(locale)", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5")
    @InlineOnly
    private static final String toUpperCase(String string, Locale locale) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        String string2 = string.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toUpperCase(locale)");
        return string2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final String uppercase(String string, Locale locale) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        String string2 = string.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toUpperCase(locale)");
        return string2;
    }

    @InlineOnly
    private static final byte[] toByteArray(String string, Charset charset) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] byArray = string.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
        return byArray;
    }

    static byte[] toByteArray$default(String string, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] byArray = string.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
        return byArray;
    }

    @InlineOnly
    private static final Pattern toPattern(String string, int n) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Pattern pattern = Pattern.compile(string, n);
        Intrinsics.checkNotNullExpressionValue(pattern, "compile(this, flags)");
        return pattern;
    }

    static Pattern toPattern$default(String string, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        Intrinsics.checkNotNullParameter(string, "<this>");
        Pattern pattern = Pattern.compile(string, n);
        Intrinsics.checkNotNullExpressionValue(pattern, "compile(this, flags)");
        return pattern;
    }

    @Deprecated(message="Use replaceFirstChar instead.", replaceWith=@ReplaceWith(expression="replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }", imports={"java.util.Locale"}))
    @DeprecatedSinceKotlin(warningSince="1.5")
    @NotNull
    public static final String capitalize(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
        return StringsKt.capitalize(string, locale);
    }

    @Deprecated(message="Use replaceFirstChar instead.", replaceWith=@ReplaceWith(expression="replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5")
    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @LowPriorityInOverloadResolution
    @NotNull
    public static final String capitalize(@NotNull String string, @NotNull Locale locale) {
        char c;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        if (((CharSequence)string).length() > 0 && Character.isLowerCase(c = string.charAt(0))) {
            int n;
            String string2;
            StringBuilder stringBuilder;
            StringBuilder stringBuilder2 = stringBuilder = new StringBuilder();
            boolean bl = false;
            char c2 = Character.toTitleCase(c);
            if (c2 != Character.toUpperCase(c)) {
                stringBuilder2.append(c2);
            } else {
                string2 = string;
                n = 0;
                int n2 = 1;
                String string3 = string2.substring(n, n2);
                Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                string2 = string3;
                Intrinsics.checkNotNull(string2, "null cannot be cast to non-null type java.lang.String");
                String string4 = string2.toUpperCase(locale);
                Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String).toUpperCase(locale)");
                stringBuilder2.append(string4);
            }
            string2 = string;
            n = 1;
            String string5 = string2.substring(n);
            Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String).substring(startIndex)");
            stringBuilder2.append(string5);
            String string6 = stringBuilder.toString();
            Intrinsics.checkNotNullExpressionValue(string6, "StringBuilder().apply(builderAction).toString()");
            return string6;
        }
        return string;
    }

    @Deprecated(message="Use replaceFirstChar instead.", replaceWith=@ReplaceWith(expression="replaceFirstChar { it.lowercase(Locale.getDefault()) }", imports={"java.util.Locale"}))
    @DeprecatedSinceKotlin(warningSince="1.5")
    @NotNull
    public static final String decapitalize(@NotNull String string) {
        String string2;
        Intrinsics.checkNotNullParameter(string, "<this>");
        if (((CharSequence)string).length() > 0 && !Character.isLowerCase(string.charAt(0))) {
            StringBuilder stringBuilder = new StringBuilder();
            String string3 = string;
            int n = 0;
            int n2 = 1;
            String string4 = string3.substring(n, n2);
            Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            string3 = string4;
            Intrinsics.checkNotNull(string3, "null cannot be cast to non-null type java.lang.String");
            String string5 = string3.toLowerCase();
            Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String).toLowerCase()");
            StringBuilder stringBuilder2 = stringBuilder.append(string5);
            string3 = string;
            n = 1;
            String string6 = string3.substring(n);
            Intrinsics.checkNotNullExpressionValue(string6, "this as java.lang.String).substring(startIndex)");
            string2 = stringBuilder2.append(string6).toString();
        } else {
            string2 = string;
        }
        return string2;
    }

    @Deprecated(message="Use replaceFirstChar instead.", replaceWith=@ReplaceWith(expression="replaceFirstChar { it.lowercase(locale) }", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.5")
    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @LowPriorityInOverloadResolution
    @NotNull
    public static final String decapitalize(@NotNull String string, @NotNull Locale locale) {
        String string2;
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        if (((CharSequence)string).length() > 0 && !Character.isLowerCase(string.charAt(0))) {
            StringBuilder stringBuilder = new StringBuilder();
            String string3 = string;
            int n = 0;
            int n2 = 1;
            String string4 = string3.substring(n, n2);
            Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            string3 = string4;
            Intrinsics.checkNotNull(string3, "null cannot be cast to non-null type java.lang.String");
            String string5 = string3.toLowerCase(locale);
            Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String).toLowerCase(locale)");
            StringBuilder stringBuilder2 = stringBuilder.append(string5);
            string3 = string;
            n = 1;
            String string6 = string3.substring(n);
            Intrinsics.checkNotNullExpressionValue(string6, "this as java.lang.String).substring(startIndex)");
            string2 = stringBuilder2.append(string6).toString();
        } else {
            string2 = string;
        }
        return string2;
    }

    @NotNull
    public static final String repeat(@NotNull CharSequence charSequence, int n) {
        String string;
        boolean bl;
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            boolean bl3 = false;
            String string2 = "Count 'n' must be non-negative, but was " + n + '.';
            throw new IllegalArgumentException(string2.toString());
        }
        block0 : switch (n) {
            case 0: {
                string = "";
                break;
            }
            case 1: {
                string = ((Object)charSequence).toString();
                break;
            }
            default: {
                switch (charSequence.length()) {
                    case 0: {
                        string = "";
                        break block0;
                    }
                    case 1: {
                        char c;
                        char c2 = c = charSequence.charAt(0);
                        boolean bl4 = false;
                        int n2 = 0;
                        char[] cArray = new char[n];
                        while (n2 < n) {
                            int n3 = n2++;
                            cArray[n3] = c2;
                        }
                        char[] cArray2 = cArray;
                        string = new String(cArray2);
                        break block0;
                    }
                }
                StringBuilder stringBuilder = new StringBuilder(n * charSequence.length());
                IntIterator intIterator = new IntRange(1, n).iterator();
                while (intIterator.hasNext()) {
                    int n4 = intIterator.nextInt();
                    stringBuilder.append(charSequence);
                }
                String string3 = stringBuilder.toString();
                Intrinsics.checkNotNullExpressionValue(string3, "{\n                    va\u2026tring()\n                }");
                string = string3;
            }
        }
        return string;
    }

    @NotNull
    public static final Comparator<String> getCASE_INSENSITIVE_ORDER(@NotNull StringCompanionObject stringCompanionObject) {
        Intrinsics.checkNotNullParameter(stringCompanionObject, "<this>");
        Comparator comparator = String.CASE_INSENSITIVE_ORDER;
        Intrinsics.checkNotNullExpressionValue(comparator, "CASE_INSENSITIVE_ORDER");
        return comparator;
    }
}

