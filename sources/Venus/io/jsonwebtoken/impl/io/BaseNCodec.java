/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.CodecPolicy;
import io.jsonwebtoken.lang.Strings;
import java.util.Arrays;
import java.util.Objects;

abstract class BaseNCodec {
    static final int EOF = -1;
    public static final int MIME_CHUNK_SIZE = 76;
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final int MAX_BUFFER_SIZE = 0x7FFFFFF7;
    protected static final int MASK_8BITS = 255;
    protected static final byte PAD_DEFAULT = 61;
    protected static final CodecPolicy DECODING_POLICY_DEFAULT = CodecPolicy.LENIENT;
    static final byte[] CHUNK_SEPARATOR = new byte[]{13, 10};
    protected final byte pad;
    private final int unencodedBlockSize;
    private final int encodedBlockSize;
    protected final int lineLength;
    private final int chunkSeparatorLength;
    private final CodecPolicy decodingPolicy;

    private static int createPositiveCapacity(int n) {
        if (n < 0) {
            throw new OutOfMemoryError("Unable to allocate array size: " + ((long)n & 0xFFFFFFFFL));
        }
        return Math.max(n, 0x7FFFFFF7);
    }

    @Deprecated
    protected static boolean isWhiteSpace(byte by) {
        return Character.isWhitespace(by);
    }

    private static int compareUnsigned(int n, int n2) {
        return Integer.compare(n + Integer.MIN_VALUE, n2 + Integer.MIN_VALUE);
    }

    private static byte[] resizeBuffer(Context context, int n) {
        int n2 = context.buffer.length;
        int n3 = n2 * 2;
        if (BaseNCodec.compareUnsigned(n3, n) < 0) {
            n3 = n;
        }
        if (BaseNCodec.compareUnsigned(n3, 0x7FFFFFF7) > 0) {
            n3 = BaseNCodec.createPositiveCapacity(n);
        }
        byte[] byArray = Arrays.copyOf(context.buffer, n3);
        context.buffer = byArray;
        return byArray;
    }

    protected BaseNCodec(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, 61);
    }

    protected BaseNCodec(int n, int n2, int n3, int n4, byte by) {
        this(n, n2, n3, n4, by, DECODING_POLICY_DEFAULT);
    }

    protected BaseNCodec(int n, int n2, int n3, int n4, byte by, CodecPolicy codecPolicy) {
        this.unencodedBlockSize = n;
        this.encodedBlockSize = n2;
        boolean bl = n3 > 0 && n4 > 0;
        this.lineLength = bl ? n3 / n2 * n2 : 0;
        this.chunkSeparatorLength = n4;
        this.pad = by;
        this.decodingPolicy = Objects.requireNonNull(codecPolicy, "codecPolicy");
    }

    int available(Context context) {
        return this.hasData(context) ? context.pos - context.readPos : 0;
    }

    protected boolean containsAlphabetOrPad(byte[] byArray) {
        if (byArray == null) {
            return true;
        }
        for (byte by : byArray) {
            if (this.pad != by && !this.isInAlphabet(by)) continue;
            return false;
        }
        return true;
    }

    static int length(byte[] byArray) {
        return byArray != null ? byArray.length : 0;
    }

    static boolean isEmpty(byte[] byArray) {
        return BaseNCodec.length(byArray) == 0;
    }

    public byte[] decode(byte[] byArray) {
        if (BaseNCodec.isEmpty(byArray)) {
            return byArray;
        }
        Context context = new Context();
        this.decode(byArray, 0, byArray.length, context);
        this.decode(byArray, 0, -1, context);
        byte[] byArray2 = new byte[context.pos];
        this.readResults(byArray2, 0, byArray2.length, context);
        return byArray2;
    }

    abstract void decode(byte[] var1, int var2, int var3, Context var4);

    public byte[] decode(String string) {
        return this.decode(Strings.utf8(string));
    }

    public byte[] encode(byte[] byArray) {
        if (BaseNCodec.isEmpty(byArray)) {
            return byArray;
        }
        return this.encode(byArray, 0, byArray.length);
    }

    public byte[] encode(byte[] byArray, int n, int n2) {
        if (BaseNCodec.isEmpty(byArray)) {
            return byArray;
        }
        Context context = new Context();
        this.encode(byArray, n, n2, context);
        this.encode(byArray, n, -1, context);
        byte[] byArray2 = new byte[context.pos - context.readPos];
        this.readResults(byArray2, 0, byArray2.length, context);
        return byArray2;
    }

    abstract void encode(byte[] var1, int var2, int var3, Context var4);

    public String encodeAsString(byte[] byArray) {
        return Strings.utf8(this.encode(byArray));
    }

    public String encodeToString(byte[] byArray) {
        return Strings.utf8(this.encode(byArray));
    }

    protected byte[] ensureBufferSize(int n, Context context) {
        if (context.buffer == null) {
            context.buffer = new byte[Math.max(n, this.getDefaultBufferSize())];
            context.pos = 0;
            context.readPos = 0;
        } else if (context.pos + n - context.buffer.length > 0) {
            return BaseNCodec.resizeBuffer(context, context.pos + n);
        }
        return context.buffer;
    }

    protected int getDefaultBufferSize() {
        return 1;
    }

    public long getEncodedLength(byte[] byArray) {
        long l = (long)((byArray.length + this.unencodedBlockSize - 1) / this.unencodedBlockSize) * (long)this.encodedBlockSize;
        if (this.lineLength > 0) {
            l += (l + (long)this.lineLength - 1L) / (long)this.lineLength * (long)this.chunkSeparatorLength;
        }
        return l;
    }

    boolean hasData(Context context) {
        return context.pos > context.readPos;
    }

    protected abstract boolean isInAlphabet(byte var1);

    public boolean isInAlphabet(byte[] byArray, boolean bl) {
        for (byte by : byArray) {
            if (this.isInAlphabet(by) || bl && (by == this.pad || Character.isWhitespace(by))) continue;
            return true;
        }
        return false;
    }

    public boolean isInAlphabet(String string) {
        return this.isInAlphabet(Strings.utf8(string), false);
    }

    public boolean isStrictDecoding() {
        return this.decodingPolicy == CodecPolicy.STRICT;
    }

    int readResults(byte[] byArray, int n, int n2, Context context) {
        if (this.hasData(context)) {
            int n3 = Math.min(this.available(context), n2);
            System.arraycopy(context.buffer, context.readPos, byArray, n, n3);
            context.readPos += n3;
            if (!this.hasData(context)) {
                context.readPos = 0;
                context.pos = 0;
            }
            return n3;
        }
        return context.eof ? -1 : 0;
    }

    static class Context {
        int ibitWorkArea;
        long lbitWorkArea;
        byte[] buffer;
        int pos;
        int readPos;
        boolean eof;
        int currentLinePos;
        int modulus;

        Context() {
        }

        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", this.getClass().getSimpleName(), Arrays.toString(this.buffer), this.currentLinePos, this.eof, this.ibitWorkArea, this.lbitWorkArea, this.modulus, this.pos, this.readPos);
        }
    }
}

