/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.collections.ByteIterator;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000Z\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0007\u001a\u00020\b*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u000b\u001a\u00020\f*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\r\u001a\u00020\u000e*\u00020\u000f2\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u001c\u0010\u0010\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\r\u0010\u0013\u001a\u00020\u000e*\u00020\u0014H\u0087\b\u001a\u001d\u0010\u0013\u001a\u00020\u000e*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u0018*\u00020\u0001H\u0086\u0002\u001a\f\u0010\u0019\u001a\u00020\u0014*\u00020\u0002H\u0007\u001a\u0016\u0010\u0019\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u0004H\u0007\u001a\u0017\u0010\u001b\u001a\u00020\u001c*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u001d\u001a\u00020\u001e*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u00a8\u0006\u001f"}, d2={"buffered", "Ljava/io/BufferedInputStream;", "Ljava/io/InputStream;", "bufferSize", "", "Ljava/io/BufferedOutputStream;", "Ljava/io/OutputStream;", "bufferedReader", "Ljava/io/BufferedReader;", "charset", "Ljava/nio/charset/Charset;", "bufferedWriter", "Ljava/io/BufferedWriter;", "byteInputStream", "Ljava/io/ByteArrayInputStream;", "", "copyTo", "", "out", "inputStream", "", "offset", "length", "iterator", "Lkotlin/collections/ByteIterator;", "readBytes", "estimatedSize", "reader", "Ljava/io/InputStreamReader;", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"})
@JvmName(name="ByteStreamsKt")
public final class ByteStreamsKt {
    @NotNull
    public static final ByteIterator iterator(@NotNull BufferedInputStream bufferedInputStream) {
        Intrinsics.checkNotNullParameter(bufferedInputStream, "<this>");
        return new ByteIterator(bufferedInputStream){
            private int nextByte;
            private boolean nextPrepared;
            private boolean finished;
            final BufferedInputStream $this_iterator;
            {
                this.$this_iterator = bufferedInputStream;
                this.nextByte = -1;
            }

            public final int getNextByte() {
                return this.nextByte;
            }

            public final void setNextByte(int n) {
                this.nextByte = n;
            }

            public final boolean getNextPrepared() {
                return this.nextPrepared;
            }

            public final void setNextPrepared(boolean bl) {
                this.nextPrepared = bl;
            }

            public final boolean getFinished() {
                return this.finished;
            }

            public final void setFinished(boolean bl) {
                this.finished = bl;
            }

            private final void prepareNext() {
                if (!this.nextPrepared && !this.finished) {
                    this.nextByte = this.$this_iterator.read();
                    this.nextPrepared = true;
                    this.finished = this.nextByte == -1;
                }
            }

            public boolean hasNext() {
                this.prepareNext();
                return !this.finished;
            }

            public byte nextByte() {
                this.prepareNext();
                if (this.finished) {
                    throw new NoSuchElementException("Input stream is over.");
                }
                byte by = (byte)this.nextByte;
                this.nextPrepared = false;
                return by;
            }
        };
    }

