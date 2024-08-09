/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.X509Context;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.security.AbstractAsymmetricJwk;
import io.jsonwebtoken.security.X509Mutator;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Set;

public class AbstractX509Context<T extends X509Mutator<T>>
extends ParameterMap
implements X509Context<T> {
    public AbstractX509Context(Set<Parameter<?>> set) {
        super(set);
    }

    protected T self() {
        return (T)this;
    }

    @Override
    public URI getX509Url() {
        return this.get(AbstractAsymmetricJwk.X5U);
    }

    @Override
    public T x509Url(URI uRI) {
        this.put(AbstractAsymmetricJwk.X5U, (Object)uRI);
        return this.self();
    }

    @Override
    public List<X509Certificate> getX509Chain() {
        return this.get(AbstractAsymmetricJwk.X5C);
    }

    @Override
    public T x509Chain(List<X509Certificate> list) {
        this.put(AbstractAsymmetricJwk.X5C, (Object)list);
        return this.self();
    }

    @Override
    public byte[] getX509Sha1Thumbprint() {
        return this.get(AbstractAsymmetricJwk.X5T);
    }

    @Override
    public T x509Sha1Thumbprint(byte[] byArray) {
        this.put(AbstractAsymmetricJwk.X5T, (Object)byArray);
        return this.self();
    }

    @Override
    public byte[] getX509Sha256Thumbprint() {
        return this.get(AbstractAsymmetricJwk.X5T_S256);
    }

    @Override
    public T x509Sha256Thumbprint(byte[] byArray) {
        this.put(AbstractAsymmetricJwk.X5T_S256, (Object)byArray);
        return this.self();
    }
}

