/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.FamilyJwkFactory;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Jwk;
import java.security.Key;

class AsymmetricJwkFactory
implements FamilyJwkFactory<Key, Jwk<Key>> {
    private final String id;
    private final FamilyJwkFactory<Key, Jwk<Key>> publicFactory;
    private final FamilyJwkFactory<Key, Jwk<Key>> privateFactory;

    AsymmetricJwkFactory(FamilyJwkFactory familyJwkFactory, FamilyJwkFactory familyJwkFactory2) {
        this.publicFactory = Assert.notNull(familyJwkFactory, "publicFactory cannot be null.");
        this.privateFactory = Assert.notNull(familyJwkFactory2, "privateFactory cannot be null.");
        this.id = Assert.notNull(familyJwkFactory.getId(), "publicFactory id cannot be null or empty.");
        Assert.isTrue(this.id.equals(familyJwkFactory2.getId()), "privateFactory id must equal publicFactory id");
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean supports(JwkContext<?> jwkContext) {
        return jwkContext != null && (this.id.equals(jwkContext.getType()) || this.privateFactory.supports(jwkContext) || this.publicFactory.supports(jwkContext));
    }

    @Override
    public boolean supports(Key key) {
        return key != null && (this.privateFactory.supports(key) || this.publicFactory.supports(key));
    }

    @Override
    public JwkContext<Key> newContext(JwkContext<?> jwkContext, Key key) {
        return this.privateFactory.supports(key) || this.privateFactory.supports(jwkContext) ? this.privateFactory.newContext(jwkContext, key) : this.publicFactory.newContext(jwkContext, key);
    }

    @Override
    public Jwk<Key> createJwk(JwkContext<Key> jwkContext) {
        if (this.privateFactory.supports(jwkContext)) {
            return this.privateFactory.createJwk(jwkContext);
        }
        return this.publicFactory.createJwk(jwkContext);
    }
}

