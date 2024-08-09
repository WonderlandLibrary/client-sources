/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.lang.Nameable;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.security.AbstractJwk;
import io.jsonwebtoken.impl.security.JwkBuilderSupplier;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.lang.Supplier;
import io.jsonwebtoken.security.DynamicJwkBuilder;
import io.jsonwebtoken.security.EcPrivateJwk;
import io.jsonwebtoken.security.EcPublicJwk;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.MalformedKeyException;
import io.jsonwebtoken.security.OctetPrivateJwk;
import io.jsonwebtoken.security.OctetPublicJwk;
import io.jsonwebtoken.security.PrivateJwk;
import io.jsonwebtoken.security.PublicJwk;
import io.jsonwebtoken.security.RsaPrivateJwk;
import io.jsonwebtoken.security.RsaPublicJwk;
import io.jsonwebtoken.security.SecretJwk;
import java.util.Map;

public final class JwkConverter<T extends Jwk<?>>
implements Converter<T, Object> {
    public static final Class<Jwk<?>> JWK_CLASS = Jwk.class;
    public static final Class<PublicJwk<?>> PUBLIC_JWK_CLASS = PublicJwk.class;
    public static final JwkConverter<Jwk<?>> ANY = new JwkConverter(JWK_CLASS);
    public static final JwkConverter<PublicJwk<?>> PUBLIC_JWK = new JwkConverter(PUBLIC_JWK_CLASS);
    private final Class<T> desiredType;
    private final Supplier<DynamicJwkBuilder<?, ?>> supplier;

    public JwkConverter(Class<T> clazz) {
        this(clazz, JwkBuilderSupplier.DEFAULT);
    }

    public JwkConverter(Supplier<DynamicJwkBuilder<?, ?>> supplier) {
        this(JWK_CLASS, supplier);
    }

    public JwkConverter(Class<T> clazz, Supplier<DynamicJwkBuilder<?, ?>> supplier) {
        this.desiredType = Assert.notNull(clazz, "desiredType cannot be null.");
        this.supplier = Assert.notNull(supplier, "supplier cannot be null.");
    }

    @Override
    public Object applyTo(T t) {
        return this.desiredType.cast(t);
    }

    private static String articleFor(String string) {
        switch (string.charAt(0)) {
            case 'E': 
            case 'R': {
                return "an";
            }
        }
        return "a";
    }

    private static String typeString(Jwk<?> jwk) {
        Assert.isInstanceOf(Nameable.class, jwk, "All JWK implementations must implement Nameable.");
        return ((Nameable)((Object)jwk)).getName();
    }

    private static String typeString(Class<?> clazz) {
        StringBuilder stringBuilder = new StringBuilder();
        if (SecretJwk.class.isAssignableFrom(clazz)) {
            stringBuilder.append("Secret");
        } else if (RsaPublicJwk.class.isAssignableFrom(clazz) || RsaPrivateJwk.class.isAssignableFrom(clazz)) {
            stringBuilder.append("RSA");
        } else if (EcPublicJwk.class.isAssignableFrom(clazz) || EcPrivateJwk.class.isAssignableFrom(clazz)) {
            stringBuilder.append("EC");
        } else if (OctetPublicJwk.class.isAssignableFrom(clazz) || OctetPrivateJwk.class.isAssignableFrom(clazz)) {
            stringBuilder.append("Edwards Curve");
        }
        return JwkConverter.typeString(stringBuilder, clazz);
    }

    private static String typeString(StringBuilder stringBuilder, Class<?> clazz) {
        if (PublicJwk.class.isAssignableFrom(clazz)) {
            Strings.nespace(stringBuilder).append("Public");
        } else if (PrivateJwk.class.isAssignableFrom(clazz)) {
            Strings.nespace(stringBuilder).append("Private");
        }
        Strings.nespace(stringBuilder).append("JWK");
        return stringBuilder.toString();
    }

    private IllegalArgumentException unexpectedIAE(Jwk<?> jwk) {
        String string = JwkConverter.typeString(this.desiredType);
        String string2 = JwkConverter.typeString(jwk);
        String string3 = "Value must be " + JwkConverter.articleFor(string) + " " + string + ", not " + JwkConverter.articleFor(string2) + " " + string2 + ".";
        return new IllegalArgumentException(string3);
    }

    @Override
    public T applyFrom(Object object) {
        Assert.notNull(object, "JWK cannot be null.");
        if (this.desiredType.isInstance(object)) {
            return (T)((Jwk)this.desiredType.cast(object));
        }
        if (object instanceof Jwk) {
            throw this.unexpectedIAE((Jwk)object);
        }
        if (!(object instanceof Map)) {
            String string = "JWK must be a Map<String,?> (JSON Object). Type found: " + object.getClass().getName() + ".";
            throw new IllegalArgumentException(string);
        }
        Map map = Collections.immutable((Map)object);
        Parameter<String> parameter = AbstractJwk.KTY;
        if (Collections.isEmpty(map) || !map.containsKey(parameter.getId())) {
            String string = "JWK is missing required " + parameter + " parameter.";
            throw new MalformedKeyException(string);
        }
        Object v = map.get(parameter.getId());
        if (v == null) {
            String string = "JWK " + parameter + " value cannot be null.";
            throw new MalformedKeyException(string);
        }
        if (!(v instanceof String)) {
            String string = "JWK " + parameter + " value must be a String. Type found: " + v.getClass().getName();
            throw new MalformedKeyException(string);
        }
        String string = (String)v;
        if (!Strings.hasText(string)) {
            String string2 = "JWK " + parameter + " value cannot be empty.";
            throw new MalformedKeyException(string2);
        }
        DynamicJwkBuilder<?, ?> dynamicJwkBuilder = this.supplier.get();
        for (Map.Entry entry : map.entrySet()) {
            String string3;
            Object k = entry.getKey();
            Assert.notNull(k, "JWK map key cannot be null.");
            if (!(k instanceof String)) {
                string3 = "JWK map keys must be Strings. Encountered key '" + k + "' of type " + k.getClass().getName() + ".";
                throw new IllegalArgumentException(string3);
            }
            string3 = (String)k;
            dynamicJwkBuilder.add(string3, entry.getValue());
        }
        Jwk jwk = (Jwk)dynamicJwkBuilder.build();
        if (this.desiredType.isInstance(jwk)) {
            return (T)((Jwk)this.desiredType.cast(jwk));
        }
        throw this.unexpectedIAE(jwk);
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom(object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((T)((Jwk)object));
    }
}

