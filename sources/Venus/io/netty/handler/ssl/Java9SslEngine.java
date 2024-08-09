/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.Java9SslUtils;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.handler.ssl.JdkSslEngine;
import io.netty.handler.ssl.SslUtils;
import java.nio.ByteBuffer;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;

final class Java9SslEngine
extends JdkSslEngine {
    private final JdkApplicationProtocolNegotiator.ProtocolSelectionListener selectionListener;
    private final AlpnSelector alpnSelector;
    static final boolean $assertionsDisabled = !Java9SslEngine.class.desiredAssertionStatus();

    Java9SslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean bl) {
        super(sSLEngine);
        if (bl) {
            this.selectionListener = null;
            this.alpnSelector = new AlpnSelector(this, jdkApplicationProtocolNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet<String>(jdkApplicationProtocolNegotiator.protocols())));
            Java9SslUtils.setHandshakeApplicationProtocolSelector(sSLEngine, this.alpnSelector);
        } else {
            this.selectionListener = jdkApplicationProtocolNegotiator.protocolListenerFactory().newListener(this, jdkApplicationProtocolNegotiator.protocols());
            this.alpnSelector = null;
            Java9SslUtils.setApplicationProtocols(sSLEngine, jdkApplicationProtocolNegotiator.protocols());
        }
    }

    private SSLEngineResult verifyProtocolSelection(SSLEngineResult sSLEngineResult) throws SSLException {
        if (sSLEngineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
            if (this.alpnSelector == null) {
                try {
                    String string = this.getApplicationProtocol();
                    if (!$assertionsDisabled && string == null) {
                        throw new AssertionError();
                    }
                    if (string.isEmpty()) {
                        this.selectionListener.unsupported();
                    }
                    this.selectionListener.selected(string);
                } catch (Throwable throwable) {
                    throw SslUtils.toSSLHandshakeException(throwable);
                }
            } else {
                if (!$assertionsDisabled && this.selectionListener != null) {
                    throw new AssertionError();
                }
                this.alpnSelector.checkUnsupported();
            }
        }
        return sSLEngineResult;
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.verifyProtocolSelection(super.wrap(byteBuffer, byteBuffer2));
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] byteBufferArray, ByteBuffer byteBuffer) throws SSLException {
        return this.verifyProtocolSelection(super.wrap(byteBufferArray, byteBuffer));
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] byteBufferArray, int n, int n2, ByteBuffer byteBuffer) throws SSLException {
        return this.verifyProtocolSelection(super.wrap(byteBufferArray, n, n2, byteBuffer));
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.verifyProtocolSelection(super.unwrap(byteBuffer, byteBuffer2));
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArray) throws SSLException {
        return this.verifyProtocolSelection(super.unwrap(byteBuffer, byteBufferArray));
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArray, int n, int n2) throws SSLException {
        return this.verifyProtocolSelection(super.unwrap(byteBuffer, byteBufferArray, n, n2));
    }

    @Override
    void setNegotiatedApplicationProtocol(String string) {
    }

    @Override
    public String getNegotiatedApplicationProtocol() {
        String string = this.getApplicationProtocol();
        if (string != null) {
            return string.isEmpty() ? null : string;
        }
        return string;
    }

    @Override
    public String getApplicationProtocol() {
        return Java9SslUtils.getApplicationProtocol(this.getWrappedEngine());
    }

    @Override
    public String getHandshakeApplicationProtocol() {
        return Java9SslUtils.getHandshakeApplicationProtocol(this.getWrappedEngine());
    }

    @Override
    public void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> biFunction) {
        Java9SslUtils.setHandshakeApplicationProtocolSelector(this.getWrappedEngine(), biFunction);
    }

    @Override
    public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return Java9SslUtils.getHandshakeApplicationProtocolSelector(this.getWrappedEngine());
    }

    private final class AlpnSelector
    implements BiFunction<SSLEngine, List<String>, String> {
        private final JdkApplicationProtocolNegotiator.ProtocolSelector selector;
        private boolean called;
        static final boolean $assertionsDisabled = !Java9SslEngine.class.desiredAssertionStatus();
        final Java9SslEngine this$0;

        AlpnSelector(Java9SslEngine java9SslEngine, JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector) {
            this.this$0 = java9SslEngine;
            this.selector = protocolSelector;
        }

        @Override
        public String apply(SSLEngine sSLEngine, List<String> list) {
            if (!$assertionsDisabled && this.called) {
                throw new AssertionError();
            }
            this.called = true;
            try {
                String string = this.selector.select(list);
                return string == null ? "" : string;
            } catch (Exception exception) {
                return null;
            }
        }

        void checkUnsupported() {
            if (this.called) {
                return;
            }
            String string = this.this$0.getApplicationProtocol();
            if (!$assertionsDisabled && string == null) {
                throw new AssertionError();
            }
            if (string.isEmpty()) {
                this.selector.unsupported();
            }
        }

        @Override
        public Object apply(Object object, Object object2) {
            return this.apply((SSLEngine)object, (List)object2);
        }
    }
}

