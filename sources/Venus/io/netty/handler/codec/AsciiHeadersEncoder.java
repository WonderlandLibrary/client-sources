/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import java.util.Map;

public final class AsciiHeadersEncoder {
    private final ByteBuf buf;
    private final SeparatorType separatorType;
    private final NewlineType newlineType;

    public AsciiHeadersEncoder(ByteBuf byteBuf) {
        this(byteBuf, SeparatorType.COLON_SPACE, NewlineType.CRLF);
    }

    public AsciiHeadersEncoder(ByteBuf byteBuf, SeparatorType separatorType, NewlineType newlineType) {
        if (byteBuf == null) {
            throw new NullPointerException("buf");
        }
        if (separatorType == null) {
            throw new NullPointerException("separatorType");
        }
        if (newlineType == null) {
            throw new NullPointerException("newlineType");
        }
        this.buf = byteBuf;
        this.separatorType = separatorType;
        this.newlineType = newlineType;
    }

    public void encode(Map.Entry<CharSequence, CharSequence> entry) {
        CharSequence charSequence = entry.getKey();
        CharSequence charSequence2 = entry.getValue();
        ByteBuf byteBuf = this.buf;
        int n = charSequence.length();
        int n2 = charSequence2.length();
        int n3 = n + n2 + 4;
        int n4 = byteBuf.writerIndex();
        byteBuf.ensureWritable(n3);
        AsciiHeadersEncoder.writeAscii(byteBuf, n4, charSequence);
        n4 += n;
        switch (this.separatorType) {
            case COLON: {
                byteBuf.setByte(n4++, 58);
                break;
            }
            case COLON_SPACE: {
                byteBuf.setByte(n4++, 58);
                byteBuf.setByte(n4++, 32);
                break;
            }
            default: {
                throw new Error();
            }
        }
        AsciiHeadersEncoder.writeAscii(byteBuf, n4, charSequence2);
        n4 += n2;
        switch (this.newlineType) {
            case LF: {
                byteBuf.setByte(n4++, 10);
                break;
            }
            case CRLF: {
                byteBuf.setByte(n4++, 13);
                byteBuf.setByte(n4++, 10);
                break;
            }
            default: {
                throw new Error();
            }
        }
        byteBuf.writerIndex(n4);
    }

    private static void writeAscii(ByteBuf byteBuf, int n, CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            ByteBufUtil.copy((AsciiString)charSequence, 0, byteBuf, n, charSequence.length());
        } else {
            byteBuf.setCharSequence(n, charSequence, CharsetUtil.US_ASCII);
        }
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

