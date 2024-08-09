/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0004H\u0002J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0010H\u0002J\u0018\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004H\u0002J\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020!H\u0002J\u0010\u0010#\u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00060\u0012j\u0002`\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2={"Lkotlin/io/LineReader;", "", "()V", "BUFFER_SIZE", "", "byteBuf", "Ljava/nio/ByteBuffer;", "bytes", "", "charBuf", "Ljava/nio/CharBuffer;", "chars", "", "decoder", "Ljava/nio/charset/CharsetDecoder;", "directEOL", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "compactBytes", "decode", "endOfInput", "decodeEndOfInput", "nBytes", "nChars", "readLine", "", "inputStream", "Ljava/io/InputStream;", "charset", "Ljava/nio/charset/Charset;", "resetAll", "", "trimStringBuilder", "updateCharset", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nConsole.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Console.kt\nkotlin/io/LineReader\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,299:1\n1#2:300\n*E\n"})
public final class LineReader {
    @NotNull
    public static final LineReader INSTANCE = new LineReader();
    private static final int BUFFER_SIZE = 32;
    private static CharsetDecoder decoder;
    private static boolean directEOL;
    @NotNull
    private static final byte[] bytes;
    @NotNull
    private static final char[] chars;
    @NotNull
    private static final ByteBuffer byteBuf;
    @NotNull
    private static final CharBuffer charBuf;
    @NotNull
    private static final StringBuilder sb;

    private LineReader() {
    }

    @Nullable
    public final synchronized String readLine(@NotNull InputStream inputStream, @NotNull Charset charset) {
        block12: {
            block11: {
                Intrinsics.checkNotNullParameter(inputStream, "inputStream");
                Intrinsics.checkNotNullParameter(charset, "charset");
                if (decoder == null) break block11;
                CharsetDecoder charsetDecoder = decoder;
                if (charsetDecoder == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("decoder");
                    charsetDecoder = null;
                }
                if (Intrinsics.areEqual(charsetDecoder.charset(), charset)) break block12;
            }
            this.updateCharset(charset);
        }
        int n = 0;
        int n2 = 0;
        while (true) {
            int n3;
            if ((n3 = inputStream.read()) == -1) {
                if (((CharSequence)sb).length() == 0 && n == 0 && n2 == 0) {
                    return null;
                }
                n2 = this.decodeEndOfInput(n, n2);
                break;
            }
            LineReader.bytes[n++] = (byte)n3;
            if (n3 != 10 && n != 32 && directEOL) continue;
            byteBuf.limit(n);
            charBuf.position(n2);
            n2 = this.decode(false);
            if (n2 > 0 && chars[n2 - 1] == '\n') {
                byteBuf.position(0);
                break;
            }
            n = this.compactBytes();
        }
        if (n2 > 0 && chars[n2 - 1] == '\n' && --n2 > 0 && chars[n2 - 1] == '\r') {
            --n2;
        }
        if (((CharSequence)sb).length() == 0) {
            return new String(chars, 0, n2);
        }
        sb.append(chars, 0, n2);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        String string2 = string;
        if (sb.length() > 32) {
            this.trimStringBuilder();
        }
        sb.setLength(0);
        return string2;
    }

    private final int decode(boolean bl) {
        while (true) {
            CoderResult coderResult;
            CharsetDecoder charsetDecoder;
            if ((charsetDecoder = decoder) == null) {
                Intrinsics.throwUninitializedPropertyAccessException("decoder");
                charsetDecoder = null;
            }
            Intrinsics.checkNotNullExpressionValue(charsetDecoder.decode(byteBuf, charBuf, bl), "decoder.decode(byteBuf, charBuf, endOfInput)");
            if (coderResult.isError()) {
                this.resetAll();
                coderResult.throwException();
            }
            int n = charBuf.position();
            if (!coderResult.isOverflow()) {
                return n;
            }
            sb.append(chars, 0, n - 1);
            charBuf.position(0);
            charBuf.limit(32);
            charBuf.put(chars[n - 1]);
        }
    }

    private final int compactBytes() {
        int n;
        ByteBuffer byteBuffer;
        ByteBuffer byteBuffer2 = byteBuffer = byteBuf;
        boolean bl = false;
        byteBuffer2.compact();
        int n2 = n = byteBuffer2.position();
        boolean bl2 = false;
        byteBuffer2.position(0);
        return n;
    }

    private final int decodeEndOfInput(int n, int n2) {
        int n3;
        byteBuf.limit(n);
        charBuf.position(n2);
        int n4 = n3 = this.decode(true);
        boolean bl = false;
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
            charsetDecoder = null;
        }
        charsetDecoder.reset();
        byteBuf.position(0);
        return n3;
    }

    private final void updateCharset(Charset charset) {
        CharsetDecoder charsetDecoder = charset.newDecoder();
        Intrinsics.checkNotNullExpressionValue(charsetDecoder, "charset.newDecoder()");
        decoder = charsetDecoder;
        byteBuf.clear();
        charBuf.clear();
        byteBuf.put((byte)10);
        byteBuf.flip();
        CharsetDecoder charsetDecoder2 = decoder;
        if (charsetDecoder2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
            charsetDecoder2 = null;
        }
        charsetDecoder2.decode(byteBuf, charBuf, true);
        directEOL = charBuf.position() == 1 && charBuf.get(0) == '\n';
        this.resetAll();
    }

    private final void resetAll() {
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
            charsetDecoder = null;
        }
        charsetDecoder.reset();
        byteBuf.position(0);
        sb.setLength(0);
    }

    private final void trimStringBuilder() {
        sb.setLength(32);
        sb.trimToSize();
    }

    static {
        bytes = new byte[32];
        chars = new char[32];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        Intrinsics.checkNotNullExpressionValue(byteBuffer, "wrap(bytes)");
        byteBuf = byteBuffer;
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        Intrinsics.checkNotNullExpressionValue(charBuffer, "wrap(chars)");
        charBuf = charBuffer;
        sb = new StringBuilder();
    }
}

