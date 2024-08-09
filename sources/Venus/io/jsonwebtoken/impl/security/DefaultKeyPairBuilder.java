/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.AbstractSecurityBuilder;
import io.jsonwebtoken.impl.security.JcaTemplate;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.KeyPairBuilder;
import java.security.KeyPair;
import java.security.spec.AlgorithmParameterSpec;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultKeyPairBuilder
extends AbstractSecurityBuilder<KeyPair, KeyPairBuilder>
implements KeyPairBuilder {
    private final String jcaName;
    private final int bitLength;
    private final AlgorithmParameterSpec params;

    public DefaultKeyPairBuilder(String string) {
        this.jcaName = Assert.hasText(string, "jcaName cannot be null or empty.");
        this.bitLength = 0;
        this.params = null;
    }

    public DefaultKeyPairBuilder(String string, int n) {
        this.jcaName = Assert.hasText(string, "jcaName cannot be null or empty.");
        this.bitLength = Assert.gt(n, 0, "bitLength must be a positive integer greater than 0");
        this.params = null;
    }

    public DefaultKeyPairBuilder(String string, AlgorithmParameterSpec algorithmParameterSpec) {
        this.jcaName = Assert.hasText(string, "jcaName cannot be null or empty.");
        this.params = Assert.notNull(algorithmParameterSpec, "AlgorithmParameterSpec params cannot be null.");
        this.bitLength = 0;
    }

    @Override
    public KeyPair build() {
        JcaTemplate jcaTemplate = new JcaTemplate(this.jcaName, this.provider, this.random);
        if (this.params != null) {
            return jcaTemplate.generateKeyPair(this.params);
        }
        if (this.bitLength > 0) {
            return jcaTemplate.generateKeyPair(this.bitLength);
        }
        return jcaTemplate.generateKeyPair();
    }

    @Override
    public Object build() {
        return this.build();
    }
}

