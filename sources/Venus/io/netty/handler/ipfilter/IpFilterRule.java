/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ipfilter;

import io.netty.handler.ipfilter.IpFilterRuleType;
import java.net.InetSocketAddress;

public interface IpFilterRule {
    public boolean matches(InetSocketAddress var1);

    public IpFilterRuleType ruleType();
}

