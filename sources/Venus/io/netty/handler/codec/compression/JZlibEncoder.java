/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.jcraft.jzlib.Deflater
 *  com.jcraft.jzlib.JZlib
 */
package io.netty.handler.codec.compression;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.JZlib;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.handler.codec.compression.ZlibEncoder;
import io.netty.handler.codec.compression.ZlibUtil;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.EmptyArrays;
import java.util.concurrent.TimeUnit;

public class JZlibEncoder
extends ZlibEncoder {
    private final int wrapperOverhead;
    private final Deflater z = new Deflater();
    private volatile boolean finished;
    private volatile ChannelHandlerContext ctx;

    public JZlibEncoder() {
        this(6);
    }

    public JZlibEncoder(int n) {
        this(ZlibWrapper.ZLIB, n);
    }

    public JZlibEncoder(ZlibWrapper zlibWrapper) {
        this(zlibWrapper, 6);
    }

    public JZlibEncoder(ZlibWrapper zlibWrapper, int n) {
        this(zlibWrapper, n, 15, 8);
    }

    public JZlibEncoder(ZlibWrapper zlibWrapper, int n, int n2, int n3) {
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        if (n2 < 9 || n2 > 15) {
            throw new IllegalArgumentException("windowBits: " + n2 + " (expected: 9-15)");
        }
        if (n3 < 1 || n3 > 9) {
            throw new IllegalArgumentException("memLevel: " + n3 + " (expected: 1-9)");
        }
        if (zlibWrapper == null) {
            throw new NullPointerException("wrapper");
        }
        if (zlibWrapper == ZlibWrapper.ZLIB_OR_NONE) {
            throw new IllegalArgumentException("wrapper '" + (Object)((Object)ZlibWrapper.ZLIB_OR_NONE) + "' is not allowed for compression.");
        }
        int n4 = this.z.init(n, n2, n3, ZlibUtil.convertWrapperType(zlibWrapper));
        if (n4 != 0) {
            ZlibUtil.fail(this.z, "initialization failure", n4);
        }
        this.wrapperOverhead = ZlibUtil.wrapperOverhead(zlibWrapper);
    }

    public JZlibEncoder(byte[] byArray) {
        this(6, byArray);
    }

    public JZlibEncoder(int n, byte[] byArray) {
        this(n, 15, 8, byArray);
    }

    public JZlibEncoder(int n, int n2, int n3, byte[] byArray) {
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        if (n2 < 9 || n2 > 15) {
            throw new IllegalArgumentException("windowBits: " + n2 + " (expected: 9-15)");
        }
        if (n3 < 1 || n3 > 9) {
            throw new IllegalArgumentException("memLevel: " + n3 + " (expected: 1-9)");
        }
        if (byArray == null) {
            throw new NullPointerException("dictionary");
        }
        int n4 = this.z.deflateInit(n, n2, n3, JZlib.W_ZLIB);
        if (n4 != 0) {
            ZlibUtil.fail(this.z, "initialization failure", n4);
        } else {
            n4 = this.z.deflateSetDictionary(byArray, byArray.length);
            if (n4 != 0) {
                ZlibUtil.fail(this.z, "failed to set the dictionary", n4);
            }
        }
        this.wrapperOverhead = ZlibUtil.wrapperOverhead(ZlibWrapper.ZLIB);
    }

    @Override
    public ChannelFuture close() {
        return this.close(this.ctx().channel().newPromise());
    }

    @Override
    public ChannelFuture close(ChannelPromise channelPromise) {
        ChannelHandlerContext channelHandlerContext = this.ctx();
        EventExecutor eventExecutor = channelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            return this.finishEncode(channelHandlerContext, channelPromise);
        }
        ChannelPromise channelPromise2 = channelHandlerContext.newPromise();
        eventExecutor.execute(new Runnable(this, channelPromise2, channelPromise){
            final ChannelPromise val$p;
            final ChannelPromise val$promise;
            final JZlibEncoder this$0;
            {
                this.this$0 = jZlibEncoder;
                this.val$p = channelPromise;
                this.val$promise = channelPromise2;
            }

            @Override
            public void run() {
                ChannelFuture channelFuture = JZlibEncoder.access$100(this.this$0, JZlibEncoder.access$000(this.this$0), this.val$p);
                channelFuture.addListener(new ChannelPromiseNotifier(this.val$promise));
            }
        });
        return channelPromise2;
    }

    private ChannelHandlerContext ctx() {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext == null) {
            throw new IllegalStateException("not added to a pipeline");
        }
        return channelHandlerContext;
    }

    @Override
    public boolean isClosed() {
        return this.finished;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        if (this.finished) {
            byteBuf2.writeBytes(byteBuf);
            return;
        }
        int n = byteBuf.readableBytes();
        if (n == 0) {
            return;
        }
        try {
            int n2;
            int n3;
            boolean bl = byteBuf.hasArray();
            this.z.avail_in = n;
            if (bl) {
                this.z.next_in = byteBuf.array();
                this.z.next_in_index = byteBuf.arrayOffset() + byteBuf.readerIndex();
            } else {
                byte[] byArray = new byte[n];
                byteBuf.getBytes(byteBuf.readerIndex(), byArray);
                this.z.next_in = byArray;
                this.z.next_in_index = 0;
            }
            int n4 = this.z.next_in_index;
            int n5 = (int)Math.ceil((double)n * 1.001) + 12 + this.wrapperOverhead;
            byteBuf2.ensureWritable(n5);
            this.z.avail_out = n5;
            this.z.next_out = byteBuf2.array();
            int n6 = this.z.next_out_index = byteBuf2.arrayOffset() + byteBuf2.writerIndex();
            try {
                n3 = this.z.deflate(2);
            } finally {
                byteBuf.skipBytes(this.z.next_in_index - n4);
            }
            if (n3 != 0) {
                ZlibUtil.fail(this.z, "compression failure", n3);
            }
            if ((n2 = this.z.next_out_index - n6) > 0) {
                byteBuf2.writerIndex(byteBuf2.writerIndex() + n2);
            }
        } finally {
            this.z.next_in = null;
            this.z.next_out = null;
        }
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        ChannelFuture channelFuture = this.finishEncode(channelHandlerContext, channelHandlerContext.newPromise());
        channelFuture.addListener(new ChannelFutureListener(this, channelHandlerContext, channelPromise){
            final ChannelHandlerContext val$ctx;
            final ChannelPromise val$promise;
            final JZlibEncoder this$0;
            {
                this.this$0 = jZlibEncoder;
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
                final JZlibEncoder this$0;
                {
                    this.this$0 = jZlibEncoder;
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
        ByteBuf byteBuf;
        if (this.finished) {
            channelPromise.setSuccess();
            return channelPromise;
        }
        this.finished = true;
        try {
            this.z.next_in = EmptyArrays.EMPTY_BYTES;
            this.z.next_in_index = 0;
            this.z.avail_in = 0;
            byte[] byArray = new byte[32];
            this.z.next_out = byArray;
            this.z.next_out_index = 0;
            this.z.avail_out = byArray.length;
            int n = this.z.deflate(4);
            if (n != 0 && n != 1) {
                channelPromise.setFailure(ZlibUtil.deflaterException(this.z, "compression failure", n));
                ChannelPromise channelPromise2 = channelPromise;
                return channelPromise2;
            }
            byteBuf = this.z.next_out_index != 0 ? Unpooled.wrappedBuffer(byArray, 0, this.z.next_out_index) : Unpooled.EMPTY_BUFFER;
        } finally {
            this.z.deflateEnd();
            this.z.next_in = null;
            this.z.next_out = null;
        }
        return channelHandlerContext.writeAndFlush(byteBuf, channelPromise);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }

    static ChannelHandlerContext access$000(JZlibEncoder jZlibEncoder) {
        return jZlibEncoder.ctx();
    }

    static ChannelFuture access$100(JZlibEncoder jZlibEncoder, ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        return jZlibEncoder.finishEncode(channelHandlerContext, channelPromise);
    }
}

