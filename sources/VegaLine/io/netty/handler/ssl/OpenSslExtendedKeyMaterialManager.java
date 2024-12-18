/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.OpenSslKeyMaterialManager;
import io.netty.handler.ssl.ReferenceCountedOpenSslEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.security.auth.x500.X500Principal;

final class OpenSslExtendedKeyMaterialManager
extends OpenSslKeyMaterialManager {
    private final X509ExtendedKeyManager keyManager;

    OpenSslExtendedKeyMaterialManager(X509ExtendedKeyManager keyManager, String password) {
        super(keyManager, password);
        this.keyManager = keyManager;
    }

    @Override
    protected String chooseClientAlias(ReferenceCountedOpenSslEngine engine, String[] keyTypes, X500Principal[] issuer) {
        return this.keyManager.chooseEngineClientAlias(keyTypes, issuer, engine);
    }

    @Override
    protected String chooseServerAlias(ReferenceCountedOpenSslEngine engine, String type2) {
        return this.keyManager.chooseEngineServerAlias(type2, null, engine);
    }
}

