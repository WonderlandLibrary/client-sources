/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.io.path.PathNode;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000$\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a%\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002\u00a2\u0006\u0002\u0010\u0007\u001a\f\u0010\b\u001a\u00020\t*\u00020\nH\u0002\u00a8\u0006\u000b"}, d2={"keyOf", "", "path", "Ljava/nio/file/Path;", "linkOptions", "", "Ljava/nio/file/LinkOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/lang/Object;", "createsCycle", "", "Lkotlin/io/path/PathNode;", "kotlin-stdlib-jdk7"})
public final class PathTreeWalkKt {
    private static final Object keyOf(Path path, LinkOption[] linkOptionArray) {
        Object object;
        try {
            object = path;
            LinkOption[] linkOptionArray2 = Arrays.copyOf(linkOptionArray, linkOptionArray.length);
            BasicFileAttributes basicFileAttributes = Files.readAttributes((Path)object, BasicFileAttributes.class, Arrays.copyOf(linkOptionArray2, linkOptionArray2.length));
            Intrinsics.checkNotNullExpressionValue(basicFileAttributes, "readAttributes(this, A::class.java, *options)");
            object = basicFileAttributes.fileKey();
        } catch (Throwable throwable) {
            object = null;
        }
        return object;
    }

    private static final boolean createsCycle(PathNode pathNode) {
        for (PathNode pathNode2 = pathNode.getParent(); pathNode2 != null; pathNode2 = pathNode2.getParent()) {
            if (pathNode2.getKey() != null && pathNode.getKey() != null) {
                if (!Intrinsics.areEqual(pathNode2.getKey(), pathNode.getKey())) continue;
                return false;
            }
            try {
                if (!Files.isSameFile(pathNode2.getPath(), pathNode.getPath())) continue;
                return false;
            } catch (IOException iOException) {
                continue;
            } catch (SecurityException securityException) {
                // empty catch block
            }
        }
        return true;
    }

    public static final boolean access$createsCycle(PathNode pathNode) {
        return PathTreeWalkKt.createsCycle(pathNode);
    }

    public static final Object access$keyOf(Path path, LinkOption[] linkOptionArray) {
        return PathTreeWalkKt.keyOf(path, linkOptionArray);
    }
}

