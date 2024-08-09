/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.security.Request;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.VerifyDigestRequest;
import java.io.InputStream;

public interface DigestAlgorithm<R extends Request<InputStream>, V extends VerifyDigestRequest>
extends Identifiable {
    public byte[] digest(R var1) throws SecurityException;

    public boolean verify(V var1) throws SecurityException;
}

