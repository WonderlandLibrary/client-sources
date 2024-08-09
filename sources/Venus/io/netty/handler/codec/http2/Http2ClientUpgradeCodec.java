/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.base64.Base64Dialect;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientUpgradeHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.netty.handler.codec.http2.Http2FrameCodec;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.collection.CharObjectMap;
import io.netty.util.internal.ObjectUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Http2ClientUpgradeCodec
implements HttpClientUpgradeHandler.UpgradeCodec {
    private static final List<CharSequence> UPGRADE_HEADERS = Collections.singletonList(Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER);
    private final String handlerName;
    private final Http2ConnectionHandler connectionHandler;
    private final ChannelHandler upgradeToHandler;

    public Http2ClientUpgradeCodec(Http2FrameCodec http2FrameCodec, ChannelHandler channelHandler) {
        this(null, http2FrameCodec, channelHandler);
    }

    public Http2ClientUpgradeCodec(String string, Http2FrameCodec http2FrameCodec, ChannelHandler channelHandler) {
        this(string, (Http2ConnectionHandler)http2FrameCodec, channelHandler);
    }

    public Http2ClientUpgradeCodec(Http2ConnectionHandler http2ConnectionHandler) {
        this((String)null, http2ConnectionHandler);
    }

    public Http2ClientUpgradeCodec(String string, Http2ConnectionHandler http2ConnectionHandler) {
        this(string, http2ConnectionHandler, (ChannelHandler)http2ConnectionHandler);
    }

    private Http2ClientUpgradeCodec(String string, Http2ConnectionHandler http2ConnectionHandler, ChannelHandler channelHandler) {
        this.handlerName = string;
        this.connectionHandler = ObjectUtil.checkNotNull(http2ConnectionHandler, "connectionHandler");
        this.upgradeToHandler = ObjectUtil.checkNotNull(channelHandler, "upgradeToHandler");
    }

    @Override
    public CharSequence protocol() {
        return Http2CodecUtil.HTTP_UPGRADE_PROTOCOL_NAME;
    }

    @Override
    public Collection<CharSequence> setUpgradeHeaders(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) {
        CharSequence charSequence = this.getSettingsHeaderValue(channelHandlerContext);
        httpRequest.headers().set(Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER, (Object)charSequence);
        return UPGRADE_HEADERS;
    }

    @Override
    public void upgradeTo(ChannelHandlerContext channelHandlerContext, FullHttpResponse fullHttpResponse) throws Exception {
        channelHandlerContext.pipeline().addAfter(channelHandlerContext.name(), this.handlerName, this.upgradeToHandler);
        this.connectionHandler.onHttpClientUpgrade();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CharSequence getSettingsHeaderValue(ChannelHandlerContext channelHandlerContext) {
        String string;
        ByteBuf byteBuf = null;
        ByteBuf byteBuf2 = null;
        try {
            Http2Settings http2Settings = this.connectionHandler.decoder().localSettings();
            int n = 6 * http2Settings.size();
            byteBuf = channelHandlerContext.alloc().buffer(n);
            for (CharObjectMap.PrimitiveEntry primitiveEntry : http2Settings.entries()) {
                byteBuf.writeChar(primitiveEntry.key());
                byteBuf.writeInt(((Long)primitiveEntry.value()).intValue());
            }
            byteBuf2 = Base64.encode(byteBuf, Base64Dialect.URL_SAFE);
            string = byteBuf2.toString(CharsetUtil.UTF_8);
        } catch (Throwable throwable) {
            ReferenceCountUtil.release(byteBuf);
            ReferenceCountUtil.release(byteBuf2);
            throw throwable;
        }
        ReferenceCountUtil.release(byteBuf);
        ReferenceCountUtil.release(byteBuf2);
        return string;
    }
}

