/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.lang.Functions;
import io.jsonwebtoken.impl.security.AbstractAsymmetricJwk;
import io.jsonwebtoken.impl.security.DefaultHashAlgorithm;
import io.jsonwebtoken.impl.security.DefaultRequest;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.security.HashAlgorithm;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.X509Builder;
import io.jsonwebtoken.security.X509Mutator;
import java.io.InputStream;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class X509BuilderSupport
implements X509Builder<X509BuilderSupport> {
    private final ParameterMap map;
    protected boolean computeX509Sha1Thumbprint;
    protected Boolean computeX509Sha256Thumbprint = null;
    private final Function<X509Certificate, byte[]> GET_X509_BYTES;

    private static Function<X509Certificate, byte[]> createGetBytesFunction(Class<? extends RuntimeException> clazz) {
        return Functions.wrapFmt(new CheckedFunction<X509Certificate, byte[]>(){

            @Override
            public byte[] apply(X509Certificate x509Certificate) throws Exception {
                return x509Certificate.getEncoded();
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((X509Certificate)object);
            }
        }, clazz, "Unable to access X509Certificate encoded bytes necessary to compute thumbprint. Certificate: %s");
    }

    public X509BuilderSupport(ParameterMap parameterMap, Class<? extends RuntimeException> clazz) {
        this.map = Assert.notNull(parameterMap, "ParameterMap cannot be null.");
        this.GET_X509_BYTES = X509BuilderSupport.createGetBytesFunction(clazz);
    }

    @Override
    public X509BuilderSupport x509Url(URI uRI) {
        this.map.put(AbstractAsymmetricJwk.X5U.getId(), (Object)uRI);
        return this;
    }

    @Override
    public X509BuilderSupport x509Chain(List<X509Certificate> list) {
        this.map.put(AbstractAsymmetricJwk.X5C.getId(), (Object)list);
        return this;
    }

    @Override
    public X509BuilderSupport x509Sha1Thumbprint(byte[] byArray) {
        this.map.put(AbstractAsymmetricJwk.X5T.getId(), (Object)byArray);
        return this;
    }

    @Override
    public X509BuilderSupport x509Sha1Thumbprint(boolean bl) {
        this.computeX509Sha1Thumbprint = bl;
        return this;
    }

    @Override
    public X509BuilderSupport x509Sha256Thumbprint(byte[] byArray) {
        this.map.put(AbstractAsymmetricJwk.X5T_S256.getId(), (Object)byArray);
        return this;
    }

    @Override
    public X509BuilderSupport x509Sha256Thumbprint(boolean bl) {
        this.computeX509Sha256Thumbprint = bl;
        return this;
    }

    private byte[] computeThumbprint(X509Certificate x509Certificate, HashAlgorithm hashAlgorithm) {
        byte[] byArray = this.GET_X509_BYTES.apply(x509Certificate);
        InputStream inputStream = Streams.of(byArray);
        DefaultRequest<InputStream> defaultRequest = new DefaultRequest<InputStream>(inputStream, null, null);
        return hashAlgorithm.digest(defaultRequest);
    }

    public void apply() {
        Boolean bl;
        List<X509Certificate> list = this.map.get(AbstractAsymmetricJwk.X5C);
        X509Certificate x509Certificate = null;
        if (!Collections.isEmpty(list)) {
            x509Certificate = list.get(0);
        }
        if ((bl = this.computeX509Sha256Thumbprint) == null) {
            bl = x509Certificate != null && !this.computeX509Sha1Thumbprint && Objects.isEmpty(this.map.get(AbstractAsymmetricJwk.X5T_S256));
        }
        if (x509Certificate != null) {
            byte[] byArray;
            if (this.computeX509Sha1Thumbprint) {
                byArray = this.computeThumbprint(x509Certificate, DefaultHashAlgorithm.SHA1);
                this.x509Sha1Thumbprint(byArray);
            }
            if (bl.booleanValue()) {
                byArray = this.computeThumbprint(x509Certificate, Jwks.HASH.SHA256);
                this.x509Sha256Thumbprint(byArray);
            }
        }
    }

    @Override
    public X509Builder x509Sha256Thumbprint(boolean bl) {
        return this.x509Sha256Thumbprint(bl);
    }

    @Override
    public X509Builder x509Sha1Thumbprint(boolean bl) {
        return this.x509Sha1Thumbprint(bl);
    }

    @Override
    public X509Mutator x509Sha256Thumbprint(byte[] byArray) {
        return this.x509Sha256Thumbprint(byArray);
    }

    @Override
    public X509Mutator x509Sha1Thumbprint(byte[] byArray) {
        return this.x509Sha1Thumbprint(byArray);
    }

    @Override
    public X509Mutator x509Chain(List list) {
        return this.x509Chain(list);
    }

    @Override
    public X509Mutator x509Url(URI uRI) {
        return this.x509Url(uRI);
    }
}

