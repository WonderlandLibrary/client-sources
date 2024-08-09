/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.reflect;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0087\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lkotlin/reflect/KVisibility;", "", "(Ljava/lang/String;I)V", "PUBLIC", "PROTECTED", "INTERNAL", "PRIVATE", "kotlin-stdlib"})
@SinceKotlin(version="1.1")
public final class KVisibility
extends Enum<KVisibility> {
    public static final /* enum */ KVisibility PUBLIC = new KVisibility();
    public static final /* enum */ KVisibility PROTECTED = new KVisibility();
    public static final /* enum */ KVisibility INTERNAL = new KVisibility();
    public static final /* enum */ KVisibility PRIVATE = new KVisibility();
    private static final KVisibility[] $VALUES = KVisibility.$values();
    private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);

    public static KVisibility[] values() {
        return (KVisibility[])$VALUES.clone();
    }

    public static KVisibility valueOf(String string) {
        return Enum.valueOf(KVisibility.class, string);
    }

    @NotNull
    public static EnumEntries<KVisibility> getEntries() {
        return $ENTRIES;
    }

    private static final KVisibility[] $values() {
        KVisibility[] kVisibilityArray = new KVisibility[]{PUBLIC, PROTECTED, INTERNAL, PRIVATE};
        return kVisibilityArray;
    }
}

