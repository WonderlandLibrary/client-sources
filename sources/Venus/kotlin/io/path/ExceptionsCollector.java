/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.path;

import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0015\u001a\u00020\u00162\n\u0010\u0017\u001a\u00060\u0007j\u0002`\bJ\u000e\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\fJ\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\fR\u001b\u0010\u0005\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001b"}, d2={"Lkotlin/io/path/ExceptionsCollector;", "", "limit", "", "(I)V", "collectedExceptions", "", "Ljava/lang/Exception;", "Lkotlin/Exception;", "getCollectedExceptions", "()Ljava/util/List;", "path", "Ljava/nio/file/Path;", "getPath", "()Ljava/nio/file/Path;", "setPath", "(Ljava/nio/file/Path;)V", "<set-?>", "totalExceptions", "getTotalExceptions", "()I", "collect", "", "exception", "enterEntry", "name", "exitEntry", "kotlin-stdlib-jdk7"})
final class ExceptionsCollector {
    private final int limit;
    private int totalExceptions;
    @NotNull
    private final List<Exception> collectedExceptions;
    @Nullable
    private Path path;

    public ExceptionsCollector(int n) {
        this.limit = n;
        this.collectedExceptions = new ArrayList();
    }

    public ExceptionsCollector(int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            n = 64;
        }
        this(n);
    }

    public final int getTotalExceptions() {
        return this.totalExceptions;
    }

    @NotNull
    public final List<Exception> getCollectedExceptions() {
        return this.collectedExceptions;
    }

    @Nullable
    public final Path getPath() {
        return this.path;
    }

    public final void setPath(@Nullable Path path) {
        this.path = path;
    }

    public final void enterEntry(@NotNull Path path) {
        Intrinsics.checkNotNullParameter(path, "name");
        Path path2 = this.path;
        this.path = path2 != null ? path2.resolve(path) : null;
    }

    public final void exitEntry(@NotNull Path path) {
        Intrinsics.checkNotNullParameter(path, "name");
        Path path2 = this.path;
        if (!Intrinsics.areEqual(path, path2 != null ? path2.getFileName() : null)) {
            String string = "Failed requirement.";
            throw new IllegalArgumentException(string.toString());
        }
        Path path3 = this.path;
        this.path = path3 != null ? path3.getParent() : null;
    }

    public final void collect(@NotNull Exception exception) {
        boolean bl;
        Intrinsics.checkNotNullParameter(exception, "exception");
        ++this.totalExceptions;
        boolean bl2 = bl = this.collectedExceptions.size() < this.limit;
        if (bl) {
            Exception exception2;
            if (this.path != null) {
                Throwable throwable = new FileSystemException(String.valueOf(this.path)).initCause(exception);
                Intrinsics.checkNotNull(throwable, "null cannot be cast to non-null type java.nio.file.FileSystemException");
                exception2 = (FileSystemException)throwable;
            } else {
                exception2 = exception;
            }
            Exception exception3 = exception2;
            this.collectedExceptions.add(exception3);
        }
    }

    public ExceptionsCollector() {
        this(0, 1, null);
    }
}

