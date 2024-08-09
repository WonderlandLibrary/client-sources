/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.ssl.NotSslRecordException;
import io.netty.handler.ssl.SniCompletionEvent;
import io.netty.handler.ssl.SslUtils;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.util.List;
import java.util.Locale;

public abstract class AbstractSniHandler<T>
extends ByteToMessageDecoder
implements ChannelOutboundHandler {
    private static final int MAX_SSL_RECORDS = 4;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractSniHandler.class);
    private boolean handshakeFailed;
    private boolean suppressRead;
    private boolean readPending;

    /*
     * Unable to fully structure code
     */
    @Override
    protected void decode(ChannelHandlerContext var1_1, ByteBuf var2_2, List<Object> var3_3) throws Exception {
        if (!this.suppressRead && !this.handshakeFailed) {
            block19: {
                var4_4 = var2_2.writerIndex();
                try {
                    block9: for (var5_5 = 0; var5_5 < 4; ++var5_5) {
                        var6_8 = var2_2.readerIndex();
                        var7_9 = var4_4 - var6_8;
                        if (var7_9 < 5) {
                            return;
                        }
                        var8_10 = var2_2.getUnsignedByte(var6_8);
                        switch (var8_10) {
                            case 20: 
                            case 21: {
                                var9_11 = SslUtils.getEncryptedPacketLength(var2_2, var6_8);
                                if (var9_11 == -2) {
                                    this.handshakeFailed = true;
                                    var10_12 = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(var2_2));
                                    var2_2.skipBytes(var2_2.readableBytes());
                                    var1_1.fireUserEventTriggered(new SniCompletionEvent(var10_12));
                                    SslUtils.handleHandshakeFailure(var1_1, var10_12, true);
                                    throw var10_12;
                                }
                                if (var9_11 == -1 || var4_4 - var6_8 - 5 < var9_11) {
                                    return;
                                }
                                var2_2.skipBytes(var9_11);
                                continue block9;
                            }
                            case 22: {
                                var10_13 = var2_2.getUnsignedByte(var6_8 + 1);
                                if (var10_13 == 3) {
                                    var11_14 = var2_2.getUnsignedShort(var6_8 + 3) + 5;
                                    if (var7_9 < var11_14) {
                                        return;
                                    }
                                    var12_15 = var6_8 + var11_14;
                                    var13_16 = var6_8 + 43;
                                    if (var12_15 - var13_16 < 6) break block19;
                                    var14_17 = var2_2.getUnsignedByte(var13_16);
                                    var15_18 = var2_2.getUnsignedShort(var13_16 += var14_17 + 1);
                                    var16_19 = var2_2.getUnsignedByte(var13_16 += var15_18 + 2);
                                    var13_16 += var16_19 + 1;
                                    ** if ((var18_21 = (var13_16 += 2) + (var17_20 = var2_2.getUnsignedShort((int)var13_16))) > var12_15) goto lbl59
                                    while (var18_21 - var13_16 >= 4) {
                                        var19_22 = var2_2.getUnsignedShort(var13_16);
                                        var13_16 += 2;
                                        if (var18_21 - (var13_16 += 2) < (var20_23 = var2_2.getUnsignedShort(var13_16))) break block19;
                                        if (var19_22 == 0) {
                                            if (var18_21 - (var13_16 += 2) < 3) break block19;
                                            var21_24 = var2_2.getUnsignedByte(var13_16);
                                            ++var13_16;
                                            if (var21_24 != 0 || var18_21 - (var13_16 += 2) < (var22_25 = var2_2.getUnsignedShort(var13_16))) break block19;
                                            var23_26 = var2_2.toString(var13_16, var22_25, CharsetUtil.US_ASCII);
                                            try {
                                                this.select(var1_1, var23_26.toLowerCase(Locale.US));
                                            } catch (Throwable var24_27) {
                                                PlatformDependent.throwException(var24_27);
                                            }
                                            return;
                                        }
                                        var13_16 += var20_23;
lbl-1000:
                                        // 2 sources

                                        {
                                        }
                                    }
lbl59:
                                    // 2 sources

                                    break block19;
                                }
                            }
                            default: {
                                break block19;
                            }
                        }
                    }
                } catch (NotSslRecordException var5_6) {
                    throw var5_6;
                } catch (Exception var5_7) {
                    if (!AbstractSniHandler.logger.isDebugEnabled()) break block19;
                    AbstractSniHandler.logger.debug("Unexpected client hello packet: " + ByteBufUtil.hexDump(var2_2), var5_7);
                }
            }
            this.select(var1_1, null);
        }
    }

    private void select(ChannelHandlerContext channelHandlerContext, String string) throws Exception {
        Future<T> future = this.lookup(channelHandlerContext, string);
        if (future.isDone()) {
            this.fireSniCompletionEvent(channelHandlerContext, string, future);
            this.onLookupComplete(channelHandlerContext, string, future);
        } else {
            this.suppressRead = true;
            future.addListener(new FutureListener<T>(this, channelHandlerContext, string){
                final ChannelHandlerContext val$ctx;
                final String val$hostname;
                final AbstractSniHandler this$0;
                {
                    this.this$0 = abstractSniHandler;
                    this.val$ctx = channelHandlerContext;
                    this.val$hostname = string;
                }

                @Override
                public void operationComplete(Future<T> future) throws Exception {
                    try {
                        AbstractSniHandler.access$002(this.this$0, false);
                        try {
                            AbstractSniHandler.access$100(this.this$0, this.val$ctx, this.val$hostname, future);
                            this.this$0.onLookupComplete(this.val$ctx, this.val$hostname, future);
                        } catch (DecoderException decoderException) {
                            this.val$ctx.fireExceptionCaught(decoderException);
                        } catch (Exception exception) {
                            this.val$ctx.fireExceptionCaught(new DecoderException(exception));
                        } catch (Throwable throwable) {
                            this.val$ctx.fireExceptionCaught(throwable);
                        }
                    } finally {
                        if (AbstractSniHandler.access$200(this.this$0)) {
                            AbstractSniHandler.access$202(this.this$0, false);
                            this.val$ctx.read();
                        }
                    }
                }
            });
        }
    }

    private void fireSniCompletionEvent(ChannelHandlerContext channelHandlerContext, String string, Future<T> future) {
        Throwable throwable = future.cause();
        if (throwable == null) {
            channelHandlerContext.fireUserEventTriggered(new SniCompletionEvent(string));
        } else {
            channelHandlerContext.fireUserEventTriggered(new SniCompletionEvent(string, throwable));
        }
    }

    protected abstract Future<T> lookup(ChannelHandlerContext var1, String var2) throws Exception;

    protected abstract void onLookupComplete(ChannelHandlerContext var1, String var2, Future<T> var3) throws Exception;

    @Override
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.suppressRead) {
            this.readPending = true;
        } else {
            channelHandlerContext.read();
        }
    }

    @Override
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.disconnect(channelPromise);
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.close(channelPromise);
    }

    @Override
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.write(object, channelPromise);
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }

    static boolean access$002(AbstractSniHandler abstractSniHandler, boolean bl) {
        abstractSniHandler.suppressRead = bl;
        return abstractSniHandler.suppressRead;
    }

    static void access$100(AbstractSniHandler abstractSniHandler, ChannelHandlerContext channelHandlerContext, String string, Future future) {
        abstractSniHandler.fireSniCompletionEvent(channelHandlerContext, string, future);
    }

    static boolean access$200(AbstractSniHandler abstractSniHandler) {
        return abstractSniHandler.readPending;
    }

    static boolean access$202(AbstractSniHandler abstractSniHandler, boolean bl) {
        abstractSniHandler.readPending = bl;
        return abstractSniHandler.readPending;
    }
}

