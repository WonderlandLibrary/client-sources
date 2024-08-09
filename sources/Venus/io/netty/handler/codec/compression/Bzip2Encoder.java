/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.compression.Bzip2BitWriter;
import io.netty.handler.codec.compression.Bzip2BlockCompressor;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Bzip2Encoder
extends MessageToByteEncoder<ByteBuf> {
    private State currentState = State.INIT;
    private final Bzip2BitWriter writer = new Bzip2BitWriter();
    private final int streamBlockSize;
    private int streamCRC;
    private Bzip2BlockCompressor blockCompressor;
    private volatile boolean finished;
    private volatile ChannelHandlerContext ctx;

    public Bzip2Encoder() {
        this(9);
    }

    public Bzip2Encoder(int n) {
        if (n < 1 || n > 9) {
            throw new IllegalArgumentException("blockSizeMultiplier: " + n + " (expected: 1-9)");
        }
        this.streamBlockSize = n * 100000;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        if (this.finished) {
            byteBuf2.writeBytes(byteBuf);
            return;
        }
        block6: while (true) {
            switch (4.$SwitchMap$io$netty$handler$codec$compression$Bzip2Encoder$State[this.currentState.ordinal()]) {
                case 1: {
                    byteBuf2.ensureWritable(4);
                    byteBuf2.writeMedium(4348520);
                    byteBuf2.writeByte(48 + this.streamBlockSize / 100000);
                    this.currentState = State.INIT_BLOCK;
                }
                case 2: {
                    this.blockCompressor = new Bzip2BlockCompressor(this.writer, this.streamBlockSize);
                    this.currentState = State.WRITE_DATA;
                }
                case 3: {
                    if (!byteBuf.isReadable()) {
                        return;
                    }
                    Bzip2BlockCompressor bzip2BlockCompressor = this.blockCompressor;
                    int n = Math.min(byteBuf.readableBytes(), bzip2BlockCompressor.availableSize());
                    int n2 = bzip2BlockCompressor.write(byteBuf, byteBuf.readerIndex(), n);
                    byteBuf.skipBytes(n2);
                    if (!bzip2BlockCompressor.isFull()) {
                        if (byteBuf.isReadable()) continue block6;
                        return;
                    }
                    this.currentState = State.CLOSE_BLOCK;
                }
                case 4: {
                    this.closeBlock(byteBuf2);
                    this.currentState = State.INIT_BLOCK;
                    continue block6;
                }
            }
            break;
        }
        throw new IllegalStateException();
    }

    private void closeBlock(ByteBuf byteBuf) {
        Bzip2BlockCompressor bzip2BlockCompressor = this.blockCompressor;
        if (!bzip2BlockCompressor.isEmpty()) {
            bzip2BlockCompressor.close(byteBuf);
            int n = bzip2BlockCompressor.crc();
            this.streamCRC = (this.streamCRC << 1 | this.streamCRC >>> 31) ^ n;
        }
    }

    public boolean isClosed() {
        return this.finished;
    }

    public ChannelFuture close() {
        return this.close(this.ctx().newPromise());
    }

    public ChannelFuture close(ChannelPromise channelPromise) {
        ChannelHandlerContext channelHandlerContext = this.ctx();
        EventExecutor eventExecutor = channelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            return this.finishEncode(channelHandlerContext, channelPromise);
        }
        eventExecutor.execute(new Runnable(this, channelPromise){
            final ChannelPromise val$promise;
            final Bzip2Encoder this$0;
            {
                this.this$0 = bzip2Encoder;
                this.val$promise = channelPromise;
            }

            @Override
            public void run() {
                ChannelFuture channelFuture = Bzip2Encoder.access$100(this.this$0, Bzip2Encoder.access$000(this.this$0), this.val$promise);
                channelFuture.addListener(new ChannelPromiseNotifier(this.val$promise));
            }
        });
        return channelPromise;
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        ChannelFuture channelFuture = this.finishEncode(channelHandlerContext, channelHandlerContext.newPromise());
        channelFuture.addListener(new ChannelFutureListener(this, channelHandlerContext, channelPromise){
            final ChannelHandlerContext val$ctx;
            final ChannelPromise val$promise;
            final Bzip2Encoder this$0;
            {
                this.this$0 = bzip2Encoder;
                this.val$ctx = channelHandlerContext;
                this.val$promise = channelPromise;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                this.val$ctx.close(this.val$promise);
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
        if (!channelFuture.isDone()) {
            channelHandlerContext.executor().schedule(new Runnable(this, channelHandlerContext, channelPromise){
                final ChannelHandlerContext val$ctx;
                final ChannelPromise val$promise;
                final Bzip2Encoder this$0;
                {
                    this.this$0 = bzip2Encoder;
                    this.val$ctx = channelHandlerContext;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    this.val$ctx.close(this.val$promise);
                }
            }, 10L, TimeUnit.SECONDS);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ChannelFuture finishEncode(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        if (this.finished) {
            channelPromise.setSuccess();
            return channelPromise;
        }
        this.finished = true;
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();
        this.closeBlock(byteBuf);
        int n = this.streamCRC;
        Bzip2BitWriter bzip2BitWriter = this.writer;
        try {
            bzip2BitWriter.writeBits(byteBuf, 24, 1536581L);
            bzip2BitWriter.writeBits(byteBuf, 24, 3690640L);
            bzip2BitWriter.writeInt(byteBuf, n);
            bzip2BitWriter.flush(byteBuf);
        } finally {
            this.blockCompressor = null;
        }
        return channelHandlerContext.writeAndFlush(byteBuf, channelPromise);
    }

    private ChannelHandlerContext ctx() {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext == null) {
            throw new IllegalStateException("not added to a pipeline");
        }
        return channelHandlerContext;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }

    static ChannelHandlerContext access$000(Bzip2Encoder bzip2Encoder) {
        return bzip2Encoder.ctx();
    }

    static ChannelFuture access$100(Bzip2Encoder bzip2Encoder, ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        return bzip2Encoder.finishEncode(channelHandlerContext, channelPromise);
    }

    private static enum State {
        INIT,
        INIT_BLOCK,
        WRITE_DATA,
        CLOSE_BLOCK;

    }
}

