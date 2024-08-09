/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.memcache.LastMemcacheContent;
import io.netty.handler.codec.memcache.MemcacheContent;
import io.netty.handler.codec.memcache.MemcacheMessage;
import io.netty.handler.codec.memcache.MemcacheObject;
import io.netty.util.internal.StringUtil;
import java.util.List;

public abstract class AbstractMemcacheObjectEncoder<M extends MemcacheMessage>
extends MessageToMessageEncoder<Object> {
    private boolean expectingMoreContent;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List<Object> list) throws Exception {
        if (object instanceof MemcacheMessage) {
            if (this.expectingMoreContent) {
                throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(object));
            }
            MemcacheMessage memcacheMessage = (MemcacheMessage)object;
            list.add(this.encodeMessage(channelHandlerContext, memcacheMessage));
        }
        if (object instanceof MemcacheContent || object instanceof ByteBuf || object instanceof FileRegion) {
            int n = AbstractMemcacheObjectEncoder.contentLength(object);
            if (n > 0) {
                list.add(AbstractMemcacheObjectEncoder.encodeAndRetain(object));
            } else {
                list.add(Unpooled.EMPTY_BUFFER);
            }
            this.expectingMoreContent = !(object instanceof LastMemcacheContent);
        }
    }

    @Override
    public boolean acceptOutboundMessage(Object object) throws Exception {
        return object instanceof MemcacheObject || object instanceof ByteBuf || object instanceof FileRegion;
    }

    protected abstract ByteBuf encodeMessage(ChannelHandlerContext var1, M var2);

    private static int contentLength(Object object) {
        if (object instanceof MemcacheContent) {
            return ((MemcacheContent)object).content().readableBytes();
        }
        if (object instanceof ByteBuf) {
            return ((ByteBuf)object).readableBytes();
        }
        if (object instanceof FileRegion) {
            return (int)((FileRegion)object).count();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(object));
    }

    private static Object encodeAndRetain(Object object) {
        if (object instanceof ByteBuf) {
            return ((ByteBuf)object).retain();
        }
        if (object instanceof MemcacheContent) {
            return ((MemcacheContent)object).content().retain();
        }
        if (object instanceof FileRegion) {
            return ((FileRegion)object).retain();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(object));
    }
}

