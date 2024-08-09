/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Request;

public interface KeyRequest<T>
extends Request<T> {
    public AeadAlgorithm getEncryptionAlgorithm();

    public JweHeader getHeader();
}

