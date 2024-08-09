/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.path;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001Bw\u0012\u001a\u0010\u0003\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004\u0012\u001a\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004\u0012\u001a\u0010\b\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004\u0012\u001c\u0010\n\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004\u00a2\u0006\u0002\u0010\u000bJ\u001a\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\tH\u0016J\u0018\u0010\u000f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0005H\u0016J\u0018\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0005H\u0016J\u0018\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\tH\u0016R$\u0010\n\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\"\u0010\u0003\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\"\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\"\u0010\b\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lkotlin/io/path/FileVisitorImpl;", "Ljava/nio/file/SimpleFileVisitor;", "Ljava/nio/file/Path;", "onPreVisitDirectory", "Lkotlin/Function2;", "Ljava/nio/file/attribute/BasicFileAttributes;", "Ljava/nio/file/FileVisitResult;", "onVisitFile", "onVisitFileFailed", "Ljava/io/IOException;", "onPostVisitDirectory", "(Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;)V", "postVisitDirectory", "dir", "exc", "preVisitDirectory", "attrs", "visitFile", "file", "visitFileFailed", "kotlin-stdlib-jdk7"})
final class FileVisitorImpl
extends SimpleFileVisitor<Path> {
    @Nullable
    private final Function2<Path, BasicFileAttributes, FileVisitResult> onPreVisitDirectory;
    @Nullable
    private final Function2<Path, BasicFileAttributes, FileVisitResult> onVisitFile;
    @Nullable
    private final Function2<Path, IOException, FileVisitResult> onVisitFileFailed;
    @Nullable
    private final Function2<Path, IOException, FileVisitResult> onPostVisitDirectory;

    public FileVisitorImpl(@Nullable Function2<? super Path, ? super BasicFileAttributes, ? extends FileVisitResult> function2, @Nullable Function2<? super Path, ? super BasicFileAttributes, ? extends FileVisitResult> function22, @Nullable Function2<? super Path, ? super IOException, ? extends FileVisitResult> function23, @Nullable Function2<? super Path, ? super IOException, ? extends FileVisitResult> function24) {
        this.onPreVisitDirectory = function2;
        this.onVisitFile = function22;
        this.onVisitFileFailed = function23;
        this.onPostVisitDirectory = function24;
    }

    @Override
    @NotNull
    public FileVisitResult preVisitDirectory(@NotNull Path path, @NotNull BasicFileAttributes basicFileAttributes) {
        Intrinsics.checkNotNullParameter(path, "dir");
        Intrinsics.checkNotNullParameter(basicFileAttributes, "attrs");
        Object object = this.onPreVisitDirectory;
        if (object == null || (object = object.invoke(path, basicFileAttributes)) == null) {
            FileVisitResult fileVisitResult = super.preVisitDirectory(path, basicFileAttributes);
            object = fileVisitResult;
            Intrinsics.checkNotNullExpressionValue((Object)fileVisitResult, "super.preVisitDirectory(dir, attrs)");
        }
        return object;
    }

    @Override
    @NotNull
    public FileVisitResult visitFile(@NotNull Path path, @NotNull BasicFileAttributes basicFileAttributes) {
        Intrinsics.checkNotNullParameter(path, "file");
        Intrinsics.checkNotNullParameter(basicFileAttributes, "attrs");
        Object object = this.onVisitFile;
        if (object == null || (object = object.invoke(path, basicFileAttributes)) == null) {
            FileVisitResult fileVisitResult = super.visitFile(path, basicFileAttributes);
            object = fileVisitResult;
            Intrinsics.checkNotNullExpressionValue((Object)fileVisitResult, "super.visitFile(file, attrs)");
        }
        return object;
    }

    @Override
    @NotNull
    public FileVisitResult visitFileFailed(@NotNull Path path, @NotNull IOException iOException) {
        Intrinsics.checkNotNullParameter(path, "file");
        Intrinsics.checkNotNullParameter(iOException, "exc");
        Object object = this.onVisitFileFailed;
        if (object == null || (object = object.invoke(path, iOException)) == null) {
            FileVisitResult fileVisitResult = super.visitFileFailed(path, iOException);
            object = fileVisitResult;
            Intrinsics.checkNotNullExpressionValue((Object)fileVisitResult, "super.visitFileFailed(file, exc)");
        }
        return object;
    }

    @Override
    @NotNull
    public FileVisitResult postVisitDirectory(@NotNull Path path, @Nullable IOException iOException) {
        Intrinsics.checkNotNullParameter(path, "dir");
        Object object = this.onPostVisitDirectory;
        if (object == null || (object = object.invoke(path, iOException)) == null) {
            FileVisitResult fileVisitResult = super.postVisitDirectory(path, iOException);
            object = fileVisitResult;
            Intrinsics.checkNotNullExpressionValue((Object)fileVisitResult, "super.postVisitDirectory(dir, exc)");
        }
        return object;
    }

    @Override
    public FileVisitResult preVisitDirectory(Object object, BasicFileAttributes basicFileAttributes) {
        return this.preVisitDirectory((Path)object, basicFileAttributes);
    }

    @Override
    public FileVisitResult visitFile(Object object, BasicFileAttributes basicFileAttributes) {
        return this.visitFile((Path)object, basicFileAttributes);
    }

    @Override
    public FileVisitResult visitFileFailed(Object object, IOException iOException) {
        return this.visitFileFailed((Path)object, iOException);
    }

    @Override
    public FileVisitResult postVisitDirectory(Object object, IOException iOException) {
        return this.postVisitDirectory((Path)object, iOException);
    }
}

