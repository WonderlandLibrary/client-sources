/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.auth;

import java.util.Locale;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class AuthScope {
    public static final String ANY_HOST = null;
    public static final int ANY_PORT = -1;
    public static final String ANY_REALM = null;
    public static final String ANY_SCHEME = null;
    public static final AuthScope ANY = new AuthScope(ANY_HOST, -1, ANY_REALM, ANY_SCHEME);
    private final String scheme;
    private final String realm;
    private final String host;
    private final int port;
    private final HttpHost origin;

    public AuthScope(String string, int n, String string2, String string3) {
        this.host = string == null ? ANY_HOST : string.toLowerCase(Locale.ROOT);
        this.port = n < 0 ? -1 : n;
        this.realm = string2 == null ? ANY_REALM : string2;
        this.scheme = string3 == null ? ANY_SCHEME : string3.toUpperCase(Locale.ROOT);
        this.origin = null;
    }

    public AuthScope(HttpHost httpHost, String string, String string2) {
        Args.notNull(httpHost, "Host");
        this.host = httpHost.getHostName().toLowerCase(Locale.ROOT);
        this.port = httpHost.getPort() < 0 ? -1 : httpHost.getPort();
        this.realm = string == null ? ANY_REALM : string;
        this.scheme = string2 == null ? ANY_SCHEME : string2.toUpperCase(Locale.ROOT);
        this.origin = httpHost;
    }

    public AuthScope(HttpHost httpHost) {
        this(httpHost, ANY_REALM, ANY_SCHEME);
    }

    public AuthScope(String string, int n, String string2) {
        this(string, n, string2, ANY_SCHEME);
    }

    public AuthScope(String string, int n) {
        this(string, n, ANY_REALM, ANY_SCHEME);
    }

    public AuthScope(AuthScope authScope) {
        Args.notNull(authScope, "Scope");
        this.host = authScope.getHost();
        this.port = authScope.getPort();
        this.realm = authScope.getRealm();
        this.scheme = authScope.getScheme();
        this.origin = authScope.getOrigin();
    }

    public HttpHost getOrigin() {
        return this.origin;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getRealm() {
        return this.realm;
    }

    public String getScheme() {
        return this.scheme;
    }

    public int match(AuthScope authScope) {
        int n = 0;
        if (LangUtils.equals(this.scheme, authScope.scheme)) {
            ++n;
        } else if (this.scheme != ANY_SCHEME && authScope.scheme != ANY_SCHEME) {
            return 1;
        }
        if (LangUtils.equals(this.realm, authScope.realm)) {
            n += 2;
        } else if (this.realm != ANY_REALM && authScope.realm != ANY_REALM) {
            return 1;
        }
        if (this.port == authScope.port) {
            n += 4;
        } else if (this.port != -1 && authScope.port != -1) {
            return 1;
        }
        if (LangUtils.equals(this.host, authScope.host)) {
            n += 8;
        } else if (this.host != ANY_HOST && authScope.host != ANY_HOST) {
            return 1;
        }
        return n;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (object == this) {
            return false;
        }
        if (!(object instanceof AuthScope)) {
            return super.equals(object);
        }
        AuthScope authScope = (AuthScope)object;
        return LangUtils.equals(this.host, authScope.host) && this.port == authScope.port && LangUtils.equals(this.realm, authScope.realm) && LangUtils.equals(this.scheme, authScope.scheme);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.scheme != null) {
            stringBuilder.append(this.scheme.toUpperCase(Locale.ROOT));
            stringBuilder.append(' ');
        }
        if (this.realm != null) {
            stringBuilder.append('\'');
            stringBuilder.append(this.realm);
            stringBuilder.append('\'');
        } else {
            stringBuilder.append("<any realm>");
        }
        if (this.host != null) {
            stringBuilder.append('@');
            stringBuilder.append(this.host);
            if (this.port >= 0) {
                stringBuilder.append(':');
                stringBuilder.append(this.port);
            }
        }
        return stringBuilder.toString();
    }

    public int hashCode() {
        int n = 17;
        n = LangUtils.hashCode(n, this.host);
        n = LangUtils.hashCode(n, this.port);
        n = LangUtils.hashCode(n, this.realm);
        n = LangUtils.hashCode(n, this.scheme);
        return n;
    }
}

