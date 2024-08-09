/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.AbstractJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.AsymmetricJwk;
import java.net.URI;
import java.security.Key;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Set;

public abstract class AbstractAsymmetricJwk<K extends Key>
extends AbstractJwk<K>
implements AsymmetricJwk<K> {
    static final Parameter<String> USE = Parameters.string("use", "Public Key Use");
    public static final Parameter<List<X509Certificate>> X5C = Parameters.x509Chain("x5c", "X.509 Certificate Chain");
    public static final Parameter<byte[]> X5T = (Parameter)Parameters.bytes("x5t", "X.509 Certificate SHA-1 Thumbprint").build();
    public static final Parameter<byte[]> X5T_S256 = (Parameter)Parameters.bytes("x5t#S256", "X.509 Certificate SHA-256 Thumbprint").build();
    public static final Parameter<URI> X5U = Parameters.uri("x5u", "X.509 URL");
    static final Set<Parameter<?>> PARAMS = Collections.concat(AbstractJwk.PARAMS, USE, X5C, X5T, X5T_S256, X5U);

    AbstractAsymmetricJwk(JwkContext<K> jwkContext, List<Parameter<?>> list) {
        super(jwkContext, list);
    }

    @Override
    public String getPublicKeyUse() {
        return this.context.getPublicKeyUse();
    }

    @Override
    public URI getX509Url() {
        return this.context.getX509Url();
    }

    @Override
    public List<X509Certificate> getX509Chain() {
        return Collections.immutable(this.context.getX509Chain());
    }

    @Override
    public byte[] getX509Sha1Thumbprint() {
        return (byte[])Arrays.copy(this.context.getX509Sha1Thumbprint());
    }

    @Override
    public byte[] getX509Sha256Thumbprint() {
        return (byte[])Arrays.copy(this.context.getX509Sha256Thumbprint());
    }
}

