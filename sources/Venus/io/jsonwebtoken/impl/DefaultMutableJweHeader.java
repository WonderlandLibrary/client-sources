/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJweHeader;
import io.jsonwebtoken.impl.DefaultJweHeaderMutator;
import io.jsonwebtoken.impl.DefaultProtectedHeader;
import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.security.PublicJwk;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Set;

public class DefaultMutableJweHeader
extends DefaultJweHeaderMutator<DefaultMutableJweHeader>
implements JweHeader {
    public DefaultMutableJweHeader(DefaultJweHeaderMutator<?> defaultJweHeaderMutator) {
        super(defaultJweHeaderMutator);
    }

    private <T> T get(Parameter<T> parameter) {
        return ((ParameterMap)this.DELEGATE).get(parameter);
    }

    @Override
    public String getAlgorithm() {
        return this.get(DefaultHeader.ALGORITHM);
    }

    @Override
    public String getContentType() {
        return this.get(DefaultHeader.CONTENT_TYPE);
    }

    @Override
    public String getType() {
        return this.get(DefaultHeader.TYPE);
    }

    @Override
    public String getCompressionAlgorithm() {
        return this.get(DefaultHeader.COMPRESSION_ALGORITHM);
    }

    @Override
    public URI getJwkSetUrl() {
        return this.get(DefaultProtectedHeader.JKU);
    }

    @Override
    public PublicJwk<?> getJwk() {
        return this.get(DefaultProtectedHeader.JWK);
    }

    @Override
    public String getKeyId() {
        return this.get(DefaultProtectedHeader.KID);
    }

    @Override
    public Set<String> getCritical() {
        return this.get(DefaultProtectedHeader.CRIT);
    }

    @Override
    public URI getX509Url() {
        return this.get(DefaultProtectedHeader.X5U);
    }

    @Override
    public List<X509Certificate> getX509Chain() {
        return this.get(DefaultProtectedHeader.X5C);
    }

    @Override
    public byte[] getX509Sha1Thumbprint() {
        return this.get(DefaultProtectedHeader.X5T);
    }

    @Override
    public byte[] getX509Sha256Thumbprint() {
        return this.get(DefaultProtectedHeader.X5T_S256);
    }

    @Override
    public byte[] getAgreementPartyUInfo() {
        return this.get(DefaultJweHeader.APU);
    }

    @Override
    public byte[] getAgreementPartyVInfo() {
        return this.get(DefaultJweHeader.APV);
    }

    @Override
    public Integer getPbes2Count() {
        return this.get(DefaultJweHeader.P2C);
    }

    @Override
    public String getEncryptionAlgorithm() {
        return this.get(DefaultJweHeader.ENCRYPTION_ALGORITHM);
    }

    @Override
    public PublicJwk<?> getEphemeralPublicKey() {
        return this.get(DefaultJweHeader.EPK);
    }

    @Override
    public byte[] getInitializationVector() {
        return this.get(DefaultJweHeader.IV);
    }

    @Override
    public byte[] getAuthenticationTag() {
        return this.get(DefaultJweHeader.TAG);
    }

    @Override
    public byte[] getPbes2Salt() {
        return this.get(DefaultJweHeader.P2S);
    }
}

