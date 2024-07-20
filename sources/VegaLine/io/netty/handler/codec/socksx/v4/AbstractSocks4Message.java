/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v4;

import io.netty.handler.codec.socksx.AbstractSocksMessage;
import io.netty.handler.codec.socksx.SocksVersion;
import io.netty.handler.codec.socksx.v4.Socks4Message;

public abstract class AbstractSocks4Message
extends AbstractSocksMessage
implements Socks4Message {
    @Override
    public final SocksVersion version() {
        return SocksVersion.SOCKS4a;
    }
}

