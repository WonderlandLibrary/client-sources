/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.security.AeadRequest;
import io.jsonwebtoken.security.AeadResult;
import io.jsonwebtoken.security.DecryptAeadRequest;
import io.jsonwebtoken.security.KeyBuilderSupplier;
import io.jsonwebtoken.security.KeyLengthSupplier;
import io.jsonwebtoken.security.SecretKeyBuilder;
import io.jsonwebtoken.security.SecurityException;
import java.io.OutputStream;
import javax.crypto.SecretKey;

public interface AeadAlgorithm
extends Identifiable,
KeyLengthSupplier,
KeyBuilderSupplier<SecretKey, SecretKeyBuilder> {
    public void encrypt(AeadRequest var1, AeadResult var2) throws SecurityException;

    public void decrypt(DecryptAeadRequest var1, OutputStream var2) throws SecurityException;
}

