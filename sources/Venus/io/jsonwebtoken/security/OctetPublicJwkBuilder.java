/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.OctetPrivateJwk;
import io.jsonwebtoken.security.OctetPrivateJwkBuilder;
import io.jsonwebtoken.security.OctetPublicJwk;
import io.jsonwebtoken.security.PublicJwkBuilder;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface OctetPublicJwkBuilder<A extends PublicKey, B extends PrivateKey>
extends PublicJwkBuilder<A, B, OctetPublicJwk<A>, OctetPrivateJwk<B, A>, OctetPrivateJwkBuilder<B, A>, OctetPublicJwkBuilder<A, B>> {
}

