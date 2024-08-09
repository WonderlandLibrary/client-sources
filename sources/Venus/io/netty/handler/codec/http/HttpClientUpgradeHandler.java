/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.AsciiString;
import io.netty.util.ReferenceCountUtil;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public class HttpClientUpgradeHandler
extends HttpObjectAggregator
implements ChannelOutboundHandler {
    private final SourceCodec sourceCodec;
    private final UpgradeCodec upgradeCodec;
    private boolean upgradeRequested;
    static final boolean $assertionsDisabled = !HttpClientUpgradeHandler.class.desiredAssertionStatus();

    public HttpClientUpgradeHandler(SourceCodec sourceCodec, UpgradeCodec upgradeCodec, int n) {
        super(n);
        if (sourceCodec == null) {
            throw new NullPointerException("sourceCodec");
        }
        if (upgradeCodec == null) {
            throw new NullPointerException("upgradeCodec");
        }
        this.sourceCodec = sourceCodec;
        this.upgradeCodec = upgradeCodec;
    }

    @Override
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.disconnect(channelPromise);
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.close(channelPromise);
    }

    @Override
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    @Override
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.read();
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        if (!(object instanceof HttpRequest)) {
            channelHandlerContext.write(object, channelPromise);
            return;
        }
        if (this.upgradeRequested) {
            channelPromise.setFailure(new IllegalStateException("Attempting to write HTTP request with upgrade in progress"));
            return;
        }
        this.upgradeRequested = true;
        this.setUpgradeRequestHeaders(channelHandlerContext, (HttpRequest)object);
        channelHandlerContext.write(object, channelPromise);
        channelHandlerContext.fireUserEventTriggered((Object)UpgradeEvent.UPGRADE_ISSUED);
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List<Object> list) throws Exception {
        FullHttpResponse fullHttpResponse = null;
        try {
            Object object;
            if (!this.upgradeRequested) {
                throw new IllegalStateException("Read HTTP response without requesting protocol switch");
            }
            if (httpObject instanceof HttpResponse && !HttpResponseStatus.SWITCHING_PROTOCOLS.equals((object = (HttpResponse)httpObject).status())) {
                channelHandlerContext.fireUserEventTriggered((Object)UpgradeEvent.UPGRADE_REJECTED);
                HttpClientUpgradeHandler.removeThisHandler(channelHandlerContext);
                channelHandlerContext.fireChannelRead(httpObject);
                return;
            }
            if (httpObject instanceof FullHttpResponse) {
                fullHttpResponse = (FullHttpResponse)httpObject;
                fullHttpResponse.retain();
                list.add(fullHttpResponse);
            } else {
                super.decode(channelHandlerContext, httpObject, list);
                if (list.isEmpty()) {
                    return;
                }
                if (!$assertionsDisabled && list.size() != 1) {
                    throw new AssertionError();
                }
                fullHttpResponse = (FullHttpResponse)list.get(0);
            }
            object = fullHttpResponse.headers().get(HttpHeaderNames.UPGRADE);
            if (object != null && !AsciiString.contentEqualsIgnoreCase(this.upgradeCodec.protocol(), (CharSequence)object)) {
                throw new IllegalStateException("Switching Protocols response with unexpected UPGRADE protocol: " + object);
            }
            this.sourceCodec.prepareUpgradeFrom(channelHandlerContext);
            this.upgradeCodec.upgradeTo(channelHandlerContext, fullHttpResponse);
            channelHandlerContext.fireUserEventTriggered((Object)UpgradeEvent.UPGRADE_SUCCESSFUL);
            this.sourceCodec.upgradeFrom(channelHandlerContext);
            fullHttpResponse.release();
            list.clear();
            HttpClientUpgradeHandler.removeThisHandler(channelHandlerContext);
        } catch (Throwable throwable) {
            ReferenceCountUtil.release(fullHttpResponse);
            channelHandlerContext.fireExceptionCaught(throwable);
            HttpClientUpgradeHandler.removeThisHandler(channelHandlerContext);
        }
    }

    private static void removeThisHandler(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.pipeline().remove(channelHandlerContext.name());
    }

    private void setUpgradeRequestHeaders(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) {
        httpRequest.headers().set((CharSequence)HttpHeaderNames.UPGRADE, (Object)this.upgradeCodec.protocol());
        LinkedHashSet<CharSequence> linkedHashSet = new LinkedHashSet<CharSequence>(2);
        linkedHashSet.addAll(this.upgradeCodec.setUpgradeHeaders(channelHandlerContext, httpRequest));
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence charSequence : linkedHashSet) {
            stringBuilder.append(charSequence);
            stringBuilder.append(',');
        }
        stringBuilder.append(HttpHeaderValues.UPGRADE);
        httpRequest.headers().add((CharSequence)HttpHeaderNames.CONNECTION, (Object)stringBuilder.toString());
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (HttpObject)object, (List<Object>)list);
    }

    public static interface UpgradeCodec {
        public CharSequence protocol();

        public Collection<CharSequence> setUpgradeHeaders(ChannelHandlerContext var1, HttpRequest var2);

        public void upgradeTo(ChannelHandlerContext var1, FullHttpResponse var2) throws Exception;
    }

    public static interface SourceCodec {
        public void prepareUpgradeFrom(ChannelHandlerContext var1);

        public void upgradeFrom(ChannelHandlerContext var1);
    }

    public static enum UpgradeEvent {
        UPGRADE_ISSUED,
        UPGRADE_SUCCESSFUL,
        UPGRADE_REJECTED;

    }
}

