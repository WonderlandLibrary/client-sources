/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.DefaultAeadRequest;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.DecryptAeadRequest;
import java.io.InputStream;
import javax.crypto.SecretKey;

public class DefaultDecryptAeadRequest
extends DefaultAeadRequest
implements DecryptAeadRequest {
    private final byte[] TAG;

    public DefaultDecryptAeadRequest(InputStream inputStream, SecretKey secretKey, InputStream inputStream2, byte[] byArray, byte[] byArray2) {
        super(inputStream, null, null, secretKey, inputStream2, Assert.notEmpty(byArray, "Initialization Vector cannot be null or empty."));
        this.TAG = Assert.notEmpty(byArray2, "AAD Authentication Tag cannot be null or empty.");
    }

    @Override
    public byte[] getDigest() {
        return this.TAG;
    }
}

