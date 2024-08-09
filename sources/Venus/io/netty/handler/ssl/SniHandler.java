/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.ssl.AbstractSniHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.AsyncMapping;
import io.netty.util.DomainNameMapping;
import io.netty.util.Mapping;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;

public class SniHandler
extends AbstractSniHandler<SslContext> {
    private static final Selection EMPTY_SELECTION = new Selection(null, null);
    protected final AsyncMapping<String, SslContext> mapping;
    private volatile Selection selection = EMPTY_SELECTION;

    public SniHandler(Mapping<? super String, ? extends SslContext> mapping) {
        this(new AsyncMappingAdapter(mapping, null));
    }

    public SniHandler(DomainNameMapping<? extends SslContext> domainNameMapping) {
        this((Mapping<? super String, ? extends SslContext>)domainNameMapping);
    }

    public SniHandler(AsyncMapping<? super String, ? extends SslContext> asyncMapping) {
        this.mapping = ObjectUtil.checkNotNull(asyncMapping, "mapping");
    }

    public String hostname() {
        return this.selection.hostname;
    }

    public SslContext sslContext() {
        return this.selection.context;
    }

    @Override
    protected Future<SslContext> lookup(ChannelHandlerContext channelHandlerContext, String string) throws Exception {
        return this.mapping.map(string, channelHandlerContext.executor().newPromise());
    }

    @Override
    protected final void onLookupComplete(ChannelHandlerContext channelHandlerContext, String string, Future<SslContext> future) throws Exception {
        if (!future.isSuccess()) {
            Throwable throwable = future.cause();
            if (throwable instanceof Error) {
                throw (Error)throwable;
            }
            throw new DecoderException("failed to get the SslContext for " + string, throwable);
        }
        SslContext sslContext = future.getNow();
        this.selection = new Selection(sslContext, string);
        try {
            this.replaceHandler(channelHandlerContext, string, sslContext);
        } catch (Throwable throwable) {
            this.selection = EMPTY_SELECTION;
            PlatformDependent.throwException(throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void replaceHandler(ChannelHandlerContext channelHandlerContext, String string, SslContext sslContext) throws Exception {
        SslHandler sslHandler = null;
        try {
            sslHandler = sslContext.newHandler(channelHandlerContext.alloc());
            channelHandlerContext.pipeline().replace(this, SslHandler.class.getName(), (ChannelHandler)sslHandler);
            sslHandler = null;
        } finally {
            if (sslHandler != null) {
                ReferenceCountUtil.safeRelease(sslHandler.engine());
            }
        }
    }

    private static final class Selection {
        final SslContext context;
        final String hostname;

        Selection(SslContext sslContext, String string) {
            this.context = sslContext;
            this.hostname = string;
        }
    }

    private static final class AsyncMappingAdapter
    implements AsyncMapping<String, SslContext> {
        private final Mapping<? super String, ? extends SslContext> mapping;

        private AsyncMappingAdapter(Mapping<? super String, ? extends SslContext> mapping) {
            this.mapping = ObjectUtil.checkNotNull(mapping, "mapping");
        }

        @Override
        public Future<SslContext> map(String string, Promise<SslContext> promise) {
            SslContext sslContext;
            try {
                sslContext = this.mapping.map(string);
            } catch (Throwable throwable) {
                return promise.setFailure(throwable);
            }
            return promise.setSuccess(sslContext);
        }

        @Override
        public Future map(Object object, Promise promise) {
            return this.map((String)object, (Promise<SslContext>)promise);
        }

        AsyncMappingAdapter(Mapping mapping, 1 var2_2) {
            this(mapping);
        }
    }
}

