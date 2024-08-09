/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.DigestAlgorithm;
import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.VerifySecureDigestRequest;
import java.io.InputStream;
import java.security.Key;

public interface SecureDigestAlgorithm<S extends Key, V extends Key>
extends DigestAlgorithm<SecureRequest<InputStream, S>, VerifySecureDigestRequest<V>> {
}

