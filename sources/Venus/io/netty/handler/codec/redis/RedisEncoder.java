/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.redis.ArrayHeaderRedisMessage;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.BulkStringHeaderRedisMessage;
import io.netty.handler.codec.redis.BulkStringRedisContent;
import io.netty.handler.codec.redis.ErrorRedisMessage;
import io.netty.handler.codec.redis.FixedRedisMessagePool;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.InlineCommandRedisMessage;
import io.netty.handler.codec.redis.IntegerRedisMessage;
import io.netty.handler.codec.redis.LastBulkStringRedisContent;
import io.netty.handler.codec.redis.RedisCodecUtil;
import io.netty.handler.codec.redis.RedisConstants;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.handler.codec.redis.RedisMessagePool;
import io.netty.handler.codec.redis.RedisMessageType;
import io.netty.handler.codec.redis.SimpleStringRedisMessage;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

public class RedisEncoder
extends MessageToMessageEncoder<RedisMessage> {
    private final RedisMessagePool messagePool;

    public RedisEncoder() {
        this(FixedRedisMessagePool.INSTANCE);
    }

    public RedisEncoder(RedisMessagePool redisMessagePool) {
        this.messagePool = ObjectUtil.checkNotNull(redisMessagePool, "messagePool");
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RedisMessage redisMessage, List<Object> list) throws Exception {
        try {
            this.writeRedisMessage(channelHandlerContext.alloc(), redisMessage, list);
        } catch (CodecException codecException) {
            throw codecException;
        } catch (Exception exception) {
            throw new CodecException(exception);
        }
    }

    private void writeRedisMessage(ByteBufAllocator byteBufAllocator, RedisMessage redisMessage, List<Object> list) {
        if (redisMessage instanceof InlineCommandRedisMessage) {
            RedisEncoder.writeInlineCommandMessage(byteBufAllocator, (InlineCommandRedisMessage)redisMessage, list);
        } else if (redisMessage instanceof SimpleStringRedisMessage) {
            RedisEncoder.writeSimpleStringMessage(byteBufAllocator, (SimpleStringRedisMessage)redisMessage, list);
        } else if (redisMessage instanceof ErrorRedisMessage) {
            RedisEncoder.writeErrorMessage(byteBufAllocator, (ErrorRedisMessage)redisMessage, list);
        } else if (redisMessage instanceof IntegerRedisMessage) {
            this.writeIntegerMessage(byteBufAllocator, (IntegerRedisMessage)redisMessage, list);
        } else if (redisMessage instanceof FullBulkStringRedisMessage) {
            this.writeFullBulkStringMessage(byteBufAllocator, (FullBulkStringRedisMessage)redisMessage, list);
        } else if (redisMessage instanceof BulkStringRedisContent) {
            RedisEncoder.writeBulkStringContent(byteBufAllocator, (BulkStringRedisContent)redisMessage, list);
        } else if (redisMessage instanceof BulkStringHeaderRedisMessage) {
            this.writeBulkStringHeader(byteBufAllocator, (BulkStringHeaderRedisMessage)redisMessage, list);
        } else if (redisMessage instanceof ArrayHeaderRedisMessage) {
            this.writeArrayHeader(byteBufAllocator, (ArrayHeaderRedisMessage)redisMessage, list);
        } else if (redisMessage instanceof ArrayRedisMessage) {
            this.writeArrayMessage(byteBufAllocator, (ArrayRedisMessage)redisMessage, list);
        } else {
            throw new CodecException("unknown message type: " + redisMessage);
        }
    }

    private static void writeInlineCommandMessage(ByteBufAllocator byteBufAllocator, InlineCommandRedisMessage inlineCommandRedisMessage, List<Object> list) {
        RedisEncoder.writeString(byteBufAllocator, RedisMessageType.INLINE_COMMAND, inlineCommandRedisMessage.content(), list);
    }

    private static void writeSimpleStringMessage(ByteBufAllocator byteBufAllocator, SimpleStringRedisMessage simpleStringRedisMessage, List<Object> list) {
        RedisEncoder.writeString(byteBufAllocator, RedisMessageType.SIMPLE_STRING, simpleStringRedisMessage.content(), list);
    }

    private static void writeErrorMessage(ByteBufAllocator byteBufAllocator, ErrorRedisMessage errorRedisMessage, List<Object> list) {
        RedisEncoder.writeString(byteBufAllocator, RedisMessageType.ERROR, errorRedisMessage.content(), list);
    }

    private static void writeString(ByteBufAllocator byteBufAllocator, RedisMessageType redisMessageType, String string, List<Object> list) {
        ByteBuf byteBuf = byteBufAllocator.ioBuffer(redisMessageType.length() + ByteBufUtil.utf8MaxBytes(string) + 2);
        redisMessageType.writeTo(byteBuf);
        ByteBufUtil.writeUtf8(byteBuf, (CharSequence)string);
        byteBuf.writeShort(RedisConstants.EOL_SHORT);
        list.add(byteBuf);
    }

    private void writeIntegerMessage(ByteBufAllocator byteBufAllocator, IntegerRedisMessage integerRedisMessage, List<Object> list) {
        ByteBuf byteBuf = byteBufAllocator.ioBuffer(23);
        RedisMessageType.INTEGER.writeTo(byteBuf);
        byteBuf.writeBytes(this.numberToBytes(integerRedisMessage.value()));
        byteBuf.writeShort(RedisConstants.EOL_SHORT);
        list.add(byteBuf);
    }

    private void writeBulkStringHeader(ByteBufAllocator byteBufAllocator, BulkStringHeaderRedisMessage bulkStringHeaderRedisMessage, List<Object> list) {
        ByteBuf byteBuf = byteBufAllocator.ioBuffer(1 + (bulkStringHeaderRedisMessage.isNull() ? 2 : 22));
        RedisMessageType.BULK_STRING.writeTo(byteBuf);
        if (bulkStringHeaderRedisMessage.isNull()) {
            byteBuf.writeShort(RedisConstants.NULL_SHORT);
        } else {
            byteBuf.writeBytes(this.numberToBytes(bulkStringHeaderRedisMessage.bulkStringLength()));
            byteBuf.writeShort(RedisConstants.EOL_SHORT);
        }
        list.add(byteBuf);
    }

    private static void writeBulkStringContent(ByteBufAllocator byteBufAllocator, BulkStringRedisContent bulkStringRedisContent, List<Object> list) {
        list.add(bulkStringRedisContent.content().retain());
        if (bulkStringRedisContent instanceof LastBulkStringRedisContent) {
            list.add(byteBufAllocator.ioBuffer(2).writeShort(RedisConstants.EOL_SHORT));
        }
    }

    private void writeFullBulkStringMessage(ByteBufAllocator byteBufAllocator, FullBulkStringRedisMessage fullBulkStringRedisMessage, List<Object> list) {
        if (fullBulkStringRedisMessage.isNull()) {
            ByteBuf byteBuf = byteBufAllocator.ioBuffer(5);
            RedisMessageType.BULK_STRING.writeTo(byteBuf);
            byteBuf.writeShort(RedisConstants.NULL_SHORT);
            byteBuf.writeShort(RedisConstants.EOL_SHORT);
            list.add(byteBuf);
        } else {
            ByteBuf byteBuf = byteBufAllocator.ioBuffer(23);
            RedisMessageType.BULK_STRING.writeTo(byteBuf);
            byteBuf.writeBytes(this.numberToBytes(fullBulkStringRedisMessage.content().readableBytes()));
            byteBuf.writeShort(RedisConstants.EOL_SHORT);
            list.add(byteBuf);
            list.add(fullBulkStringRedisMessage.content().retain());
            list.add(byteBufAllocator.ioBuffer(2).writeShort(RedisConstants.EOL_SHORT));
        }
    }

    private void writeArrayHeader(ByteBufAllocator byteBufAllocator, ArrayHeaderRedisMessage arrayHeaderRedisMessage, List<Object> list) {
        this.writeArrayHeader(byteBufAllocator, arrayHeaderRedisMessage.isNull(), arrayHeaderRedisMessage.length(), list);
    }

    private void writeArrayMessage(ByteBufAllocator byteBufAllocator, ArrayRedisMessage arrayRedisMessage, List<Object> list) {
        if (arrayRedisMessage.isNull()) {
            this.writeArrayHeader(byteBufAllocator, arrayRedisMessage.isNull(), -1L, list);
        } else {
            this.writeArrayHeader(byteBufAllocator, arrayRedisMessage.isNull(), arrayRedisMessage.children().size(), list);
            for (RedisMessage redisMessage : arrayRedisMessage.children()) {
                this.writeRedisMessage(byteBufAllocator, redisMessage, list);
            }
        }
    }

    private void writeArrayHeader(ByteBufAllocator byteBufAllocator, boolean bl, long l, List<Object> list) {
        if (bl) {
            ByteBuf byteBuf = byteBufAllocator.ioBuffer(5);
            RedisMessageType.ARRAY_HEADER.writeTo(byteBuf);
            byteBuf.writeShort(RedisConstants.NULL_SHORT);
            byteBuf.writeShort(RedisConstants.EOL_SHORT);
            list.add(byteBuf);
        } else {
            ByteBuf byteBuf = byteBufAllocator.ioBuffer(23);
            RedisMessageType.ARRAY_HEADER.writeTo(byteBuf);
            byteBuf.writeBytes(this.numberToBytes(l));
            byteBuf.writeShort(RedisConstants.EOL_SHORT);
            list.add(byteBuf);
        }
    }

    private byte[] numberToBytes(long l) {
        byte[] byArray = this.messagePool.getByteBufOfInteger(l);
        return byArray != null ? byArray : RedisCodecUtil.longToAsciiBytes(l);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (RedisMessage)object, (List<Object>)list);
    }
}

