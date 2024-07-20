/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Http2SecurityUtil {
    public static final List<String> CIPHERS;
    private static final List<String> CIPHERS_JAVA_MOZILLA_INCREASED_SECURITY;
    private static final List<String> CIPHERS_JAVA_NO_MOZILLA_INCREASED_SECURITY;

    private Http2SecurityUtil() {
    }

    static {
        CIPHERS_JAVA_MOZILLA_INCREASED_SECURITY = Collections.unmodifiableList(Arrays.asList("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "SSL_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "SSL_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "SSL_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "SSL_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", "SSL_DHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", "SSL_DHE_DSS_WITH_AES_128_GCM_SHA256"));
        CIPHERS_JAVA_NO_MOZILLA_INCREASED_SECURITY = Collections.unmodifiableList(Arrays.asList("TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", "SSL_DHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", "SSL_DHE_DSS_WITH_AES_256_GCM_SHA384"));
        ArrayList<String> ciphers = new ArrayList<String>(CIPHERS_JAVA_MOZILLA_INCREASED_SECURITY.size() + CIPHERS_JAVA_NO_MOZILLA_INCREASED_SECURITY.size());
        ciphers.addAll(CIPHERS_JAVA_MOZILLA_INCREASED_SECURITY);
        ciphers.addAll(CIPHERS_JAVA_NO_MOZILLA_INCREASED_SECURITY);
        CIPHERS = Collections.unmodifiableList(ciphers);
    }
}

