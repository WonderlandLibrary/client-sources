/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.util.ByteProcessor;

@Deprecated
public interface ByteBufProcessor
extends ByteProcessor {
    @Deprecated
    public static final ByteBufProcessor FIND_NUL = new ByteBufProcessor(){

        @Override
        public boolean process(byte by) throws Exception {
            return by != 0;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_NON_NUL = new ByteBufProcessor(){

        @Override
        public boolean process(byte by) throws Exception {
            return by == 0;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_CR = new ByteBufProcessor(){

        @Override
        public boolean process(byte by) throws Exception {
            return by != 13;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_NON_CR = new ByteBufProcessor(){

        @Override
        public boolean process(byte by) throws Exception {
            return by == 13;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_LF = new ByteBufProcessor(){

        @Override
        public boolean process(byte by) throws Exception {
            return by != 10;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_NON_LF = new ByteBufProcessor(){

        @Override
        public boolean process(byte by) throws Exception {
            return by == 10;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_CRLF = new ByteBufProcessor(){

        @Override
        public boolean process(byte by) throws Exception {
            return by != 13 && by != 10;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_NON_CRLF = new ByteBufProcessor(){

        @Override
        public boolean process(byte by) throws Exception {
            return by == 13 || by == 10;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_LINEAR_WHITESPACE = new ByteBufProcessor(){

        @Override
        public boolean process(byte by) throws Exception {
            return by != 32 && by != 9;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_NON_LINEAR_WHITESPACE = new ByteBufProcessor(){

        @Override
        public boolean process(byte by) throws Exception {
            return by == 32 || by == 9;
        }
    };
}

