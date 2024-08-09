/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.KeyOperation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class KeyOperationConverter
implements Converter<KeyOperation, Object> {
    static final Converter<KeyOperation, Object> DEFAULT = new KeyOperationConverter(Jwks.OP.get());
    private final Registry<String, KeyOperation> registry;

    KeyOperationConverter(Registry<String, KeyOperation> registry) {
        this.registry = Assert.notEmpty(registry, "KeyOperation registry cannot be null or empty.");
    }

    @Override
    public String applyTo(KeyOperation keyOperation) {
        Assert.notNull(keyOperation, "KeyOperation cannot be null.");
        return keyOperation.getId();
    }

    @Override
    public KeyOperation applyFrom(Object object) {
        if (object instanceof KeyOperation) {
            return (KeyOperation)object;
        }
        String string = Assert.isInstanceOf(String.class, object, "Argument must be a KeyOperation or String.");
        Assert.hasText(string, "KeyOperation string value cannot be null or empty.");
        KeyOperation keyOperation = (KeyOperation)this.registry.get(string);
        return keyOperation != null ? keyOperation : (KeyOperation)Jwks.OP.builder().id(string).build();
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom(object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((KeyOperation)object);
    }
}

