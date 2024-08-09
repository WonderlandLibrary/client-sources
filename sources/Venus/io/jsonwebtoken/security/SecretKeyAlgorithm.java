/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.KeyBuilderSupplier;
import io.jsonwebtoken.security.KeyLengthSupplier;
import io.jsonwebtoken.security.SecretKeyBuilder;
import javax.crypto.SecretKey;

public interface SecretKeyAlgorithm
extends KeyAlgorithm<SecretKey, SecretKey>,
KeyBuilderSupplier<SecretKey, SecretKeyBuilder>,
KeyLengthSupplier {
}

