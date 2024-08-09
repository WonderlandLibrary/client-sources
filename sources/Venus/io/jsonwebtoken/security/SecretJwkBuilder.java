/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.JwkBuilder;
import io.jsonwebtoken.security.SecretJwk;
import javax.crypto.SecretKey;

public interface SecretJwkBuilder
extends JwkBuilder<SecretKey, SecretJwk, SecretJwkBuilder> {
}

