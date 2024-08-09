/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;

final class HttpHeadersEncoder {
    private static final int COLON_AND_SPACE_SHORT = 14880;

    private HttpHeadersEncoder() {
    }

    static void encoderHeader(CharSequence charSequence, CharSequence charSequence2, ByteBuf byteBuf) {
        int n = charSequence.length();
        int n2 = charSequence2.length();
        int n3 = n + n2 + 4;
        byteBuf.ensureWritable(n3);
        int n4 = byteBuf.writerIndex();
        HttpHeadersEncoder.writeAscii(byteBuf, n4, charSequence);
        ByteBufUtil.setShortBE(byteBuf, n4 += n, 14880);
        HttpHeadersEncoder.writeAscii(byteBuf, n4 += 2, charSequence2);
        ByteBufUtil.setShortBE(byteBuf, n4 += n2, 3338);
        byteBuf.writerIndex(n4 += 2);
    }

    private static void writeAscii(ByteBuf byteBuf, int n, CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            ByteBufUtil.copy((AsciiString)charSequence, 0, byteBuf, n, charSequence.length());
        } else {
            byteBuf.setCharSequence(n, charSequence, CharsetUtil.US_ASCII);
        }
    }
}

