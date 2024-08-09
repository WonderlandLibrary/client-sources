/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.bootstrap;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.util.AttributeKey;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;
import java.util.Map;

public abstract class AbstractBootstrapConfig<B extends AbstractBootstrap<B, C>, C extends Channel> {
    protected final B bootstrap;

    protected AbstractBootstrapConfig(B b) {
        this.bootstrap = (AbstractBootstrap)ObjectUtil.checkNotNull(b, "bootstrap");
    }

    public final SocketAddress localAddress() {
        return ((AbstractBootstrap)this.bootstrap).localAddress();
    }

    public final ChannelFactory<? extends C> channelFactory() {
        return ((AbstractBootstrap)this.bootstrap).channelFactory();
    }

    public final ChannelHandler handler() {
        return ((AbstractBootstrap)this.bootstrap).handler();
    }

    public final Map<ChannelOption<?>, Object> options() {
        return ((AbstractBootstrap)this.bootstrap).options();
    }

    public final Map<AttributeKey<?>, Object> attrs() {
        return ((AbstractBootstrap)this.bootstrap).attrs();
    }

    public final EventLoopGroup group() {
        return ((AbstractBootstrap)this.bootstrap).group();
    }

    public String toString() {
        ChannelHandler channelHandler;
        Map<AttributeKey<?>, Object> map;
        Map<ChannelOption<?>, Object> map2;
        SocketAddress socketAddress;
        ChannelFactory<C> channelFactory;
        StringBuilder stringBuilder = new StringBuilder().append(StringUtil.simpleClassName(this)).append('(');
        EventLoopGroup eventLoopGroup = this.group();
        if (eventLoopGroup != null) {
            stringBuilder.append("group: ").append(StringUtil.simpleClassName(eventLoopGroup)).append(", ");
        }
        if ((channelFactory = this.channelFactory()) != null) {
            stringBuilder.append("channelFactory: ").append(channelFactory).append(", ");
        }
        if ((socketAddress = this.localAddress()) != null) {
            stringBuilder.append("localAddress: ").append(socketAddress).append(", ");
        }
        if (!(map2 = this.options()).isEmpty()) {
            stringBuilder.append("options: ").append(map2).append(", ");
        }
        if (!(map = this.attrs()).isEmpty()) {
            stringBuilder.append("attrs: ").append(map).append(", ");
        }
        if ((channelHandler = this.handler()) != null) {
            stringBuilder.append("handler: ").append(channelHandler).append(", ");
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == '(') {
            stringBuilder.append(')');
        } else {
            stringBuilder.setCharAt(stringBuilder.length() - 2, ')');
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}

