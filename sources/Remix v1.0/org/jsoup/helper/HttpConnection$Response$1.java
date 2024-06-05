package org.jsoup.helper;

import javax.net.ssl.*;

static final class HttpConnection$Response$1 implements HostnameVerifier {
    public boolean verify(final String urlHostName, final SSLSession session) {
        return true;
    }
}