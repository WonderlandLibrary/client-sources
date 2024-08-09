/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ipfilter;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ipfilter.AbstractRemoteAddressFilter;
import io.netty.handler.ipfilter.IpFilterRule;
import io.netty.handler.ipfilter.IpFilterRuleType;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

@ChannelHandler.Sharable
public class RuleBasedIpFilter
extends AbstractRemoteAddressFilter<InetSocketAddress> {
    private final IpFilterRule[] rules;

    public RuleBasedIpFilter(IpFilterRule ... ipFilterRuleArray) {
        if (ipFilterRuleArray == null) {
            throw new NullPointerException("rules");
        }
        this.rules = ipFilterRuleArray;
    }

    @Override
    protected boolean accept(ChannelHandlerContext channelHandlerContext, InetSocketAddress inetSocketAddress) throws Exception {
        for (IpFilterRule ipFilterRule : this.rules) {
            if (ipFilterRule == null) break;
            if (!ipFilterRule.matches(inetSocketAddress)) continue;
            return ipFilterRule.ruleType() == IpFilterRuleType.ACCEPT;
        }
        return false;
    }

    @Override
    protected boolean accept(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress) throws Exception {
        return this.accept(channelHandlerContext, (InetSocketAddress)socketAddress);
    }
}

