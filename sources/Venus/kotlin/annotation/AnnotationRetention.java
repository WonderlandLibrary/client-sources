/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.annotation;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/annotation/AnnotationRetention;", "", "(Ljava/lang/String;I)V", "SOURCE", "BINARY", "RUNTIME", "kotlin-stdlib"})
public final class AnnotationRetention
extends Enum<AnnotationRetention> {
    public static final /* enum */ AnnotationRetention SOURCE = new AnnotationRetention();
    public static final /* enum */ AnnotationRetention BINARY = new AnnotationRetention();
    public static final /* enum */ AnnotationRetention RUNTIME = new AnnotationRetention();
    private static final AnnotationRetention[] $VALUES = AnnotationRetention.$values();
    private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);

    public static AnnotationRetention[] values() {
        return (AnnotationRetention[])$VALUES.clone();
    }

    public static AnnotationRetention valueOf(String string) {
        return Enum.valueOf(AnnotationRetention.class, string);
    }

    @NotNull
    public static EnumEntries<AnnotationRetention> getEntries() {
        return $ENTRIES;
    }

    private static final AnnotationRetention[] $values() {
        AnnotationRetention[] annotationRetentionArray = new AnnotationRetention[]{SOURCE, BINARY, RUNTIME};
        return annotationRetentionArray;
    }
}

