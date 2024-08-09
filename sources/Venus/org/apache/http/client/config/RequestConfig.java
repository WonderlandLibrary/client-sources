/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.config;

import java.net.InetAddress;
import java.util.Collection;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RequestConfig
implements Cloneable {
    public static final RequestConfig DEFAULT = new Builder().build();
    private final boolean expectContinueEnabled;
    private final HttpHost proxy;
    private final InetAddress localAddress;
    private final boolean staleConnectionCheckEnabled;
    private final String cookieSpec;
    private final boolean redirectsEnabled;
    private final boolean relativeRedirectsAllowed;
    private final boolean circularRedirectsAllowed;
    private final int maxRedirects;
    private final boolean authenticationEnabled;
    private final Collection<String> targetPreferredAuthSchemes;
    private final Collection<String> proxyPreferredAuthSchemes;
    private final int connectionRequestTimeout;
    private final int connectTimeout;
    private final int socketTimeout;
    private final boolean contentCompressionEnabled;
    private final boolean normalizeUri;

    protected RequestConfig() {
        this(false, null, null, false, null, false, false, false, 0, false, null, null, 0, 0, 0, true, true);
    }

    RequestConfig(boolean bl, HttpHost httpHost, InetAddress inetAddress, boolean bl2, String string, boolean bl3, boolean bl4, boolean bl5, int n, boolean bl6, Collection<String> collection, Collection<String> collection2, int n2, int n3, int n4, boolean bl7, boolean bl8) {
        this.expectContinueEnabled = bl;
        this.proxy = httpHost;
        this.localAddress = inetAddress;
        this.staleConnectionCheckEnabled = bl2;
        this.cookieSpec = string;
        this.redirectsEnabled = bl3;
        this.relativeRedirectsAllowed = bl4;
        this.circularRedirectsAllowed = bl5;
        this.maxRedirects = n;
        this.authenticationEnabled = bl6;
        this.targetPreferredAuthSchemes = collection;
        this.proxyPreferredAuthSchemes = collection2;
        this.connectionRequestTimeout = n2;
        this.connectTimeout = n3;
        this.socketTimeout = n4;
        this.contentCompressionEnabled = bl7;
        this.normalizeUri = bl8;
    }

    public boolean isExpectContinueEnabled() {
        return this.expectContinueEnabled;
    }

    public HttpHost getProxy() {
        return this.proxy;
    }

    public InetAddress getLocalAddress() {
        return this.localAddress;
    }

    @Deprecated
    public boolean isStaleConnectionCheckEnabled() {
        return this.staleConnectionCheckEnabled;
    }

    public String getCookieSpec() {
        return this.cookieSpec;
    }

    public boolean isRedirectsEnabled() {
        return this.redirectsEnabled;
    }

    public boolean isRelativeRedirectsAllowed() {
        return this.relativeRedirectsAllowed;
    }

    public boolean isCircularRedirectsAllowed() {
        return this.circularRedirectsAllowed;
    }

    public int getMaxRedirects() {
        return this.maxRedirects;
    }

    public boolean isAuthenticationEnabled() {
        return this.authenticationEnabled;
    }

    public Collection<String> getTargetPreferredAuthSchemes() {
        return this.targetPreferredAuthSchemes;
    }

    public Collection<String> getProxyPreferredAuthSchemes() {
        return this.proxyPreferredAuthSchemes;
    }

    public int getConnectionRequestTimeout() {
        return this.connectionRequestTimeout;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    @Deprecated
    public boolean isDecompressionEnabled() {
        return this.contentCompressionEnabled;
    }

    public boolean isContentCompressionEnabled() {
        return this.contentCompressionEnabled;
    }

    public boolean isNormalizeUri() {
        return this.normalizeUri;
    }

    protected RequestConfig clone() throws CloneNotSupportedException {
        return (RequestConfig)super.clone();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append("expectContinueEnabled=").append(this.expectContinueEnabled);
        stringBuilder.append(", proxy=").append(this.proxy);
        stringBuilder.append(", localAddress=").append(this.localAddress);
        stringBuilder.append(", cookieSpec=").append(this.cookieSpec);
        stringBuilder.append(", redirectsEnabled=").append(this.redirectsEnabled);
        stringBuilder.append(", relativeRedirectsAllowed=").append(this.relativeRedirectsAllowed);
        stringBuilder.append(", maxRedirects=").append(this.maxRedirects);
        stringBuilder.append(", circularRedirectsAllowed=").append(this.circularRedirectsAllowed);
        stringBuilder.append(", authenticationEnabled=").append(this.authenticationEnabled);
        stringBuilder.append(", targetPreferredAuthSchemes=").append(this.targetPreferredAuthSchemes);
        stringBuilder.append(", proxyPreferredAuthSchemes=").append(this.proxyPreferredAuthSchemes);
        stringBuilder.append(", connectionRequestTimeout=").append(this.connectionRequestTimeout);
        stringBuilder.append(", connectTimeout=").append(this.connectTimeout);
        stringBuilder.append(", socketTimeout=").append(this.socketTimeout);
        stringBuilder.append(", contentCompressionEnabled=").append(this.contentCompressionEnabled);
        stringBuilder.append(", normalizeUri=").append(this.normalizeUri);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static Builder custom() {
        return new Builder();
    }

    public static Builder copy(RequestConfig requestConfig) {
        return new Builder().setExpectContinueEnabled(requestConfig.isExpectContinueEnabled()).setProxy(requestConfig.getProxy()).setLocalAddress(requestConfig.getLocalAddress()).setStaleConnectionCheckEnabled(requestConfig.isStaleConnectionCheckEnabled()).setCookieSpec(requestConfig.getCookieSpec()).setRedirectsEnabled(requestConfig.isRedirectsEnabled()).setRelativeRedirectsAllowed(requestConfig.isRelativeRedirectsAllowed()).setCircularRedirectsAllowed(requestConfig.isCircularRedirectsAllowed()).setMaxRedirects(requestConfig.getMaxRedirects()).setAuthenticationEnabled(requestConfig.isAuthenticationEnabled()).setTargetPreferredAuthSchemes(requestConfig.getTargetPreferredAuthSchemes()).setProxyPreferredAuthSchemes(requestConfig.getProxyPreferredAuthSchemes()).setConnectionRequestTimeout(requestConfig.getConnectionRequestTimeout()).setConnectTimeout(requestConfig.getConnectTimeout()).setSocketTimeout(requestConfig.getSocketTimeout()).setDecompressionEnabled(requestConfig.isDecompressionEnabled()).setContentCompressionEnabled(requestConfig.isContentCompressionEnabled()).setNormalizeUri(requestConfig.isNormalizeUri());
    }

    protected Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    public static class Builder {
        private boolean expectContinueEnabled;
        private HttpHost proxy;
        private InetAddress localAddress;
        private boolean staleConnectionCheckEnabled = false;
        private String cookieSpec;
        private boolean redirectsEnabled = true;
        private boolean relativeRedirectsAllowed = true;
        private boolean circularRedirectsAllowed;
        private int maxRedirects = 50;
        private boolean authenticationEnabled = true;
        private Collection<String> targetPreferredAuthSchemes;
        private Collection<String> proxyPreferredAuthSchemes;
        private int connectionRequestTimeout = -1;
        private int connectTimeout = -1;
        private int socketTimeout = -1;
        private boolean contentCompressionEnabled = true;
        private boolean normalizeUri = true;

        Builder() {
        }

        public Builder setExpectContinueEnabled(boolean bl) {
            this.expectContinueEnabled = bl;
            return this;
        }

        public Builder setProxy(HttpHost httpHost) {
            this.proxy = httpHost;
            return this;
        }

        public Builder setLocalAddress(InetAddress inetAddress) {
            this.localAddress = inetAddress;
            return this;
        }

        @Deprecated
        public Builder setStaleConnectionCheckEnabled(boolean bl) {
            this.staleConnectionCheckEnabled = bl;
            return this;
        }

        public Builder setCookieSpec(String string) {
            this.cookieSpec = string;
            return this;
        }

        public Builder setRedirectsEnabled(boolean bl) {
            this.redirectsEnabled = bl;
            return this;
        }

        public Builder setRelativeRedirectsAllowed(boolean bl) {
            this.relativeRedirectsAllowed = bl;
            return this;
        }

        public Builder setCircularRedirectsAllowed(boolean bl) {
            this.circularRedirectsAllowed = bl;
            return this;
        }

        public Builder setMaxRedirects(int n) {
            this.maxRedirects = n;
            return this;
        }

        public Builder setAuthenticationEnabled(boolean bl) {
            this.authenticationEnabled = bl;
            return this;
        }

        public Builder setTargetPreferredAuthSchemes(Collection<String> collection) {
            this.targetPreferredAuthSchemes = collection;
            return this;
        }

        public Builder setProxyPreferredAuthSchemes(Collection<String> collection) {
            this.proxyPreferredAuthSchemes = collection;
            return this;
        }

        public Builder setConnectionRequestTimeout(int n) {
            this.connectionRequestTimeout = n;
            return this;
        }

        public Builder setConnectTimeout(int n) {
            this.connectTimeout = n;
            return this;
        }

        public Builder setSocketTimeout(int n) {
            this.socketTimeout = n;
            return this;
        }

        @Deprecated
        public Builder setDecompressionEnabled(boolean bl) {
            this.contentCompressionEnabled = bl;
            return this;
        }

        public Builder setContentCompressionEnabled(boolean bl) {
            this.contentCompressionEnabled = bl;
            return this;
        }

        public Builder setNormalizeUri(boolean bl) {
            this.normalizeUri = bl;
            return this;
        }

        public RequestConfig build() {
            return new RequestConfig(this.expectContinueEnabled, this.proxy, this.localAddress, this.staleConnectionCheckEnabled, this.cookieSpec, this.redirectsEnabled, this.relativeRedirectsAllowed, this.circularRedirectsAllowed, this.maxRedirects, this.authenticationEnabled, this.targetPreferredAuthSchemes, this.proxyPreferredAuthSchemes, this.connectionRequestTimeout, this.connectTimeout, this.socketTimeout, this.contentCompressionEnabled, this.normalizeUri);
        }
    }
}

