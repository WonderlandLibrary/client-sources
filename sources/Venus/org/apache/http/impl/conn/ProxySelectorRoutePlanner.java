/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
public class ProxySelectorRoutePlanner
implements HttpRoutePlanner {
    protected final SchemeRegistry schemeRegistry;
    protected ProxySelector proxySelector;

    public ProxySelectorRoutePlanner(SchemeRegistry schemeRegistry, ProxySelector proxySelector) {
        Args.notNull(schemeRegistry, "SchemeRegistry");
        this.schemeRegistry = schemeRegistry;
        this.proxySelector = proxySelector;
    }

    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public void setProxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
    }

    @Override
    public HttpRoute determineRoute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        Args.notNull(httpRequest, "HTTP request");
        HttpRoute httpRoute = ConnRouteParams.getForcedRoute(httpRequest.getParams());
        if (httpRoute != null) {
            return httpRoute;
        }
        Asserts.notNull(httpHost, "Target host");
        InetAddress inetAddress = ConnRouteParams.getLocalAddress(httpRequest.getParams());
        HttpHost httpHost2 = this.determineProxy(httpHost, httpRequest, httpContext);
        Scheme scheme = this.schemeRegistry.getScheme(httpHost.getSchemeName());
        boolean bl = scheme.isLayered();
        httpRoute = httpHost2 == null ? new HttpRoute(httpHost, inetAddress, bl) : new HttpRoute(httpHost, inetAddress, httpHost2, bl);
        return httpRoute;
    }

    protected HttpHost determineProxy(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        ProxySelector proxySelector = this.proxySelector;
        if (proxySelector == null) {
            proxySelector = ProxySelector.getDefault();
        }
        if (proxySelector == null) {
            return null;
        }
        URI uRI = null;
        try {
            uRI = new URI(httpHost.toURI());
        } catch (URISyntaxException uRISyntaxException) {
            throw new HttpException("Cannot convert host to URI: " + httpHost, uRISyntaxException);
        }
        List<Proxy> list = proxySelector.select(uRI);
        Proxy proxy = this.chooseProxy(list, httpHost, httpRequest, httpContext);
        HttpHost httpHost2 = null;
        if (proxy.type() == Proxy.Type.HTTP) {
            if (!(proxy.address() instanceof InetSocketAddress)) {
                throw new HttpException("Unable to handle non-Inet proxy address: " + proxy.address());
            }
            InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy.address();
            httpHost2 = new HttpHost(this.getHost(inetSocketAddress), inetSocketAddress.getPort());
        }
        return httpHost2;
    }

    protected String getHost(InetSocketAddress inetSocketAddress) {
        return inetSocketAddress.isUnresolved() ? inetSocketAddress.getHostName() : inetSocketAddress.getAddress().getHostAddress();
    }

    protected Proxy chooseProxy(List<Proxy> list, HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        Args.notEmpty(list, "List of proxies");
        Proxy proxy = null;
        block3: for (int i = 0; proxy == null && i < list.size(); ++i) {
            Proxy proxy2 = list.get(i);
            switch (1.$SwitchMap$java$net$Proxy$Type[proxy2.type().ordinal()]) {
                case 1: 
                case 2: {
                    proxy = proxy2;
                    continue block3;
                }
            }
        }
        if (proxy == null) {
            proxy = Proxy.NO_PROXY;
        }
        return proxy;
    }

    static class 1 {
        static final int[] $SwitchMap$java$net$Proxy$Type = new int[Proxy.Type.values().length];

        static {
            try {
                1.$SwitchMap$java$net$Proxy$Type[Proxy.Type.DIRECT.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$java$net$Proxy$Type[Proxy.Type.HTTP.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$java$net$Proxy$Type[Proxy.Type.SOCKS.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }
}

