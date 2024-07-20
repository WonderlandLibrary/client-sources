/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import java.util.Map;

public final class AsciiHeadersEncoder {
    private final ByteBuf buf;
    private final SeparatorType separatorType;
    private final NewlineType newlineType;

    public AsciiHeadersEncoder(ByteBuf buf) {
        this(buf, SeparatorType.COLON_SPACE, NewlineType.CRLF);
    }

    public AsciiHeadersEncoder(ByteBuf buf, SeparatorType separatorType, NewlineType newlineType) {
        if (buf == null) {
            throw new NullPointerException("buf");
        }
        if (separatorType == null) {
            throw new NullPointerException("separatorType");
        }
        if (newlineType == null) {
            throw new NullPointerException("newlineType");
        }
        this.buf = buf;
        this.separatorType = separatorType;
        this.newlineType = newlineType;
    }

    public void encode(Map.Entry<CharSequence, CharSequence> entry) {
        CharSequence name = entry.getKey();
        CharSequence value = entry.getValue();
        ByteBuf buf = this.buf;
        int nameLen = name.length();
        int valueLen = value.length();
        int entryLen = nameLen + valueLen + 4;
        int offset = buf.writerIndex();
        buf.ensureWritable(entryLen);
        AsciiHeadersEncoder.writeAscii(buf, offset, name, nameLen);
        offset += nameLen;
        switch (this.separatorType) {
            case COLON: {
                buf.setByte(offset++, 58);
                break;
            }
            case COLON_SPACE: {
                buf.setByte(offset++, 58);
                buf.setByte(offset++, 32);
                break;
            }
            default: {
                throw new Error();
            }
        }
        AsciiHeadersEncoder.writeAscii(buf, offset, value, valueLen);
        offset += valueLen;
        switch (this.newlineType) {
            case LF: {
                buf.setByte(offset++, 10);
                break;
            }
            case CRLF: {
                buf.setByte(offset++, 13);
                buf.setByte(offset++, 10);
                break;
            }
            default: {
                throw new Error();
            }
        }
        buf.writerIndex(offset);
    }

    private static void writeAscii(ByteBuf buf, int offset, CharSequence value, int valueLen) {
        if (value instanceof AsciiString) {
            AsciiHeadersEncoder.writeAsciiString(buf, offset, (AsciiString)value, valueLen);
        } else {
            AsciiHeadersEncoder.writeCharSequence(buf, offset, value, valueLen);
        }
    }

    private static void writeAsciiString(ByteBuf buf, int offset, AsciiString value, int valueLen) {
        ByteBufUtil.copy(value, 0, buf, offset, valueLen);
    }

    private static void writeCharSequence(ByteBuf buf, int offset, CharSequence value, int valueLen) {
        for (int i = 0; i < valueLen; ++i) {
            buf.setByte(offset++, AsciiHeadersEncoder.c2b(value.charAt(i)));
        }
    }

    private static int c2b(char ch) {
        return ch < '\u0100' ? (int)ch : 63;
    }

    public static enum NewlineType {
        LF,
        CRLF;

    }

    public static enum SeparatorType {
        COLON,
        COLON_SPACE;

    }
}

