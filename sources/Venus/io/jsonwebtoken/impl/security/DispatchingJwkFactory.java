/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.AbstractJwk;
import io.jsonwebtoken.impl.security.AsymmetricJwkFactory;
import io.jsonwebtoken.impl.security.EcPrivateJwkFactory;
import io.jsonwebtoken.impl.security.EcPublicJwkFactory;
import io.jsonwebtoken.impl.security.FamilyJwkFactory;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.JwkFactory;
import io.jsonwebtoken.impl.security.OctetPrivateJwkFactory;
import io.jsonwebtoken.impl.security.OctetPublicJwkFactory;
import io.jsonwebtoken.impl.security.RsaPrivateJwkFactory;
import io.jsonwebtoken.impl.security.RsaPublicJwkFactory;
import io.jsonwebtoken.impl.security.SecretJwkFactory;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.UnsupportedKeyException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;

class DispatchingJwkFactory
implements JwkFactory<Key, Jwk<Key>> {
    private static final Collection<FamilyJwkFactory<Key, ?>> DEFAULT_FACTORIES = DispatchingJwkFactory.createDefaultFactories();
    static final JwkFactory<Key, Jwk<Key>> DEFAULT_INSTANCE = new DispatchingJwkFactory();
    private final Collection<FamilyJwkFactory<Key, ?>> factories;

    private static Collection<FamilyJwkFactory<Key, ?>> createDefaultFactories() {
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(new SecretJwkFactory());
        arrayList.add(new AsymmetricJwkFactory(EcPublicJwkFactory.INSTANCE, new EcPrivateJwkFactory()));
        arrayList.add(new AsymmetricJwkFactory(RsaPublicJwkFactory.INSTANCE, new RsaPrivateJwkFactory()));
        arrayList.add(new AsymmetricJwkFactory(OctetPublicJwkFactory.INSTANCE, new OctetPrivateJwkFactory()));
        return arrayList;
    }

    DispatchingJwkFactory() {
        this(DEFAULT_FACTORIES);
    }

    DispatchingJwkFactory(Collection<? extends FamilyJwkFactory<?, ?>> collection) {
        Assert.notEmpty(collection, "FamilyJwkFactory collection cannot be null or empty.");
        this.factories = new ArrayList(collection.size());
        for (FamilyJwkFactory<?, ?> familyJwkFactory : collection) {
            Assert.hasText(familyJwkFactory.getId(), "FamilyJwkFactory.getFactoryId() cannot return null or empty.");
            this.factories.add(familyJwkFactory);
        }
    }

    @Override
    public JwkContext<Key> newContext(JwkContext<?> jwkContext, Key key) {
        Assert.notNull(jwkContext, "JwkContext cannot be null.");
        String string = jwkContext.getType();
        DispatchingJwkFactory.assertKeyOrKeyType(key, string);
        for (FamilyJwkFactory<Key, ?> familyJwkFactory : this.factories) {
            if (!familyJwkFactory.supports(key) && !familyJwkFactory.supports(jwkContext)) continue;
            JwkContext<Key> jwkContext2 = familyJwkFactory.newContext(jwkContext, key);
            return Assert.notNull(jwkContext2, "FamilyJwkFactory implementation cannot return null JwkContexts.");
        }
        throw DispatchingJwkFactory.noFamily(key, string);
    }

    private static void assertKeyOrKeyType(Key key, String string) {
        if (key == null && !Strings.hasText(string)) {
            String string2 = "Either a Key instance or a " + AbstractJwk.KTY + " value is required to create a JWK.";
            throw new InvalidKeyException(string2);
        }
    }

    @Override
    public Jwk<Key> createJwk(JwkContext<Key> jwkContext) {
        Assert.notNull(jwkContext, "JwkContext cannot be null.");
        Key key = jwkContext.getKey();
        String string = Strings.clean(jwkContext.getType());
        DispatchingJwkFactory.assertKeyOrKeyType(key, string);
        for (FamilyJwkFactory<Key, Key> familyJwkFactory : this.factories) {
            if (!familyJwkFactory.supports(jwkContext)) continue;
            String string2 = Assert.hasText(familyJwkFactory.getId(), "factory id cannot be null or empty.");
            if (string == null) {
                jwkContext.setType(string2);
            }
            return familyJwkFactory.createJwk(jwkContext);
        }
        throw DispatchingJwkFactory.noFamily(key, string);
    }

    private static UnsupportedKeyException noFamily(Key key, String string) {
        String string2 = key != null ? "key of type " + key.getClass().getName() : "kty value '" + string + "'";
        String string3 = "Unable to create JWK for unrecognized " + string2 + ": there is no known JWK Factory capable of creating JWKs for this key type.";
        return new UnsupportedKeyException(string3);
    }
}

