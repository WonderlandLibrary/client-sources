/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.DefaultSecretKeyBuilder;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RandomSecretKeyBuilder
extends DefaultSecretKeyBuilder {
    public RandomSecretKeyBuilder(String string, int n) {
        super(string, n);
    }

    @Override
    public SecretKey build() {
        byte[] byArray = new byte[this.BIT_LENGTH / 8];
        this.random.nextBytes(byArray);
        return new SecretKeySpec(byArray, this.JCA_NAME);
    }

    @Override
    public Object build() {
        return this.build();
    }
}

