/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.io.path.ExperimentalPathApi;
import kotlin.io.path.PathsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\u0082\u0001\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a%\u0010\u0005\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a%\u0010\u0005\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u000b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u001e\u0010\f\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a:\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b\u00a2\u0006\u0002\u0010\u0015\u001a:\u0010\u0016\u001a\u00020\u0017*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b\u00a2\u0006\u0002\u0010\u0018\u001a=\u0010\u0019\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2!\u0010\u001a\u001a\u001d\u0012\u0013\u0012\u00110\u001c\u00a2\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u00010\u001bH\u0087\b\u00f8\u0001\u0000\u001a&\u0010 \u001a\u00020!*\u00020\u00022\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b\u00a2\u0006\u0002\u0010\"\u001a&\u0010#\u001a\u00020$*\u00020\u00022\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b\u00a2\u0006\u0002\u0010%\u001a\r\u0010&\u001a\u00020\u0004*\u00020\u0002H\u0087\b\u001a\u001d\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001c0(*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0016\u0010)\u001a\u00020\u001c*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a0\u0010*\u001a\u00020+*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b\u00a2\u0006\u0002\u0010,\u001a?\u0010-\u001a\u0002H.\"\u0004\b\u0000\u0010.*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\u0018\u0010/\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u000b\u0012\u0004\u0012\u0002H.0\u001bH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100\u001a.\u00101\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b\u00a2\u0006\u0002\u00102\u001a>\u00103\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b\u00a2\u0006\u0002\u00104\u001a>\u00103\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u000b2\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b\u00a2\u0006\u0002\u00105\u001a7\u00106\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0007\u00a2\u0006\u0002\u00107\u001a0\u00108\u001a\u000209*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0087\b\u00a2\u0006\u0002\u0010:\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006;"}, d2={"appendBytes", "", "Ljava/nio/file/Path;", "array", "", "appendLines", "lines", "", "", "charset", "Ljava/nio/charset/Charset;", "Lkotlin/sequences/Sequence;", "appendText", "text", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "options", "", "Ljava/nio/file/OpenOption;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;I[Ljava/nio/file/OpenOption;)Ljava/io/BufferedReader;", "bufferedWriter", "Ljava/io/BufferedWriter;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;I[Ljava/nio/file/OpenOption;)Ljava/io/BufferedWriter;", "forEachLine", "action", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "line", "inputStream", "Ljava/io/InputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/InputStream;", "outputStream", "Ljava/io/OutputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/OutputStream;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/io/InputStreamReader;", "useLines", "T", "block", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "(Ljava/nio/file/Path;[B[Ljava/nio/file/OpenOption;)V", "writeLines", "(Ljava/nio/file/Path;Ljava/lang/Iterable;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;", "(Ljava/nio/file/Path;Lkotlin/sequences/Sequence;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;", "writeText", "(Ljava/nio/file/Path;Ljava/lang/CharSequence;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)V", "writer", "Ljava/io/OutputStreamWriter;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/io/OutputStreamWriter;", "kotlin-stdlib-jdk7"}, xs="kotlin/io/path/PathsKt")
@SourceDebugExtension(value={"SMAP\nPathReadWrite.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PathReadWrite.kt\nkotlin/io/path/PathsKt__PathReadWriteKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 ReadWrite.kt\nkotlin/io/TextStreamsKt\n+ 4 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n*L\n1#1,326:1\n1#2:327\n1#2:329\n52#3:328\n1313#4,2:330\n*S KotlinDebug\n*F\n+ 1 PathReadWrite.kt\nkotlin/io/path/PathsKt__PathReadWriteKt\n*L\n202#1:329\n202#1:328\n202#1:330,2\n*E\n"})
class PathsKt__PathReadWriteKt {
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final InputStreamReader reader(Path path, Charset charset, OpenOption ... openOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        return new InputStreamReader(Files.newInputStream(path, Arrays.copyOf(openOptionArray, openOptionArray.length)), charset);
    }

