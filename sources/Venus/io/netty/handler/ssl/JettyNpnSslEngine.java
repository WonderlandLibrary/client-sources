/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.npn.NextProtoNego
 *  org.eclipse.jetty.npn.NextProtoNego$ClientProvider
 *  org.eclipse.jetty.npn.NextProtoNego$Provider
 *  org.eclipse.jetty.npn.NextProtoNego$ServerProvider
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
import org.eclipse.jetty.npn.NextProtoNego;

final class JettyNpnSslEngine
extends JdkSslEngine {
    private static boolean available;

    static boolean isAvailable() {
        JettyNpnSslEngine.updateAvailability();
        return available;
    }

    private static void updateAvailability() {
        if (available) {
            return;
        }
        try {
            Class.forName("sun.security.ssl.NextProtoNegoExtension", true, null);
            available = true;
        } catch (Exception exception) {
            // empty catch block
        }
    }

    JettyNpnSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean bl) {
        super(sSLEngine);
        ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator, "applicationNegotiator");
        if (bl) {
            JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolSelectionListener = ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolListenerFactory().newListener(this, jdkApplicationProtocolNegotiator.protocols()), "protocolListener");
            NextProtoNego.put((SSLEngine)sSLEngine, (NextProtoNego.Provider)new NextProtoNego.ServerProvider(this, protocolSelectionListener, jdkApplicationProtocolNegotiator){
                final JdkApplicationProtocolNegotiator.ProtocolSelectionListener val$protocolListener;
                final JdkApplicationProtocolNegotiator val$applicationNegotiator;
                final JettyNpnSslEngine this$0;
                {
                    this.this$0 = jettyNpnSslEngine;
                    this.val$protocolListener = protocolSelectionListener;
                    this.val$applicationNegotiator = jdkApplicationProtocolNegotiator;
                }

                public void unsupported() {
                    this.val$protocolListener.unsupported();
                }

                public List<String> protocols() {
                    return this.val$applicationNegotiator.protocols();
                }

                public void protocolSelected(String string) {
                    try {
                        this.val$protocolListener.selected(string);
                    } catch (Throwable throwable) {
                        PlatformDependent.throwException(throwable);
                    }
                }
            });
        } else {
            JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector = ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet<String>(jdkApplicationProtocolNegotiator.protocols())), "protocolSelector");
            NextProtoNego.put((SSLEngine)sSLEngine, (NextProtoNego.Provider)new NextProtoNego.ClientProvider(this, protocolSelector){
                final JdkApplicationProtocolNegotiator.ProtocolSelector val$protocolSelector;
                final JettyNpnSslEngine this$0;
                {
                    this.this$0 = jettyNpnSslEngine;
                    this.val$protocolSelector = protocolSelector;
                }

                public boolean supports() {
                    return false;
                }

                public void unsupported() {
                    this.val$protocolSelector.unsupported();
                }

                public String selectProtocol(List<String> list) {
                    try {
                        return this.val$protocolSelector.select(list);
                    } catch (Throwable throwable) {
                        PlatformDependent.throwException(throwable);
                        return null;
                    }
                }
            });
        }
    }

    @Override
    public void closeInbound() throws SSLException {
        NextProtoNego.remove((SSLEngine)this.getWrappedEngine());
        super.closeInbound();
    }

    @Override
    public void closeOutbound() {
        NextProtoNego.remove((SSLEngine)this.getWrappedEngine());
        super.closeOutbound();
    }
}

