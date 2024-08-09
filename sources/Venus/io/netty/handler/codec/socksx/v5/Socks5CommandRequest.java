/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.socksx.v5.Socks5AddressType;
import io.netty.handler.codec.socksx.v5.Socks5CommandType;
import io.netty.handler.codec.socksx.v5.Socks5Message;

public interface Socks5CommandRequest
extends Socks5Message {
    public Socks5CommandType type();

    public Socks5AddressType dstAddrType();

    public String dstAddr();

    public int dstPort();
}

