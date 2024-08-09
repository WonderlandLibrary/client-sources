/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.path;

import java.nio.file.FileSystemLoopException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.io.path.DirectoryEntriesReader;
import kotlin.io.path.ExperimentalPathApi;
import kotlin.io.path.LinkFollowing;
import kotlin.io.path.PathNode;
import kotlin.io.path.PathTreeWalkKt;
import kotlin.io.path.PathWalkOption;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010(\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\b\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015H\u0002J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015H\u0002J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015H\u0096\u0002JE\u0010\u0018\u001a\u00020\u0019*\b\u0012\u0004\u0012\u00020\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0018\u0010\u001f\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0!\u0012\u0004\u0012\u00020\u00190 H\u0082H\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\"R\u0014\u0010\b\u001a\u00020\t8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\t8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0014\u0010\u000e\u001a\u00020\t8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000bR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0013R\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006#"}, d2={"Lkotlin/io/path/PathTreeWalk;", "Lkotlin/sequences/Sequence;", "Ljava/nio/file/Path;", "start", "options", "", "Lkotlin/io/path/PathWalkOption;", "(Ljava/nio/file/Path;[Lkotlin/io/path/PathWalkOption;)V", "followLinks", "", "getFollowLinks", "()Z", "includeDirectories", "getIncludeDirectories", "isBFS", "linkOptions", "Ljava/nio/file/LinkOption;", "getLinkOptions", "()[Ljava/nio/file/LinkOption;", "[Lkotlin/io/path/PathWalkOption;", "bfsIterator", "", "dfsIterator", "iterator", "yieldIfNeeded", "", "Lkotlin/sequences/SequenceScope;", "node", "Lkotlin/io/path/PathNode;", "entriesReader", "Lkotlin/io/path/DirectoryEntriesReader;", "entriesAction", "Lkotlin/Function1;", "", "(Lkotlin/sequences/SequenceScope;Lkotlin/io/path/PathNode;Lkotlin/io/path/DirectoryEntriesReader;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib-jdk7"})
@ExperimentalPathApi
public final class PathTreeWalk
implements Sequence<Path> {
    @NotNull
    private final Path start;
    @NotNull
    private final PathWalkOption[] options;

    public PathTreeWalk(@NotNull Path path, @NotNull PathWalkOption[] pathWalkOptionArray) {
        Intrinsics.checkNotNullParameter(path, "start");
        Intrinsics.checkNotNullParameter(pathWalkOptionArray, "options");
        this.start = path;
        this.options = pathWalkOptionArray;
    }

    private final boolean getFollowLinks() {
        return ArraysKt.contains(this.options, PathWalkOption.FOLLOW_LINKS);
    }

    private final LinkOption[] getLinkOptions() {
        return LinkFollowing.INSTANCE.toLinkOptions(this.getFollowLinks());
    }

    private final boolean getIncludeDirectories() {
        return ArraysKt.contains(this.options, PathWalkOption.INCLUDE_DIRECTORIES);
    }

    private final boolean isBFS() {
        return ArraysKt.contains(this.options, PathWalkOption.BREADTH_FIRST);
    }

    @Override
    @NotNull
    public Iterator<Path> iterator() {
        return this.isBFS() ? this.bfsIterator() : this.dfsIterator();
    }

    private final Object yieldIfNeeded(SequenceScope<? super Path> sequenceScope, PathNode pathNode, DirectoryEntriesReader directoryEntriesReader, Function1<? super List<PathNode>, Unit> function1, Continuation<? super Unit> continuation) {
        Path path;
        boolean bl = false;
        Path path2 = path = pathNode.getPath();
        LinkOption[] linkOptionArray = PathTreeWalk.access$getLinkOptions(this);
        linkOptionArray = Arrays.copyOf(linkOptionArray, linkOptionArray.length);
        if (Files.isDirectory(path2, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
            if (PathTreeWalkKt.access$createsCycle(pathNode)) {
                throw new FileSystemLoopException(((Object)path).toString());
            }
            if (PathTreeWalk.access$getIncludeDirectories(this)) {
                InlineMarker.mark(0);
                sequenceScope.yield(path, continuation);
                InlineMarker.mark(1);
            }
            path2 = path;
            linkOptionArray = PathTreeWalk.access$getLinkOptions(this);
            linkOptionArray = Arrays.copyOf(linkOptionArray, linkOptionArray.length);
            if (Files.isDirectory(path2, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                function1.invoke(directoryEntriesReader.readEntries(pathNode));
            }
        } else {
            path2 = path;
            linkOptionArray = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
            if (Files.exists(path2, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                InlineMarker.mark(0);
                sequenceScope.yield(path, continuation);
                InlineMarker.mark(1);
                return Unit.INSTANCE;
            }
        }
        return Unit.INSTANCE;
    }

    private final Iterator<Path> dfsIterator() {
        return SequencesKt.iterator((Function2)new Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object>(this, null){
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            int label;
            private Object L$0;
            final PathTreeWalk this$0;
            {
                this.this$0 = pathTreeWalk;
                super(2, continuation);
            }

            /*
             * Unable to fully structure code
             * Could not resolve type clashes
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                block17: {
                    var17_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    block0 : switch (this.label) {
                        case 0: {
                            ResultKt.throwOnFailure(var1_1);
                            var2_3 = (SequenceScope)this.L$0;
                            var3_4 = new ArrayDeque();
                            var4_5 = new DirectoryEntriesReader(PathTreeWalk.access$getFollowLinks(this.this$0));
                            var5_6 = new PathNode(PathTreeWalk.access$getStart$p(this.this$0), PathTreeWalkKt.access$keyOf(PathTreeWalk.access$getStart$p(this.this$0), PathTreeWalk.access$getLinkOptions(this.this$0)), null);
                            var6_7 = this.this$0;
                            var7_8 = var2_3;
                            var8_9 = false;
                            var10_12 = var9_11 = var5_6.getPath();
                            var11_13 = PathTreeWalk.access$getLinkOptions((PathTreeWalk)var6_7);
                            var11_13 = Arrays.copyOf(var11_13, var11_13.length);
                            if (!Files.isDirectory((Path)var10_12, Arrays.copyOf(var11_13, var11_13.length))) break;
                            if (PathTreeWalkKt.access$createsCycle(var5_6)) {
                                throw new FileSystemLoopException(var9_11.toString());
                            }
                            if (PathTreeWalk.access$getIncludeDirectories((PathTreeWalk)var6_7)) {
                                this.L$0 = var2_3;
                                this.L$1 = var3_4;
                                this.L$2 = var4_5;
                                this.L$3 = var5_6;
                                this.L$4 = var6_7;
                                this.L$5 = var9_11;
                                this.label = 1;
                                v0 = var7_8.yield(var9_11, this);
                                if (v0 == var17_2) {
                                    return var17_2;
                                }
                            }
                            ** GOTO lbl40
                        }
                        case 1: {
                            var8_9 = false;
                            var9_11 = (Path)this.L$5;
                            var6_7 = (PathTreeWalk)this.L$4;
                            var5_6 = (PathNode)this.L$3;
                            var4_5 = (DirectoryEntriesReader)this.L$2;
                            var3_4 = (ArrayDeque)this.L$1;
                            var2_3 = (SequenceScope)this.L$0;
                            ResultKt.throwOnFailure(var1_1);
                            v0 = var1_1;
lbl40:
                            // 2 sources

                            var10_12 = var9_11;
                            var11_13 = PathTreeWalk.access$getLinkOptions((PathTreeWalk)var6_7);
                            if (Files.isDirectory((Path)var10_12, Arrays.copyOf(var11_13 = Arrays.copyOf(var11_13, var11_13.length), var11_13.length))) {
                                var12_15 = var4_5.readEntries(var5_6);
                                var13_16 = false;
                                var5_6.setContentIterator(var12_15.iterator());
                                var3_4.addLast(var5_6);
                            }
                            ** GOTO lbl67
                        }
                    }
                    var10_12 = var9_11;
                    var11_13 = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
                    if (Files.exists((Path)var10_12, Arrays.copyOf(var11_13, var11_13.length))) {
                        this.L$0 = var2_3;
                        this.L$1 = var3_4;
                        this.L$2 = var4_5;
                        this.label = 2;
                        v1 = var7_8.yield(var9_11, this);
                        if (v1 == var17_2) {
                            return var17_2;
                        }
                    }
                    ** GOTO lbl67
                    {
                        case 2: {
                            var8_9 = false;
                            var4_5 = (DirectoryEntriesReader)this.L$2;
                            var3_4 = (ArrayDeque)this.L$1;
                            var2_3 = (SequenceScope)this.L$0;
                            ResultKt.throwOnFailure(var1_1);
                            v1 = var1_1;
lbl67:
                            // 9 sources

                            while (((Collection)var3_4).isEmpty() == false) {
                                var6_7 = (PathNode)var3_4.last();
                                Intrinsics.checkNotNull(var6_7.getContentIterator());
                                if (!var7_8.hasNext()) break block0;
                                var8_10 = (PathNode)var7_8.next();
                                var9_11 = this.this$0;
                                var10_12 = var2_3;
                                var11_14 = false;
                                var12_15 = var8_10.getPath();
                                var13_17 /* !! */  = var12_15;
                                var14_18 = PathTreeWalk.access$getLinkOptions((PathTreeWalk)var9_11);
                                if (!Files.isDirectory(var13_17 /* !! */ , Arrays.copyOf(var14_18 = Arrays.copyOf(var14_18, var14_18.length), var14_18.length))) break block0;
                                if (PathTreeWalkKt.access$createsCycle(var8_10)) {
                                    throw new FileSystemLoopException(var12_15.toString());
                                }
                                if (PathTreeWalk.access$getIncludeDirectories((PathTreeWalk)var9_11)) {
                                    this.L$0 = var2_3;
                                    this.L$1 = var3_4;
                                    this.L$2 = var4_5;
                                    this.L$3 = var8_10;
                                    this.L$4 = var9_11;
                                    this.L$5 = var12_15;
                                    this.label = 3;
                                    v2 = var10_12.yield(var12_15, this);
                                    if (v2 == var17_2) {
                                        return var17_2;
                                    }
                                }
                                ** GOTO lbl104
                            }
                            break block17;
                        }
                        case 3: {
                            var11_14 = false;
                            var12_15 = (Path)this.L$5;
                            var9_11 = (PathTreeWalk)this.L$4;
                            var8_10 = (PathNode)this.L$3;
                            var4_5 = (DirectoryEntriesReader)this.L$2;
                            var3_4 = (ArrayDeque)this.L$1;
                            var2_3 = (SequenceScope)this.L$0;
                            ResultKt.throwOnFailure(var1_1);
                            v2 = var1_1;
lbl104:
                            // 2 sources

                            var13_17 /* !! */  = var12_15;
                            var14_18 = PathTreeWalk.access$getLinkOptions((PathTreeWalk)var9_11);
                            if (!Files.isDirectory(var13_17 /* !! */ , Arrays.copyOf(var14_18 = Arrays.copyOf(var14_18, var14_18.length), var14_18.length))) ** GOTO lbl67
                            var15_19 = var4_5.readEntries(var8_10);
                            var16_20 = false;
                            var8_10.setContentIterator(var15_19.iterator());
                            var3_4.addLast(var8_10);
                            ** GOTO lbl67
                        }
                    }
                    var13_17 /* !! */  = var12_15;
                    var14_18 = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
                    if (!Files.exists(var13_17 /* !! */ , Arrays.copyOf(var14_18, var14_18.length))) ** GOTO lbl67
                    this.L$0 = var2_3;
                    this.L$1 = var3_4;
                    this.L$2 = var4_5;
                    this.L$3 = null;
                    this.L$4 = null;
                    this.L$5 = null;
                    this.label = 4;
                    v3 = var10_12.yield(var12_15, this);
                    if (v3 != var17_2) ** GOTO lbl67
                    return var17_2;
                    {
                        case 4: {
                            var11_14 = false;
                            var4_5 = (DirectoryEntriesReader)this.L$2;
                            var3_4 = (ArrayDeque)this.L$1;
                            var2_3 = (SequenceScope)this.L$0;
                            ResultKt.throwOnFailure(var1_1);
                            v3 = var1_1;
                            ** GOTO lbl67
                        }
                    }
                    var3_4.removeLast();
                    ** GOTO lbl67
                }
                return Unit.INSTANCE;
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object object, @NotNull Continuation<?> continuation) {
                Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = object;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull SequenceScope<? super Path> sequenceScope, @Nullable Continuation<? super Unit> continuation) {
                return (this.create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            public Object invoke(Object object, Object object2) {
                return this.invoke((SequenceScope)object, (Continuation)object2);
            }
        });
    }

    private final Iterator<Path> bfsIterator() {
        return SequencesKt.iterator((Function2)new Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object>(this, null){
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            int label;
            private Object L$0;
            final PathTreeWalk this$0;
            {
                this.this$0 = pathTreeWalk;
                super(2, continuation);
            }

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                var14_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                block0 : switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(var1_1);
                        var2_3 = (SequenceScope)this.L$0;
                        var3_4 = new ArrayDeque();
                        var4_5 = new DirectoryEntriesReader(PathTreeWalk.access$getFollowLinks(this.this$0));
                        var3_4.addLast(new PathNode(PathTreeWalk.access$getStart$p(this.this$0), PathTreeWalkKt.access$keyOf(PathTreeWalk.access$getStart$p(this.this$0), PathTreeWalk.access$getLinkOptions(this.this$0)), null));
lbl9:
                        // 6 sources

                        while (((Collection)var3_4).isEmpty() == false) {
                            var5_6 = (PathNode)var3_4.removeFirst();
                            var6_7 = this.this$0;
                            var7_8 = var2_3;
                            var8_9 = false;
                            var10_11 = var9_10 = var5_6.getPath();
                            var11_12 = PathTreeWalk.access$getLinkOptions(var6_7);
                            if (!Files.isDirectory(var10_11, Arrays.copyOf(var11_12 = Arrays.copyOf(var11_12, var11_12.length), var11_12.length))) break block0;
                            if (PathTreeWalkKt.access$createsCycle(var5_6)) {
                                throw new FileSystemLoopException(var9_10.toString());
                            }
                            if (PathTreeWalk.access$getIncludeDirectories(var6_7)) {
                                this.L$0 = var2_3;
                                this.L$1 = var3_4;
                                this.L$2 = var4_5;
                                this.L$3 = var5_6;
                                this.L$4 = var6_7;
                                this.L$5 = var9_10;
                                this.label = 1;
                                v0 = var7_8.yield(var9_10, this);
                                if (v0 == var14_2) {
                                    return var14_2;
                                }
                            }
                            ** GOTO lbl42
                        }
                        break;
                    }
                    case 1: {
                        var8_9 = false;
                        var9_10 = (Path)this.L$5;
                        var6_7 = (PathTreeWalk)this.L$4;
                        var5_6 = (PathNode)this.L$3;
                        var4_5 = (DirectoryEntriesReader)this.L$2;
                        var3_4 = (ArrayDeque)this.L$1;
                        var2_3 = (SequenceScope)this.L$0;
                        ResultKt.throwOnFailure(var1_1);
                        v0 = var1_1;
lbl42:
                        // 2 sources

                        var10_11 = var9_10;
                        var11_12 = PathTreeWalk.access$getLinkOptions(var6_7);
                        if (!Files.isDirectory(var10_11, Arrays.copyOf(var11_12 = Arrays.copyOf(var11_12, var11_12.length), var11_12.length))) ** GOTO lbl9
                        var12_13 = var4_5.readEntries(var5_6);
                        var13_14 = false;
                        var3_4.addAll((Collection)var12_13);
                        ** GOTO lbl9
                    }
                }
                var10_11 = var9_10;
                var11_12 = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
                if (!Files.exists(var10_11, Arrays.copyOf(var11_12, var11_12.length))) ** GOTO lbl9
                this.L$0 = var2_3;
                this.L$1 = var3_4;
                this.L$2 = var4_5;
                this.L$3 = null;
                this.L$4 = null;
                this.L$5 = null;
                this.label = 2;
                v1 = var7_8.yield(var9_10, this);
                if (v1 != var14_2) ** GOTO lbl9
                return var14_2;
                {
                    case 2: {
                        var8_9 = false;
                        var4_5 = (DirectoryEntriesReader)this.L$2;
                        var3_4 = (ArrayDeque)this.L$1;
                        var2_3 = (SequenceScope)this.L$0;
                        ResultKt.throwOnFailure(var1_1);
                        v1 = var1_1;
                        ** GOTO lbl9
                    }
                }
                return Unit.INSTANCE;
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object object, @NotNull Continuation<?> continuation) {
                Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = object;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull SequenceScope<? super Path> sequenceScope, @Nullable Continuation<? super Unit> continuation) {
                return (this.create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            public Object invoke(Object object, Object object2) {
                return this.invoke((SequenceScope)object, (Continuation)object2);
            }
        });
    }

    public static final LinkOption[] access$getLinkOptions(PathTreeWalk pathTreeWalk) {
        return pathTreeWalk.getLinkOptions();
    }

    public static final boolean access$getIncludeDirectories(PathTreeWalk pathTreeWalk) {
        return pathTreeWalk.getIncludeDirectories();
    }

    public static final boolean access$getFollowLinks(PathTreeWalk pathTreeWalk) {
        return pathTreeWalk.getFollowLinks();
    }

    public static final Path access$getStart$p(PathTreeWalk pathTreeWalk) {
        return pathTreeWalk.start;
    }
}

