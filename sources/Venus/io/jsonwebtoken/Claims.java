/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Identifiable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface Claims
extends Map<String, Object>,
Identifiable {
    public static final String ISSUER = "iss";
    public static final String SUBJECT = "sub";
    public static final String AUDIENCE = "aud";
    public static final String EXPIRATION = "exp";
    public static final String NOT_BEFORE = "nbf";
    public static final String ISSUED_AT = "iat";
    public static final String ID = "jti";

    public String getIssuer();

    public String getSubject();

    public Set<String> getAudience();

    public Date getExpiration();

    public Date getNotBefore();

    public Date getIssuedAt();

    @Override
    public String getId();

    public <T> T get(String var1, Class<T> var2);
}

