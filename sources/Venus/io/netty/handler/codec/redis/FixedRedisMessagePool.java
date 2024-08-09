/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.redis.AbstractStringRedisMessage;
import io.netty.handler.codec.redis.ErrorRedisMessage;
import io.netty.handler.codec.redis.IntegerRedisMessage;
import io.netty.handler.codec.redis.RedisCodecUtil;
import io.netty.handler.codec.redis.RedisMessagePool;
import io.netty.handler.codec.redis.SimpleStringRedisMessage;
import io.netty.util.CharsetUtil;
import io.netty.util.collection.LongObjectHashMap;
import io.netty.util.collection.LongObjectMap;
import java.util.HashMap;
import java.util.Map;

public final class FixedRedisMessagePool
implements RedisMessagePool {
    private static final String[] DEFAULT_SIMPLE_STRINGS = new String[]{"OK", "PONG", "QUEUED"};
    private static final String[] DEFAULT_ERRORS = new String[]{"ERR", "ERR index out of range", "ERR no such key", "ERR source and destination objects are the same", "ERR syntax error", "BUSY Redis is busy running a script. You can only call SCRIPT KILL or SHUTDOWN NOSAVE.", "BUSYKEY Target key name already exists.", "EXECABORT Transaction discarded because of previous errors.", "LOADING Redis is loading the dataset in memory", "MASTERDOWN Link with MASTER is down and slave-serve-stale-data is set to 'no'.", "MISCONF Redis is configured to save RDB snapshots, but is currently not able to persist on disk. Commands that may modify the data set are disabled. Please check Redis logs for details about the error.", "NOAUTH Authentication required.", "NOREPLICAS Not enough good slaves to write.", "NOSCRIPT No matching script. Please use EVAL.", "OOM command not allowed when used memory > 'maxmemory'.", "READONLY You can't write against a read only slave.", "WRONGTYPE Operation against a key holding the wrong kind of value"};
    private static final long MIN_CACHED_INTEGER_NUMBER = -1L;
    private static final long MAX_CACHED_INTEGER_NUMBER = 128L;
    private static final int SIZE_CACHED_INTEGER_NUMBER = 129;
    public static final FixedRedisMessagePool INSTANCE = new FixedRedisMessagePool();
    private final Map<ByteBuf, SimpleStringRedisMessage> byteBufToSimpleStrings = new HashMap<ByteBuf, SimpleStringRedisMessage>(DEFAULT_SIMPLE_STRINGS.length, 1.0f);
    private final Map<String, SimpleStringRedisMessage> stringToSimpleStrings = new HashMap<String, SimpleStringRedisMessage>(DEFAULT_SIMPLE_STRINGS.length, 1.0f);
    private final Map<ByteBuf, ErrorRedisMessage> byteBufToErrors;
    private final Map<String, ErrorRedisMessage> stringToErrors;
    private final Map<ByteBuf, IntegerRedisMessage> byteBufToIntegers;
    private final LongObjectMap<IntegerRedisMessage> longToIntegers;
    private final LongObjectMap<byte[]> longToByteBufs;

    private FixedRedisMessagePool() {
        AbstractStringRedisMessage abstractStringRedisMessage;
        Object object;
        for (String object2 : DEFAULT_SIMPLE_STRINGS) {
            object = Unpooled.unmodifiableBuffer(Unpooled.unreleasableBuffer(Unpooled.wrappedBuffer(object2.getBytes(CharsetUtil.UTF_8))));
            abstractStringRedisMessage = new SimpleStringRedisMessage(object2);
            this.byteBufToSimpleStrings.put((ByteBuf)object, (SimpleStringRedisMessage)abstractStringRedisMessage);
            this.stringToSimpleStrings.put(object2, (SimpleStringRedisMessage)abstractStringRedisMessage);
        }
        this.byteBufToErrors = new HashMap<ByteBuf, ErrorRedisMessage>(DEFAULT_ERRORS.length, 1.0f);
        this.stringToErrors = new HashMap<String, ErrorRedisMessage>(DEFAULT_ERRORS.length, 1.0f);
        for (String string : DEFAULT_ERRORS) {
            object = Unpooled.unmodifiableBuffer(Unpooled.unreleasableBuffer(Unpooled.wrappedBuffer(string.getBytes(CharsetUtil.UTF_8))));
            abstractStringRedisMessage = new ErrorRedisMessage(string);
            this.byteBufToErrors.put((ByteBuf)object, (ErrorRedisMessage)abstractStringRedisMessage);
            this.stringToErrors.put(string, (ErrorRedisMessage)abstractStringRedisMessage);
        }
        this.byteBufToIntegers = new HashMap<ByteBuf, IntegerRedisMessage>(129, 1.0f);
        this.longToIntegers = new LongObjectHashMap<IntegerRedisMessage>(129, 1.0f);
        this.longToByteBufs = new LongObjectHashMap<byte[]>(129, 1.0f);
        for (long i = -1L; i < 128L; ++i) {
            byte[] byArray = RedisCodecUtil.longToAsciiBytes(i);
            ByteBuf byteBuf = Unpooled.unmodifiableBuffer(Unpooled.unreleasableBuffer(Unpooled.wrappedBuffer(byArray)));
            object = new IntegerRedisMessage(i);
            this.byteBufToIntegers.put(byteBuf, (IntegerRedisMessage)object);
            this.longToIntegers.put(i, (IntegerRedisMessage)object);
            this.longToByteBufs.put(i, byArray);
        }
    }

    @Override
    public SimpleStringRedisMessage getSimpleString(String string) {
        return this.stringToSimpleStrings.get(string);
    }

    @Override
    public SimpleStringRedisMessage getSimpleString(ByteBuf byteBuf) {
        return this.byteBufToSimpleStrings.get(byteBuf);
    }

    @Override
    public ErrorRedisMessage getError(String string) {
        return this.stringToErrors.get(string);
    }

    @Override
    public ErrorRedisMessage getError(ByteBuf byteBuf) {
        return this.byteBufToErrors.get(byteBuf);
    }

    @Override
    public IntegerRedisMessage getInteger(long l) {
        return this.longToIntegers.get(l);
    }

    @Override
    public IntegerRedisMessage getInteger(ByteBuf byteBuf) {
        return this.byteBufToIntegers.get(byteBuf);
    }

    @Override
    public byte[] getByteBufOfInteger(long l) {
        return this.longToByteBufs.get(l);
    }
}

