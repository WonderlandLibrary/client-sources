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
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import org.eclipse.jetty.alpn.ALPN;

final class JdkAlpnSslEngine
extends JdkSslEngine {
    private static boolean available;

    static boolean isAvailable() {
        JdkAlpnSslEngine.updateAvailability();
        return available;
    }

    private static void updateAvailability() {
        if (available || PlatformDependent.javaVersion() > 8) {
            return;
        }
        try {
            Class.forName("sun.security.ssl.ALPNExtension", true, null);
            available = true;
        } catch (Exception exception) {
            // empty catch block
        }
    }

    JdkAlpnSslEngine(SSLEngine engine, final JdkApplicationProtocolNegotiator applicationNegotiator, boolean server) {
        super(engine);
        ObjectUtil.checkNotNull(applicationNegotiator, "applicationNegotiator");
        if (server) {
            final JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector = ObjectUtil.checkNotNull(applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet<String>(applicationNegotiator.protocols())), "protocolSelector");
            ALPN.put((SSLEngine)engine, (ALPN.Provider)new ALPN.ServerProvider(){

                public String select(List<String> protocols) throws SSLException {
                    try {
                        return protocolSelector.select(protocols);
                    } catch (SSLHandshakeException e) {
                        throw e;
                    } catch (Throwable t) {
                        SSLHandshakeException e = new SSLHandshakeException(t.getMessage());
                        e.initCause(t);
                        throw e;
                    }
                }

                public void unsupported() {
                    protocolSelector.unsupported();
                }
            });
        } else {
            final JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolListener = ObjectUtil.checkNotNull(applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols()), "protocolListener");
            ALPN.put((SSLEngine)engine, (ALPN.Provider)new ALPN.ClientProvider(){

                public List<String> protocols() {
                    return applicationNegotiator.protocols();
                }

                public void selected(String protocol) throws SSLException {
                    try {
                        protocolListener.selected(protocol);
                    } catch (SSLHandshakeException e) {
                        throw e;
                    } catch (Throwable t) {
                        SSLHandshakeException e = new SSLHandshakeException(t.getMessage());
                        e.initCause(t);
                        throw e;
                    }
                }

                public void unsupported() {
                    protocolListener.unsupported();
                }
            });
        }
    }

    @Override
    public void closeInbound() throws SSLException {
        ALPN.remove((SSLEngine)this.getWrappedEngine());
        super.closeInbound();
    }

    @Override
    public void closeOutbound() {
        ALPN.remove((SSLEngine)this.getWrappedEngine());
        super.closeOutbound();
    }
}

