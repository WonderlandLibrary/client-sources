/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.memcache.MemcacheObject;
import io.netty.util.AbstractReferenceCounted;

public abstract class AbstractMemcacheObject
extends AbstractReferenceCounted
implements MemcacheObject {
    private DecoderResult decoderResult = DecoderResult.SUCCESS;

    protected AbstractMemcacheObject() {
    }

    @Override
    public DecoderResult decoderResult() {
        return this.decoderResult;
    }

    @Override
    public void setDecoderResult(DecoderResult decoderResult) {
        if (decoderResult == null) {
            throw new NullPointerException("DecoderResult should not be null.");
        }
        this.decoderResult = decoderResult;
    }
}

