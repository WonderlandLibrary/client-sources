/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.encoding;

import java.io.IOException;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.io.encoding.Base64;
import kotlin.io.encoding.ExperimentalEncodingApi;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u000fH\u0016J \u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\tH\u0002J\b\u0010\u0015\u001a\u00020\u000fH\u0002J \u0010\u0016\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\tH\u0002J\b\u0010\u0017\u001a\u00020\u000fH\u0016J \u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tH\u0016J\u0010\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2={"Lkotlin/io/encoding/EncodeOutputStream;", "Ljava/io/OutputStream;", "output", "base64", "Lkotlin/io/encoding/Base64;", "(Ljava/io/OutputStream;Lkotlin/io/encoding/Base64;)V", "byteBuffer", "", "byteBufferLength", "", "isClosed", "", "lineLength", "symbolBuffer", "checkOpen", "", "close", "copyIntoByteBuffer", "source", "startIndex", "endIndex", "encodeByteBufferIntoOutput", "encodeIntoOutput", "flush", "write", "offset", "length", "b", "kotlin-stdlib"})
@ExperimentalEncodingApi
final class EncodeOutputStream
extends OutputStream {
    @NotNull
    private final OutputStream output;
    @NotNull
    private final Base64 base64;
    private boolean isClosed;
    private int lineLength;
    @NotNull
    private final byte[] symbolBuffer;
    @NotNull
    private final byte[] byteBuffer;
    private int byteBufferLength;

    public EncodeOutputStream(@NotNull OutputStream outputStream, @NotNull Base64 base64) {
        Intrinsics.checkNotNullParameter(outputStream, "output");
        Intrinsics.checkNotNullParameter(base64, "base64");
        this.output = outputStream;
        this.base64 = base64;
        this.lineLength = this.base64.isMimeScheme$kotlin_stdlib() ? 76 : -1;
        this.symbolBuffer = new byte[1024];
        this.byteBuffer = new byte[3];
    }

    @Override
    public void write(int n) {
        this.checkOpen();
        int n2 = this.byteBufferLength;
        this.byteBufferLength = n2 + 1;
        this.byteBuffer[n2] = (byte)n;
        if (this.byteBufferLength == 3) {
            this.encodeByteBufferIntoOutput();
        }
    }

    @Override
    public void write(@NotNull byte[] byArray, int n, int n2) {
        int n3;
        Intrinsics.checkNotNullParameter(byArray, "source");
        this.checkOpen();
        if (n < 0 || n2 < 0 || n + n2 > byArray.length) {
            throw new IndexOutOfBoundsException("offset: " + n + ", length: " + n2 + ", source size: " + byArray.length);
        }
        if (n2 == 0) {
            return;
        }
        int n4 = n3 = this.byteBufferLength < 3 ? 1 : 0;
        if (n3 == 0) {
            String string = "Check failed.";
            throw new IllegalStateException(string.toString());
        }
        n3 = n;
        int n5 = n3 + n2;
        if (this.byteBufferLength != 0) {
            n3 += this.copyIntoByteBuffer(byArray, n3, n5);
            if (this.byteBufferLength != 0) {
                return;
            }
        }
        while (n3 + 3 <= n5) {
            boolean bl;
            int n6 = (this.base64.isMimeScheme$kotlin_stdlib() ? this.lineLength : this.symbolBuffer.length) / 4;
            int n7 = Math.min(n6, (n5 - n3) / 3);
            int n8 = n7 * 3;
            int n9 = this.encodeIntoOutput(byArray, n3, n3 + n8);
            boolean bl2 = bl = n9 == n7 * 4;
            if (!bl) {
                String string = "Check failed.";
                throw new IllegalStateException(string.toString());
            }
            n3 += n8;
        }
        ArraysKt.copyInto(byArray, this.byteBuffer, 0, n3, n5);
        this.byteBufferLength = n5 - n3;
    }

    @Override
    public void flush() {
        this.checkOpen();
        this.output.flush();
    }

    @Override
    public void close() {
        if (!this.isClosed) {
            this.isClosed = true;
            if (this.byteBufferLength != 0) {
                this.encodeByteBufferIntoOutput();
            }
            this.output.close();
        }
    }

    private final int copyIntoByteBuffer(byte[] byArray, int n, int n2) {
        int n3 = Math.min(3 - this.byteBufferLength, n2 - n);
        ArraysKt.copyInto(byArray, this.byteBuffer, this.byteBufferLength, n, n + n3);
        this.byteBufferLength += n3;
        if (this.byteBufferLength == 3) {
            this.encodeByteBufferIntoOutput();
        }
        return n3;
    }

    private final void encodeByteBufferIntoOutput() {
        boolean bl;
        int n = this.encodeIntoOutput(this.byteBuffer, 0, this.byteBufferLength);
        boolean bl2 = bl = n == 4;
        if (!bl) {
            String string = "Check failed.";
            throw new IllegalStateException(string.toString());
        }
        this.byteBufferLength = 0;
    }

    private final int encodeIntoOutput(byte[] byArray, int n, int n2) {
        int n3 = this.base64.encodeIntoByteArray(byArray, this.symbolBuffer, 0, n, n2);
        if (this.lineLength == 0) {
            boolean bl;
            this.output.write(Base64.Default.getMimeLineSeparatorSymbols$kotlin_stdlib());
            this.lineLength = 76;
            boolean bl2 = bl = n3 <= 76;
            if (!bl) {
                String string = "Check failed.";
                throw new IllegalStateException(string.toString());
            }
        }
        this.output.write(this.symbolBuffer, 0, n3);
        this.lineLength -= n3;
        return n3;
    }

    private final void checkOpen() {
        if (this.isClosed) {
            throw new IOException("The output stream is closed.");
        }
    }
}

