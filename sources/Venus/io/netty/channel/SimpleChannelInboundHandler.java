/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.TypeParameterMatcher;

public abstract class SimpleChannelInboundHandler<I>
extends ChannelInboundHandlerAdapter {
    private final TypeParameterMatcher matcher;
    private final boolean autoRelease;

    protected SimpleChannelInboundHandler() {
        this(true);
    }

    protected SimpleChannelInboundHandler(boolean bl) {
        this.matcher = TypeParameterMatcher.find(this, SimpleChannelInboundHandler.class, "I");
        this.autoRelease = bl;
    }

    protected SimpleChannelInboundHandler(Class<? extends I> clazz) {
        this(clazz, true);
    }

    protected SimpleChannelInboundHandler(Class<? extends I> clazz, boolean bl) {
        this.matcher = TypeParameterMatcher.get(clazz);
        this.autoRelease = bl;
    }

    public boolean acceptInboundMessage(Object object) throws Exception {
        return this.matcher.match(object);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        boolean bl = true;
        try {
            if (this.acceptInboundMessage(object)) {
                Object object2 = object;
                this.channelRead0(channelHandlerContext, object2);
            } else {
                bl = false;
                channelHandlerContext.fireChannelRead(object);
            }
        } finally {
            if (this.autoRelease && bl) {
                ReferenceCountUtil.release(object);
            }
        }
    }

    protected abstract void channelRead0(ChannelHandlerContext var1, I var2) throws Exception;
}

