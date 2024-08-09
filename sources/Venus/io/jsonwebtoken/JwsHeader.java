/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.ProtectedHeader;

public interface JwsHeader
extends ProtectedHeader {
    @Deprecated
    public static final String ALGORITHM = "alg";
    @Deprecated
    public static final String JWK_SET_URL = "jku";
    @Deprecated
    public static final String JSON_WEB_KEY = "jwk";
    @Deprecated
    public static final String KEY_ID = "kid";
    @Deprecated
    public static final String X509_URL = "x5u";
    @Deprecated
    public static final String X509_CERT_CHAIN = "x5c";
    @Deprecated
    public static final String X509_CERT_SHA1_THUMBPRINT = "x5t";
    @Deprecated
    public static final String X509_CERT_SHA256_THUMBPRINT = "x5t#S256";
    @Deprecated
    public static final String CRITICAL = "crit";

    public boolean isPayloadEncoded();
}

