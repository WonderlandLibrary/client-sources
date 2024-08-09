/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.KeySupplier;
import java.security.Key;
import java.security.PrivateKey;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PrivateECKey
implements PrivateKey,
ECKey,
KeySupplier<PrivateKey> {
    private final PrivateKey privateKey;
    private final ECParameterSpec params;

    public PrivateECKey(PrivateKey privateKey, ECParameterSpec eCParameterSpec) {
        this.privateKey = Assert.notNull(privateKey, "PrivateKey cannot be null.");
        this.params = Assert.notNull(eCParameterSpec, "ECParameterSpec cannot be null.");
    }

    @Override
    public String getAlgorithm() {
        return this.privateKey.getAlgorithm();
    }

    @Override
    public String getFormat() {
        return this.privateKey.getFormat();
    }

    @Override
    public byte[] getEncoded() {
        return this.privateKey.getEncoded();
    }

    @Override
    public ECParameterSpec getParams() {
        return this.params;
    }

    @Override
    public PrivateKey getKey() {
        return this.privateKey;
    }

    @Override
    public Key getKey() {
        return this.getKey();
    }
}

