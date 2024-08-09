/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.util.collection.CharObjectHashMap;
import io.netty.util.internal.ObjectUtil;

public final class Http2Settings
extends CharObjectHashMap<Long> {
    private static final int DEFAULT_CAPACITY = 13;
    private static final Long FALSE = 0L;
    private static final Long TRUE = 1L;

    public Http2Settings() {
        this(13);
    }

    public Http2Settings(int n, float f) {
        super(n, f);
    }

    public Http2Settings(int n) {
        super(n);
    }

    @Override
    public Long put(char c, Long l) {
        Http2Settings.verifyStandardSetting(c, l);
        return super.put(c, l);
    }

    public Long headerTableSize() {
        return (Long)this.get('\u0001');
    }

    public Http2Settings headerTableSize(long l) {
        this.put('\u0001', l);
        return this;
    }

    public Boolean pushEnabled() {
        Long l = (Long)this.get('\u0002');
        if (l == null) {
            return null;
        }
        return TRUE.equals(l);
    }

    public Http2Settings pushEnabled(boolean bl) {
        this.put('\u0002', bl ? TRUE : FALSE);
        return this;
    }

    public Long maxConcurrentStreams() {
        return (Long)this.get('\u0003');
    }

    public Http2Settings maxConcurrentStreams(long l) {
        this.put('\u0003', l);
        return this;
    }

    public Integer initialWindowSize() {
        return this.getIntValue('\u0004');
    }

    public Http2Settings initialWindowSize(int n) {
        this.put('\u0004', Long.valueOf(n));
        return this;
    }

    public Integer maxFrameSize() {
        return this.getIntValue('\u0005');
    }

    public Http2Settings maxFrameSize(int n) {
        this.put('\u0005', Long.valueOf(n));
        return this;
    }

    public Long maxHeaderListSize() {
        return (Long)this.get('\u0006');
    }

    public Http2Settings maxHeaderListSize(long l) {
        this.put('\u0006', l);
        return this;
    }

    public Http2Settings copyFrom(Http2Settings http2Settings) {
        this.clear();
        this.putAll(http2Settings);
        return this;
    }

    public Integer getIntValue(char c) {
        Long l = (Long)this.get(c);
        if (l == null) {
            return null;
        }
        return l.intValue();
    }

    private static void verifyStandardSetting(int n, Long l) {
        ObjectUtil.checkNotNull(l, "value");
        switch (n) {
            case 1: {
                if (l >= 0L && l <= 0xFFFFFFFFL) break;
                throw new IllegalArgumentException("Setting HEADER_TABLE_SIZE is invalid: " + l);
            }
            case 2: {
                if (l == 0L || l == 1L) break;
                throw new IllegalArgumentException("Setting ENABLE_PUSH is invalid: " + l);
            }
            case 3: {
                if (l >= 0L && l <= 0xFFFFFFFFL) break;
                throw new IllegalArgumentException("Setting MAX_CONCURRENT_STREAMS is invalid: " + l);
            }
            case 4: {
                if (l >= 0L && l <= Integer.MAX_VALUE) break;
                throw new IllegalArgumentException("Setting INITIAL_WINDOW_SIZE is invalid: " + l);
            }
            case 5: {
                if (Http2CodecUtil.isMaxFrameSizeValid(l.intValue())) break;
                throw new IllegalArgumentException("Setting MAX_FRAME_SIZE is invalid: " + l);
            }
            case 6: {
                if (l >= 0L && l <= 0xFFFFFFFFL) break;
                throw new IllegalArgumentException("Setting MAX_HEADER_LIST_SIZE is invalid: " + l);
            }
        }
    }

    @Override
    protected String keyToString(char c) {
        switch (c) {
            case '\u0001': {
                return "HEADER_TABLE_SIZE";
            }
            case '\u0002': {
                return "ENABLE_PUSH";
            }
            case '\u0003': {
                return "MAX_CONCURRENT_STREAMS";
            }
            case '\u0004': {
                return "INITIAL_WINDOW_SIZE";
            }
            case '\u0005': {
                return "MAX_FRAME_SIZE";
            }
            case '\u0006': {
                return "MAX_HEADER_LIST_SIZE";
            }
        }
        return super.keyToString(c);
    }

    public static Http2Settings defaultSettings() {
        return new Http2Settings().maxHeaderListSize(8192L);
    }

    @Override
    public Object put(char c, Object object) {
        return this.put(c, (Long)object);
    }
}

