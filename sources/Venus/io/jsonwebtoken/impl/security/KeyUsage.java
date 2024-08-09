/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import java.security.cert.X509Certificate;

public final class KeyUsage {
    private static final boolean[] NO_FLAGS = new boolean[9];
    private static final int digitalSignature = 0;
    private static final int nonRepudiation = 1;
    private static final int keyEncipherment = 2;
    private static final int dataEncipherment = 3;
    private static final int keyAgreement = 4;
    private static final int keyCertSign = 5;
    private static final int cRLSign = 6;
    private static final int encipherOnly = 7;
    private static final int decipherOnly = 8;
    private final boolean[] is;

    public KeyUsage(X509Certificate x509Certificate) {
        boolean[] blArray = x509Certificate != null ? x509Certificate.getKeyUsage() : NO_FLAGS;
        this.is = blArray != null ? blArray : NO_FLAGS;
    }

    public boolean isDigitalSignature() {
        return this.is[0];
    }

    public boolean isNonRepudiation() {
        return this.is[1];
    }

    public boolean isKeyEncipherment() {
        return this.is[2];
    }

    public boolean isDataEncipherment() {
        return this.is[3];
    }

    public boolean isKeyAgreement() {
        return this.is[4];
    }

    public boolean isKeyCertSign() {
        return this.is[5];
    }

    public boolean isCRLSign() {
        return this.is[6];
    }

    public boolean isEncipherOnly() {
        return this.is[7];
    }

    public boolean isDecipherOnly() {
        return this.is[8];
    }
}

