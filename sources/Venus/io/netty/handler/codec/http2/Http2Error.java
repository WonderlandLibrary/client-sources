/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

public enum Http2Error {
    NO_ERROR(0L),
    PROTOCOL_ERROR(1L),
    INTERNAL_ERROR(2L),
    FLOW_CONTROL_ERROR(3L),
    SETTINGS_TIMEOUT(4L),
    STREAM_CLOSED(5L),
    FRAME_SIZE_ERROR(6L),
    REFUSED_STREAM(7L),
    CANCEL(8L),
    COMPRESSION_ERROR(9L),
    CONNECT_ERROR(10L),
    ENHANCE_YOUR_CALM(11L),
    INADEQUATE_SECURITY(12L),
    HTTP_1_1_REQUIRED(13L);

    private final long code;
    private static final Http2Error[] INT_TO_ENUM_MAP;

    private Http2Error(long l) {
        this.code = l;
    }

    public long code() {
        return this.code;
    }

    public static Http2Error valueOf(long l) {
        return l >= (long)INT_TO_ENUM_MAP.length || l < 0L ? null : INT_TO_ENUM_MAP[(int)l];
    }

    static {
        Http2Error[] http2ErrorArray = Http2Error.values();
        Http2Error[] http2ErrorArray2 = new Http2Error[http2ErrorArray.length];
        Http2Error[] http2ErrorArray3 = http2ErrorArray;
        int n = http2ErrorArray3.length;
        for (int i = 0; i < n; ++i) {
            Http2Error http2Error;
            http2ErrorArray2[(int)http2Error.code()] = http2Error = http2ErrorArray3[i];
        }
        INT_TO_ENUM_MAP = http2ErrorArray2;
    }
}

