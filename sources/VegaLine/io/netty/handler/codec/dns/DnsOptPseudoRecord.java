/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.handler.codec.dns.DnsRecord;

public interface DnsOptPseudoRecord
extends DnsRecord {
    public int extendedRcode();

    public int version();

    public int flags();
}

