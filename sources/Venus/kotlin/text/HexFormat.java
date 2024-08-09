/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0004\u0011\u0012\u0013\u0014B\u001f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0015"}, d2={"Lkotlin/text/HexFormat;", "", "upperCase", "", "bytes", "Lkotlin/text/HexFormat$BytesHexFormat;", "number", "Lkotlin/text/HexFormat$NumberHexFormat;", "(ZLkotlin/text/HexFormat$BytesHexFormat;Lkotlin/text/HexFormat$NumberHexFormat;)V", "getBytes", "()Lkotlin/text/HexFormat$BytesHexFormat;", "getNumber", "()Lkotlin/text/HexFormat$NumberHexFormat;", "getUpperCase", "()Z", "toString", "", "Builder", "BytesHexFormat", "Companion", "NumberHexFormat", "kotlin-stdlib"})
@ExperimentalStdlibApi
@SinceKotlin(version="1.9")
public final class HexFormat {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final boolean upperCase;
    @NotNull
    private final BytesHexFormat bytes;
    @NotNull
    private final NumberHexFormat number;
    @NotNull
    private static final HexFormat Default = new HexFormat(false, BytesHexFormat.Companion.getDefault$kotlin_stdlib(), NumberHexFormat.Companion.getDefault$kotlin_stdlib());
    @NotNull
    private static final HexFormat UpperCase = new HexFormat(true, BytesHexFormat.Companion.getDefault$kotlin_stdlib(), NumberHexFormat.Companion.getDefault$kotlin_stdlib());

    public HexFormat(boolean bl, @NotNull BytesHexFormat bytesHexFormat, @NotNull NumberHexFormat numberHexFormat) {
        Intrinsics.checkNotNullParameter(bytesHexFormat, "bytes");
        Intrinsics.checkNotNullParameter(numberHexFormat, "number");
        this.upperCase = bl;
        this.bytes = bytesHexFormat;
        this.number = numberHexFormat;
    }

    public final boolean getUpperCase() {
        return this.upperCase;
    }

    @NotNull
    public final BytesHexFormat getBytes() {
        return this.bytes;
    }

