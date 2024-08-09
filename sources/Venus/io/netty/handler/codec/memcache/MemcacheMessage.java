/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache;

import io.netty.handler.codec.memcache.MemcacheObject;
import io.netty.util.ReferenceCounted;

public interface MemcacheMessage
extends MemcacheObject,
ReferenceCounted {
    @Override
    public MemcacheMessage retain();

    @Override
    public MemcacheMessage retain(int var1);

    @Override
    public MemcacheMessage touch();

    @Override
    public MemcacheMessage touch(Object var1);
}

