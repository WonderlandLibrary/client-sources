/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.encoding;

import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.io.encoding.Base64;
import kotlin.io.encoding.Base64Kt;
import kotlin.io.encoding.ExperimentalEncodingApi;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u000f\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0013\u001a\u00020\u0014H\u0016J \u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\tH\u0002J(\u0010\u0019\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\tH\u0002J\u0010\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\tH\u0002J\b\u0010\u001d\u001a\u00020\tH\u0016J \u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\tH\u0016J\b\u0010 \u001a\u00020\tH\u0002J\b\u0010!\u001a\u00020\u0014H\u0002J\b\u0010\"\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\t8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2={"Lkotlin/io/encoding/DecodeInputStream;", "Ljava/io/InputStream;", "input", "base64", "Lkotlin/io/encoding/Base64;", "(Ljava/io/InputStream;Lkotlin/io/encoding/Base64;)V", "byteBuffer", "", "byteBufferEndIndex", "", "byteBufferLength", "getByteBufferLength", "()I", "byteBufferStartIndex", "isClosed", "", "isEOF", "singleByteBuffer", "symbolBuffer", "close", "", "copyByteBufferInto", "dst", "dstOffset", "length", "decodeSymbolBufferInto", "dstEndIndex", "symbolBufferLength", "handlePaddingSymbol", "read", "destination", "offset", "readNextSymbol", "resetByteBufferIfEmpty", "shiftByteBufferToStartIfNeeded", "kotlin-stdlib"})
@ExperimentalEncodingApi
final class DecodeInputStream
extends InputStream {
    @NotNull
    private final InputStream input;
    @NotNull
    private final Base64 base64;
    private boolean isClosed;
    private boolean isEOF;
    @NotNull
    private final byte[] singleByteBuffer;
    @NotNull
    private final byte[] symbolBuffer;
    @NotNull
    private final byte[] byteBuffer;
    private int byteBufferStartIndex;
    private int byteBufferEndIndex;

    public DecodeInputStream(@NotNull InputStream inputStream, @NotNull Base64 base64) {
        Intrinsics.checkNotNullParameter(inputStream, "input");
        Intrinsics.checkNotNullParameter(base64, "base64");
        this.input = inputStream;
        this.base64 = base64;
        this.singleByteBuffer = new byte[1];
        this.symbolBuffer = new byte[1024];
        this.byteBuffer = new byte[1024];
    }

    private final int getByteBufferLength() {
        return this.byteBufferEndIndex - this.byteBufferStartIndex;
    }

    @Override
    public int read() {
        int n;
        if (this.byteBufferStartIndex < this.byteBufferEndIndex) {
            int n2 = this.byteBuffer[this.byteBufferStartIndex] & 0xFF;
            ++this.byteBufferStartIndex;
            this.resetByteBufferIfEmpty();
            return n2;
        }
        switch (this.read(this.singleByteBuffer, 0, 1)) {
            case -1: {
                n = -1;
                break;
            }
            case 1: {
                n = this.singleByteBuffer[0] & 0xFF;
                break;
            }
            default: {
                throw new IllegalStateException("Unreachable".toString());
            }
        }
        return n;
    }

    @Override
    public int read(@NotNull byte[] byArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(byArray, "destination");
        if (n < 0 || n2 < 0 || n + n2 > byArray.length) {
            throw new IndexOutOfBoundsException("offset: " + n + ", length: " + n2 + ", buffer size: " + byArray.length);
        }
        if (this.isClosed) {
            throw new IOException("The input stream is closed.");
        }
        if (this.isEOF) {
            return 1;
        }
        if (n2 == 0) {
            return 1;
        }
        if (this.getByteBufferLength() >= n2) {
            this.copyByteBufferInto(byArray, n, n2);
            return n2;
        }
        int n3 = n2 - this.getByteBufferLength();
        int n4 = (n3 + 3 - 1) / 3;
        int n5 = n4 * 4;
        int n6 = n;
        while (!this.isEOF && n5 > 0) {
            int n7;
            int n8 = 0;
            int n9 = Math.min(this.symbolBuffer.length, n5);
            block5: while (!this.isEOF && n8 < n9) {
                n7 = this.readNextSymbol();
                switch (n7) {
                    case -1: {
                        this.isEOF = true;
                        continue block5;
                    }
                    case 61: {
                        n8 = this.handlePaddingSymbol(n8);
                        this.isEOF = true;
                        continue block5;
                    }
                }
                this.symbolBuffer[n8] = (byte)n7;
                ++n8;
            }
            int n10 = n7 = this.isEOF || n8 == n9 ? 1 : 0;
            if (n7 == 0) {
                String string = "Check failed.";
                throw new IllegalStateException(string.toString());
            }
            n5 -= n8;
            n6 += this.decodeSymbolBufferInto(byArray, n6, n2 + n, n8);
        }
        return n6 == n && this.isEOF ? -1 : n6 - n;
    }

    @Override
    public void close() {
        if (!this.isClosed) {
            this.isClosed = true;
            this.input.close();
        }
    }

    private final int decodeSymbolBufferInto(byte[] byArray, int n, int n2, int n3) {
        this.byteBufferEndIndex += this.base64.decodeIntoByteArray(this.symbolBuffer, this.byteBuffer, this.byteBufferEndIndex, 0, n3);
        int n4 = Math.min(this.getByteBufferLength(), n2 - n);
        this.copyByteBufferInto(byArray, n, n4);
        this.shiftByteBufferToStartIfNeeded();
        return n4;
    }

    private final void copyByteBufferInto(byte[] byArray, int n, int n2) {
        ArraysKt.copyInto(this.byteBuffer, byArray, n, this.byteBufferStartIndex, this.byteBufferStartIndex + n2);
        this.byteBufferStartIndex += n2;
        this.resetByteBufferIfEmpty();
    }

    private final void resetByteBufferIfEmpty() {
        if (this.byteBufferStartIndex == this.byteBufferEndIndex) {
            this.byteBufferStartIndex = 0;
            this.byteBufferEndIndex = 0;
        }
    }

    private final void shiftByteBufferToStartIfNeeded() {
        int n = this.symbolBuffer.length / 4 * 3;
        int n2 = this.byteBuffer.length - this.byteBufferEndIndex;
        if (n > n2) {
            ArraysKt.copyInto(this.byteBuffer, this.byteBuffer, 0, this.byteBufferStartIndex, this.byteBufferEndIndex);
            this.byteBufferEndIndex -= this.byteBufferStartIndex;
            this.byteBufferStartIndex = 0;
        }
    }

    private final int handlePaddingSymbol(int n) {
        int n2;
        this.symbolBuffer[n] = 61;
        if ((n & 3) == 2) {
            int n3 = this.readNextSymbol();
            if (n3 >= 0) {
                this.symbolBuffer[n + 1] = (byte)n3;
            }
            n2 = n + 2;
        } else {
            n2 = n + 1;
        }
        return n2;
    }

    private final int readNextSymbol() {
        if (!this.base64.isMimeScheme$kotlin_stdlib()) {
            return this.input.read();
        }
        int n = 0;
        while ((n = this.input.read()) != -1 && !Base64Kt.isInMimeAlphabet(n)) {
        }
        return n;
    }
}

