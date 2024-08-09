/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.ProtectedHeader;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.AbstractAsymmetricJwk;
import io.jsonwebtoken.impl.security.AbstractJwk;
import io.jsonwebtoken.impl.security.JwkConverter;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.PublicJwk;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultProtectedHeader
extends DefaultHeader
implements ProtectedHeader {
    static final Parameter<URI> JKU = Parameters.uri("jku", "JWK Set URL");
    static final Parameter<PublicJwk<?>> JWK = (Parameter)Parameters.builder(JwkConverter.PUBLIC_JWK_CLASS).setId("jwk").setName("JSON Web Key").setConverter(JwkConverter.PUBLIC_JWK).build();
    static final Parameter<Set<String>> CRIT = Parameters.stringSet("crit", "Critical");
    static final Parameter<String> KID = AbstractJwk.KID;
    static final Parameter<URI> X5U = AbstractAsymmetricJwk.X5U;
    static final Parameter<List<X509Certificate>> X5C = AbstractAsymmetricJwk.X5C;
    static final Parameter<byte[]> X5T = AbstractAsymmetricJwk.X5T;
    static final Parameter<byte[]> X5T_S256 = AbstractAsymmetricJwk.X5T_S256;
    static final Registry<String, Parameter<?>> PARAMS = Parameters.registry(DefaultHeader.PARAMS, CRIT, JKU, JWK, KID, X5U, X5C, X5T, X5T_S256);

    static boolean isCandidate(ParameterMap parameterMap) {
        String string = parameterMap.get(DefaultHeader.ALGORITHM);
        return Strings.hasText(string) && !string.equalsIgnoreCase(Jwts.SIG.NONE.getId());
    }

    protected DefaultProtectedHeader(Registry<String, Parameter<?>> registry, Map<String, ?> map) {
        super(registry, map);
    }

    @Override
    public String getKeyId() {
        return this.get(KID);
    }

    @Override
    public URI getJwkSetUrl() {
        return this.get(JKU);
    }

    @Override
    public PublicJwk<?> getJwk() {
        return this.get(JWK);
    }

    @Override
    public URI getX509Url() {
        return this.get(AbstractAsymmetricJwk.X5U);
    }

    @Override
    public List<X509Certificate> getX509Chain() {
        return this.get(X5C);
    }

    @Override
    public byte[] getX509Sha1Thumbprint() {
        return this.get(X5T);
    }

    @Override
    public byte[] getX509Sha256Thumbprint() {
        return this.get(X5T_S256);
    }

    @Override
    public Set<String> getCritical() {
        return this.get(CRIT);
    }
}

