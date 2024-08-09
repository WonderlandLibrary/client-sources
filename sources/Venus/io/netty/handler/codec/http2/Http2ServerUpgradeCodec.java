/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.base64.Base64Dialect;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpServerUpgradeHandler;
import io.netty.handler.codec.http2.DefaultHttp2FrameReader;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2FrameAdapter;
import io.netty.handler.codec.http2.Http2FrameCodec;
import io.netty.handler.codec.http2.Http2FrameReader;
import io.netty.handler.codec.http2.Http2MultiplexCodec;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Http2ServerUpgradeCodec
implements HttpServerUpgradeHandler.UpgradeCodec {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Http2ServerUpgradeCodec.class);
    private static final List<CharSequence> REQUIRED_UPGRADE_HEADERS = Collections.singletonList(Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER);
    private static final ChannelHandler[] EMPTY_HANDLERS = new ChannelHandler[0];
    private final String handlerName;
    private final Http2ConnectionHandler connectionHandler;
    private final ChannelHandler[] handlers;
    private final Http2FrameReader frameReader;
    private Http2Settings settings;

    public Http2ServerUpgradeCodec(Http2ConnectionHandler http2ConnectionHandler) {
        this(null, http2ConnectionHandler, EMPTY_HANDLERS);
    }

    public Http2ServerUpgradeCodec(Http2MultiplexCodec http2MultiplexCodec) {
        this(null, http2MultiplexCodec, EMPTY_HANDLERS);
    }

    public Http2ServerUpgradeCodec(String string, Http2ConnectionHandler http2ConnectionHandler) {
        this(string, http2ConnectionHandler, EMPTY_HANDLERS);
    }

    public Http2ServerUpgradeCodec(String string, Http2MultiplexCodec http2MultiplexCodec) {
        this(string, http2MultiplexCodec, EMPTY_HANDLERS);
    }

    public Http2ServerUpgradeCodec(Http2FrameCodec http2FrameCodec, ChannelHandler ... channelHandlerArray) {
        this(null, http2FrameCodec, channelHandlerArray);
    }

    private Http2ServerUpgradeCodec(String string, Http2ConnectionHandler http2ConnectionHandler, ChannelHandler ... channelHandlerArray) {
        this.handlerName = string;
        this.connectionHandler = http2ConnectionHandler;
        this.handlers = channelHandlerArray;
        this.frameReader = new DefaultHttp2FrameReader();
    }

    @Override
    public Collection<CharSequence> requiredUpgradeHeaders() {
        return REQUIRED_UPGRADE_HEADERS;
    }

    @Override
    public boolean prepareUpgradeResponse(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest, HttpHeaders httpHeaders) {
        try {
            List<String> list = fullHttpRequest.headers().getAll(Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER);
            if (list.isEmpty() || list.size() > 1) {
                throw new IllegalArgumentException("There must be 1 and only 1 " + Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER + " header.");
            }
            this.settings = this.decodeSettingsHeader(channelHandlerContext, list.get(0));
            return true;
        } catch (Throwable throwable) {
            logger.info("Error during upgrade to HTTP/2", throwable);
            return true;
        }
    }

    @Override
    public void upgradeTo(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) {
        try {
            channelHandlerContext.pipeline().addAfter(channelHandlerContext.name(), this.handlerName, this.connectionHandler);
            this.connectionHandler.onHttpServerUpgrade(this.settings);
        } catch (Http2Exception http2Exception) {
            channelHandlerContext.fireExceptionCaught(http2Exception);
            channelHandlerContext.close();
            return;
        }
        if (this.handlers != null) {
            String string = channelHandlerContext.pipeline().context(this.connectionHandler).name();
            for (int i = this.handlers.length - 1; i >= 0; --i) {
                channelHandlerContext.pipeline().addAfter(string, null, this.handlers[i]);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Http2Settings decodeSettingsHeader(ChannelHandlerContext channelHandlerContext, CharSequence charSequence) throws Http2Exception {
        ByteBuf byteBuf = ByteBufUtil.encodeString(channelHandlerContext.alloc(), CharBuffer.wrap(charSequence), CharsetUtil.UTF_8);
        try {
            ByteBuf byteBuf2 = Base64.decode(byteBuf, Base64Dialect.URL_SAFE);
            ByteBuf byteBuf3 = Http2ServerUpgradeCodec.createSettingsFrame(channelHandlerContext, byteBuf2);
            Http2Settings http2Settings = this.decodeSettings(channelHandlerContext, byteBuf3);
            return http2Settings;
        } finally {
            byteBuf.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Http2Settings decodeSettings(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Http2Exception {
        try {
            Http2Settings http2Settings = new Http2Settings();
            this.frameReader.readFrame(channelHandlerContext, byteBuf, new Http2FrameAdapter(this, http2Settings){
                final Http2Settings val$decodedSettings;
                final Http2ServerUpgradeCodec this$0;
                {
                    this.this$0 = http2ServerUpgradeCodec;
                    this.val$decodedSettings = http2Settings;
                }

                @Override
                public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) {
                    this.val$decodedSettings.copyFrom(http2Settings);
                }
            });
            Http2Settings http2Settings2 = http2Settings;
            return http2Settings2;
        } finally {
            byteBuf.release();
        }
    }

    private static ByteBuf createSettingsFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer(9 + byteBuf.readableBytes());
        Http2CodecUtil.writeFrameHeader(byteBuf2, byteBuf.readableBytes(), (byte)4, new Http2Flags(), 0);
        byteBuf2.writeBytes(byteBuf);
        byteBuf.release();
        return byteBuf2;
    }
}

