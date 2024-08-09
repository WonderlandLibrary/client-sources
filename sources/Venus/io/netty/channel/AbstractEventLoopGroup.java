/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.AbstractEventExecutorGroup;
import io.netty.util.concurrent.EventExecutor;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractEventLoopGroup
extends AbstractEventExecutorGroup
implements EventLoopGroup {
    @Override
    public abstract EventLoop next();

    @Override
    public EventExecutor next() {
        return this.next();
    }
}

