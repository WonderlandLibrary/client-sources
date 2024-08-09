/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.OctetPrivateJwk;
import io.jsonwebtoken.security.OctetPublicJwk;
import io.jsonwebtoken.security.PrivateJwkBuilder;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface OctetPrivateJwkBuilder<K extends PrivateKey, L extends PublicKey>
extends PrivateJwkBuilder<K, L, OctetPublicJwk<L>, OctetPrivateJwk<K, L>, OctetPrivateJwkBuilder<K, L>> {
}

