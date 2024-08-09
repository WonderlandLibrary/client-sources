/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.PrivateECKey;
import io.jsonwebtoken.impl.security.ProvidedKeyBuilder;
import io.jsonwebtoken.impl.security.ProviderPrivateKey;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.PrivateKeyBuilder;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECKey;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ProvidedPrivateKeyBuilder
extends ProvidedKeyBuilder<PrivateKey, PrivateKeyBuilder>
implements PrivateKeyBuilder {
    private PublicKey publicKey;

    ProvidedPrivateKeyBuilder(PrivateKey privateKey) {
        super(privateKey);
    }

    @Override
    public PrivateKeyBuilder publicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    @Override
    public PrivateKey doBuild() {
        PrivateKey privateKey = (PrivateKey)this.key;
        String string = Strings.clean(((PrivateKey)this.key).getAlgorithm());
        if (!(privateKey instanceof ECKey) && ("EC".equalsIgnoreCase(string) || "ECDSA".equalsIgnoreCase(string)) && this.publicKey instanceof ECKey) {
            privateKey = new PrivateECKey(privateKey, ((ECKey)((Object)this.publicKey)).getParams());
        }
        return this.provider != null ? new ProviderPrivateKey(this.provider, privateKey) : privateKey;
    }

    @Override
    public Key doBuild() {
        return this.doBuild();
    }
}

