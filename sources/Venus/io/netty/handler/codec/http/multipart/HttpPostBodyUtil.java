/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;

final class HttpPostBodyUtil {
    public static final int chunkSize = 8096;
    public static final String DEFAULT_BINARY_CONTENT_TYPE = "application/octet-stream";
    public static final String DEFAULT_TEXT_CONTENT_TYPE = "text/plain";

    private HttpPostBodyUtil() {
    }

    static int findNonWhitespace(String string, int n) {
        int n2;
        for (n2 = n; n2 < string.length() && Character.isWhitespace(string.charAt(n2)); ++n2) {
        }
        return n2;
    }

    static int findEndOfString(String string) {
        int n;
        for (n = string.length(); n > 0 && Character.isWhitespace(string.charAt(n - 1)); --n) {
        }
        return n;
    }

    static class SeekAheadOptimize {
        byte[] bytes;
        int readerIndex;
        int pos;
        int origPos;
        int limit;
        ByteBuf buffer;

        SeekAheadOptimize(ByteBuf byteBuf) {
            if (!byteBuf.hasArray()) {
                throw new IllegalArgumentException("buffer hasn't backing byte array");
            }
            this.buffer = byteBuf;
            this.bytes = byteBuf.array();
            this.readerIndex = byteBuf.readerIndex();
            this.origPos = this.pos = byteBuf.arrayOffset() + this.readerIndex;
            this.limit = byteBuf.arrayOffset() + byteBuf.writerIndex();
        }

        void setReadPosition(int n) {
            this.pos -= n;
            this.readerIndex = this.getReadPosition(this.pos);
            this.buffer.readerIndex(this.readerIndex);
        }

        int getReadPosition(int n) {
            return n - this.origPos + this.readerIndex;
        }
    }

    public static enum TransferEncodingMechanism {
        BIT7("7bit"),
        BIT8("8bit"),
        BINARY("binary");

        private final String value;

        private TransferEncodingMechanism(String string2) {
            this.value = string2;
        }

        public String value() {
            return this.value;
        }

        public String toString() {
            return this.value;
        }
    }
}

