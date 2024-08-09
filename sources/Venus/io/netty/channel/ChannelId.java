/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import java.io.Serializable;

public interface ChannelId
extends Serializable,
Comparable<ChannelId> {
    public String asShortText();

    public String asLongText();
}

