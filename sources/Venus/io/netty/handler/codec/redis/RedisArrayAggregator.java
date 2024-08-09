/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.redis.ArrayHeaderRedisMessage;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class RedisArrayAggregator
extends MessageToMessageDecoder<RedisMessage> {
    private final Deque<AggregateState> depths = new ArrayDeque<AggregateState>(4);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, RedisMessage redisMessage, List<Object> list) throws Exception {
        if (redisMessage instanceof ArrayHeaderRedisMessage) {
            if ((redisMessage = this.decodeRedisArrayHeader((ArrayHeaderRedisMessage)redisMessage)) == null) {
                return;
            }
        } else {
            ReferenceCountUtil.retain(redisMessage);
        }
        while (!this.depths.isEmpty()) {
            AggregateState aggregateState = this.depths.peek();
            AggregateState.access$000(aggregateState).add(redisMessage);
            if (AggregateState.access$000(aggregateState).size() == AggregateState.access$100(aggregateState)) {
                redisMessage = new ArrayRedisMessage(AggregateState.access$000(aggregateState));
                this.depths.pop();
                continue;
            }
            return;
        }
        list.add(redisMessage);
    }

    private RedisMessage decodeRedisArrayHeader(ArrayHeaderRedisMessage arrayHeaderRedisMessage) {
        if (arrayHeaderRedisMessage.isNull()) {
            return ArrayRedisMessage.NULL_INSTANCE;
        }
        if (arrayHeaderRedisMessage.length() == 0L) {
            return ArrayRedisMessage.EMPTY_INSTANCE;
        }
        if (arrayHeaderRedisMessage.length() > 0L) {
            if (arrayHeaderRedisMessage.length() > Integer.MAX_VALUE) {
                throw new CodecException("this codec doesn't support longer length than 2147483647");
            }
            this.depths.push(new AggregateState((int)arrayHeaderRedisMessage.length()));
            return null;
        }
        throw new CodecException("bad length: " + arrayHeaderRedisMessage.length());
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (RedisMessage)object, (List<Object>)list);
    }

    private static final class AggregateState {
        private final int length;
        private final List<RedisMessage> children;

        AggregateState(int n) {
            this.length = n;
            this.children = new ArrayList<RedisMessage>(n);
        }

        static List access$000(AggregateState aggregateState) {
            return aggregateState.children;
        }

        static int access$100(AggregateState aggregateState) {
            return aggregateState.length;
        }
    }
}

