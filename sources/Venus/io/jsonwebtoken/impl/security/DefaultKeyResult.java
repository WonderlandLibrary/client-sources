/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.security.DefaultMessage;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.KeyResult;
import java.security.Key;
import javax.crypto.SecretKey;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultKeyResult
extends DefaultMessage<byte[]>
implements KeyResult {
    private final SecretKey key;

    public DefaultKeyResult(SecretKey secretKey) {
        this(secretKey, Bytes.EMPTY);
    }

    public DefaultKeyResult(SecretKey secretKey, byte[] byArray) {
        super(byArray);
        this.key = Assert.notNull(secretKey, "Content Encryption Key cannot be null.");
    }

    @Override
    protected void assertBytePayload(byte[] byArray) {
        Assert.notNull(byArray, "encrypted key bytes cannot be null (but may be empty.");
    }

    @Override
    public SecretKey getKey() {
        return this.key;
    }

    @Override
    public Key getKey() {
        return this.getKey();
    }
}

