/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectEncoder;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpStatusClass;

public class HttpResponseEncoder
extends HttpObjectEncoder<HttpResponse> {
    @Override
    public boolean acceptOutboundMessage(Object object) throws Exception {
        return super.acceptOutboundMessage(object) && !(object instanceof HttpRequest);
    }

    @Override
    protected void encodeInitialLine(ByteBuf byteBuf, HttpResponse httpResponse) throws Exception {
        httpResponse.protocolVersion().encode(byteBuf);
        byteBuf.writeByte(32);
        httpResponse.status().encode(byteBuf);
        ByteBufUtil.writeShortBE(byteBuf, 3338);
    }

    @Override
    protected void sanitizeHeadersBeforeEncode(HttpResponse httpResponse, boolean bl) {
        if (bl) {
            HttpResponseStatus httpResponseStatus = httpResponse.status();
            if (httpResponseStatus.codeClass() == HttpStatusClass.INFORMATIONAL || httpResponseStatus.code() == HttpResponseStatus.NO_CONTENT.code()) {
                httpResponse.headers().remove(HttpHeaderNames.CONTENT_LENGTH);
                httpResponse.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
            } else if (httpResponseStatus.code() == HttpResponseStatus.RESET_CONTENT.code()) {
                httpResponse.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
                httpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, 0);
            }
        }
    }

    @Override
    protected boolean isContentAlwaysEmpty(HttpResponse httpResponse) {
        HttpResponseStatus httpResponseStatus = httpResponse.status();
        if (httpResponseStatus.codeClass() == HttpStatusClass.INFORMATIONAL) {
            if (httpResponseStatus.code() == HttpResponseStatus.SWITCHING_PROTOCOLS.code()) {
                return httpResponse.headers().contains(HttpHeaderNames.SEC_WEBSOCKET_VERSION);
            }
            return false;
        }
        return httpResponseStatus.code() == HttpResponseStatus.NO_CONTENT.code() || httpResponseStatus.code() == HttpResponseStatus.NOT_MODIFIED.code() || httpResponseStatus.code() == HttpResponseStatus.RESET_CONTENT.code();
    }

    @Override
    protected void encodeInitialLine(ByteBuf byteBuf, HttpMessage httpMessage) throws Exception {
        this.encodeInitialLine(byteBuf, (HttpResponse)httpMessage);
    }

    @Override
    protected boolean isContentAlwaysEmpty(HttpMessage httpMessage) {
        return this.isContentAlwaysEmpty((HttpResponse)httpMessage);
    }

    @Override
    protected void sanitizeHeadersBeforeEncode(HttpMessage httpMessage, boolean bl) {
        this.sanitizeHeadersBeforeEncode((HttpResponse)httpMessage, bl);
    }
}

