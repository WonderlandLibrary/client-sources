/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.binary;

import java.util.Arrays;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;

public abstract class BaseNCodec
implements BinaryEncoder,
BinaryDecoder {
    static final int EOF = -1;
    public static final int MIME_CHUNK_SIZE = 76;
    public static final int PEM_CHUNK_SIZE = 64;
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    protected static final int MASK_8BITS = 255;
    protected static final byte PAD_DEFAULT = 61;
    @Deprecated
    protected final byte PAD = (byte)61;
    protected final byte pad;
    private final int unencodedBlockSize;
    private final int encodedBlockSize;
    protected final int lineLength;
    private final int chunkSeparatorLength;

    protected BaseNCodec(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, 61);
    }

    protected BaseNCodec(int n, int n2, int n3, int n4, byte by) {
        this.unencodedBlockSize = n;
        this.encodedBlockSize = n2;
        boolean bl = n3 > 0 && n4 > 0;
        this.lineLength = bl ? n3 / n2 * n2 : 0;
        this.chunkSeparatorLength = n4;
        this.pad = by;
    }

    boolean hasData(Context context) {
        return context.buffer != null;
    }

    int available(Context context) {
        return context.buffer != null ? context.pos - context.readPos : 0;
    }

    protected int getDefaultBufferSize() {
        return 1;
    }

    private byte[] resizeBuffer(Context context) {
        if (context.buffer == null) {
            context.buffer = new byte[this.getDefaultBufferSize()];
            context.pos = 0;
            context.readPos = 0;
        } else {
            byte[] byArray = new byte[context.buffer.length * 2];
            System.arraycopy(context.buffer, 0, byArray, 0, context.buffer.length);
            context.buffer = byArray;
        }
        return context.buffer;
    }

    protected byte[] ensureBufferSize(int n, Context context) {
        if (context.buffer == null || context.buffer.length < context.pos + n) {
            return this.resizeBuffer(context);
        }
        return context.buffer;
    }

    int readResults(byte[] byArray, int n, int n2, Context context) {
        if (context.buffer != null) {
            int n3 = Math.min(this.available(context), n2);
            System.arraycopy(context.buffer, context.readPos, byArray, n, n3);
            context.readPos += n3;
            if (context.readPos >= context.pos) {
                context.buffer = null;
            }
            return n3;
        }
        return context.eof ? -1 : 0;
    }

    protected static boolean isWhiteSpace(byte by) {
        switch (by) {
            case 9: 
            case 10: 
            case 13: 
            case 32: {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof byte[])) {
            throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
        }
        return this.encode((byte[])object);
    }

    public String encodeToString(byte[] byArray) {
        return StringUtils.newStringUtf8(this.encode(byArray));
    }

    public String encodeAsString(byte[] byArray) {
        return StringUtils.newStringUtf8(this.encode(byArray));
    }

    @Override
    public Object decode(Object object) throws DecoderException {
        if (object instanceof byte[]) {
            return this.decode((byte[])object);
        }
        if (object instanceof String) {
            return this.decode((String)object);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }

    public byte[] decode(String string) {
        return this.decode(StringUtils.getBytesUtf8(string));
    }

    @Override
    public byte[] decode(byte[] byArray) {
        if (byArray == null || byArray.length == 0) {
            return byArray;
        }
        Context context = new Context();
        this.decode(byArray, 0, byArray.length, context);
        this.decode(byArray, 0, -1, context);
        byte[] byArray2 = new byte[context.pos];
        this.readResults(byArray2, 0, byArray2.length, context);
        return byArray2;
    }

    @Override
    public byte[] encode(byte[] byArray) {
        if (byArray == null || byArray.length == 0) {
            return byArray;
        }
        return this.encode(byArray, 0, byArray.length);
    }

    public byte[] encode(byte[] byArray, int n, int n2) {
        if (byArray == null || byArray.length == 0) {
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

    abstract void decode(byte[] var1, int var2, int var3, Context var4);

    protected abstract boolean isInAlphabet(byte var1);

    public boolean isInAlphabet(byte[] byArray, boolean bl) {
        for (byte by : byArray) {
            if (this.isInAlphabet(by) || bl && (by == this.pad || BaseNCodec.isWhiteSpace(by))) continue;
            return true;
        }
        return false;
    }

    public boolean isInAlphabet(String string) {
        return this.isInAlphabet(StringUtils.getBytesUtf8(string), false);
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

    public long getEncodedLength(byte[] byArray) {
        long l = (long)((byArray.length + this.unencodedBlockSize - 1) / this.unencodedBlockSize) * (long)this.encodedBlockSize;
        if (this.lineLength > 0) {
            l += (l + (long)this.lineLength - 1L) / (long)this.lineLength * (long)this.chunkSeparatorLength;
        }
        return l;
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

