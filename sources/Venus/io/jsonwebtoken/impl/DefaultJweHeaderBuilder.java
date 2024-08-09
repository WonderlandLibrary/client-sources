/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.JweHeaderMutator;
import io.jsonwebtoken.impl.DefaultJweHeaderMutator;
import io.jsonwebtoken.security.X509Builder;

public class DefaultJweHeaderBuilder<T extends JweHeaderMutator<T> & X509Builder<T>>
extends DefaultJweHeaderMutator<T>
implements X509Builder<T> {
    protected DefaultJweHeaderBuilder() {
    }

    protected DefaultJweHeaderBuilder(DefaultJweHeaderMutator<?> defaultJweHeaderMutator) {
        super(defaultJweHeaderMutator);
    }

    @Override
    public T x509Sha1Thumbprint(boolean bl) {
        this.x509.x509Sha1Thumbprint(bl);
        return (T)((JweHeaderMutator)this.self());
    }

    @Override
    public T x509Sha256Thumbprint(boolean bl) {
        this.x509.x509Sha256Thumbprint(bl);
        return (T)((JweHeaderMutator)this.self());
    }

    @Override
    public X509Builder x509Sha256Thumbprint(boolean bl) {
        return (X509Builder)this.x509Sha256Thumbprint(bl);
    }

    @Override
    public X509Builder x509Sha1Thumbprint(boolean bl) {
        return (X509Builder)this.x509Sha1Thumbprint(bl);
    }
}

