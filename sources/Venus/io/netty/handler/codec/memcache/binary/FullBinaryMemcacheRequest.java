/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.FullMemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest;

public interface FullBinaryMemcacheRequest
extends BinaryMemcacheRequest,
FullMemcacheMessage {
    @Override
    public FullBinaryMemcacheRequest copy();

    @Override
    public FullBinaryMemcacheRequest duplicate();

    @Override
    public FullBinaryMemcacheRequest retainedDuplicate();

    @Override
    public FullBinaryMemcacheRequest replace(ByteBuf var1);

    @Override
    public FullBinaryMemcacheRequest retain(int var1);

    @Override
    public FullBinaryMemcacheRequest retain();

    @Override
    public FullBinaryMemcacheRequest touch();

    @Override
    public FullBinaryMemcacheRequest touch(Object var1);
}

