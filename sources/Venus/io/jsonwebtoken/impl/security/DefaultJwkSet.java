/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.JwkConverter;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.JwkSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DefaultJwkSet
extends ParameterMap
implements JwkSet {
    private static final String NAME = "JWK Set";
    static final Parameter<Set<Jwk<?>>> KEYS = DefaultJwkSet.param(JwkConverter.ANY);

    static Parameter<Set<Jwk<?>>> param(Converter<Jwk<?>, ?> converter) {
        return (Parameter)Parameters.builder(JwkConverter.JWK_CLASS).setConverter(converter).set().setId("keys").setName("JSON Web Keys").setSecret(true).build();
    }

    public DefaultJwkSet(Parameter<Set<Jwk<?>>> parameter, Map<String, ?> map) {
        super(Parameters.registry(parameter), map);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Set<Jwk<?>> getKeys() {
        Set<Jwk<?>> set = this.get(KEYS);
        if (Collections.isEmpty(set)) {
            return Collections.emptySet();
        }
        return Collections.immutable(set);
    }

    @Override
    public Iterator<Jwk<?>> iterator() {
        return this.getKeys().iterator();
    }
}

