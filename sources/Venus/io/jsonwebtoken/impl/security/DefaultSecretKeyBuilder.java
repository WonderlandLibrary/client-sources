/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.AbstractSecurityBuilder;
import io.jsonwebtoken.impl.security.JcaTemplate;
import io.jsonwebtoken.impl.security.Randoms;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.SecretKeyBuilder;
import javax.crypto.SecretKey;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultSecretKeyBuilder
extends AbstractSecurityBuilder<SecretKey, SecretKeyBuilder>
implements SecretKeyBuilder {
    protected final String JCA_NAME;
    protected final int BIT_LENGTH;

    public DefaultSecretKeyBuilder(String string, int n) {
        this.JCA_NAME = Assert.hasText(string, "jcaName cannot be null or empty.");
        if (n % 8 != 0) {
            String string2 = "bitLength must be an even multiple of 8";
            throw new IllegalArgumentException(string2);
        }
        this.BIT_LENGTH = Assert.gt(n, 0, "bitLength must be > 0");
        this.random(Randoms.secureRandom());
    }

    @Override
    public SecretKey build() {
        JcaTemplate jcaTemplate = new JcaTemplate(this.JCA_NAME, this.provider, this.random);
        return jcaTemplate.generateSecretKey(this.BIT_LENGTH);
    }

    @Override
    public Object build() {
        return this.build();
    }
}

