/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.redis.RedisCodecException;

public enum RedisMessageType {
    INLINE_COMMAND(null, true),
    SIMPLE_STRING((byte)43, true),
    ERROR((byte)45, true),
    INTEGER((byte)58, true),
    BULK_STRING((byte)36, false),
    ARRAY_HEADER((byte)42, false);

    private final Byte value;
    private final boolean inline;

    private RedisMessageType(Byte by, boolean bl) {
        this.value = by;
        this.inline = bl;
    }

    public int length() {
        return this.value != null ? 1 : 0;
    }

    public boolean isInline() {
        return this.inline;
    }

    public static RedisMessageType readFrom(ByteBuf byteBuf, boolean bl) {
        int n = byteBuf.readerIndex();
        RedisMessageType redisMessageType = RedisMessageType.valueOf(byteBuf.readByte());
        if (redisMessageType == INLINE_COMMAND) {
            if (!bl) {
                throw new RedisCodecException("Decoding of inline commands is disabled");
            }
            byteBuf.readerIndex(n);
        }
        return redisMessageType;
    }

    public void writeTo(ByteBuf byteBuf) {
        if (this.value == null) {
            return;
        }
        byteBuf.writeByte(this.value.byteValue());
    }

    private static RedisMessageType valueOf(byte by) {
        switch (by) {
            case 43: {
                return SIMPLE_STRING;
            }
            case 45: {
                return ERROR;
            }
            case 58: {
                return INTEGER;
            }
            case 36: {
                return BULK_STRING;
            }
            case 42: {
                return ARRAY_HEADER;
            }
        }
        return INLINE_COMMAND;
    }
}

