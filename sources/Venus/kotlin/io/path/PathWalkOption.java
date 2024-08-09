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

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0087\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/io/path/PathWalkOption;", "", "(Ljava/lang/String;I)V", "INCLUDE_DIRECTORIES", "BREADTH_FIRST", "FOLLOW_LINKS", "kotlin-stdlib-jdk7"})
@ExperimentalPathApi
@SinceKotlin(version="1.7")
public final class PathWalkOption
extends Enum<PathWalkOption> {
    public static final /* enum */ PathWalkOption INCLUDE_DIRECTORIES = new PathWalkOption();
    public static final /* enum */ PathWalkOption BREADTH_FIRST = new PathWalkOption();
    public static final /* enum */ PathWalkOption FOLLOW_LINKS = new PathWalkOption();
    private static final PathWalkOption[] $VALUES = PathWalkOption.$values();
    private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);

    public static PathWalkOption[] values() {
        return (PathWalkOption[])$VALUES.clone();
    }

    public static PathWalkOption valueOf(String string) {
        return Enum.valueOf(PathWalkOption.class, string);
    }

    @NotNull
    public static EnumEntries<PathWalkOption> getEntries() {
        return $ENTRIES;
    }

    private static final PathWalkOption[] $values() {
        PathWalkOption[] pathWalkOptionArray = new PathWalkOption[]{INCLUDE_DIRECTORIES, BREADTH_FIRST, FOLLOW_LINKS};
        return pathWalkOptionArray;
    }
}