    static InputStreamReader reader$default(Path path, Charset charset, OpenOption[] openOptionArray, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        return new InputStreamReader(Files.newInputStream(path, Arrays.copyOf(openOptionArray, openOptionArray.length)), charset);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final BufferedReader bufferedReader(Path path, Charset charset, int n, OpenOption ... openOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        return new BufferedReader(new InputStreamReader(Files.newInputStream(path, Arrays.copyOf(openOptionArray, openOptionArray.length)), charset), n);
    }

    static BufferedReader bufferedReader$default(Path path, Charset charset, int n, OpenOption[] openOptionArray, int n2, Object object) throws IOException {
        if ((n2 & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((n2 & 2) != 0) {
            n = 8192;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        return new BufferedReader(new InputStreamReader(Files.newInputStream(path, Arrays.copyOf(openOptionArray, openOptionArray.length)), charset), n);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final OutputStreamWriter writer(Path path, Charset charset, OpenOption ... openOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        return new OutputStreamWriter(Files.newOutputStream(path, Arrays.copyOf(openOptionArray, openOptionArray.length)), charset);
    }

    static OutputStreamWriter writer$default(Path path, Charset charset, OpenOption[] openOptionArray, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        return new OutputStreamWriter(Files.newOutputStream(path, Arrays.copyOf(openOptionArray, openOptionArray.length)), charset);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final BufferedWriter bufferedWriter(Path path, Charset charset, int n, OpenOption ... openOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        return new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(path, Arrays.copyOf(openOptionArray, openOptionArray.length)), charset), n);
    }

    static BufferedWriter bufferedWriter$default(Path path, Charset charset, int n, OpenOption[] openOptionArray, int n2, Object object) throws IOException {
        if ((n2 & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((n2 & 2) != 0) {
            n = 8192;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        return new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(path, Arrays.copyOf(openOptionArray, openOptionArray.length)), charset), n);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final byte[] readBytes(Path path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        byte[] byArray = Files.readAllBytes(path);
        Intrinsics.checkNotNullExpressionValue(byArray, "readAllBytes(this)");
        return byArray;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final void writeBytes(Path path, byte[] byArray, OpenOption ... openOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(byArray, "array");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        Files.write(path, byArray, Arrays.copyOf(openOptionArray, openOptionArray.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final void appendBytes(Path path, byte[] byArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(byArray, "array");
        OpenOption[] openOptionArray = new OpenOption[]{StandardOpenOption.APPEND};
        Files.write(path, byArray, openOptionArray);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @NotNull
    public static final String readText(@NotNull Path path, @NotNull Charset charset) throws IOException {
        Object object;
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Object object2 = path;
        Object object3 = new OpenOption[]{};
        object2 = new InputStreamReader(Files.newInputStream((Path)object2, Arrays.copyOf(object3, ((OpenOption[])object3).length)), charset);
        object3 = null;
        try {
            object = (InputStreamReader)object2;
            boolean bl = false;
            object = TextStreamsKt.readText((Reader)object);
        } catch (Throwable throwable) {
            object3 = throwable;
            throw throwable;
        } finally {
            CloseableKt.closeFinally((Closeable)object2, (Throwable)object3);
        }
        return object;
    }

    public static String readText$default(Path path, Charset charset, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return PathsKt.readText(path, charset);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    public static final void writeText(@NotNull Path path, @NotNull CharSequence charSequence, @NotNull Charset charset, @NotNull OpenOption ... openOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "text");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        OutputStream outputStream = Files.newOutputStream(path, Arrays.copyOf(openOptionArray, openOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(outputStream, "newOutputStream(this, *options)");
        Closeable closeable = outputStream;
        closeable = new OutputStreamWriter((OutputStream)closeable, charset);
        Throwable throwable = null;
        try {
            Writer writer = (OutputStreamWriter)closeable;
            boolean bl = false;
            writer = writer.append(charSequence);
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }

    public static void writeText$default(Path path, CharSequence charSequence, Charset charset, OpenOption[] openOptionArray, int n, Object object) throws IOException {
        if ((n & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        PathsKt.writeText(path, charSequence, charset, openOptionArray);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    public static final void appendText(@NotNull Path path, @NotNull CharSequence charSequence, @NotNull Charset charset) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "text");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Object object = new OpenOption[]{StandardOpenOption.APPEND};
        OutputStream outputStream = Files.newOutputStream(path, object);
        Intrinsics.checkNotNullExpressionValue(outputStream, "newOutputStream(this, StandardOpenOption.APPEND)");
        Closeable closeable = outputStream;
        closeable = new OutputStreamWriter((OutputStream)closeable, charset);
        object = null;
        try {
            Writer writer = (OutputStreamWriter)closeable;
            boolean bl = false;
            writer = writer.append(charSequence);
        } catch (Throwable throwable) {
            object = throwable;
            throw throwable;
        } finally {
            CloseableKt.closeFinally(closeable, (Throwable)object);
        }
    }

    public static void appendText$default(Path path, CharSequence charSequence, Charset charset, int n, Object object) throws IOException {
        if ((n & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        PathsKt.appendText(path, charSequence, charset);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final void forEachLine(Path path, Charset charset, Function1<? super String, Unit> function1) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(function1, "action");
        BufferedReader bufferedReader = Files.newBufferedReader(path, charset);
        Intrinsics.checkNotNullExpressionValue(bufferedReader, "newBufferedReader(this, charset)");
        Reader reader = bufferedReader;
        boolean bl = false;
        Closeable closeable = reader;
        closeable = (BufferedReader)closeable;
        Throwable throwable = null;
        try {
            Object object = (BufferedReader)closeable;
            boolean bl2 = false;
            Sequence<String> sequence = TextStreamsKt.lineSequence((BufferedReader)object);
            boolean bl3 = false;
            Sequence<String> sequence2 = sequence;
            boolean bl4 = false;
            Iterator<String> iterator2 = sequence2.iterator();
            while (iterator2.hasNext()) {
                String string = iterator2.next();
                function1.invoke(string);
            }
            object = Unit.INSTANCE;
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, throwable);
            InlineMarker.finallyEnd(1);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void forEachLine$default(Path path, Charset charset, Function1 function1, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(function1, "action");
        BufferedReader bufferedReader = Files.newBufferedReader(path, charset);
        Intrinsics.checkNotNullExpressionValue(bufferedReader, "newBufferedReader(this, charset)");
        Reader reader = bufferedReader;
        boolean bl = false;
        Closeable closeable = reader;
        closeable = (BufferedReader)closeable;
        Throwable throwable = null;
        try {
            Object object2 = (BufferedReader)closeable;
            boolean bl2 = false;
            Sequence<String> sequence = TextStreamsKt.lineSequence((BufferedReader)object2);
            boolean bl3 = false;
            Sequence<String> sequence2 = sequence;
            boolean bl4 = false;
            Iterator<String> iterator2 = sequence2.iterator();
            while (iterator2.hasNext()) {
                String string = iterator2.next();
                function1.invoke(string);
            }
            object2 = Unit.INSTANCE;
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, throwable);
            InlineMarker.finallyEnd(1);
        }
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final InputStream inputStream(Path path, OpenOption ... openOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        InputStream inputStream = Files.newInputStream(path, Arrays.copyOf(openOptionArray, openOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(inputStream, "newInputStream(this, *options)");
        return inputStream;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final OutputStream outputStream(Path path, OpenOption ... openOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        OutputStream outputStream = Files.newOutputStream(path, Arrays.copyOf(openOptionArray, openOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(outputStream, "newOutputStream(this, *options)");
        return outputStream;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final List<String> readLines(Path path, Charset charset) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        List<String> list = Files.readAllLines(path, charset);
        Intrinsics.checkNotNullExpressionValue(list, "readAllLines(this, charset)");
        return list;
    }

    static List readLines$default(Path path, Charset charset, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        List<String> list = Files.readAllLines(path, charset);
        Intrinsics.checkNotNullExpressionValue(list, "readAllLines(this, charset)");
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final <T> T useLines(Path path, Charset charset, Function1<? super Sequence<String>, ? extends T> function1) throws IOException {
        BufferedReader bufferedReader;
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(function1, "block");
        Closeable closeable = Files.newBufferedReader(path, charset);
        Throwable throwable = null;
        try {
            bufferedReader = (BufferedReader)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(bufferedReader, "it");
            bufferedReader = function1.invoke(TextStreamsKt.lineSequence(bufferedReader));
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, throwable);
            InlineMarker.finallyEnd(1);
        }
        return (T)bufferedReader;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static Object useLines$default(Path path, Charset charset, Function1 function1, int n, Object object) throws IOException {
        BufferedReader bufferedReader;
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(function1, "block");
        Closeable closeable = Files.newBufferedReader(path, charset);
        object = null;
        try {
            bufferedReader = (BufferedReader)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(bufferedReader, "it");
            bufferedReader = function1.invoke(TextStreamsKt.lineSequence(bufferedReader));
        } catch (Throwable throwable) {
            object = throwable;
            throw throwable;
        } finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, (Throwable)object);
            InlineMarker.finallyEnd(1);
        }
        return bufferedReader;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path writeLines(Path path, Iterable<? extends CharSequence> iterable, Charset charset, OpenOption ... openOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(iterable, "lines");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        Path path2 = Files.write(path, iterable, charset, Arrays.copyOf(openOptionArray, openOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(path2, "write(this, lines, charset, *options)");
        return path2;
    }

    static Path writeLines$default(Path path, Iterable iterable, Charset charset, OpenOption[] openOptionArray, int n, Object object) throws IOException {
        if ((n & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(iterable, "lines");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        Path path2 = Files.write(path, (Iterable<? extends CharSequence>)iterable, charset, Arrays.copyOf(openOptionArray, openOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(path2, "write(this, lines, charset, *options)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path writeLines(Path path, Sequence<? extends CharSequence> sequence, Charset charset, OpenOption ... openOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(sequence, "lines");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        Path path2 = Files.write(path, SequencesKt.asIterable(sequence), charset, Arrays.copyOf(openOptionArray, openOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(path2, "write(this, lines.asIterable(), charset, *options)");
        return path2;
    }

    static Path writeLines$default(Path path, Sequence sequence, Charset charset, OpenOption[] openOptionArray, int n, Object object) throws IOException {
        if ((n & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(sequence, "lines");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(openOptionArray, "options");
        Path path2 = Files.write(path, SequencesKt.asIterable(sequence), charset, Arrays.copyOf(openOptionArray, openOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(path2, "write(this, lines.asIterable(), charset, *options)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path appendLines(Path path, Iterable<? extends CharSequence> iterable, Charset charset) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(iterable, "lines");
        Intrinsics.checkNotNullParameter(charset, "charset");
        OpenOption[] openOptionArray = new OpenOption[]{StandardOpenOption.APPEND};
        Path path2 = Files.write(path, iterable, charset, openOptionArray);
        Intrinsics.checkNotNullExpressionValue(path2, "write(this, lines, chars\u2026tandardOpenOption.APPEND)");
        return path2;
    }

    static Path appendLines$default(Path path, Iterable iterable, Charset charset, int n, Object openOptionArray) throws IOException {
        if ((n & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(iterable, "lines");
        Intrinsics.checkNotNullParameter(charset, "charset");
        openOptionArray = new OpenOption[]{StandardOpenOption.APPEND};
        Path path2 = Files.write(path, (Iterable<? extends CharSequence>)iterable, charset, openOptionArray);
        Intrinsics.checkNotNullExpressionValue(path2, "write(this, lines, chars\u2026tandardOpenOption.APPEND)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path appendLines(Path path, Sequence<? extends CharSequence> sequence, Charset charset) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(sequence, "lines");
        Intrinsics.checkNotNullParameter(charset, "charset");
        OpenOption[] openOptionArray = new OpenOption[]{StandardOpenOption.APPEND};
        Path path2 = Files.write(path, SequencesKt.asIterable(sequence), charset, openOptionArray);
        Intrinsics.checkNotNullExpressionValue(path2, "write(this, lines.asIter\u2026tandardOpenOption.APPEND)");
        return path2;
    }

    static Path appendLines$default(Path path, Sequence sequence, Charset charset, int n, Object openOptionArray) throws IOException {
        if ((n & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(sequence, "lines");
        Intrinsics.checkNotNullParameter(charset, "charset");
        openOptionArray = new OpenOption[]{StandardOpenOption.APPEND};
        Path path2 = Files.write(path, SequencesKt.asIterable(sequence), charset, openOptionArray);
        Intrinsics.checkNotNullExpressionValue(path2, "write(this, lines.asIter\u2026tandardOpenOption.APPEND)");
        return path2;
    }
}

