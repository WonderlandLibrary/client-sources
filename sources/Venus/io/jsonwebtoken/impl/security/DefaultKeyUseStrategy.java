/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.KeyUsage;
import io.jsonwebtoken.impl.security.KeyUseStrategy;

public class DefaultKeyUseStrategy
implements KeyUseStrategy {
    static final KeyUseStrategy INSTANCE = new DefaultKeyUseStrategy();
    private static final String SIGNATURE = "sig";
    private static final String ENCRYPTION = "enc";

    @Override
    public String toJwkValue(KeyUsage keyUsage) {
        if (keyUsage.isKeyEncipherment() || keyUsage.isDataEncipherment() || keyUsage.isKeyAgreement()) {
            return ENCRYPTION;
        }
        if (keyUsage.isDigitalSignature() || keyUsage.isNonRepudiation() || keyUsage.isKeyCertSign() || keyUsage.isCRLSign()) {
            return SIGNATURE;
        }
        return null;
    }
}

