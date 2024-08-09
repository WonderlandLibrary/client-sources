/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  io.netty.internal.tcnative.CertificateRequestedCallback$KeyMaterial
 *  io.netty.internal.tcnative.SSL
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

    OpenSslKeyMaterialManager(X509KeyManager x509KeyManager, String string) {
        this.keyManager = x509KeyManager;
        this.password = string;
    }

    void setKeyMaterial(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) throws SSLException {
        long l = referenceCountedOpenSslEngine.sslPointer();
        String[] stringArray = SSL.authenticationMethods((long)l);
        HashSet<String> hashSet = new HashSet<String>(stringArray.length);
        for (String string : stringArray) {
            String string2;
            String string3 = KEY_TYPES.get(string);
            if (string3 == null || (string2 = this.chooseServerAlias(referenceCountedOpenSslEngine, string3)) == null || !hashSet.add(string2)) continue;
            this.setKeyMaterial(l, string2);
        }
    }

    CertificateRequestedCallback.KeyMaterial keyMaterial(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String[] stringArray, X500Principal[] x500PrincipalArray) throws SSLException {
        String string = this.chooseClientAlias(referenceCountedOpenSslEngine, stringArray, x500PrincipalArray);
        long l = 0L;
        long l2 = 0L;
        long l3 = 0L;
        long l4 = 0L;
        try {
            X509Certificate[] x509CertificateArray = this.keyManager.getCertificateChain(string);
            if (x509CertificateArray == null || x509CertificateArray.length == 0) {
                CertificateRequestedCallback.KeyMaterial keyMaterial = null;
                return keyMaterial;
            }
            PrivateKey privateKey = this.keyManager.getPrivateKey(string);
            l2 = ReferenceCountedOpenSslContext.toBIO(x509CertificateArray);
            l4 = SSL.parseX509Chain((long)l2);
            if (privateKey != null) {
                l = ReferenceCountedOpenSslContext.toBIO(privateKey);
                l3 = SSL.parsePrivateKey((long)l, (String)this.password);
            }
            CertificateRequestedCallback.KeyMaterial keyMaterial = new CertificateRequestedCallback.KeyMaterial(l4, l3);
            l3 = 0L;
            l4 = 0L;
            CertificateRequestedCallback.KeyMaterial keyMaterial2 = keyMaterial;
            return keyMaterial2;
        } catch (SSLException sSLException) {
            throw sSLException;
        } catch (Exception exception) {
            throw new SSLException(exception);
        } finally {
            ReferenceCountedOpenSslContext.freeBio(l);
            ReferenceCountedOpenSslContext.freeBio(l2);
            SSL.freePrivateKey((long)l3);
            SSL.freeX509Chain((long)l4);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setKeyMaterial(long l, String string) throws SSLException {
        long l2 = 0L;
        long l3 = 0L;
        long l4 = 0L;
        try {
            X509Certificate[] x509CertificateArray = this.keyManager.getCertificateChain(string);
            if (x509CertificateArray == null || x509CertificateArray.length == 0) {
                return;
            }
            PrivateKey privateKey = this.keyManager.getPrivateKey(string);
            PemEncoded pemEncoded = PemX509Certificate.toPEM(ByteBufAllocator.DEFAULT, true, x509CertificateArray);
            try {
                l3 = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, pemEncoded.retain());
                l4 = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, pemEncoded.retain());
                if (privateKey != null) {
                    l2 = ReferenceCountedOpenSslContext.toBIO(privateKey);
                }
                SSL.setCertificateBio((long)l, (long)l3, (long)l2, (String)this.password);
                SSL.setCertificateChainBio((long)l, (long)l4, (boolean)true);
            } finally {
                pemEncoded.release();
            }
        } catch (SSLException sSLException) {
            throw sSLException;
        } catch (Exception exception) {
            throw new SSLException(exception);
        } finally {
            ReferenceCountedOpenSslContext.freeBio(l2);
            ReferenceCountedOpenSslContext.freeBio(l3);
            ReferenceCountedOpenSslContext.freeBio(l4);
        }
    }

    protected String chooseClientAlias(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String[] stringArray, X500Principal[] x500PrincipalArray) {
        return this.keyManager.chooseClientAlias(stringArray, x500PrincipalArray, null);
    }

    protected String chooseServerAlias(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String string) {
        return this.keyManager.chooseServerAlias(string, null, null);
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

