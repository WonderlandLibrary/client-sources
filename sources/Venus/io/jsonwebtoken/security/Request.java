/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.Message;
import java.security.Provider;
import java.security.SecureRandom;

public interface Request<T>
extends Message<T> {
    public Provider getProvider();

    public SecureRandom getSecureRandom();
}

