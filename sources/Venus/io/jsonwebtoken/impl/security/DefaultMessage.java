/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Message;

class DefaultMessage<T>
implements Message<T> {
    private final T payload;

    DefaultMessage(T t) {
        this.payload = Assert.notNull(t, "payload cannot be null.");
        if (t instanceof byte[]) {
            this.assertBytePayload((byte[])t);
        }
    }

    protected void assertBytePayload(byte[] byArray) {
        Assert.notEmpty(byArray, "payload byte array cannot be null or empty.");
    }

    @Override
    public T getPayload() {
        return this.payload;
    }
}

