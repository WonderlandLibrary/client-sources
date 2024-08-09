/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.lang.NestedCollection;
import java.util.Date;

public interface ClaimsMutator<T extends ClaimsMutator<T>> {
    @Deprecated
    public T setIssuer(String var1);

    public T issuer(String var1);

    @Deprecated
    public T setSubject(String var1);

    public T subject(String var1);

    @Deprecated
    public T setAudience(String var1);

    public AudienceCollection<T> audience();

    @Deprecated
    public T setExpiration(Date var1);

    public T expiration(Date var1);

    @Deprecated
    public T setNotBefore(Date var1);

    public T notBefore(Date var1);

    @Deprecated
    public T setIssuedAt(Date var1);

    public T issuedAt(Date var1);

    @Deprecated
    public T setId(String var1);

    public T id(String var1);

    public static interface AudienceCollection<P>
    extends NestedCollection<String, P> {
        @Deprecated
        public P single(String var1);
    }
}

