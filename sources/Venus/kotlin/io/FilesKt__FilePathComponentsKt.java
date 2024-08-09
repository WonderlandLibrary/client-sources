/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilePathComponents;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000$\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\u000b\u001a\u00020\f*\u00020\bH\u0002\u00a2\u0006\u0002\b\r\u001a\u001c\u0010\u000e\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\fH\u0000\u001a\f\u0010\u0011\u001a\u00020\u0012*\u00020\u0002H\u0000\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0018\u0010\u0004\u001a\u00020\u0002*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\"\u0018\u0010\u0007\u001a\u00020\b*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0013"}, d2={"isRooted", "", "Ljava/io/File;", "(Ljava/io/File;)Z", "root", "getRoot", "(Ljava/io/File;)Ljava/io/File;", "rootName", "", "getRootName", "(Ljava/io/File;)Ljava/lang/String;", "getRootLength", "", "getRootLength$FilesKt__FilePathComponentsKt", "subPath", "beginIndex", "endIndex", "toComponents", "Lkotlin/io/FilePathComponents;", "kotlin-stdlib"}, xs="kotlin/io/FilesKt")
@SourceDebugExtension(value={"SMAP\nFilePathComponents.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FilePathComponents.kt\nkotlin/io/FilesKt__FilePathComponentsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,148:1\n1549#2:149\n1620#2,3:150\n*S KotlinDebug\n*F\n+ 1 FilePathComponents.kt\nkotlin/io/FilesKt__FilePathComponentsKt\n*L\n133#1:149\n133#1:150,3\n*E\n"})
class FilesKt__FilePathComponentsKt {
    private static final int getRootLength$FilesKt__FilePathComponentsKt(String string) {
        int n = StringsKt.indexOf$default((CharSequence)string, File.separatorChar, 0, false, 4, null);
        if (n == 0) {
            if (string.length() > 1 && string.charAt(1) == File.separatorChar && (n = StringsKt.indexOf$default((CharSequence)string, File.separatorChar, 2, false, 4, null)) >= 0) {
                if ((n = StringsKt.indexOf$default((CharSequence)string, File.separatorChar, n + 1, false, 4, null)) >= 0) {
                    return n + 1;
                }
                return string.length();
            }
            return 0;
        }
        if (n > 0 && string.charAt(n - 1) == ':') {
            return ++n;
        }
        if (n == -1 && StringsKt.endsWith$default((CharSequence)string, ':', false, 2, null)) {
            return string.length();
        }
        return 1;
    }

    @NotNull
    public static final String getRootName(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        String string = file.getPath();
        Intrinsics.checkNotNullExpressionValue(string, "path");
        String string2 = string;
        int n = 0;
        String string3 = file.getPath();
        Intrinsics.checkNotNullExpressionValue(string3, "path");
        int n2 = FilesKt__FilePathComponentsKt.getRootLength$FilesKt__FilePathComponentsKt(string3);
        String string4 = string2.substring(n, n2);
        Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        return string4;
    }

    @NotNull
    public static final File getRoot(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return new File(FilesKt.getRootName(file));
    }

    public static final boolean isRooted(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        String string = file.getPath();
        Intrinsics.checkNotNullExpressionValue(string, "path");
        return FilesKt__FilePathComponentsKt.getRootLength$FilesKt__FilePathComponentsKt(string) > 0;
    }

    @NotNull
    public static final FilePathComponents toComponents(@NotNull File file) {
        List list;
        Intrinsics.checkNotNullParameter(file, "<this>");
        String string = file.getPath();
        Intrinsics.checkNotNullExpressionValue(string, "path");
        int n = FilesKt__FilePathComponentsKt.getRootLength$FilesKt__FilePathComponentsKt(string);
        String string2 = string;
        int n2 = 0;
        String string3 = string2.substring(n2, n);
        Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        String string4 = string3;
        String string5 = string.substring(n);
        Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String).substring(startIndex)");
        string2 = string5;
        if (((CharSequence)string2).length() == 0) {
            list = CollectionsKt.emptyList();
        } else {
            Object object = new char[]{File.separatorChar};
            object = StringsKt.split$default((CharSequence)string2, object, false, 0, 6, null);
            boolean bl = false;
            char[] cArray = object;
            Collection collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(object, 10));
            boolean bl2 = false;
            Iterator iterator2 = cArray.iterator();
            while (iterator2.hasNext()) {
                Object t = iterator2.next();
                String string6 = (String)t;
                Collection collection2 = collection;
                boolean bl3 = false;
                collection2.add(new File(string6));
            }
            list = (List)collection;
        }
        List list2 = list;
        return new FilePathComponents(new File(string4), list2);
    }

    @NotNull
    public static final File subPath(@NotNull File file, int n, int n2) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return FilesKt.toComponents(file).subPath(n, n2);
    }
}

