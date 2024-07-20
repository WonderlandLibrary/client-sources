/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.socksx.AbstractSocksMessage;
import io.netty.handler.codec.socksx.SocksVersion;
import io.netty.handler.codec.socksx.v5.Socks5Message;

public abstract class AbstractSocks5Message
extends AbstractSocksMessage
implements Socks5Message {
    @Override
    public final SocksVersion version() {
        return SocksVersion.SOCKS5;
    }
}

