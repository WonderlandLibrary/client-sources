/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.FlagEnum;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0019\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0006R\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010\u00a8\u0006\u0011"}, d2={"Lkotlin/text/RegexOption;", "", "Lkotlin/text/FlagEnum;", "value", "", "mask", "(Ljava/lang/String;III)V", "getMask", "()I", "getValue", "IGNORE_CASE", "MULTILINE", "LITERAL", "UNIX_LINES", "COMMENTS", "DOT_MATCHES_ALL", "CANON_EQ", "kotlin-stdlib"})
public final class RegexOption
extends Enum<RegexOption>
implements FlagEnum {
    private final int value;
    private final int mask;
    public static final /* enum */ RegexOption IGNORE_CASE = new RegexOption(2, 0, 2, null);
    public static final /* enum */ RegexOption MULTILINE = new RegexOption(8, 0, 2, null);
    public static final /* enum */ RegexOption LITERAL = new RegexOption(16, 0, 2, null);
    public static final /* enum */ RegexOption UNIX_LINES = new RegexOption(1, 0, 2, null);
    public static final /* enum */ RegexOption COMMENTS = new RegexOption(4, 0, 2, null);
    public static final /* enum */ RegexOption DOT_MATCHES_ALL = new RegexOption(32, 0, 2, null);
    public static final /* enum */ RegexOption CANON_EQ = new RegexOption(128, 0, 2, null);
    private static final RegexOption[] $VALUES = RegexOption.$values();
    private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);

    private RegexOption(int n2, int n3) {
        this.value = n2;
        this.mask = n3;
    }

    RegexOption(int n2, int n3, int n4, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n4 & 2) != 0) {
            n3 = n2;
        }
        this(n2, n3);
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public int getMask() {
        return this.mask;
    }

    public static RegexOption[] values() {
        return (RegexOption[])$VALUES.clone();
    }

    public static RegexOption valueOf(String string) {
        return Enum.valueOf(RegexOption.class, string);
    }

    @NotNull
    public static EnumEntries<RegexOption> getEntries() {
        return $ENTRIES;
    }

    private static final RegexOption[] $values() {
        RegexOption[] regexOptionArray = new RegexOption[]{IGNORE_CASE, MULTILINE, LITERAL, UNIX_LINES, COMMENTS, DOT_MATCHES_ALL, CANON_EQ};
        return regexOptionArray;
    }
}

