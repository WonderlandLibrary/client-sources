/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.flow;

import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;

public class FlowControlHandler
extends ChannelDuplexHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(FlowControlHandler.class);
    private final boolean releaseMessages;
    private RecyclableArrayDeque queue;
    private ChannelConfig config;
    private boolean shouldConsume;

    public FlowControlHandler() {
        this(true);
    }

    public FlowControlHandler(boolean bl) {
        this.releaseMessages = bl;
    }

    boolean isQueueEmpty() {
        return this.queue.isEmpty();
    }

    private void destroy() {
        if (this.queue != null) {
            if (!this.queue.isEmpty()) {
                logger.trace("Non-empty queue: {}", (Object)this.queue);
                if (this.releaseMessages) {
                    Object e;
                    while ((e = this.queue.poll()) != null) {
                        ReferenceCountUtil.safeRelease(e);
                    }
                }
            }
            this.queue.recycle();
            this.queue = null;
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.config = channelHandlerContext.channel().config();
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.destroy();
        channelHandlerContext.fireChannelInactive();
    }

    @Override
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.dequeue(channelHandlerContext, 1) == 0) {
            this.shouldConsume = true;
            channelHandlerContext.read();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (this.queue == null) {
            this.queue = RecyclableArrayDeque.newInstance();
        }
        this.queue.offer(object);
        int n = this.shouldConsume ? 1 : 0;
        this.shouldConsume = false;
        this.dequeue(channelHandlerContext, n);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    private int dequeue(ChannelHandlerContext channelHandlerContext, int n) {
        if (this.queue != null) {
            Object e;
            int n2;
            for (n2 = 0; (n2 < n || this.config.isAutoRead()) && (e = this.queue.poll()) != null; ++n2) {
                channelHandlerContext.fireChannelRead(e);
            }
            if (this.queue.isEmpty() && n2 > 0) {
                channelHandlerContext.fireChannelReadComplete();
            }
            return n2;
        }
        return 1;
    }

    private static final class RecyclableArrayDeque
    extends ArrayDeque<Object> {
        private static final long serialVersionUID = 0L;
        private static final int DEFAULT_NUM_ELEMENTS = 2;
        private static final Recycler<RecyclableArrayDeque> RECYCLER = new Recycler<RecyclableArrayDeque>(){

            @Override
            protected RecyclableArrayDeque newObject(Recycler.Handle<RecyclableArrayDeque> handle) {
                return new RecyclableArrayDeque(2, handle, null);
            }

            @Override
            protected Object newObject(Recycler.Handle handle) {
                return this.newObject(handle);
            }
        };
        private final Recycler.Handle<RecyclableArrayDeque> handle;

        public static RecyclableArrayDeque newInstance() {
            return RECYCLER.get();
        }

        private RecyclableArrayDeque(int n, Recycler.Handle<RecyclableArrayDeque> handle) {
            super(n);
            this.handle = handle;
        }

        public void recycle() {
            this.clear();
            this.handle.recycle(this);
        }

        RecyclableArrayDeque(int n, Recycler.Handle handle, 1 var3_3) {
            this(n, handle);
        }
    }
}

