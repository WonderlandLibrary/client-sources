/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.smtp.LastSmtpContent;
import io.netty.handler.codec.smtp.SmtpCommand;
import io.netty.handler.codec.smtp.SmtpContent;
import io.netty.handler.codec.smtp.SmtpRequest;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

public final class SmtpRequestEncoder
extends MessageToMessageEncoder<Object> {
    private static final int CRLF_SHORT = 3338;
    private static final byte SP = 32;
    private static final ByteBuf DOT_CRLF_BUFFER = Unpooled.unreleasableBuffer(Unpooled.directBuffer(3).writeByte(46).writeByte(13).writeByte(10));
    private boolean contentExpected;

    @Override
    public boolean acceptOutboundMessage(Object object) throws Exception {
        return object instanceof SmtpRequest || object instanceof SmtpContent;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List<Object> list) throws Exception {
        Object object2;
        if (object instanceof SmtpRequest) {
            object2 = (SmtpRequest)object;
            if (this.contentExpected) {
                if (object2.command().equals(SmtpCommand.RSET)) {
                    this.contentExpected = false;
                } else {
                    throw new IllegalStateException("SmtpContent expected");
                }
            }
            boolean bl = true;
            ByteBuf byteBuf = channelHandlerContext.alloc().buffer();
            try {
                object2.command().encode(byteBuf);
                SmtpRequestEncoder.writeParameters(object2.parameters(), byteBuf);
                ByteBufUtil.writeShortBE(byteBuf, 3338);
                list.add(byteBuf);
                bl = false;
                if (object2.command().isContentExpected()) {
                    this.contentExpected = true;
                }
            } finally {
                if (bl) {
                    byteBuf.release();
                }
            }
        }
        if (object instanceof SmtpContent) {
            if (!this.contentExpected) {
                throw new IllegalStateException("No SmtpContent expected");
            }
            object2 = ((SmtpContent)object).content();
            list.add(((ByteBuf)object2).retain());
            if (object instanceof LastSmtpContent) {
                list.add(DOT_CRLF_BUFFER.retainedDuplicate());
                this.contentExpected = false;
            }
        }
    }

    private static void writeParameters(List<CharSequence> list, ByteBuf byteBuf) {
        if (list.isEmpty()) {
            return;
        }
        byteBuf.writeByte(32);
        if (list instanceof RandomAccess) {
            int n = list.size() - 1;
            for (int i = 0; i < n; ++i) {
                ByteBufUtil.writeAscii(byteBuf, list.get(i));
                byteBuf.writeByte(32);
            }
            ByteBufUtil.writeAscii(byteBuf, list.get(n));
        } else {
            Iterator<CharSequence> iterator2 = list.iterator();
            while (true) {
                ByteBufUtil.writeAscii(byteBuf, iterator2.next());
                if (!iterator2.hasNext()) break;
                byteBuf.writeByte(32);
            }
        }
    }
}

