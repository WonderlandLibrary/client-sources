/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.DigestAlgorithm;
import io.jsonwebtoken.security.Request;
import io.jsonwebtoken.security.VerifyDigestRequest;
import java.io.InputStream;

public interface HashAlgorithm
extends DigestAlgorithm<Request<InputStream>, VerifyDigestRequest> {
}

