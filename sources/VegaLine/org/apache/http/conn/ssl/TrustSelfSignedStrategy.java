/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.apache.http.conn.ssl.TrustStrategy;

public class TrustSelfSignedStrategy
implements TrustStrategy {
    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        return chain.length == 1;
    }
}

