/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.ProtectedHeader;
import io.jsonwebtoken.security.DigestSupplier;

public interface ProtectedJwt<H extends ProtectedHeader, P>
extends Jwt<H, P>,
DigestSupplier {
}

