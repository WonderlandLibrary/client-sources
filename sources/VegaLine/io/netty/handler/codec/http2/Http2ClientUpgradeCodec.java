/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.base64.Base64Dialect;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientUpgradeHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
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

    public Http2ClientUpgradeCodec(Http2ConnectionHandler connectionHandler) {
        this(null, connectionHandler);
    }

    public Http2ClientUpgradeCodec(String handlerName, Http2ConnectionHandler connectionHandler) {
        this.handlerName = handlerName;
        this.connectionHandler = ObjectUtil.checkNotNull(connectionHandler, "connectionHandler");
    }

    @Override
    public CharSequence protocol() {
        return Http2CodecUtil.HTTP_UPGRADE_PROTOCOL_NAME;
    }

    @Override
    public Collection<CharSequence> setUpgradeHeaders(ChannelHandlerContext ctx, HttpRequest upgradeRequest) {
        CharSequence settingsValue = this.getSettingsHeaderValue(ctx);
        upgradeRequest.headers().set(Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER, (Object)settingsValue);
        return UPGRADE_HEADERS;
    }

    @Override
    public void upgradeTo(ChannelHandlerContext ctx, FullHttpResponse upgradeResponse) throws Exception {
        this.connectionHandler.onHttpClientUpgrade();
        ctx.pipeline().addAfter(ctx.name(), this.handlerName, this.connectionHandler);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CharSequence getSettingsHeaderValue(ChannelHandlerContext ctx) {
        String string;
        ByteBuf buf = null;
        ByteBuf encodedBuf = null;
        try {
            Http2Settings settings = this.connectionHandler.decoder().localSettings();
            int payloadLength = 6 * settings.size();
            buf = ctx.alloc().buffer(payloadLength);
            for (CharObjectMap.PrimitiveEntry entry : settings.entries()) {
                Http2CodecUtil.writeUnsignedShort(entry.key(), buf);
                Http2CodecUtil.writeUnsignedInt((Long)entry.value(), buf);
            }
            encodedBuf = Base64.encode(buf, Base64Dialect.URL_SAFE);
            string = encodedBuf.toString(CharsetUtil.UTF_8);
        } catch (Throwable throwable) {
            ReferenceCountUtil.release(buf);
            ReferenceCountUtil.release(encodedBuf);
            throw throwable;
        }
        ReferenceCountUtil.release(buf);
        ReferenceCountUtil.release(encodedBuf);
        return string;
    }
}

