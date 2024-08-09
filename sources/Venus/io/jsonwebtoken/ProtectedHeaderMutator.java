/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.HeaderMutator;
import io.jsonwebtoken.lang.NestedCollection;
import io.jsonwebtoken.security.PublicJwk;
import io.jsonwebtoken.security.X509Mutator;
import java.net.URI;

public interface ProtectedHeaderMutator<T extends ProtectedHeaderMutator<T>>
extends HeaderMutator<T>,
X509Mutator<T> {
    public NestedCollection<String, T> critical();

    public T jwk(PublicJwk<?> var1);

    public T jwkSetUrl(URI var1);

    public T keyId(String var1);

    @Deprecated
    public T setKeyId(String var1);

    @Deprecated
    public T setAlgorithm(String var1);
}

