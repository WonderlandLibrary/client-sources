/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.smtp.DefaultSmtpResponse;
import io.netty.handler.codec.smtp.SmtpResponse;
import io.netty.util.CharsetUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SmtpResponseDecoder
extends LineBasedFrameDecoder {
    private List<CharSequence> details;

    public SmtpResponseDecoder(int maxLineLength) {
        super(maxLineLength);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected SmtpResponse decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        ByteBuf frame = (ByteBuf)super.decode(ctx, buffer);
        if (frame == null) {
            return null;
        }
        try {
            int readable = frame.readableBytes();
            int readerIndex = frame.readerIndex();
            if (readable < 3) {
                throw SmtpResponseDecoder.newDecoderException(buffer, readerIndex, readable);
            }
            int code = SmtpResponseDecoder.parseCode(frame);
            byte separator = frame.readByte();
            String detail = frame.isReadable() ? frame.toString(CharsetUtil.US_ASCII) : null;
            List<CharSequence> details = this.details;
            switch (separator) {
                case 32: {
                    this.details = null;
                    if (details != null) {
                        if (detail != null) {
                            details.add(detail);
                        }
                    } else {
                        details = Collections.singletonList(detail);
                    }
                    DefaultSmtpResponse defaultSmtpResponse = new DefaultSmtpResponse(code, details);
                    return defaultSmtpResponse;
                }
                case 45: {
                    if (detail == null) return null;
                    if (details == null) {
                        this.details = details = new ArrayList<CharSequence>(4);
                    }
                    details.add(detail);
                    return null;
                }
                default: {
                    throw SmtpResponseDecoder.newDecoderException(buffer, readerIndex, readable);
                }
            }
        } finally {
            frame.release();
        }
    }

    private static DecoderException newDecoderException(ByteBuf buffer, int readerIndex, int readable) {
        return new DecoderException("Received invalid line: '" + buffer.toString(readerIndex, readable, CharsetUtil.US_ASCII) + '\'');
    }

    private static int parseCode(ByteBuf buffer) {
        int first = SmtpResponseDecoder.parseNumber(buffer.readByte()) * 100;
        int second = SmtpResponseDecoder.parseNumber(buffer.readByte()) * 10;
        int third = SmtpResponseDecoder.parseNumber(buffer.readByte());
        return first + second + third;
    }

    private static int parseNumber(byte b) {
        return Character.digit((char)b, 10);
    }
}

