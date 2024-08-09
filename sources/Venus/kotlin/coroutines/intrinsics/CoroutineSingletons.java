/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0081\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/coroutines/intrinsics/CoroutineSingletons;", "", "(Ljava/lang/String;I)V", "COROUTINE_SUSPENDED", "UNDECIDED", "RESUMED", "kotlin-stdlib"})
@SinceKotlin(version="1.3")
@PublishedApi
public final class CoroutineSingletons
extends Enum<CoroutineSingletons> {
    public static final /* enum */ CoroutineSingletons COROUTINE_SUSPENDED = new CoroutineSingletons();
    public static final /* enum */ CoroutineSingletons UNDECIDED = new CoroutineSingletons();
    public static final /* enum */ CoroutineSingletons RESUMED = new CoroutineSingletons();
    private static final CoroutineSingletons[] $VALUES = CoroutineSingletons.$values();
    private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);

    public static CoroutineSingletons[] values() {
        return (CoroutineSingletons[])$VALUES.clone();
    }

    public static CoroutineSingletons valueOf(String string) {
        return Enum.valueOf(CoroutineSingletons.class, string);
    }

    @NotNull
    public static EnumEntries<CoroutineSingletons> getEntries() {
        return $ENTRIES;
    }

    private static final CoroutineSingletons[] $values() {
        CoroutineSingletons[] coroutineSingletonsArray = new CoroutineSingletons[]{COROUTINE_SUSPENDED, UNDECIDED, RESUMED};
        return coroutineSingletonsArray;
    }
}

