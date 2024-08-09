/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectEncoder;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;

public class HttpRequestEncoder
extends HttpObjectEncoder<HttpRequest> {
    private static final char SLASH = '/';
    private static final char QUESTION_MARK = '?';
    private static final int SLASH_AND_SPACE_SHORT = 12064;
    private static final int SPACE_SLASH_AND_SPACE_MEDIUM = 0x202F20;

    @Override
    public boolean acceptOutboundMessage(Object object) throws Exception {
        return super.acceptOutboundMessage(object) && !(object instanceof HttpResponse);
    }

    @Override
    protected void encodeInitialLine(ByteBuf byteBuf, HttpRequest httpRequest) throws Exception {
        ByteBufUtil.copy(httpRequest.method().asciiName(), byteBuf);
        String string = httpRequest.uri();
        if (string.isEmpty()) {
            ByteBufUtil.writeMediumBE(byteBuf, 0x202F20);
        } else {
            CharSequence charSequence = string;
            boolean bl = false;
            int n = string.indexOf("://");
            if (n != -1 && string.charAt(0) != '/') {
                int n2 = string.indexOf(63, n += 3);
                if (n2 == -1) {
                    if (string.lastIndexOf(47) < n) {
                        bl = true;
                    }
                } else if (string.lastIndexOf(47, n2) < n) {
                    charSequence = new StringBuilder(string).insert(n2, '/');
                }
            }
            byteBuf.writeByte(32).writeCharSequence(charSequence, CharsetUtil.UTF_8);
            if (bl) {
                ByteBufUtil.writeShortBE(byteBuf, 12064);
            } else {
                byteBuf.writeByte(32);
            }
        }
        httpRequest.protocolVersion().encode(byteBuf);
        ByteBufUtil.writeShortBE(byteBuf, 3338);
    }

    @Override
    protected void encodeInitialLine(ByteBuf byteBuf, HttpMessage httpMessage) throws Exception {
        this.encodeInitialLine(byteBuf, (HttpRequest)httpMessage);
    }
}

