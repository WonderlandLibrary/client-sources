/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl.util;

import io.netty.util.internal.ObjectUtil;
import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

final class X509TrustManagerWrapper
extends X509ExtendedTrustManager {
    private final X509TrustManager delegate;

    X509TrustManagerWrapper(X509TrustManager x509TrustManager) {
        this.delegate = ObjectUtil.checkNotNull(x509TrustManager, "delegate");
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509CertificateArray, String string) throws CertificateException {
        this.delegate.checkClientTrusted(x509CertificateArray, string);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509CertificateArray, String string, Socket socket) throws CertificateException {
        this.delegate.checkClientTrusted(x509CertificateArray, string);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509CertificateArray, String string, SSLEngine sSLEngine) throws CertificateException {
        this.delegate.checkClientTrusted(x509CertificateArray, string);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509CertificateArray, String string) throws CertificateException {
        this.delegate.checkServerTrusted(x509CertificateArray, string);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509CertificateArray, String string, Socket socket) throws CertificateException {
        this.delegate.checkServerTrusted(x509CertificateArray, string);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509CertificateArray, String string, SSLEngine sSLEngine) throws CertificateException {
        this.delegate.checkServerTrusted(x509CertificateArray, string);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return this.delegate.getAcceptedIssuers();
    }
}

