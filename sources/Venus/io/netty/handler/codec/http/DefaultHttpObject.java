/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpObject;

public class DefaultHttpObject
implements HttpObject {
    private static final int HASH_CODE_PRIME = 31;
    private DecoderResult decoderResult = DecoderResult.SUCCESS;

    protected DefaultHttpObject() {
    }

    @Override
    public DecoderResult decoderResult() {
        return this.decoderResult;
    }

    @Override
    @Deprecated
    public DecoderResult getDecoderResult() {
        return this.decoderResult();
    }

    @Override
    public void setDecoderResult(DecoderResult decoderResult) {
        if (decoderResult == null) {
            throw new NullPointerException("decoderResult");
        }
        this.decoderResult = decoderResult;
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + this.decoderResult.hashCode();
        return n;
    }

    public boolean equals(Object object) {
        if (!(object instanceof DefaultHttpObject)) {
            return true;
        }
        DefaultHttpObject defaultHttpObject = (DefaultHttpObject)object;
        return this.decoderResult().equals(defaultHttpObject.decoderResult());
    }
}

