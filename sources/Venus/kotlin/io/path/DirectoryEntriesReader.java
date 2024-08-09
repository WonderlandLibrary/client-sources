/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.path;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.io.path.LinkFollowing;
import kotlin.io.path.PathNode;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u0006\u001a\u00020\u0007J\u0018\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, d2={"Lkotlin/io/path/DirectoryEntriesReader;", "Ljava/nio/file/SimpleFileVisitor;", "Ljava/nio/file/Path;", "followLinks", "", "(Z)V", "directoryNode", "Lkotlin/io/path/PathNode;", "entries", "Lkotlin/collections/ArrayDeque;", "getFollowLinks", "()Z", "preVisitDirectory", "Ljava/nio/file/FileVisitResult;", "dir", "attrs", "Ljava/nio/file/attribute/BasicFileAttributes;", "readEntries", "", "visitFile", "file", "kotlin-stdlib-jdk7"})
@SourceDebugExtension(value={"SMAP\nPathTreeWalk.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PathTreeWalk.kt\nkotlin/io/path/DirectoryEntriesReader\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,177:1\n1#2:178\n*E\n"})
final class DirectoryEntriesReader
extends SimpleFileVisitor<Path> {
    private final boolean followLinks;
    @Nullable
    private PathNode directoryNode;
    @NotNull
    private ArrayDeque<PathNode> entries;

    public DirectoryEntriesReader(boolean bl) {
        this.followLinks = bl;
        this.entries = new ArrayDeque();
    }

    public final boolean getFollowLinks() {
        return this.followLinks;
    }

    @NotNull
    public final List<PathNode> readEntries(@NotNull PathNode pathNode) {
        ArrayDeque<PathNode> arrayDeque;
        Intrinsics.checkNotNullParameter(pathNode, "directoryNode");
        this.directoryNode = pathNode;
        Files.walkFileTree(pathNode.getPath(), LinkFollowing.INSTANCE.toVisitOptions(this.followLinks), 1, this);
        this.entries.removeFirst();
        ArrayDeque<PathNode> arrayDeque2 = arrayDeque = this.entries;
        boolean bl = false;
        this.entries = new ArrayDeque();
        return arrayDeque;
    }

    @Override
    @NotNull
    public FileVisitResult preVisitDirectory(@NotNull Path path, @NotNull BasicFileAttributes basicFileAttributes) {
        Intrinsics.checkNotNullParameter(path, "dir");
        Intrinsics.checkNotNullParameter(basicFileAttributes, "attrs");
        PathNode pathNode = new PathNode(path, basicFileAttributes.fileKey(), this.directoryNode);
        this.entries.add(pathNode);
        FileVisitResult fileVisitResult = super.preVisitDirectory(path, basicFileAttributes);
        Intrinsics.checkNotNullExpressionValue((Object)fileVisitResult, "super.preVisitDirectory(dir, attrs)");
        return fileVisitResult;
    }

    @Override
    @NotNull
    public FileVisitResult visitFile(@NotNull Path path, @NotNull BasicFileAttributes basicFileAttributes) {
        Intrinsics.checkNotNullParameter(path, "file");
        Intrinsics.checkNotNullParameter(basicFileAttributes, "attrs");
        PathNode pathNode = new PathNode(path, null, this.directoryNode);
        this.entries.add(pathNode);
        FileVisitResult fileVisitResult = super.visitFile(path, basicFileAttributes);
        Intrinsics.checkNotNullExpressionValue((Object)fileVisitResult, "super.visitFile(file, attrs)");
        return fileVisitResult;
    }

    @Override
    public FileVisitResult preVisitDirectory(Object object, BasicFileAttributes basicFileAttributes) {
        return this.preVisitDirectory((Path)object, basicFileAttributes);
    }

    @Override
    public FileVisitResult visitFile(Object object, BasicFileAttributes basicFileAttributes) {
        return this.visitFile((Path)object, basicFileAttributes);
    }
}

