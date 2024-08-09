/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.security.PublicJwk;
import io.jsonwebtoken.security.X509Accessor;
import java.net.URI;
import java.util.Set;

public interface ProtectedHeader
extends Header,
X509Accessor {
    public URI getJwkSetUrl();

    public PublicJwk<?> getJwk();

    public String getKeyId();

    public Set<String> getCritical();
}

