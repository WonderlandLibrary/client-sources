/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.Conscrypt;
import io.netty.handler.ssl.ConscryptAlpnSslEngine;
import io.netty.handler.ssl.Java9SslEngine;
import io.netty.handler.ssl.Java9SslUtils;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator;
import io.netty.handler.ssl.JettyAlpnSslEngine;
import io.netty.util.internal.PlatformDependent;
import java.util.List;
import javax.net.ssl.SSLEngine;

@Deprecated
public final class JdkAlpnApplicationProtocolNegotiator
extends JdkBaseApplicationProtocolNegotiator {
    private static final boolean AVAILABLE = Conscrypt.isAvailable() || JdkAlpnApplicationProtocolNegotiator.jdkAlpnSupported() || JettyAlpnSslEngine.isAvailable();
    private static final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory ALPN_WRAPPER = AVAILABLE ? new AlpnWrapper(null) : new FailureWrapper(null);

    public JdkAlpnApplicationProtocolNegotiator(Iterable<String> iterable) {
        this(false, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(String ... stringArray) {
        this(false, stringArray);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean bl, Iterable<String> iterable) {
        this(bl, bl, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean bl, String ... stringArray) {
        this(bl, bl, stringArray);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean bl, boolean bl2, Iterable<String> iterable) {
        this(bl2 ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, bl ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean bl, boolean bl2, String ... stringArray) {
        this(bl2 ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, bl ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, stringArray);
    }

    public JdkAlpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolSelectionListenerFactory, Iterable<String> iterable) {
        super(ALPN_WRAPPER, protocolSelectorFactory, protocolSelectionListenerFactory, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolSelectionListenerFactory, String ... stringArray) {
        super(ALPN_WRAPPER, protocolSelectorFactory, protocolSelectionListenerFactory, stringArray);
    }

    static boolean jdkAlpnSupported() {
        return PlatformDependent.javaVersion() >= 9 && Java9SslUtils.supportsAlpn();
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

    private static final class AlpnWrapper
    extends JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory {
        private AlpnWrapper() {
        }

        @Override
        public SSLEngine wrapSslEngine(SSLEngine sSLEngine, ByteBufAllocator byteBufAllocator, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean bl) {
            if (Conscrypt.isEngineSupported(sSLEngine)) {
                return bl ? ConscryptAlpnSslEngine.newServerEngine(sSLEngine, byteBufAllocator, jdkApplicationProtocolNegotiator) : ConscryptAlpnSslEngine.newClientEngine(sSLEngine, byteBufAllocator, jdkApplicationProtocolNegotiator);
            }
            if (JdkAlpnApplicationProtocolNegotiator.jdkAlpnSupported()) {
                return new Java9SslEngine(sSLEngine, jdkApplicationProtocolNegotiator, bl);
            }
            if (JettyAlpnSslEngine.isAvailable()) {
                return bl ? JettyAlpnSslEngine.newServerEngine(sSLEngine, jdkApplicationProtocolNegotiator) : JettyAlpnSslEngine.newClientEngine(sSLEngine, jdkApplicationProtocolNegotiator);
            }
            throw new RuntimeException("Unable to wrap SSLEngine of type " + sSLEngine.getClass().getName());
        }

        AlpnWrapper(1 var1_1) {
            this();
        }
    }

    private static final class FailureWrapper
    extends JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory {
        private FailureWrapper() {
        }

        @Override
        public SSLEngine wrapSslEngine(SSLEngine sSLEngine, ByteBufAllocator byteBufAllocator, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean bl) {
            throw new RuntimeException("ALPN unsupported. Is your classpath configured correctly? For Conscrypt, add the appropriate Conscrypt JAR to classpath and set the security provider. For Jetty-ALPN, see http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html#alpn-starting");
        }

        FailureWrapper(1 var1_1) {
            this();
        }
    }
}

