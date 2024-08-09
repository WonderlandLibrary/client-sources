/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.OctetPublicJwk;
import io.jsonwebtoken.security.PrivateJwk;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface OctetPrivateJwk<K extends PrivateKey, L extends PublicKey>
extends PrivateJwk<K, L, OctetPublicJwk<L>> {
}

