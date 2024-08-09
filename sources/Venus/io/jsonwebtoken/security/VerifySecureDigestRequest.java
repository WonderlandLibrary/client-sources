/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.VerifyDigestRequest;
import java.io.InputStream;
import java.security.Key;

public interface VerifySecureDigestRequest<K extends Key>
extends SecureRequest<InputStream, K>,
VerifyDigestRequest {
}

