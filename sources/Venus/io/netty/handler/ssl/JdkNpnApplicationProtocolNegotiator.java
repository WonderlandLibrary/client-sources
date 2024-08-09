/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator;
import io.netty.handler.ssl.JettyNpnSslEngine;
import java.util.List;
import javax.net.ssl.SSLEngine;

@Deprecated
public final class JdkNpnApplicationProtocolNegotiator
extends JdkBaseApplicationProtocolNegotiator {
    private static final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory NPN_WRAPPER = new JdkApplicationProtocolNegotiator.SslEngineWrapperFactory(){
        {
            if (!JettyNpnSslEngine.isAvailable()) {
                throw new RuntimeException("NPN unsupported. Is your classpath configured correctly? See https://wiki.eclipse.org/Jetty/Feature/NPN");
            }
        }

        @Override
        public SSLEngine wrapSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean bl) {
            return new JettyNpnSslEngine(sSLEngine, jdkApplicationProtocolNegotiator, bl);
        }
    };

    public JdkNpnApplicationProtocolNegotiator(Iterable<String> iterable) {
        this(false, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(String ... stringArray) {
        this(false, stringArray);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean bl, Iterable<String> iterable) {
        this(bl, bl, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean bl, String ... stringArray) {
        this(bl, bl, stringArray);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean bl, boolean bl2, Iterable<String> iterable) {
        this(bl ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, bl2 ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean bl, boolean bl2, String ... stringArray) {
        this(bl ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, bl2 ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, stringArray);
    }

    public JdkNpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolSelectionListenerFactory, Iterable<String> iterable) {
        super(NPN_WRAPPER, protocolSelectorFactory, protocolSelectionListenerFactory, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolSelectionListenerFactory, String ... stringArray) {
        super(NPN_WRAPPER, protocolSelectorFactory, protocolSelectionListenerFactory, stringArray);
    }

    @Override
    public JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory() {
        return super.wrapperFactory();
    }

    @Override
    public JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolListenerFactory() {
        return super.protocolListenerFactory();
    }

    @Override
    public JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory() {
        return super.protocolSelectorFactory();
    }

    public List protocols() {
        return super.protocols();
    }
}

