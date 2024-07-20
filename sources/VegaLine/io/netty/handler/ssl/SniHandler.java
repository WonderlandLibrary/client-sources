/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.ssl.NotSslRecordException;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SslUtils;
import io.netty.util.AsyncMapping;
import io.netty.util.CharsetUtil;
import io.netty.util.DomainNameMapping;
import io.netty.util.Mapping;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.IDN;
import java.net.SocketAddress;
import java.util.List;
import java.util.Locale;

public class SniHandler
extends ByteToMessageDecoder
implements ChannelOutboundHandler {
    private static final int MAX_SSL_RECORDS = 4;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SniHandler.class);
    private static final Selection EMPTY_SELECTION = new Selection(null, null);
    protected final AsyncMapping<String, SslContext> mapping;
    private boolean handshakeFailed;
    private boolean suppressRead;
    private boolean readPending;
    private volatile Selection selection = EMPTY_SELECTION;

    public SniHandler(Mapping<? super String, ? extends SslContext> mapping) {
        this(new AsyncMappingAdapter(mapping));
    }

    public SniHandler(DomainNameMapping<? extends SslContext> mapping) {
        this((Mapping<? super String, ? extends SslContext>)mapping);
    }

    public SniHandler(AsyncMapping<? super String, ? extends SslContext> mapping) {
        this.mapping = ObjectUtil.checkNotNull(mapping, "mapping");
    }

    public String hostname() {
        return this.selection.hostname;
    }

    public SslContext sslContext() {
        return this.selection.context;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (!this.suppressRead && !this.handshakeFailed) {
            block18: {
                writerIndex = in.writerIndex();
                try {
                    block8: for (i = 0; i < 4; ++i) {
                        readerIndex = in.readerIndex();
                        readableBytes = writerIndex - readerIndex;
                        if (readableBytes < 5) {
                            return;
                        }
                        command = in.getUnsignedByte(readerIndex);
                        switch (command) {
                            case 20: 
                            case 21: {
                                len = SslUtils.getEncryptedPacketLength(in, readerIndex);
                                if (len == -2) {
                                    this.handshakeFailed = true;
                                    e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
                                    in.skipBytes(in.readableBytes());
                                    SslUtils.notifyHandshakeFailure(ctx, e);
                                    throw e;
                                }
                                if (len == -1 || writerIndex - readerIndex - 5 < len) {
                                    return;
                                }
                                in.skipBytes(len);
                                continue block8;
                            }
                            case 22: {
                                majorVersion = in.getUnsignedByte(readerIndex + 1);
                                if (majorVersion == 3) {
                                    packetLength = in.getUnsignedShort(readerIndex + 3) + 5;
                                    if (readableBytes < packetLength) {
                                        return;
                                    }
                                    endOffset = readerIndex + packetLength;
                                    offset = readerIndex + 43;
                                    if (endOffset - offset < 6) break block18;
                                    sessionIdLength = in.getUnsignedByte(offset);
                                    cipherSuitesLength = in.getUnsignedShort(offset += sessionIdLength + 1);
                                    compressionMethodLength = in.getUnsignedByte(offset += cipherSuitesLength + 2);
                                    offset += compressionMethodLength + 1;
                                    ** if ((extensionsLimit = (offset += 2) + (extensionsLength = in.getUnsignedShort((int)offset))) > endOffset) goto lbl57
                                    while (extensionsLimit - offset >= 4) {
                                        extensionType = in.getUnsignedShort(offset);
                                        offset += 2;
                                        if (extensionsLimit - (offset += 2) < (extensionLength = in.getUnsignedShort(offset))) break block18;
                                        if (extensionType == 0) {
                                            if (extensionsLimit - (offset += 2) < 3) break block18;
                                            serverNameType = in.getUnsignedByte(offset);
                                            ++offset;
                                            if (serverNameType != 0 || extensionsLimit - (offset += 2) < (serverNameLength = in.getUnsignedShort(offset))) break block18;
                                            hostname = in.toString(offset, serverNameLength, CharsetUtil.UTF_8);
                                            try {
                                                this.select(ctx, IDN.toASCII(hostname, 1).toLowerCase(Locale.US));
                                            } catch (Throwable t) {
                                                PlatformDependent.throwException(t);
                                            }
                                            return;
                                        }
                                        offset += extensionLength;
lbl-1000:
                                        // 2 sources

                                        {
                                        }
                                    }
lbl57:
                                    // 2 sources

                                    break block18;
                                }
                            }
                            default: {
                                break block18;
                            }
                        }
                    }
                } catch (Throwable e) {
                    if (!SniHandler.logger.isDebugEnabled()) break block18;
                    SniHandler.logger.debug("Unexpected client hello packet: " + ByteBufUtil.hexDump(in), e);
                }
            }
            this.select(ctx, null);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void select(final ChannelHandlerContext ctx, final String hostname) throws Exception {
        Future<SslContext> future = this.lookup(ctx, hostname);
        if (future.isDone()) {
            if (!future.isSuccess()) throw new DecoderException("failed to get the SslContext for " + hostname, future.cause());
            this.onSslContext(ctx, hostname, future.getNow());
            return;
        } else {
            this.suppressRead = true;
            future.addListener((GenericFutureListener<Future<SslContext>>)new FutureListener<SslContext>(){

                @Override
                public void operationComplete(Future<SslContext> future) throws Exception {
                    try {
                        SniHandler.this.suppressRead = false;
                        if (future.isSuccess()) {
                            try {
                                SniHandler.this.onSslContext(ctx, hostname, future.getNow());
                            } catch (Throwable cause) {
                                ctx.fireExceptionCaught(new DecoderException(cause));
                            }
                        } else {
                            ctx.fireExceptionCaught(new DecoderException("failed to get the SslContext for " + hostname, future.cause()));
                        }
                    } finally {
                        if (SniHandler.this.readPending) {
                            SniHandler.this.readPending = false;
                            ctx.read();
                        }
                    }
                }
            });
        }
    }

    protected Future<SslContext> lookup(ChannelHandlerContext ctx, String hostname) throws Exception {
        return this.mapping.map(hostname, ctx.executor().newPromise());
    }

    private void onSslContext(ChannelHandlerContext ctx, String hostname, SslContext sslContext) {
        this.selection = new Selection(sslContext, hostname);
        try {
            this.replaceHandler(ctx, hostname, sslContext);
        } catch (Throwable cause) {
            this.selection = EMPTY_SELECTION;
            PlatformDependent.throwException(cause);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void replaceHandler(ChannelHandlerContext ctx, String hostname, SslContext sslContext) throws Exception {
        SslHandler sslHandler = null;
        try {
            sslHandler = sslContext.newHandler(ctx.alloc());
            ctx.pipeline().replace(this, SslHandler.class.getName(), (ChannelHandler)sslHandler);
            sslHandler = null;
        } finally {
            if (sslHandler != null) {
                ReferenceCountUtil.safeRelease(sslHandler.engine());
            }
        }
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.connect(remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.disconnect(promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.close(promise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.deregister(promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        if (this.suppressRead) {
            this.readPending = true;
        } else {
            ctx.read();
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private static final class Selection {
        final SslContext context;
        final String hostname;

        Selection(SslContext context, String hostname) {
            this.context = context;
            this.hostname = hostname;
        }
    }

    private static final class AsyncMappingAdapter
    implements AsyncMapping<String, SslContext> {
        private final Mapping<? super String, ? extends SslContext> mapping;

        private AsyncMappingAdapter(Mapping<? super String, ? extends SslContext> mapping) {
            this.mapping = ObjectUtil.checkNotNull(mapping, "mapping");
        }

        @Override
        public Future<SslContext> map(String input, Promise<SslContext> promise) {
            SslContext context;
            try {
                context = this.mapping.map(input);
            } catch (Throwable cause) {
                return promise.setFailure(cause);
            }
            return promise.setSuccess(context);
        }
    }
}

