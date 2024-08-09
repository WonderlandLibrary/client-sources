/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.timeout;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutException;
import java.util.concurrent.TimeUnit;

public class ReadTimeoutHandler
extends IdleStateHandler {
    private boolean closed;
    static final boolean $assertionsDisabled = !ReadTimeoutHandler.class.desiredAssertionStatus();

    public ReadTimeoutHandler(int n) {
        this(n, TimeUnit.SECONDS);
    }

    public ReadTimeoutHandler(long l, TimeUnit timeUnit) {
        super(l, 0L, 0L, timeUnit);
    }

    @Override
    protected final void channelIdle(ChannelHandlerContext channelHandlerContext, IdleStateEvent idleStateEvent) throws Exception {
        if (!$assertionsDisabled && idleStateEvent.state() != IdleState.READER_IDLE) {
            throw new AssertionError();
        }
        this.readTimedOut(channelHandlerContext);
    }

    protected void readTimedOut(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.closed) {
            channelHandlerContext.fireExceptionCaught(ReadTimeoutException.INSTANCE);
            channelHandlerContext.close();
            this.closed = true;
        }
    }
}

