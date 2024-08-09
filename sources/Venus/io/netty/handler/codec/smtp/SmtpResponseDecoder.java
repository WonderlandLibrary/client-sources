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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class SmtpResponseDecoder
extends LineBasedFrameDecoder {
    private List<CharSequence> details;

    public SmtpResponseDecoder(int n) {
        super(n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected SmtpResponse decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        ByteBuf byteBuf2 = (ByteBuf)super.decode(channelHandlerContext, byteBuf);
        if (byteBuf2 == null) {
            return null;
        }
        try {
            int n = byteBuf2.readableBytes();
            int n2 = byteBuf2.readerIndex();
            if (n < 3) {
                throw SmtpResponseDecoder.newDecoderException(byteBuf, n2, n);
            }
            int n3 = SmtpResponseDecoder.parseCode(byteBuf2);
            byte by = byteBuf2.readByte();
            String string = byteBuf2.isReadable() ? byteBuf2.toString(CharsetUtil.US_ASCII) : null;
            List<CharSequence> list = this.details;
            switch (by) {
                case 32: {
                    void var9_12;
                    this.details = null;
                    if (list != null) {
                        if (string != null) {
                            list.add(string);
                        }
                    } else if (string == null) {
                        List list2 = Collections.emptyList();
                    } else {
                        List<String> list3 = Collections.singletonList(string);
                    }
                    DefaultSmtpResponse defaultSmtpResponse = new DefaultSmtpResponse(n3, (List<CharSequence>)var9_12);
                    return defaultSmtpResponse;
                }
                case 45: {
                    void var9_14;
                    if (string == null) return null;
                    if (list == null) {
                        ArrayList<CharSequence> arrayList = new ArrayList<CharSequence>(4);
                        this.details = arrayList;
                    }
                    var9_14.add(string);
                    return null;
                }
                default: {
                    throw SmtpResponseDecoder.newDecoderException(byteBuf, n2, n);
                }
            }
        } finally {
            byteBuf2.release();
        }
    }

    private static DecoderException newDecoderException(ByteBuf byteBuf, int n, int n2) {
        return new DecoderException("Received invalid line: '" + byteBuf.toString(n, n2, CharsetUtil.US_ASCII) + '\'');
    }

    private static int parseCode(ByteBuf byteBuf) {
        int n = SmtpResponseDecoder.parseNumber(byteBuf.readByte()) * 100;
        int n2 = SmtpResponseDecoder.parseNumber(byteBuf.readByte()) * 10;
        int n3 = SmtpResponseDecoder.parseNumber(byteBuf.readByte());
        return n + n2 + n3;
    }

    private static int parseNumber(byte by) {
        return Character.digit((char)by, 10);
    }

    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        return this.decode(channelHandlerContext, byteBuf);
    }
}

