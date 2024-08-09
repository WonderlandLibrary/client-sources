/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.oio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.oio.AbstractOioChannel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOioMessageChannel
extends AbstractOioChannel {
    private final List<Object> readBuf = new ArrayList<Object>();

    protected AbstractOioMessageChannel(Channel channel) {
        super(channel);
    }

    @Override
    protected void doRead() {
        int n;
        if (!this.readPending) {
            return;
        }
        this.readPending = false;
        ChannelConfig channelConfig = this.config();
        ChannelPipeline channelPipeline = this.pipeline();
        RecvByteBufAllocator.Handle handle = this.unsafe().recvBufAllocHandle();
        handle.reset(channelConfig);
        boolean bl = false;
        Throwable throwable = null;
        try {
            while ((n = this.doReadMessages(this.readBuf)) != 0) {
                if (n < 0) {
                    bl = true;
                } else {
                    handle.incMessagesRead(n);
                    if (handle.continueReading()) continue;
                }
                break;
            }
        } catch (Throwable throwable2) {
            throwable = throwable2;
        }
        n = 0;
        int n2 = this.readBuf.size();
        if (n2 > 0) {
            n = 1;
            for (int i = 0; i < n2; ++i) {
                this.readPending = false;
                channelPipeline.fireChannelRead(this.readBuf.get(i));
            }
            this.readBuf.clear();
            handle.readComplete();
            channelPipeline.fireChannelReadComplete();
        }
        if (throwable != null) {
            if (throwable instanceof IOException) {
                bl = true;
            }
            channelPipeline.fireExceptionCaught(throwable);
        }
        if (bl) {
            if (this.isOpen()) {
                this.unsafe().close(this.unsafe().voidPromise());
            }
        } else if (this.readPending || channelConfig.isAutoRead() || n == 0 && this.isActive()) {
            this.read();
        }
    }

    protected abstract int doReadMessages(List<Object> var1) throws Exception;
}

