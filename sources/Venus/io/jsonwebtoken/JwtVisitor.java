/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;

public interface JwtVisitor<T> {
    public T visit(Jwt<?, ?> var1);

    public T visit(Jws<?> var1);

    public T visit(Jwe<?> var1);
}

