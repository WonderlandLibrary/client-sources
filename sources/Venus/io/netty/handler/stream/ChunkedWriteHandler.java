/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.handler.stream.ChunkedInput;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

public class ChunkedWriteHandler
extends ChannelDuplexHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);
    private final Queue<PendingWrite> queue = new ArrayDeque<PendingWrite>();
    private volatile ChannelHandlerContext ctx;
    private PendingWrite currentWrite;

    public ChunkedWriteHandler() {
    }

    @Deprecated
    public ChunkedWriteHandler(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("maxPendingWrites: " + n + " (expected: > 0)");
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
    }

    public void resumeTransfer() {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext == null) {
            return;
        }
        if (channelHandlerContext.executor().inEventLoop()) {
            this.resumeTransfer0(channelHandlerContext);
        } else {
            channelHandlerContext.executor().execute(new Runnable(this, channelHandlerContext){
                final ChannelHandlerContext val$ctx;
                final ChunkedWriteHandler this$0;
                {
                    this.this$0 = chunkedWriteHandler;
                    this.val$ctx = channelHandlerContext;
                }

                @Override
                public void run() {
                    ChunkedWriteHandler.access$000(this.this$0, this.val$ctx);
                }
            });
        }
    }

    private void resumeTransfer0(ChannelHandlerContext channelHandlerContext) {
        block2: {
            try {
                this.doFlush(channelHandlerContext);
            } catch (Exception exception) {
                if (!logger.isWarnEnabled()) break block2;
                logger.warn("Unexpected exception while sending chunks.", exception);
            }
        }
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        this.queue.add(new PendingWrite(object, channelPromise));
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.doFlush(channelHandlerContext);
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.doFlush(channelHandlerContext);
        channelHandlerContext.fireChannelInactive();
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isWritable()) {
            this.doFlush(channelHandlerContext);
        }
        channelHandlerContext.fireChannelWritabilityChanged();
    }

    private void discard(Throwable throwable) {
        while (true) {
            PendingWrite pendingWrite = this.currentWrite;
            if (this.currentWrite == null) {
                pendingWrite = this.queue.poll();
            } else {
                this.currentWrite = null;
            }
            if (pendingWrite == null) break;
            Object object = pendingWrite.msg;
            if (object instanceof ChunkedInput) {
                ChunkedInput chunkedInput = (ChunkedInput)object;
                try {
                    if (!chunkedInput.isEndOfInput()) {
                        if (throwable == null) {
                            throwable = new ClosedChannelException();
                        }
                        pendingWrite.fail(throwable);
                    } else {
                        pendingWrite.success(chunkedInput.length());
                    }
                    ChunkedWriteHandler.closeInput(chunkedInput);
                } catch (Exception exception) {
                    pendingWrite.fail(exception);
                    logger.warn(ChunkedInput.class.getSimpleName() + ".isEndOfInput() failed", exception);
                    ChunkedWriteHandler.closeInput(chunkedInput);
                }
                continue;
            }
            if (throwable == null) {
                throwable = new ClosedChannelException();
            }
            pendingWrite.fail(throwable);
        }
    }

    private void doFlush(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        if (!channel.isActive()) {
            this.discard(null);
            return;
        }
        boolean bl = true;
        ByteBufAllocator byteBufAllocator = channelHandlerContext.alloc();
        while (channel.isWritable()) {
            if (this.currentWrite == null) {
                this.currentWrite = this.queue.poll();
            }
            if (this.currentWrite == null) break;
            PendingWrite pendingWrite = this.currentWrite;
            Object object = pendingWrite.msg;
            if (object instanceof ChunkedInput) {
                boolean bl2;
                boolean bl3;
                ChunkedInput chunkedInput = (ChunkedInput)object;
                ByteBuf byteBuf = null;
                try {
                    byteBuf = (ByteBuf)chunkedInput.readChunk(byteBufAllocator);
                    bl3 = chunkedInput.isEndOfInput();
                    bl2 = byteBuf == null ? !bl3 : false;
                } catch (Throwable throwable) {
                    this.currentWrite = null;
                    if (byteBuf != null) {
                        ReferenceCountUtil.release(byteBuf);
                    }
                    pendingWrite.fail(throwable);
                    ChunkedWriteHandler.closeInput(chunkedInput);
                    break;
                }
                if (bl2) break;
                if (byteBuf == null) {
                    byteBuf = Unpooled.EMPTY_BUFFER;
                }
                ChannelFuture channelFuture = channelHandlerContext.write(byteBuf);
                if (bl3) {
                    this.currentWrite = null;
                    channelFuture.addListener(new ChannelFutureListener(this, pendingWrite, chunkedInput){
                        final PendingWrite val$currentWrite;
                        final ChunkedInput val$chunks;
                        final ChunkedWriteHandler this$0;
                        {
                            this.this$0 = chunkedWriteHandler;
                            this.val$currentWrite = pendingWrite;
                            this.val$chunks = chunkedInput;
                        }

                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            this.val$currentWrite.progress(this.val$chunks.progress(), this.val$chunks.length());
                            this.val$currentWrite.success(this.val$chunks.length());
                            ChunkedWriteHandler.access$100(this.val$chunks);
                        }

                        @Override
                        public void operationComplete(Future future) throws Exception {
                            this.operationComplete((ChannelFuture)future);
                        }
                    });
                } else if (channel.isWritable()) {
                    channelFuture.addListener(new ChannelFutureListener(this, object, pendingWrite, chunkedInput){
                        final Object val$pendingMessage;
                        final PendingWrite val$currentWrite;
                        final ChunkedInput val$chunks;
                        final ChunkedWriteHandler this$0;
                        {
                            this.this$0 = chunkedWriteHandler;
                            this.val$pendingMessage = object;
                            this.val$currentWrite = pendingWrite;
                            this.val$chunks = chunkedInput;
                        }

                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (!channelFuture.isSuccess()) {
                                ChunkedWriteHandler.access$100((ChunkedInput)this.val$pendingMessage);
                                this.val$currentWrite.fail(channelFuture.cause());
                            } else {
                                this.val$currentWrite.progress(this.val$chunks.progress(), this.val$chunks.length());
                            }
                        }

                        @Override
                        public void operationComplete(Future future) throws Exception {
                            this.operationComplete((ChannelFuture)future);
                        }
                    });
                } else {
                    channelFuture.addListener(new ChannelFutureListener(this, object, pendingWrite, chunkedInput, channel){
                        final Object val$pendingMessage;
                        final PendingWrite val$currentWrite;
                        final ChunkedInput val$chunks;
                        final Channel val$channel;
                        final ChunkedWriteHandler this$0;
                        {
                            this.this$0 = chunkedWriteHandler;
                            this.val$pendingMessage = object;
                            this.val$currentWrite = pendingWrite;
                            this.val$chunks = chunkedInput;
                            this.val$channel = channel;
                        }

                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (!channelFuture.isSuccess()) {
                                ChunkedWriteHandler.access$100((ChunkedInput)this.val$pendingMessage);
                                this.val$currentWrite.fail(channelFuture.cause());
                            } else {
                                this.val$currentWrite.progress(this.val$chunks.progress(), this.val$chunks.length());
                                if (this.val$channel.isWritable()) {
                                    this.this$0.resumeTransfer();
                                }
                            }
                        }

                        @Override
                        public void operationComplete(Future future) throws Exception {
                            this.operationComplete((ChannelFuture)future);
                        }
                    });
                }
                channelHandlerContext.flush();
                bl = false;
            } else {
                this.currentWrite = null;
                channelHandlerContext.write(object, pendingWrite.promise);
                bl = true;
            }
            if (channel.isActive()) continue;
            this.discard(new ClosedChannelException());
            break;
        }
        if (bl) {
            channelHandlerContext.flush();
        }
    }

    private static void closeInput(ChunkedInput<?> chunkedInput) {
        block2: {
            try {
                chunkedInput.close();
            } catch (Throwable throwable) {
                if (!logger.isWarnEnabled()) break block2;
                logger.warn("Failed to close a chunked input.", throwable);
            }
        }
    }

    static void access$000(ChunkedWriteHandler chunkedWriteHandler, ChannelHandlerContext channelHandlerContext) {
        chunkedWriteHandler.resumeTransfer0(channelHandlerContext);
    }

    static void access$100(ChunkedInput chunkedInput) {
        ChunkedWriteHandler.closeInput(chunkedInput);
    }

    private static final class PendingWrite {
        final Object msg;
        final ChannelPromise promise;

        PendingWrite(Object object, ChannelPromise channelPromise) {
            this.msg = object;
            this.promise = channelPromise;
        }

        void fail(Throwable throwable) {
            ReferenceCountUtil.release(this.msg);
            this.promise.tryFailure(throwable);
        }

        void success(long l) {
            if (this.promise.isDone()) {
                return;
            }
            this.progress(l, l);
            this.promise.trySuccess();
        }

        void progress(long l, long l2) {
            if (this.promise instanceof ChannelProgressivePromise) {
                ((ChannelProgressivePromise)this.promise).tryProgress(l, l2);
            }
        }
    }
}

