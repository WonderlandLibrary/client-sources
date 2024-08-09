/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.handler.codec.dns.DnsOptPseudoRecord;

public interface DnsOptEcsRecord
extends DnsOptPseudoRecord {
    public int sourcePrefixLength();

    public int scopePrefixLength();

    public byte[] address();
}

