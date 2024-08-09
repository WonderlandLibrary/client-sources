/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.redis.ArrayHeaderRedisMessage;
import io.netty.handler.codec.redis.BulkStringHeaderRedisMessage;
import io.netty.handler.codec.redis.DefaultBulkStringRedisContent;
import io.netty.handler.codec.redis.DefaultLastBulkStringRedisContent;
import io.netty.handler.codec.redis.ErrorRedisMessage;
import io.netty.handler.codec.redis.FixedRedisMessagePool;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.InlineCommandRedisMessage;
import io.netty.handler.codec.redis.IntegerRedisMessage;
import io.netty.handler.codec.redis.RedisCodecException;
import io.netty.handler.codec.redis.RedisCodecUtil;
import io.netty.handler.codec.redis.RedisConstants;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.handler.codec.redis.RedisMessagePool;
import io.netty.handler.codec.redis.RedisMessageType;
import io.netty.handler.codec.redis.SimpleStringRedisMessage;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import java.util.List;

public final class RedisDecoder
extends ByteToMessageDecoder {
    private final ToPositiveLongProcessor toPositiveLongProcessor = new ToPositiveLongProcessor(null);
    private final boolean decodeInlineCommands;
    private final int maxInlineMessageLength;
    private final RedisMessagePool messagePool;
    private State state = State.DECODE_TYPE;
    private RedisMessageType type;
    private int remainingBulkLength;

    public RedisDecoder() {
        this(false);
    }

    public RedisDecoder(boolean bl) {
        this(65536, FixedRedisMessagePool.INSTANCE, bl);
    }

    public RedisDecoder(int n, RedisMessagePool redisMessagePool) {
        this(n, redisMessagePool, false);
    }

    public RedisDecoder(int n, RedisMessagePool redisMessagePool, boolean bl) {
        if (n <= 0 || n > 0x20000000) {
            throw new RedisCodecException("maxInlineMessageLength: " + n + " (expected: <= " + 0x20000000 + ")");
        }
        this.maxInlineMessageLength = n;
        this.messagePool = redisMessagePool;
        this.decodeInlineCommands = bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            block10: while (true) {
                switch (this.state) {
                    case DECODE_TYPE: {
                        if (this.decodeType(byteBuf)) continue block10;
                        return;
                    }
                    case DECODE_INLINE: {
                        if (this.decodeInline(byteBuf, list)) continue block10;
                        return;
                    }
                    case DECODE_LENGTH: {
                        if (this.decodeLength(byteBuf, list)) continue block10;
                        return;
                    }
                    case DECODE_BULK_STRING_EOL: {
                        if (this.decodeBulkStringEndOfLine(byteBuf, list)) continue block10;
                        return;
                    }
                    case DECODE_BULK_STRING_CONTENT: {
                        if (!this.decodeBulkStringContent(byteBuf, list)) return;
                        continue block10;
                    }
                }
                break;
            }
            throw new RedisCodecException("Unknown state: " + (Object)((Object)this.state));
        } catch (RedisCodecException redisCodecException) {
            this.resetDecoder();
            throw redisCodecException;
        } catch (Exception exception) {
            this.resetDecoder();
            throw new RedisCodecException(exception);
        }
    }

    private void resetDecoder() {
        this.state = State.DECODE_TYPE;
        this.remainingBulkLength = 0;
    }

    private boolean decodeType(ByteBuf byteBuf) throws Exception {
        if (!byteBuf.isReadable()) {
            return true;
        }
        this.type = RedisMessageType.readFrom(byteBuf, this.decodeInlineCommands);
        this.state = this.type.isInline() ? State.DECODE_INLINE : State.DECODE_LENGTH;
        return false;
    }

    private boolean decodeInline(ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteBuf byteBuf2 = RedisDecoder.readLine(byteBuf);
        if (byteBuf2 == null) {
            if (byteBuf.readableBytes() > this.maxInlineMessageLength) {
                throw new RedisCodecException("length: " + byteBuf.readableBytes() + " (expected: <= " + this.maxInlineMessageLength + ")");
            }
            return true;
        }
        list.add(this.newInlineRedisMessage(this.type, byteBuf2));
        this.resetDecoder();
        return false;
    }

    private boolean decodeLength(ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteBuf byteBuf2 = RedisDecoder.readLine(byteBuf);
        if (byteBuf2 == null) {
            return true;
        }
        long l = this.parseRedisNumber(byteBuf2);
        if (l < -1L) {
            throw new RedisCodecException("length: " + l + " (expected: >= " + -1 + ")");
        }
        switch (this.type) {
            case ARRAY_HEADER: {
                list.add(new ArrayHeaderRedisMessage(l));
                this.resetDecoder();
                return false;
            }
            case BULK_STRING: {
                if (l > 0x20000000L) {
                    throw new RedisCodecException("length: " + l + " (expected: <= " + 0x20000000 + ")");
                }
                this.remainingBulkLength = (int)l;
                return this.decodeBulkString(byteBuf, list);
            }
        }
        throw new RedisCodecException("bad type: " + (Object)((Object)this.type));
    }

    private boolean decodeBulkString(ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (this.remainingBulkLength) {
            case -1: {
                list.add(FullBulkStringRedisMessage.NULL_INSTANCE);
                this.resetDecoder();
                return false;
            }
            case 0: {
                this.state = State.DECODE_BULK_STRING_EOL;
                return this.decodeBulkStringEndOfLine(byteBuf, list);
            }
        }
        list.add(new BulkStringHeaderRedisMessage(this.remainingBulkLength));
        this.state = State.DECODE_BULK_STRING_CONTENT;
        return this.decodeBulkStringContent(byteBuf, list);
    }

    private boolean decodeBulkStringEndOfLine(ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 2) {
            return true;
        }
        RedisDecoder.readEndOfLine(byteBuf);
        list.add(FullBulkStringRedisMessage.EMPTY_INSTANCE);
        this.resetDecoder();
        return false;
    }

    private boolean decodeBulkStringContent(ByteBuf byteBuf, List<Object> list) throws Exception {
        int n = byteBuf.readableBytes();
        if (n == 0 || this.remainingBulkLength == 0 && n < 2) {
            return true;
        }
        if (n >= this.remainingBulkLength + 2) {
            ByteBuf byteBuf2 = byteBuf.readSlice(this.remainingBulkLength);
            RedisDecoder.readEndOfLine(byteBuf);
            list.add(new DefaultLastBulkStringRedisContent(byteBuf2.retain()));
            this.resetDecoder();
            return false;
        }
        int n2 = Math.min(this.remainingBulkLength, n);
        this.remainingBulkLength -= n2;
        list.add(new DefaultBulkStringRedisContent(byteBuf.readSlice(n2).retain()));
        return false;
    }

    private static void readEndOfLine(ByteBuf byteBuf) {
        short s = byteBuf.readShort();
        if (RedisConstants.EOL_SHORT == s) {
            return;
        }
        byte[] byArray = RedisCodecUtil.shortToBytes(s);
        throw new RedisCodecException("delimiter: [" + byArray[0] + "," + byArray[1] + "] (expected: \\r\\n)");
    }

    private RedisMessage newInlineRedisMessage(RedisMessageType redisMessageType, ByteBuf byteBuf) {
        switch (redisMessageType) {
            case INLINE_COMMAND: {
                return new InlineCommandRedisMessage(byteBuf.toString(CharsetUtil.UTF_8));
            }
            case SIMPLE_STRING: {
                SimpleStringRedisMessage simpleStringRedisMessage = this.messagePool.getSimpleString(byteBuf);
                return simpleStringRedisMessage != null ? simpleStringRedisMessage : new SimpleStringRedisMessage(byteBuf.toString(CharsetUtil.UTF_8));
            }
            case ERROR: {
                ErrorRedisMessage errorRedisMessage = this.messagePool.getError(byteBuf);
                return errorRedisMessage != null ? errorRedisMessage : new ErrorRedisMessage(byteBuf.toString(CharsetUtil.UTF_8));
            }
            case INTEGER: {
                IntegerRedisMessage integerRedisMessage = this.messagePool.getInteger(byteBuf);
                return integerRedisMessage != null ? integerRedisMessage : new IntegerRedisMessage(this.parseRedisNumber(byteBuf));
            }
        }
        throw new RedisCodecException("bad type: " + (Object)((Object)redisMessageType));
    }

    private static ByteBuf readLine(ByteBuf byteBuf) {
        if (!byteBuf.isReadable(1)) {
            return null;
        }
        int n = byteBuf.forEachByte(ByteProcessor.FIND_LF);
        if (n < 0) {
            return null;
        }
        ByteBuf byteBuf2 = byteBuf.readSlice(n - byteBuf.readerIndex() - 1);
        RedisDecoder.readEndOfLine(byteBuf);
        return byteBuf2;
    }

    private long parseRedisNumber(ByteBuf byteBuf) {
        int n;
        int n2 = byteBuf.readableBytes();
        boolean bl = n2 > 0 && byteBuf.getByte(byteBuf.readerIndex()) == 45;
        int n3 = n = bl ? 1 : 0;
        if (n2 <= n) {
            throw new RedisCodecException("no number to parse: " + byteBuf.toString(CharsetUtil.US_ASCII));
        }
        if (n2 > 19 + n) {
            throw new RedisCodecException("too many characters to be a valid RESP Integer: " + byteBuf.toString(CharsetUtil.US_ASCII));
        }
        if (bl) {
            return -this.parsePositiveNumber(byteBuf.skipBytes(n));
        }
        return this.parsePositiveNumber(byteBuf);
    }

    private long parsePositiveNumber(ByteBuf byteBuf) {
        this.toPositiveLongProcessor.reset();
        byteBuf.forEachByte(this.toPositiveLongProcessor);
        return this.toPositiveLongProcessor.content();
    }

    private static final class ToPositiveLongProcessor
    implements ByteProcessor {
        private long result;

        private ToPositiveLongProcessor() {
        }

        @Override
        public boolean process(byte by) throws Exception {
            if (by < 48 || by > 57) {
                throw new RedisCodecException("bad byte in number: " + by);
            }
            this.result = this.result * 10L + (long)(by - 48);
            return false;
        }

        public long content() {
            return this.result;
        }

        public void reset() {
            this.result = 0L;
        }

        ToPositiveLongProcessor(1 var1_1) {
            this();
        }
    }

    private static enum State {
        DECODE_TYPE,
        DECODE_INLINE,
        DECODE_LENGTH,
        DECODE_BULK_STRING_EOL,
        DECODE_BULK_STRING_CONTENT;

    }
}

