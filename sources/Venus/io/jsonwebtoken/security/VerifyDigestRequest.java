/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.DigestSupplier;
import io.jsonwebtoken.security.Request;
import java.io.InputStream;

public interface VerifyDigestRequest
extends Request<InputStream>,
DigestSupplier {
}

