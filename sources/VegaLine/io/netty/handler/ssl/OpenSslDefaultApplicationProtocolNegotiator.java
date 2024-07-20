/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

public final class OpenSslDefaultApplicationProtocolNegotiator
implements OpenSslApplicationProtocolNegotiator {
    private final ApplicationProtocolConfig config;

    public OpenSslDefaultApplicationProtocolNegotiator(ApplicationProtocolConfig config) {
        this.config = ObjectUtil.checkNotNull(config, "config");
    }

    @Override
    public List<String> protocols() {
        return this.config.supportedProtocols();
    }

    @Override
    public ApplicationProtocolConfig.Protocol protocol() {
        return this.config.protocol();
    }

    @Override
    public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
        return this.config.selectorFailureBehavior();
    }

    @Override
    public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return this.config.selectedListenerFailureBehavior();
    }
}

