/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public abstract class ApplicationProtocolNegotiationHandler
extends ChannelInboundHandlerAdapter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ApplicationProtocolNegotiationHandler.class);
    private final String fallbackProtocol;

    protected ApplicationProtocolNegotiationHandler(String string) {
        this.fallbackProtocol = ObjectUtil.checkNotNull(string, "fallbackProtocol");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (object instanceof SslHandshakeCompletionEvent) {
            channelHandlerContext.pipeline().remove(this);
            SslHandshakeCompletionEvent sslHandshakeCompletionEvent = (SslHandshakeCompletionEvent)object;
            if (sslHandshakeCompletionEvent.isSuccess()) {
                SslHandler sslHandler = channelHandlerContext.pipeline().get(SslHandler.class);
                if (sslHandler == null) {
                    throw new IllegalStateException("cannot find a SslHandler in the pipeline (required for application-level protocol negotiation)");
                }
                String string = sslHandler.applicationProtocol();
                this.configurePipeline(channelHandlerContext, string != null ? string : this.fallbackProtocol);
            } else {
                this.handshakeFailure(channelHandlerContext, sslHandshakeCompletionEvent.cause());
            }
        }
        channelHandlerContext.fireUserEventTriggered(object);
    }

    protected abstract void configurePipeline(ChannelHandlerContext var1, String var2) throws Exception;

    protected void handshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        logger.warn("{} TLS handshake failed:", (Object)channelHandlerContext.channel(), (Object)throwable);
        channelHandlerContext.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        logger.warn("{} Failed to select the application-level protocol:", (Object)channelHandlerContext.channel(), (Object)throwable);
        channelHandlerContext.close();
    }
}

