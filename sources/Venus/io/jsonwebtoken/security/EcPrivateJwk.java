/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.EcPublicJwk;
import io.jsonwebtoken.security.PrivateJwk;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public interface EcPrivateJwk
extends PrivateJwk<ECPrivateKey, ECPublicKey, EcPublicJwk> {
}

