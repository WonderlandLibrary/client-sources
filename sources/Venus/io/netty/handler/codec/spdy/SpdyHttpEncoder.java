/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.spdy.DefaultSpdyDataFrame;
import io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame;
import io.netty.handler.codec.spdy.DefaultSpdySynReplyFrame;
import io.netty.handler.codec.spdy.DefaultSpdySynStreamFrame;
import io.netty.handler.codec.spdy.SpdyCodecUtil;
import io.netty.handler.codec.spdy.SpdyHeaders;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyHttpHeaders;
import io.netty.handler.codec.spdy.SpdyStreamFrame;
import io.netty.handler.codec.spdy.SpdySynStreamFrame;
import io.netty.handler.codec.spdy.SpdyVersion;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SpdyHttpEncoder
extends MessageToMessageEncoder<HttpObject> {
    private int currentStreamId;
    private final boolean validateHeaders;
    private final boolean headersToLowerCase;

    public SpdyHttpEncoder(SpdyVersion spdyVersion) {
        this(spdyVersion, true, true);
    }

    public SpdyHttpEncoder(SpdyVersion spdyVersion, boolean bl, boolean bl2) {
        if (spdyVersion == null) {
            throw new NullPointerException("version");
        }
        this.headersToLowerCase = bl;
        this.validateHeaders = bl2;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List<Object> list) throws Exception {
        SpdyStreamFrame spdyStreamFrame;
        HttpObject httpObject2;
        boolean bl = false;
        boolean bl2 = false;
        if (httpObject instanceof HttpRequest) {
            httpObject2 = (HttpRequest)httpObject;
            spdyStreamFrame = this.createSynStreamFrame((HttpRequest)httpObject2);
            list.add(spdyStreamFrame);
            bl2 = spdyStreamFrame.isLast() || spdyStreamFrame.isUnidirectional();
            bl = true;
        }
        if (httpObject instanceof HttpResponse) {
            httpObject2 = (HttpResponse)httpObject;
            spdyStreamFrame = this.createHeadersFrame((HttpResponse)httpObject2);
            list.add(spdyStreamFrame);
            bl2 = spdyStreamFrame.isLast();
            bl = true;
        }
        if (httpObject instanceof HttpContent && !bl2) {
            httpObject2 = (HttpContent)httpObject;
            httpObject2.content().retain();
            spdyStreamFrame = new DefaultSpdyDataFrame(this.currentStreamId, httpObject2.content());
            if (httpObject2 instanceof LastHttpContent) {
                LastHttpContent lastHttpContent = (LastHttpContent)httpObject2;
                HttpHeaders httpHeaders = lastHttpContent.trailingHeaders();
                if (httpHeaders.isEmpty()) {
                    spdyStreamFrame.setLast(true);
                    list.add(spdyStreamFrame);
                } else {
                    DefaultSpdyHeadersFrame defaultSpdyHeadersFrame = new DefaultSpdyHeadersFrame(this.currentStreamId, this.validateHeaders);
                    defaultSpdyHeadersFrame.setLast(true);
                    Iterator<Map.Entry<CharSequence, CharSequence>> iterator2 = httpHeaders.iteratorCharSequence();
                    while (iterator2.hasNext()) {
                        Map.Entry<CharSequence, CharSequence> entry = iterator2.next();
                        CharSequence charSequence = this.headersToLowerCase ? AsciiString.of(entry.getKey()).toLowerCase() : entry.getKey();
                        defaultSpdyHeadersFrame.headers().add(charSequence, entry.getValue());
                    }
                    list.add(spdyStreamFrame);
                    list.add(defaultSpdyHeadersFrame);
                }
            } else {
                list.add(spdyStreamFrame);
            }
            bl = true;
        }
        if (!bl) {
            throw new UnsupportedMessageTypeException(httpObject, new Class[0]);
        }
    }

    private SpdySynStreamFrame createSynStreamFrame(HttpRequest httpRequest) throws Exception {
        HttpHeaders httpHeaders = httpRequest.headers();
        int n = httpHeaders.getInt(SpdyHttpHeaders.Names.STREAM_ID);
        int n2 = httpHeaders.getInt(SpdyHttpHeaders.Names.ASSOCIATED_TO_STREAM_ID, 0);
        byte by = (byte)httpHeaders.getInt(SpdyHttpHeaders.Names.PRIORITY, 0);
        String string = httpHeaders.get(SpdyHttpHeaders.Names.SCHEME);
        httpHeaders.remove(SpdyHttpHeaders.Names.STREAM_ID);
        httpHeaders.remove(SpdyHttpHeaders.Names.ASSOCIATED_TO_STREAM_ID);
        httpHeaders.remove(SpdyHttpHeaders.Names.PRIORITY);
        httpHeaders.remove(SpdyHttpHeaders.Names.SCHEME);
        httpHeaders.remove(HttpHeaderNames.CONNECTION);
        httpHeaders.remove("Keep-Alive");
        httpHeaders.remove("Proxy-Connection");
        httpHeaders.remove(HttpHeaderNames.TRANSFER_ENCODING);
        DefaultSpdySynStreamFrame defaultSpdySynStreamFrame = new DefaultSpdySynStreamFrame(n, n2, by, this.validateHeaders);
        SpdyHeaders spdyHeaders = defaultSpdySynStreamFrame.headers();
        spdyHeaders.set(SpdyHeaders.HttpNames.METHOD, httpRequest.method().name());
        spdyHeaders.set(SpdyHeaders.HttpNames.PATH, httpRequest.uri());
        spdyHeaders.set(SpdyHeaders.HttpNames.VERSION, httpRequest.protocolVersion().text());
        String string2 = httpHeaders.get(HttpHeaderNames.HOST);
        httpHeaders.remove(HttpHeaderNames.HOST);
        spdyHeaders.set(SpdyHeaders.HttpNames.HOST, string2);
        if (string == null) {
            string = "https";
        }
        spdyHeaders.set(SpdyHeaders.HttpNames.SCHEME, string);
        Iterator<Map.Entry<CharSequence, CharSequence>> iterator2 = httpHeaders.iteratorCharSequence();
        while (iterator2.hasNext()) {
            Map.Entry<CharSequence, CharSequence> entry = iterator2.next();
            CharSequence charSequence = this.headersToLowerCase ? AsciiString.of(entry.getKey()).toLowerCase() : entry.getKey();
            spdyHeaders.add(charSequence, entry.getValue());
        }
        this.currentStreamId = defaultSpdySynStreamFrame.streamId();
        if (n2 == 0) {
            defaultSpdySynStreamFrame.setLast(SpdyHttpEncoder.isLast(httpRequest));
        } else {
            defaultSpdySynStreamFrame.setUnidirectional(true);
        }
        return defaultSpdySynStreamFrame;
    }

    private SpdyHeadersFrame createHeadersFrame(HttpResponse httpResponse) throws Exception {
        HttpHeaders httpHeaders = httpResponse.headers();
        int n = httpHeaders.getInt(SpdyHttpHeaders.Names.STREAM_ID);
        httpHeaders.remove(SpdyHttpHeaders.Names.STREAM_ID);
        httpHeaders.remove(HttpHeaderNames.CONNECTION);
        httpHeaders.remove("Keep-Alive");
        httpHeaders.remove("Proxy-Connection");
        httpHeaders.remove(HttpHeaderNames.TRANSFER_ENCODING);
        DefaultSpdyHeadersFrame defaultSpdyHeadersFrame = SpdyCodecUtil.isServerId(n) ? new DefaultSpdyHeadersFrame(n, this.validateHeaders) : new DefaultSpdySynReplyFrame(n, this.validateHeaders);
        SpdyHeaders spdyHeaders = defaultSpdyHeadersFrame.headers();
        spdyHeaders.set(SpdyHeaders.HttpNames.STATUS, httpResponse.status().codeAsText());
        spdyHeaders.set(SpdyHeaders.HttpNames.VERSION, httpResponse.protocolVersion().text());
        Iterator<Map.Entry<CharSequence, CharSequence>> iterator2 = httpHeaders.iteratorCharSequence();
        while (iterator2.hasNext()) {
            Map.Entry<CharSequence, CharSequence> entry = iterator2.next();
            CharSequence charSequence = this.headersToLowerCase ? AsciiString.of(entry.getKey()).toLowerCase() : entry.getKey();
            defaultSpdyHeadersFrame.headers().add(charSequence, entry.getValue());
        }
        this.currentStreamId = n;
        defaultSpdyHeadersFrame.setLast(SpdyHttpEncoder.isLast(httpResponse));
        return defaultSpdyHeadersFrame;
    }

    private static boolean isLast(HttpMessage httpMessage) {
        FullHttpMessage fullHttpMessage;
        return !(httpMessage instanceof FullHttpMessage) || !(fullHttpMessage = (FullHttpMessage)httpMessage).trailingHeaders().isEmpty() || fullHttpMessage.content().isReadable();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (HttpObject)object, (List<Object>)list);
    }
}

