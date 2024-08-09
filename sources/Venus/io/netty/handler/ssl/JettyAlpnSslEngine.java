/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.alpn.ALPN
 *  org.eclipse.jetty.alpn.ALPN$ClientProvider
 *  org.eclipse.jetty.alpn.ALPN$Provider
 *  org.eclipse.jetty.alpn.ALPN$ServerProvider
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.handler.ssl.JdkSslEngine;
import io.netty.handler.ssl.SslUtils;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import org.eclipse.jetty.alpn.ALPN;

abstract class JettyAlpnSslEngine
extends JdkSslEngine {
    private static final boolean available = JettyAlpnSslEngine.initAvailable();

    static boolean isAvailable() {
        return available;
    }

    private static boolean initAvailable() {
        if (PlatformDependent.javaVersion() <= 8) {
            try {
                Class.forName("sun.security.ssl.ALPNExtension", true, null);
                return true;
            } catch (Throwable throwable) {
                // empty catch block
            }
        }
        return true;
    }

    static JettyAlpnSslEngine newClientEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
        return new ClientEngine(sSLEngine, jdkApplicationProtocolNegotiator);
    }

    static JettyAlpnSslEngine newServerEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
        return new ServerEngine(sSLEngine, jdkApplicationProtocolNegotiator);
    }

    private JettyAlpnSslEngine(SSLEngine sSLEngine) {
        super(sSLEngine);
    }

    JettyAlpnSslEngine(SSLEngine sSLEngine, 1 var2_2) {
        this(sSLEngine);
    }

    private static final class ServerEngine
    extends JettyAlpnSslEngine {
        ServerEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
            super(sSLEngine, null);
            ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator, "applicationNegotiator");
            JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector = ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet<String>(jdkApplicationProtocolNegotiator.protocols())), "protocolSelector");
            ALPN.put((SSLEngine)sSLEngine, (ALPN.Provider)new ALPN.ServerProvider(this, protocolSelector){
                final JdkApplicationProtocolNegotiator.ProtocolSelector val$protocolSelector;
                final ServerEngine this$0;
                {
                    this.this$0 = serverEngine;
                    this.val$protocolSelector = protocolSelector;
                }

                public String select(List<String> list) throws SSLException {
                    try {
                        return this.val$protocolSelector.select(list);
                    } catch (Throwable throwable) {
                        throw SslUtils.toSSLHandshakeException(throwable);
                    }
                }

                public void unsupported() {
                    this.val$protocolSelector.unsupported();
                }
            });
        }

        @Override
        public void closeInbound() throws SSLException {
            try {
                ALPN.remove((SSLEngine)this.getWrappedEngine());
            } finally {
                super.closeInbound();
            }
        }

        @Override
        public void closeOutbound() {
            try {
                ALPN.remove((SSLEngine)this.getWrappedEngine());
            } finally {
                super.closeOutbound();
            }
        }
    }

    private static final class ClientEngine
    extends JettyAlpnSslEngine {
        ClientEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
            super(sSLEngine, null);
            ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator, "applicationNegotiator");
            JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolSelectionListener = ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolListenerFactory().newListener(this, jdkApplicationProtocolNegotiator.protocols()), "protocolListener");
            ALPN.put((SSLEngine)sSLEngine, (ALPN.Provider)new ALPN.ClientProvider(this, jdkApplicationProtocolNegotiator, protocolSelectionListener){
                final JdkApplicationProtocolNegotiator val$applicationNegotiator;
                final JdkApplicationProtocolNegotiator.ProtocolSelectionListener val$protocolListener;
                final ClientEngine this$0;
                {
                    this.this$0 = clientEngine;
                    this.val$applicationNegotiator = jdkApplicationProtocolNegotiator;
                    this.val$protocolListener = protocolSelectionListener;
                }

                public List<String> protocols() {
                    return this.val$applicationNegotiator.protocols();
                }

                public void selected(String string) throws SSLException {
                    try {
                        this.val$protocolListener.selected(string);
                    } catch (Throwable throwable) {
                        throw SslUtils.toSSLHandshakeException(throwable);
                    }
                }

                public void unsupported() {
                    this.val$protocolListener.unsupported();
                }
            });
        }

        @Override
        public void closeInbound() throws SSLException {
            try {
                ALPN.remove((SSLEngine)this.getWrappedEngine());
            } finally {
                super.closeInbound();
            }
        }

        @Override
        public void closeOutbound() {
            try {
                ALPN.remove((SSLEngine)this.getWrappedEngine());
            } finally {
                super.closeOutbound();
            }
        }
    }
}

