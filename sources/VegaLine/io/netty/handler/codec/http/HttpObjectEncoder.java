/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeadersEncoder;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class HttpObjectEncoder<H extends HttpMessage>
extends MessageToMessageEncoder<Object> {
    static final byte[] CRLF = new byte[]{13, 10};
    private static final byte[] ZERO_CRLF = new byte[]{48, 13, 10};
    private static final byte[] ZERO_CRLF_CRLF = new byte[]{48, 13, 10, 13, 10};
    private static final ByteBuf CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(CRLF.length).writeBytes(CRLF));
    private static final ByteBuf ZERO_CRLF_CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(ZERO_CRLF_CRLF.length).writeBytes(ZERO_CRLF_CRLF));
    private static final int ST_INIT = 0;
    private static final int ST_CONTENT_NON_CHUNK = 1;
    private static final int ST_CONTENT_CHUNK = 2;
    private static final int ST_CONTENT_ALWAYS_EMPTY = 3;
    private int state = 0;

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        ByteBuf buf = null;
        if (msg instanceof HttpMessage) {
            if (this.state != 0) {
                throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
            }
            HttpMessage m = (HttpMessage)msg;
            buf = ctx.alloc().buffer();
            this.encodeInitialLine(buf, m);
            this.encodeHeaders(m.headers(), buf);
            buf.writeBytes(CRLF);
            int n = this.isContentAlwaysEmpty(m) ? 3 : (this.state = HttpUtil.isTransferEncodingChunked(m) ? 2 : 1);
        }
        if (msg instanceof ByteBuf && !((ByteBuf)msg).isReadable()) {
            out.add(Unpooled.EMPTY_BUFFER);
            return;
        }
        if (msg instanceof HttpContent || msg instanceof ByteBuf || msg instanceof FileRegion) {
            switch (this.state) {
                case 0: {
                    throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
                }
                case 1: {
                    long contentLength = HttpObjectEncoder.contentLength(msg);
                    if (contentLength > 0L) {
                        if (buf != null && (long)buf.writableBytes() >= contentLength && msg instanceof HttpContent) {
                            buf.writeBytes(((HttpContent)msg).content());
                            out.add(buf);
                        } else {
                            if (buf != null) {
                                out.add(buf);
                            }
                            out.add(HttpObjectEncoder.encodeAndRetain(msg));
                        }
                        if (!(msg instanceof LastHttpContent)) break;
                        this.state = 0;
                        break;
                    }
                }
                case 3: {
                    if (buf != null) {
                        out.add(buf);
                        break;
                    }
                    out.add(Unpooled.EMPTY_BUFFER);
                    break;
                }
                case 2: {
                    if (buf != null) {
                        out.add(buf);
                    }
                    this.encodeChunkedContent(ctx, msg, HttpObjectEncoder.contentLength(msg), out);
                    break;
                }
                default: {
                    throw new Error();
                }
            }
            if (msg instanceof LastHttpContent) {
                this.state = 0;
            }
        } else if (buf != null) {
            out.add(buf);
        }
    }

    protected void encodeHeaders(HttpHeaders headers, ByteBuf buf) throws Exception {
        Iterator<Map.Entry<CharSequence, CharSequence>> iter = headers.iteratorCharSequence();
        while (iter.hasNext()) {
            Map.Entry<CharSequence, CharSequence> header = iter.next();
            HttpHeadersEncoder.encoderHeader(header.getKey(), header.getValue(), buf);
        }
    }

    private void encodeChunkedContent(ChannelHandlerContext ctx, Object msg, long contentLength, List<Object> out) {
        ByteBuf buf;
        if (contentLength > 0L) {
            byte[] length = Long.toHexString(contentLength).getBytes(CharsetUtil.US_ASCII);
            buf = ctx.alloc().buffer(length.length + 2);
            buf.writeBytes(length);
            buf.writeBytes(CRLF);
            out.add(buf);
            out.add(HttpObjectEncoder.encodeAndRetain(msg));
            out.add(CRLF_BUF.duplicate());
        }
        if (msg instanceof LastHttpContent) {
            HttpHeaders headers = ((LastHttpContent)msg).trailingHeaders();
            if (headers.isEmpty()) {
                out.add(ZERO_CRLF_CRLF_BUF.duplicate());
            } else {
                buf = ctx.alloc().buffer();
                buf.writeBytes(ZERO_CRLF);
                try {
                    this.encodeHeaders(headers, buf);
                } catch (Exception ex) {
                    buf.release();
                    PlatformDependent.throwException(ex);
                }
                buf.writeBytes(CRLF);
                out.add(buf);
            }
        } else if (contentLength == 0L) {
            out.add(Unpooled.EMPTY_BUFFER);
        }
    }

    boolean isContentAlwaysEmpty(H msg) {
        return false;
    }

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return msg instanceof HttpObject || msg instanceof ByteBuf || msg instanceof FileRegion;
    }

    private static Object encodeAndRetain(Object msg) {
        if (msg instanceof ByteBuf) {
            return ((ByteBuf)msg).retain();
        }
        if (msg instanceof HttpContent) {
            return ((HttpContent)msg).content().retain();
        }
        if (msg instanceof FileRegion) {
            return ((FileRegion)msg).retain();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
    }

    private static long contentLength(Object msg) {
        if (msg instanceof HttpContent) {
            return ((HttpContent)msg).content().readableBytes();
        }
        if (msg instanceof ByteBuf) {
            return ((ByteBuf)msg).readableBytes();
        }
        if (msg instanceof FileRegion) {
            return ((FileRegion)msg).count();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
    }

    @Deprecated
    protected static void encodeAscii(String s, ByteBuf buf) {
        HttpUtil.encodeAscii0(s, buf);
    }

    protected abstract void encodeInitialLine(ByteBuf var1, H var2) throws Exception;
}

