/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.KeyPairBuilderSupplier;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface SignatureAlgorithm
extends SecureDigestAlgorithm<PrivateKey, PublicKey>,
KeyPairBuilderSupplier {
}

