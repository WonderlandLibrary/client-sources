/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
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
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class HttpObjectEncoder<H extends HttpMessage>
extends MessageToMessageEncoder<Object> {
    static final int CRLF_SHORT = 3338;
    private static final int ZERO_CRLF_MEDIUM = 3149066;
    private static final byte[] ZERO_CRLF_CRLF = new byte[]{48, 13, 10, 13, 10};
    private static final ByteBuf CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(2).writeByte(13).writeByte(10));
    private static final ByteBuf ZERO_CRLF_CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(ZERO_CRLF_CRLF.length).writeBytes(ZERO_CRLF_CRLF));
    private static final float HEADERS_WEIGHT_NEW = 0.2f;
    private static final float HEADERS_WEIGHT_HISTORICAL = 0.8f;
    private static final float TRAILERS_WEIGHT_NEW = 0.2f;
    private static final float TRAILERS_WEIGHT_HISTORICAL = 0.8f;
    private static final int ST_INIT = 0;
    private static final int ST_CONTENT_NON_CHUNK = 1;
    private static final int ST_CONTENT_CHUNK = 2;
    private static final int ST_CONTENT_ALWAYS_EMPTY = 3;
    private int state = 0;
    private float headersEncodedSizeAccumulator = 256.0f;
    private float trailersEncodedSizeAccumulator = 256.0f;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List<Object> list) throws Exception {
        Object object2;
        ByteBuf byteBuf = null;
        if (object instanceof HttpMessage) {
            if (this.state != 0) {
                throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(object));
            }
            object2 = (HttpMessage)object;
            byteBuf = channelHandlerContext.alloc().buffer((int)this.headersEncodedSizeAccumulator);
            this.encodeInitialLine(byteBuf, object2);
            this.state = this.isContentAlwaysEmpty(object2) ? 3 : (HttpUtil.isTransferEncodingChunked((HttpMessage)object2) ? 2 : 1);
            this.sanitizeHeadersBeforeEncode(object2, this.state == 3);
            this.encodeHeaders(object2.headers(), byteBuf);
            ByteBufUtil.writeShortBE(byteBuf, 3338);
            this.headersEncodedSizeAccumulator = 0.2f * (float)HttpObjectEncoder.padSizeForAccumulation(byteBuf.readableBytes()) + 0.8f * this.headersEncodedSizeAccumulator;
        }
        if (object instanceof ByteBuf && !((ByteBuf)(object2 = (ByteBuf)object)).isReadable()) {
            list.add(((ByteBuf)object2).retain());
            return;
        }
        if (object instanceof HttpContent || object instanceof ByteBuf || object instanceof FileRegion) {
            switch (this.state) {
                case 0: {
                    throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(object));
                }
                case 1: {
                    long l = HttpObjectEncoder.contentLength(object);
                    if (l > 0L) {
                        if (byteBuf != null && (long)byteBuf.writableBytes() >= l && object instanceof HttpContent) {
                            byteBuf.writeBytes(((HttpContent)object).content());
                            list.add(byteBuf);
                        } else {
                            if (byteBuf != null) {
                                list.add(byteBuf);
                            }
                            list.add(HttpObjectEncoder.encodeAndRetain(object));
                        }
                        if (!(object instanceof LastHttpContent)) break;
                        this.state = 0;
                        break;
                    }
                }
                case 3: {
                    if (byteBuf != null) {
                        list.add(byteBuf);
                        break;
                    }
                    list.add(Unpooled.EMPTY_BUFFER);
                    break;
                }
                case 2: {
                    if (byteBuf != null) {
                        list.add(byteBuf);
                    }
                    this.encodeChunkedContent(channelHandlerContext, object, HttpObjectEncoder.contentLength(object), list);
                    break;
                }
                default: {
                    throw new Error();
                }
            }
            if (object instanceof LastHttpContent) {
                this.state = 0;
            }
        } else if (byteBuf != null) {
            list.add(byteBuf);
        }
    }

    protected void encodeHeaders(HttpHeaders httpHeaders, ByteBuf byteBuf) {
        Iterator<Map.Entry<CharSequence, CharSequence>> iterator2 = httpHeaders.iteratorCharSequence();
        while (iterator2.hasNext()) {
            Map.Entry<CharSequence, CharSequence> entry = iterator2.next();
            HttpHeadersEncoder.encoderHeader(entry.getKey(), entry.getValue(), byteBuf);
        }
    }

    private void encodeChunkedContent(ChannelHandlerContext channelHandlerContext, Object object, long l, List<Object> list) {
        ByteBuf byteBuf;
        Object object2;
        if (l > 0L) {
            object2 = Long.toHexString(l);
            byteBuf = channelHandlerContext.alloc().buffer(((String)object2).length() + 2);
            byteBuf.writeCharSequence((CharSequence)object2, CharsetUtil.US_ASCII);
            ByteBufUtil.writeShortBE(byteBuf, 3338);
            list.add(byteBuf);
            list.add(HttpObjectEncoder.encodeAndRetain(object));
            list.add(CRLF_BUF.duplicate());
        }
        if (object instanceof LastHttpContent) {
            object2 = ((LastHttpContent)object).trailingHeaders();
            if (((HttpHeaders)object2).isEmpty()) {
                list.add(ZERO_CRLF_CRLF_BUF.duplicate());
            } else {
                byteBuf = channelHandlerContext.alloc().buffer((int)this.trailersEncodedSizeAccumulator);
                ByteBufUtil.writeMediumBE(byteBuf, 3149066);
                this.encodeHeaders((HttpHeaders)object2, byteBuf);
                ByteBufUtil.writeShortBE(byteBuf, 3338);
                this.trailersEncodedSizeAccumulator = 0.2f * (float)HttpObjectEncoder.padSizeForAccumulation(byteBuf.readableBytes()) + 0.8f * this.trailersEncodedSizeAccumulator;
                list.add(byteBuf);
            }
        } else if (l == 0L) {
            list.add(HttpObjectEncoder.encodeAndRetain(object));
        }
    }

    protected void sanitizeHeadersBeforeEncode(H h, boolean bl) {
    }

    protected boolean isContentAlwaysEmpty(H h) {
        return true;
    }

    @Override
    public boolean acceptOutboundMessage(Object object) throws Exception {
        return object instanceof HttpObject || object instanceof ByteBuf || object instanceof FileRegion;
    }

    private static Object encodeAndRetain(Object object) {
        if (object instanceof ByteBuf) {
            return ((ByteBuf)object).retain();
        }
        if (object instanceof HttpContent) {
            return ((HttpContent)object).content().retain();
        }
        if (object instanceof FileRegion) {
            return ((FileRegion)object).retain();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(object));
    }

    private static long contentLength(Object object) {
        if (object instanceof HttpContent) {
            return ((HttpContent)object).content().readableBytes();
        }
        if (object instanceof ByteBuf) {
            return ((ByteBuf)object).readableBytes();
        }
        if (object instanceof FileRegion) {
            return ((FileRegion)object).count();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(object));
    }

    private static int padSizeForAccumulation(int n) {
        return (n << 2) / 3;
    }

    @Deprecated
    protected static void encodeAscii(String string, ByteBuf byteBuf) {
        byteBuf.writeCharSequence(string, CharsetUtil.US_ASCII);
    }

    protected abstract void encodeInitialLine(ByteBuf var1, H var2) throws Exception;
}

