/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Locale;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public final class HttpHost
implements Cloneable,
Serializable {
    private static final long serialVersionUID = -7529410654042457626L;
    public static final String DEFAULT_SCHEME_NAME = "http";
    protected final String hostname;
    protected final String lcHostname;
    protected final int port;
    protected final String schemeName;
    protected final InetAddress address;

    public HttpHost(String string, int n, String string2) {
        this.hostname = Args.containsNoBlanks(string, "Host name");
        this.lcHostname = string.toLowerCase(Locale.ROOT);
        this.schemeName = string2 != null ? string2.toLowerCase(Locale.ROOT) : DEFAULT_SCHEME_NAME;
        this.port = n;
        this.address = null;
    }

    public HttpHost(String string, int n) {
        this(string, n, null);
    }

    public static HttpHost create(String string) {
        Args.containsNoBlanks(string, "HTTP Host");
        String string2 = string;
        String string3 = null;
        int n = string2.indexOf("://");
        if (n > 0) {
            string3 = string2.substring(0, n);
            string2 = string2.substring(n + 3);
        }
        int n2 = -1;
        int n3 = string2.lastIndexOf(":");
        if (n3 > 0) {
            try {
                n2 = Integer.parseInt(string2.substring(n3 + 1));
            } catch (NumberFormatException numberFormatException) {
                throw new IllegalArgumentException("Invalid HTTP host: " + string2);
            }
            string2 = string2.substring(0, n3);
        }
        return new HttpHost(string2, n2, string3);
    }

    public HttpHost(String string) {
        this(string, -1, null);
    }

    public HttpHost(InetAddress inetAddress, int n, String string) {
        this(Args.notNull(inetAddress, "Inet address"), inetAddress.getHostName(), n, string);
    }

    public HttpHost(InetAddress inetAddress, String string, int n, String string2) {
        this.address = Args.notNull(inetAddress, "Inet address");
        this.hostname = Args.notNull(string, "Hostname");
        this.lcHostname = this.hostname.toLowerCase(Locale.ROOT);
        this.schemeName = string2 != null ? string2.toLowerCase(Locale.ROOT) : DEFAULT_SCHEME_NAME;
        this.port = n;
    }

    public HttpHost(InetAddress inetAddress, int n) {
        this(inetAddress, n, null);
    }

    public HttpHost(InetAddress inetAddress) {
        this(inetAddress, -1, null);
    }

    public HttpHost(HttpHost httpHost) {
        Args.notNull(httpHost, "HTTP host");
        this.hostname = httpHost.hostname;
        this.lcHostname = httpHost.lcHostname;
        this.schemeName = httpHost.schemeName;
        this.port = httpHost.port;
        this.address = httpHost.address;
    }

    public String getHostName() {
        return this.hostname;
    }

    public int getPort() {
        return this.port;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    public String toURI() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.schemeName);
        stringBuilder.append("://");
        stringBuilder.append(this.hostname);
        if (this.port != -1) {
            stringBuilder.append(':');
            stringBuilder.append(Integer.toString(this.port));
        }
        return stringBuilder.toString();
    }

    public String toHostString() {
        if (this.port != -1) {
            StringBuilder stringBuilder = new StringBuilder(this.hostname.length() + 6);
            stringBuilder.append(this.hostname);
            stringBuilder.append(":");
            stringBuilder.append(Integer.toString(this.port));
            return stringBuilder.toString();
        }
        return this.hostname;
    }

    public String toString() {
        return this.toURI();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof HttpHost) {
            HttpHost httpHost = (HttpHost)object;
            return this.lcHostname.equals(httpHost.lcHostname) && this.port == httpHost.port && this.schemeName.equals(httpHost.schemeName) && (this.address == null ? httpHost.address == null : this.address.equals(httpHost.address));
        }
        return true;
    }

    public int hashCode() {
        int n = 17;
        n = LangUtils.hashCode(n, this.lcHostname);
        n = LangUtils.hashCode(n, this.port);
        n = LangUtils.hashCode(n, this.schemeName);
        if (this.address != null) {
            n = LangUtils.hashCode(n, this.address);
        }
        return n;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

