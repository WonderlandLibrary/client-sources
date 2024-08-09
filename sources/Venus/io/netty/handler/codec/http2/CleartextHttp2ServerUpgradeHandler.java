/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerUpgradeHandler;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

public final class CleartextHttp2ServerUpgradeHandler
extends ChannelHandlerAdapter {
    private static final ByteBuf CONNECTION_PREFACE = Unpooled.unreleasableBuffer(Http2CodecUtil.connectionPrefaceBuf());
    private final HttpServerCodec httpServerCodec;
    private final HttpServerUpgradeHandler httpServerUpgradeHandler;
    private final ChannelHandler http2ServerHandler;

    public CleartextHttp2ServerUpgradeHandler(HttpServerCodec httpServerCodec, HttpServerUpgradeHandler httpServerUpgradeHandler, ChannelHandler channelHandler) {
        this.httpServerCodec = ObjectUtil.checkNotNull(httpServerCodec, "httpServerCodec");
        this.httpServerUpgradeHandler = ObjectUtil.checkNotNull(httpServerUpgradeHandler, "httpServerUpgradeHandler");
        this.http2ServerHandler = ObjectUtil.checkNotNull(channelHandler, "http2ServerHandler");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), null, new PriorKnowledgeHandler(this, null)).addBefore(channelHandlerContext.name(), null, this.httpServerCodec).replace(this, null, (ChannelHandler)this.httpServerUpgradeHandler);
    }

    static ByteBuf access$100() {
        return CONNECTION_PREFACE;
    }

    static HttpServerUpgradeHandler access$200(CleartextHttp2ServerUpgradeHandler cleartextHttp2ServerUpgradeHandler) {
        return cleartextHttp2ServerUpgradeHandler.httpServerUpgradeHandler;
    }

    static HttpServerCodec access$300(CleartextHttp2ServerUpgradeHandler cleartextHttp2ServerUpgradeHandler) {
        return cleartextHttp2ServerUpgradeHandler.httpServerCodec;
    }

    static ChannelHandler access$400(CleartextHttp2ServerUpgradeHandler cleartextHttp2ServerUpgradeHandler) {
        return cleartextHttp2ServerUpgradeHandler.http2ServerHandler;
    }

    public static final class PriorKnowledgeUpgradeEvent {
        private static final PriorKnowledgeUpgradeEvent INSTANCE = new PriorKnowledgeUpgradeEvent();

        private PriorKnowledgeUpgradeEvent() {
        }

        static PriorKnowledgeUpgradeEvent access$500() {
            return INSTANCE;
        }
    }

    private final class PriorKnowledgeHandler
    extends ByteToMessageDecoder {
        final CleartextHttp2ServerUpgradeHandler this$0;

        private PriorKnowledgeHandler(CleartextHttp2ServerUpgradeHandler cleartextHttp2ServerUpgradeHandler) {
            this.this$0 = cleartextHttp2ServerUpgradeHandler;
        }

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            int n = CleartextHttp2ServerUpgradeHandler.access$100().readableBytes();
            int n2 = Math.min(byteBuf.readableBytes(), n);
            if (!ByteBufUtil.equals(CleartextHttp2ServerUpgradeHandler.access$100(), CleartextHttp2ServerUpgradeHandler.access$100().readerIndex(), byteBuf, byteBuf.readerIndex(), n2)) {
                channelHandlerContext.pipeline().remove(this);
            } else if (n2 == n) {
                channelHandlerContext.pipeline().remove(CleartextHttp2ServerUpgradeHandler.access$300(this.this$0)).remove(CleartextHttp2ServerUpgradeHandler.access$200(this.this$0));
                channelHandlerContext.pipeline().addAfter(channelHandlerContext.name(), null, CleartextHttp2ServerUpgradeHandler.access$400(this.this$0));
                channelHandlerContext.pipeline().remove(this);
                channelHandlerContext.fireUserEventTriggered(PriorKnowledgeUpgradeEvent.access$500());
            }
        }

        PriorKnowledgeHandler(CleartextHttp2ServerUpgradeHandler cleartextHttp2ServerUpgradeHandler, 1 var2_2) {
            this(cleartextHttp2ServerUpgradeHandler);
        }
    }
}

