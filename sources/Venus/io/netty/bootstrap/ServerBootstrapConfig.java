/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.bootstrap;

import io.netty.bootstrap.AbstractBootstrapConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.StringUtil;
import java.util.Map;

public final class ServerBootstrapConfig
extends AbstractBootstrapConfig<ServerBootstrap, ServerChannel> {
    ServerBootstrapConfig(ServerBootstrap serverBootstrap) {
        super(serverBootstrap);
    }

    public EventLoopGroup childGroup() {
        return ((ServerBootstrap)this.bootstrap).childGroup();
    }

    public ChannelHandler childHandler() {
        return ((ServerBootstrap)this.bootstrap).childHandler();
    }

    public Map<ChannelOption<?>, Object> childOptions() {
        return ((ServerBootstrap)this.bootstrap).childOptions();
    }

    public Map<AttributeKey<?>, Object> childAttrs() {
        return ((ServerBootstrap)this.bootstrap).childAttrs();
    }

    @Override
    public String toString() {
        ChannelHandler channelHandler;
        Map<AttributeKey<?>, Object> map;
        Map<ChannelOption<?>, Object> map2;
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(", ");
        EventLoopGroup eventLoopGroup = this.childGroup();
        if (eventLoopGroup != null) {
            stringBuilder.append("childGroup: ");
            stringBuilder.append(StringUtil.simpleClassName(eventLoopGroup));
            stringBuilder.append(", ");
        }
        if (!(map2 = this.childOptions()).isEmpty()) {
            stringBuilder.append("childOptions: ");
            stringBuilder.append(map2);
            stringBuilder.append(", ");
        }
        if (!(map = this.childAttrs()).isEmpty()) {
            stringBuilder.append("childAttrs: ");
            stringBuilder.append(map);
            stringBuilder.append(", ");
        }
        if ((channelHandler = this.childHandler()) != null) {
            stringBuilder.append("childHandler: ");
            stringBuilder.append(channelHandler);
            stringBuilder.append(", ");
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

