package org.jsoup.helper;

import javax.net.ssl.*;
import java.security.cert.*;

static final class HttpConnection$Response$2 implements X509TrustManager {
    public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
    }
    
    public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
    }
    
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}