/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.compression.ByteBufChecksum;
import io.netty.handler.codec.compression.CompressionException;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.zip.Checksum;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Exception;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.xxhash.XXHashFactory;

public class Lz4FrameEncoder
extends MessageToByteEncoder<ByteBuf> {
    private static final EncoderException ENCODE_FINSHED_EXCEPTION = ThrowableUtil.unknownStackTrace(new EncoderException(new IllegalStateException("encode finished and not enough space to write remaining data")), Lz4FrameEncoder.class, "encode");
    static final int DEFAULT_MAX_ENCODE_SIZE = Integer.MAX_VALUE;
    private final int blockSize;
    private final LZ4Compressor compressor;
    private final ByteBufChecksum checksum;
    private final int compressionLevel;
    private ByteBuf buffer;
    private final int maxEncodeSize;
    private volatile boolean finished;
    private volatile ChannelHandlerContext ctx;

    public Lz4FrameEncoder() {
        this(false);
    }

    public Lz4FrameEncoder(boolean bl) {
        this(LZ4Factory.fastestInstance(), bl, 65536, XXHashFactory.fastestInstance().newStreamingHash32(-1756908916).asChecksum());
    }

    public Lz4FrameEncoder(LZ4Factory lZ4Factory, boolean bl, int n, Checksum checksum) {
        this(lZ4Factory, bl, n, checksum, Integer.MAX_VALUE);
    }

    public Lz4FrameEncoder(LZ4Factory lZ4Factory, boolean bl, int n, Checksum checksum, int n2) {
        if (lZ4Factory == null) {
            throw new NullPointerException("factory");
        }
        if (checksum == null) {
            throw new NullPointerException("checksum");
        }
        this.compressor = bl ? lZ4Factory.highCompressor() : lZ4Factory.fastCompressor();
        this.checksum = ByteBufChecksum.wrapChecksum(checksum);
        this.compressionLevel = Lz4FrameEncoder.compressionLevel(n);
        this.blockSize = n;
        this.maxEncodeSize = ObjectUtil.checkPositive(n2, "maxEncodeSize");
        this.finished = false;
    }

    private static int compressionLevel(int n) {
        if (n < 64 || n > 0x2000000) {
            throw new IllegalArgumentException(String.format("blockSize: %d (expected: %d-%d)", n, 64, 0x2000000));
        }
        int n2 = 32 - Integer.numberOfLeadingZeros(n - 1);
        n2 = Math.max(0, n2 - 10);
        return n2;
    }

    @Override
    protected ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, boolean bl) {
        return this.allocateBuffer(channelHandlerContext, byteBuf, bl, true);
    }

    private ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, boolean bl, boolean bl2) {
        int n = 0;
        int n2 = byteBuf.readableBytes() + this.buffer.readableBytes();
        if (n2 < 0) {
            throw new EncoderException("too much data to allocate a buffer for compression");
        }
        while (n2 > 0) {
            int n3 = Math.min(this.blockSize, n2);
            n2 -= n3;
            n += this.compressor.maxCompressedLength(n3) + 21;
        }
        if (n > this.maxEncodeSize || 0 > n) {
            throw new EncoderException(String.format("requested encode buffer size (%d bytes) exceeds the maximum allowable size (%d bytes)", n, this.maxEncodeSize));
        }
        if (bl2 && n < this.blockSize) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (bl) {
            return channelHandlerContext.alloc().ioBuffer(n, n);
        }
        return channelHandlerContext.alloc().heapBuffer(n, n);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int n;
        if (this.finished) {
            if (!byteBuf2.isWritable(byteBuf.readableBytes())) {
                throw ENCODE_FINSHED_EXCEPTION;
            }
            byteBuf2.writeBytes(byteBuf);
            return;
        }
        ByteBuf byteBuf3 = this.buffer;
        while ((n = byteBuf.readableBytes()) > 0) {
            int n2 = Math.min(n, byteBuf3.writableBytes());
            byteBuf.readBytes(byteBuf3, n2);
            if (byteBuf3.isWritable()) continue;
            this.flushBufferedData(byteBuf2);
        }
    }

    private void flushBufferedData(ByteBuf byteBuf) {
        int n;
        int n2;
        int n3 = this.buffer.readableBytes();
        if (n3 == 0) {
            return;
        }
        this.checksum.reset();
        this.checksum.update(this.buffer, this.buffer.readerIndex(), n3);
        int n4 = (int)this.checksum.getValue();
        int n5 = this.compressor.maxCompressedLength(n3) + 21;
        byteBuf.ensureWritable(n5);
        int n6 = byteBuf.writerIndex();
        try {
            ByteBuffer byteBuffer = byteBuf.internalNioBuffer(n6 + 21, byteBuf.writableBytes() - 21);
            int n7 = byteBuffer.position();
            this.compressor.compress(this.buffer.internalNioBuffer(this.buffer.readerIndex(), n3), byteBuffer);
            n2 = byteBuffer.position() - n7;
        } catch (LZ4Exception lZ4Exception) {
            throw new CompressionException(lZ4Exception);
        }
        if (n2 >= n3) {
            n = 16;
            n2 = n3;
            byteBuf.setBytes(n6 + 21, this.buffer, 0, n3);
        } else {
            n = 32;
        }
        byteBuf.setLong(n6, 5501767354678207339L);
        byteBuf.setByte(n6 + 8, (byte)(n | this.compressionLevel));
        byteBuf.setIntLE(n6 + 9, n2);
        byteBuf.setIntLE(n6 + 13, n3);
        byteBuf.setIntLE(n6 + 17, n4);
        byteBuf.writerIndex(n6 + 21 + n2);
        this.buffer.clear();
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.buffer != null && this.buffer.isReadable()) {
            ByteBuf byteBuf = this.allocateBuffer(channelHandlerContext, Unpooled.EMPTY_BUFFER, this.isPreferDirect(), false);
            this.flushBufferedData(byteBuf);
            channelHandlerContext.write(byteBuf);
        }
        channelHandlerContext.flush();
    }

    private ChannelFuture finishEncode(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        if (this.finished) {
            channelPromise.setSuccess();
            return channelPromise;
        }
        this.finished = true;
        ByteBuf byteBuf = channelHandlerContext.alloc().heapBuffer(this.compressor.maxCompressedLength(this.buffer.readableBytes()) + 21);
        this.flushBufferedData(byteBuf);
        int n = byteBuf.writerIndex();
        byteBuf.setLong(n, 5501767354678207339L);
        byteBuf.setByte(n + 8, (byte)(0x10 | this.compressionLevel));
        byteBuf.setInt(n + 9, 0);
        byteBuf.setInt(n + 13, 0);
        byteBuf.setInt(n + 17, 0);
        byteBuf.writerIndex(n + 21);
        return channelHandlerContext.writeAndFlush(byteBuf, channelPromise);
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
            final Lz4FrameEncoder this$0;
            {
                this.this$0 = lz4FrameEncoder;
                this.val$promise = channelPromise;
            }

            @Override
            public void run() {
                ChannelFuture channelFuture = Lz4FrameEncoder.access$100(this.this$0, Lz4FrameEncoder.access$000(this.this$0), this.val$promise);
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
            final Lz4FrameEncoder this$0;
            {
                this.this$0 = lz4FrameEncoder;
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
                final Lz4FrameEncoder this$0;
                {
                    this.this$0 = lz4FrameEncoder;
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

    private ChannelHandlerContext ctx() {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext == null) {
            throw new IllegalStateException("not added to a pipeline");
        }
        return channelHandlerContext;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        this.ctx = channelHandlerContext;
        this.buffer = Unpooled.wrappedBuffer(new byte[this.blockSize]);
        this.buffer.clear();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerRemoved(channelHandlerContext);
        if (this.buffer != null) {
            this.buffer.release();
            this.buffer = null;
        }
    }

    final ByteBuf getBackingBuffer() {
        return this.buffer;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }

    @Override
    protected ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, Object object, boolean bl) throws Exception {
        return this.allocateBuffer(channelHandlerContext, (ByteBuf)object, bl);
    }

    static ChannelHandlerContext access$000(Lz4FrameEncoder lz4FrameEncoder) {
        return lz4FrameEncoder.ctx();
    }

    static ChannelFuture access$100(Lz4FrameEncoder lz4FrameEncoder, ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        return lz4FrameEncoder.finishEncode(channelHandlerContext, channelPromise);
    }
}

