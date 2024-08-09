/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.ProvidedKeyBuilder;
import io.jsonwebtoken.impl.security.ProviderSecretKey;
import io.jsonwebtoken.security.Password;
import io.jsonwebtoken.security.SecretKeyBuilder;
import java.security.Key;
import javax.crypto.SecretKey;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class ProvidedSecretKeyBuilder
extends ProvidedKeyBuilder<SecretKey, SecretKeyBuilder>
implements SecretKeyBuilder {
    ProvidedSecretKeyBuilder(SecretKey secretKey) {
        super(secretKey);
    }

    @Override
    public SecretKey doBuild() {
        if (this.key instanceof Password) {
            return (SecretKey)this.key;
        }
        return this.provider != null ? new ProviderSecretKey(this.provider, (SecretKey)this.key) : (SecretKey)this.key;
    }

    @Override
    public Key doBuild() {
        return this.doBuild();
    }
}

