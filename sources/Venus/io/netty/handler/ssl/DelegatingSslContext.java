/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolNegotiator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.internal.ObjectUtil;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;

public abstract class DelegatingSslContext
extends SslContext {
    private final SslContext ctx;

    protected DelegatingSslContext(SslContext sslContext) {
        this.ctx = ObjectUtil.checkNotNull(sslContext, "ctx");
    }

    @Override
    public final boolean isClient() {
        return this.ctx.isClient();
    }

    @Override
    public final List<String> cipherSuites() {
        return this.ctx.cipherSuites();
    }

    @Override
    public final long sessionCacheSize() {
        return this.ctx.sessionCacheSize();
    }

    @Override
    public final long sessionTimeout() {
        return this.ctx.sessionTimeout();
    }

    @Override
    public final ApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.ctx.applicationProtocolNegotiator();
    }

    @Override
    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator) {
        SSLEngine sSLEngine = this.ctx.newEngine(byteBufAllocator);
        this.initEngine(sSLEngine);
        return sSLEngine;
    }

    @Override
    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String string, int n) {
        SSLEngine sSLEngine = this.ctx.newEngine(byteBufAllocator, string, n);
        this.initEngine(sSLEngine);
        return sSLEngine;
    }

    @Override
    protected final SslHandler newHandler(ByteBufAllocator byteBufAllocator, boolean bl) {
        SslHandler sslHandler = this.ctx.newHandler(byteBufAllocator, bl);
        this.initHandler(sslHandler);
        return sslHandler;
    }

    @Override
    protected final SslHandler newHandler(ByteBufAllocator byteBufAllocator, String string, int n, boolean bl) {
        SslHandler sslHandler = this.ctx.newHandler(byteBufAllocator, string, n, bl);
        this.initHandler(sslHandler);
        return sslHandler;
    }

    @Override
    public final SSLSessionContext sessionContext() {
        return this.ctx.sessionContext();
    }

    protected abstract void initEngine(SSLEngine var1);

    protected void initHandler(SslHandler sslHandler) {
        this.initEngine(sslHandler.engine());
    }
}

