/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.KeyBuilderSupplier;
import io.jsonwebtoken.security.KeyLengthSupplier;
import io.jsonwebtoken.security.SecretKeyBuilder;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import javax.crypto.SecretKey;

public interface MacAlgorithm
extends SecureDigestAlgorithm<SecretKey, SecretKey>,
KeyBuilderSupplier<SecretKey, SecretKeyBuilder>,
KeyLengthSupplier {
}

