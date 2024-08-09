/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.HidesMembers;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u00004\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0014\u0010\r\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003H\u0007\u001a\r\u0010\u0010\u001a\u00020\u000e*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u0010\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u0087\b\u001a\u0015\u0010\u0010\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a\f\u0010\u0015\u001a\u00020\u0016*\u00020\u0003H\u0007\"!\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F\u00a2\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"$\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t*\u00020\u00038FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\n\u0010\u0005\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0017"}, d2={"stackTrace", "", "Ljava/lang/StackTraceElement;", "", "getStackTrace$annotations", "(Ljava/lang/Throwable;)V", "getStackTrace", "(Ljava/lang/Throwable;)[Ljava/lang/StackTraceElement;", "suppressedExceptions", "", "getSuppressedExceptions$annotations", "getSuppressedExceptions", "(Ljava/lang/Throwable;)Ljava/util/List;", "addSuppressed", "", "exception", "printStackTrace", "stream", "Ljava/io/PrintStream;", "writer", "Ljava/io/PrintWriter;", "stackTraceToString", "", "kotlin-stdlib"}, xs="kotlin/ExceptionsKt")
class ExceptionsKt__ExceptionsKt {
    @InlineOnly
    private static final void printStackTrace(Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "<this>");
        throwable.printStackTrace();
    }

    @InlineOnly
    private static final void printStackTrace(Throwable throwable, PrintWriter printWriter) {
        Intrinsics.checkNotNullParameter(throwable, "<this>");
        Intrinsics.checkNotNullParameter(printWriter, "writer");
        throwable.printStackTrace(printWriter);
    }

    @InlineOnly
    private static final void printStackTrace(Throwable throwable, PrintStream printStream) {
        Intrinsics.checkNotNullParameter(throwable, "<this>");
        Intrinsics.checkNotNullParameter(printStream, "stream");
        throwable.printStackTrace(printStream);
    }

    @NotNull
    public static final StackTraceElement[] getStackTrace(@NotNull Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "<this>");
        StackTraceElement[] stackTraceElementArray = throwable.getStackTrace();
        Intrinsics.checkNotNull(stackTraceElementArray);
        return stackTraceElementArray;
    }

    public static void getStackTrace$annotations(Throwable throwable) {
    }

    @SinceKotlin(version="1.4")
    @NotNull
    public static final String stackTraceToString(@NotNull Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "<this>");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.flush();
        String string = stringWriter.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sw.toString()");
        return string;
    }

    @SinceKotlin(version="1.1")
    @HidesMembers
    public static final void addSuppressed(@NotNull Throwable throwable, @NotNull Throwable throwable2) {
        Intrinsics.checkNotNullParameter(throwable, "<this>");
        Intrinsics.checkNotNullParameter(throwable2, "exception");
        if (throwable != throwable2) {
            PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed(throwable, throwable2);
        }
    }

    @NotNull
    public static final List<Throwable> getSuppressedExceptions(@NotNull Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "<this>");
        return PlatformImplementationsKt.IMPLEMENTATIONS.getSuppressed(throwable);
    }

    @SinceKotlin(version="1.4")
    public static void getSuppressedExceptions$annotations(Throwable throwable) {
    }
}

