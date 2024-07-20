/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;

final class HttpHeadersEncoder {
    private HttpHeadersEncoder() {
    }

    public static void encoderHeader(CharSequence name, CharSequence value, ByteBuf buf) throws Exception {
        int nameLen = name.length();
        int valueLen = value.length();
        int entryLen = nameLen + valueLen + 4;
        buf.ensureWritable(entryLen);
        int offset = buf.writerIndex();
        HttpHeadersEncoder.writeAscii(buf, offset, name, nameLen);
        offset += nameLen;
        buf.setByte(offset++, 58);
        buf.setByte(offset++, 32);
        HttpHeadersEncoder.writeAscii(buf, offset, value, valueLen);
        offset += valueLen;
        buf.setByte(offset++, 13);
        buf.setByte(offset++, 10);
        buf.writerIndex(offset);
    }

    private static void writeAscii(ByteBuf buf, int offset, CharSequence value, int valueLen) {
        if (value instanceof AsciiString) {
            ByteBufUtil.copy((AsciiString)value, 0, buf, offset, valueLen);
        } else {
            HttpHeadersEncoder.writeCharSequence(buf, offset, value, valueLen);
        }
    }

    private static void writeCharSequence(ByteBuf buf, int offset, CharSequence value, int valueLen) {
        for (int i = 0; i < valueLen; ++i) {
            buf.setByte(offset++, AsciiString.c2b(value.charAt(i)));
        }
    }
}

