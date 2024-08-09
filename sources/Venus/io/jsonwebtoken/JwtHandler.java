/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtVisitor;

@Deprecated
public interface JwtHandler<T>
extends JwtVisitor<T> {
    public T onContentJwt(Jwt<Header, byte[]> var1);

    public T onClaimsJwt(Jwt<Header, Claims> var1);

    public T onContentJws(Jws<byte[]> var1);

    public T onClaimsJws(Jws<Claims> var1);

    public T onContentJwe(Jwe<byte[]> var1);

    public T onClaimsJwe(Jwe<Claims> var1);
}