    @InlineOnly
    private static final ByteArrayInputStream byteInputStream(String string, Charset charset) {
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] byArray = string.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
        return new ByteArrayInputStream(byArray);
    }

    static ByteArrayInputStream byteInputStream$default(String string, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(string, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] byArray = string.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
        return new ByteArrayInputStream(byArray);
    }

    @InlineOnly
    private static final ByteArrayInputStream inputStream(byte[] byArray) {
        Intrinsics.checkNotNullParameter(byArray, "<this>");
        return new ByteArrayInputStream(byArray);
    }

    @InlineOnly
    private static final ByteArrayInputStream inputStream(byte[] byArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(byArray, "<this>");
        return new ByteArrayInputStream(byArray, n, n2);
    }

    @InlineOnly
    private static final BufferedInputStream buffered(InputStream inputStream, int n) {
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream)inputStream : new BufferedInputStream(inputStream, n);
    }

    static BufferedInputStream buffered$default(InputStream inputStream, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 8192;
        }
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream)inputStream : new BufferedInputStream(inputStream, n);
    }

    @InlineOnly
    private static final InputStreamReader reader(InputStream inputStream, Charset charset) {
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new InputStreamReader(inputStream, charset);
    }

    static InputStreamReader reader$default(InputStream inputStream, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new InputStreamReader(inputStream, charset);
    }

    @InlineOnly
    private static final BufferedReader bufferedReader(InputStream inputStream, Charset charset) {
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Reader reader = new InputStreamReader(inputStream, charset);
        int n = 8192;
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, n);
    }

    static BufferedReader bufferedReader$default(InputStream inputStream, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Reader reader = new InputStreamReader(inputStream, charset);
        int n2 = 8192;
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, n2);
    }

    @InlineOnly
    private static final BufferedOutputStream buffered(OutputStream outputStream, int n) {
        Intrinsics.checkNotNullParameter(outputStream, "<this>");
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream)outputStream : new BufferedOutputStream(outputStream, n);
    }

    static BufferedOutputStream buffered$default(OutputStream outputStream, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 8192;
        }
        Intrinsics.checkNotNullParameter(outputStream, "<this>");
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream)outputStream : new BufferedOutputStream(outputStream, n);
    }

    @InlineOnly
    private static final OutputStreamWriter writer(OutputStream outputStream, Charset charset) {
        Intrinsics.checkNotNullParameter(outputStream, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new OutputStreamWriter(outputStream, charset);
    }

    static OutputStreamWriter writer$default(OutputStream outputStream, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(outputStream, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new OutputStreamWriter(outputStream, charset);
    }

    @InlineOnly
    private static final BufferedWriter bufferedWriter(OutputStream outputStream, Charset charset) {
        Intrinsics.checkNotNullParameter(outputStream, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Writer writer = new OutputStreamWriter(outputStream, charset);
        int n = 8192;
        return writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer, n);
    }

    static BufferedWriter bufferedWriter$default(OutputStream outputStream, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(outputStream, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Writer writer = new OutputStreamWriter(outputStream, charset);
        int n2 = 8192;
        return writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer, n2);
    }

    public static final long copyTo(@NotNull InputStream inputStream, @NotNull OutputStream outputStream, int n) {
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        Intrinsics.checkNotNullParameter(outputStream, "out");
        long l = 0L;
        byte[] byArray = new byte[n];
        int n2 = inputStream.read(byArray);
        while (n2 >= 0) {
            outputStream.write(byArray, 0, n2);
            l += (long)n2;
            n2 = inputStream.read(byArray);
        }
        return l;
    }

    public static long copyTo$default(InputStream inputStream, OutputStream outputStream, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 8192;
        }
        return ByteStreamsKt.copyTo(inputStream, outputStream, n);
    }

    @Deprecated(message="Use readBytes() overload without estimatedSize parameter", replaceWith=@ReplaceWith(expression="readBytes()", imports={}))
    @DeprecatedSinceKotlin(warningSince="1.3", errorSince="1.5")
    @NotNull
    public static final byte[] readBytes(@NotNull InputStream inputStream, int n) {
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(n, inputStream.available()));
        ByteStreamsKt.copyTo$default(inputStream, byteArrayOutputStream, 0, 2, null);
        byte[] byArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byArray, "buffer.toByteArray()");
        return byArray;
    }

    public static byte[] readBytes$default(InputStream inputStream, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 8192;
        }
        return ByteStreamsKt.readBytes(inputStream, n);
    }

    @SinceKotlin(version="1.3")
    @NotNull
    public static final byte[] readBytes(@NotNull InputStream inputStream) {
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(8192, inputStream.available()));
        ByteStreamsKt.copyTo$default(inputStream, byteArrayOutputStream, 0, 2, null);
        byte[] byArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byArray, "buffer.toByteArray()");
        return byArray;
    }
}

