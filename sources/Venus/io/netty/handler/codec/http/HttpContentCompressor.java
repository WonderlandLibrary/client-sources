/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpContentEncoder;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;

public class HttpContentCompressor
extends HttpContentEncoder {
    private final int compressionLevel;
    private final int windowBits;
    private final int memLevel;
    private final int contentSizeThreshold;
    private ChannelHandlerContext ctx;

    public HttpContentCompressor() {
        this(6);
    }

    public HttpContentCompressor(int n) {
        this(n, 15, 8, 0);
    }

    public HttpContentCompressor(int n, int n2, int n3) {
        this(n, n2, n3, 0);
    }

    public HttpContentCompressor(int n, int n2, int n3, int n4) {
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        if (n2 < 9 || n2 > 15) {
            throw new IllegalArgumentException("windowBits: " + n2 + " (expected: 9-15)");
        }
        if (n3 < 1 || n3 > 9) {
            throw new IllegalArgumentException("memLevel: " + n3 + " (expected: 1-9)");
        }
        if (n4 < 0) {
            throw new IllegalArgumentException("contentSizeThreshold: " + n4 + " (expected: non negative number)");
        }
        this.compressionLevel = n;
        this.windowBits = n2;
        this.memLevel = n3;
        this.contentSizeThreshold = n4;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
    }

    @Override
    protected HttpContentEncoder.Result beginEncode(HttpResponse httpResponse, String string) throws Exception {
        String string2;
        if (this.contentSizeThreshold > 0 && httpResponse instanceof HttpContent && ((HttpContent)((Object)httpResponse)).content().readableBytes() < this.contentSizeThreshold) {
            return null;
        }
        String string3 = httpResponse.headers().get(HttpHeaderNames.CONTENT_ENCODING);
        if (string3 != null) {
            return null;
        }
        ZlibWrapper zlibWrapper = this.determineWrapper(string);
        if (zlibWrapper == null) {
            return null;
        }
        switch (1.$SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[zlibWrapper.ordinal()]) {
            case 1: {
                string2 = "gzip";
                break;
            }
            case 2: {
                string2 = "deflate";
                break;
            }
            default: {
                throw new Error();
            }
        }
        return new HttpContentEncoder.Result(string2, new EmbeddedChannel(this.ctx.channel().id(), this.ctx.channel().metadata().hasDisconnect(), this.ctx.channel().config(), ZlibCodecFactory.newZlibEncoder(zlibWrapper, this.compressionLevel, this.windowBits, this.memLevel)));
    }

    protected ZlibWrapper determineWrapper(String string) {
        float f = -1.0f;
        float f2 = -1.0f;
        float f3 = -1.0f;
        for (String string2 : string.split(",")) {
            float f4 = 1.0f;
            int n = string2.indexOf(61);
            if (n != -1) {
                try {
                    f4 = Float.parseFloat(string2.substring(n + 1));
                } catch (NumberFormatException numberFormatException) {
                    f4 = 0.0f;
                }
            }
            if (string2.contains("*")) {
                f = f4;
                continue;
            }
            if (string2.contains("gzip") && f4 > f2) {
                f2 = f4;
                continue;
            }
            if (!string2.contains("deflate") || !(f4 > f3)) continue;
            f3 = f4;
        }
        if (f2 > 0.0f || f3 > 0.0f) {
            if (f2 >= f3) {
                return ZlibWrapper.GZIP;
            }
            return ZlibWrapper.ZLIB;
        }
        if (f > 0.0f) {
            if (f2 == -1.0f) {
                return ZlibWrapper.GZIP;
            }
            if (f3 == -1.0f) {
                return ZlibWrapper.ZLIB;
            }
        }
        return null;
    }
}

