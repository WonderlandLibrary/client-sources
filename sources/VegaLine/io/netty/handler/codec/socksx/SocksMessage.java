/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx;

import io.netty.handler.codec.DecoderResultProvider;
import io.netty.handler.codec.socksx.SocksVersion;

public interface SocksMessage
extends DecoderResultProvider {
    public SocksVersion version();
}

