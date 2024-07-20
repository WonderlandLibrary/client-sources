/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.group;

import io.netty.channel.Channel;

public interface ChannelMatcher {
    public boolean matches(Channel var1);
}

