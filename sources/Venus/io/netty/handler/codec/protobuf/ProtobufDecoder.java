/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.google.protobuf.ExtensionRegistry
 *  com.google.protobuf.ExtensionRegistryLite
 *  com.google.protobuf.MessageLite
 */
package io.netty.handler.codec.protobuf;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@ChannelHandler.Sharable
public class ProtobufDecoder
extends MessageToMessageDecoder<ByteBuf> {
    private static final boolean HAS_PARSER;
    private final MessageLite prototype;
    private final ExtensionRegistryLite extensionRegistry;

    public ProtobufDecoder(MessageLite messageLite) {
        this(messageLite, (ExtensionRegistry)null);
    }

    public ProtobufDecoder(MessageLite messageLite, ExtensionRegistry extensionRegistry) {
        this(messageLite, (ExtensionRegistryLite)extensionRegistry);
    }

    public ProtobufDecoder(MessageLite messageLite, ExtensionRegistryLite extensionRegistryLite) {
        if (messageLite == null) {
            throw new NullPointerException("prototype");
        }
        this.prototype = messageLite.getDefaultInstanceForType();
        this.extensionRegistry = extensionRegistryLite;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int n;
        byte[] byArray;
        int n2 = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            byArray = byteBuf.array();
            n = byteBuf.arrayOffset() + byteBuf.readerIndex();
        } else {
            byArray = new byte[n2];
            byteBuf.getBytes(byteBuf.readerIndex(), byArray, 0, n2);
            n = 0;
        }
        if (this.extensionRegistry == null) {
            if (HAS_PARSER) {
                list.add(this.prototype.getParserForType().parseFrom(byArray, n, n2));
            } else {
                list.add(this.prototype.newBuilderForType().mergeFrom(byArray, n, n2).build());
            }
        } else if (HAS_PARSER) {
            list.add(this.prototype.getParserForType().parseFrom(byArray, n, n2, this.extensionRegistry));
        } else {
            list.add(this.prototype.newBuilderForType().mergeFrom(byArray, n, n2, this.extensionRegistry).build());
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)object, (List<Object>)list);
    }

    static {
        boolean bl = false;
        try {
            MessageLite.class.getDeclaredMethod("getParserForType", new Class[0]);
            bl = true;
        } catch (Throwable throwable) {
            // empty catch block
        }
        HAS_PARSER = bl;
    }
}

