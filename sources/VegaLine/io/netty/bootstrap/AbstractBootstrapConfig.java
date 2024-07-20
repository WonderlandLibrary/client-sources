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

    protected AbstractBootstrapConfig(B bootstrap) {
        this.bootstrap = (AbstractBootstrap)ObjectUtil.checkNotNull(bootstrap, "bootstrap");
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
        ChannelHandler handler;
        Map<AttributeKey<?>, Object> attrs;
        Map<ChannelOption<?>, Object> options;
        SocketAddress localAddress;
        ChannelFactory<C> factory;
        StringBuilder buf = new StringBuilder().append(StringUtil.simpleClassName(this)).append('(');
        EventLoopGroup group = this.group();
        if (group != null) {
            buf.append("group: ").append(StringUtil.simpleClassName(group)).append(", ");
        }
        if ((factory = this.channelFactory()) != null) {
            buf.append("channelFactory: ").append(factory).append(", ");
        }
        if ((localAddress = this.localAddress()) != null) {
            buf.append("localAddress: ").append(localAddress).append(", ");
        }
        if (!(options = this.options()).isEmpty()) {
            buf.append("options: ").append(options).append(", ");
        }
        if (!(attrs = this.attrs()).isEmpty()) {
            buf.append("attrs: ").append(attrs).append(", ");
        }
        if ((handler = this.handler()) != null) {
            buf.append("handler: ").append(handler).append(", ");
        }
        if (buf.charAt(buf.length() - 1) == '(') {
            buf.append(')');
        } else {
            buf.setCharAt(buf.length() - 2, ')');
            buf.setLength(buf.length() - 1);
        }
        return buf.toString();
    }
}

