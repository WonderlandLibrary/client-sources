/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.InboundHttp2ToHttpAdapter;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class InboundHttp2ToHttpAdapterBuilder
extends AbstractInboundHttp2ToHttpAdapterBuilder<InboundHttp2ToHttpAdapter, InboundHttp2ToHttpAdapterBuilder> {
    public InboundHttp2ToHttpAdapterBuilder(Http2Connection http2Connection) {
        super(http2Connection);
    }

    @Override
    public InboundHttp2ToHttpAdapterBuilder maxContentLength(int n) {
        return (InboundHttp2ToHttpAdapterBuilder)super.maxContentLength(n);
    }

    @Override
    public InboundHttp2ToHttpAdapterBuilder validateHttpHeaders(boolean bl) {
        return (InboundHttp2ToHttpAdapterBuilder)super.validateHttpHeaders(bl);
    }

    @Override
    public InboundHttp2ToHttpAdapterBuilder propagateSettings(boolean bl) {
        return (InboundHttp2ToHttpAdapterBuilder)super.propagateSettings(bl);
    }

    @Override
    public InboundHttp2ToHttpAdapter build() {
        return super.build();
    }

    @Override
    protected InboundHttp2ToHttpAdapter build(Http2Connection http2Connection, int n, boolean bl, boolean bl2) throws Exception {
        return new InboundHttp2ToHttpAdapter(http2Connection, n, bl, bl2);
    }

    @Override
    public AbstractInboundHttp2ToHttpAdapterBuilder propagateSettings(boolean bl) {
        return this.propagateSettings(bl);
    }

    @Override
    public AbstractInboundHttp2ToHttpAdapterBuilder validateHttpHeaders(boolean bl) {
        return this.validateHttpHeaders(bl);
    }

    @Override
    public AbstractInboundHttp2ToHttpAdapterBuilder maxContentLength(int n) {
        return this.maxContentLength(n);
    }
}

