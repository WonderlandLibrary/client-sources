package me.dinozoid.strife.util.network;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class AllHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
