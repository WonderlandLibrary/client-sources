/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.socksx.v5.Socks5AddressType;
import io.netty.handler.codec.socksx.v5.Socks5CommandStatus;
import io.netty.handler.codec.socksx.v5.Socks5Message;

public interface Socks5CommandResponse
extends Socks5Message {
    public Socks5CommandStatus status();

    public Socks5AddressType bndAddrType();

    public String bndAddr();

    public int bndPort();
}

