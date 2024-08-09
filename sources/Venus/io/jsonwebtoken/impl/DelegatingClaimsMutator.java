/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.ClaimsMutator;
import io.jsonwebtoken.impl.AbstractAudienceCollection;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.lang.DelegatingMapMutator;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.MapMutator;
import io.jsonwebtoken.lang.Strings;
import java.util.Date;
import java.util.Set;

public class DelegatingClaimsMutator<T extends MapMutator<String, Object, T> & ClaimsMutator<T>>
extends DelegatingMapMutator<String, Object, ParameterMap, T>
implements ClaimsMutator<T> {
    private static final Parameter<String> AUDIENCE_STRING = Parameters.string(DefaultClaims.AUDIENCE.getId(), DefaultClaims.AUDIENCE.getName());

    protected DelegatingClaimsMutator() {
        super(new ParameterMap(DefaultClaims.PARAMS));
    }

    @Override
    <F> T put(Parameter<F> parameter, F f) {
        ((ParameterMap)this.DELEGATE).put(parameter, f);
        return this.self();
    }

    <F> F get(Parameter<F> parameter) {
        return ((ParameterMap)this.DELEGATE).get(parameter);
    }

    @Override
    public T setIssuer(String string) {
        return this.issuer(string);
    }

    @Override
    public T issuer(String string) {
        return this.put((Parameter)DefaultClaims.ISSUER, (Object)string);
    }

    @Override
    public T setSubject(String string) {
        return this.subject(string);
    }

    @Override
    public T subject(String string) {
        return this.put((Parameter)DefaultClaims.SUBJECT, (Object)string);
    }

    @Override
    public T setAudience(String string) {
        return (T)((MapMutator)this.audience().single(string));
    }

    private Set<String> getAudience() {
        if (!((Parameter)((ParameterMap)this.DELEGATE).PARAMS.get(AUDIENCE_STRING.getId())).supports(Collections.emptySet())) {
            String string = this.get(AUDIENCE_STRING);
            this.remove(AUDIENCE_STRING.getId());
            this.setDelegate(((ParameterMap)this.DELEGATE).replace(DefaultClaims.AUDIENCE));
            this.put((Parameter)DefaultClaims.AUDIENCE, (Object)Collections.setOf(string));
        }
        return this.get(DefaultClaims.AUDIENCE);
    }

    private T audienceSingle(String string) {
        if (!Strings.hasText(string)) {
            return this.put((Parameter)DefaultClaims.AUDIENCE, (Object)null);
        }
        this.remove(AUDIENCE_STRING.getId());
        this.setDelegate(((ParameterMap)this.DELEGATE).replace(AUDIENCE_STRING));
        return this.put((Parameter)AUDIENCE_STRING, (Object)string);
    }

    @Override
    public ClaimsMutator.AudienceCollection<T> audience() {
        return new AbstractAudienceCollection<T>(this, (MapMutator)this.self(), this.getAudience()){
            final DelegatingClaimsMutator this$0;
            {
                this.this$0 = delegatingClaimsMutator;
                super(mapMutator, collection);
            }

            @Override
            public T single(String string) {
                return DelegatingClaimsMutator.access$000(this.this$0, string);
            }

            @Override
            public T and() {
                this.this$0.put(DefaultClaims.AUDIENCE, Collections.asSet(this.getCollection()));
                return (MapMutator)super.and();
            }

            @Override
            public Object single(String string) {
                return this.single(string);
            }

            @Override
            public Object and() {
                return this.and();
            }
        };
    }

    @Override
    public T setExpiration(Date date) {
        return this.expiration(date);
    }

    @Override
    public T expiration(Date date) {
        return this.put((Parameter)DefaultClaims.EXPIRATION, (Object)date);
    }

    @Override
    public T setNotBefore(Date date) {
        return this.notBefore(date);
    }

    @Override
    public T notBefore(Date date) {
        return this.put((Parameter)DefaultClaims.NOT_BEFORE, (Object)date);
    }

    @Override
    public T setIssuedAt(Date date) {
        return this.issuedAt(date);
    }

    @Override
    public T issuedAt(Date date) {
        return this.put((Parameter)DefaultClaims.ISSUED_AT, (Object)date);
    }

    @Override
    public T setId(String string) {
        return this.id(string);
    }

    @Override
    public T id(String string) {
        return this.put((Parameter)DefaultClaims.JTI, (Object)string);
    }

    @Override
    public ClaimsMutator id(String string) {
        return (ClaimsMutator)this.id(string);
    }

    @Override
    public ClaimsMutator setId(String string) {
        return (ClaimsMutator)this.setId(string);
    }

    @Override
    public ClaimsMutator issuedAt(Date date) {
        return (ClaimsMutator)this.issuedAt(date);
    }

    @Override
    public ClaimsMutator setIssuedAt(Date date) {
        return (ClaimsMutator)this.setIssuedAt(date);
    }

    @Override
    public ClaimsMutator notBefore(Date date) {
        return (ClaimsMutator)this.notBefore(date);
    }

    @Override
    public ClaimsMutator setNotBefore(Date date) {
        return (ClaimsMutator)this.setNotBefore(date);
    }

    @Override
    public ClaimsMutator expiration(Date date) {
        return (ClaimsMutator)this.expiration(date);
    }

    @Override
    public ClaimsMutator setExpiration(Date date) {
        return (ClaimsMutator)this.setExpiration(date);
    }

    @Override
    public ClaimsMutator setAudience(String string) {
        return (ClaimsMutator)this.setAudience(string);
    }

    @Override
    public ClaimsMutator subject(String string) {
        return (ClaimsMutator)this.subject(string);
    }

    @Override
    public ClaimsMutator setSubject(String string) {
        return (ClaimsMutator)this.setSubject(string);
    }

    @Override
    public ClaimsMutator issuer(String string) {
        return (ClaimsMutator)this.issuer(string);
    }

    @Override
    public ClaimsMutator setIssuer(String string) {
        return (ClaimsMutator)this.setIssuer(string);
    }

    static MapMutator access$000(DelegatingClaimsMutator delegatingClaimsMutator, String string) {
        return delegatingClaimsMutator.audienceSingle(string);
    }
}

