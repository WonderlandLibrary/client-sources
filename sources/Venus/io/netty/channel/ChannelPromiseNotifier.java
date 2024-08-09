/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.PromiseNotifier;

public final class ChannelPromiseNotifier
extends PromiseNotifier<Void, ChannelFuture>
implements ChannelFutureListener {
    public ChannelPromiseNotifier(ChannelPromise ... channelPromiseArray) {
        super(channelPromiseArray);
    }

    public ChannelPromiseNotifier(boolean bl, ChannelPromise ... channelPromiseArray) {
        super(bl, channelPromiseArray);
    }
}

