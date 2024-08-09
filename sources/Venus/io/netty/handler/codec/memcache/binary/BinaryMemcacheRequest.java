/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;

public interface BinaryMemcacheRequest
extends BinaryMemcacheMessage {
    public short reserved();

    public BinaryMemcacheRequest setReserved(short var1);

    @Override
    public BinaryMemcacheRequest retain();

    @Override
    public BinaryMemcacheRequest retain(int var1);

    @Override
    public BinaryMemcacheRequest touch();

    @Override
    public BinaryMemcacheRequest touch(Object var1);
}

