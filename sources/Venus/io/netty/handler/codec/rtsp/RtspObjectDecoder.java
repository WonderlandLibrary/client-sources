/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectDecoder;
import io.netty.handler.codec.rtsp.RtspHeaderNames;

@Deprecated
public abstract class RtspObjectDecoder
extends HttpObjectDecoder {
    protected RtspObjectDecoder() {
        this(4096, 8192, 8192);
    }

    protected RtspObjectDecoder(int n, int n2, int n3) {
        super(n, n2, n3 * 2, false);
    }

    protected RtspObjectDecoder(int n, int n2, int n3, boolean bl) {
        super(n, n2, n3 * 2, false, bl);
    }

    @Override
    protected boolean isContentAlwaysEmpty(HttpMessage httpMessage) {
        boolean bl = super.isContentAlwaysEmpty(httpMessage);
        if (bl) {
            return false;
        }
        if (!httpMessage.headers().contains(RtspHeaderNames.CONTENT_LENGTH)) {
            return false;
        }
        return bl;
    }
}

