/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.InboundHttp2ToHttpAdapter;
import io.netty.util.internal.ObjectUtil;

public abstract class AbstractInboundHttp2ToHttpAdapterBuilder<T extends InboundHttp2ToHttpAdapter, B extends AbstractInboundHttp2ToHttpAdapterBuilder<T, B>> {
    private final Http2Connection connection;
    private int maxContentLength;
    private boolean validateHttpHeaders;
    private boolean propagateSettings;

    protected AbstractInboundHttp2ToHttpAdapterBuilder(Http2Connection http2Connection) {
        this.connection = ObjectUtil.checkNotNull(http2Connection, "connection");
    }

    protected final B self() {
        return (B)this;
    }

    protected Http2Connection connection() {
        return this.connection;
    }

    protected int maxContentLength() {
        return this.maxContentLength;
    }

    protected B maxContentLength(int n) {
        this.maxContentLength = n;
        return this.self();
    }

    protected boolean isValidateHttpHeaders() {
        return this.validateHttpHeaders;
    }

    protected B validateHttpHeaders(boolean bl) {
        this.validateHttpHeaders = bl;
        return this.self();
    }

    protected boolean isPropagateSettings() {
        return this.propagateSettings;
    }

    protected B propagateSettings(boolean bl) {
        this.propagateSettings = bl;
        return this.self();
    }

    protected T build() {
        T t;
        try {
            t = this.build(this.connection(), this.maxContentLength(), this.isValidateHttpHeaders(), this.isPropagateSettings());
        } catch (Throwable throwable) {
            throw new IllegalStateException("failed to create a new InboundHttp2ToHttpAdapter", throwable);
        }
        this.connection.addListener((Http2Connection.Listener)t);
        return t;
    }

    protected abstract T build(Http2Connection var1, int var2, boolean var3, boolean var4) throws Exception;
}

