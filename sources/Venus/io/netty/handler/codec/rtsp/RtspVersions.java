/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.HttpVersion;

public final class RtspVersions {
    public static final HttpVersion RTSP_1_0 = new HttpVersion("RTSP", 1, 0, true);

    public static HttpVersion valueOf(String string) {
        if (string == null) {
            throw new NullPointerException("text");
        }
        if ("RTSP/1.0".equals(string = string.trim().toUpperCase())) {
            return RTSP_1_0;
        }
        return new HttpVersion(string, true);
    }

    private RtspVersions() {
    }
}

