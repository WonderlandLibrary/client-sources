/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.EcPrivateJwk;
import io.jsonwebtoken.security.EcPrivateJwkBuilder;
import io.jsonwebtoken.security.EcPublicJwk;
import io.jsonwebtoken.security.PublicJwkBuilder;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public interface EcPublicJwkBuilder
extends PublicJwkBuilder<ECPublicKey, ECPrivateKey, EcPublicJwk, EcPrivateJwk, EcPrivateJwkBuilder, EcPublicJwkBuilder> {
}

