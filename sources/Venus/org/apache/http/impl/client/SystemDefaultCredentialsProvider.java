/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.SAFE)
public class SystemDefaultCredentialsProvider
implements CredentialsProvider {
    private static final Map<String, String> SCHEME_MAP = new ConcurrentHashMap<String, String>();
    private final BasicCredentialsProvider internal = new BasicCredentialsProvider();

    private static String translateScheme(String string) {
        if (string == null) {
            return null;
        }
        String string2 = SCHEME_MAP.get(string);
        return string2 != null ? string2 : string;
    }

    @Override
    public void setCredentials(AuthScope authScope, Credentials credentials) {
        this.internal.setCredentials(authScope, credentials);
    }

    private static PasswordAuthentication getSystemCreds(String string, AuthScope authScope, Authenticator.RequestorType requestorType) {
        return Authenticator.requestPasswordAuthentication(authScope.getHost(), null, authScope.getPort(), string, null, SystemDefaultCredentialsProvider.translateScheme(authScope.getScheme()), null, requestorType);
    }

    @Override
    public Credentials getCredentials(AuthScope authScope) {
        Args.notNull(authScope, "Auth scope");
        Credentials credentials = this.internal.getCredentials(authScope);
        if (credentials != null) {
            return credentials;
        }
        String string = authScope.getHost();
        if (string != null) {
            HttpHost httpHost = authScope.getOrigin();
            String string2 = httpHost != null ? httpHost.getSchemeName() : (authScope.getPort() == 443 ? "https" : "http");
            PasswordAuthentication passwordAuthentication = SystemDefaultCredentialsProvider.getSystemCreds(string2, authScope, Authenticator.RequestorType.SERVER);
            if (passwordAuthentication == null) {
                passwordAuthentication = SystemDefaultCredentialsProvider.getSystemCreds(string2, authScope, Authenticator.RequestorType.PROXY);
            }
            if (passwordAuthentication == null && (passwordAuthentication = SystemDefaultCredentialsProvider.getProxyCredentials("http", authScope)) == null) {
                passwordAuthentication = SystemDefaultCredentialsProvider.getProxyCredentials("https", authScope);
            }
            if (passwordAuthentication != null) {
                String string3 = System.getProperty("http.auth.ntlm.domain");
                if (string3 != null) {
                    return new NTCredentials(passwordAuthentication.getUserName(), new String(passwordAuthentication.getPassword()), null, string3);
                }
                return "NTLM".equalsIgnoreCase(authScope.getScheme()) ? new NTCredentials(passwordAuthentication.getUserName(), new String(passwordAuthentication.getPassword()), null, null) : new UsernamePasswordCredentials(passwordAuthentication.getUserName(), new String(passwordAuthentication.getPassword()));
            }
        }
        return null;
    }

    private static PasswordAuthentication getProxyCredentials(String string, AuthScope authScope) {
        String string2 = System.getProperty(string + ".proxyHost");
        if (string2 == null) {
            return null;
        }
        String string3 = System.getProperty(string + ".proxyPort");
        if (string3 == null) {
            return null;
        }
        try {
            AuthScope authScope2 = new AuthScope(string2, Integer.parseInt(string3));
            if (authScope.match(authScope2) >= 0) {
                String string4 = System.getProperty(string + ".proxyUser");
                if (string4 == null) {
                    return null;
                }
                String string5 = System.getProperty(string + ".proxyPassword");
                return new PasswordAuthentication(string4, string5 != null ? string5.toCharArray() : new char[]{});
            }
        } catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return null;
    }

    @Override
    public void clear() {
        this.internal.clear();
    }

    static {
        SCHEME_MAP.put("Basic".toUpperCase(Locale.ROOT), "Basic");
        SCHEME_MAP.put("Digest".toUpperCase(Locale.ROOT), "Digest");
        SCHEME_MAP.put("NTLM".toUpperCase(Locale.ROOT), "NTLM");
        SCHEME_MAP.put("Negotiate".toUpperCase(Locale.ROOT), "SPNEGO");
        SCHEME_MAP.put("Kerberos".toUpperCase(Locale.ROOT), "Kerberos");
    }
}

