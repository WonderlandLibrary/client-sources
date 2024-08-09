/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolAccessor;
import java.nio.ByteBuffer;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;

class JdkSslEngine
extends SSLEngine
implements ApplicationProtocolAccessor {
    private final SSLEngine engine;
    private volatile String applicationProtocol;

    JdkSslEngine(SSLEngine sSLEngine) {
        this.engine = sSLEngine;
    }

    @Override
    public String getNegotiatedApplicationProtocol() {
        return this.applicationProtocol;
    }

    void setNegotiatedApplicationProtocol(String string) {
        this.applicationProtocol = string;
    }

    @Override
    public SSLSession getSession() {
        return this.engine.getSession();
    }

    public SSLEngine getWrappedEngine() {
        return this.engine;
    }

    @Override
    public void closeInbound() throws SSLException {
        this.engine.closeInbound();
    }

    @Override
    public void closeOutbound() {
        this.engine.closeOutbound();
    }

    @Override
    public String getPeerHost() {
        return this.engine.getPeerHost();
    }

    @Override
    public int getPeerPort() {
        return this.engine.getPeerPort();
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.engine.wrap(byteBuffer, byteBuffer2);
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] byteBufferArray, ByteBuffer byteBuffer) throws SSLException {
        return this.engine.wrap(byteBufferArray, byteBuffer);
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] byteBufferArray, int n, int n2, ByteBuffer byteBuffer) throws SSLException {
        return this.engine.wrap(byteBufferArray, n, n2, byteBuffer);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.engine.unwrap(byteBuffer, byteBuffer2);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArray) throws SSLException {
        return this.engine.unwrap(byteBuffer, byteBufferArray);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArray, int n, int n2) throws SSLException {
        return this.engine.unwrap(byteBuffer, byteBufferArray, n, n2);
    }

    @Override
    public Runnable getDelegatedTask() {
        return this.engine.getDelegatedTask();
    }

    @Override
    public boolean isInboundDone() {
        return this.engine.isInboundDone();
    }

    @Override
    public boolean isOutboundDone() {
        return this.engine.isOutboundDone();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return this.engine.getSupportedCipherSuites();
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return this.engine.getEnabledCipherSuites();
    }

    @Override
    public void setEnabledCipherSuites(String[] stringArray) {
        this.engine.setEnabledCipherSuites(stringArray);
    }

    @Override
    public String[] getSupportedProtocols() {
        return this.engine.getSupportedProtocols();
    }

    @Override
    public String[] getEnabledProtocols() {
        return this.engine.getEnabledProtocols();
    }

    @Override
    public void setEnabledProtocols(String[] stringArray) {
        this.engine.setEnabledProtocols(stringArray);
    }

    @Override
    public SSLSession getHandshakeSession() {
        return this.engine.getHandshakeSession();
    }

    @Override
    public void beginHandshake() throws SSLException {
        this.engine.beginHandshake();
    }

    @Override
    public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return this.engine.getHandshakeStatus();
    }

    @Override
    public void setUseClientMode(boolean bl) {
        this.engine.setUseClientMode(bl);
    }

    @Override
    public boolean getUseClientMode() {
        return this.engine.getUseClientMode();
    }

    @Override
    public void setNeedClientAuth(boolean bl) {
        this.engine.setNeedClientAuth(bl);
    }

    @Override
    public boolean getNeedClientAuth() {
        return this.engine.getNeedClientAuth();
    }

    @Override
    public void setWantClientAuth(boolean bl) {
        this.engine.setWantClientAuth(bl);
    }

    @Override
    public boolean getWantClientAuth() {
        return this.engine.getWantClientAuth();
    }

    @Override
    public void setEnableSessionCreation(boolean bl) {
        this.engine.setEnableSessionCreation(bl);
    }

    @Override
    public boolean getEnableSessionCreation() {
        return this.engine.getEnableSessionCreation();
    }

    @Override
    public SSLParameters getSSLParameters() {
        return this.engine.getSSLParameters();
    }

    @Override
    public void setSSLParameters(SSLParameters sSLParameters) {
        this.engine.setSSLParameters(sSLParameters);
    }
}

