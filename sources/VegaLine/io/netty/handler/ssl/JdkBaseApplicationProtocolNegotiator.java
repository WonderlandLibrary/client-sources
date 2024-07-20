/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolUtil;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.handler.ssl.JdkSslEngine;
import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLHandshakeException;

class JdkBaseApplicationProtocolNegotiator
implements JdkApplicationProtocolNegotiator {
    private final List<String> protocols;
    private final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory;
    private final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory;
    private final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory;
    static final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory FAIL_SELECTOR_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectorFactory(){

        @Override
        public JdkApplicationProtocolNegotiator.ProtocolSelector newSelector(SSLEngine engine, Set<String> supportedProtocols) {
            return new FailProtocolSelector((JdkSslEngine)engine, supportedProtocols);
        }
    };
    static final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory NO_FAIL_SELECTOR_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectorFactory(){

        @Override
        public JdkApplicationProtocolNegotiator.ProtocolSelector newSelector(SSLEngine engine, Set<String> supportedProtocols) {
            return new NoFailProtocolSelector((JdkSslEngine)engine, supportedProtocols);
        }
    };
    static final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory FAIL_SELECTION_LISTENER_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory(){

        @Override
        public JdkApplicationProtocolNegotiator.ProtocolSelectionListener newListener(SSLEngine engine, List<String> supportedProtocols) {
            return new FailProtocolSelectionListener((JdkSslEngine)engine, supportedProtocols);
        }
    };
    static final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory NO_FAIL_SELECTION_LISTENER_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory(){

        @Override
        public JdkApplicationProtocolNegotiator.ProtocolSelectionListener newListener(SSLEngine engine, List<String> supportedProtocols) {
            return new NoFailProtocolSelectionListener((JdkSslEngine)engine, supportedProtocols);
        }
    };

    protected JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, Iterable<String> protocols) {
        this(wrapperFactory, selectorFactory, listenerFactory, ApplicationProtocolUtil.toList(protocols));
    }

    protected JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, String ... protocols) {
        this(wrapperFactory, selectorFactory, listenerFactory, ApplicationProtocolUtil.toList(protocols));
    }

    private JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, List<String> protocols) {
        this.wrapperFactory = ObjectUtil.checkNotNull(wrapperFactory, "wrapperFactory");
        this.selectorFactory = ObjectUtil.checkNotNull(selectorFactory, "selectorFactory");
        this.listenerFactory = ObjectUtil.checkNotNull(listenerFactory, "listenerFactory");
        this.protocols = Collections.unmodifiableList(ObjectUtil.checkNotNull(protocols, "protocols"));
    }

    @Override
    public List<String> protocols() {
        return this.protocols;
    }

    @Override
    public JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory() {
        return this.selectorFactory;
    }

    @Override
    public JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolListenerFactory() {
        return this.listenerFactory;
    }

    @Override
    public JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory() {
        return this.wrapperFactory;
    }

    protected static final class FailProtocolSelectionListener
    extends NoFailProtocolSelectionListener {
        public FailProtocolSelectionListener(JdkSslEngine jettyWrapper, List<String> supportedProtocols) {
            super(jettyWrapper, supportedProtocols);
        }

        @Override
        public void noSelectedMatchFound(String protocol) throws Exception {
            throw new SSLHandshakeException("No compatible protocols found");
        }
    }

    protected static class NoFailProtocolSelectionListener
    implements JdkApplicationProtocolNegotiator.ProtocolSelectionListener {
        private final JdkSslEngine jettyWrapper;
        private final List<String> supportedProtocols;

        public NoFailProtocolSelectionListener(JdkSslEngine jettyWrapper, List<String> supportedProtocols) {
            this.jettyWrapper = jettyWrapper;
            this.supportedProtocols = supportedProtocols;
        }

        @Override
        public void unsupported() {
            this.jettyWrapper.getSession().setApplicationProtocol(null);
        }

        @Override
        public void selected(String protocol) throws Exception {
            if (this.supportedProtocols.contains(protocol)) {
                this.jettyWrapper.getSession().setApplicationProtocol(protocol);
            } else {
                this.noSelectedMatchFound(protocol);
            }
        }

        public void noSelectedMatchFound(String protocol) throws Exception {
        }
    }

    protected static final class FailProtocolSelector
    extends NoFailProtocolSelector {
        public FailProtocolSelector(JdkSslEngine jettyWrapper, Set<String> supportedProtocols) {
            super(jettyWrapper, supportedProtocols);
        }

        @Override
        public String noSelectMatchFound() throws Exception {
            throw new SSLHandshakeException("Selected protocol is not supported");
        }
    }

    protected static class NoFailProtocolSelector
    implements JdkApplicationProtocolNegotiator.ProtocolSelector {
        private final JdkSslEngine jettyWrapper;
        private final Set<String> supportedProtocols;

        public NoFailProtocolSelector(JdkSslEngine jettyWrapper, Set<String> supportedProtocols) {
            this.jettyWrapper = jettyWrapper;
            this.supportedProtocols = supportedProtocols;
        }

        @Override
        public void unsupported() {
            this.jettyWrapper.getSession().setApplicationProtocol(null);
        }

        @Override
        public String select(List<String> protocols) throws Exception {
            for (String p : this.supportedProtocols) {
                if (!protocols.contains(p)) continue;
                this.jettyWrapper.getSession().setApplicationProtocol(p);
                return p;
            }
            return this.noSelectMatchFound();
        }

        public String noSelectMatchFound() throws Exception {
            this.jettyWrapper.getSession().setApplicationProtocol(null);
            return null;
        }
    }
}

