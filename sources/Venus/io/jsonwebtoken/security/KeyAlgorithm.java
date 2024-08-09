/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.security.DecryptionKeyRequest;
import io.jsonwebtoken.security.KeyRequest;
import io.jsonwebtoken.security.KeyResult;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import javax.crypto.SecretKey;

public interface KeyAlgorithm<E extends Key, D extends Key>
extends Identifiable {
    public KeyResult getEncryptionKey(KeyRequest<E> var1) throws SecurityException;

    public SecretKey getDecryptionKey(DecryptionKeyRequest<D> var1) throws SecurityException;
}

