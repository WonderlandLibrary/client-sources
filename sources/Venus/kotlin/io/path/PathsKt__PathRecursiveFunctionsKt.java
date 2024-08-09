/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.path;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.path.CopyActionContext;
import kotlin.io.path.CopyActionResult;
import kotlin.io.path.DefaultCopyActionContext;
import kotlin.io.path.ExceptionsCollector;
import kotlin.io.path.ExperimentalPathApi;
import kotlin.io.path.FileVisitorBuilder;
import kotlin.io.path.LinkFollowing;
import kotlin.io.path.OnErrorResult;
import kotlin.io.path.PathsKt;
import kotlin.io.path.PathsKt__PathReadWriteKt;
import kotlin.io.path.PathsKt__PathRecursiveFunctionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.SpreadBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000v\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0082\b\u00a2\u0006\u0002\b\u0006\u001a\u001d\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u00a2\u0006\u0002\b\n\u001a\u001d\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u00a2\u0006\u0002\b\r\u001a&\u0010\u000e\u001a\u0004\u0018\u0001H\u000f\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0005H\u0082\b\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001aw\u0010\u0012\u001a\u00020\t*\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2Q\b\u0002\u0010\u0014\u001aK\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0013\u0012\u0017\u0012\u00150\u0019j\u0002`\u001a\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u001c0\u00152\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eH\u0007\u001a\u00b4\u0001\u0010\u0012\u001a\u00020\t*\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2Q\b\u0002\u0010\u0014\u001aK\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0013\u0012\u0017\u0012\u00150\u0019j\u0002`\u001a\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u001c0\u00152\u0006\u0010\u001d\u001a\u00020\u001e2C\b\u0002\u0010 \u001a=\u0012\u0004\u0012\u00020!\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\"0\u0015\u00a2\u0006\u0002\b#H\u0007\u001a\f\u0010$\u001a\u00020\u0001*\u00020\tH\u0007\u001a\u001b\u0010%\u001a\f\u0012\b\u0012\u00060\u0019j\u0002`\u001a0&*\u00020\tH\u0002\u00a2\u0006\u0002\b'\u001a'\u0010(\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\t0)2\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u00a2\u0006\u0002\b*\u001a'\u0010+\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\t0)2\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u00a2\u0006\u0002\b,\u001a5\u0010-\u001a\u00020\u001e*\b\u0012\u0004\u0012\u00020\t0)2\u0006\u0010.\u001a\u00020\t2\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020100\"\u000201H\u0002\u00a2\u0006\u0004\b2\u00103\u001a\u0011\u00104\u001a\u000205*\u00020\"H\u0003\u00a2\u0006\u0002\b6\u001a\u0011\u00104\u001a\u000205*\u00020\u001cH\u0003\u00a2\u0006\u0002\b6\u00a8\u00067"}, d2={"collectIfThrows", "", "collector", "Lkotlin/io/path/ExceptionsCollector;", "function", "Lkotlin/Function0;", "collectIfThrows$PathsKt__PathRecursiveFunctionsKt", "insecureEnterDirectory", "path", "Ljava/nio/file/Path;", "insecureEnterDirectory$PathsKt__PathRecursiveFunctionsKt", "insecureHandleEntry", "entry", "insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt", "tryIgnoreNoSuchFileException", "R", "tryIgnoreNoSuchFileException$PathsKt__PathRecursiveFunctionsKt", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "copyToRecursively", "target", "onError", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "source", "Ljava/lang/Exception;", "Lkotlin/Exception;", "exception", "Lkotlin/io/path/OnErrorResult;", "followLinks", "", "overwrite", "copyAction", "Lkotlin/io/path/CopyActionContext;", "Lkotlin/io/path/CopyActionResult;", "Lkotlin/ExtensionFunctionType;", "deleteRecursively", "deleteRecursivelyImpl", "", "deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt", "enterDirectory", "Ljava/nio/file/SecureDirectoryStream;", "enterDirectory$PathsKt__PathRecursiveFunctionsKt", "handleEntry", "handleEntry$PathsKt__PathRecursiveFunctionsKt", "isDirectory", "entryName", "options", "", "Ljava/nio/file/LinkOption;", "isDirectory$PathsKt__PathRecursiveFunctionsKt", "(Ljava/nio/file/SecureDirectoryStream;Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", "toFileVisitResult", "Ljava/nio/file/FileVisitResult;", "toFileVisitResult$PathsKt__PathRecursiveFunctionsKt", "kotlin-stdlib-jdk7"}, xs="kotlin/io/path/PathsKt")
@SourceDebugExtension(value={"SMAP\nPathRecursiveFunctions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PathRecursiveFunctions.kt\nkotlin/io/path/PathsKt__PathRecursiveFunctionsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,420:1\n336#1,2:424\n344#1:426\n344#1:427\n338#1,4:428\n336#1,2:432\n344#1:434\n338#1,4:435\n344#1:439\n336#1,6:440\n336#1,2:446\n344#1:448\n338#1,4:449\n1#2:421\n1855#3,2:422\n*S KotlinDebug\n*F\n+ 1 PathRecursiveFunctions.kt\nkotlin/io/path/PathsKt__PathRecursiveFunctionsKt\n*L\n352#1:424,2\n361#1:426\n364#1:427\n352#1:428,4\n372#1:432,2\n373#1:434\n372#1:435,4\n384#1:439\n392#1:440,6\n410#1:446,2\n411#1:448\n410#1:449,4\n274#1:422,2\n*E\n"})
class PathsKt__PathRecursiveFunctionsKt
extends PathsKt__PathReadWriteKt {
    @ExperimentalPathApi
    @SinceKotlin(version="1.8")
    @NotNull
    public static final Path copyToRecursively(@NotNull Path path, @NotNull Path path2, @NotNull Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> function3, boolean bl, boolean bl2) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "target");
        Intrinsics.checkNotNullParameter(function3, "onError");
        return bl2 ? PathsKt.copyToRecursively(path, path2, function3, bl, (Function3<? super CopyActionContext, ? super Path, ? super Path, ? extends CopyActionResult>)new Function3<CopyActionContext, Path, Path, CopyActionResult>(bl){
            final boolean $followLinks;
            {
                this.$followLinks = bl;
                super(3);
            }

            @NotNull
            public final CopyActionResult invoke(@NotNull CopyActionContext copyActionContext, @NotNull Path path, @NotNull Path path2) {
                Intrinsics.checkNotNullParameter(copyActionContext, "$this$copyToRecursively");
                Intrinsics.checkNotNullParameter(path, "src");
                Intrinsics.checkNotNullParameter(path2, "dst");
                LinkOption[] linkOptionArray = LinkFollowing.INSTANCE.toLinkOptions(this.$followLinks);
                Path path3 = path2;
                Object object = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
                boolean bl = Files.isDirectory(path3, Arrays.copyOf(object, ((LinkOption[])object).length));
                LinkOption[] linkOptionArray2 = Arrays.copyOf(linkOptionArray, linkOptionArray.length);
                boolean bl2 = Files.isDirectory(path, Arrays.copyOf(linkOptionArray2, linkOptionArray2.length));
                if (!bl2 || !bl) {
                    if (bl) {
                        PathsKt.deleteRecursively(path2);
                    }
                    object = path;
                    CopyOption[] copyOptionArray = new SpreadBuilder(2);
                    copyOptionArray.addSpread(linkOptionArray);
                    copyOptionArray.add(StandardCopyOption.REPLACE_EXISTING);
                    copyOptionArray = (CopyOption[])copyOptionArray.toArray(new CopyOption[copyOptionArray.size()]);
                    Intrinsics.checkNotNullExpressionValue(Files.copy((Path)object, path2, Arrays.copyOf(copyOptionArray, copyOptionArray.length)), "copy(this, target, *options)");
                }
                return CopyActionResult.CONTINUE;
            }

            public Object invoke(Object object, Object object2, Object object3) {
                return this.invoke((CopyActionContext)object, (Path)object2, (Path)object3);
            }
        }) : PathsKt.copyToRecursively$default(path, path2, function3, bl, null, 8, null);
    }

    public static Path copyToRecursively$default(Path path, Path path2, Function3 function3, boolean bl, boolean bl2, int n, Object object) {
        if ((n & 2) != 0) {
            function3 = copyToRecursively.1.INSTANCE;
        }
        return PathsKt.copyToRecursively(path, path2, (Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult>)function3, bl, bl2);
    }

    @ExperimentalPathApi
    @SinceKotlin(version="1.8")
    @NotNull
    public static final Path copyToRecursively(@NotNull Path path, @NotNull Path path2, @NotNull Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> function3, boolean bl, @NotNull Function3<? super CopyActionContext, ? super Path, ? super Path, ? extends CopyActionResult> function32) {
        block11: {
            boolean bl2;
            boolean bl3;
            boolean bl4;
            block12: {
                Intrinsics.checkNotNullParameter(path, "<this>");
                Intrinsics.checkNotNullParameter(path2, "target");
                Intrinsics.checkNotNullParameter(function3, "onError");
                Intrinsics.checkNotNullParameter(function32, "copyAction");
                Path path3 = path;
                LinkOption[] linkOptionArray = LinkFollowing.INSTANCE.toLinkOptions(bl);
                linkOptionArray = Arrays.copyOf(linkOptionArray, linkOptionArray.length);
                if (!Files.exists(path3, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                    throw new NoSuchFileException(((Object)path).toString(), ((Object)path2).toString(), "The source file doesn't exist.");
                }
                LinkOption[] linkOptionArray2 = new LinkOption[]{};
                if (!Files.exists(path, Arrays.copyOf(linkOptionArray2, linkOptionArray2.length)) || !bl && Files.isSymbolicLink(path)) break block11;
                LinkOption[] linkOptionArray3 = new LinkOption[]{};
                boolean bl5 = bl4 = Files.exists(path2, Arrays.copyOf(linkOptionArray3, linkOptionArray3.length)) && !Files.isSymbolicLink(path2);
                if (!bl4) break block12;
                if (Files.isSameFile(path, path2)) break block11;
            }
            if (!Intrinsics.areEqual(path.getFileSystem(), path2.getFileSystem())) {
                bl3 = false;
            } else if (bl4) {
                bl3 = path2.toRealPath(new LinkOption[0]).startsWith(path.toRealPath(new LinkOption[0]));
            } else {
                Path path4 = path2.getParent();
                if (path4 != null) {
                    Path path5 = path4;
                    boolean bl6 = false;
                    LinkOption[] linkOptionArray = new LinkOption[]{};
                    bl3 = Files.exists(path5, Arrays.copyOf(linkOptionArray, linkOptionArray.length)) && path5.toRealPath(new LinkOption[0]).startsWith(path.toRealPath(new LinkOption[0]));
                } else {
                    bl3 = bl2 = false;
                }
            }
            if (bl2) {
                throw new FileSystemException(((Object)path).toString(), ((Object)path2).toString(), "Recursively copying a directory into its subdirectory is prohibited.");
            }
        }
        PathsKt.visitFileTree$default(path, 0, bl, new Function1<FileVisitorBuilder, Unit>(function32, path, path2, function3){
            final Function3<CopyActionContext, Path, Path, CopyActionResult> $copyAction;
            final Path $this_copyToRecursively;
            final Path $target;
            final Function3<Path, Path, Exception, OnErrorResult> $onError;
            {
                this.$copyAction = function3;
                this.$this_copyToRecursively = path;
                this.$target = path2;
                this.$onError = function32;
                super(1);
            }

            public final void invoke(@NotNull FileVisitorBuilder fileVisitorBuilder) {
                Intrinsics.checkNotNullParameter(fileVisitorBuilder, "$this$visitFileTree");
                fileVisitorBuilder.onPreVisitDirectory((Function2<? super Path, ? super BasicFileAttributes, ? extends FileVisitResult>)new Function2<Path, BasicFileAttributes, FileVisitResult>(this.$copyAction, this.$this_copyToRecursively, this.$target, this.$onError){
                    final Function3<CopyActionContext, Path, Path, CopyActionResult> $copyAction;
                    final Path $this_copyToRecursively;
                    final Path $target;
                    final Function3<Path, Path, Exception, OnErrorResult> $onError;
                    {
                        this.$copyAction = function3;
                        this.$this_copyToRecursively = path;
                        this.$target = path2;
                        this.$onError = function32;
                        super(2, Intrinsics.Kotlin.class, "copy", "copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/Path;Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/attribute/BasicFileAttributes;)Ljava/nio/file/FileVisitResult;", 0);
                    }

                    @NotNull
                    public final FileVisitResult invoke(@NotNull Path path, @NotNull BasicFileAttributes basicFileAttributes) {
                        Intrinsics.checkNotNullParameter(path, "p0");
                        Intrinsics.checkNotNullParameter(basicFileAttributes, "p1");
                        return PathsKt__PathRecursiveFunctionsKt.access$copyToRecursively$copy(this.$copyAction, this.$this_copyToRecursively, this.$target, this.$onError, path, basicFileAttributes);
                    }

                    public Object invoke(Object object, Object object2) {
                        return this.invoke((Path)object, (BasicFileAttributes)object2);
                    }
                });
                fileVisitorBuilder.onVisitFile((Function2<? super Path, ? super BasicFileAttributes, ? extends FileVisitResult>)new Function2<Path, BasicFileAttributes, FileVisitResult>(this.$copyAction, this.$this_copyToRecursively, this.$target, this.$onError){
                    final Function3<CopyActionContext, Path, Path, CopyActionResult> $copyAction;
                    final Path $this_copyToRecursively;
                    final Path $target;
                    final Function3<Path, Path, Exception, OnErrorResult> $onError;
                    {
                        this.$copyAction = function3;
                        this.$this_copyToRecursively = path;
                        this.$target = path2;
                        this.$onError = function32;
                        super(2, Intrinsics.Kotlin.class, "copy", "copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/Path;Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/attribute/BasicFileAttributes;)Ljava/nio/file/FileVisitResult;", 0);
                    }

                    @NotNull
                    public final FileVisitResult invoke(@NotNull Path path, @NotNull BasicFileAttributes basicFileAttributes) {
                        Intrinsics.checkNotNullParameter(path, "p0");
                        Intrinsics.checkNotNullParameter(basicFileAttributes, "p1");
                        return PathsKt__PathRecursiveFunctionsKt.access$copyToRecursively$copy(this.$copyAction, this.$this_copyToRecursively, this.$target, this.$onError, path, basicFileAttributes);
                    }

                    public Object invoke(Object object, Object object2) {
                        return this.invoke((Path)object, (BasicFileAttributes)object2);
                    }
                });
                fileVisitorBuilder.onVisitFileFailed((Function2<? super Path, ? super IOException, ? extends FileVisitResult>)new Function2<Path, Exception, FileVisitResult>(this.$onError, this.$this_copyToRecursively, this.$target){
                    final Function3<Path, Path, Exception, OnErrorResult> $onError;
                    final Path $this_copyToRecursively;
                    final Path $target;
                    {
                        this.$onError = function3;
                        this.$this_copyToRecursively = path;
                        this.$target = path2;
                        super(2, Intrinsics.Kotlin.class, "error", "copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/lang/Exception;)Ljava/nio/file/FileVisitResult;", 0);
                    }

                    @NotNull
                    public final FileVisitResult invoke(@NotNull Path path, @NotNull Exception exception) {
                        Intrinsics.checkNotNullParameter(path, "p0");
                        Intrinsics.checkNotNullParameter(exception, "p1");
                        return PathsKt__PathRecursiveFunctionsKt.access$copyToRecursively$error(this.$onError, this.$this_copyToRecursively, this.$target, path, exception);
                    }

                    public Object invoke(Object object, Object object2) {
                        return this.invoke((Path)object, (Exception)object2);
                    }
                });
                fileVisitorBuilder.onPostVisitDirectory((Function2<? super Path, ? super IOException, ? extends FileVisitResult>)new Function2<Path, IOException, FileVisitResult>(this.$onError, this.$this_copyToRecursively, this.$target){
                    final Function3<Path, Path, Exception, OnErrorResult> $onError;
                    final Path $this_copyToRecursively;
                    final Path $target;
                    {
                        this.$onError = function3;
                        this.$this_copyToRecursively = path;
                        this.$target = path2;
                        super(2);
                    }

                    @NotNull
                    public final FileVisitResult invoke(@NotNull Path path, @Nullable IOException iOException) {
                        Intrinsics.checkNotNullParameter(path, "directory");
                        return iOException == null ? FileVisitResult.CONTINUE : PathsKt__PathRecursiveFunctionsKt.access$copyToRecursively$error(this.$onError, this.$this_copyToRecursively, this.$target, path, iOException);
                    }

                    public Object invoke(Object object, Object object2) {
                        return this.invoke((Path)object, (IOException)object2);
                    }
                });
            }

            public Object invoke(Object object) {
                this.invoke((FileVisitorBuilder)object);
                return Unit.INSTANCE;
            }
        }, 1, null);
        return path2;
    }

    public static Path copyToRecursively$default(Path path, Path path2, Function3 function3, boolean bl, Function3 function32, int n, Object object) {
        if ((n & 2) != 0) {
            function3 = copyToRecursively.3.INSTANCE;
        }
        if ((n & 8) != 0) {
            function32 = new Function3<CopyActionContext, Path, Path, CopyActionResult>(bl){
                final boolean $followLinks;
                {
                    this.$followLinks = bl;
                    super(3);
                }

                @NotNull
                public final CopyActionResult invoke(@NotNull CopyActionContext copyActionContext, @NotNull Path path, @NotNull Path path2) {
                    Intrinsics.checkNotNullParameter(copyActionContext, "$this$null");
                    Intrinsics.checkNotNullParameter(path, "src");
                    Intrinsics.checkNotNullParameter(path2, "dst");
                    return copyActionContext.copyToIgnoringExistingDirectory(path, path2, this.$followLinks);
                }

                public Object invoke(Object object, Object object2, Object object3) {
                    return this.invoke((CopyActionContext)object, (Path)object2, (Path)object3);
                }
            };
        }
        return PathsKt.copyToRecursively(path, path2, (Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult>)function3, bl, function32);
    }

    @ExperimentalPathApi
    private static final FileVisitResult toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(CopyActionResult copyActionResult) {
        FileVisitResult fileVisitResult;
        switch (WhenMappings.$EnumSwitchMapping$0[copyActionResult.ordinal()]) {
            case 1: {
                fileVisitResult = FileVisitResult.CONTINUE;
                break;
            }
            case 2: {
                fileVisitResult = FileVisitResult.TERMINATE;
                break;
            }
            case 3: {
                fileVisitResult = FileVisitResult.SKIP_SUBTREE;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return fileVisitResult;
    }

    @ExperimentalPathApi
    private static final FileVisitResult toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(OnErrorResult onErrorResult) {
        FileVisitResult fileVisitResult;
        switch (WhenMappings.$EnumSwitchMapping$1[onErrorResult.ordinal()]) {
            case 1: {
                fileVisitResult = FileVisitResult.TERMINATE;
                break;
            }
            case 2: {
                fileVisitResult = FileVisitResult.SKIP_SUBTREE;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return fileVisitResult;
    }

    @ExperimentalPathApi
    @SinceKotlin(version="1.8")
    public static final void deleteRecursively(@NotNull Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        List<Exception> list = PathsKt__PathRecursiveFunctionsKt.deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt(path);
        if (!((Collection)list).isEmpty()) {
            FileSystemException fileSystemException;
            FileSystemException fileSystemException2 = fileSystemException = new FileSystemException("Failed to delete one or more files. See suppressed exceptions for details.");
            boolean bl = false;
            Iterable iterable = list;
            boolean bl2 = false;
            for (Object t : iterable) {
                Exception exception = (Exception)t;
                boolean bl3 = false;
                ExceptionsKt.addSuppressed(fileSystemException2, exception);
            }
            throw (Throwable)fileSystemException;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static final List<Exception> deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt(Path path) {
        ExceptionsCollector exceptionsCollector = new ExceptionsCollector(0, 1, null);
        boolean bl = false;
        bl = true;
        Path path2 = path.getParent();
        if (path2 != null) {
            DirectoryStream<Path> directoryStream;
            Closeable closeable;
            Path path3 = path2;
            boolean bl2 = false;
            try {
                closeable = Files.newDirectoryStream(path3);
            } catch (Throwable throwable) {
                closeable = null;
            }
            DirectoryStream<Path> directoryStream2 = directoryStream = closeable;
            if (directoryStream2 != null) {
                closeable = directoryStream2;
                Throwable throwable = null;
                try {
                    Object object = closeable;
                    boolean bl3 = false;
                    if (object instanceof SecureDirectoryStream) {
                        bl = false;
                        exceptionsCollector.setPath(path3);
                        SecureDirectoryStream secureDirectoryStream = (SecureDirectoryStream)object;
                        Path path4 = path.getFileName();
                        Intrinsics.checkNotNullExpressionValue(path4, "this.fileName");
                        PathsKt__PathRecursiveFunctionsKt.handleEntry$PathsKt__PathRecursiveFunctionsKt(secureDirectoryStream, path4, exceptionsCollector);
                    }
                    object = Unit.INSTANCE;
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                } finally {
                    CloseableKt.closeFinally(closeable, throwable);
                }
            }
        }
        if (bl) {
            PathsKt__PathRecursiveFunctionsKt.insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt(path, exceptionsCollector);
        }
        return exceptionsCollector.getCollectedExceptions();
    }

    private static final void collectIfThrows$PathsKt__PathRecursiveFunctionsKt(ExceptionsCollector exceptionsCollector, Function0<Unit> function0) {
        boolean bl = false;
        try {
            function0.invoke();
        } catch (Exception exception) {
            exceptionsCollector.collect(exception);
        }
    }

    private static final <R> R tryIgnoreNoSuchFileException$PathsKt__PathRecursiveFunctionsKt(Function0<? extends R> function0) {
        R r;
        boolean bl = false;
        try {
            r = function0.invoke();
        } catch (NoSuchFileException noSuchFileException) {
            r = null;
        }
        return r;
    }

    private static final void handleEntry$PathsKt__PathRecursiveFunctionsKt(SecureDirectoryStream<Path> secureDirectoryStream, Path path, ExceptionsCollector exceptionsCollector) {
        exceptionsCollector.enterEntry(path);
        boolean bl = false;
        try {
            boolean bl2 = false;
            LinkOption[] linkOptionArray = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
            if (PathsKt__PathRecursiveFunctionsKt.isDirectory$PathsKt__PathRecursiveFunctionsKt(secureDirectoryStream, path, linkOptionArray)) {
                int n = exceptionsCollector.getTotalExceptions();
                PathsKt__PathRecursiveFunctionsKt.enterDirectory$PathsKt__PathRecursiveFunctionsKt(secureDirectoryStream, path, exceptionsCollector);
                if (n == exceptionsCollector.getTotalExceptions()) {
                    boolean bl3 = false;
                    try {
                        boolean bl4 = false;
                        secureDirectoryStream.deleteDirectory(path);
                        Unit unit = Unit.INSTANCE;
                    } catch (NoSuchFileException noSuchFileException) {
                        Object var8_14 = null;
                    }
                }
            } else {
                boolean bl5 = false;
                try {
                    boolean bl6 = false;
                    secureDirectoryStream.deleteFile(path);
                    Unit unit = Unit.INSTANCE;
                } catch (NoSuchFileException noSuchFileException) {
                    Object var7_12 = null;
                }
            }
        } catch (Exception exception) {
            exceptionsCollector.collect(exception);
        }
        exceptionsCollector.exitEntry(path);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static final void enterDirectory$PathsKt__PathRecursiveFunctionsKt(SecureDirectoryStream<Path> secureDirectoryStream, Path path, ExceptionsCollector exceptionsCollector) {
        block10: {
            boolean bl = false;
            try {
                SecureDirectoryStream<Path> secureDirectoryStream2;
                Object object;
                boolean bl2 = false;
                boolean bl3 = false;
                try {
                    boolean bl4 = false;
                    object = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
                    secureDirectoryStream2 = secureDirectoryStream.newDirectoryStream(path, (LinkOption[])object);
                } catch (NoSuchFileException noSuchFileException) {
                    secureDirectoryStream2 = null;
                }
                SecureDirectoryStream<Path> secureDirectoryStream3 = secureDirectoryStream2;
                if (secureDirectoryStream3 == null) break block10;
                Closeable closeable = secureDirectoryStream3;
                Throwable throwable = null;
                try {
                    object = (SecureDirectoryStream)closeable;
                    boolean bl5 = false;
                    Iterator iterator2 = object.iterator();
                    while (iterator2.hasNext()) {
                        Path path2 = (Path)iterator2.next();
                        Path path3 = path2.getFileName();
                        Intrinsics.checkNotNullExpressionValue(path3, "entry.fileName");
                        PathsKt__PathRecursiveFunctionsKt.handleEntry$PathsKt__PathRecursiveFunctionsKt((SecureDirectoryStream<Path>)object, path3, exceptionsCollector);
                    }
                    object = Unit.INSTANCE;
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                } finally {
                    CloseableKt.closeFinally(closeable, throwable);
                }
            } catch (Exception exception) {
                exceptionsCollector.collect(exception);
            }
        }
    }

    private static final boolean isDirectory$PathsKt__PathRecursiveFunctionsKt(SecureDirectoryStream<Path> secureDirectoryStream, Path path, LinkOption ... linkOptionArray) {
        Boolean bl;
        boolean bl2 = false;
        try {
            boolean bl3 = false;
            bl = secureDirectoryStream.getFileAttributeView(path, BasicFileAttributeView.class, Arrays.copyOf(linkOptionArray, linkOptionArray.length)).readAttributes().isDirectory();
        } catch (NoSuchFileException noSuchFileException) {
            bl = null;
        }
        Boolean bl4 = bl;
        return bl4 != null ? bl4 : false;
    }

    private static final void insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt(Path path, ExceptionsCollector exceptionsCollector) {
        boolean bl = false;
        try {
            boolean bl2 = false;
            Path path2 = path;
            LinkOption[] linkOptionArray = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
            if (Files.isDirectory(path2, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                int n = exceptionsCollector.getTotalExceptions();
                PathsKt__PathRecursiveFunctionsKt.insecureEnterDirectory$PathsKt__PathRecursiveFunctionsKt(path, exceptionsCollector);
                if (n == exceptionsCollector.getTotalExceptions()) {
                    Files.deleteIfExists(path);
                }
            } else {
                Files.deleteIfExists(path);
            }
        } catch (Exception exception) {
            exceptionsCollector.collect(exception);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static final void insecureEnterDirectory$PathsKt__PathRecursiveFunctionsKt(Path path, ExceptionsCollector exceptionsCollector) {
        block10: {
            boolean bl = false;
            try {
                Object object;
                boolean bl2 = false;
                boolean bl3 = false;
                try {
                    boolean bl4 = false;
                    object = Files.newDirectoryStream(path);
                } catch (NoSuchFileException noSuchFileException) {
                    object = null;
                }
                DirectoryStream directoryStream = object;
                if (directoryStream == null) break block10;
                Closeable closeable = directoryStream;
                Throwable throwable = null;
                try {
                    object = (DirectoryStream)closeable;
                    boolean bl5 = false;
                    Iterator iterator2 = object.iterator();
                    while (iterator2.hasNext()) {
                        Path path2 = (Path)iterator2.next();
                        Intrinsics.checkNotNullExpressionValue(path2, "entry");
                        PathsKt__PathRecursiveFunctionsKt.insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt(path2, exceptionsCollector);
                    }
                    object = Unit.INSTANCE;
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                } finally {
                    CloseableKt.closeFinally(closeable, throwable);
                }
            } catch (Exception exception) {
                exceptionsCollector.collect(exception);
            }
        }
    }

    private static final Path copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt(Path path, Path path2, Path path3) {
        Path path4 = PathsKt.relativeTo(path3, path);
        Path path5 = path2.resolve(((Object)path4).toString());
        Intrinsics.checkNotNullExpressionValue(path5, "target.resolve(relativePath.pathString)");
        return path5;
    }

    private static final FileVisitResult copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> function3, Path path, Path path2, Path path3, Exception exception) {
        return PathsKt__PathRecursiveFunctionsKt.toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(function3.invoke(path3, PathsKt__PathRecursiveFunctionsKt.copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt(path, path2, path3), exception));
    }

    private static final FileVisitResult copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(Function3<? super CopyActionContext, ? super Path, ? super Path, ? extends CopyActionResult> function3, Path path, Path path2, Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> function32, Path path3, BasicFileAttributes basicFileAttributes) {
        FileVisitResult fileVisitResult;
        try {
            fileVisitResult = PathsKt__PathRecursiveFunctionsKt.toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(function3.invoke(DefaultCopyActionContext.INSTANCE, path3, PathsKt__PathRecursiveFunctionsKt.copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt(path, path2, path3)));
        } catch (Exception exception) {
            fileVisitResult = PathsKt__PathRecursiveFunctionsKt.copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(function32, path, path2, path3, exception);
        }
        return fileVisitResult;
    }

    public static final FileVisitResult access$copyToRecursively$copy(Function3 function3, Path path, Path path2, Function3 function32, Path path3, BasicFileAttributes basicFileAttributes) {
        return PathsKt__PathRecursiveFunctionsKt.copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(function3, path, path2, function32, path3, basicFileAttributes);
    }

    public static final FileVisitResult access$copyToRecursively$error(Function3 function3, Path path, Path path2, Path path3, Exception exception) {
        return PathsKt__PathRecursiveFunctionsKt.copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(function3, path, path2, path3, exception);
    }

    @Metadata(mv={1, 9, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0;
        public static final int[] $EnumSwitchMapping$1;

        static {
            int[] nArray = new int[CopyActionResult.values().length];
            try {
                nArray[CopyActionResult.CONTINUE.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[CopyActionResult.TERMINATE.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[CopyActionResult.SKIP_SUBTREE.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
            nArray = new int[OnErrorResult.values().length];
            try {
                nArray[OnErrorResult.TERMINATE.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[OnErrorResult.SKIP_SUBTREE.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$1 = nArray;
        }
    }
}

