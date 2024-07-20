/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v4;

import io.netty.handler.codec.socksx.v4.Socks4CommandType;
import io.netty.handler.codec.socksx.v4.Socks4Message;

public interface Socks4CommandRequest
extends Socks4Message {
    public Socks4CommandType type();

    public String userId();

    public String dstAddr();

    public int dstPort();
}

