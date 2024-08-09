/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.internal;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0081\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/internal/RequireKotlinVersionKind;", "", "(Ljava/lang/String;I)V", "LANGUAGE_VERSION", "COMPILER_VERSION", "API_VERSION", "kotlin-stdlib"})
@SinceKotlin(version="1.2")
public final class RequireKotlinVersionKind
extends Enum<RequireKotlinVersionKind> {
    public static final /* enum */ RequireKotlinVersionKind LANGUAGE_VERSION = new RequireKotlinVersionKind();
    public static final /* enum */ RequireKotlinVersionKind COMPILER_VERSION = new RequireKotlinVersionKind();
    public static final /* enum */ RequireKotlinVersionKind API_VERSION = new RequireKotlinVersionKind();
    private static final RequireKotlinVersionKind[] $VALUES = RequireKotlinVersionKind.$values();
    private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);

    public static RequireKotlinVersionKind[] values() {
        return (RequireKotlinVersionKind[])$VALUES.clone();
    }

    public static RequireKotlinVersionKind valueOf(String string) {
        return Enum.valueOf(RequireKotlinVersionKind.class, string);
    }

    @NotNull
    public static EnumEntries<RequireKotlinVersionKind> getEntries() {
        return $ENTRIES;
    }

    private static final RequireKotlinVersionKind[] $values() {
        RequireKotlinVersionKind[] requireKotlinVersionKindArray = new RequireKotlinVersionKind[]{LANGUAGE_VERSION, COMPILER_VERSION, API_VERSION};
        return requireKotlinVersionKindArray;
    }
}

