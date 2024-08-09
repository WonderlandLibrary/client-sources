/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.HeaderMutator;
import io.jsonwebtoken.JweHeaderMutator;
import io.jsonwebtoken.ProtectedHeaderMutator;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJweHeader;
import io.jsonwebtoken.impl.DefaultProtectedHeader;
import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.lang.DefaultNestedCollection;
import io.jsonwebtoken.impl.lang.DelegatingMapMutator;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.security.X509BuilderSupport;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.NestedCollection;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.PublicJwk;
import io.jsonwebtoken.security.X509Mutator;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;

public class DefaultJweHeaderMutator<T extends JweHeaderMutator<T>>
extends DelegatingMapMutator<String, Object, ParameterMap, T>
implements JweHeaderMutator<T> {
    protected X509BuilderSupport x509;

    public DefaultJweHeaderMutator() {
        super(new ParameterMap(DefaultJweHeader.PARAMS));
        this.clear();
    }

    public DefaultJweHeaderMutator(DefaultJweHeaderMutator<?> defaultJweHeaderMutator) {
        super(defaultJweHeaderMutator.DELEGATE);
        this.x509 = defaultJweHeaderMutator.x509;
    }

    @Override
    private <F> T put(Parameter<F> parameter, F f) {
        ((ParameterMap)this.DELEGATE).put(parameter, f);
        return (T)((JweHeaderMutator)this.self());
    }

    @Override
    public void clear() {
        super.clear();
        this.x509 = new X509BuilderSupport((ParameterMap)this.DELEGATE, IllegalStateException.class);
    }

    @Override
    public T contentType(String string) {
        return this.put((Parameter)DefaultHeader.CONTENT_TYPE, (Object)string);
    }

    @Override
    public T type(String string) {
        return this.put((Parameter)DefaultHeader.TYPE, (Object)string);
    }

    @Override
    public T setType(String string) {
        return (T)this.type(string);
    }

    @Override
    public T setContentType(String string) {
        return (T)this.contentType(string);
    }

    @Override
    public T setCompressionAlgorithm(String string) {
        return this.put((Parameter)DefaultHeader.COMPRESSION_ALGORITHM, (Object)string);
    }

    @Override
    public NestedCollection<String, T> critical() {
        return new DefaultNestedCollection<String, T>(this, (JweHeaderMutator)this.self(), ((ParameterMap)this.DELEGATE).get(DefaultProtectedHeader.CRIT)){
            final DefaultJweHeaderMutator this$0;
            {
                this.this$0 = defaultJweHeaderMutator;
                super(jweHeaderMutator, collection);
            }

            @Override
            public T and() {
                DefaultJweHeaderMutator.access$000(this.this$0, DefaultProtectedHeader.CRIT, Collections.asSet(this.getCollection()));
                return (JweHeaderMutator)super.and();
            }

            @Override
            public Object and() {
                return this.and();
            }
        };
    }

    @Override
    public T jwk(PublicJwk<?> publicJwk) {
        return this.put((Parameter)DefaultProtectedHeader.JWK, (Object)publicJwk);
    }

    @Override
    public T jwkSetUrl(URI uRI) {
        return this.put((Parameter)DefaultProtectedHeader.JKU, (Object)uRI);
    }

    @Override
    public T keyId(String string) {
        return this.put((Parameter)DefaultProtectedHeader.KID, (Object)string);
    }

    @Override
    public T setKeyId(String string) {
        return (T)this.keyId(string);
    }

    @Override
    public T setAlgorithm(String string) {
        return this.put((Parameter)DefaultHeader.ALGORITHM, (Object)string);
    }

    @Override
    public T x509Url(URI uRI) {
        this.x509.x509Url(uRI);
        return (T)((JweHeaderMutator)this.self());
    }

    @Override
    public T x509Chain(List<X509Certificate> list) {
        this.x509.x509Chain((List)list);
        return (T)((JweHeaderMutator)this.self());
    }

    @Override
    public T x509Sha1Thumbprint(byte[] byArray) {
        this.x509.x509Sha1Thumbprint(byArray);
        return (T)((JweHeaderMutator)this.self());
    }

    @Override
    public T x509Sha256Thumbprint(byte[] byArray) {
        this.x509.x509Sha256Thumbprint(byArray);
        return (T)((JweHeaderMutator)this.self());
    }

    @Override
    public T agreementPartyUInfo(byte[] byArray) {
        return this.put((Parameter)DefaultJweHeader.APU, (Object)byArray);
    }

    @Override
    public T agreementPartyUInfo(String string) {
        return this.agreementPartyUInfo(Strings.utf8(Strings.clean(string)));
    }

    @Override
    public T agreementPartyVInfo(byte[] byArray) {
        return this.put((Parameter)DefaultJweHeader.APV, (Object)byArray);
    }

    @Override
    public T agreementPartyVInfo(String string) {
        return this.agreementPartyVInfo(Strings.utf8(Strings.clean(string)));
    }

    @Override
    public T pbes2Count(int n) {
        return this.put((Parameter)DefaultJweHeader.P2C, n);
    }

    @Override
    public ProtectedHeaderMutator setAlgorithm(String string) {
        return this.setAlgorithm(string);
    }

    @Override
    public ProtectedHeaderMutator setKeyId(String string) {
        return this.setKeyId(string);
    }

    @Override
    public ProtectedHeaderMutator keyId(String string) {
        return this.keyId(string);
    }

    @Override
    public ProtectedHeaderMutator jwkSetUrl(URI uRI) {
        return this.jwkSetUrl(uRI);
    }

    @Override
    public ProtectedHeaderMutator jwk(PublicJwk publicJwk) {
        return this.jwk(publicJwk);
    }

    @Override
    public HeaderMutator setCompressionAlgorithm(String string) {
        return this.setCompressionAlgorithm(string);
    }

    @Override
    public HeaderMutator setContentType(String string) {
        return this.setContentType(string);
    }

    @Override
    public HeaderMutator setType(String string) {
        return this.setType(string);
    }

    @Override
    public HeaderMutator contentType(String string) {
        return this.contentType(string);
    }

    @Override
    public HeaderMutator type(String string) {
        return this.type(string);
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

    static JweHeaderMutator access$000(DefaultJweHeaderMutator defaultJweHeaderMutator, Parameter parameter, Object object) {
        return defaultJweHeaderMutator.put(parameter, object);
    }
}

