/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.IdRegistry;
import io.jsonwebtoken.impl.security.DefaultMacAlgorithm;
import io.jsonwebtoken.impl.security.EcSignatureAlgorithm;
import io.jsonwebtoken.impl.security.EdSignatureAlgorithm;
import io.jsonwebtoken.impl.security.NoneSignatureAlgorithm;
import io.jsonwebtoken.impl.security.RsaSignatureAlgorithm;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Password;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import java.security.Key;
import java.security.PrivateKey;
import javax.crypto.SecretKey;

public final class StandardSecureDigestAlgorithms
extends IdRegistry<SecureDigestAlgorithm<?, ?>> {
    public static final String NAME = "JWS Digital Signature or MAC";

    public StandardSecureDigestAlgorithms() {
        super(NAME, Collections.of(NoneSignatureAlgorithm.INSTANCE, DefaultMacAlgorithm.HS256, DefaultMacAlgorithm.HS384, DefaultMacAlgorithm.HS512, RsaSignatureAlgorithm.RS256, RsaSignatureAlgorithm.RS384, RsaSignatureAlgorithm.RS512, RsaSignatureAlgorithm.PS256, RsaSignatureAlgorithm.PS384, RsaSignatureAlgorithm.PS512, EcSignatureAlgorithm.ES256, EcSignatureAlgorithm.ES384, EcSignatureAlgorithm.ES512, EdSignatureAlgorithm.INSTANCE));
    }

    public static <K extends Key> SecureDigestAlgorithm<K, ?> findBySigningKey(K k) {
        SecureDigestAlgorithm<SecretKey, SecretKey> secureDigestAlgorithm = null;
        if (k instanceof SecretKey && !(k instanceof Password)) {
            secureDigestAlgorithm = DefaultMacAlgorithm.findByKey(k);
        } else if (k instanceof PrivateKey) {
            PrivateKey privateKey = (PrivateKey)k;
            secureDigestAlgorithm = RsaSignatureAlgorithm.findByKey(privateKey);
            if (secureDigestAlgorithm == null) {
                secureDigestAlgorithm = EcSignatureAlgorithm.findByKey(privateKey);
            }
            if (secureDigestAlgorithm == null && EdSignatureAlgorithm.isSigningKey(privateKey)) {
                secureDigestAlgorithm = EdSignatureAlgorithm.INSTANCE;
            }
        }
        return secureDigestAlgorithm;
    }
}

