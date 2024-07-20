/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.handler.codec.dns.DnsRecord;

public interface DnsQuestion
extends DnsRecord {
    @Override
    public long timeToLive();
}

