/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.PrivateJwk;
import io.jsonwebtoken.security.RsaPublicJwk;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface RsaPrivateJwk
extends PrivateJwk<RSAPrivateKey, RSAPublicKey, RsaPublicJwk> {
}

