/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.PemEncoded;
import io.netty.handler.ssl.PemX509Certificate;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.handler.ssl.ReferenceCountedOpenSslEngine;
import io.netty.internal.tcnative.CertificateRequestedCallback;
import io.netty.internal.tcnative.SSL;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.net.ssl.SSLException;
import javax.net.ssl.X509KeyManager;
import javax.security.auth.x500.X500Principal;

class OpenSslKeyMaterialManager {
    static final String KEY_TYPE_RSA = "RSA";
    static final String KEY_TYPE_DH_RSA = "DH_RSA";
    static final String KEY_TYPE_EC = "EC";
    static final String KEY_TYPE_EC_EC = "EC_EC";
    static final String KEY_TYPE_EC_RSA = "EC_RSA";
    private static final Map<String, String> KEY_TYPES = new HashMap<String, String>();
    private final X509KeyManager keyManager;
    private final String password;

    OpenSslKeyMaterialManager(X509KeyManager keyManager, String password) {
        this.keyManager = keyManager;
        this.password = password;
    }

    void setKeyMaterial(ReferenceCountedOpenSslEngine engine) throws SSLException {
        long ssl = engine.sslPointer();
        String[] authMethods = SSL.authenticationMethods(ssl);
        HashSet<String> aliases = new HashSet<String>(authMethods.length);
        for (String authMethod : authMethods) {
            String alias;
            String type2 = KEY_TYPES.get(authMethod);
            if (type2 == null || (alias = this.chooseServerAlias(engine, type2)) == null || !aliases.add(alias)) continue;
            this.setKeyMaterial(ssl, alias);
        }
    }

    CertificateRequestedCallback.KeyMaterial keyMaterial(ReferenceCountedOpenSslEngine engine, String[] keyTypes, X500Principal[] issuer) throws SSLException {
        String alias = this.chooseClientAlias(engine, keyTypes, issuer);
        long keyBio = 0L;
        long keyCertChainBio = 0L;
        long pkey = 0L;
        long certChain = 0L;
        try {
            X509Certificate[] certificates = this.keyManager.getCertificateChain(alias);
            if (certificates == null || certificates.length == 0) {
                CertificateRequestedCallback.KeyMaterial keyMaterial = null;
                return keyMaterial;
            }
            PrivateKey key = this.keyManager.getPrivateKey(alias);
            keyCertChainBio = ReferenceCountedOpenSslContext.toBIO(certificates);
            certChain = SSL.parseX509Chain(keyCertChainBio);
            if (key != null) {
                keyBio = ReferenceCountedOpenSslContext.toBIO(key);
                pkey = SSL.parsePrivateKey(keyBio, this.password);
            }
            CertificateRequestedCallback.KeyMaterial material = new CertificateRequestedCallback.KeyMaterial(certChain, pkey);
            pkey = 0L;
            certChain = 0L;
            CertificateRequestedCallback.KeyMaterial keyMaterial = material;
            return keyMaterial;
        } catch (SSLException e) {
            throw e;
        } catch (Exception e) {
            throw new SSLException(e);
        } finally {
            ReferenceCountedOpenSslContext.freeBio(keyBio);
            ReferenceCountedOpenSslContext.freeBio(keyCertChainBio);
            SSL.freePrivateKey(pkey);
            SSL.freeX509Chain(certChain);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setKeyMaterial(long ssl, String alias) throws SSLException {
        long keyBio = 0L;
        long keyCertChainBio = 0L;
        long keyCertChainBio2 = 0L;
        try {
            X509Certificate[] certificates = this.keyManager.getCertificateChain(alias);
            if (certificates == null || certificates.length == 0) {
                return;
            }
            PrivateKey key = this.keyManager.getPrivateKey(alias);
            PemEncoded encoded = PemX509Certificate.toPEM(ByteBufAllocator.DEFAULT, true, certificates);
            try {
                keyCertChainBio = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
                keyCertChainBio2 = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
                if (key != null) {
                    keyBio = ReferenceCountedOpenSslContext.toBIO(key);
                }
                SSL.setCertificateBio(ssl, keyCertChainBio, keyBio, this.password);
                SSL.setCertificateChainBio(ssl, keyCertChainBio2, true);
            } finally {
                encoded.release();
            }
        } catch (SSLException e) {
            throw e;
        } catch (Exception e) {
            throw new SSLException(e);
        } finally {
            ReferenceCountedOpenSslContext.freeBio(keyBio);
            ReferenceCountedOpenSslContext.freeBio(keyCertChainBio);
            ReferenceCountedOpenSslContext.freeBio(keyCertChainBio2);
        }
    }

    protected String chooseClientAlias(ReferenceCountedOpenSslEngine engine, String[] keyTypes, X500Principal[] issuer) {
        return this.keyManager.chooseClientAlias(keyTypes, issuer, null);
    }

    protected String chooseServerAlias(ReferenceCountedOpenSslEngine engine, String type2) {
        return this.keyManager.chooseServerAlias(type2, null, null);
    }

    static {
        KEY_TYPES.put(KEY_TYPE_RSA, KEY_TYPE_RSA);
        KEY_TYPES.put("DHE_RSA", KEY_TYPE_RSA);
        KEY_TYPES.put("ECDHE_RSA", KEY_TYPE_RSA);
        KEY_TYPES.put("ECDHE_ECDSA", KEY_TYPE_EC);
        KEY_TYPES.put("ECDH_RSA", KEY_TYPE_EC_RSA);
        KEY_TYPES.put("ECDH_ECDSA", KEY_TYPE_EC_EC);
        KEY_TYPES.put(KEY_TYPE_DH_RSA, KEY_TYPE_DH_RSA);
    }
}

