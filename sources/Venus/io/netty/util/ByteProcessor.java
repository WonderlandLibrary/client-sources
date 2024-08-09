/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

public interface ByteProcessor {
    public static final ByteProcessor FIND_NUL = new IndexOfProcessor(0);
    public static final ByteProcessor FIND_NON_NUL = new IndexNotOfProcessor(0);
    public static final ByteProcessor FIND_CR = new IndexOfProcessor(13);
    public static final ByteProcessor FIND_NON_CR = new IndexNotOfProcessor(13);
    public static final ByteProcessor FIND_LF = new IndexOfProcessor(10);
    public static final ByteProcessor FIND_NON_LF = new IndexNotOfProcessor(10);
    public static final ByteProcessor FIND_SEMI_COLON = new IndexOfProcessor(59);
    public static final ByteProcessor FIND_COMMA = new IndexOfProcessor(44);
    public static final ByteProcessor FIND_ASCII_SPACE = new IndexOfProcessor(32);
    public static final ByteProcessor FIND_CRLF = new ByteProcessor(){

        @Override
        public boolean process(byte by) {
            return by != 13 && by != 10;
        }
    };
    public static final ByteProcessor FIND_NON_CRLF = new ByteProcessor(){

        @Override
        public boolean process(byte by) {
            return by == 13 || by == 10;
        }
    };
    public static final ByteProcessor FIND_LINEAR_WHITESPACE = new ByteProcessor(){

        @Override
        public boolean process(byte by) {
            return by != 32 && by != 9;
        }
    };
    public static final ByteProcessor FIND_NON_LINEAR_WHITESPACE = new ByteProcessor(){

        @Override
        public boolean process(byte by) {
            return by == 32 || by == 9;
        }
    };

    public boolean process(byte var1) throws Exception;

    public static class IndexNotOfProcessor
    implements ByteProcessor {
        private final byte byteToNotFind;

        public IndexNotOfProcessor(byte by) {
            this.byteToNotFind = by;
        }

        @Override
        public boolean process(byte by) {
            return by == this.byteToNotFind;
        }
    }

    public static class IndexOfProcessor
    implements ByteProcessor {
        private final byte byteToFind;

        public IndexOfProcessor(byte by) {
            this.byteToFind = by;
        }

        @Override
        public boolean process(byte by) {
            return by != this.byteToFind;
        }
    }
}

