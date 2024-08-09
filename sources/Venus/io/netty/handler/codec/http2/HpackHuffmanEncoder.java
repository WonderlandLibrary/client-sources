/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.HpackUtil;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;

final class HpackHuffmanEncoder {
    private final int[] codes;
    private final byte[] lengths;
    private final EncodedLengthProcessor encodedLengthProcessor = new EncodedLengthProcessor(this, null);
    private final EncodeProcessor encodeProcessor = new EncodeProcessor(this, null);

    HpackHuffmanEncoder() {
        this(HpackUtil.HUFFMAN_CODES, HpackUtil.HUFFMAN_CODE_LENGTHS);
    }

    private HpackHuffmanEncoder(int[] nArray, byte[] byArray) {
        this.codes = nArray;
        this.lengths = byArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void encode(ByteBuf byteBuf, CharSequence charSequence) {
        ObjectUtil.checkNotNull(byteBuf, "out");
        if (charSequence instanceof AsciiString) {
            AsciiString asciiString = (AsciiString)charSequence;
            try {
                this.encodeProcessor.out = byteBuf;
                asciiString.forEachByte(this.encodeProcessor);
            } catch (Exception exception) {
                PlatformDependent.throwException(exception);
            } finally {
                this.encodeProcessor.end();
            }
        } else {
            this.encodeSlowPath(byteBuf, charSequence);
        }
    }

    private void encodeSlowPath(ByteBuf byteBuf, CharSequence charSequence) {
        long l = 0L;
        int n = 0;
        for (int i = 0; i < charSequence.length(); ++i) {
            int n2 = charSequence.charAt(i) & 0xFF;
            int n3 = this.codes[n2];
            byte by = this.lengths[n2];
            l <<= by;
            l |= (long)n3;
            n += by;
            while (n >= 8) {
                byteBuf.writeByte((int)(l >> (n -= 8)));
            }
        }
        if (n > 0) {
            l <<= 8 - n;
            byteBuf.writeByte((int)(l |= (long)(255 >>> n)));
        }
    }

    int getEncodedLength(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            AsciiString asciiString = (AsciiString)charSequence;
            try {
                this.encodedLengthProcessor.reset();
                asciiString.forEachByte(this.encodedLengthProcessor);
                return this.encodedLengthProcessor.length();
            } catch (Exception exception) {
                PlatformDependent.throwException(exception);
                return 1;
            }
        }
        return this.getEncodedLengthSlowPath(charSequence);
    }

    private int getEncodedLengthSlowPath(CharSequence charSequence) {
        long l = 0L;
        for (int i = 0; i < charSequence.length(); ++i) {
            l += (long)this.lengths[charSequence.charAt(i) & 0xFF];
        }
        return (int)(l + 7L >> 3);
    }

    static byte[] access$200(HpackHuffmanEncoder hpackHuffmanEncoder) {
        return hpackHuffmanEncoder.lengths;
    }

    static int[] access$300(HpackHuffmanEncoder hpackHuffmanEncoder) {
        return hpackHuffmanEncoder.codes;
    }

    private final class EncodedLengthProcessor
    implements ByteProcessor {
        private long len;
        final HpackHuffmanEncoder this$0;

        private EncodedLengthProcessor(HpackHuffmanEncoder hpackHuffmanEncoder) {
            this.this$0 = hpackHuffmanEncoder;
        }

        @Override
        public boolean process(byte by) {
            this.len += (long)HpackHuffmanEncoder.access$200(this.this$0)[by & 0xFF];
            return false;
        }

        void reset() {
            this.len = 0L;
        }

        int length() {
            return (int)(this.len + 7L >> 3);
        }

        EncodedLengthProcessor(HpackHuffmanEncoder hpackHuffmanEncoder, 1 var2_2) {
            this(hpackHuffmanEncoder);
        }
    }

    private final class EncodeProcessor
    implements ByteProcessor {
        ByteBuf out;
        private long current;
        private int n;
        final HpackHuffmanEncoder this$0;

        private EncodeProcessor(HpackHuffmanEncoder hpackHuffmanEncoder) {
            this.this$0 = hpackHuffmanEncoder;
        }

        @Override
        public boolean process(byte by) {
            int n = by & 0xFF;
            byte by2 = HpackHuffmanEncoder.access$200(this.this$0)[n];
            this.current <<= by2;
            this.current |= (long)HpackHuffmanEncoder.access$300(this.this$0)[n];
            this.n += by2;
            while (this.n >= 8) {
                this.n -= 8;
                this.out.writeByte((int)(this.current >> this.n));
            }
            return false;
        }

        void end() {
            try {
                if (this.n > 0) {
                    this.current <<= 8 - this.n;
                    this.current |= (long)(255 >>> this.n);
                    this.out.writeByte((int)this.current);
                }
            } finally {
                this.out = null;
                this.current = 0L;
                this.n = 0;
            }
        }

        EncodeProcessor(HpackHuffmanEncoder hpackHuffmanEncoder, 1 var2_2) {
            this(hpackHuffmanEncoder);
        }
    }
}

