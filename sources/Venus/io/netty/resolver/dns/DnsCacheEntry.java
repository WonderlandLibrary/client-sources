/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import java.net.InetAddress;

public interface DnsCacheEntry {
    public InetAddress address();

    public Throwable cause();
}

