/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0001H\u0002\u00a8\u0006\u0006"}, d2={"constructMessage", "", "file", "Ljava/io/File;", "other", "reason", "kotlin-stdlib"})
public final class ExceptionsKt {
    private static final String constructMessage(File file, File file2, String string) {
        StringBuilder stringBuilder = new StringBuilder(file.toString());
        if (file2 != null) {
            stringBuilder.append(" -> " + file2);
        }
        if (string != null) {
            stringBuilder.append(": " + string);
        }
        String string2 = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "sb.toString()");
        return string2;
    }

    public static final String access$constructMessage(File file, File file2, String string) {
        return ExceptionsKt.constructMessage(file, file2, string);
    }
}

