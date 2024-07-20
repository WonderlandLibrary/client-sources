/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.rtsp;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectEncoder;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

public class RtspEncoder
extends HttpObjectEncoder<HttpMessage> {
    private static final byte[] CRLF = new byte[]{13, 10};

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return super.acceptOutboundMessage(msg) && (msg instanceof HttpRequest || msg instanceof HttpResponse);
    }

    @Override
    protected void encodeInitialLine(ByteBuf buf, HttpMessage message) throws Exception {
        if (message instanceof HttpRequest) {
            HttpRequest request = (HttpRequest)message;
            HttpHeaders.encodeAscii(request.method().toString(), buf);
            buf.writeByte(32);
            buf.writeBytes(request.uri().getBytes(CharsetUtil.UTF_8));
            buf.writeByte(32);
            HttpHeaders.encodeAscii(request.protocolVersion().toString(), buf);
            buf.writeBytes(CRLF);
        } else if (message instanceof HttpResponse) {
            HttpResponse response = (HttpResponse)message;
            HttpHeaders.encodeAscii(response.protocolVersion().toString(), buf);
            buf.writeByte(32);
            buf.writeBytes(String.valueOf(response.status().code()).getBytes(CharsetUtil.US_ASCII));
            buf.writeByte(32);
            HttpHeaders.encodeAscii(String.valueOf(response.status().reasonPhrase()), buf);
            buf.writeBytes(CRLF);
        } else {
            throw new UnsupportedMessageTypeException("Unsupported type " + StringUtil.simpleClassName(message));
        }
    }
}

