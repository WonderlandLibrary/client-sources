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

    OpenSslExtendedKeyMaterialManager(X509ExtendedKeyManager x509ExtendedKeyManager, String string) {
        super(x509ExtendedKeyManager, string);
        this.keyManager = x509ExtendedKeyManager;
    }

    @Override
    protected String chooseClientAlias(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String[] stringArray, X500Principal[] x500PrincipalArray) {
        return this.keyManager.chooseEngineClientAlias(stringArray, x500PrincipalArray, referenceCountedOpenSslEngine);
    }

    @Override
    protected String chooseServerAlias(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String string) {
        return this.keyManager.chooseEngineServerAlias(string, null, referenceCountedOpenSslEngine);
    }
}

