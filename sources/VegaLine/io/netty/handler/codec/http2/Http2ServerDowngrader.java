/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultLastHttpContent;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http2.DefaultHttp2DataFrame;
import io.netty.handler.codec.http2.DefaultHttp2HeadersFrame;
import io.netty.handler.codec.http2.Http2DataFrame;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersFrame;
import io.netty.handler.codec.http2.Http2StreamFrame;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.util.ReferenceCountUtil;
import java.util.List;

public class Http2ServerDowngrader
extends MessageToMessageCodec<Http2StreamFrame, HttpObject> {
    private final boolean validateHeaders;

    public Http2ServerDowngrader(boolean validateHeaders) {
        this.validateHeaders = validateHeaders;
    }

    public Http2ServerDowngrader() {
        this(true);
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        return msg instanceof Http2HeadersFrame || msg instanceof Http2DataFrame;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Http2StreamFrame frame, List<Object> out) throws Exception {
        if (frame instanceof Http2HeadersFrame) {
            int id = 0;
            Http2HeadersFrame headersFrame = (Http2HeadersFrame)frame;
            Http2Headers headers = headersFrame.headers();
            if (headersFrame.isEndStream()) {
                if (headers.method() == null) {
                    DefaultLastHttpContent last = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.validateHeaders);
                    HttpConversionUtil.addHttp2ToHttpHeaders(id, headers, last.trailingHeaders(), HttpVersion.HTTP_1_1, true, true);
                    out.add(last);
                } else {
                    FullHttpRequest full = HttpConversionUtil.toFullHttpRequest(id, headers, ctx.alloc(), this.validateHeaders);
                    out.add(full);
                }
            } else {
                HttpRequest req = HttpConversionUtil.toHttpRequest(id, headersFrame.headers(), this.validateHeaders);
                if (!HttpUtil.isContentLengthSet(req)) {
                    req.headers().add((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, (Object)HttpHeaderValues.CHUNKED);
                }
                out.add(req);
            }
        } else if (frame instanceof Http2DataFrame) {
            Http2DataFrame dataFrame = (Http2DataFrame)frame;
            if (dataFrame.isEndStream()) {
                out.add(new DefaultLastHttpContent(dataFrame.content(), this.validateHeaders));
            } else {
                out.add(new DefaultHttpContent(dataFrame.content()));
            }
        }
        ReferenceCountUtil.retain(frame);
    }

    private void encodeLastContent(LastHttpContent last, List<Object> out) {
        boolean needFiller;
        boolean bl = needFiller = !(last instanceof FullHttpResponse) && last.trailingHeaders().isEmpty();
        if (last.content().isReadable() || needFiller) {
            out.add(new DefaultHttp2DataFrame(last.content(), last.trailingHeaders().isEmpty()));
        }
        if (!last.trailingHeaders().isEmpty()) {
            Http2Headers headers = HttpConversionUtil.toHttp2Headers(last.trailingHeaders(), this.validateHeaders);
            out.add(new DefaultHttp2HeadersFrame(headers, true));
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, HttpObject obj, List<Object> out) throws Exception {
        if (obj instanceof HttpResponse) {
            Http2Headers headers = HttpConversionUtil.toHttp2Headers((HttpResponse)obj, this.validateHeaders);
            boolean noMoreFrames = false;
            if (obj instanceof FullHttpResponse) {
                FullHttpResponse full = (FullHttpResponse)obj;
                noMoreFrames = !full.content().isReadable() && full.trailingHeaders().isEmpty();
            }
            out.add(new DefaultHttp2HeadersFrame(headers, noMoreFrames));
        }
        if (obj instanceof LastHttpContent) {
            LastHttpContent last = (LastHttpContent)obj;
            this.encodeLastContent(last, out);
        } else if (obj instanceof HttpContent) {
            HttpContent cont = (HttpContent)obj;
            out.add(new DefaultHttp2DataFrame(cont.content(), false));
        }
        ReferenceCountUtil.retain(obj);
    }
}

