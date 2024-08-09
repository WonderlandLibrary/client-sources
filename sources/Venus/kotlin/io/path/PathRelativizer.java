/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.path;

import java.nio.file.Path;
import java.nio.file.Paths;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lkotlin/io/path/PathRelativizer;", "", "()V", "emptyPath", "Ljava/nio/file/Path;", "kotlin.jvm.PlatformType", "parentPath", "tryRelativeTo", "path", "base", "kotlin-stdlib-jdk7"})
final class PathRelativizer {
    @NotNull
    public static final PathRelativizer INSTANCE = new PathRelativizer();
    private static final Path emptyPath = Paths.get("", new String[0]);
    private static final Path parentPath = Paths.get("..", new String[0]);

    private PathRelativizer() {
    }

    @NotNull
    public final Path tryRelativeTo(@NotNull Path path, @NotNull Path path2) {
        Path path3;
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(path2, "base");
        Path path4 = path2.normalize();
        Path path5 = path.normalize();
        Path path6 = path4.relativize(path5);
        int n = Math.min(path4.getNameCount(), path5.getNameCount());
        for (int i = 0; i < n && Intrinsics.areEqual(path4.getName(i), parentPath); ++i) {
            if (Intrinsics.areEqual(path5.getName(i), parentPath)) continue;
            throw new IllegalArgumentException("Unable to compute relative path");
        }
        if (!Intrinsics.areEqual(path5, path4) && Intrinsics.areEqual(path4, emptyPath)) {
            path3 = path5;
        } else {
            String string = ((Object)path6).toString();
            String string2 = path6.getFileSystem().getSeparator();
            Intrinsics.checkNotNullExpressionValue(string2, "rn.fileSystem.separator");
            path3 = StringsKt.endsWith$default(string, string2, false, 2, null) ? path6.getFileSystem().getPath(StringsKt.dropLast(string, path6.getFileSystem().getSeparator().length()), new String[0]) : path6;
        }
        Path path7 = path3;
        Intrinsics.checkNotNullExpressionValue(path7, "r");
        return path7;
    }
}

