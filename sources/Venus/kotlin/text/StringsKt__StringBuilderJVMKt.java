/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__RegexExtensionsKt;
import kotlin.text.SystemProperties;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\\\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\u001a\u001f\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0006H\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0007H\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\bH\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\tH\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\nH\u0087\b\u001a%\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u000e\u0010\u0003\u001a\n\u0018\u00010\u0001j\u0004\u0018\u0001`\u0002H\u0087\b\u001a-\u0010\u000b\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0087\b\u001a-\u0010\u000b\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0087\b\u001a\u0014\u0010\u0010\u001a\u00060\u0011j\u0002`\u0012*\u00060\u0011j\u0002`\u0012H\u0007\u001a\u001d\u0010\u0010\u001a\u00060\u0011j\u0002`\u0012*\u00060\u0011j\u0002`\u00122\u0006\u0010\u0003\u001a\u00020\u0013H\u0087\b\u001a\u001f\u0010\u0010\u001a\u00060\u0011j\u0002`\u0012*\u00060\u0011j\u0002`\u00122\b\u0010\u0003\u001a\u0004\u0018\u00010\u000fH\u0087\b\u001a\u0014\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u0002H\u0007\u001a\u001f\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0087\b\u001a\u001f\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0014H\u0087\b\u001a\u001d\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0015H\u0087\b\u001a\u001d\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u001d\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0013H\u0087\b\u001a\u001d\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\fH\u0087\b\u001a\u001f\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u000fH\u0087\b\u001a\u001d\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0006H\u0087\b\u001a\u001d\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0007H\u0087\b\u001a\u001d\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\bH\u0087\b\u001a\u001d\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\tH\u0087\b\u001a\u001d\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\nH\u0087\b\u001a\u001f\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0016H\u0087\b\u001a%\u0010\u0010\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u000e\u0010\u0003\u001a\n\u0018\u00010\u0001j\u0004\u0018\u0001`\u0002H\u0087\b\u001a\u0014\u0010\u0017\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u0002H\u0007\u001a\u001d\u0010\u0018\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0019\u001a\u00020\bH\u0087\b\u001a%\u0010\u001a\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0087\b\u001a5\u0010\u001b\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0087\b\u001a5\u0010\u001b\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0087\b\u001a!\u0010\u001c\u001a\u00020\u001d*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0013H\u0087\n\u001a-\u0010\u001e\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0016H\u0087\b\u001a7\u0010\u001f\u001a\u00020\u001d*\u00060\u0001j\u0002`\u00022\u0006\u0010 \u001a\u00020\f2\b\b\u0002\u0010!\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\bH\u0087\b\u00a8\u0006\""}, d2={"appendLine", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "value", "Ljava/lang/StringBuffer;", "", "", "", "", "", "", "appendRange", "", "startIndex", "endIndex", "", "appendln", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "", "", "", "", "clear", "deleteAt", "index", "deleteRange", "insertRange", "set", "", "setRange", "toCharArray", "destination", "destinationOffset", "kotlin-stdlib"}, xs="kotlin/text/StringsKt")
@SourceDebugExtension(value={"SMAP\nStringBuilderJVM.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringBuilderJVM.kt\nkotlin/text/StringsKt__StringBuilderJVMKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,381:1\n1#2:382\n*E\n"})
class StringsKt__StringBuilderJVMKt
extends StringsKt__RegexExtensionsKt {
    @SinceKotlin(version="1.3")
    @NotNull
    public static final StringBuilder clear(@NotNull StringBuilder stringBuilder) {
        StringBuilder stringBuilder2;
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder3 = stringBuilder2 = stringBuilder;
        boolean bl = false;
        stringBuilder3.setLength(0);
        return stringBuilder2;
    }

    @InlineOnly
    private static final void set(StringBuilder stringBuilder, int n, char c) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        stringBuilder.setCharAt(n, c);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final StringBuilder setRange(StringBuilder stringBuilder, int n, int n2, String string) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        Intrinsics.checkNotNullParameter(string, "value");
        StringBuilder stringBuilder2 = stringBuilder.replace(n, n2, string);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "this.replace(startIndex, endIndex, value)");
        return stringBuilder2;
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final StringBuilder deleteAt(StringBuilder stringBuilder, int n) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.deleteCharAt(n);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "this.deleteCharAt(index)");
        return stringBuilder2;
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final StringBuilder deleteRange(StringBuilder stringBuilder, int n, int n2) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.delete(n, n2);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "this.delete(startIndex, endIndex)");
        return stringBuilder2;
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final void toCharArray(StringBuilder stringBuilder, char[] cArray, int n, int n2, int n3) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "destination");
        stringBuilder.getChars(n2, n3, cArray, n);
    }

    static void toCharArray$default(StringBuilder stringBuilder, char[] cArray, int n, int n2, int n3, int n4, Object object) {
        if ((n4 & 2) != 0) {
            n = 0;
        }
        if ((n4 & 4) != 0) {
            n2 = 0;
        }
        if ((n4 & 8) != 0) {
            n3 = stringBuilder.length();
        }
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "destination");
        stringBuilder.getChars(n2, n3, cArray, n);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final StringBuilder appendRange(StringBuilder stringBuilder, char[] cArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "value");
        StringBuilder stringBuilder2 = stringBuilder.append(cArray, n, n2 - n);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "this.append(value, start\u2026x, endIndex - startIndex)");
        return stringBuilder2;
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final StringBuilder appendRange(StringBuilder stringBuilder, CharSequence charSequence, int n, int n2) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "value");
        StringBuilder stringBuilder2 = stringBuilder.append(charSequence, n, n2);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "this.append(value, startIndex, endIndex)");
        return stringBuilder2;
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final StringBuilder insertRange(StringBuilder stringBuilder, int n, char[] cArray, int n2, int n3) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "value");
        StringBuilder stringBuilder2 = stringBuilder.insert(n, cArray, n2, n3 - n2);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "this.insert(index, value\u2026x, endIndex - startIndex)");
        return stringBuilder2;
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    @InlineOnly
    private static final StringBuilder insertRange(StringBuilder stringBuilder, int n, CharSequence charSequence, int n2, int n3) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "value");
        StringBuilder stringBuilder2 = stringBuilder.insert(n, charSequence, n2, n3);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "this.insert(index, value, startIndex, endIndex)");
        return stringBuilder2;
    }

    @SinceKotlin(version="1.4")
    @InlineOnly
    private static final StringBuilder appendLine(StringBuilder stringBuilder, StringBuffer stringBuffer) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(stringBuffer);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        StringBuilder stringBuilder3 = stringBuilder2.append('\n');
        Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append('\\n')");
        return stringBuilder3;
    }

    @SinceKotlin(version="1.4")
    @InlineOnly
    private static final StringBuilder appendLine(StringBuilder stringBuilder, StringBuilder stringBuilder2) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder3 = stringBuilder.append((CharSequence)stringBuilder2);
        Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append(value)");
        StringBuilder stringBuilder4 = stringBuilder3.append('\n');
        Intrinsics.checkNotNullExpressionValue(stringBuilder4, "append('\\n')");
        return stringBuilder4;
    }

    @SinceKotlin(version="1.4")
    @InlineOnly
    private static final StringBuilder appendLine(StringBuilder stringBuilder, int n) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(n);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        StringBuilder stringBuilder3 = stringBuilder2.append('\n');
        Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append('\\n')");
        return stringBuilder3;
    }

    @SinceKotlin(version="1.4")
    @InlineOnly
    private static final StringBuilder appendLine(StringBuilder stringBuilder, short s) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(s);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value.toInt())");
        StringBuilder stringBuilder3 = stringBuilder2.append('\n');
        Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append('\\n')");
        return stringBuilder3;
    }

    @SinceKotlin(version="1.4")
    @InlineOnly
    private static final StringBuilder appendLine(StringBuilder stringBuilder, byte by) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(by);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value.toInt())");
        StringBuilder stringBuilder3 = stringBuilder2.append('\n');
        Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append('\\n')");
        return stringBuilder3;
    }

    @SinceKotlin(version="1.4")
    @InlineOnly
    private static final StringBuilder appendLine(StringBuilder stringBuilder, long l) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(l);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        StringBuilder stringBuilder3 = stringBuilder2.append('\n');
        Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append('\\n')");
        return stringBuilder3;
    }

    @SinceKotlin(version="1.4")
    @InlineOnly
    private static final StringBuilder appendLine(StringBuilder stringBuilder, float f) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(f);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        StringBuilder stringBuilder3 = stringBuilder2.append('\n');
        Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append('\\n')");
        return stringBuilder3;
    }

    @SinceKotlin(version="1.4")
    @InlineOnly
    private static final StringBuilder appendLine(StringBuilder stringBuilder, double d) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(d);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        StringBuilder stringBuilder3 = stringBuilder2.append('\n');
        Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append('\\n')");
        return stringBuilder3;
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine()", imports={}), level=DeprecationLevel.WARNING)
    @NotNull
    public static final Appendable appendln(@NotNull Appendable appendable) {
        Intrinsics.checkNotNullParameter(appendable, "<this>");
        Appendable appendable2 = appendable.append(SystemProperties.LINE_SEPARATOR);
        Intrinsics.checkNotNullExpressionValue(appendable2, "append(SystemProperties.LINE_SEPARATOR)");
        return appendable2;
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final Appendable appendln(Appendable appendable, CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(appendable, "<this>");
        Appendable appendable2 = appendable.append(charSequence);
        Intrinsics.checkNotNullExpressionValue(appendable2, "append(value)");
        return StringsKt.appendln(appendable2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final Appendable appendln(Appendable appendable, char c) {
        Intrinsics.checkNotNullParameter(appendable, "<this>");
        Appendable appendable2 = appendable.append(c);
        Intrinsics.checkNotNullExpressionValue(appendable2, "append(value)");
        return StringsKt.appendln(appendable2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine()", imports={}), level=DeprecationLevel.WARNING)
    @NotNull
    public static final StringBuilder appendln(@NotNull StringBuilder stringBuilder) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(SystemProperties.LINE_SEPARATOR);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(SystemProperties.LINE_SEPARATOR)");
        return stringBuilder2;
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, StringBuffer stringBuffer) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(stringBuffer);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(charSequence);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, String string) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(string);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, Object object) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(object);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, StringBuilder stringBuilder2) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder3 = stringBuilder.append((CharSequence)stringBuilder2);
        Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append(value)");
        return StringsKt.appendln(stringBuilder3);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, char[] cArray) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        Intrinsics.checkNotNullParameter(cArray, "value");
        StringBuilder stringBuilder2 = stringBuilder.append(cArray);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, char c) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(c);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, boolean bl) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(bl);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, int n) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(n);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, short s) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(s);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value.toInt())");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, byte by) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(by);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value.toInt())");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, long l) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(l);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, float f) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(f);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        return StringsKt.appendln(stringBuilder2);
    }

    @Deprecated(message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final StringBuilder appendln(StringBuilder stringBuilder, double d) {
        Intrinsics.checkNotNullParameter(stringBuilder, "<this>");
        StringBuilder stringBuilder2 = stringBuilder.append(d);
        Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
        return StringsKt.appendln(stringBuilder2);
    }
}

