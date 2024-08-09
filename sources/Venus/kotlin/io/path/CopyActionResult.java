/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.path;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.path.ExperimentalPathApi;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0087\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/io/path/CopyActionResult;", "", "(Ljava/lang/String;I)V", "CONTINUE", "SKIP_SUBTREE", "TERMINATE", "kotlin-stdlib-jdk7"})
@ExperimentalPathApi
@SinceKotlin(version="1.8")
public final class CopyActionResult
extends Enum<CopyActionResult> {
    public static final /* enum */ CopyActionResult CONTINUE = new CopyActionResult();
    public static final /* enum */ CopyActionResult SKIP_SUBTREE = new CopyActionResult();
    public static final /* enum */ CopyActionResult TERMINATE = new CopyActionResult();
    private static final CopyActionResult[] $VALUES = CopyActionResult.$values();
    private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);

    public static CopyActionResult[] values() {
        return (CopyActionResult[])$VALUES.clone();
    }

    public static CopyActionResult valueOf(String string) {
        return Enum.valueOf(CopyActionResult.class, string);
    }

    @NotNull
    public static EnumEntries<CopyActionResult> getEntries() {
        return $ENTRIES;
    }

    private static final CopyActionResult[] $values() {
        CopyActionResult[] copyActionResultArray = new CopyActionResult[]{CONTINUE, SKIP_SUBTREE, TERMINATE};
        return copyActionResultArray;
    }
}

