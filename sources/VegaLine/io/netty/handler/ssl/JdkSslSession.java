/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolAccessor;
import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;

final class JdkSslSession
implements SSLSession,
ApplicationProtocolAccessor {
    private final SSLEngine engine;
    private volatile String applicationProtocol;

    JdkSslSession(SSLEngine engine) {
        this.engine = engine;
    }

    private SSLSession unwrap() {
        return this.engine.getSession();
    }

    @Override
    public String getProtocol() {
        return this.unwrap().getProtocol();
    }

    @Override
    public String getApplicationProtocol() {
        return this.applicationProtocol;
    }

    void setApplicationProtocol(String applicationProtocol) {
        this.applicationProtocol = applicationProtocol;
    }

    @Override
    public byte[] getId() {
        return this.unwrap().getId();
    }

    @Override
    public SSLSessionContext getSessionContext() {
        return this.unwrap().getSessionContext();
    }

    @Override
    public long getCreationTime() {
        return this.unwrap().getCreationTime();
    }

    @Override
    public long getLastAccessedTime() {
        return this.unwrap().getLastAccessedTime();
    }

    @Override
    public void invalidate() {
        this.unwrap().invalidate();
    }

    @Override
    public boolean isValid() {
        return this.unwrap().isValid();
    }

    @Override
    public void putValue(String s, Object o) {
        this.unwrap().putValue(s, o);
    }

    @Override
    public Object getValue(String s) {
        return this.unwrap().getValue(s);
    }

    @Override
    public void removeValue(String s) {
        this.unwrap().removeValue(s);
    }

    @Override
    public String[] getValueNames() {
        return this.unwrap().getValueNames();
    }

    @Override
    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        return this.unwrap().getPeerCertificates();
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return this.unwrap().getLocalCertificates();
    }

    @Override
    public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        return this.unwrap().getPeerCertificateChain();
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return this.unwrap().getPeerPrincipal();
    }

    @Override
    public Principal getLocalPrincipal() {
        return this.unwrap().getLocalPrincipal();
    }

    @Override
    public String getCipherSuite() {
        return this.unwrap().getCipherSuite();
    }

    @Override
    public String getPeerHost() {
        return this.unwrap().getPeerHost();
    }

    @Override
    public int getPeerPort() {
        return this.unwrap().getPeerPort();
    }

    @Override
    public int getPacketBufferSize() {
        return this.unwrap().getPacketBufferSize();
    }

    @Override
    public int getApplicationBufferSize() {
        return this.unwrap().getApplicationBufferSize();
    }
}

