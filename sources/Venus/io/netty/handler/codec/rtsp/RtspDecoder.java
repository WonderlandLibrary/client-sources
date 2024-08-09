/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectDecoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.rtsp.RtspHeaderNames;
import io.netty.handler.codec.rtsp.RtspMethods;
import io.netty.handler.codec.rtsp.RtspVersions;
import java.util.regex.Pattern;

public class RtspDecoder
extends HttpObjectDecoder {
    private static final HttpResponseStatus UNKNOWN_STATUS = new HttpResponseStatus(999, "Unknown");
    private boolean isDecodingRequest;
    private static final Pattern versionPattern = Pattern.compile("RTSP/\\d\\.\\d");
    public static final int DEFAULT_MAX_INITIAL_LINE_LENGTH = 4096;
    public static final int DEFAULT_MAX_HEADER_SIZE = 8192;
    public static final int DEFAULT_MAX_CONTENT_LENGTH = 8192;

    public RtspDecoder() {
        this(4096, 8192, 8192);
    }

    public RtspDecoder(int n, int n2, int n3) {
        super(n, n2, n3 * 2, false);
    }

    public RtspDecoder(int n, int n2, int n3, boolean bl) {
        super(n, n2, n3 * 2, false, bl);
    }

    @Override
    protected HttpMessage createMessage(String[] stringArray) throws Exception {
        if (versionPattern.matcher(stringArray[0]).matches()) {
            this.isDecodingRequest = false;
            return new DefaultHttpResponse(RtspVersions.valueOf(stringArray[0]), new HttpResponseStatus(Integer.parseInt(stringArray[5]), stringArray[5]), this.validateHeaders);
        }
        this.isDecodingRequest = true;
        return new DefaultHttpRequest(RtspVersions.valueOf(stringArray[5]), RtspMethods.valueOf(stringArray[0]), stringArray[5], this.validateHeaders);
    }

    @Override
    protected boolean isContentAlwaysEmpty(HttpMessage httpMessage) {
        return super.isContentAlwaysEmpty(httpMessage) || !httpMessage.headers().contains(RtspHeaderNames.CONTENT_LENGTH);
    }

    @Override
    protected HttpMessage createInvalidMessage() {
        if (this.isDecodingRequest) {
            return new DefaultFullHttpRequest(RtspVersions.RTSP_1_0, RtspMethods.OPTIONS, "/bad-request", this.validateHeaders);
        }
        return new DefaultFullHttpResponse(RtspVersions.RTSP_1_0, UNKNOWN_STATUS, this.validateHeaders);
    }

    @Override
    protected boolean isDecodingRequest() {
        return this.isDecodingRequest;
    }
}

