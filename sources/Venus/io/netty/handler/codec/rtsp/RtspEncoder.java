/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.rtsp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectEncoder;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

public class RtspEncoder
extends HttpObjectEncoder<HttpMessage> {
    private static final int CRLF_SHORT = 3338;

    @Override
    public boolean acceptOutboundMessage(Object object) throws Exception {
        return super.acceptOutboundMessage(object) && (object instanceof HttpRequest || object instanceof HttpResponse);
    }

    @Override
    protected void encodeInitialLine(ByteBuf byteBuf, HttpMessage httpMessage) throws Exception {
        if (httpMessage instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest)httpMessage;
            ByteBufUtil.copy(httpRequest.method().asciiName(), byteBuf);
            byteBuf.writeByte(32);
            byteBuf.writeCharSequence(httpRequest.uri(), CharsetUtil.UTF_8);
            byteBuf.writeByte(32);
            byteBuf.writeCharSequence(httpRequest.protocolVersion().toString(), CharsetUtil.US_ASCII);
            ByteBufUtil.writeShortBE(byteBuf, 3338);
        } else if (httpMessage instanceof HttpResponse) {
            HttpResponse httpResponse = (HttpResponse)httpMessage;
            byteBuf.writeCharSequence(httpResponse.protocolVersion().toString(), CharsetUtil.US_ASCII);
            byteBuf.writeByte(32);
            ByteBufUtil.copy(httpResponse.status().codeAsText(), byteBuf);
            byteBuf.writeByte(32);
            byteBuf.writeCharSequence(httpResponse.status().reasonPhrase(), CharsetUtil.US_ASCII);
            ByteBufUtil.writeShortBE(byteBuf, 3338);
        } else {
            throw new UnsupportedMessageTypeException("Unsupported type " + StringUtil.simpleClassName(httpMessage));
        }
    }
}

