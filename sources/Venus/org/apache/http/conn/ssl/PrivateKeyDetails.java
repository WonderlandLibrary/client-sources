/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.ssl;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import org.apache.http.util.Args;

@Deprecated
public final class PrivateKeyDetails {
    private final String type;
    private final X509Certificate[] certChain;

    public PrivateKeyDetails(String string, X509Certificate[] x509CertificateArray) {
        this.type = Args.notNull(string, "Private key type");
        this.certChain = x509CertificateArray;
    }

    public String getType() {
        return this.type;
    }

    public X509Certificate[] getCertChain() {
        return this.certChain;
    }

    public String toString() {
        return this.type + ':' + Arrays.toString(this.certChain);
    }
}

