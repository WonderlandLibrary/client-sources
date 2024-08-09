/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.path;

import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.io.path.CopyActionContext;
import kotlin.io.path.CopyActionResult;
import kotlin.io.path.ExperimentalPathApi;
import kotlin.io.path.LinkFollowing;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u00c3\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2={"Lkotlin/io/path/DefaultCopyActionContext;", "Lkotlin/io/path/CopyActionContext;", "()V", "copyToIgnoringExistingDirectory", "Lkotlin/io/path/CopyActionResult;", "Ljava/nio/file/Path;", "target", "followLinks", "", "kotlin-stdlib-jdk7"})
@ExperimentalPathApi
final class DefaultCopyActionContext
implements CopyActionContext {
    @NotNull
    public static final DefaultCopyActionContext INSTANCE = new DefaultCopyActionContext();

    private DefaultCopyActionContext() {
    }

    @Override
    @NotNull
    public CopyActionResult copyToIgnoringExistingDirectory(@NotNull Path path, @NotNull Path path2, boolean bl) {
        block3: {
            LinkOption[] linkOptionArray;
            block2: {
                Intrinsics.checkNotNullParameter(path, "<this>");
                Intrinsics.checkNotNullParameter(path2, "target");
                linkOptionArray = LinkFollowing.INSTANCE.toLinkOptions(bl);
                LinkOption[] linkOptionArray2 = Arrays.copyOf(linkOptionArray, linkOptionArray.length);
                if (!Files.isDirectory(path, Arrays.copyOf(linkOptionArray2, linkOptionArray2.length))) break block2;
                Path path3 = path2;
                LinkOption[] linkOptionArray3 = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
                if (Files.isDirectory(path3, Arrays.copyOf(linkOptionArray3, linkOptionArray3.length))) break block3;
            }
            CopyOption[] copyOptionArray = Arrays.copyOf(linkOptionArray, linkOptionArray.length);
            Intrinsics.checkNotNullExpressionValue(Files.copy(path, path2, Arrays.copyOf(copyOptionArray, copyOptionArray.length)), "copy(this, target, *options)");
        }
        return CopyActionResult.CONTINUE;
    }
}