    @NotNull
    public final NumberHexFormat getNumber() {
        return this.number;
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2 = stringBuilder = new StringBuilder();
        boolean bl = false;
        StringBuilder stringBuilder3 = stringBuilder2.append("HexFormat(");
        Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append(\"HexFormat(\")");
        Intrinsics.checkNotNullExpressionValue(stringBuilder3.append('\n'), "append('\\n')");
        StringBuilder stringBuilder4 = stringBuilder2.append("    upperCase = ").append(this.upperCase);
        Intrinsics.checkNotNullExpressionValue(stringBuilder4, "append(\"    upperCase = \").append(upperCase)");
        StringBuilder stringBuilder5 = stringBuilder4;
        StringBuilder stringBuilder6 = stringBuilder5.append(",");
        Intrinsics.checkNotNullExpressionValue(stringBuilder6, "append(value)");
        Intrinsics.checkNotNullExpressionValue(stringBuilder6.append('\n'), "append('\\n')");
        StringBuilder stringBuilder7 = stringBuilder2.append("    bytes = BytesHexFormat(");
        Intrinsics.checkNotNullExpressionValue(stringBuilder7, "append(\"    bytes = BytesHexFormat(\")");
        Intrinsics.checkNotNullExpressionValue(stringBuilder7.append('\n'), "append('\\n')");
        Intrinsics.checkNotNullExpressionValue(this.bytes.appendOptionsTo$kotlin_stdlib(stringBuilder2, "        ").append('\n'), "append('\\n')");
        StringBuilder stringBuilder8 = stringBuilder2.append("    ),");
        Intrinsics.checkNotNullExpressionValue(stringBuilder8, "append(\"    ),\")");
        Intrinsics.checkNotNullExpressionValue(stringBuilder8.append('\n'), "append('\\n')");
        StringBuilder stringBuilder9 = stringBuilder2.append("    number = NumberHexFormat(");
        Intrinsics.checkNotNullExpressionValue(stringBuilder9, "append(\"    number = NumberHexFormat(\")");
        Intrinsics.checkNotNullExpressionValue(stringBuilder9.append('\n'), "append('\\n')");
        Intrinsics.checkNotNullExpressionValue(this.number.appendOptionsTo$kotlin_stdlib(stringBuilder2, "        ").append('\n'), "append('\\n')");
        StringBuilder stringBuilder10 = stringBuilder2.append("    )");
        Intrinsics.checkNotNullExpressionValue(stringBuilder10, "append(\"    )\")");
        Intrinsics.checkNotNullExpressionValue(stringBuilder10.append('\n'), "append('\\n')");
        stringBuilder2.append(")");
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public static final HexFormat access$getDefault$cp() {
        return Default;
    }

    public static final HexFormat access$getUpperCase$cp() {
        return UpperCase;
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007\b\u0001\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0001J%\u0010\u0007\u001a\u00020\u00152\u0017\u0010\u0016\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00150\u0017\u00a2\u0006\u0002\b\u0018H\u0087\b\u00f8\u0001\u0000J%\u0010\n\u001a\u00020\u00152\u0017\u0010\u0016\u001a\u0013\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00150\u0017\u00a2\u0006\u0002\b\u0018H\u0087\b\u00f8\u0001\u0000R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00068F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\u0019"}, d2={"Lkotlin/text/HexFormat$Builder;", "", "()V", "_bytes", "Lkotlin/text/HexFormat$BytesHexFormat$Builder;", "_number", "Lkotlin/text/HexFormat$NumberHexFormat$Builder;", "bytes", "getBytes", "()Lkotlin/text/HexFormat$BytesHexFormat$Builder;", "number", "getNumber", "()Lkotlin/text/HexFormat$NumberHexFormat$Builder;", "upperCase", "", "getUpperCase", "()Z", "setUpperCase", "(Z)V", "build", "Lkotlin/text/HexFormat;", "", "builderAction", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "kotlin-stdlib"})
    public static final class Builder {
        private boolean upperCase = Companion.getDefault().getUpperCase();
        @Nullable
        private BytesHexFormat.Builder _bytes;
        @Nullable
        private NumberHexFormat.Builder _number;

        @PublishedApi
        public Builder() {
        }

        public final boolean getUpperCase() {
            return this.upperCase;
        }

        public final void setUpperCase(boolean bl) {
            this.upperCase = bl;
        }

        @NotNull
        public final BytesHexFormat.Builder getBytes() {
            if (this._bytes == null) {
                this._bytes = new BytesHexFormat.Builder();
            }
            BytesHexFormat.Builder builder = this._bytes;
            Intrinsics.checkNotNull(builder);
            return builder;
        }

        @NotNull
        public final NumberHexFormat.Builder getNumber() {
            if (this._number == null) {
                this._number = new NumberHexFormat.Builder();
            }
            NumberHexFormat.Builder builder = this._number;
            Intrinsics.checkNotNull(builder);
            return builder;
        }

        @InlineOnly
        private final void bytes(Function1<? super BytesHexFormat.Builder, Unit> function1) {
            Intrinsics.checkNotNullParameter(function1, "builderAction");
            function1.invoke(this.getBytes());
        }

        @InlineOnly
        private final void number(Function1<? super NumberHexFormat.Builder, Unit> function1) {
            Intrinsics.checkNotNullParameter(function1, "builderAction");
            function1.invoke(this.getNumber());
        }

        @PublishedApi
        @NotNull
        public final HexFormat build() {
            Object object;
            Object object2 = this._bytes;
            if (object2 == null || (object2 = ((BytesHexFormat.Builder)object2).build$kotlin_stdlib()) == null) {
                object2 = BytesHexFormat.Companion.getDefault$kotlin_stdlib();
            }
            if ((object = this._number) == null || (object = ((NumberHexFormat.Builder)object).build$kotlin_stdlib()) == null) {
                object = NumberHexFormat.Companion.getDefault$kotlin_stdlib();
            }
            return new HexFormat(this.upperCase, (BytesHexFormat)object2, (NumberHexFormat)object);
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0002\u001a\u001bB7\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\nJ%\u0010\u0013\u001a\u00060\u0014j\u0002`\u00152\n\u0010\u0016\u001a\u00060\u0014j\u0002`\u00152\u0006\u0010\u0017\u001a\u00020\u0006H\u0000\u00a2\u0006\u0002\b\u0018J\b\u0010\u0019\u001a\u00020\u0006H\u0016R\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f\u00a8\u0006\u001c"}, d2={"Lkotlin/text/HexFormat$BytesHexFormat;", "", "bytesPerLine", "", "bytesPerGroup", "groupSeparator", "", "byteSeparator", "bytePrefix", "byteSuffix", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getBytePrefix", "()Ljava/lang/String;", "getByteSeparator", "getByteSuffix", "getBytesPerGroup", "()I", "getBytesPerLine", "getGroupSeparator", "appendOptionsTo", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "sb", "indent", "appendOptionsTo$kotlin_stdlib", "toString", "Builder", "Companion", "kotlin-stdlib"})
    public static final class BytesHexFormat {
        @NotNull
        public static final Companion Companion = new Companion(null);
        private final int bytesPerLine;
        private final int bytesPerGroup;
        @NotNull
        private final String groupSeparator;
        @NotNull
        private final String byteSeparator;
        @NotNull
        private final String bytePrefix;
        @NotNull
        private final String byteSuffix;
        @NotNull
        private static final BytesHexFormat Default = new BytesHexFormat(Integer.MAX_VALUE, Integer.MAX_VALUE, "  ", "", "", "");

        public BytesHexFormat(int n, int n2, @NotNull String string, @NotNull String string2, @NotNull String string3, @NotNull String string4) {
            Intrinsics.checkNotNullParameter(string, "groupSeparator");
            Intrinsics.checkNotNullParameter(string2, "byteSeparator");
            Intrinsics.checkNotNullParameter(string3, "bytePrefix");
            Intrinsics.checkNotNullParameter(string4, "byteSuffix");
            this.bytesPerLine = n;
            this.bytesPerGroup = n2;
            this.groupSeparator = string;
            this.byteSeparator = string2;
            this.bytePrefix = string3;
            this.byteSuffix = string4;
        }

        public final int getBytesPerLine() {
            return this.bytesPerLine;
        }

        public final int getBytesPerGroup() {
            return this.bytesPerGroup;
        }

        @NotNull
        public final String getGroupSeparator() {
            return this.groupSeparator;
        }

        @NotNull
        public final String getByteSeparator() {
            return this.byteSeparator;
        }

        @NotNull
        public final String getBytePrefix() {
            return this.bytePrefix;
        }

        @NotNull
        public final String getByteSuffix() {
            return this.byteSuffix;
        }

        @NotNull
        public String toString() {
            StringBuilder stringBuilder;
            StringBuilder stringBuilder2 = stringBuilder = new StringBuilder();
            boolean bl = false;
            StringBuilder stringBuilder3 = stringBuilder2.append("BytesHexFormat(");
            Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append(\"BytesHexFormat(\")");
            Intrinsics.checkNotNullExpressionValue(stringBuilder3.append('\n'), "append('\\n')");
            Intrinsics.checkNotNullExpressionValue(this.appendOptionsTo$kotlin_stdlib(stringBuilder2, "    ").append('\n'), "append('\\n')");
            stringBuilder2.append(")");
            String string = stringBuilder.toString();
            Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
            return string;
        }

        @NotNull
        public final StringBuilder appendOptionsTo$kotlin_stdlib(@NotNull StringBuilder stringBuilder, @NotNull String string) {
            Intrinsics.checkNotNullParameter(stringBuilder, "sb");
            Intrinsics.checkNotNullParameter(string, "indent");
            StringBuilder stringBuilder2 = stringBuilder.append(string).append("bytesPerLine = ").append(this.bytesPerLine);
            Intrinsics.checkNotNullExpressionValue(stringBuilder2, "sb.append(indent).append\u2026= \").append(bytesPerLine)");
            StringBuilder stringBuilder3 = stringBuilder2;
            StringBuilder stringBuilder4 = stringBuilder3.append(",");
            Intrinsics.checkNotNullExpressionValue(stringBuilder4, "append(value)");
            Intrinsics.checkNotNullExpressionValue(stringBuilder4.append('\n'), "append('\\n')");
            StringBuilder stringBuilder5 = stringBuilder.append(string).append("bytesPerGroup = ").append(this.bytesPerGroup);
            Intrinsics.checkNotNullExpressionValue(stringBuilder5, "sb.append(indent).append\u2026 \").append(bytesPerGroup)");
            stringBuilder3 = stringBuilder5;
            StringBuilder stringBuilder6 = stringBuilder3.append(",");
            Intrinsics.checkNotNullExpressionValue(stringBuilder6, "append(value)");
            Intrinsics.checkNotNullExpressionValue(stringBuilder6.append('\n'), "append('\\n')");
            StringBuilder stringBuilder7 = stringBuilder.append(string).append("groupSeparator = \"").append(this.groupSeparator);
            Intrinsics.checkNotNullExpressionValue(stringBuilder7, "sb.append(indent).append\u2026\").append(groupSeparator)");
            stringBuilder3 = stringBuilder7;
            StringBuilder stringBuilder8 = stringBuilder3.append("\",");
            Intrinsics.checkNotNullExpressionValue(stringBuilder8, "append(value)");
            Intrinsics.checkNotNullExpressionValue(stringBuilder8.append('\n'), "append('\\n')");
            StringBuilder stringBuilder9 = stringBuilder.append(string).append("byteSeparator = \"").append(this.byteSeparator);
            Intrinsics.checkNotNullExpressionValue(stringBuilder9, "sb.append(indent).append\u2026\"\").append(byteSeparator)");
            stringBuilder3 = stringBuilder9;
            StringBuilder stringBuilder10 = stringBuilder3.append("\",");
            Intrinsics.checkNotNullExpressionValue(stringBuilder10, "append(value)");
            Intrinsics.checkNotNullExpressionValue(stringBuilder10.append('\n'), "append('\\n')");
            StringBuilder stringBuilder11 = stringBuilder.append(string).append("bytePrefix = \"").append(this.bytePrefix);
            Intrinsics.checkNotNullExpressionValue(stringBuilder11, "sb.append(indent).append\u2026= \\\"\").append(bytePrefix)");
            stringBuilder3 = stringBuilder11;
            StringBuilder stringBuilder12 = stringBuilder3.append("\",");
            Intrinsics.checkNotNullExpressionValue(stringBuilder12, "append(value)");
            Intrinsics.checkNotNullExpressionValue(stringBuilder12.append('\n'), "append('\\n')");
            stringBuilder.append(string).append("byteSuffix = \"").append(this.byteSuffix).append("\"");
            return stringBuilder;
        }

        public static final BytesHexFormat access$getDefault$cp() {
            return Default;
        }

        @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0000\u00a2\u0006\u0002\u0010\u0002J\r\u0010\u001c\u001a\u00020\u001dH\u0000\u00a2\u0006\u0002\b\u001eR$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\n\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR$\u0010\r\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR$\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u0010@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R$\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u0010@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015R\u001a\u0010\u0019\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0007\"\u0004\b\u001b\u0010\t\u00a8\u0006\u001f"}, d2={"Lkotlin/text/HexFormat$BytesHexFormat$Builder;", "", "()V", "value", "", "bytePrefix", "getBytePrefix", "()Ljava/lang/String;", "setBytePrefix", "(Ljava/lang/String;)V", "byteSeparator", "getByteSeparator", "setByteSeparator", "byteSuffix", "getByteSuffix", "setByteSuffix", "", "bytesPerGroup", "getBytesPerGroup", "()I", "setBytesPerGroup", "(I)V", "bytesPerLine", "getBytesPerLine", "setBytesPerLine", "groupSeparator", "getGroupSeparator", "setGroupSeparator", "build", "Lkotlin/text/HexFormat$BytesHexFormat;", "build$kotlin_stdlib", "kotlin-stdlib"})
        public static final class Builder {
            private int bytesPerLine = Companion.getDefault$kotlin_stdlib().getBytesPerLine();
            private int bytesPerGroup = Companion.getDefault$kotlin_stdlib().getBytesPerGroup();
            @NotNull
            private String groupSeparator = Companion.getDefault$kotlin_stdlib().getGroupSeparator();
            @NotNull
            private String byteSeparator = Companion.getDefault$kotlin_stdlib().getByteSeparator();
            @NotNull
            private String bytePrefix = Companion.getDefault$kotlin_stdlib().getBytePrefix();
            @NotNull
            private String byteSuffix = Companion.getDefault$kotlin_stdlib().getByteSuffix();

            public final int getBytesPerLine() {
                return this.bytesPerLine;
            }

            public final void setBytesPerLine(int n) {
                if (n <= 0) {
                    throw new IllegalArgumentException("Non-positive values are prohibited for bytesPerLine, but was " + n);
                }
                this.bytesPerLine = n;
            }

            public final int getBytesPerGroup() {
                return this.bytesPerGroup;
            }

            public final void setBytesPerGroup(int n) {
                if (n <= 0) {
                    throw new IllegalArgumentException("Non-positive values are prohibited for bytesPerGroup, but was " + n);
                }
                this.bytesPerGroup = n;
            }

            @NotNull
            public final String getGroupSeparator() {
                return this.groupSeparator;
            }

            public final void setGroupSeparator(@NotNull String string) {
                Intrinsics.checkNotNullParameter(string, "<set-?>");
                this.groupSeparator = string;
            }

            @NotNull
            public final String getByteSeparator() {
                return this.byteSeparator;
            }

            public final void setByteSeparator(@NotNull String string) {
                Intrinsics.checkNotNullParameter(string, "value");
                if (StringsKt.contains$default((CharSequence)string, '\n', false, 2, null) || StringsKt.contains$default((CharSequence)string, '\r', false, 2, null)) {
                    throw new IllegalArgumentException("LF and CR characters are prohibited in byteSeparator, but was " + string);
                }
                this.byteSeparator = string;
            }

            @NotNull
            public final String getBytePrefix() {
                return this.bytePrefix;
            }

            public final void setBytePrefix(@NotNull String string) {
                Intrinsics.checkNotNullParameter(string, "value");
                if (StringsKt.contains$default((CharSequence)string, '\n', false, 2, null) || StringsKt.contains$default((CharSequence)string, '\r', false, 2, null)) {
                    throw new IllegalArgumentException("LF and CR characters are prohibited in bytePrefix, but was " + string);
                }
                this.bytePrefix = string;
            }

            @NotNull
            public final String getByteSuffix() {
                return this.byteSuffix;
            }

            public final void setByteSuffix(@NotNull String string) {
                Intrinsics.checkNotNullParameter(string, "value");
                if (StringsKt.contains$default((CharSequence)string, '\n', false, 2, null) || StringsKt.contains$default((CharSequence)string, '\r', false, 2, null)) {
                    throw new IllegalArgumentException("LF and CR characters are prohibited in byteSuffix, but was " + string);
                }
                this.byteSuffix = string;
            }

            @NotNull
            public final BytesHexFormat build$kotlin_stdlib() {
                return new BytesHexFormat(this.bytesPerLine, this.bytesPerGroup, this.groupSeparator, this.byteSeparator, this.bytePrefix, this.byteSuffix);
            }
        }

        @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lkotlin/text/HexFormat$BytesHexFormat$Companion;", "", "()V", "Default", "Lkotlin/text/HexFormat$BytesHexFormat;", "getDefault$kotlin_stdlib", "()Lkotlin/text/HexFormat$BytesHexFormat;", "kotlin-stdlib"})
        public static final class Companion {
            private Companion() {
            }

            @NotNull
            public final BytesHexFormat getDefault$kotlin_stdlib() {
                return BytesHexFormat.access$getDefault$cp();
            }

            public Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\t"}, d2={"Lkotlin/text/HexFormat$Companion;", "", "()V", "Default", "Lkotlin/text/HexFormat;", "getDefault", "()Lkotlin/text/HexFormat;", "UpperCase", "getUpperCase", "kotlin-stdlib"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final HexFormat getDefault() {
            return HexFormat.access$getDefault$cp();
        }

        @NotNull
        public final HexFormat getUpperCase() {
            return HexFormat.access$getUpperCase$cp();
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00152\u00020\u0001:\u0002\u0014\u0015B\u001f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J%\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\n\u0010\u0010\u001a\u00060\u000ej\u0002`\u000f2\u0006\u0010\u0011\u001a\u00020\u0003H\u0000\u00a2\u0006\u0002\b\u0012J\b\u0010\u0013\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\u0016"}, d2={"Lkotlin/text/HexFormat$NumberHexFormat;", "", "prefix", "", "suffix", "removeLeadingZeros", "", "(Ljava/lang/String;Ljava/lang/String;Z)V", "getPrefix", "()Ljava/lang/String;", "getRemoveLeadingZeros", "()Z", "getSuffix", "appendOptionsTo", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "sb", "indent", "appendOptionsTo$kotlin_stdlib", "toString", "Builder", "Companion", "kotlin-stdlib"})
    public static final class NumberHexFormat {
        @NotNull
        public static final Companion Companion = new Companion(null);
        @NotNull
        private final String prefix;
        @NotNull
        private final String suffix;
        private final boolean removeLeadingZeros;
        @NotNull
        private static final NumberHexFormat Default = new NumberHexFormat("", "", false);

        public NumberHexFormat(@NotNull String string, @NotNull String string2, boolean bl) {
            Intrinsics.checkNotNullParameter(string, "prefix");
            Intrinsics.checkNotNullParameter(string2, "suffix");
            this.prefix = string;
            this.suffix = string2;
            this.removeLeadingZeros = bl;
        }

        @NotNull
        public final String getPrefix() {
            return this.prefix;
        }

        @NotNull
        public final String getSuffix() {
            return this.suffix;
        }

        public final boolean getRemoveLeadingZeros() {
            return this.removeLeadingZeros;
        }

        @NotNull
        public String toString() {
            StringBuilder stringBuilder;
            StringBuilder stringBuilder2 = stringBuilder = new StringBuilder();
            boolean bl = false;
            StringBuilder stringBuilder3 = stringBuilder2.append("NumberHexFormat(");
            Intrinsics.checkNotNullExpressionValue(stringBuilder3, "append(\"NumberHexFormat(\")");
            Intrinsics.checkNotNullExpressionValue(stringBuilder3.append('\n'), "append('\\n')");
            Intrinsics.checkNotNullExpressionValue(this.appendOptionsTo$kotlin_stdlib(stringBuilder2, "    ").append('\n'), "append('\\n')");
            stringBuilder2.append(")");
            String string = stringBuilder.toString();
            Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
            return string;
        }

        @NotNull
        public final StringBuilder appendOptionsTo$kotlin_stdlib(@NotNull StringBuilder stringBuilder, @NotNull String string) {
            Intrinsics.checkNotNullParameter(stringBuilder, "sb");
            Intrinsics.checkNotNullParameter(string, "indent");
            StringBuilder stringBuilder2 = stringBuilder.append(string).append("prefix = \"").append(this.prefix);
            Intrinsics.checkNotNullExpressionValue(stringBuilder2, "sb.append(indent).append\u2026fix = \\\"\").append(prefix)");
            StringBuilder stringBuilder3 = stringBuilder2;
            StringBuilder stringBuilder4 = stringBuilder3.append("\",");
            Intrinsics.checkNotNullExpressionValue(stringBuilder4, "append(value)");
            Intrinsics.checkNotNullExpressionValue(stringBuilder4.append('\n'), "append('\\n')");
            StringBuilder stringBuilder5 = stringBuilder.append(string).append("suffix = \"").append(this.suffix);
            Intrinsics.checkNotNullExpressionValue(stringBuilder5, "sb.append(indent).append\u2026fix = \\\"\").append(suffix)");
            stringBuilder3 = stringBuilder5;
            StringBuilder stringBuilder6 = stringBuilder3.append("\",");
            Intrinsics.checkNotNullExpressionValue(stringBuilder6, "append(value)");
            Intrinsics.checkNotNullExpressionValue(stringBuilder6.append('\n'), "append('\\n')");
            stringBuilder.append(string).append("removeLeadingZeros = ").append(this.removeLeadingZeros);
            return stringBuilder;
        }

        public static final NumberHexFormat access$getDefault$cp() {
            return Default;
        }

        @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0000\u00a2\u0006\u0002\u0010\u0002J\r\u0010\u0013\u001a\u00020\u0014H\u0000\u00a2\u0006\u0002\b\u0015R$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\t\u00a8\u0006\u0016"}, d2={"Lkotlin/text/HexFormat$NumberHexFormat$Builder;", "", "()V", "value", "", "prefix", "getPrefix", "()Ljava/lang/String;", "setPrefix", "(Ljava/lang/String;)V", "removeLeadingZeros", "", "getRemoveLeadingZeros", "()Z", "setRemoveLeadingZeros", "(Z)V", "suffix", "getSuffix", "setSuffix", "build", "Lkotlin/text/HexFormat$NumberHexFormat;", "build$kotlin_stdlib", "kotlin-stdlib"})
        public static final class Builder {
            @NotNull
            private String prefix = Companion.getDefault$kotlin_stdlib().getPrefix();
            @NotNull
            private String suffix = Companion.getDefault$kotlin_stdlib().getSuffix();
            private boolean removeLeadingZeros = Companion.getDefault$kotlin_stdlib().getRemoveLeadingZeros();

            @NotNull
            public final String getPrefix() {
                return this.prefix;
            }

            public final void setPrefix(@NotNull String string) {
                Intrinsics.checkNotNullParameter(string, "value");
                if (StringsKt.contains$default((CharSequence)string, '\n', false, 2, null) || StringsKt.contains$default((CharSequence)string, '\r', false, 2, null)) {
                    throw new IllegalArgumentException("LF and CR characters are prohibited in prefix, but was " + string);
                }
                this.prefix = string;
            }

            @NotNull
            public final String getSuffix() {
                return this.suffix;
            }

            public final void setSuffix(@NotNull String string) {
                Intrinsics.checkNotNullParameter(string, "value");
                if (StringsKt.contains$default((CharSequence)string, '\n', false, 2, null) || StringsKt.contains$default((CharSequence)string, '\r', false, 2, null)) {
                    throw new IllegalArgumentException("LF and CR characters are prohibited in suffix, but was " + string);
                }
                this.suffix = string;
            }

            public final boolean getRemoveLeadingZeros() {
                return this.removeLeadingZeros;
            }

            public final void setRemoveLeadingZeros(boolean bl) {
                this.removeLeadingZeros = bl;
            }

            @NotNull
            public final NumberHexFormat build$kotlin_stdlib() {
                return new NumberHexFormat(this.prefix, this.suffix, this.removeLeadingZeros);
            }
        }

        @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lkotlin/text/HexFormat$NumberHexFormat$Companion;", "", "()V", "Default", "Lkotlin/text/HexFormat$NumberHexFormat;", "getDefault$kotlin_stdlib", "()Lkotlin/text/HexFormat$NumberHexFormat;", "kotlin-stdlib"})
        public static final class Companion {
            private Companion() {
            }

            @NotNull
            public final NumberHexFormat getDefault$kotlin_stdlib() {
                return NumberHexFormat.access$getDefault$cp();
            }

            public Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }
    }
}

